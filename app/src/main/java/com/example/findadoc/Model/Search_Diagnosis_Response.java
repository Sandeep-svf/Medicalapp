package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search_Diagnosis_Response {
    @SerializedName("symptom")
    @Expose
    private String symptom;
    @SerializedName("symptom_description")
    @Expose
    private String symptomDescription;
    @SerializedName("related_disease")
    @Expose
    private String relatedDisease;

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }

    public String getRelatedDisease() {
        return relatedDisease;
    }

    public void setRelatedDisease(String relatedDisease) {
        this.relatedDisease = relatedDisease;
    }

}
