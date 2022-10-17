package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doctor_specialist_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("doctor specialty")
    @Expose
    private List<DoctorSpecialty> doctorSpecialty = null;

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

    public List<DoctorSpecialty> getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(List<DoctorSpecialty> doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }
}
