package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Doctor_Profile;
import com.example.findadoc.Activity.Email_Details_Activity;
import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.User_Profile_Activity;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.LoginModel2;
import com.example.findadoc.sam.dev.Model.LoginResponse2;
import com.example.findadoc.sam.dev.Model.ModuleModel;
import com.example.findadoc.sam.dev.Model.ModuleResult;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class Home_Fragment extends Fragment {

    LinearLayout health_layout;
    LinearLayout distributer_space;
    LinearLayout directory_layout;
    LinearLayout ladies_space_layout;
    LinearLayout my_space_layout;
    LinearLayout medicine_layout;
    LinearLayout diagnosis_layout;
    LinearLayout Useful_number;
    LinearLayout pharmacy_layout;
    CardView health_layout_CV, directory_layout_CV, diagnosis_layout_CV, medicine_layout_CV,
            my_space_layout_CV, ladies_space_layout_CV,Useful_number_CV;
    TextView health_text;
    List<String> moduleNameList = new ArrayList<>();
    private String moduleNameData = "";
    private String id,userStatus,person_id,userType;
    GPSTracker gps;
    private String currentlatitude,currentlongtide;
    private double latDouble, longdouble;
    private String cityData, countryData, subLocality, stateData;
    ScrollView home_fragment_layout;
    LinearLayout layout_one, layout_two, layout_three;
    private String healthFlag="1";
    private String pharmacyFlag="1";
    private String diagnosisFlag="1";
    private String MedicineFlag="1";
    private String mySpaceFlag="1";
    private String ladiesSpaceFlag="1";
    private String usefulFlag="1";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home_, container, false);
        home_fragment_layout=v.findViewById(R.id.home_fragment_layout);
        layout_one=v.findViewById(R.id.layout_one);
        layout_two=v.findViewById(R.id.layout_two);
        layout_three=v.findViewById(R.id.layout_three);
        Useful_number_CV=v.findViewById(R.id.Useful_number_CV);
        ladies_space_layout_CV=v.findViewById(R.id.ladies_space_layout_CV);
        my_space_layout_CV=v.findViewById(R.id.my_space_layout_CV);
        health_layout_CV=v.findViewById(R.id.health_layout_CV);
        directory_layout_CV=v.findViewById(R.id.directory_layout_CV);
        diagnosis_layout_CV=v.findViewById(R.id.diagnosis_layout_CV);
        medicine_layout_CV=v.findViewById(R.id.medicine_layout_CV);
        health_layout=v.findViewById(R.id.health_layout);
        health_text=v.findViewById(R.id.health_text);
        ladies_space_layout=v.findViewById(R.id.ladies_space_layout);
        my_space_layout=v.findViewById(R.id.my_space_layout);
        medicine_layout=v.findViewById(R.id.medicine_layout);
        diagnosis_layout=v.findViewById(R.id.diagnosis_layout);
        directory_layout=v.findViewById(R.id.directory_layout);
        Useful_number=v.findViewById(R.id.Useful_number);
//        distributer_space=v.findViewById(R.id.distributer_space);
        MainActivity.title.setText(getResources().getString(R.string.home));
//        pharmacy_layout=v.findViewById(R.id.pharmacy_layout);
/*
        distributer_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                */
/*Intent intent=new Intent(getActivity(), Doctor_Profile.class);
                startActivity(intent);*//*

            }
        });
*/

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        userStatus=sharedPreferences.getString("userStatus254","");
        person_id=sharedPreferences.getString("person_id","");
        userType=sharedPreferences.getString("userType","");

        Log.e("hdjkfshkjdfsd",userType);


        // Get Current Location
        get_current_lat_long();

        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latDouble, longdouble, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {

            cityData = addresses.get(0).getLocality();
            countryData = addresses.get(0).getCountryName();
            String subLocality = addresses.get(0).getSubLocality();
            stateData = addresses.get(0).getAdminArea();
            Log.e("hfjshkdjfh",cityData+" "+countryData+" "+stateData+" "+subLocality);

        }
        else {
            // do your stuff
        }

        Log.e("dhfjkdkf",userStatus);
        if(userStatus.equals("staff"))
        {
            healthFlag="0";
            pharmacyFlag="0";
            diagnosisFlag="0";
            MedicineFlag="0";
            mySpaceFlag="0";
            ladiesSpaceFlag="0";
            usefulFlag="0";
        }else if(person_id.equals(""))
        {
            healthFlag="0";
            pharmacyFlag="0";
            diagnosisFlag="0";
            MedicineFlag="0";
            mySpaceFlag="0";
            ladiesSpaceFlag="0";
            usefulFlag="0";
        }else
        {
            module_settings_api();
        }


        directory_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(healthFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Health_Directory_Fragment my_fav_fragment = new Health_Directory_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }

            }
        });
        my_space_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {

                    if(mySpaceFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        My_Space_Fragment my_fav_fragment = new My_Space_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }
            }
        });
        medicine_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(MedicineFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Medicine_Fragment my_fav_fragment = new Medicine_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }
            }
        });
        diagnosis_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {
                    Log.e("dflksjdlkfjslkd",diagnosisFlag);
                    if(diagnosisFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Diagnosis_Fragment my_fav_fragment = new Diagnosis_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }
            }
        });
        ladies_space_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(ladiesSpaceFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Ladies_Space_Fragment my_fav_fragment = new Ladies_Space_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }
            }
        });
        Useful_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else
                {
                    if(usefulFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        UseFull_MobilesNo my_fav_fragment = new UseFull_MobilesNo();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(my_fav_fragment, fragmentManager);
                    }
                }


            }
        });
        health_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person_id.equals(""))
                {
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(pharmacyFlag.equals("1"))
                    {
                        Toast.makeText(getActivity(), "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();
                    }else {
                        Pharmacies_All pharmacies_all = new Pharmacies_All();
                        FragmentManager fragmentManager = getFragmentManager();

                        MainActivity.load_home_fragment(pharmacies_all, fragmentManager);
                    }
                }
            }
        });
       /* ladies_space_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        medicine_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pharmacy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/




        return  v;
    }

    private void get_current_lat_long() {

        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            double latitudes = gps.getLatitude();
            double longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

            latDouble = latitudes;
            longdouble = longitudes;

            // user_address = currentlatitude+","+currentlongtide;
            Log.e("latlong", "latitudes: " + latitudes);
            Log.e("latlong", "longitudes: " + longitudes);
        }
    }

    private void module_settings_api()   {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<ModuleModel> call = Api_client.getClient().moduleModelCall(
                "7c538f486ea815187d12c54c3646d71e",
                countryData,stateData,cityData,userType);

        //calling the api
        call.enqueue(new Callback<ModuleModel>() {
            @Override
            public void onResponse(Call<ModuleModel> call, retrofit2.Response<ModuleModel> response)
            {

                progressDialog.dismiss();
//                Toast.makeText(Login_Activity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                try
                {
                    if (response.isSuccessful())
                    {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        Log.e("flag","API RUNNING SUCCESSFULLY....");



                        if (success.equals("true") || success.equals("True"))
                        {
                            Log.e("flag","SUCCESS IS TRUE");

                            List<ModuleResult> moduleResultList = response.body().getData();

                            Log.e("flag", String.valueOf(moduleResultList.size()));

                        /*    for(int i=0; i<moduleResultList.size();i++)
                            {
                               // moduleNameList = new ArrayList<>();
                               moduleNameList.add(String.valueOf(moduleResultList.get(i).getServiceId()));
                                Log.e("flag", String.valueOf(moduleResultList.get(i).getServiceName()));
                            }*/



                                for (int i = 0; i < moduleResultList.size(); i++) {
                                    //Log.e("moduleName", String.valueOf(moduleResultList.size()));
                                    Log.e("moduleName", moduleResultList.get(i).getServiceId()+ " : " );
                                    Log.e("moduleName", moduleResultList.get(i).getBlock()+ " : " );
                                    String serviceId = String.valueOf(moduleResultList.get(i).getServiceId());
                                    String serviceName = String.valueOf(moduleResultList.get(i).getServiceName());
                                    String serviceStatus = String.valueOf(moduleResultList.get(i).getBlock());

                                    // userStatus
                                    // 0 give access to user
                                    // 1 block user for access

                                    switch (serviceId) {
                                        case "2":
                                            // Code...
                                           // health_layout_CV.setVisibility(View.GONE);
                                            pharmacyFlag=serviceStatus;
                                            break;
                                        case "3":
                                            // Code...
                                            //diagnosis_layout_CV.setVisibility(View.GONE);
                                            diagnosisFlag=serviceStatus;
                                            break;
                                        case "1":
                                            // Code...
                                           // directory_layout_CV.setVisibility(View.GONE);
                                            healthFlag=serviceStatus;
                                            break;
                                        case "4":
                                            // Code...
                                           // medicine_layout_CV.setVisibility(View.GONE);
                                            MedicineFlag=serviceStatus;
                                            break;
                                        case "6":
                                            // Code...
                                            //ladies_space_layout_CV.setVisibility(View.GONE);
                                            ladiesSpaceFlag=serviceStatus;
                                            break;
                                        case "9":
                                            // Code...
                                          //  my_space_layout_CV.setVisibility(View.GONE);
                                            mySpaceFlag=serviceStatus;
                                            break;
                                        case "8":
                                            // Code...
                                          //  Useful_number_CV.setVisibility(View.GONE);
                                            usefulFlag=serviceStatus;
                                            break;
                                        default:
                                            //code...
                                            Toast.makeText(getActivity(), "Something went wrong. Please restrt yout app", Toast.LENGTH_SHORT).show();
                                            break;

                                }
                            }
                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
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

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }
                catch (JsonSyntaxException exception )
                {
                    exception.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<ModuleModel> call, Throwable t)
            {
                progressDialog.dismiss();
                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException)
                {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion_issue",t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();



    }

    @Override
    public void onResume() {
        super.onResume();
        if(userStatus.equals("staff"))
        {

        }else
        {
          //  module_settings_api();
        }
    }


}