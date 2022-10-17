package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel2 {
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("errortype")
    @Expose
    public String getErrortype;
    @SerializedName("data")
    @Expose
    public LoginResponse2 loginResponse2;


    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LoginResponse2 getData() {
        return loginResponse2;
    }

    public String getGetErrortype() {
        return getErrortype;
    }


}
