package com.example.findadoc.Model.dev.sam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State_Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName(value = "provinceNameEn" , alternate = {"province_name_en","province_name_fr","province_name_ar"})
@Expose
private String provinceNameEn;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getProvinceNameEn() {
return provinceNameEn;
}

public void setProvinceNameEn(String provinceNameEn) {
this.provinceNameEn = provinceNameEn;
}

}