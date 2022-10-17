package com.example.findadoc.Model.dev.sam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsModel {

        @SerializedName("success")
        @Expose
        public String success;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("record")
        @Expose
        public List<NewsRecord> newsRecord = new ArrayList<NewsRecord>();

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

        public List<NewsRecord> getRecord() {
            return newsRecord;
        }

        public void setRecord(List<NewsRecord> newsRecord) {
            this.newsRecord = newsRecord;
        }
    }