package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Our_Partners_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sponsor")
    @Expose
    private List<Sponsor_Response> sponsor = null;

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

    public List<Sponsor_Response> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<Sponsor_Response> sponsor) {
        this.sponsor = sponsor;
    }
}
