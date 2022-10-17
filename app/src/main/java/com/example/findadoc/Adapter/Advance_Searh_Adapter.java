package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.Activity.Indications_and_Dosage_Sub_Activity;
import com.example.findadoc.Model.Advance_Search_Response;
import com.example.findadoc.R;

import java.util.List;

public class Advance_Searh_Adapter extends RecyclerView.Adapter<Advance_Searh_Adapter.My_View_holder> {
   List<Advance_Search_Response> advance_search_responses;
   Context context;
    private PopupWindow popupWindow;

    public Advance_Searh_Adapter(List<Advance_Search_Response> advance_search_responses, Context context) {
        this.advance_search_responses = advance_search_responses;
        this.context = context;
    }

    @NonNull
    @Override
    public My_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.advance_search_adapter, parent, false);
        My_View_holder myViewHolder = new My_View_holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_holder holder, @SuppressLint("RecyclerView") int position) {
    holder.price.setText("Price: $"+ advance_search_responses.get(position).getDrugPrice());
    holder.cat_name.setText(advance_search_responses.get(position).getCategoryMedicamentEn());
    holder.med_name.setText(advance_search_responses.get(position).getDrugNameEn());
        holder.presentaion_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Pop_Up(advance_search_responses.get(position).drug_presentaion(),context.getResources().getString(R.string.presentation));
            }
        });

        holder.dosage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pop_Up(advance_search_responses.get(position).getDrugDosesEn(),context.getResources().getString(R.string.dosage));

            }
        });

    }
    private void Pop_Up(String presentaion,String head) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.medication_data_popup,null);
        TextView data_text=customView.findViewById(R.id.data_text);
        TextView heading=customView.findViewById(R.id.heading);
        ImageView imageView=customView.findViewById(R.id.pop_up_canlce);
        data_text.setText(presentaion);
        heading.setText(head);
        popupWindow=new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, 700,true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
//               popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(customView, Gravity.BOTTOM, 40, 60);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return advance_search_responses.size();
    }

    public class My_View_holder extends RecyclerView.ViewHolder {
        TextView med_name,cat_name,price;
        RelativeLayout dosage_layout,presentaion_layout;
        public My_View_holder(@NonNull View itemView) {
            super(itemView);
            med_name=itemView.findViewById(R.id.med_name);
            cat_name=itemView.findViewById(R.id.cat_name);
            price=itemView.findViewById(R.id.price);
            dosage_layout=itemView.findViewById(R.id.dosage_layout);
            presentaion_layout=itemView.findViewById(R.id.presentaion_layout);
        }
    }
}
