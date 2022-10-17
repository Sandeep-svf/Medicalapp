package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment_Response {
    @SerializedName("comment_id")
    @Expose
    private Integer commentId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("comment")
    @Expose
    private String comment;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
