package com.example.findadoc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findadoc.Model.DoctorSpecialty;
import com.example.findadoc.R;

import java.util.List;

public class Doctor_Specialist_Adapter extends BaseAdapter {
    Context context;
    List<DoctorSpecialty> doctorSpecialties;
    LayoutInflater inflter;
    public Doctor_Specialist_Adapter(Context context, List<DoctorSpecialty> doctorSpecialties) {
        this.context = context;
        this.doctorSpecialties = doctorSpecialties;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return doctorSpecialties.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorSpecialties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custom_spinner_three, null);

        TextView names = (TextView) convertView.findViewById(R.id.textView);
        names.setText(doctorSpecialties.get(position).getDoctorSpecialtyName());
        return convertView;
    }
}
