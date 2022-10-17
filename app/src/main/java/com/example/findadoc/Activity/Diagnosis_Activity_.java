package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Adapter.Associated_Adapter;
import com.example.findadoc.R;

import java.util.Arrays;
import java.util.List;

public class Diagnosis_Activity_ extends AppCompatActivity {
    RecyclerView associate_recycler;
    RelativeLayout relatice_back_change_password;
    TextView description,heading;
    List<String> associted_disease_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis__);
        heading=findViewById(R.id.heading);
        associate_recycler=findViewById(R.id.associate_recycler);
        description=findViewById(R.id.description);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        try {
            LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
            associate_recycler.setLayoutManager(manager);
            associate_recycler.setNestedScrollingEnabled(false);
            String symtoms_id=getIntent().getStringExtra("symtoms_id");
            String symtomps=getIntent().getStringExtra("symtomps");
            String get_description= getIntent().getStringExtra("discription");
            String related_disease=getIntent().getStringExtra("related_disease");
            associted_disease_list = Arrays.asList(related_disease.split(","));
            Associated_Adapter associated_adapter=new Associated_Adapter(getApplicationContext(),associted_disease_list);
            associate_recycler.setAdapter(associated_adapter);
//        Toast.makeText(this, symtoms_id+"", Toast.LENGTH_SHORT).show();
            description.setText(get_description);
            heading.setText(symtomps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}