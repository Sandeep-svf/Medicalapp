package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findadoc.Model.Categories_Response;
import com.example.findadoc.R;

import java.util.List;

public class Categories_Space_Adapter extends BaseAdapter {
    Context context;
    List<Categories_Response> responses;
    LayoutInflater inflter;

    public Categories_Space_Adapter(Context applicationContext, List<Categories_Response> cityNames) {
        this.context = applicationContext;

        this.responses = cityNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return responses.size();
    }

    @Override
    public Object getItem(int i) {
        return responses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_three, null);

        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(responses.get(i).getCategoryName());
        return view;
    }
}