package com.example.findadoc.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Adapter.Clinic_Data_Adapter;
import com.example.findadoc.Adapter.Doctor_Data_Adapter;
import com.example.findadoc.Adapter.Drug_Store_Adapter;
import com.example.findadoc.Adapter.Drug_Store_Second_Adapter;
import com.example.findadoc.Adapter.Duty_Adapter;
import com.example.findadoc.Adapter.Hospital_Adapter;
import com.example.findadoc.Adapter.Laboratory_Data_Adapter;
import com.example.findadoc.Adapter.Opticiens_Adapter;
import com.example.findadoc.Adapter.Pharmacy_Adapter_Support;
import com.example.findadoc.Adapter.Radiology_Adapter;
import com.example.findadoc.Adapter.Veternity_Adapter;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hospital_Data_Activities extends AppCompatActivity {
    RecyclerView hospital_recycler;
    RelativeLayout relatice_back_change_password;
    String currentlatitude,currentlongtide;
    String sp_id="", country_sp_id = "", state_sp_id = "", city_sp_id = "";
    String recopmmedn="";
    String search_text="";
    ImageView image_map;
    private String specialist_id="";
    private String filter_key="";
    TextView name;
    SharedPreferences sharedPreferences;
    String language="en";
    private String user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__data__activities);
        hospital_recycler = findViewById(R.id.hospital_recycler);
        name = findViewById(R.id.name);
        sharedPreferences = Hospital_Data_Activities.this.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
        sharedPreferences = Hospital_Data_Activities.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);

        user_id = sharedPreferences.getString("person_id", "");
        relatice_back_change_password = findViewById(R.id.relatice_back_change_password);
        image_map = findViewById(R.id.image_map);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        hospital_recycler.setLayoutManager(linearLayoutManager);

        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp_id = getIntent().getStringExtra("sp_id");
        state_sp_id = getIntent().getStringExtra("state_sp_id");
        country_sp_id = getIntent().getStringExtra("country_sp_id");

        if (sp_id.equals("0")) {
            sp_id = "";
        }
        currentlatitude = getIntent().getStringExtra("lat");
        Log.e("ghty" , currentlatitude);
        currentlongtide = getIntent().getStringExtra("long");
        Log.e("ghty" , currentlongtide);
        recopmmedn = getIntent().getStringExtra("recomend");
        search_text = getIntent().getStringExtra("search");
        specialist_id = getIntent().getStringExtra("specialist_id");
        filter_key = getIntent().getStringExtra("filter_key");
        if (specialist_id.equals("0")) {
            specialist_id = "";
        }


        image_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(getApplicationContext(), Hospital_List_Map.class);
                intent.putExtra("sp_id", sp_id);
                intent.putExtra("country_sp_id", country_sp_id);
                intent.putExtra("state_sp_id", state_sp_id);
                intent.putExtra("lat", currentlatitude);
                intent.putExtra("long", currentlongtide);
                intent.putExtra("recomend", recopmmedn);
                intent.putExtra("search", search_text);
                intent.putExtra("specialist_id", specialist_id);
                intent.putExtra("filter_key", filter_key);
                Log.e("filter_key", filter_key);

                startActivity(intent);

            }
        });
        Log.e("DATA", recopmmedn + "RR");
        Log.e("DATA", sp_id + "RR");
        Log.e("DATA", currentlatitude + "RR");
        Log.e("DATA", currentlongtide + "RR");
        Log.e("DATA", search_text + "RR");
        Log.e("DATA", filter_key + "RR");
        Log.e("DATA", specialist_id + "RR");
        if (filter_key.equals("1")) {
            getHospital_API();
            name.setText(getResources().getString(R.string.hospitals));
        } else if (filter_key.equals("2")) {
            getClinic_Data();
            name.setText(getResources().getString(R.string.clicnic));
        } else if (filter_key.equals("3")) {
            get_Doctor_Data();
            name.setText(getResources().getString(R.string.doctor));
        } else if (filter_key.equals("4")) {
            get_Laboratory_data();
            name.setText(getResources().getString(R.string.labora));
        } else if (filter_key.equals("5")) {
            get_Radioliogy_API();
            name.setText(getResources().getString(R.string.radiology));
        } else if (filter_key.equals("6")) {
            get_opticals_data();
            name.setText(getResources().getString(R.string.opticians));
        } else if (filter_key.equals("7")) {
            get_para_pharmacy();
            name.setText(getResources().getString(R.string.parapharmacy));
        } else if (filter_key.equals("8")) {
            get_verti_data();
            name.setText(getResources().getString(R.string.ver));
        } else if (filter_key.equals("9")) {
            get_pharmacy_data();
            name.setText(getResources().getString(R.string.pharm));
        } else if (filter_key.equals("11"))
        {
            phaecy_on_duty();
        name.setText(getResources().getString(R.string.pharmacy_duuty));
    }
         else if (filter_key.equals("10"))
        {
            drug_store_API();
            name.setText(getResources().getString(R.string.drugs_styore));
        }

        }

    private void drug_store_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Drug_Store_Model> call = Api_client.getClient().get_drug_store("7c538f486ea815187d12c54c3646d71e",
                sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
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
                            List<Drug_Store_REsponse> drug_store_rEsponses=response.body().getData();
                            if(drug_store_rEsponses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Drug_Store_Second_Adapter pharmacy_adapter_support=new Drug_Store_Second_Adapter(Hospital_Data_Activities.this,drug_store_rEsponses);
                                hospital_recycler.setAdapter(pharmacy_adapter_support);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void phaecy_on_duty() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Duty_Model> call = Api_client.getClient().pharmacy_on_duty("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);



        Log.e("pharmacy_on_duty",search_text+ " search_text");
        Log.e("pharmacy_on_duty",recopmmedn+ " recopmmedn");
        Log.e("pharmacy_on_duty",currentlatitude+ " currentlatitude");
        Log.e("pharmacy_on_duty",currentlongtide+ " currentlongtide");
        Log.e("pharmacy_on_duty",language+ " language");
        Log.e("pharmacy_on_duty",user_id+ " user_id");
        Log.e("pharmacy_on_duty",country_sp_id+ "country_sp_id");
        Log.e("pharmacy_on_duty",state_sp_id+ "state_sp_id");
        Log.e("pharmacy_on_duty",sp_id + " sp_id");

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
                            List<Duty_Response> duty_responses=response.body().getData();
                            if(duty_responses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Duty_Adapter pharmacy_adapter_support=new Duty_Adapter(Hospital_Data_Activities.this,duty_responses, "1");
                                hospital_recycler.setAdapter(pharmacy_adapter_support);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_pharmacy_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_pharmacy("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);

        Log.e("get_pharmacy",search_text+ " search_text");
        Log.e("get_pharmacy",recopmmedn+ " recopmmedn");
        Log.e("get_pharmacy",currentlatitude+ " currentlatitude");
        Log.e("get_pharmacy",currentlongtide+ " currentlongtide");
        Log.e("get_pharmacy",language+ " language");
        Log.e("get_pharmacy",user_id+ " user_id");
        Log.e("get_pharmacy",country_sp_id+ " user_id");
        Log.e("get_pharmacy",state_sp_id+ " user_id");
        Log.e("get_pharmacy",sp_id + " sp_id");


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
                            List<Pharmacies_Data_Response> pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Pharmacy_Adapter_Support pharmacy_adapter_support=new Pharmacy_Adapter_Support(Hospital_Data_Activities.this,pharmacies_data_responses , "96");
                                hospital_recycler.setAdapter(pharmacy_adapter_support);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_verti_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_veterinary("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);

        Log.e("get_veterinary",search_text+ " search_text");
        Log.e("get_veterinary",recopmmedn+ " recopmmedn");
        Log.e("get_veterinary",currentlatitude+ " currentlatitude");
        Log.e("get_veterinary",currentlongtide+ " currentlongtide");
        Log.e("get_veterinary",language+ " language");
        Log.e("get_veterinary",user_id+ " user_id");
        Log.e("get_veterinary",country_sp_id+ "country_sp_id");
        Log.e("get_veterinary",state_sp_id+ "state_sp_id");
        Log.e("get_veterinary",sp_id + " sp_id");

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
                            List<Pharmacies_Data_Response> pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Veternity_Adapter veternity_adapter=new Veternity_Adapter(Hospital_Data_Activities.this,pharmacies_data_responses,"45");
                                hospital_recycler.setAdapter(veternity_adapter);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_para_pharmacy() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_para_pharmacy("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);


        Log.e("get_para_pharmacy",search_text+ " search_text");
        Log.e("get_para_pharmacy",recopmmedn+ " recopmmedn");
        Log.e("get_para_pharmacy",currentlatitude+ " currentlatitude");
        Log.e("get_para_pharmacy",currentlongtide+ " currentlongtide");
        Log.e("get_para_pharmacy",language+ " language");
        Log.e("get_para_pharmacy",user_id+ " user_id");
        Log.e("get_para_pharmacy",country_sp_id+ " country_sp_id");
        Log.e("get_para_pharmacy",state_sp_id+ " state_sp_id");
        Log.e("get_para_pharmacy",sp_id + " sp_id");

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
                            List<Pharmacies_Data_Response> pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Drug_Store_Adapter hospital_adapter=new Drug_Store_Adapter(Hospital_Data_Activities.this,pharmacies_data_responses , "39");
                                hospital_recycler.setAdapter(hospital_adapter);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_opticals_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pharmacies_Data_Model> call = Api_client.getClient().get_opticians("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                currentlatitude,
                currentlongtide,
                language,
                user_id);


        Log.e("get_opticians",search_text+ " search_text");
        Log.e("get_opticians",recopmmedn+ " recopmmedn");
        Log.e("get_opticians",currentlatitude+ " currentlatitude");
        Log.e("get_opticians",currentlongtide+ " currentlongtide");
        Log.e("get_opticians",language+ " language");
        Log.e("get_opticians",user_id+ " user_id");
        Log.e("get_opticians",country_sp_id+ " country_sp_id");
        Log.e("get_opticians",state_sp_id+ " state_sp_id");
        Log.e("get_opticians",sp_id + " sp_id");

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
                            List<Pharmacies_Data_Response> pharmacies_data_responses=response.body().getData();
                            if(pharmacies_data_responses.size()==0)
                            {
                                popup();

                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Opticiens_Adapter hospital_adapter=new Opticiens_Adapter(Hospital_Data_Activities.this,pharmacies_data_responses , "89");
                                hospital_recycler.setAdapter(hospital_adapter);
                            }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    private void getClinic_Data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_clinic_data("7c538f486ea815187d12c54c3646d71e",
                specialist_id,
                sp_id,country_sp_id,state_sp_id,
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);

        Log.e("fgfgfg", "specialist_id :" +specialist_id);

        Log.e("fgfgfg", "search_text :" +search_text);
        Log.e("fgfgfg", "recopmmedn :" +recopmmedn);
        Log.e("fgfgfg", "currentlatitude :" +currentlatitude);
        Log.e("fgfgfg", "currentlongtide :" +currentlongtide);
        Log.e("fgfgfg", "language :" +language);
        Log.e("fgfgfg", "user_id :" +user_id);
        Log.e("fgfgfg", "sp_id :" +sp_id);
        Log.e("fgfgfg", "country_sp_id :" +country_sp_id);
        Log.e("fgfgfg", "state_sp_id :" +state_sp_id);


        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True"))

                        {
                            List<Clinic_Response> clinic_responses=response.body().getClinic();
                            if(clinic_responses.size()==0)
                            {
                               popup();
                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                                Clinic_Data_Adapter clinic_data_adapter=new Clinic_Data_Adapter(Hospital_Data_Activities.this,clinic_responses);
                                hospital_recycler.setAdapter(clinic_data_adapter);
                            }

                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Hospital_Model> call, Throwable t) {
                Log.e("fgdfgd", String.valueOf(t));
                if (t instanceof IOException) {

                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void popup_error(String msg) {
        final Dialog dialog = new Dialog(Hospital_Data_Activities.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);
        TextView gallary=dialog.findViewById(R.id.gallary);
        gallary.setText(msg);
        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        dialog.setCancelable(false);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });
    }

    private void popup() {
        final Dialog dialog = new Dialog(Hospital_Data_Activities.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);
        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
                dialog.dismiss();
            }
        });
    }


    private void getHospital_API() {



        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_hospital("7c538f486ea815187d12c54c3646d71e",
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,user_id,state_sp_id,country_sp_id,sp_id
                );


        Log.e("sssss",search_text+ " search_text");
        Log.e("sssss",recopmmedn+ " recopmmedn");
        Log.e("sssss",currentlatitude+ " currentlatitude");
        Log.e("sssss",currentlongtide+ " currentlongtide");
        Log.e("sssss",language+ " language");
        Log.e("sssss",user_id+ " user_id");
        Log.e("sssss",state_sp_id+ " state_sp_id");
        Log.e("sssss",country_sp_id+ " country_sp_id");
        Log.e("sssss",sp_id + " sp_id");


        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))

                        {
                            List<Hospital_Resposne> hospital_resposnes=response.body().getHospitalResposne();
                           if(hospital_resposnes.size()==0)
                           {
                               popup();

                           }
                           else
                           {
                               Log.e("fdsdfs", String.valueOf(hospital_resposnes.get(0).getFavourite()));
                               Log.e("fdsdfs", String.valueOf(hospital_resposnes.get(0).getHospitalEmail()));

//                               Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                               Hospital_Adapter hospital_adapter=new Hospital_Adapter(Hospital_Data_Activities.this,hospital_resposnes);
                               hospital_recycler.setAdapter(hospital_adapter);
                           }


                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_Doctor_Data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_doctor_data("7c538f486ea815187d12c54c3646d71e",
                specialist_id,
                sp_id,country_sp_id,state_sp_id,
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);

        Log.e("sdfrt", "specialist_id :" +specialist_id);

        Log.e("sdfrt", "search_text :" +search_text);
        Log.e("sdfrt", "recopmmedn :" +recopmmedn);
        Log.e("sdfrt", "currentlatitude :" +currentlatitude);
        Log.e("sdfrt", "currentlongtide :" +currentlongtide);
        Log.e("sdfrt", "language :" +language);
        Log.e("sdfrt", "user_id :" +user_id);
        Log.e("sdfrt", "country_sp_id :" +country_sp_id);
        Log.e("sdfrt", "state_sp_id :" +state_sp_id);
        Log.e("sdfrt", "sp_id :" +sp_id);


        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            List<Doctor_Response> doctor_responses=response.body().getDoctor();
                           if(doctor_responses.size()==0)
                           {
                               popup();
                           }
                           else
                           {
//                               Toast.makeText(Hospital_Data_Activities.this, message+"", Toast.LENGTH_LONG).show();
                               Doctor_Data_Adapter doctor_data_adapter=new Doctor_Data_Adapter(Hospital_Data_Activities.this,doctor_responses);
                               hospital_recycler.setAdapter(doctor_data_adapter);

                           }

                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    private void get_Laboratory_data() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_laboratory("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);


        Log.e("get_laboratory",search_text+ " search_text");
        Log.e("get_laboratory",recopmmedn+ " recopmmedn");
        Log.e("get_laboratory",currentlatitude+ " currentlatitude");
        Log.e("get_laboratory",currentlongtide+ " currentlongtide");
        Log.e("get_laboratory",language+ " language");
        Log.e("get_laboratory",user_id+ " user_id");
        Log.e("get_laboratory",country_sp_id+ " country_sp_id");
        Log.e("get_laboratory",state_sp_id+ " state_sp_id");
        Log.e("get_laboratory",sp_id + " sp_id");

        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True"))

                        {
                            List<Lab_Response> lab_responses=response.body().getLab();
                            if(lab_responses.size()==0)
                            {
                                popup();
                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"AA", Toast.LENGTH_LONG).show();
                                Laboratory_Data_Adapter clinic_data_adapter=new Laboratory_Data_Adapter(Hospital_Data_Activities.this,lab_responses);
                                hospital_recycler.setAdapter(clinic_data_adapter);
                            }




                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_Radioliogy_API() {

        final ProgressDialog pd = new ProgressDialog(Hospital_Data_Activities.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Hospital_Model> call = Api_client.getClient().get_radiology("7c538f486ea815187d12c54c3646d71e",
                sp_id,country_sp_id,state_sp_id,
                search_text,
                recopmmedn,
                currentlatitude,
                currentlongtide,
                language,
                user_id);
        Log.e("get_radiology",search_text+ " search_text");
        Log.e("get_radiology",recopmmedn+ " recopmmedn");
        Log.e("get_radiology",currentlatitude+ " currentlatitude");
        Log.e("get_radiology",currentlongtide+ " currentlongtide");
        Log.e("get_radiology",language+ " language");
        Log.e("get_radiology",user_id+ " user_id");
        Log.e("get_radiology",country_sp_id+ " country_sp_id");
        Log.e("get_radiology",state_sp_id+ " state_sp_id");
        Log.e("get_radiology",sp_id + " sp_id");



        call.enqueue(new Callback<Hospital_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Hospital_Model> call, Response<Hospital_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))

                        {
                            List<Radiology_Response> radiology_responses=response.body().getRadiology();
                            if(radiology_responses.size()==0)
                            {
                                popup();
                            }
                            else
                            {
//                                Toast.makeText(Hospital_Data_Activities.this, message+"AA", Toast.LENGTH_LONG).show();
                                Radiology_Adapter clinic_data_adapter=new Radiology_Adapter(Hospital_Data_Activities.this,radiology_responses);
                                hospital_recycler.setAdapter(clinic_data_adapter);
                            }




                        }else {
//                            Toast.makeText(Hospital_Data_Activities.this, message+success+"BB", Toast.LENGTH_LONG).show();
                            if(message.equals("No record found"))
                            {
                                popup();
                            }
                            else
                            {
                                popup_error(message);
                            }
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Hospital_Data_Activities.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Hospital_Data_Activities.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Hospital_Data_Activities.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Hospital_Data_Activities.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Hospital_Data_Activities.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Hospital_Data_Activities.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Hospital_Data_Activities.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Hospital_Data_Activities.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Hospital_Data_Activities.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Hospital_Data_Activities.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Hospital_Data_Activities.this, "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

}