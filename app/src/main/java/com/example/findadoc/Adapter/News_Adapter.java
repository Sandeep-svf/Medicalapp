package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Model.dev.sam.NewsResult;
import com.example.findadoc.R;

import java.util.List;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.My_View_Holder> {
   Context context;

    public News_Adapter(List<NewsResult> newsResultList, Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        Glide.with(context).load(R.drawable.news_1).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        ImageView image;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }
}
