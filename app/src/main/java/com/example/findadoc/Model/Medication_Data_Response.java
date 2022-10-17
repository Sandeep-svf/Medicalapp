package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medication_Data_Response {
    @SerializedName("drug_name")
    @Expose
    private String drugNameEn;
    @SerializedName("presentation")
    @Expose
    private String presentationMedicamentEn;
    @SerializedName("drug_price")
    @Expose
    private String drugPrice;
    @SerializedName("reimbursement_medician")
    @Expose
    private String reimbursementMedician;
    @SerializedName("precaution")
    @Expose
    private String precautionMedicamentEn;
    @SerializedName("effect")
    @Expose
    private String effectMedicamentEn;
    @SerializedName("category")
    @Expose
    private String categoryMedicamentEn;
    @SerializedName("expiration_date")
    @Expose
    private String expirationDate;
    @SerializedName("pregnancy")
    @Expose
    private String drug_pregnancy_en;

    public String getDrug_pregnancy_en() {
        return drug_pregnancy_en;
    }

    public void setDrug_pregnancy_en(String drug_pregnancy_en) {
        this.drug_pregnancy_en = drug_pregnancy_en;
    }

    public String getDrugNameEn() {
        return drugNameEn;
    }

    public void setDrugNameEn(String drugNameEn) {
        this.drugNameEn = drugNameEn;
    }

    public String getPresentationMedicamentEn() {
        return presentationMedicamentEn;
    }

    public void setPresentationMedicamentEn(String presentationMedicamentEn) {
        this.presentationMedicamentEn = presentationMedicamentEn;
    }

    public String getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(String drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getReimbursementMedician() {
        return reimbursementMedician;
    }

    public void setReimbursementMedician(String reimbursementMedician) {
        this.reimbursementMedician = reimbursementMedician;
    }

    public String getPrecautionMedicamentEn() {
        return precautionMedicamentEn;
    }

    public void setPrecautionMedicamentEn(String precautionMedicamentEn) {
        this.precautionMedicamentEn = precautionMedicamentEn;
    }

    public String getEffectMedicamentEn() {
        return effectMedicamentEn;
    }

    public void setEffectMedicamentEn(String effectMedicamentEn) {
        this.effectMedicamentEn = effectMedicamentEn;
    }

    public String getCategoryMedicamentEn() {
        return categoryMedicamentEn;
    }

    public void setCategoryMedicamentEn(String categoryMedicamentEn) {
        this.categoryMedicamentEn = categoryMedicamentEn;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
