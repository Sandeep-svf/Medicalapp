package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorSpecialty {
    @SerializedName("doctor_speciality_id")
    @Expose
    private Integer doctorSpecialityId;
    @SerializedName("doctor_specialty_name")
    @Expose
    private String doctorSpecialtyName;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getDoctorSpecialityId() {
        return doctorSpecialityId;
    }

    public void setDoctorSpecialityId(Integer doctorSpecialityId) {
        this.doctorSpecialityId = doctorSpecialityId;
    }

    public String getDoctorSpecialtyName() {
        return doctorSpecialtyName;
    }

    public void setDoctorSpecialtyName(String doctorSpecialtyName) {
        this.doctorSpecialtyName = doctorSpecialtyName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
