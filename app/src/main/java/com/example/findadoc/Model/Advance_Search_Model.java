package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Advance_Search_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("drug_data")
    @Expose
    private List<Advance_Search_Response> drugData = null;
    @SerializedName("data")
    @Expose
    private List<Login_Resp> data = null;
    public String getSuccess() {
        return success;
    }

    public List<Login_Resp> getData() {
        return data;
    }

    public void setData(List<Login_Resp> data) {
        this.data = data;
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

    public List<Advance_Search_Response> getDrugData() {
        return drugData;
    }

    public void setDrugData(List<Advance_Search_Response> drugData) {
        this.drugData = drugData;
    }

}
