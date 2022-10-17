package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRS {
    @SerializedName("noti_id")
    @Expose
    private Integer notiId;
    @SerializedName("noti_user_id")
    @Expose
    private String notiUserId;
    @SerializedName("noti_title")
    @Expose
    private String notiTitle;
    @SerializedName("noti_message")
    @Expose
    private String notiMessage;
    @SerializedName("noti_read_status")
    @Expose
    private Integer notiReadStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public String getNotiUserId() {
        return notiUserId;
    }

    public void setNotiUserId(String notiUserId) {
        this.notiUserId = notiUserId;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiMessage() {
        return notiMessage;
    }

    public void setNotiMessage(String notiMessage) {
        this.notiMessage = notiMessage;
    }

    public Integer getNotiReadStatus() {
        return notiReadStatus;
    }

    public void setNotiReadStatus(Integer notiReadStatus) {
        this.notiReadStatus = notiReadStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
