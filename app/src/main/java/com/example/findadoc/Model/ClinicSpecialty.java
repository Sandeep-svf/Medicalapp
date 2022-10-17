package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicSpecialty {
    @SerializedName("clinic_special_id")
    @Expose
    private Integer clinicSpecialId;
    @SerializedName("clinic_specialty_name")
    @Expose
    private String clinicSpecialtyName;

    public Integer getClinicSpecialId() {
        return clinicSpecialId;
    }

    public void setClinicSpecialId(Integer clinicSpecialId) {
        this.clinicSpecialId = clinicSpecialId;
    }

    public String getClinicSpecialtyName() {
        return clinicSpecialtyName;
    }

    public void setClinicSpecialtyName(String clinicSpecialtyName) {
        this.clinicSpecialtyName = clinicSpecialtyName;
    }
}
