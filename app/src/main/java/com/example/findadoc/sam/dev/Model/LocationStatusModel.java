package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationStatusModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public Integer data;


    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getData() {
        return data;
    }
}