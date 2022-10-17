package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Model.UsefulNumber_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import java.util.List;

public class Useful_No_Adapter extends RecyclerView.Adapter<Useful_No_Adapter.My_View_Holder> {
    Context context;
    List<UsefulNumber_Response> usefulNumber_responses;

    public Useful_No_Adapter(Context context, List<UsefulNumber_Response> usefulNumber_responses) {
        this.context = context;
        this.usefulNumber_responses = usefulNumber_responses;
    }


    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usefulno_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.call_do_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+usefulNumber_responses.get(position).getNumber()));//change the number
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
        holder.heading.setText(usefulNumber_responses.get(position).getName());
        holder.number.setText(usefulNumber_responses.get(position).getNumber());
        Glide.with(context).load(Api_client.BASE_IMAGE+usefulNumber_responses.get(position).getImage()).into(holder.f_fighter_image);
     //   Log.e("IMAGE",usefulNumber_responses.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return usefulNumber_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        ImageView call_do_icon,f_fighter_image;
        TextView heading,number;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
             call_do_icon=itemView.findViewById(R.id.call_do_icon);
            number=itemView.findViewById(R.id.number);
            heading=itemView.findViewById(R.id.heading);
            f_fighter_image=itemView.findViewById(R.id.f_fighter_image);
        }
    }
}
