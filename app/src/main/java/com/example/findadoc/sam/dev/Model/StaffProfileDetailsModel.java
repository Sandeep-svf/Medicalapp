package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StaffProfileDetailsModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<StaffProfileDetailsResult> data = new ArrayList<StaffProfileDetailsResult>();


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

    public List<StaffProfileDetailsResult> getData() {
        return data;
    }

    public void setData(List<StaffProfileDetailsResult> data) {
        this.data = data;
    }
}