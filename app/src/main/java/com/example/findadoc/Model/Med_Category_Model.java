package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Med_Category_Model {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("medicine_category")
    @Expose
    private List<MedicineCategory_Response> medicineCategory = null;

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

    public List<MedicineCategory_Response> getMedicineCategory() {
        return medicineCategory;
    }

    public void setMedicineCategory(List<MedicineCategory_Response> medicineCategory) {
        this.medicineCategory = medicineCategory;
    }
}
