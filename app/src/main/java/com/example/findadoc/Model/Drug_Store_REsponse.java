package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug_Store_REsponse {
    @SerializedName("drugstore_id")
    @Expose
    private Integer drugstoreId;
    @SerializedName("drugstore_image")
    @Expose
    private String drugstoreImage;
    @SerializedName("drugstore_muncipal")
    @Expose
    private String drugstoreMuncipal;
    @SerializedName("drugstore_recommend")
    @Expose
    private Integer drugstoreRecommend;
    @SerializedName("drugstore_name")
    @Expose
    private String drugstoreName;
    @SerializedName("drugstore_add")
    @Expose
    private String drugstoreAdd;
    @SerializedName("drugstore_email")
    @Expose
    private String drugstoreEmail;
    @SerializedName("drugstore_latitude")
    @Expose
    private String drugstoreLatitude;
    @SerializedName("drugstore_longitude")
    @Expose
    private String drugstoreLongitude;
    @SerializedName("drugstore_timing")
    @Expose
    private String drugstoreTiming;
    @SerializedName("drugstore_phone")
    @Expose
    private String drugstorePhone;
    @SerializedName("drugstore_other_phone")
    @Expose
    private String drugstoreOtherPhone;
    @SerializedName("drugstore_city_name")
    @Expose
    private String drugstoreCityName;

    @SerializedName("favourite")
    @Expose
    private String favourite;

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public Integer getDrugstoreId() {
        return drugstoreId;
    }

    public void setDrugstoreId(Integer drugstoreId) {
        this.drugstoreId = drugstoreId;
    }

    public String getDrugstoreImage() {
        return drugstoreImage;
    }

    public void setDrugstoreImage(String drugstoreImage) {
        this.drugstoreImage = drugstoreImage;
    }

    public String getDrugstoreMuncipal() {
        return drugstoreMuncipal;
    }

    public void setDrugstoreMuncipal(String drugstoreMuncipal) {
        this.drugstoreMuncipal = drugstoreMuncipal;
    }

    public Integer getDrugstoreRecommend() {
        return drugstoreRecommend;
    }

    public void setDrugstoreRecommend(Integer drugstoreRecommend) {
        this.drugstoreRecommend = drugstoreRecommend;
    }

    public String getDrugstoreName() {
        return drugstoreName;
    }

    public void setDrugstoreName(String drugstoreName) {
        this.drugstoreName = drugstoreName;
    }

    public String getDrugstoreAdd() {
        return drugstoreAdd;
    }

    public void setDrugstoreAdd(String drugstoreAdd) {
        this.drugstoreAdd = drugstoreAdd;
    }

    public String getDrugstoreEmail() {
        return drugstoreEmail;
    }

    public void setDrugstoreEmail(String drugstoreEmail) {
        this.drugstoreEmail = drugstoreEmail;
    }

    public String getDrugstoreLatitude() {
        return drugstoreLatitude;
    }

    public void setDrugstoreLatitude(String drugstoreLatitude) {
        this.drugstoreLatitude = drugstoreLatitude;
    }

    public String getDrugstoreLongitude() {
        return drugstoreLongitude;
    }

    public void setDrugstoreLongitude(String drugstoreLongitude) {
        this.drugstoreLongitude = drugstoreLongitude;
    }

    public String getDrugstoreTiming() {
        return drugstoreTiming;
    }

    public void setDrugstoreTiming(String drugstoreTiming) {
        this.drugstoreTiming = drugstoreTiming;
    }

    public String getDrugstorePhone() {
        return drugstorePhone;
    }

    public void setDrugstorePhone(String drugstorePhone) {
        this.drugstorePhone = drugstorePhone;
    }

    public String getDrugstoreOtherPhone() {
        return drugstoreOtherPhone;
    }

    public void setDrugstoreOtherPhone(String drugstoreOtherPhone) {
        this.drugstoreOtherPhone = drugstoreOtherPhone;
    }

    public String getDrugstoreCityName() {
        return drugstoreCityName;
    }

    public void setDrugstoreCityName(String drugstoreCityName) {
        this.drugstoreCityName = drugstoreCityName;
    }

}
