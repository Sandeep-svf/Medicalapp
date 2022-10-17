package com.example.findadoc.Model.dev.sam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsRecord {

@SerializedName("heading_en")
@Expose
public String headingEn;
@SerializedName("content_en")
@Expose
public String contentEn;
@SerializedName("news_image")
@Expose
public String newsImage;
@SerializedName("created_at")
@Expose
public String createdAt;


    public String getHeadingEn() {
        return headingEn;
    }

    public void setHeadingEn(String headingEn) {
        this.headingEn = headingEn;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}