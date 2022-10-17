package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.R;

import java.util.List;

public class Associated_Adapter extends RecyclerView.Adapter<Associated_Adapter.My_View_Holder> {
    Context context;
    List<String> list;
    public Associated_Adapter(Context context, List<String> associted_disease_list) {
        this.context = context;
        this.list=associted_disease_list;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.associated_diseases, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        holder.text.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView text;

        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);

        }
    }
}
