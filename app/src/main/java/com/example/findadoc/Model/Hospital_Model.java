package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hospital_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("hospital")
    @Expose
    private List<Hospital_Resposne> hospitalResposne = null;

    @SerializedName("clinic")
    @Expose
    private List<Clinic_Response> clinic = null;
    @SerializedName("Doctor")
    @Expose
    private List<Doctor_Response> doctor = null;
    @SerializedName("lab")
    @Expose
    private List<Lab_Response> lab = null;
    @SerializedName("radiology")
    @Expose
    private List<Radiology_Response> radiology = null;

    public List<Radiology_Response> getRadiology() {
        return radiology;
    }

    public void setRadiology(List<Radiology_Response> radiology) {
        this.radiology = radiology;
    }

    public List<Lab_Response> getLab() {
        return lab;
    }

    public void setLab(List<Lab_Response> lab) {
        this.lab = lab;
    }

    public List<Doctor_Response> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<Doctor_Response> doctor) {
        this.doctor = doctor;
    }

    public List<Clinic_Response> getClinic() {
        return clinic;
    }

    public void setClinic(List<Clinic_Response> clinic) {
        this.clinic = clinic;
    }

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

    public List<Hospital_Resposne> getHospitalResposne() {
        return hospitalResposne;
    }

    public void setHospitalResposne(List<Hospital_Resposne> hospitalResposne) {
        this.hospitalResposne = hospitalResposne;
    }
}
