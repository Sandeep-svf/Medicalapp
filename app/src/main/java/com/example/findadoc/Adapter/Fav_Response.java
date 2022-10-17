package com.example.findadoc.Adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fav_Response {


    @SerializedName("optician_recommend")
    @Expose
    private Integer optician_recommend;

    //dr
    @SerializedName("dr_id")
    @Expose
    private Integer drId;
    @SerializedName("dr_recommend")
    @Expose
    private Integer dr_recommend;
    @SerializedName("dr_name")
    @Expose
    private String drName;
    @SerializedName("dr_email")
    @Expose
    private String drEmail;
    @SerializedName("dr_phone")
    @Expose
    private String drPhone;
    @SerializedName("dr_other_phone")
    @Expose
    private String drOtherPhone;
    @SerializedName("dr_address")
    @Expose
    private String drAddress;
    @SerializedName("dr_latitude")
    @Expose
    private String drLatitude;
    @SerializedName("dr_longitude")
    @Expose
    private String drLongitude;
    @SerializedName("dr_keyword")
    @Expose
    private String drKeyword;
    @SerializedName("dr_timing")
    @Expose
    private String drTiming;
    @SerializedName("dr_city_name")
    @Expose
    private String drCityName;
    @SerializedName("dr_image")
    @Expose
    private String dr_image;

    //clinic
    @SerializedName("clinic_id")
    @Expose
    private Integer clinicId;

    @SerializedName("clinic_recommend")
    @Expose
    private Integer clinic_recommend;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_longitude")
    @Expose
    private String clinicLongitude;
    @SerializedName("clinic_latitude")
    @Expose
    private String clinicLatitude;
    @SerializedName("clinic_email")
    @Expose
    private String clinicEmail;
    @SerializedName("clinic_phone")
    @Expose
    private String clinicPhone;
    @SerializedName("clinic_other_phone")
    @Expose
    private Object clinicOtherPhone;
    @SerializedName("clinic_address")
    @Expose
    private String clinicAddress;
    @SerializedName("clinic_keyword")
    @Expose
    private String clinicKeyword;
    @SerializedName("clinic_timing")
    @Expose
    private String clinicTiming;
    @SerializedName("clinic_city_name")
    @Expose
    private String clinicCityName;
    @SerializedName("clinic_image")
    @Expose
    private String clinic_image;

    //hospital
    @SerializedName("hospital_id")
    @Expose
    private Integer hospitalId;

    @SerializedName("hospital_recommend")
    @Expose
    private Integer hospital_recommend;


    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("hospital_email")
    @Expose
    private String hospitalEmail;
    @SerializedName("hospital_phone")
    @Expose
    private String hospitalPhone;
    @SerializedName("hospital_other_phone")
    @Expose
    private Object hospitalOtherPhone;
    @SerializedName("hospital_address")
    @Expose
    private String hospitalAddress;
    @SerializedName("hospital_keyword")
    @Expose
    private String hospitalKeyword;
    @SerializedName("hospital_timing")
    @Expose
    private String hospitalTiming;
    @SerializedName("hospital_latitude")
    @Expose
    private String hospitalLatitude;
    @SerializedName("hospital_longitude")
    @Expose
    private String hospitalLongitude;
    @SerializedName("hospital_city_name")
    @Expose
    private String hospitalCityName;
    @SerializedName("hospital_image")
    @Expose
    private String hospital_image;

    //lab
    @SerializedName("laboratory_id")
    @Expose
    private Integer laboratoryId;
    @SerializedName("laboratory_name")
    @Expose
    private String laboratoryName;
    @SerializedName("laboratory_email")
    @Expose
    private String laboratoryEmail;
    @SerializedName("laboratory_phone")
    @Expose
    private String laboratoryPhone;
    @SerializedName("laboratory_other_phone")
    @Expose
    private String laboratoryOtherPhone;
    @SerializedName("laboratory_address")
    @Expose
    private String laboratoryAddress;
    @SerializedName("laboratory_keyword_en")
    @Expose
    private String laboratoryKeywordEn;
    @SerializedName("laboratory_latitude")
    @Expose
    private String laboratoryLatitude;
    @SerializedName("laboratory_longitude")
    @Expose
    private String laboratoryLongitude;
    @SerializedName("laboratory_time")
    @Expose
    private String laboratoryTime;
    @SerializedName("laboratory_city_name")
    @Expose
    private String laboratoryCityName;
    @SerializedName("laboratory_image")
    @Expose
    private String laboratory_image;

    //radiology
    @SerializedName("radiology_center_id")
    @Expose
    private Integer radiologyCenterId;
    @SerializedName("rad_center_name")
    @Expose
    private String radCenterName;
    @SerializedName("rad_center_email")
    @Expose
    private String radCenterEmail;
    @SerializedName("rad_center_latitude")
    @Expose
    private String radCenterLatitude;
    @SerializedName("rad_center_longitude")
    @Expose
    private String radCenterLongitude;
    @SerializedName("rad_center_phone")
    @Expose
    private String radCenterPhone;
    @SerializedName("rad_center_other_phone")
    @Expose
    private String radCenterOtherPhone;
    @SerializedName("rad_center_address")
    @Expose
    private String radCenterAddress;
    @SerializedName("rad_center_keyword")
    @Expose
    private String radCenterKeyword;
    @SerializedName("rad_center_timing")
    @Expose
    private String radCenterTiming;
    @SerializedName("rad_center_city_name")
    @Expose
    private String radCenterCityName;
    @SerializedName("rad_center_image")
    @Expose
    private String rad_center_image;


    @SerializedName("veterinary_id")
    @Expose
    private Integer veterinaryId;
    @SerializedName("veterinary_image")
    @Expose
    private String veterinaryImage;
    @SerializedName("veterinary_muncipal")
    @Expose
    private Integer veterinaryMuncipal;
    @SerializedName("veterinary_recommend")
    @Expose
    private Object veterinaryRecommend;
    @SerializedName("veterinary_name")
    @Expose
    private String veterinaryName;
    @SerializedName("veterinary_add")
    @Expose
    private String veterinaryAdd;
    @SerializedName("veterinary_email")
    @Expose
    private String veterinaryEmail;
    @SerializedName("veterinary_latitude")
    @Expose
    private String veterinaryLatitude;
    @SerializedName("veterinary_longitude")
    @Expose
    private String veterinaryLongitude;
    @SerializedName("veterinary_timing")
    @Expose
    private String veterinaryTiming;
    @SerializedName("veterinary_phone")
    @Expose
    private String veterinaryPhone;
    @SerializedName("veterinary_other_phone")
    @Expose
    private String veterinaryOtherPhone;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("veterinary_city_name")
    @Expose
    private String veterinaryCityName;


    @SerializedName("laboratory_recommend")
    @Expose
    private Integer laboratory_recommend;

    @SerializedName("rad_center_recommend")
    @Expose
    private Integer rad_center_recommend;



    @SerializedName("optician_id")
    @Expose
    private Integer opticianId;
    @SerializedName("optician_image")
    @Expose
    private String opticianImage;
    @SerializedName("optician_muncipal")
    @Expose
    private Integer opticianMuncipal;
    @SerializedName("optician_name")
    @Expose
    private String opticianName;
    @SerializedName("optician_add")
    @Expose
    private String opticianAdd;
    @SerializedName("optician_email")
    @Expose
    private String opticianEmail;
    @SerializedName("optician_latitude")
    @Expose
    private String opticianLatitude;
    @SerializedName("optician_longitude")
    @Expose
    private String opticianLongitude;
    @SerializedName("optician_timing")
    @Expose
    private String opticianTiming;
    @SerializedName("optician_phone")
    @Expose
    private String opticianPhone;
    @SerializedName("optician_other_phone")
    @Expose
    private String opticianOtherPhone;

    @SerializedName("optician_city_name")
    @Expose
    private String opticianCityName;




    @SerializedName("parapharmacy_id")
    @Expose
    private Integer parapharmacyId;
    @SerializedName("parapharmacy_image")
    @Expose
    private String parapharmacyImage;
    @SerializedName("parapharmacy_muncipal")
    @Expose
    private Integer parapharmacyMuncipal;
    @SerializedName("parapharmacy_recommend")
    @Expose
    private String parapharmacyRecommend;
    @SerializedName("parapharmacy_name")
    @Expose
    private String parapharmacyName;
    @SerializedName("parapharmacy_add")
    @Expose
    private String parapharmacyAdd;
    @SerializedName("parapharmacy_email")
    @Expose
    private String parapharmacyEmail;
    @SerializedName("parapharmacy_latitude")
    @Expose
    private String parapharmacyLatitude;
    @SerializedName("parapharmacy_longitude")
    @Expose
    private String parapharmacyLongitude;
    @SerializedName("parapharmacy_timing")
    @Expose
    private String parapharmacyTiming;
    @SerializedName("parapharmacy_phone")
    @Expose
    private String parapharmacyPhone;
    @SerializedName("parapharmacy_other_phone")
    @Expose
    private String parapharmacyOtherPhone;

    @SerializedName("parapharmacy_city_name")
    @Expose
    private String parapharmacyCityName;


    @SerializedName("pharmacy_id")
    @Expose
    private Integer pharmacyId;
    @SerializedName("pharmacy_image")
    @Expose
    private String pharmacyImage;
    @SerializedName("pharmacy_muncipal")
    @Expose
    private Integer pharmacyMuncipal;
    @SerializedName("pharmacy_recommend")
    @Expose
    private String pharmacyRecommend;
    @SerializedName("pharmacy_name")
    @Expose
    private String pharmacyName;
    @SerializedName("pharmacy_add")
    @Expose
    private String pharmacyAdd;
    @SerializedName("pharmacy_email")
    @Expose
    private String pharmacyEmail;
    @SerializedName("pharmacy_latitude")
    @Expose
    private String pharmacyLatitude;
    @SerializedName("pharmacy_longitude")
    @Expose
    private String pharmacyLongitude;
    @SerializedName("pharmacy_timing")
    @Expose
    private String pharmacyTiming;
    @SerializedName("pharmacy_phone")
    @Expose
    private String pharmacyPhone;
    @SerializedName("pharmacy_other_phone")
    @Expose
    private String pharmacyOtherPhone;

    @SerializedName("pharmacy_city_name")
    @Expose
    private String pharmacyCityName;


    public Integer getOptician_recommend() {
        return optician_recommend;
    }

    public Integer getRad_center_recommend() {
        return rad_center_recommend;
    }

    public Integer getLaboratory_recommend() {
        return laboratory_recommend;
    }

    public Integer getDr_recommend() {
        return dr_recommend;
    }

    public Integer getClinic_recommend() {
        return clinic_recommend;
    }

    public Integer getHospital_recommend() {
        return hospital_recommend;
    }

    public Integer getDrId() {
        return drId;
    }

    public void setDrId(Integer drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getDrPhone() {
        return drPhone;
    }

    public void setDrPhone(String drPhone) {
        this.drPhone = drPhone;
    }

    public String getDrOtherPhone() {
        return drOtherPhone;
    }

    public void setDrOtherPhone(String drOtherPhone) {
        this.drOtherPhone = drOtherPhone;
    }

    public String getDrAddress() {
        return drAddress;
    }

    public void setDrAddress(String drAddress) {
        this.drAddress = drAddress;
    }

    public String getDrLatitude() {
        return drLatitude;
    }

    public void setDrLatitude(String drLatitude) {
        this.drLatitude = drLatitude;
    }

    public String getDrLongitude() {
        return drLongitude;
    }

    public void setDrLongitude(String drLongitude) {
        this.drLongitude = drLongitude;
    }

    public String getDrKeyword() {
        return drKeyword;
    }

    public void setDrKeyword(String drKeyword) {
        this.drKeyword = drKeyword;
    }

    public String getDrTiming() {
        return drTiming;
    }

    public void setDrTiming(String drTiming) {
        this.drTiming = drTiming;
    }

    public String getDrCityName() {
        return drCityName;
    }

    public void setDrCityName(String drCityName) {
        this.drCityName = drCityName;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicLongitude() {
        return clinicLongitude;
    }

    public void setClinicLongitude(String clinicLongitude) {
        this.clinicLongitude = clinicLongitude;
    }

    public String getClinicLatitude() {
        return clinicLatitude;
    }

    public void setClinicLatitude(String clinicLatitude) {
        this.clinicLatitude = clinicLatitude;
    }

    public String getClinicEmail() {
        return clinicEmail;
    }

    public void setClinicEmail(String clinicEmail) {
        this.clinicEmail = clinicEmail;
    }

    public String getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(String clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public Object getClinicOtherPhone() {
        return clinicOtherPhone;
    }

    public void setClinicOtherPhone(Object clinicOtherPhone) {
        this.clinicOtherPhone = clinicOtherPhone;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicKeyword() {
        return clinicKeyword;
    }

    public void setClinicKeyword(String clinicKeyword) {
        this.clinicKeyword = clinicKeyword;
    }

    public String getClinicTiming() {
        return clinicTiming;
    }

    public void setClinicTiming(String clinicTiming) {
        this.clinicTiming = clinicTiming;
    }

    public String getClinicCityName() {
        return clinicCityName;
    }

    public void setClinicCityName(String clinicCityName) {
        this.clinicCityName = clinicCityName;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }

    public Object getHospitalOtherPhone() {
        return hospitalOtherPhone;
    }

    public void setHospitalOtherPhone(Object hospitalOtherPhone) {
        this.hospitalOtherPhone = hospitalOtherPhone;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalKeyword() {
        return hospitalKeyword;
    }

    public void setHospitalKeyword(String hospitalKeyword) {
        this.hospitalKeyword = hospitalKeyword;
    }

    public String getHospitalTiming() {
        return hospitalTiming;
    }

    public void setHospitalTiming(String hospitalTiming) {
        this.hospitalTiming = hospitalTiming;
    }

    public String getHospitalLatitude() {
        return hospitalLatitude;
    }

    public void setHospitalLatitude(String hospitalLatitude) {
        this.hospitalLatitude = hospitalLatitude;
    }

    public String getHospitalLongitude() {
        return hospitalLongitude;
    }

    public void setHospitalLongitude(String hospitalLongitude) {
        this.hospitalLongitude = hospitalLongitude;
    }

    public String getHospitalCityName() {
        return hospitalCityName;
    }

    public void setHospitalCityName(String hospitalCityName) {
        this.hospitalCityName = hospitalCityName;
    }

    public Integer getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Integer laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getLaboratoryEmail() {
        return laboratoryEmail;
    }

    public void setLaboratoryEmail(String laboratoryEmail) {
        this.laboratoryEmail = laboratoryEmail;
    }

    public String getLaboratoryPhone() {
        return laboratoryPhone;
    }

    public void setLaboratoryPhone(String laboratoryPhone) {
        this.laboratoryPhone = laboratoryPhone;
    }

    public String getLaboratoryOtherPhone() {
        return laboratoryOtherPhone;
    }

    public void setLaboratoryOtherPhone(String laboratoryOtherPhone) {
        this.laboratoryOtherPhone = laboratoryOtherPhone;
    }

    public String getLaboratoryAddress() {
        return laboratoryAddress;
    }

    public void setLaboratoryAddress(String laboratoryAddress) {
        this.laboratoryAddress = laboratoryAddress;
    }

    public String getLaboratoryKeywordEn() {
        return laboratoryKeywordEn;
    }

    public void setLaboratoryKeywordEn(String laboratoryKeywordEn) {
        this.laboratoryKeywordEn = laboratoryKeywordEn;
    }

    public String getLaboratoryLatitude() {
        return laboratoryLatitude;
    }

    public void setLaboratoryLatitude(String laboratoryLatitude) {
        this.laboratoryLatitude = laboratoryLatitude;
    }

    public String getLaboratoryLongitude() {
        return laboratoryLongitude;
    }

    public void setLaboratoryLongitude(String laboratoryLongitude) {
        this.laboratoryLongitude = laboratoryLongitude;
    }

    public String getLaboratoryTime() {
        return laboratoryTime;
    }

    public void setLaboratoryTime(String laboratoryTime) {
        this.laboratoryTime = laboratoryTime;
    }

    public String getLaboratoryCityName() {
        return laboratoryCityName;
    }

    public void setLaboratoryCityName(String laboratoryCityName) {
        this.laboratoryCityName = laboratoryCityName;
    }

    public Integer getRadiologyCenterId() {
        return radiologyCenterId;
    }

    public void setRadiologyCenterId(Integer radiologyCenterId) {
        this.radiologyCenterId = radiologyCenterId;
    }

    public String getRadCenterName() {
        return radCenterName;
    }

    public void setRadCenterName(String radCenterName) {
        this.radCenterName = radCenterName;
    }

    public String getRadCenterEmail() {
        return radCenterEmail;
    }

    public void setRadCenterEmail(String radCenterEmail) {
        this.radCenterEmail = radCenterEmail;
    }

    public String getRadCenterLatitude() {
        return radCenterLatitude;
    }

    public void setRadCenterLatitude(String radCenterLatitude) {
        this.radCenterLatitude = radCenterLatitude;
    }

    public String getRadCenterLongitude() {
        return radCenterLongitude;
    }

    public void setRadCenterLongitude(String radCenterLongitude) {
        this.radCenterLongitude = radCenterLongitude;
    }

    public String getRadCenterPhone() {
        return radCenterPhone;
    }

    public void setRadCenterPhone(String radCenterPhone) {
        this.radCenterPhone = radCenterPhone;
    }

    public String getRadCenterOtherPhone() {
        return radCenterOtherPhone;
    }

    public void setRadCenterOtherPhone(String radCenterOtherPhone) {
        this.radCenterOtherPhone = radCenterOtherPhone;
    }

    public String getRadCenterAddress() {
        return radCenterAddress;
    }

    public void setRadCenterAddress(String radCenterAddress) {
        this.radCenterAddress = radCenterAddress;
    }

    public String getRadCenterKeyword() {
        return radCenterKeyword;
    }

    public void setRadCenterKeyword(String radCenterKeyword) {
        this.radCenterKeyword = radCenterKeyword;
    }

    public String getRadCenterTiming() {
        return radCenterTiming;
    }

    public void setRadCenterTiming(String radCenterTiming) {
        this.radCenterTiming = radCenterTiming;
    }

    public String getRadCenterCityName() {
        return radCenterCityName;
    }

    public void setRadCenterCityName(String radCenterCityName) {
        this.radCenterCityName = radCenterCityName;
    }

    public String getDr_image() {
        return dr_image;
    }

    public void setDr_image(String dr_image) {
        this.dr_image = dr_image;
    }

    public String getClinic_image() {
        return clinic_image;
    }

    public void setClinic_image(String clinic_image) {
        this.clinic_image = clinic_image;
    }

    public String getHospital_image() {
        return hospital_image;
    }

    public void setHospital_image(String hospital_image) {
        this.hospital_image = hospital_image;
    }

    public String getLaboratory_image() {
        return laboratory_image;
    }

    public void setLaboratory_image(String laboratory_image) {
        this.laboratory_image = laboratory_image;
    }

    public String getRad_center_image() {
        return rad_center_image;
    }

    public void setRad_center_image(String rad_center_image) {
        this.rad_center_image = rad_center_image;
    }


    public Integer getVeterinaryId() {
        return veterinaryId;
    }

    public void setVeterinaryId(Integer veterinaryId) {
        this.veterinaryId = veterinaryId;
    }

    public String getVeterinaryImage() {
        return veterinaryImage;
    }

    public void setVeterinaryImage(String veterinaryImage) {
        this.veterinaryImage = veterinaryImage;
    }

    public Integer getVeterinaryMuncipal() {
        return veterinaryMuncipal;
    }

    public void setVeterinaryMuncipal(Integer veterinaryMuncipal) {
        this.veterinaryMuncipal = veterinaryMuncipal;
    }

    public Object getVeterinaryRecommend() {
        return veterinaryRecommend;
    }

    public void setVeterinaryRecommend(Object veterinaryRecommend) {
        this.veterinaryRecommend = veterinaryRecommend;
    }

    public String getVeterinaryName() {
        return veterinaryName;
    }

    public void setVeterinaryName(String veterinaryName) {
        this.veterinaryName = veterinaryName;
    }

    public String getVeterinaryAdd() {
        return veterinaryAdd;
    }

    public void setVeterinaryAdd(String veterinaryAdd) {
        this.veterinaryAdd = veterinaryAdd;
    }

    public String getVeterinaryEmail() {
        return veterinaryEmail;
    }

    public void setVeterinaryEmail(String veterinaryEmail) {
        this.veterinaryEmail = veterinaryEmail;
    }

    public String getVeterinaryLatitude() {
        return veterinaryLatitude;
    }

    public void setVeterinaryLatitude(String veterinaryLatitude) {
        this.veterinaryLatitude = veterinaryLatitude;
    }

    public String getVeterinaryLongitude() {
        return veterinaryLongitude;
    }

    public void setVeterinaryLongitude(String veterinaryLongitude) {
        this.veterinaryLongitude = veterinaryLongitude;
    }

    public String getVeterinaryTiming() {
        return veterinaryTiming;
    }

    public void setVeterinaryTiming(String veterinaryTiming) {
        this.veterinaryTiming = veterinaryTiming;
    }

    public String getVeterinaryPhone() {
        return veterinaryPhone;
    }

    public void setVeterinaryPhone(String veterinaryPhone) {
        this.veterinaryPhone = veterinaryPhone;
    }

    public String getVeterinaryOtherPhone() {
        return veterinaryOtherPhone;
    }

    public void setVeterinaryOtherPhone(String veterinaryOtherPhone) {
        this.veterinaryOtherPhone = veterinaryOtherPhone;
    }



    public String getVeterinaryCityName() {
        return veterinaryCityName;
    }

    public void setVeterinaryCityName(String veterinaryCityName) {
        this.veterinaryCityName = veterinaryCityName;
    }

    public Integer getOpticianId() {
        return opticianId;
    }

    public void setOpticianId(Integer opticianId) {
        this.opticianId = opticianId;
    }

    public String getOpticianImage() {
        return opticianImage;
    }

    public void setOpticianImage(String opticianImage) {
        this.opticianImage = opticianImage;
    }

    public Integer getOpticianMuncipal() {
        return opticianMuncipal;
    }

    public void setOpticianMuncipal(Integer opticianMuncipal) {
        this.opticianMuncipal = opticianMuncipal;
    }



    public String getOpticianName() {
        return opticianName;
    }

    public void setOpticianName(String opticianName) {
        this.opticianName = opticianName;
    }

    public String getOpticianAdd() {
        return opticianAdd;
    }

    public void setOpticianAdd(String opticianAdd) {
        this.opticianAdd = opticianAdd;
    }

    public String getOpticianEmail() {
        return opticianEmail;
    }

    public void setOpticianEmail(String opticianEmail) {
        this.opticianEmail = opticianEmail;
    }

    public String getOpticianLatitude() {
        return opticianLatitude;
    }

    public void setOpticianLatitude(String opticianLatitude) {
        this.opticianLatitude = opticianLatitude;
    }

    public String getOpticianLongitude() {
        return opticianLongitude;
    }

    public void setOpticianLongitude(String opticianLongitude) {
        this.opticianLongitude = opticianLongitude;
    }

    public String getOpticianTiming() {
        return opticianTiming;
    }

    public void setOpticianTiming(String opticianTiming) {
        this.opticianTiming = opticianTiming;
    }

    public String getOpticianPhone() {
        return opticianPhone;
    }

    public void setOpticianPhone(String opticianPhone) {
        this.opticianPhone = opticianPhone;
    }

    public String getOpticianOtherPhone() {
        return opticianOtherPhone;
    }

    public void setOpticianOtherPhone(String opticianOtherPhone) {
        this.opticianOtherPhone = opticianOtherPhone;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public String getOpticianCityName() {
        return opticianCityName;
    }

    public void setOpticianCityName(String opticianCityName) {
        this.opticianCityName = opticianCityName;
    }

    public Integer getParapharmacyId() {
        return parapharmacyId;
    }

    public void setParapharmacyId(Integer parapharmacyId) {
        this.parapharmacyId = parapharmacyId;
    }

    public String getParapharmacyImage() {
        return parapharmacyImage;
    }

    public void setParapharmacyImage(String parapharmacyImage) {
        this.parapharmacyImage = parapharmacyImage;
    }

    public Integer getParapharmacyMuncipal() {
        return parapharmacyMuncipal;
    }

    public void setParapharmacyMuncipal(Integer parapharmacyMuncipal) {
        this.parapharmacyMuncipal = parapharmacyMuncipal;
    }

    public String getParapharmacyRecommend() {
        return parapharmacyRecommend;
    }

    public void setParapharmacyRecommend(String parapharmacyRecommend) {
        this.parapharmacyRecommend = parapharmacyRecommend;
    }

    public String getParapharmacyName() {
        return parapharmacyName;
    }

    public void setParapharmacyName(String parapharmacyName) {
        this.parapharmacyName = parapharmacyName;
    }

    public String getParapharmacyAdd() {
        return parapharmacyAdd;
    }

    public void setParapharmacyAdd(String parapharmacyAdd) {
        this.parapharmacyAdd = parapharmacyAdd;
    }

    public String getParapharmacyEmail() {
        return parapharmacyEmail;
    }

    public void setParapharmacyEmail(String parapharmacyEmail) {
        this.parapharmacyEmail = parapharmacyEmail;
    }

    public String getParapharmacyLatitude() {
        return parapharmacyLatitude;
    }

    public void setParapharmacyLatitude(String parapharmacyLatitude) {
        this.parapharmacyLatitude = parapharmacyLatitude;
    }

    public String getParapharmacyLongitude() {
        return parapharmacyLongitude;
    }

    public void setParapharmacyLongitude(String parapharmacyLongitude) {
        this.parapharmacyLongitude = parapharmacyLongitude;
    }

    public String getParapharmacyTiming() {
        return parapharmacyTiming;
    }

    public void setParapharmacyTiming(String parapharmacyTiming) {
        this.parapharmacyTiming = parapharmacyTiming;
    }

    public String getParapharmacyPhone() {
        return parapharmacyPhone;
    }

    public void setParapharmacyPhone(String parapharmacyPhone) {
        this.parapharmacyPhone = parapharmacyPhone;
    }

    public String getParapharmacyOtherPhone() {
        return parapharmacyOtherPhone;
    }

    public void setParapharmacyOtherPhone(String parapharmacyOtherPhone) {
        this.parapharmacyOtherPhone = parapharmacyOtherPhone;
    }

    public String getParapharmacyCityName() {
        return parapharmacyCityName;
    }

    public void setParapharmacyCityName(String parapharmacyCityName) {
        this.parapharmacyCityName = parapharmacyCityName;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyImage() {
        return pharmacyImage;
    }

    public void setPharmacyImage(String pharmacyImage) {
        this.pharmacyImage = pharmacyImage;
    }

    public Integer getPharmacyMuncipal() {
        return pharmacyMuncipal;
    }

    public void setPharmacyMuncipal(Integer pharmacyMuncipal) {
        this.pharmacyMuncipal = pharmacyMuncipal;
    }

    public String getPharmacyRecommend() {
        return pharmacyRecommend;
    }

    public void setPharmacyRecommend(String pharmacyRecommend) {
        this.pharmacyRecommend = pharmacyRecommend;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAdd() {
        return pharmacyAdd;
    }

    public void setPharmacyAdd(String pharmacyAdd) {
        this.pharmacyAdd = pharmacyAdd;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }

    public void setPharmacyEmail(String pharmacyEmail) {
        this.pharmacyEmail = pharmacyEmail;
    }

    public String getPharmacyLatitude() {
        return pharmacyLatitude;
    }

    public void setPharmacyLatitude(String pharmacyLatitude) {
        this.pharmacyLatitude = pharmacyLatitude;
    }

    public String getPharmacyLongitude() {
        return pharmacyLongitude;
    }

    public void setPharmacyLongitude(String pharmacyLongitude) {
        this.pharmacyLongitude = pharmacyLongitude;
    }

    public String getPharmacyTiming() {
        return pharmacyTiming;
    }

    public void setPharmacyTiming(String pharmacyTiming) {
        this.pharmacyTiming = pharmacyTiming;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    public String getPharmacyOtherPhone() {
        return pharmacyOtherPhone;
    }

    public void setPharmacyOtherPhone(String pharmacyOtherPhone) {
        this.pharmacyOtherPhone = pharmacyOtherPhone;
    }



    public String getPharmacyCityName() {
        return pharmacyCityName;
    }

    public void setPharmacyCityName(String pharmacyCityName) {
        this.pharmacyCityName = pharmacyCityName;
    }


    //duty_response

    @SerializedName("pharmacy_guard_id")
    @Expose
    private Integer pharmacyGuardId;
    @SerializedName("pharmacy_guard_image")
    @Expose
    private String pharmacyGuardImage;
    @SerializedName("pharmacy_guard_muncipal")
    @Expose
    private Integer pharmacyGuardMuncipal;
    @SerializedName("pharmacy_guard_recommend")
    @Expose
    private Integer pharmacyGuardRecommend;
    @SerializedName("pharmacy_guard_name")
    @Expose
    private String pharmacyGuardName;
    @SerializedName("pharmacy_guard_add")
    @Expose
    private String pharmacyGuardAdd;
    @SerializedName("pharmacy_guard_email")
    @Expose
    private String pharmacyGuardEmail;
    @SerializedName("pharmacy_guard_latitude")
    @Expose
    private String pharmacyGuardLatitude;
    @SerializedName("pharmacy_guard_longitude")
    @Expose
    private String pharmacyGuardLongitude;
    @SerializedName("pharmacy_guard_timing")
    @Expose
    private String pharmacyGuardTiming;
    @SerializedName("pharmacy_guard_phone")
    @Expose
    private String pharmacyGuardPhone;
    @SerializedName("pharmacy_guard_other_phone")
    @Expose
    private String pharmacyGuardOtherPhone;
    @SerializedName("pharmacy_guard_city_name")
    @Expose
    private String pharmacyGuardCityName;

    public Integer getPharmacyGuardId() {
        return pharmacyGuardId;
    }

    public void setPharmacyGuardId(Integer pharmacyGuardId) {
        this.pharmacyGuardId = pharmacyGuardId;
    }

    public String getPharmacyGuardImage() {
        return pharmacyGuardImage;
    }

    public void setPharmacyGuardImage(String pharmacyGuardImage) {
        this.pharmacyGuardImage = pharmacyGuardImage;
    }

    public Integer getPharmacyGuardMuncipal() {
        return pharmacyGuardMuncipal;
    }

    public void setPharmacyGuardMuncipal(Integer pharmacyGuardMuncipal) {
        this.pharmacyGuardMuncipal = pharmacyGuardMuncipal;
    }

    public Integer getPharmacyGuardRecommend() {
        return pharmacyGuardRecommend;
    }

    public void setPharmacyGuardRecommend(Integer pharmacyGuardRecommend) {
        this.pharmacyGuardRecommend = pharmacyGuardRecommend;
    }

    public String getPharmacyGuardName() {
        return pharmacyGuardName;
    }

    public void setPharmacyGuardName(String pharmacyGuardName) {
        this.pharmacyGuardName = pharmacyGuardName;
    }

    public String getPharmacyGuardAdd() {
        return pharmacyGuardAdd;
    }

    public void setPharmacyGuardAdd(String pharmacyGuardAdd) {
        this.pharmacyGuardAdd = pharmacyGuardAdd;
    }

    public String getPharmacyGuardEmail() {
        return pharmacyGuardEmail;
    }

    public void setPharmacyGuardEmail(String pharmacyGuardEmail) {
        this.pharmacyGuardEmail = pharmacyGuardEmail;
    }

    public String getPharmacyGuardLatitude() {
        return pharmacyGuardLatitude;
    }

    public void setPharmacyGuardLatitude(String pharmacyGuardLatitude) {
        this.pharmacyGuardLatitude = pharmacyGuardLatitude;
    }

    public String getPharmacyGuardLongitude() {
        return pharmacyGuardLongitude;
    }

    public void setPharmacyGuardLongitude(String pharmacyGuardLongitude) {
        this.pharmacyGuardLongitude = pharmacyGuardLongitude;
    }

    public String getPharmacyGuardTiming() {
        return pharmacyGuardTiming;
    }

    public void setPharmacyGuardTiming(String pharmacyGuardTiming) {
        this.pharmacyGuardTiming = pharmacyGuardTiming;
    }

    public String getPharmacyGuardPhone() {
        return pharmacyGuardPhone;
    }

    public void setPharmacyGuardPhone(String pharmacyGuardPhone) {
        this.pharmacyGuardPhone = pharmacyGuardPhone;
    }

    public String getPharmacyGuardOtherPhone() {
        return pharmacyGuardOtherPhone;
    }

    public void setPharmacyGuardOtherPhone(String pharmacyGuardOtherPhone) {
        this.pharmacyGuardOtherPhone = pharmacyGuardOtherPhone;
    }

    public String getPharmacyGuardCityName() {
        return pharmacyGuardCityName;
    }

    public void setPharmacyGuardCityName(String pharmacyGuardCityName) {
        this.pharmacyGuardCityName = pharmacyGuardCityName;
    }

    //drug_store

    @SerializedName("drugstore_id")
    @Expose
    private Integer drugstoreId;
    @SerializedName("drugstore_image")
    @Expose
    private String drugstoreImage;
    @SerializedName("drugstore_muncipal")
    @Expose
    private String drugstoreMuncipal;
    @SerializedName("drugstore_recommend")
    @Expose
    private Integer drugstoreRecommend;
    @SerializedName("drugstore_name")
    @Expose
    private String drugstoreName;
    @SerializedName("drugstore_add")
    @Expose
    private String drugstoreAdd;
    @SerializedName("drugstore_email")
    @Expose
    private String drugstoreEmail;
    @SerializedName("drugstore_latitude")
    @Expose
    private String drugstoreLatitude;
    @SerializedName("drugstore_longitude")
    @Expose
    private String drugstoreLongitude;
    @SerializedName("drugstore_timing")
    @Expose
    private String drugstoreTiming;
    @SerializedName("drugstore_phone")
    @Expose
    private String drugstorePhone;
    @SerializedName("drugstore_other_phone")
    @Expose
    private String drugstoreOtherPhone;
    @SerializedName("drugstore_city_name")
    @Expose
    private String drugstoreCityName;




    public Integer getDrugstoreId() {
        return drugstoreId;
    }

    public void setDrugstoreId(Integer drugstoreId) {
        this.drugstoreId = drugstoreId;
    }

    public String getDrugstoreImage() {
        return drugstoreImage;
    }

    public void setDrugstoreImage(String drugstoreImage) {
        this.drugstoreImage = drugstoreImage;
    }

    public String getDrugstoreMuncipal() {
        return drugstoreMuncipal;
    }

    public void setDrugstoreMuncipal(String drugstoreMuncipal) {
        this.drugstoreMuncipal = drugstoreMuncipal;
    }

    public Integer getDrugstoreRecommend() {
        return drugstoreRecommend;
    }

    public void setDrugstoreRecommend(Integer drugstoreRecommend) {
        this.drugstoreRecommend = drugstoreRecommend;
    }

    public String getDrugstoreName() {
        return drugstoreName;
    }

    public void setDrugstoreName(String drugstoreName) {
        this.drugstoreName = drugstoreName;
    }

    public String getDrugstoreAdd() {
        return drugstoreAdd;
    }

    public void setDrugstoreAdd(String drugstoreAdd) {
        this.drugstoreAdd = drugstoreAdd;
    }

    public String getDrugstoreEmail() {
        return drugstoreEmail;
    }

    public void setDrugstoreEmail(String drugstoreEmail) {
        this.drugstoreEmail = drugstoreEmail;
    }

    public String getDrugstoreLatitude() {
        return drugstoreLatitude;
    }

    public void setDrugstoreLatitude(String drugstoreLatitude) {
        this.drugstoreLatitude = drugstoreLatitude;
    }

    public String getDrugstoreLongitude() {
        return drugstoreLongitude;
    }

    public void setDrugstoreLongitude(String drugstoreLongitude) {
        this.drugstoreLongitude = drugstoreLongitude;
    }

    public String getDrugstoreTiming() {
        return drugstoreTiming;
    }

    public void setDrugstoreTiming(String drugstoreTiming) {
        this.drugstoreTiming = drugstoreTiming;
    }

    public String getDrugstorePhone() {
        return drugstorePhone;
    }

    public void setDrugstorePhone(String drugstorePhone) {
        this.drugstorePhone = drugstorePhone;
    }

    public String getDrugstoreOtherPhone() {
        return drugstoreOtherPhone;
    }

    public void setDrugstoreOtherPhone(String drugstoreOtherPhone) {
        this.drugstoreOtherPhone = drugstoreOtherPhone;
    }

    public String getDrugstoreCityName() {
        return drugstoreCityName;
    }

    public void setDrugstoreCityName(String drugstoreCityName) {
        this.drugstoreCityName = drugstoreCityName;
    }


}
