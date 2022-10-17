package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModuleModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<ModuleResult> data = null;
    @SerializedName("testing")
    @Expose
    public Integer testing;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<ModuleResult> getData() {
        return data;
    }

    public Integer getTesting() {
        return testing;
    }
}