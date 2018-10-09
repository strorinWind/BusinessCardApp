package ru.strorin.businesscardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.strorin.businesscardapp.data.DataUtils;


public class NewListActivity extends AppCompatActivity {

    private final NewsItemRecyclerAdapter.OnItemClickListener clickListener = newsItem -> {
        String clickMessage = "Clicked " + newsItem.getTitle();
        Toast.makeText(this, clickMessage, Toast.LENGTH_SHORT).show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new NewsItemRecyclerAdapter(this,
                DataUtils.generateNews(), clickListener));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
