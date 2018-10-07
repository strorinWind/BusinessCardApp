package ru.strorin.businesscardapp.data;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsItemRecyclerAdapter extends RecyclerView.Adapter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView, @Nullable AdapterView.OnItemClickListener listener) {
            super(itemView);
        }
    }
}
