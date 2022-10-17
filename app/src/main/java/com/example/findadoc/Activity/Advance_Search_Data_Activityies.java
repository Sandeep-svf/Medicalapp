package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.findadoc.Adapter.Advance_Searh_Adapter;
import com.example.findadoc.Model.Advance_Search_Response;
import com.example.findadoc.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Advance_Search_Data_Activityies extends AppCompatActivity {
    RecyclerView medicne_recycler;
    RelativeLayout relatice_back_change_password;
    String best_price;
    LinearLayout layout_hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance__search__data__activityies);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        medicne_recycler=findViewById(R.id.medicne_recycler);
        layout_hospital=findViewById(R.id.layout_hospital);
        best_price=getIntent().getStringExtra("best_price");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        medicne_recycler.setLayoutManager(linearLayoutManager);
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        Toast.makeText(this, best_price+"", Toast.LENGTH_SHORT).show();
        ArrayList<Advance_Search_Response> myList = (ArrayList<Advance_Search_Response>) getIntent().getSerializableExtra("my_list");
       if(myList.size()==0)
       {
           layout_hospital.setVisibility(View.VISIBLE);
           medicne_recycler.setVisibility(View.GONE);
       }
       else
       {
           layout_hospital.setVisibility(View.GONE);
           medicne_recycler.setVisibility(View.VISIBLE);
       }
        if (best_price.equals("1"))
        {
            Collections.sort(myList, new Comparator<Advance_Search_Response>() {
                @Override
                public int compare(Advance_Search_Response o1, Advance_Search_Response o2) {
                    return Integer.valueOf(o1.getDrugPrice()).compareTo(o2.getDrugPrice());
                }
            });
            Advance_Searh_Adapter advance_searh_adapter=new Advance_Searh_Adapter(myList,Advance_Search_Data_Activityies.this);
            medicne_recycler.setAdapter(advance_searh_adapter);
        }
        else
        {
            Advance_Searh_Adapter advance_searh_adapter=new Advance_Searh_Adapter(myList,Advance_Search_Data_Activityies.this);
            medicne_recycler.setAdapter(advance_searh_adapter);
        }



    }
}