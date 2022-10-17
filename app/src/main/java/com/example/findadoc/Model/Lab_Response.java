package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lab_Response {
    @SerializedName("laboratory_id")
    @Expose
    public Integer laboratoryId;
    @SerializedName("laboratory_recommend")
    @Expose
    public Integer laboratoryRecommend;
    @SerializedName("laboratory_image")
    @Expose
    public String laboratoryImage;
    @SerializedName("laboratory_other_phone")
    @Expose
    public String laboratoryOtherPhone;
    @SerializedName("laboratory_muncipal")
    @Expose
    public Integer laboratoryMuncipal;
    @SerializedName("laboratory_time")
    @Expose
    public String laboratoryTime;
    @SerializedName("laboratory_city_name")
    @Expose
    public String laboratoryCityName;
    @SerializedName("laboratory_name")
    @Expose
    public String laboratoryName;
    @SerializedName("laboratory_email")
    @Expose
    public String laboratoryEmail;
    @SerializedName("laboratory_address")
    @Expose
    public String laboratoryAddress;
    @SerializedName("laboratory_latitude")
    @Expose
    public String laboratoryLatitude;
    @SerializedName("laboratory_longitude")
    @Expose
    public String laboratoryLongitude;
    @SerializedName("laboratory_phone")
    @Expose
    public String laboratoryPhone;
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

    public Integer getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Integer laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public Integer getLaboratoryRecommend() {
        return laboratoryRecommend;
    }

    public void setLaboratoryRecommend(Integer laboratoryRecommend) {
        this.laboratoryRecommend = laboratoryRecommend;
    }

    public String getLaboratoryImage() {
        return laboratoryImage;
    }

    public void setLaboratoryImage(String laboratoryImage) {
        this.laboratoryImage = laboratoryImage;
    }

    public String getLaboratoryOtherPhone() {
        return laboratoryOtherPhone;
    }

    public void setLaboratoryOtherPhone(String laboratoryOtherPhone) {
        this.laboratoryOtherPhone = laboratoryOtherPhone;
    }

    public Integer getLaboratoryMuncipal() {
        return laboratoryMuncipal;
    }

    public void setLaboratoryMuncipal(Integer laboratoryMuncipal) {
        this.laboratoryMuncipal = laboratoryMuncipal;
    }

    public String getLaboratoryTime() {
        return laboratoryTime;
    }

    public void setLaboratoryTime(String laboratoryTime) {
        this.laboratoryTime = laboratoryTime;
    }

    public String getLaboratoryCityName() {
        return laboratoryCityName;
    }

    public void setLaboratoryCityName(String laboratoryCityName) {
        this.laboratoryCityName = laboratoryCityName;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getLaboratoryEmail() {
        return laboratoryEmail;
    }

    public void setLaboratoryEmail(String laboratoryEmail) {
        this.laboratoryEmail = laboratoryEmail;
    }

    public String getLaboratoryAddress() {
        return laboratoryAddress;
    }

    public void setLaboratoryAddress(String laboratoryAddress) {
        this.laboratoryAddress = laboratoryAddress;
    }

    public String getLaboratoryLatitude() {
        return laboratoryLatitude;
    }

    public void setLaboratoryLatitude(String laboratoryLatitude) {
        this.laboratoryLatitude = laboratoryLatitude;
    }

    public String getLaboratoryLongitude() {
        return laboratoryLongitude;
    }

    public void setLaboratoryLongitude(String laboratoryLongitude) {
        this.laboratoryLongitude = laboratoryLongitude;
    }

    public String getLaboratoryPhone() {
        return laboratoryPhone;
    }

    public void setLaboratoryPhone(String laboratoryPhone) {
        this.laboratoryPhone = laboratoryPhone;
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
