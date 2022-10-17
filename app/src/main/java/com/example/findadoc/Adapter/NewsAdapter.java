package com.example.findadoc.Adapter;

import static com.example.findadoc.Retrofir.Api_client.BASE_IMAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Model.dev.sam.NewsRecord;
import com.example.findadoc.Model.dev.sam.NewsResult;
import com.example.findadoc.R;
import com.example.findadoc.sam.dev.Activity.NewsDetailsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    List<NewsRecord> newsResultList ;
    Context context;

    public NewsAdapter(List<NewsRecord> newsResultList, Context context) {
        this.newsResultList = newsResultList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_adapter, parent, false);
        return  new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(BASE_IMAGE+newsResultList.get(position).getNewsImage()).into(holder.image);


        Log.e("hdfkjsf",newsResultList.get(position).getHeadingEn());

        holder.heading.setText(newsResultList.get(position).getHeadingEn());
        holder.subtext.setText(newsResultList.get(position).getContentEn());
        holder.created_time.setText(newsResultList.get(position).getCreatedAt());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("heading",newsResultList.get(position).getHeadingEn());
                intent.putExtra("contant",newsResultList.get(position).getContentEn());
                intent.putExtra("created_time",newsResultList.get(position).getCreatedAt());
                intent.putExtra("image",newsResultList.get(position).getNewsImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsResultList.size();
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView heading, subtext, created_time;
    RelativeLayout layout;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image);
        heading = itemView.findViewById(R.id.heading);
        subtext = itemView.findViewById(R.id.subtext);
        created_time = itemView.findViewById(R.id.created_time);
        layout = itemView.findViewById(R.id.news_layout);
    }
}