package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital_Resposne {
    @SerializedName("hospital_id")
    @Expose
    public Integer hospitalId;
    @SerializedName("hospital_city_name")
    @Expose
    public String hospitalCityName;
    @SerializedName("hospital_image")
    @Expose
    public String hospitalImage;
    @SerializedName("hospital_muncipal_id")
    @Expose
    public Integer hospitalMuncipalId;
    @SerializedName("hospital_recommend")
    @Expose
    public Integer hospitalRecommend;
    @SerializedName("hospital_other_phone")
    @Expose
    public String hospitalOtherPhone;
    @SerializedName("hospital_name")
    @Expose
    public String hospitalName;
    @SerializedName("hospital_email")
    @Expose
    public String hospitalEmail;
    @SerializedName("hospital_timing")
    @Expose
    public String hospitalTiming;
    @SerializedName("hospital_address")
    @Expose
    public String hospitalAddress;
    @SerializedName("hospital_latitude")
    @Expose
    public String hospitalLatitude;
    @SerializedName("hospital_longitude")
    @Expose
    public String hospitalLongitude;
    @SerializedName("hospital_phone")
    @Expose
    public String hospitalPhone;
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
    @SerializedName("country_id")
    @Expose
    public Integer countryId;
    @SerializedName("state_id")
    @Expose
    public Integer stateId;
    @SerializedName("city_id")
    @Expose
    public Integer cityId;
    @SerializedName("favourite")
    @Expose
    public Integer favourite;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public String getHospitalCityName() {
        return hospitalCityName;
    }

    public String getHospitalImage() {
        return hospitalImage;
    }

    public Integer getHospitalMuncipalId() {
        return hospitalMuncipalId;
    }

    public Integer getHospitalRecommend() {
        return hospitalRecommend;
    }

    public String getHospitalOtherPhone() {
        return hospitalOtherPhone;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public String getHospitalTiming() {
        return hospitalTiming;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getHospitalLatitude() {
        return hospitalLatitude;
    }

    public String getHospitalLongitude() {
        return hospitalLongitude;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public Object getSun() {
        return sun;
    }

    public Object getMon() {
        return mon;
    }

    public Object getTue() {
        return tue;
    }

    public Object getWed() {
        return wed;
    }

    public Object getThu() {
        return thu;
    }

    public Object getFri() {
        return fri;
    }

    public Object getSat() {
        return sat;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public Integer getFavourite() {
        return favourite;
    }
}
