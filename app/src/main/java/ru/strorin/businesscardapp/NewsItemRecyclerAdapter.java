package ru.strorin.businesscardapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.strorin.businesscardapp.data.NewsItem;

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsItemRecyclerAdapter.ViewHolder> {
    @NonNull
    private final List<NewsItem> news;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;
    private ViewHolder holder;
    private int position;

    public NewsItemRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> news,
                                   @Nullable OnItemClickListener clickListener) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        RequestOptions imageOption = new RequestOptions()
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.news_item_card, parent, false), clickListener
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView newsImageView;
        public final TextView categoryView;
        public final TextView headerView;
        public final TextView previewView;
        public final TextView dateView;


        ViewHolder(View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.news_image);
            categoryView = itemView.findViewById(R.id.category_text);
            headerView = itemView.findViewById(R.id.header_text);
            previewView = itemView.findViewById(R.id.preview_text);
            dateView = itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(news.get(position));
                }
            });
        }

        void bind(NewsItem newsItem) {
            imageLoader.load(newsItem.getImageUrl()).into(newsImageView);
            categoryView.setText(newsItem.getCategory().getName());
            headerView.setText(newsItem.getTitle());
            previewView.setText(newsItem.getPreviewText());
            dateView.setText(newsItem.getPublishDate().toString());
        }
    }
}
