package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Advance_Search_Response implements Serializable {
    @SerializedName("drug_name")
    @Expose
    private String drugNameEn;
    @SerializedName("drug_presentaion")
    @Expose
    private String drug_presentaion;
    @SerializedName("drug_price")
    @Expose
    private Integer drugPrice;
    @SerializedName("reimbursement_medician")
    @Expose
    private String reimbursementMedician;
    @SerializedName("category_medicament")
    @Expose
    private String categoryMedicamentEn;
    @SerializedName("drug_dose")
    @Expose
    private String drugDosesEn;

    public String getDrugNameEn() {
        return drugNameEn;
    }

    public void setDrugNameEn(String drugNameEn) {
        this.drugNameEn = drugNameEn;
    }

    public String drug_presentaion() {
        return drug_presentaion;
    }

    public void drug_presentaion(String presentationMedicamentEn) {
        this.drug_presentaion = presentationMedicamentEn;
    }

    public Integer getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(Integer drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getReimbursementMedician() {
        return reimbursementMedician;
    }

    public void setReimbursementMedician(String reimbursementMedician) {
        this.reimbursementMedician = reimbursementMedician;
    }

    public String getCategoryMedicamentEn() {
        return categoryMedicamentEn;
    }

    public void setCategoryMedicamentEn(String categoryMedicamentEn) {
        this.categoryMedicamentEn = categoryMedicamentEn;
    }

    public String getDrugDosesEn() {
        return drugDosesEn;
    }

    public void setDrugDosesEn(String drugDosesEn) {
        this.drugDosesEn = drugDosesEn;
    }
}
