package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.findadoc.Adapter.Doctors_Data_Adapter;
import com.example.findadoc.R;

public class Doctore_Data_Activities extends AppCompatActivity {
    RelativeLayout relatice_back_change_password;
    RecyclerView doctor_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctore__data__activities);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        doctor_recycler=findViewById(R.id.doctor_recycler);

        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManage=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        doctor_recycler.setLayoutManager(linearLayoutManage);
        Doctors_Data_Adapter doctors_data_adapter=new Doctors_Data_Adapter(Doctore_Data_Activities.this);
        doctor_recycler.setAdapter(doctors_data_adapter);

    }
}