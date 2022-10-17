package com.example.findadoc.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.findadoc.Adapter.Pharmacy_Adapter_Support;
import com.example.findadoc.Model.Clinic_Response;
import com.example.findadoc.Model.Doctor_Response;
import com.example.findadoc.Model.Drug_Store_Model;
import com.example.findadoc.Model.Drug_Store_REsponse;
import com.example.findadoc.Model.Duty_Model;
import com.example.findadoc.Model.Duty_Response;
import com.example.findadoc.Model.Hospital_Model;
import com.example.findadoc.Model.Hospital_Resposne;
import com.example.findadoc.Model.Lab_Response;
import com.example.findadoc.Model.Pharmacies_Data_Model;
import com.example.findadoc.Model.Pharmacies_Data_Response;
import com.example.findadoc.Model.Radiology_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hospital_List_Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String currentlatitude,currentlongtide;
    String sp_id="", state_sp_id= "", country_sp_id= "", City_id= "";
    String recopmmedn="";
    String search_text="";
    List<Hospital_Resposne> hospital_resposnes;
    List<Clinic_Response> clinic_responses;
    List<Doctor_Response> doctor_responses;
    List<Lab_Response> lab_responses;
    List<Radiology_Response> radiology_responses;

    List<Pharmacies_Data_Response> pharmacies_data_responses;
    List<Drug_Store_REsponse> drug_store_rEsponses;
    List<Duty_Response> duty_responses;
    private String specialist_id="";
    private String key="";
    SharedPreferences sharedPreferences;
    String language="en";
    private String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__list__map);
        currentlatitude= getIntent().getStringExtra("lat");
        currentlongtide= getIntent().getStringExtra("long");

        Log.e("fsdfsdfsdf","currentlatitude: "+currentlatitude);
        Log.e("fsdfsdfsdf","currentlongtide: "+currentlongtide);


        recopmmedn= getIntent().getStringExtra("recomend");
        search_text= getIntent().getStringExtra("search");
        state_sp_id= getIntent().getStringExtra("state_sp_id");

        country_sp_id= getIntent().getStringExtra("country_sp_id");
        sp_id= getIntent().getStringExtra("sp_id");

        Log.e("fhgdhgfdhde","fhgdhgfdhde"+sp_id);

        specialist_id= getIntent().getStringExtra("specialist_id");
        key= getIntent().getStringExtra("filter_key");
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        sharedPreferences = Hospital_List_Map.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("person_id","");
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.list_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(key.equals("1"))
        {
            getHospital_API();
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<hospital_resposnes.size();i++)
                    {
                        String name=hospital_resposnes.get(i).getHospitalName();
                        if(name.equals(title))
                        {
                            String drname=hospital_resposnes.get(i).getHospitalName();
                            String experience=hospital_resposnes.get(i).getHospitalEmail();

                      /*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*/
//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);
                        }
                    }
                }
            });

        }
        else if(key.equals("2"))
        {
            getClinioc_API();
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<clinic_responses.size();i++)
                    {
                        String name=clinic_responses.get(i).getClinicName();
                        if(name.equals(title))
                        {
                            String drname=clinic_responses.get(i).getClinicName();
                            String experience=clinic_responses.get(i).getClinicEmail();

                      /*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*/
//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });

        }

        else if(key.equals("3"))
        {
           getDoctor_DATA_API();
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<doctor_responses.size();i++)
                    {
                        String name=doctor_responses.get(i).getDrName();
                        if(name.equals(title))
                        {
                            String drname=doctor_responses.get(i).getDrName();
                            String experience=doctor_responses.get(i).getDrEmail();

                      /*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*/
//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });

        }
        else if(key.equals("4"))
        {
            getLaboraotory_API();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<lab_responses.size();i++)
                    {
                        String name=lab_responses.get(i).getLaboratoryName();
                        if(name.equals(title))
                        {
                            String drname=lab_responses.get(i).getLaboratoryName();
                            String experience=lab_responses.get(i).getLaboratoryEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("5"))
        {
            getRadiology_API();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<radiology_responses.size();i++)
                    {
                        String name=radiology_responses.get(i).getRadCenterName();
                        if(name.equals(title))
                        {
                            String drname=radiology_responses.get(i).getRadCenterName();
                            String experience=radiology_responses.get(i).getRadCenterEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("6"))
        {
            get_opticals_data();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getOpticianName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getOpticianName();
                            String experience=pharmacies_data_responses.get(i).getOpticianEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("7"))
        {
            get_drug_store_data();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getParapharmacyName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getParapharmacyName();
                            String experience=pharmacies_data_responses.get(i).getParapharmacyEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("8"))
        {
            get_verti_data();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getVeterinaryName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getVeterinaryName();
                            String experience=pharmacies_data_responses.get(i).getVeterinaryEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("9"))
        {
            get_pharmacy_data();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getPharmacyName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getPharmacyName();
                            String experience=pharmacies_data_responses.get(i).getPharmacyEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }
        else if(key.equals("10"))
        {
            drug_store_api();
/*
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getPharmacyName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getPharmacyName();
                            String experience=pharmacies_data_responses.get(i).getPharmacyEmail();

                      */
/*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*

//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });
*/

        }

        else if(key.equals("11"))
        {
            duty_api();
           /* mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    String title =  marker.getTitle();
                    for(int i=0;i<pharmacies_data_responses.size();i++)
                    {
                        String name=pharmacies_data_responses.get(i).getPharmacyName();
                        if(name.equals(title))
                        {
                            String drname=pharmacies_data_responses.get(i).getPharmacyName();
                            String experience=pharmacies_data_responses.get(i).getPharmacyEmail();

                      *//*  String charges=providers.get(i).getDoctorCharge();
                        String dr_image=providers.get(i).getDrProfile();
                        String dr_id=providers.get(i).getDrId();
                        String distance= String.valueOf(hospital_resposnes.get(i).getHospitalCityId());
                        String specialty=providers.get(i).getSpeciality();*//*
//                        open_popup(drname,experience,achievements,specialty,charges,dr_image,dr_id,distance);

                        }

                    }

                }
            });*/

        }


    }
    private void duty_api() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Duty_Model> call = Api_client.getClient().pharmacy_on_duty("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Duty_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Duty_Model> call, Response<Duty_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                            duty_responses=response.body().getData();
                            if(duty_responses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<duty_responses.size();i++)
                                {
                                    String name=duty_responses.get(i).getPharmacyGuardName();
                                    String city_name= String.valueOf(duty_responses.get(i).getPharmacyGuardCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute =Double.parseDouble(duty_responses.get(i).getPharmacyGuardLatitude());
                                    Double longitute=Double.parseDouble(duty_responses.get(i).getPharmacyGuardLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }


                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Duty_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    private void drug_store_api() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Drug_Store_Model> call = Api_client.getClient().get_drug_store("7c538f486ea815187d12c54c3646d71e",sp_id,currentlatitude,currentlongtide,language,user_id);
        call.enqueue(new Callback<Drug_Store_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Drug_Store_Model> call, Response<Drug_Store_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                            drug_store_rEsponses=response.body().getData();
                            if(drug_store_rEsponses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<drug_store_rEsponses.size();i++)
                                {
                                    String name=drug_store_rEsponses.get(i).getDrugstoreName();
                                    String city_name= String.valueOf(drug_store_rEsponses.get(i).getDrugstoreCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute =Double.parseDouble(drug_store_rEsponses.get(i).getDrugstoreLatitude());
                                    Double longitute=Double.parseDouble(drug_store_rEsponses.get(i).getDrugstoreLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }

                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Drug_Store_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_opticals_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_opticians("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Pharmacies_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Pharmacies_Data_Model> call, Response<Pharmacies_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                            pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<pharmacies_data_responses.size();i++)
                                {
                                    String name=pharmacies_data_responses.get(i).getOpticianName();
                                    String city_name= String.valueOf(pharmacies_data_responses.get(i).getOpticianCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute =Double.parseDouble(pharmacies_data_responses.get(i).getOpticianLatitude());
                                    Double longitute=Double.parseDouble(pharmacies_data_responses.get(i).getOpticianLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }


                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Pharmacies_Data_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_drug_store_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_para_pharmacy("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Pharmacies_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Pharmacies_Data_Model> call, Response<Pharmacies_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                            pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<pharmacies_data_responses.size();i++)
                                {
                                    String name=pharmacies_data_responses.get(i).getParapharmacyName();
                                    String city_name= String.valueOf(pharmacies_data_responses.get(i).getParapharmacyCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute=Double.parseDouble(pharmacies_data_responses.get(i).getParapharmacyLatitude());
                                    Double longitute=Double.parseDouble(pharmacies_data_responses.get(i).getParapharmacyLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }


                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Pharmacies_Data_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_verti_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_veterinary("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Pharmacies_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Pharmacies_Data_Model> call, Response<Pharmacies_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                            pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<pharmacies_data_responses.size();i++)
                                {
                                    String name=pharmacies_data_responses.get(i).getVeterinaryName();
                                    String city_name= String.valueOf(pharmacies_data_responses.get(i).getVeterinaryCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute=Double.parseDouble(pharmacies_data_responses.get(i).getVeterinaryLatitude());
                                    Double longitute=Double.parseDouble(pharmacies_data_responses.get(i).getVeterinaryLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }


                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Pharmacies_Data_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    private void getHospital_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_hospital("7c538f486ea815187d12c54c3646d71e",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id,state_sp_id,country_sp_id,sp_id);
        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if(success.equals("true"))
                        {

                            if (mMap!= null){
                                mMap.clear();
                            }


                            hospital_resposnes=response.body().getHospitalResposne();
                            if(hospital_resposnes.size()==0)
                            {
                                Toast.makeText(Hospital_List_Map.this,"No Data",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                for(int i=0;i<hospital_resposnes.size();i++)
                                {
                                    String name=hospital_resposnes.get(i).getHospitalName();
                                    String city_name=hospital_resposnes.get(i).getHospitalCityName();
//                                      String distance=providers.get(i).getDistance();
                                    Double latitute=Double.parseDouble(hospital_resposnes.get(i).getHospitalLatitude());
                                    Double longitute=Double.parseDouble(hospital_resposnes.get(i).getHospitalLongitude());
                                    createMarker(latitute,longitute,name);
                                }

                            }

                        }
else {
                            Toast.makeText(Hospital_List_Map.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    public void createMarker(double latitude, double longitude,String comppanyname) {
        LatLng latLng = new LatLng(latitude, longitude);
//        personClusterManager.addItem(new Person(latLng,comppanyname,comppanyname));
        if(key.equals("1"))
        {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.placeholder2x));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else if(key.equals("2"))
        {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else if(key.equals("3"))
        {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else if(key.equals("4"))
        {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else if(key.equals("5")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else if(key.equals("6"))
        {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }
        else {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(),R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 7);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        }


//        personClusterManager.cluster();
/*
        personClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Person>() {
            @Override
            public boolean onClusterClick(Cluster<Person> cluster) {
                for (Person p:
                     cluster.getItems()) {
                    Toast.makeText(getActivity(), ""+p.getTitle(), Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
*/

    }
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        //vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        //Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        // below line is use to add bitmap in our canvas.
        //Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        //vectorDrawable.draw(canvas);

        Bitmap sccale = Bitmap.createScaledBitmap(((BitmapDrawable)vectorDrawable).getBitmap(), 96, 96, false);


        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(sccale);
    }

    private void getClinioc_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_clinic_data("7c538f486ea815187d12c54c3646d71e",
                specialist_id,
                sp_id,"1","1",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if(success.equals("true"))
                        {

                            if (mMap!= null){
                                mMap.clear();
                            }


                            clinic_responses=response.body().getClinic();
                            if(clinic_responses.size()==0)
                            {
                                Toast.makeText(Hospital_List_Map.this,"No Data",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                for(int i=0;i<clinic_responses.size();i++)
                                {
                                    String name=clinic_responses.get(i).getClinicName();
                                    String city_name=clinic_responses.get(i).getClinicCityName();
//                                    String distance=providers.get(i).getDistance();
                                    Double longitute=Double.parseDouble(clinic_responses.get(i).getClinicLongitude());
                                    Double latitute=Double.parseDouble(clinic_responses.get(i).getClinicLatitude());
                                    createMarker(latitute,longitute,name);
                                }

                            }

                        }
                        else {
                            Toast.makeText(Hospital_List_Map.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    private void getDoctor_DATA_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_doctor_data("7c538f486ea815187d12c54c3646d71e",
                specialist_id,
                sp_id,"","",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if(success.equals("true"))
                        {

                            if (mMap!= null){
                                mMap.clear();
                            }


                            doctor_responses=response.body().getDoctor();
                            if(doctor_responses.size()==0)
                            {
                                Toast.makeText(Hospital_List_Map.this,"No Data",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                for(int i=0;i<doctor_responses.size();i++)
                                {
                                    String name=doctor_responses.get(i).getDrName();
                                    String city_name=doctor_responses.get(i).getDrCityName();
//                                    String distance=providers.get(i).getDistance();
                                    Double longitute=Double.parseDouble(doctor_responses.get(i).getDrLongitude());
                                    Double latitute=Double.parseDouble(doctor_responses.get(i).getDrLatitude());
                                    createMarker(latitute,longitute,name);
                                }

                            }

                        }
                        else {
                            Toast.makeText(Hospital_List_Map.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void getLaboraotory_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_laboratory("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if(success.equals("true"))
                        {

                            if (mMap!= null){
                                mMap.clear();
                            }

                            lab_responses=response.body().getLab();
                            if(lab_responses.size()==0)
                            {
                                Toast.makeText(Hospital_List_Map.this,"No Data",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                for(int i=0;i<lab_responses.size();i++)
                                {
                                    String name=lab_responses.get(i).getLaboratoryName();
                                    String city_name= String.valueOf(lab_responses.get(i).getLaboratoryCityName()   );
//                                    String distance=providers.get(i).getDistance();
                                    Double longitute=Double.parseDouble(lab_responses.get(i).getLaboratoryLongitude());
                                    Double latitute=Double.parseDouble(lab_responses.get(i).getLaboratoryLatitude());
                                    createMarker(latitute,longitute,name);
                                }

                            }

                        }
                        else {
                            Toast.makeText(Hospital_List_Map.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void getRadiology_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_radiology("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if(success.equals("true"))
                        {

                            if (mMap!= null){
                                mMap.clear();
                            }


                            radiology_responses=response.body().getRadiology();
                            if(radiology_responses.size()==0)
                            {
                                Toast.makeText(Hospital_List_Map.this,"No Data",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                for(int i=0;i<radiology_responses.size();i++)
                                {
                                    String name=radiology_responses.get(i).getRadCenterName();
                                    String city_name= String.valueOf(radiology_responses.get(i).getRadCenterCity());
//                                    String distance=providers.get(i).getDistance();
                                    Double longitute=Double.parseDouble(radiology_responses.get(i).getRadCenterLongitude());
                                    Double latitute=Double.parseDouble(radiology_responses.get(i).getRadCenterLatitude());
                                    createMarker(latitute,longitute,name);
                                }

                            }

                        }
                        else {
                            Toast.makeText(Hospital_List_Map.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    private void get_pharmacy_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_List_Map.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_pharmacy("7c538f486ea815187d12c54c3646d71e",
                sp_id,"","",
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        call.enqueue(new Callback<Pharmacies_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Pharmacies_Data_Model> call, Response<Pharmacies_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            if (mMap!= null){
                                mMap.clear();
                            }

                             pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {


                            }
                            else
                            {
                                for(int i=0;i<pharmacies_data_responses.size();i++)
                                {
                                    String name=pharmacies_data_responses.get(i).getPharmacyName();
                                    String city_name= String.valueOf(pharmacies_data_responses.get(i).getPharmacyCityName());
//                                    String distance=providers.get(i).getDistance();
                                    Double latitute=Double.parseDouble(pharmacies_data_responses.get(i).getPharmacyLatitude());
                                    Double longitute=Double.parseDouble(pharmacies_data_responses.get(i).getPharmacyLongitude());
                                    createMarker(latitute,longitute,name);
                                }
                            }


                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_List_Map.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_List_Map.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_List_Map.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_List_Map.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_List_Map.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_List_Map.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_List_Map.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_List_Map.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_List_Map.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_List_Map.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Pharmacies_Data_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Hospital_List_Map.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_List_Map.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

}