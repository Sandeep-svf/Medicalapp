package com.example.findadoc.Model.dev.sam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country_Result {

@SerializedName("country_id")
@Expose
private Integer countryId;
@SerializedName(value = "countryNameEn" , alternate = {"country_name_en" , "country_name_ar" , "country_name_fr"})
@Expose
private String countryNameEn;

public Integer getCountryId() {
return countryId;
}

public void setCountryId(Integer countryId) {
this.countryId = countryId;
}

public String getCountryNameEn() {
return countryNameEn;
}

public void setCountryNameEn(String countryNameEn) {
this.countryNameEn = countryNameEn;
}

}