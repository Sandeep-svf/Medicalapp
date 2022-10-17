package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Week_Response {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Week")
    @Expose
    private String week;
  //  @SerializedName("Recommended Weight Gain")
    @SerializedName("Recommended")
    @Expose
    private String recommendedWeightGain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRecommendedWeightGain() {
        return recommendedWeightGain;
    }

    public void setRecommendedWeightGain(String recommendedWeightGain) {
        this.recommendedWeightGain = recommendedWeightGain;
    }
}
