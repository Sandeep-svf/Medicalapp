package com.example.findadoc.sam.dev.Activity;

import static com.example.findadoc.Retrofir.Api_client.BASE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.findadoc.R;

public class NewsDetailsActivity extends AppCompatActivity {

    AppCompatTextView news_contant,news_created_time, news_headline;
    AppCompatImageView news_image, bcak_image;
    private String newsHeadline, newsContant, newsCreatedTime, newImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        intis();

        Intent intent = getIntent();

        // Get intent data
        newsHeadline = intent.getStringExtra("heading");
        newsContant = intent.getStringExtra("contant");
        newsCreatedTime = intent.getStringExtra("created_time");
        newImage =  intent.getStringExtra("image");


        // SET data
        news_headline.setText(newsHeadline);
        news_contant.setText(newsContant);
        news_created_time.setText(newsCreatedTime);
        Glide.with(NewsDetailsActivity.this).load(BASE_IMAGE+newImage).into(news_image);




        bcak_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void intis() {
        news_contant = findViewById(R.id.news_contant);
        news_created_time = findViewById(R.id.news_created_time);
        news_headline = findViewById(R.id.news_headline);
        news_image = findViewById(R.id.news_image);
        bcak_image = findViewById(R.id.bcak_image);

    }
}