package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor_Response {
    @SerializedName("dr_id")
    @Expose
    private Integer drId;
    @SerializedName("dr_experience")
    @Expose
    private String drExperience;
    @SerializedName("dr_recommend")
    @Expose
    private Integer drRecommend;
    @SerializedName("dr_image")
    @Expose
    private String drImage;
    @SerializedName("dr_muncipal")
    @Expose
    private String drMuncipal;
    @SerializedName("dr_other_phone")
    @Expose
    private String drOtherPhone;
    @SerializedName("dr_city_name")
    @Expose
    private String drCityName;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("dr_address")
    @Expose
    private String drAddress;
    @SerializedName("dr_timing")
    @Expose
    private String drTiming;
    @SerializedName("dr_email")
    @Expose
    private String drEmail;
    @SerializedName("dr_phone")
    @Expose
    private String drPhone;
    @SerializedName("dr_latitude")
    @Expose
    private String drLatitude;
    @SerializedName("dr_longitude")
    @Expose
    private String drLongitude;
    @SerializedName("dr_keyword_en")
    @Expose
    private String drKeywordEn;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("doctor_city_name")
    @Expose
    private String doctorCityName;
    @SerializedName("days")
    @Expose
    private String days;

    public Integer getDrId() {
        return drId;
    }

    public void setDrId(Integer drId) {
        this.drId = drId;
    }

    public String getDrExperience() {
        return drExperience;
    }

    public void setDrExperience(String drExperience) {
        this.drExperience = drExperience;
    }

    public Integer getDrRecommend() {
        return drRecommend;
    }

    public void setDrRecommend(Integer drRecommend) {
        this.drRecommend = drRecommend;
    }

    public String getDrImage() {
        return drImage;
    }

    public void setDrImage(String drImage) {
        this.drImage = drImage;
    }

    public String getDrMuncipal() {
        return drMuncipal;
    }

    public void setDrMuncipal(String drMuncipal) {
        this.drMuncipal = drMuncipal;
    }

    public String getDrOtherPhone() {
        return drOtherPhone;
    }

    public void setDrOtherPhone(String drOtherPhone) {
        this.drOtherPhone = drOtherPhone;
    }

    public String getDrCityName() {
        return drCityName;
    }

    public void setDrCityName(String drCityName) {
        this.drCityName = drCityName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrAddress() {
        return drAddress;
    }

    public void setDrAddress(String drAddress) {
        this.drAddress = drAddress;
    }

    public String getDrTiming() {
        return drTiming;
    }

    public void setDrTiming(String drTiming) {
        this.drTiming = drTiming;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getDrPhone() {
        return drPhone;
    }

    public void setDrPhone(String drPhone) {
        this.drPhone = drPhone;
    }

    public String getDrLatitude() {
        return drLatitude;
    }

    public void setDrLatitude(String drLatitude) {
        this.drLatitude = drLatitude;
    }

    public String getDrLongitude() {
        return drLongitude;
    }

    public void setDrLongitude(String drLongitude) {
        this.drLongitude = drLongitude;
    }

    public String getDrKeywordEn() {
        return drKeywordEn;
    }

    public void setDrKeywordEn(String drKeywordEn) {
        this.drKeywordEn = drKeywordEn;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public String getDoctorCityName() {
        return doctorCityName;
    }

    public void setDoctorCityName(String doctorCityName) {
        this.doctorCityName = doctorCityName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

}
