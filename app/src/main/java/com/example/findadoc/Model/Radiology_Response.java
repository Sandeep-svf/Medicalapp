package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Radiology_Response {

    @SerializedName("radiology_center_id")
    @Expose
    public Integer radiologyCenterId;
    @SerializedName("rad_center_image")
    @Expose
    public String radCenterImage;
    @SerializedName("rad_center_recommend")
    @Expose
    public Integer radCenterRecommend;
    @SerializedName("rad_center_timing")
    @Expose
    public String radCenterTiming;
    @SerializedName("rad_center_muncipal")
    @Expose
    public Integer radCenterMuncipal;
    @SerializedName("rad_center_other_phone")
    @Expose
    public String radCenterOtherPhone;
    @SerializedName("rad_center_city")
    @Expose
    public String radCenterCity;
    @SerializedName("rad_center_name")
    @Expose
    public String radCenterName;
    @SerializedName("rad_center_address")
    @Expose
    public String radCenterAddress;
    @SerializedName("rad_center_email")
    @Expose
    public String radCenterEmail;
    @SerializedName("rad_center_latitude")
    @Expose
    public String radCenterLatitude;
    @SerializedName("rad_center_longitude")
    @Expose
    public String radCenterLongitude;
    @SerializedName("rad_center_phone")
    @Expose
    public String radCenterPhone;
    @SerializedName("sun")
    @Expose
    public Object sun;
    @SerializedName("mon")
    @Expose
    public Object mon;
    @SerializedName("tue")
    @Expose
    public Object tue;
    @SerializedName("wed")
    @Expose
    public Object wed;
    @SerializedName("thu")
    @Expose
    public Object thu;
    @SerializedName("fri")
    @Expose
    public Object fri;
    @SerializedName("sat")
    @Expose
    public Object sat;
    @SerializedName("favourite")
    @Expose
    public Integer favourite;


    public Integer getRadiologyCenterId() {
        return radiologyCenterId;
    }

    public void setRadiologyCenterId(Integer radiologyCenterId) {
        this.radiologyCenterId = radiologyCenterId;
    }

    public String getRadCenterImage() {
        return radCenterImage;
    }

    public void setRadCenterImage(String radCenterImage) {
        this.radCenterImage = radCenterImage;
    }

    public Integer getRadCenterRecommend() {
        return radCenterRecommend;
    }

    public void setRadCenterRecommend(Integer radCenterRecommend) {
        this.radCenterRecommend = radCenterRecommend;
    }

    public String getRadCenterTiming() {
        return radCenterTiming;
    }

    public void setRadCenterTiming(String radCenterTiming) {
        this.radCenterTiming = radCenterTiming;
    }

    public Integer getRadCenterMuncipal() {
        return radCenterMuncipal;
    }

    public void setRadCenterMuncipal(Integer radCenterMuncipal) {
        this.radCenterMuncipal = radCenterMuncipal;
    }

    public String getRadCenterOtherPhone() {
        return radCenterOtherPhone;
    }

    public void setRadCenterOtherPhone(String radCenterOtherPhone) {
        this.radCenterOtherPhone = radCenterOtherPhone;
    }

    public String getRadCenterCity() {
        return radCenterCity;
    }

    public void setRadCenterCity(String radCenterCity) {
        this.radCenterCity = radCenterCity;
    }

    public String getRadCenterName() {
        return radCenterName;
    }

    public void setRadCenterName(String radCenterName) {
        this.radCenterName = radCenterName;
    }

    public String getRadCenterAddress() {
        return radCenterAddress;
    }

    public void setRadCenterAddress(String radCenterAddress) {
        this.radCenterAddress = radCenterAddress;
    }

    public String getRadCenterEmail() {
        return radCenterEmail;
    }

    public void setRadCenterEmail(String radCenterEmail) {
        this.radCenterEmail = radCenterEmail;
    }

    public String getRadCenterLatitude() {
        return radCenterLatitude;
    }

    public void setRadCenterLatitude(String radCenterLatitude) {
        this.radCenterLatitude = radCenterLatitude;
    }

    public String getRadCenterLongitude() {
        return radCenterLongitude;
    }

    public void setRadCenterLongitude(String radCenterLongitude) {
        this.radCenterLongitude = radCenterLongitude;
    }

    public String getRadCenterPhone() {
        return radCenterPhone;
    }

    public void setRadCenterPhone(String radCenterPhone) {
        this.radCenterPhone = radCenterPhone;
    }

    public Object getSun() {
        return sun;
    }

    public void setSun(Object sun) {
        this.sun = sun;
    }

    public Object getMon() {
        return mon;
    }

    public void setMon(Object mon) {
        this.mon = mon;
    }

    public Object getTue() {
        return tue;
    }

    public void setTue(Object tue) {
        this.tue = tue;
    }

    public Object getWed() {
        return wed;
    }

    public void setWed(Object wed) {
        this.wed = wed;
    }

    public Object getThu() {
        return thu;
    }

    public void setThu(Object thu) {
        this.thu = thu;
    }

    public Object getFri() {
        return fri;
    }

    public void setFri(Object fri) {
        this.fri = fri;
    }

    public Object getSat() {
        return sat;
    }

    public void setSat(Object sat) {
        this.sat = sat;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }
}
