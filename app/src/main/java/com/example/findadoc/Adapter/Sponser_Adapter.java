package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Model.Sponsor_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.w3c.dom.Text;

import java.util.List;

public class Sponser_Adapter extends RecyclerView.Adapter<Sponser_Adapter.My_View_Holder> {
   Context context;
   List<Sponsor_Response> sponsor_responses;

    public Sponser_Adapter(Context context, List<Sponsor_Response> sponsor_responses) {
        this.context = context;
        this.sponsor_responses = sponsor_responses;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sponsers_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        Glide.with(context).load(Api_client.BASE_IMAGE+sponsor_responses.get(position).getSponsorLogo()).into(holder.image_one);
        holder.text_data.setText(sponsor_responses.get(position).getSponsorName());
    }

    @Override
    public int getItemCount() {
        return sponsor_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        ImageView image_one;
        TextView text_data;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            text_data=itemView.findViewById(R.id.text_data);
            image_one=itemView.findViewById(R.id.image_one);
        }
    }
}
