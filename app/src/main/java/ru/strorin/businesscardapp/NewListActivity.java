package ru.strorin.businesscardapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.strorin.businesscardapp.data.DataUtils;
import ru.strorin.businesscardapp.data.NewsItem;


public class NewListActivity extends AppCompatActivity {
    private List<NewsItem> news = new ArrayList<>();

    private final NewsItemRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        NewsDetailsActivity.start(this, newsItem);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        new Thread(() -> {
            news = DataUtils.generateNews();
            System.out.println(Thread.currentThread());
            runOnUiThread(() -> {
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setAdapter(new NewsItemRecyclerAdapter(this,
                        news, clickListener));
                setRecyclerViewDecoration(recyclerView);
            });
        }).start();
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
