package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Clinic_Specialist_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("clinic specialty")
    @Expose
    private List<ClinicSpecialty> clinicSpecialty = null;

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

    public List<ClinicSpecialty> getClinicSpecialty() {
        return clinicSpecialty;
    }

    public void setClinicSpecialty(List<ClinicSpecialty> clinicSpecialty) {
        this.clinicSpecialty = clinicSpecialty;
    }
}
