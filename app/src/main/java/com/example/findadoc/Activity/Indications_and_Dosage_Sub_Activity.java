package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.findadoc.R;

public class Indications_and_Dosage_Sub_Activity extends AppCompatActivity {
    RelativeLayout relatice_back_change_password;
    String presentaion,precautions,pregnancy,adverse_effect,drungd_name;
    TextView medicine_name;
    RelativeLayout relative_presentation,precaution_relative,pregnancy_relat8ive,effect_relative;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indications_and__dosage__sub_);
        medicine_name=findViewById(R.id.medicine_name);
        relative_presentation=findViewById(R.id.relative_presentation);
        precaution_relative=findViewById(R.id.precaution_relative);
        pregnancy_relat8ive=findViewById(R.id.pregnancy_relat8ive);
        effect_relative=findViewById(R.id.effect_relative);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        drungd_name=getIntent().getStringExtra("drug_name_en");
        precautions=getIntent().getStringExtra("precaution_medicament");
        pregnancy=getIntent().getStringExtra("pregnancy");
        presentaion=getIntent().getStringExtra("presentation_medicament_en");
        adverse_effect=getIntent().getStringExtra("effect_medicament");
        medicine_name.setText(drungd_name);
        relative_presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headin="Presentation";
                Pop_Up(presentaion,headin);
            }
        });
        effect_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headin="Adverse Effect";
                Pop_Up(adverse_effect,headin);
            }
        });
        pregnancy_relat8ive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headin="Pregnancy";
                Pop_Up(pregnancy,headin);
            }
        });
        precaution_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headin="Precautions";
                Pop_Up(precautions,headin);
            }
        });
    }

    private void Pop_Up(String presentaion,String head) {
        LayoutInflater layoutInflater = (LayoutInflater)Indications_and_Dosage_Sub_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.medication_data_popup,null);
        TextView data_text=customView.findViewById(R.id.data_text);
        TextView heading=customView.findViewById(R.id.heading);
        ImageView imageView=customView.findViewById(R.id.pop_up_canlce);
        data_text.setText(presentaion);
        heading.setText(head);
        popupWindow=new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
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
}