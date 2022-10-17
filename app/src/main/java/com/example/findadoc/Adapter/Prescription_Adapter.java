package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.R;

public class Prescription_Adapter extends RecyclerView.Adapter<Prescription_Adapter.My_View_Holder> {
   Context context;

    public Prescription_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prescription, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
