package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand_Med_Response {
    @SerializedName(value = "drugNameEn", alternate =  {"drug_name_en","drug_name_ar","drug_name_fr"})
    @Expose
    private String drugNameEn;
    @SerializedName("drug_id")
    @Expose
    private Integer drugId;

    public String getDrugNameEn() {
        return drugNameEn;
    }

    public void setDrugNameEn(String drugNameEn) {
        this.drugNameEn = drugNameEn;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }
}
