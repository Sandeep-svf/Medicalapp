package com.example.findadoc.Model.dev.sam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City_Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName(value = "cityNameAr" , alternate = {"city_name_ar" , "city_name_en" , "city_name_fr"})
@Expose
private String cityNameAr;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCityNameAr() {
return cityNameAr;
}

public void setCityNameAr(String cityNameAr) {
this.cityNameAr = cityNameAr;
}

}