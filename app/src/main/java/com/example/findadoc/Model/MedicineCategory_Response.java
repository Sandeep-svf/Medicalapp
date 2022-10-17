package com.example.findadoc.Model;

import android.renderscript.Sampler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineCategory_Response {
    @SerializedName(value = "categoryMedicamentEn", alternate = {"category_medicament_en", "category_medicament_ar", "category_medicament_fr"})
    @Expose
    private String categoryMedicamentEn;

    public String getCategoryMedicamentEn() {
        return categoryMedicamentEn;
    }

    public void setCategoryMedicamentEn(String categoryMedicamentEn) {
        this.categoryMedicamentEn = categoryMedicamentEn;
    }
}
