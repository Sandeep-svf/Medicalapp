package com.example.findadoc.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.findadoc.Model.Days_Model;
import com.example.findadoc.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Days_Adapter extends RecyclerView.Adapter<Days_Adapter.My_View_Holder> {
    Context context;
    ArrayList<Days_Model> days_model;
    private OnClickListener onClickListener;
    int i;
    public Days_Adapter(Context context, ArrayList<Days_Model> days_model) {
        this.context = context;
        this.days_model = days_model;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void click(String id, String title);
    }
    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.days_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        holder.text.setText(days_model.get(position).getDays());
//        holder.text.setTag(days_model.get(position).getId());
        holder.text.setChecked(days_model.get(position).getId().equals("1"));

        holder.text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                days_model.get(position).setId(days_model.get(position).getId().equals("0") ? "1" : "0");
                if (isChecked) {
                    onClickListener.click(days_model.get(position).getId(), buttonView.getText().toString());
                }else {
                    onClickListener.click(days_model.get(position).getId(), buttonView.getText().toString());

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return days_model.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        CheckBox text;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
        }
    }
}
