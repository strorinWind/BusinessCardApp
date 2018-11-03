package ru.strorin.businesscardapp.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.strorin.businesscardapp.R;
import ru.strorin.businesscardapp.data.network.dto.NewsDTO;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final RequestManager imageLoader;

    private final ImageView newsImageView;
    private final TextView categoryView;
    private final TextView headerView;
    private final TextView previewView;
    private final TextView dateView;

    NewsViewHolder(View itemView, @Nullable NewsItemRecyclerAdapter.OnItemClickListener listener,
                   Context context, List<NewsDTO> news) {
        super(itemView);
        newsImageView = itemView.findViewById(R.id.news_image);
        categoryView = itemView.findViewById(R.id.category_text);
        headerView = itemView.findViewById(R.id.header_text);
        previewView = itemView.findViewById(R.id.preview_text);
        dateView = itemView.findViewById(R.id.date_text);

        RequestOptions imageOption = new RequestOptions()
                .centerCrop();
        imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);

        itemView.setOnClickListener(view -> {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClick(news.get(position));
            }
        });
    }

    void bind(NewsDTO newsItem) {
        headerView.setText(newsItem.getTitle());
        previewView.setText(newsItem.getPreviewText());
        dateView.setText(newsItem.getPrettyDateString());

        String categoryText = newsItem.getCategory();
        if (categoryText == null || categoryText.isEmpty() || categoryText.equals("")){
            categoryView.setVisibility(View.GONE);
        }
        else {
            categoryView.setText(newsItem.getCategory());
        }

        if (newsItem.getImageUrl() != null) {
            imageLoader.load(newsItem.getImageUrl()).into(newsImageView);
        }
        else {
            newsImageView.setImageResource(R.drawable.image_placeholder);
        }
    }
}