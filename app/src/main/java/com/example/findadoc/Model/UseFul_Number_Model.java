package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UseFul_Number_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("useful_number")
    @Expose
    private List<UsefulNumber_Response> usefulNumber = null;

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

    public List<UsefulNumber_Response> getUsefulNumber() {
        return usefulNumber;
    }

    public void setUsefulNumber(List<UsefulNumber_Response> usefulNumber) {
        this.usefulNumber = usefulNumber;
    }
}
