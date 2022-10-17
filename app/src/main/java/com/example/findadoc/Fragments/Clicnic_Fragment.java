package com.example.findadoc.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Hospital_Data_Activities;
import com.example.findadoc.Adapter.Specilist_Adapter;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.Model.City;
import com.example.findadoc.Model.City_main;
import com.example.findadoc.Model.ClinicSpecialty;
import com.example.findadoc.Model.Clinic_Specialist_Model;
import com.example.findadoc.Model.dev.sam.City_Model;
import com.example.findadoc.Model.dev.sam.City_Result;
import com.example.findadoc.Model.dev.sam.Country_Model;
import com.example.findadoc.Model.dev.sam.Country_Result;
import com.example.findadoc.Model.dev.sam.State_Model;
import com.example.findadoc.Model.dev.sam.State_Result;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Clicnic_Fragment extends Fragment {

    double longitudes,latitudes;
    String currentlatitude="", currentlongtide="";
    GPSTracker gps;
    Spinner specialist_spinner;
    SearchableSpinner city_spinner , spinner_country_clinic ,spinner_state_clinic , spinner_city_clinic;
    private String sp_id="" , state_sp_id = "" , country_sp_id = "" , city_sp_id = "";
    Button search_btn;
    Switch recommend_switch;
    Switch around_switch;
    List<City> cityList=new ArrayList<>();
    List<String> city_name_list=new ArrayList<>();
    List<Country_Result> countryList=new ArrayList<>();
    List<State_Result> stateList=new ArrayList<>();
    List<City_Result> cityList2 =new ArrayList<>();
    List<String> country_name_list=new ArrayList<>();
    List<String> state_name_list=new ArrayList<>();
    List<String> city_name_list2 =new ArrayList<>();
    EditText keyword;
    private String recopmmedn="";
    private String clinic_id="";
    private String search_text="";
    SharedPreferences sharedPreferences;
    String language="en";
    RelativeLayout spinner_city_clinic_layout, spinner_state_clinic_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_clicnic_, container, false);
        spinner_city_clinic_layout=v.findViewById(R.id.spinner_city_clinic_layout);
        spinner_state_clinic_layout=v.findViewById(R.id.spinner_state_clinic_layout);
        city_spinner=v.findViewById(R.id.city_spinner);
        sharedPreferences=getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        //Toast.makeText(getActivity(), language, Toast.LENGTH_SHORT).show();
        specialist_spinner=v.findViewById(R.id.specialist_spinner);
        search_btn=v.findViewById(R.id.search_btn);
        around_switch=v.findViewById(R.id.around_switch);
        recommend_switch=v.findViewById(R.id.recommend_switch);
        keyword=v.findViewById(R.id.keyword);
        spinner_country_clinic=v.findViewById(R.id.spinner_country_clinic);
        spinner_state_clinic=v.findViewById(R.id.spinner_state_clinic);
        spinner_city_clinic=v.findViewById(R.id.spinner_city_clinic);
        around_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(around_switch.isChecked())
                {
                    gps = new GPSTracker(getActivity());
                    if (gps.canGetLocation())
                    {
                        latitudes = gps.getLatitude();
                        longitudes = gps.getLongitude();

                        currentlatitude = String.valueOf(latitudes);
                        currentlongtide = String.valueOf(longitudes);

                    }
                    else
                    {
                        gps.showSettingsAlert(around_switch);

                    }
                    city_spinner.setSelection(0);
                    sp_id="";
                    spinner_country_clinic.setSelection(0);
                    country_sp_id = "";
                    spinner_state_clinic.setSelection(0);
                    state_sp_id = "" ;
                    spinner_city_clinic.setSelection(0);
                    city_sp_id = "" ;
//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();




                }
                else
                {


                    currentlatitude = "";
                    currentlongtide ="";

//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();

                }

            }
        });

        recommend_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recommend_switch.isChecked())
                {

                    recopmmedn="recommend";
//                    Toast.makeText(getActivity(), recopmmedn, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recopmmedn="";
//                    Toast.makeText(getActivity(), recopmmedn, Toast.LENGTH_SHORT).show();

                }
            }
        });
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = city_spinner.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_city)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }
                else
                {
                    sp_id = String.valueOf(cityList.get(position).getMunicipalId());
                    Log.e("LIST_ID",sp_id+"ID");
                    around_switch.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner_country_clinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = spinner_country_clinic.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_country)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    country_sp_id = String.valueOf(countryList.get(i).getCountryId());

                    getStateSearch();
                    Log.e("LIST_ID_COUNTRY",sp_id+"ID");
                    around_switch.setChecked(false);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        spinner_state_clinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // Toast.makeText(getActivity(), "State Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = spinner_state_clinic.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_state)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    state_sp_id = String.valueOf(stateList.get(i).getId());

                    getCitySearch();

                    Log.e("LIST_ID_STATE",sp_id+"ID");
                    around_switch.setChecked(false);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city_clinic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

             //   Toast.makeText(getActivity(), "################### City Spinner Working ##############", Toast.LENGTH_SHORT).show();

                String item = spinner_city_clinic.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_city)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    city_sp_id = String.valueOf(cityList2.get(i).getId());

                    Log.e("LIST_ID_CITY",sp_id+"ID");
                    around_switch.setChecked(false);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!around_switch.isChecked()) &&  (!recommend_switch.isChecked() && city_sp_id.equals("") || recommend_switch.isChecked() && city_sp_id.equals("")))
                {
                    popup();
                    //Toast.makeText(getActivity(), getResources().getString(R.string.search_val_text), Toast.LENGTH_SHORT).show();
                }else
                {
                    if(around_switch.isChecked())
                    {
                        gps = new GPSTracker(getActivity());
                        if (gps.canGetLocation())
                        {
                            latitudes = gps.getLatitude();
                            longitudes = gps.getLongitude();

                            currentlatitude = String.valueOf(latitudes);
                            currentlongtide = String.valueOf(longitudes);

                        }
//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        currentlatitude = "";
                        currentlongtide ="";

//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();

                    }

                    search_text=keyword.getText().toString();

                    Intent intent=new Intent(getActivity(), Hospital_Data_Activities.class);
                    intent.putExtra("sp_id",city_sp_id);
                    intent.putExtra("country_sp_id",country_sp_id);
                    intent.putExtra("state_sp_id",state_sp_id);
                    intent.putExtra("lat",currentlatitude);
                    intent.putExtra("long",currentlongtide);
                    intent.putExtra("recomend",recopmmedn);
                    intent.putExtra("search",search_text);
                    intent.putExtra("specialist_id",clinic_id);
                    intent.putExtra("filter_key","2");
                    startActivity(intent);
                }

            }
        });
        getSpeialist_Data();
            getCity();
        getCountrySearch();
        return v;
    }

    private void popup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout2);
        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
    }

    private void getSpeialist_Data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Clinic_Specialist_Model> call = Api_client.getClient().specilist_data("7c538f486ea815187d12c54c3646d71e",language);
        call.enqueue(new Callback<Clinic_Specialist_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Clinic_Specialist_Model> call, Response<Clinic_Specialist_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))
                        {
                            List<ClinicSpecialty> clinicSpecialties=response.body().getClinicSpecialty();
//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
                            Specilist_Adapter customAdapter=new Specilist_Adapter(getActivity(),clinicSpecialties);
                            specialist_spinner.setAdapter(customAdapter);

                            ClinicSpecialty clinicSpecialty =new ClinicSpecialty();

                            clinicSpecialty.setClinicSpecialtyName(getResources().getString(R.string.selct_spec));
                            clinicSpecialty.setClinicSpecialId(0);
                            clinicSpecialties.add(0,clinicSpecialty);
                            specialist_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parnet, View view, int position, long id) {
                                    clinic_id= String.valueOf(clinicSpecialties.get(position).getClinicSpecialId());
                                    String cities_name=clinicSpecialties.get(position).getClinicSpecialtyName();

                                    ((TextView)view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        }else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Clinic_Specialist_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void   getCitySearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<City_Model> call = Api_client.getClient().get_city_search("7c538f486ea815187d12c54c3646d71e",language,country_sp_id,state_sp_id);
        call.enqueue(new Callback<City_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<City_Model> call, Response<City_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {
                            spinner_city_clinic_layout.setVisibility(View.VISIBLE);
                            // spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list2);

                            // #########################################################################################################

                            city_name_list2.clear();

                            // #########################################################################################################
                            cityList2 = response.body().getData();

                            City_Result city_result =new City_Result();

                            city_result.setCityNameAr(getResources().getString(R.string.select_city));
                            city_result.setId(0);
                            cityList2.add(0,city_result);
                            for (int j=0;j<cityList2.size();j++)
                            {
                                String category_name = cityList2.get(j).getCityNameAr();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    city_name_list2.add(category_name);
                                }
                                Log.e("city_name_list2",city_name_list2.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list2);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(city_name_list2);
                            dAdapter.add(getResources().getString(R.string.select_city));
                            spinner_city_clinic.setAdapter(dAdapter);
                            spinner_city_clinic.setSelection(dAdapter.getCount());

                        }else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<City_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }



    private void getStateSearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<State_Model> call = Api_client.getClient().get_province_search("7c538f486ea815187d12c54c3646d71e",language,country_sp_id);
        call.enqueue(new Callback<State_Model>() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<State_Model> call, Response<State_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {

                            spinner_state_clinic_layout.setVisibility(View.VISIBLE);
                            state_name_list.clear();

                            stateList = response.body().getData();
                            State_Result state_result =new State_Result();

                            state_result.setProvinceNameEn(getResources().getString(R.string.select_state));
                            state_result.setId(0);
                            stateList.add(0,state_result);
                            Log.e("stateListSize", String.valueOf(stateList.size()));
                            for (int j=0;j<stateList.size();j++)
                            {
                                String category_name = stateList.get(j).getProvinceNameEn();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    state_name_list.add(category_name);
                                }
                                Log.e("country_name_list",state_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, state_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            TextView textView = getView().findViewById(R.id.textView);
                            textView.setTextColor(R.color.purple_500);
                            Log.e("check","calling.........");
                            dAdapter.addAll(state_name_list);
                            dAdapter.add(getResources().getString(R.string.select_state));
                            spinner_state_clinic.setAdapter(dAdapter);
                            spinner_state_clinic.setSelection(dAdapter.getCount());

                        }else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<State_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });



    }

    private void getCountrySearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Country_Model> call = Api_client.getClient().get_country_search("7c538f486ea815187d12c54c3646d71e",
                language);
        call.enqueue(new Callback<Country_Model>() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Country_Model> call, Response<Country_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {

                            country_name_list.clear();
                            countryList = response.body().getData();

                            Country_Result country_result =new Country_Result();
                            country_result.setCountryNameEn(getResources().getString(R.string.select_country));
                            country_result.setCountryId(0);
                            countryList.add(0,country_result);
                            for (int j=0;j<countryList.size();j++)
                            {
                                String category_name = countryList.get(j).getCountryNameEn();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    country_name_list.add(category_name);
                                }
                                Log.e("country_name_list",country_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, country_name_list);
                            TextView textView = getView().findViewById(R.id.textView);
                            textView.setTextColor(R.color.purple_500);
                            Log.e("jdfjsl","calling this line............");
                            Toast.makeText(getActivity(), "caling...............", Toast.LENGTH_SHORT).show();
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(country_name_list);
                            dAdapter.add(getResources().getString(R.string.select_country));
                            spinner_country_clinic.setAdapter(dAdapter);
                            spinner_country_clinic.setSelection(dAdapter.getCount());

                        }else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Country_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    private void getCity() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<City_main> call = Api_client.getClient().get_city("7c538f486ea815187d12c54c3646d71e",language);
        call.enqueue(new Callback<City_main>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<City_main> call, Response<City_main> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))
                        {
                            cityList=response.body().getCity();
                            City city_res =new City();

                            city_res.setMunicipalName(getResources().getString(R.string.select_city));
                            city_res.setMunicipalId(0);
                            cityList.add(0,city_res);
                            for (int j=0;j<cityList.size();j++)
                            {
                                String category_name = cityList.get(j).getMunicipalName();
                                if(category_name!=null)
                                {
                                    city_name_list.add(category_name);
                                }

                                Log.e("LIST",city_name_list.get(j)+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(city_name_list);
                            dAdapter.add(getResources().getString(R.string.select_city));
                            city_spinner.setAdapter(dAdapter);
                            city_spinner.setSelection(dAdapter.getCount());


//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
/*                            CustomAdapter customAdapter=new CustomAdapter(getActivity(),cityList);
                            city_spinner.setAdapter(customAdapter);

                            City city_res =new City();

                            city_res.setMunicipalName(getResources().getString(R.string.select_city));
                            city_res.setMunicipalId(0);
                            cityList.add(0,city_res);
                            city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    around_switch.setChecked(false);
                                    sp_id= String.valueOf(cityList.get(position).getMunicipalId());
                                    String cities_name=cityList.get(position).getMunicipalName();

                                    ((TextView)view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });*/

                        }else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<City_main> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    public class spinnerAdapter extends ArrayAdapter<String>
    {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }


}