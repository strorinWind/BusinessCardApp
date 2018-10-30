package ru.strorin.businesscardapp.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;
import ru.strorin.businesscardapp.R;
import ru.strorin.businesscardapp.data.NewsItem;
import ru.strorin.businesscardapp.data.network.dto.NewsDTO;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String NEWS_ITEM = "NEWS_ITEM";

    private ImageView newsImageView;
    private TextView headerView;
    private TextView newsTextView;
    private TextView dateView;

    public static void start(Activity activity, NewsDTO item){
        Intent newsDetailsActivityIntent = new Intent(activity, NewsDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(NEWS_ITEM, item);
        newsDetailsActivityIntent.putExtras(bundle);
        activity.startActivity(newsDetailsActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        newsImageView = findViewById(R.id.news_details_image);
        headerView = findViewById(R.id.news_details_header);
        newsTextView = findViewById(R.id.news_details_text);
        dateView = findViewById(R.id.news_details_date);

        Bundle bundle = getIntent().getExtras();
        NewsItem item = (NewsItem) bundle.getSerializable(NEWS_ITEM);
        if (item != null) setDataFromNewsItem(item);
    }

    private void setDataFromNewsItem(NewsItem newsItem){
        RequestOptions imageOption = new RequestOptions()
                .centerCrop();
        RequestManager imageLoader = Glide.with(this).applyDefaultRequestOptions(imageOption);

        imageLoader.load(newsItem.getImageUrl()).into(newsImageView);
        setTitle(newsItem.getCategory().getName());
        headerView.setText(newsItem.getTitle());
        newsTextView.setText(newsItem.getFullText());
        dateView.setText(newsItem.getPrettyDateString());
    }
}
