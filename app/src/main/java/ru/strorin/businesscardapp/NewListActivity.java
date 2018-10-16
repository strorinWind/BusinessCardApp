package ru.strorin.businesscardapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.strorin.businesscardapp.data.DataUtils;
import ru.strorin.businesscardapp.data.NewsItem;


public class NewListActivity extends AppCompatActivity {
    private static final String NEWS_LIST = "NEWS_LIST";
    private static final String TAG = NewListActivity.class.getCanonicalName();

    private List<NewsItem> news = new ArrayList<>();
    private NewsItemRecyclerAdapter adapter;
    private ProgressBar progressBar;

    private Disposable observer;

    private final NewsItemRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.start(this, newsItem);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        adapter = new NewsItemRecyclerAdapter(this, news, clickListener);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        setRecyclerViewDecoration(recyclerView);

        if (savedInstanceState == null){
            getNews();
        }
        else {
            news = savedInstanceState.getParcelableArrayList(NEWS_LIST);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(NEWS_LIST, (ArrayList<NewsItem>) news);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.dispose();
    }

    private void getNews() {
        Observable<? extends Long> delay = Observable.interval(2, TimeUnit.SECONDS);
        observer = Observable.zip(delay,
                Observable.fromIterable(DataUtils.generateNews()),
                (d, newsItem) -> newsItem)
                .doOnNext(item -> Log.e(TAG, Thread.currentThread().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadItem, e -> {}, this::hideProgressBar);
    }

    private void loadItem(NewsItem newsItem){
        adapter.addItem(newsItem);
        int progress = progressBar.getProgress() + 10;
        progressBar.setProgress(progress);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void setRecyclerViewDecoration(RecyclerView recyclerView){
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
            recyclerView.addItemDecoration(itemDecoration);
        }
        else {
            int numColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));

            Drawable divider = ContextCompat.getDrawable(this, R.drawable.divider);
            GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(divider, divider, numColumns);
            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
