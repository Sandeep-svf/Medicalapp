package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_Resp {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("user_id2")
    @Expose
    public Object userId2;
    @SerializedName("user_image")
    @Expose
    public String userImage;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("user_email")
    @Expose
    public String userEmail;
    @SerializedName("user_mobile")
    @Expose
    public String userMobile;
    @SerializedName("user_status")
    @Expose
    public Integer userStatus;
    @SerializedName("user_created_at")
    @Expose
    public String userCreatedAt;
    @SerializedName("user_address")
    @Expose
    public String userAddress;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;
    @SerializedName("cname")
    @Expose
    public String cname;
    @SerializedName("rname")
    @Expose
    public Object rname;
    @SerializedName("ctname")
    @Expose
    public String ctname;
    @SerializedName("aprove")
    @Expose
    public Integer aprove;
    @SerializedName("userType")
    @Expose
    public Integer userType;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Object getUserId2() {
        return userId2;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public String getUserCreatedAt() {
        return userCreatedAt;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getCname() {
        return cname;
    }

    public Object getRname() {
        return rname;
    }

    public String getCtname() {
        return ctname;
    }

    public Integer getAprove() {
        return aprove;
    }

    public Integer getUserType() {
        return userType;
    }
}
