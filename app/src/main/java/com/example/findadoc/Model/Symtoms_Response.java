package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Symtoms_Response {

    @SerializedName("symptom_id")
    @Expose
    private Integer symptomId;
    @SerializedName("symptom_en")
    @Expose
    private String symptomEn;

    public Integer getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Integer symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomEn() {
        return symptomEn;
    }

    public void setSymptomEn(String symptomEn) {
        this.symptomEn = symptomEn;
    }

}
