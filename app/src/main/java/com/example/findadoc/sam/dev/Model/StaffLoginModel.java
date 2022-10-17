package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaffLoginModel {

@SerializedName("success")
@Expose
public String success;
    @SerializedName("usertpye")
    @Expose
public String usertpye;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public StaffLoginResult staffLoginResult;

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

    public StaffLoginResult getStaffLoginResult() {
        return staffLoginResult;
    }

    public void setStaffLoginResult(StaffLoginResult staffLoginResult) {
        this.staffLoginResult = staffLoginResult;
    }

    public String getUsertpye() {
        return usertpye;
    }
}