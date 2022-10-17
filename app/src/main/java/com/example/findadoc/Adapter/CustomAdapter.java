package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findadoc.Model.City;
import com.example.findadoc.R;


import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<City> cityList;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<City> cityNames) {
        this.context = applicationContext;

        this.cityList = cityNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int i) {
        return cityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_two, null);

        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(cityList.get(i).getMunicipalName());
        return view;
    }
}