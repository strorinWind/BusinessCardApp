package ru.strorin.businesscardapp.news;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.strorin.businesscardapp.R;
import ru.strorin.businesscardapp.data.NewsItem;

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    @NonNull
    private List<NewsItem> news;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;

    private Context context;

    NewsItemRecyclerAdapter(@NonNull Context cont, @NonNull List<NewsItem> newsList,
                            @Nullable OnItemClickListener listener) {
        if (newsList != null){
            news = newsList;
        }
        else {
            news = new ArrayList<>();
        }
        context = cont;
        inflater = LayoutInflater.from(context);
        clickListener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                inflater.inflate(R.layout.news_item_card, parent, false),
                clickListener,
                context,
                news
        );
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    public int getPosition(NewsItem newsItem){
        for (int i = 0; i < news.size(); i++){
            if (news.get(i).equals(newsItem)){
                return i;
            }
        }
        return -1;
    }

    public boolean contains(NewsItem newsItem){
        return getPosition(newsItem) != -1;
    }

    public void setDataset(List<NewsItem> newsList){
        news.clear();
        news.addAll(newsList);
        notifyDataSetChanged();
    }

    public void addItem(NewsItem item){
        news.add(item);
        notifyItemInserted(news.indexOf(item));
    }
}
