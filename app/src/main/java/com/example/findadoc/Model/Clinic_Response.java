package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic_Response {
    @SerializedName("clinic_id")
    @Expose
    private Integer clinicId;
    @SerializedName("clinic_recommend")
    @Expose
    private Integer clinicRecommend;
    @SerializedName("clinic_image")
    @Expose
    private String clinicImage;
    @SerializedName("clinic_muncipal")
    @Expose
    private Integer clinicMuncipal;
    @SerializedName("clinic_city_name")
    @Expose
    private String clinicCityName;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_address")
    @Expose
    private String clinicAddress;
    @SerializedName("clinic_email")
    @Expose
    private String clinicEmail;
    @SerializedName("clinic_latitude")
    @Expose
    private String clinicLatitude;
    @SerializedName("clinic_timing")
    @Expose
    private String clinicTiming;
    @SerializedName("clinic_longitude")
    @Expose
    private String clinicLongitude;
    @SerializedName("clinic_phone")
    @Expose
    private Long clinicPhone;
    @SerializedName("sun")
    @Expose
    private Integer sun;
    @SerializedName("mon")
    @Expose
    private Integer mon;
    @SerializedName("tue")
    @Expose
    private Integer tue;
    @SerializedName("wed")
    @Expose
    private Integer wed;
    @SerializedName("thu")
    @Expose
    private Integer thu;
    @SerializedName("fri")
    @Expose
    private Integer fri;
    @SerializedName("sat")
    @Expose
    private Integer sat;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getClinicRecommend() {
        return clinicRecommend;
    }

    public void setClinicRecommend(Integer clinicRecommend) {
        this.clinicRecommend = clinicRecommend;
    }

    public String getClinicImage() {
        return clinicImage;
    }

    public void setClinicImage(String clinicImage) {
        this.clinicImage = clinicImage;
    }

    public Integer getClinicMuncipal() {
        return clinicMuncipal;
    }

    public void setClinicMuncipal(Integer clinicMuncipal) {
        this.clinicMuncipal = clinicMuncipal;
    }

    public String getClinicCityName() {
        return clinicCityName;
    }

    public void setClinicCityName(String clinicCityName) {
        this.clinicCityName = clinicCityName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicEmail() {
        return clinicEmail;
    }

    public void setClinicEmail(String clinicEmail) {
        this.clinicEmail = clinicEmail;
    }

    public String getClinicLatitude() {
        return clinicLatitude;
    }

    public void setClinicLatitude(String clinicLatitude) {
        this.clinicLatitude = clinicLatitude;
    }

    public String getClinicTiming() {
        return clinicTiming;
    }

    public void setClinicTiming(String clinicTiming) {
        this.clinicTiming = clinicTiming;
    }

    public String getClinicLongitude() {
        return clinicLongitude;
    }

    public void setClinicLongitude(String clinicLongitude) {
        this.clinicLongitude = clinicLongitude;
    }

    public Long getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(Long clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public Integer getSun() {
        return sun;
    }

    public void setSun(Integer sun) {
        this.sun = sun;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Integer getTue() {
        return tue;
    }

    public void setTue(Integer tue) {
        this.tue = tue;
    }

    public Integer getWed() {
        return wed;
    }

    public void setWed(Integer wed) {
        this.wed = wed;
    }

    public Integer getThu() {
        return thu;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public Integer getFri() {
        return fri;
    }

    public void setFri(Integer fri) {
        this.fri = fri;
    }

    public Integer getSat() {
        return sat;
    }

    public void setSat(Integer sat) {
        this.sat = sat;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }
}
