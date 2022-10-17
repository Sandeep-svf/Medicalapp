package com.example.findadoc.Model;

public class Days_Model {
    String days;
    String id;

    public Days_Model(String days, String id) {
        this.days = days;
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
