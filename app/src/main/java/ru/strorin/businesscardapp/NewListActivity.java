package ru.strorin.businesscardapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private static final String NEWS_LOADED = "NEWS_LOADED";
    private static final String TAG = NewListActivity.class.getCanonicalName();

    private List<NewsItem> news = new ArrayList<>();
    private NewsItemRecyclerAdapter adapter;
    private ProgressBar progressBar;

    private Disposable observer;
    private boolean newsLoaded;

    private final NewsItemRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.start(this, newsItem);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        progressBar = findViewById(R.id.progress_bar);

        adapter = new NewsItemRecyclerAdapter(this, news, clickListener);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        setRecyclerViewDecoration(recyclerView);

        if (savedInstanceState == null){
            newsLoaded = false;
            getNews();
        }
        else {
            news = savedInstanceState.getParcelableArrayList(NEWS_LIST);
            newsLoaded = savedInstanceState.getBoolean(NEWS_LOADED);
            adapter.setDataset(news);
            if (!newsLoaded){
                getNews();
            }
            else hideProgressBar();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(NEWS_LIST, (ArrayList<NewsItem>) news);
        outState.putBoolean(NEWS_LOADED, newsLoaded);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newsLoaded && observer != null){
            observer.dispose();
        }
    }

    private void getNews() {
        Observable<? extends Long> delay = Observable.interval(2, TimeUnit.SECONDS);
        observer = Observable.zip(delay,
                Observable.fromIterable(DataUtils.generateNews()),
                (d, newsItem) -> newsItem)
                .doOnNext(item -> Log.d(TAG, Thread.currentThread().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadItem, e -> {}, () -> {
                    hideProgressBar();
                    newsLoaded = true;
                });
    }

    private void loadItem(NewsItem newsItem){
        if (!adapter.contains(newsItem)){
            adapter.addItem(newsItem);
            adapter.notifyItemInserted(adapter.getPosition(newsItem));
        }
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void setRecyclerViewDecoration(RecyclerView recyclerView){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
        else {
            int numColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
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
