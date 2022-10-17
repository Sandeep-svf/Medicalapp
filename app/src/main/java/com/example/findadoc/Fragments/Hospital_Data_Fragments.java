package com.example.findadoc.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findadoc.R;


public class Hospital_Data_Fragments extends Fragment {


        RecyclerView hospital_recycler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_hospital__data__fragments, container, false);
        hospital_recycler=v.findViewById(R.id.hospital_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        hospital_recycler.setLayoutManager(linearLayoutManager);
       /* Hospital_Adapter hospital_adapter=new Hospital_Adapter(getActivity(),);
        hospital_recycler.setAdapter(hospital_adapter);*/
        return v;
    }
}