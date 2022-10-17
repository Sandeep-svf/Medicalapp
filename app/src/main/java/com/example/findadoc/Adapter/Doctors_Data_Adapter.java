package com.example.findadoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.Activity.Doctor_Profile;
import com.example.findadoc.R;

public class Doctors_Data_Adapter extends RecyclerView.Adapter<Doctors_Data_Adapter.My_View_Holder> {
    Context context;

    public Doctors_Data_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        holder.layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Doctor_Profile.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        RelativeLayout layout_one;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            layout_one=itemView.findViewById(R.id.layout_one);
        }
    }
}
