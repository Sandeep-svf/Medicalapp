package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medication_Data_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("drug_data")
    @Expose
    private List<Medication_Data_Response> drugData = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Medication_Data_Response> getDrugData() {
        return drugData;
    }

    public void setDrugData(List<Medication_Data_Response> drugData) {
        this.drugData = drugData;
    }

}
