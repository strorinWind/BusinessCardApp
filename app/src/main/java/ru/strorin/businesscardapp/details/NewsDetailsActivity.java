package ru.strorin.businesscardapp.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import ru.strorin.businesscardapp.R;
import ru.strorin.businesscardapp.data.network.dto.NewsDTO;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String NEWS_ITEM = "NEWS_ITEM";

    private WebView webView;

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

        webView = findViewById(R.id.webView);

        Bundle bundle = getIntent().getExtras();
        NewsDTO item = (NewsDTO) bundle.getSerializable(NEWS_ITEM);
        if (item != null) setDataFromNewsItem(item);

    }

    private void setDataFromNewsItem(NewsDTO newsItem){
        webView.loadUrl(newsItem.getUrl());
    }
}
