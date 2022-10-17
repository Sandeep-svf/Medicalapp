package com.example.findadoc.Retrofir;

import com.example.findadoc.Adapter.Fav_Model;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Advice_Model;
import com.example.findadoc.Model.Brand_Medicne_Model;
import com.example.findadoc.Model.Categoried_Model;
import com.example.findadoc.Model.City_main;
import com.example.findadoc.Model.Clinic_Specialist_Model;
import com.example.findadoc.Model.Comment_Model_Data;
import com.example.findadoc.Model.Count_Model;
import com.example.findadoc.Model.Doctor_Profile_Model;
import com.example.findadoc.Model.Doctor_specialist_Model;
import com.example.findadoc.Model.Drug_Store_Model;
import com.example.findadoc.Model.Duty_Model;
import com.example.findadoc.Model.Hospital_Model;
import com.example.findadoc.Model.Med_Category_Model;
import com.example.findadoc.Model.Medication_Data_Model;
import com.example.findadoc.Model.Notifiation_Model;
import com.example.findadoc.Model.Our_Partners_Model;
import com.example.findadoc.Model.Pharmacies_Data_Model;
import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.Model.Prescription_Data_Model;
import com.example.findadoc.Model.Search_Diagnosis_Model;
import com.example.findadoc.Model.Symtoms_Model;
import com.example.findadoc.Model.UseFul_Number_Model;
import com.example.findadoc.Model.Week_Model;
import com.example.findadoc.Model.dev.sam.City_Model;
import com.example.findadoc.Model.dev.sam.Country_Model;
import com.example.findadoc.Model.dev.sam.NewsModel;
import com.example.findadoc.Model.dev.sam.State_Model;
import com.example.findadoc.sam.dev.Model.LocationStatusModel;
import com.example.findadoc.sam.dev.Model.LoginModel2;
import com.example.findadoc.sam.dev.Model.ModuleModel;
import com.example.findadoc.sam.dev.Model.StaffLoginModel;
import com.example.findadoc.sam.dev.Model.StaffProfileDetailsModel;
import com.example.findadoc.sam.dev.Model.StaffProfileUpdateModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("city_list")
    Call<City_main> get_city(@Field("token") String token,@Field("lang") String lang);

    @FormUrlEncoded
    @POST("country")
    Call<Country_Model> get_country_search(@Field("token") String token,@Field("lang") String lang);

    @FormUrlEncoded
    @POST("province")
    Call<State_Model> get_province_search(@Field("token") String token,
                                 @Field("lang") String lang,
                                 @Field("country_id") String country_id);


    @FormUrlEncoded
    @POST("city")
    Call<City_Model> get_city_search(@Field("token") String token,
                                 @Field("lang") String lang,
                                 @Field("country_id") String country_id,
                                 @Field("province_id") String province_id);

    @FormUrlEncoded
    @POST("get_hospital")
    Call<Hospital_Model> get_hospital(@Field("token") String token,
                                      @Field("search")String search,
                                      @Field("recommend")String  recommend,
                                      @Field("latitude") String latitude,
                                      @Field("longitude")String longitude,
                                      @Field("lang")String lang,
                                      @Field("user_id")String user_id,
                                      @Field("state_id")String state_id,
                                      @Field("country_id")String country_id,
                                      @Field("city_id") String city_id);

    @FormUrlEncoded
    @POST("clinic_specialty")
    Call<Clinic_Specialist_Model> specilist_data(@Field("token") String token,@Field("lang")String lang);

    @FormUrlEncoded
    @POST("get_clinic")
    Call<Hospital_Model> get_clinic_data(@Field("token") String token,
                                         @Field("specialty") String specialty,
                                         @Field("city_id")String city_id,
                                         @Field("country_id")String country_id,
                                         @Field("state_id")String state_id,
                                         @Field("search")String search,@Field("recommend")String  recommend,
                                         @Field("latitude") String latitude,@Field("longitude")String longitude,
                                         @Field("lang")String lang,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("doctor_specialty")
    Call<Doctor_specialist_Model> doctore_specialist(@Field("token") String token,@Field("lang")String lang);

    @FormUrlEncoded
    @POST("get_doctor")
    Call<Hospital_Model> get_doctor_data(@Field("token") String token,
                                         @Field("specialty") String specialty,
                                         @Field("city_id")String city_id,
                                         @Field("country_id")String country_id,
                                         @Field("state_id")String state_id,
                                         @Field("search")String search,@Field("recommend")String  recommend,
                                         @Field("latitude") String latitude,@Field("longitude")String longitude,
                                         @Field("lang")String lang,@Field("user_id")String user_id);


    @FormUrlEncoded
    @POST("get_laboratory")
    Call<Hospital_Model> get_laboratory(@Field("token") String token,
                                         @Field("city_id")String city_id,
                                         @Field("country_id")String country_id,
                                         @Field("state_id")String state_id,
                                         @Field("search")String search,@Field("recommend")String  recommend,
                                         @Field("latitude") String latitude,@Field("longitude")String longitude, @Field("lang")String lang,
                                        @Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("get_radiology")
    Call<Hospital_Model> get_radiology(@Field("token") String token,
                                        @Field("city_id")String city_id,
                                        @Field("country_id")String country_id,
                                        @Field("state_id")String state_id,
                                        @Field("search")String search,@Field("recommend")String  recommend,
                                        @Field("latitude") String latitude,@Field("longitude")String longitude, @Field("lang")String lang,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("symptom_list")
    Call<Symtoms_Model> getsymtoms_list(@Field("token")String token,@Field("lang")String lang);

    @FormUrlEncoded
    @POST("diagnosis")
    Call<Search_Diagnosis_Model> getsearch_diagnosis_details(@Field("lang") String lang,@Field("search")String search,
                                                             @Field("token")String token);
    @FormUrlEncoded
    @POST("advice")
    Call<Advice_Model> get_Advice(@Field("token")String token, @Field("lang")String lang);


    @FormUrlEncoded
    @POST("medicine_list")
    Call<Brand_Medicne_Model> get_brand_list(@Field("token")String token, @Field("lang")String lang);

    @FormUrlEncoded
    @POST("medication_data")
    Call<Medication_Data_Model> get_medicine_data(@Field("token")String token, @Field("lang")String lang,@Field("drug_id")String drug_id);

    @FormUrlEncoded
    @POST("medicine_category")
    Call<Med_Category_Model>   get_category_data(@Field("token")String token, @Field("lang")String lang);

    @FormUrlEncoded
    @POST("advance_search")
    Call<Advance_Search_Model> getAdvance_Search(@Field("token")String token,@Field("drug_name") String drug_name, @Field("lang")String lang,
                                                 @Field("best_price")String best_price,@Field("category") String category,@Field("refund")String refund);


    @FormUrlEncoded
    @POST("my_space_contact")
    Call<Advance_Search_Model> my_space_contact(@Field("token")String token,
                                                @Field("lang")String lang,
                                                @Field("name") String name,
                                                @Field("email") String email,
                                                @Field("mobile")String mobile,
                                                @Field("message")String message,
                                                @Field("check")String check);

    @FormUrlEncoded
    @POST("category")
    Call<Categoried_Model> categores_model(@Field("token")String token, @Field("lang")String lang);

    @Multipart
    @POST("new_entry")
    Call<Advance_Search_Model> get_new_entry_data(@Part("token") RequestBody token,
                                                  @Part("lang")RequestBody lang,
                                                  @Part("name")RequestBody name,
                                                  @Part("country_id")RequestBody country_id,
                                                  @Part("state_id")RequestBody state_id,
                                                  @Part("city_id")RequestBody city_id,
                                                  @Part("category")RequestBody category,
                                                  @Part("address")RequestBody address,
                                                  @Part("keyword")RequestBody keyword,
                                                  @Part("email")RequestBody email,
                                                  @Part("phone")RequestBody phone,
                                                  @Part("mobile")RequestBody mobile,
                                                  @Part("open")RequestBody open,
                                                  @Part("close")RequestBody close,
                                                  @Part("special_id")RequestBody special_id,
                                                  @Part("dr_experience") RequestBody dr_experience,
                                                  @Part("days") RequestBody days,
                                                  @Part("user_id") RequestBody user_id,
                                                  @Part("latitude") RequestBody latitude,
                                                  @Part("longitude") RequestBody longitude,
                                                  @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("user_login")
    Call<LoginModel2> login_api(@Field("token")String token,
                                @Field("lang")String lang,
                                @Field("user_name")String user_name,
                                @Field("user_address")String user_address,
                                @Field("user_mobile")String user_mobile,
                                @Field("user_id")String user_id,
                                @Field("device_token")String device_token,
                                @Field("user_email")String user_email,
                                @Field("countryName")String countryName,
                                @Field("stateName ")String stateName ,
                                @Field("cityName")String cityName);


    @FormUrlEncoded
    @POST("favourite_save")
    Call<Advance_Search_Model> save_as_favourite_API(@Field("token")String token,@Field("user_id")String user_id,
                                                     @Field("category_id")String category_id,@Field("ref_id")String ref_id,@Field("lang")String lang
                                                     );

    @FormUrlEncoded
    @POST("get_favourite")
    Call<Fav_Model> get_favourite_data(@Field("token")String token,@Field("lang")String lang,
                                       @Field("user_id")String user_id,@Field("category_id")String category_id);

    @FormUrlEncoded
    @POST("useful_number")
    Call<UseFul_Number_Model> use_ful_number(@Field("token")String token);

    @Multipart
    @POST("complaint")
    Call<UseFul_Number_Model> complaint(@Part("token") RequestBody token,
                                        @Part("mobile_number")RequestBody mobile_number,
                                        @Part("name")RequestBody name,
                                        @Part("email")RequestBody email,
                                        @Part("description")RequestBody description,
                                        @Part("user_id")RequestBody user_id,
                                        @Part("usertype")RequestBody usertype,
                                        @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("payment")
    Call<ResponseBody>payment(@Body String body);


    @POST("doctor_profile")
    Call<Doctor_Profile_Model> get_Doctor_profile(@Field("dr_id")String dr_id,
                                                  @Field("lang")String lang,
                                                  @Field("token")String token,
                                                  @Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("recommend")
    Call<Fav_Model> get_recommend(@Field("token")String token,
                                  @Field("category_id")String category_id,
                                  @Field("id")String id,
                                  @Field("user_id")String user_id,
                                  @Field("previous_count")String previous_count,
                                  @Field("lang")String lang);

    @FormUrlEncoded
    @POST("dc_like")
    Call<Fav_Model> do_like_unlike(@Field("token")String token,@Field("user_id")String user_id,@Field("dr_id") String dr_id,
                                   @Field("previous_like")String previous_like);

    @FormUrlEncoded
    @POST("doctor_comment")
    Call<Fav_Model> comment_api(@Field("token")String token,@Field("user_id")String user_id,@Field("dr_id") String dr_id,
                                   @Field("lang")String lang,@Field("comment")String comment);


    @FormUrlEncoded
    @POST("get_comment")
    Call<Comment_Model_Data> get_comment(@Field("token")String token, @Field("user_id")String user_id, @Field("dr_id") String dr_id
    );

    @FormUrlEncoded
    @POST("delete_comment")
    Call<Fav_Model> comment_delete(@Field("token")String token
                                   ,@Field("comment_id")String comment_id,@Field("lang")String lang);

    @FormUrlEncoded
    @POST("get_optician")
    Call<Pharmacies_Data_Model> get_opticians(@Field("token") String token,
                                       @Field("city_id")String city_id,
                                       @Field("country_id")String country_id,
                                       @Field("state_id")String state_id,
                                       @Field("latitude") String latitude,
                                       @Field("longitude")String longitude,
                                       @Field("lang")String lang,
                                       @Field("user_id")String user_id);
    @FormUrlEncoded
    @POST("get_veterinary")
    Call<Pharmacies_Data_Model> get_veterinary(@Field("token") String token,
                                              @Field("city_id")String city_id,
                                              @Field("country_id")String country_id,
                                              @Field("state_id")String state_id,
                                              @Field("latitude") String latitude,
                                              @Field("longitude")String longitude,
                                              @Field("lang")String lang,
                                              @Field("user_id")String user_id);
    @FormUrlEncoded
    @POST("get_para_pharmacy")
    Call<Pharmacies_Data_Model> get_para_pharmacy(@Field("token") String token,
                                              @Field("city_id")String city_id,
                                              @Field("country_id")String country_id,
                                              @Field("state_id")String state_id,
                                              @Field("latitude") String latitude,
                                              @Field("longitude")String longitude,
                                              @Field("lang")String lang,
                                              @Field("user_id")String user_id);
    @FormUrlEncoded
    @POST("get_pharmacy")
    Call<Pharmacies_Data_Model> get_pharmacy(@Field("token") String token,
                                              @Field("city_id")String city_id,
                                              @Field("country_id")String country_id,
                                              @Field("state_id")String state_id,
                                              @Field("latitude") String latitude,
                                              @Field("longitude")String longitude,
                                              @Field("lang")String lang,
                                              @Field("user_id")String user_id);


    @FormUrlEncoded
    @POST("get_pharmacy_onduty")
    Call<Duty_Model> pharmacy_on_duty(@Field("token") String token,
                                      @Field("city_id")String city_id,
                                      @Field("country_id")String country_id,
                                      @Field("state_id")String state_id,
                                      @Field("latitude") String latitude,
                                      @Field("longitude")String longitude,
                                      @Field("lang")String lang,
                                      @Field("user_id")String user_id);
    @FormUrlEncoded
    @POST("get_drugstore")
    Call<Drug_Store_Model> get_drug_store(@Field("token") String token,
                                          @Field("city_id")String city_id,
                                          @Field("latitude") String latitude,
                                          @Field("longitude")String longitude,
                                          @Field("lang")String lang,
                                          @Field("user_id")String user_id);






    @FormUrlEncoded
    @POST("sponsor")
    Call<Our_Partners_Model> get_sponsers(@Field("token") String token);

    @FormUrlEncoded
    @POST("disease_list")
    Call<Pres_Model> get_disease(@Field("token") String token,@Field("lang")String lang);


    @FormUrlEncoded
    @POST("get_prescription")
    Call<Prescription_Data_Model> get_prescription(@Field("token") String token, @Field("lang")String lang,@Field("disease")String disease);

    @FormUrlEncoded
    @POST("noti_disable")
    Call<Pres_Model> noti_disable(@Field("token")String auth_token,
                                            @Field("lang")String lang,@Field("user_id")String user_id,@Field("device_token")String device_token);

   @FormUrlEncoded
    @POST("add_prescription")
    Call<Pres_Model> get_Add_prescripiton(@Field("token")String token,
                                          @Field("lang")String lang,@Field("disease")String disease,@Field("disclaimer")String disclaimer);


   @FormUrlEncoded
    @POST("get_notification")
    Call<Notifiation_Model> notification_data(@Field("token") String token,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("noti_delete")
    Call<Pres_Model> notification_delete(@Field("token") String token,
                                         @Field("user_id")String user_id,
                                         @Field("noti_id")String noti_id,
                                         @Field("lang")String lang);

    @FormUrlEncoded
    @POST("noti_delete")
    Call<Pres_Model> notification_delete_All(@Field("token") String token,
                                             @Field("user_id")String user_id,@Field("lang")String lang);

    @FormUrlEncoded
    @POST("noti_count")
    Call<Count_Model> count_data(@Field("token") String token, @Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("noti_read")
    Call<Count_Model> notification_read(@Field("token") String token, @Field("user_id")String user_id,@Field("noti_id") String noti_id);

    @FormUrlEncoded
    @POST("dr_view")
    Call<Notifiation_Model> view_doctor(@Field("pre_count")String pre_count,@Field("dr_id")String dr_id,@Field("token")String token);

    @Multipart
    @POST("update_profile")
    Call<Advance_Search_Model> update_profile(@Part("token") RequestBody device_token,
                                              @Part("lang") RequestBody user_name,
                                              @Part("user_id") RequestBody email,
                                              @Part("user_mobile") RequestBody mobile,
                                              @Part("user_name") RequestBody grade_id,
                                              @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("get_weight")
    Call<Week_Model> get_week_Data(@Field("week")String week,@Field("token")String token);


    @FormUrlEncoded
    @POST("get_news_letter")
    Call<NewsModel> news (@Field("token") String device_token,
                            @Field("lang") String user_name);

    @FormUrlEncoded
    @POST("staff_login")
    Call<StaffLoginModel> staffLogin (@Field("token") String token,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("lang") String language,
                                      @Field("device_token") String device_token

    );



    @Multipart
    @POST("staff_update_profile")
    Call<StaffProfileUpdateModel> staffProfileUPdate (@Part("token") RequestBody token,
                                      @Part("lang") RequestBody lang,
                                      @Part("staff_id") RequestBody staff_id,
                                      @Part("staff_mobile") RequestBody staff_mobile,
                                      @Part("staff_name") RequestBody staff_name,
                                              @Part MultipartBody.Part image);



    @FormUrlEncoded
    @POST(" staff_detail")
    Call<StaffProfileDetailsModel> staffProfileDetails (@Field("token") String token,
                                                        @Field("lang") String lang,
                                                        @Field("staff_id") String staff_id);
    @FormUrlEncoded
    @POST("staff_location")
    Call<LocationStatusModel> locationStatusModelCall (@Field("token") String token,
                                                       @Field("staff_id") String staff_id);

    @FormUrlEncoded
    @POST("blockModule")
    Call<ModuleModel> moduleModelCall(@Field("token") String token,
                                      @Field("countryName") String countryName,
                                      @Field("stateName") String stateName,
                                      @Field("cityName") String cityName,
                                      @Field("usertype") String usertype) ;

}
