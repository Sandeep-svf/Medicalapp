package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findadoc.Model.ClinicSpecialty;
import com.example.findadoc.R;

import java.util.List;

public class Specilist_Adapter extends BaseAdapter {
    Context context;
    List<ClinicSpecialty> clinicSpecialties;
    LayoutInflater inflter;

    public Specilist_Adapter(Context context, List<ClinicSpecialty> clinicSpecialties) {
        this.context = context;
        this.clinicSpecialties = clinicSpecialties;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return clinicSpecialties.size();
    }

    @Override
    public Object getItem(int position) {
       return clinicSpecialties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custom_spinner_three, null);

        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(clinicSpecialties.get(position).getClinicSpecialtyName());
        return convertView;
    }
}
