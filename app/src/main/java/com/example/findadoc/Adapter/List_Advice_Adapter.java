package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.Model.Advice_Responmse;
import com.example.findadoc.R;

import java.util.List;

public class List_Advice_Adapter extends RecyclerView.Adapter<List_Advice_Adapter.MyView_Holder> {
    Context context;
    private PopupWindow popupWindow;
    List<Advice_Responmse> advice_responmses;

    public List_Advice_Adapter(Context context, List<Advice_Responmse> advice_responmses) {
        this.context = context;
        this.advice_responmses = advice_responmses;
    }

    @NonNull
    @Override
    public MyView_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_advice_adapter, parent, false);
        MyView_Holder myViewHolder = new MyView_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView_Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.layout_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Pop_Up();
            }
        });
        holder.advice_text.setText(advice_responmses.get(position).getDescriptionBoardEn());
        try {
            Log.e("print_text" , advice_responmses.get(position).getDescriptionBoardEn() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.share_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, advice_responmses.get(position).getDescriptionBoardEn());
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

    }


    private void Pop_Up() {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.pop_up,null);
        popupWindow=new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
//               popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(customView, Gravity.BOTTOM, 40, 60);
    }

    @Override
    public int getItemCount() {
        return advice_responmses.size();
    }

    public class MyView_Holder extends RecyclerView.ViewHolder {
        CardView layout_list;
        ImageView share_image;
        TextView advice_text;

        public MyView_Holder(@NonNull View itemView) {
            super(itemView);
            layout_list=itemView.findViewById(R.id.layout_list);
            share_image=itemView.findViewById(R.id.share_image);
            advice_text=itemView.findViewById(R.id.advice_text);
        }
    }
}
