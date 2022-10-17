package com.example.findadoc;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.Seeting_Fragments;
import com.example.findadoc.Activity.Splash_Activity;
import com.example.findadoc.Activity.User_Profile_Activity;
import com.example.findadoc.Activity.Web_View_Activity;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.Fragments.Complaint_Fragment;
import com.example.findadoc.Fragments.Diagnosis_Fragment;
import com.example.findadoc.Fragments.Health_Directory_Fragment;
import com.example.findadoc.Fragments.Home_Fragment;
import com.example.findadoc.Fragments.Ladies_Space_Fragment;
import com.example.findadoc.Fragments.Medicine_Fragment;
import com.example.findadoc.Fragments.My_Space_Fragment;
import com.example.findadoc.Fragments.Notification_Fragments;
import com.example.findadoc.Fragments.Pharmacies_All;
import com.example.findadoc.Model.Count_Model;
import com.example.findadoc.Model.Notifiation_Model;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.ModuleModel;
import com.example.findadoc.sam.dev.Model.ModuleResult;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonSyntaxException;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bolts.CancellationTokenSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {
    boolean gps_enabled = false;
    boolean network_enabled = false;
    AlertDialog alertDialog;
    ImageView btnMenu;
    private String cityData, countryData, subLocality, stateData;
    MainActivity mainActivity;
   public static TextView title;
    public static int navItemIndex = 0;
    int flag ;
    ImageView img_notification;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private static final String TAG_DASH_BOARD = "dashboard";
    public static String CURRENT_TAG = TAG_DASH_BOARD;
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences sharedPreferences;
    RelativeLayout help_layout,my_profile_layout, logout_layout,dashboard_layout,profile_layout,Complaint_Layout,ladies_spsace,My_Spance_layout,pharmacy_layout,Medicine_layout,Diagnosis_layout,setting_layout;
    private String language="en";
    TextView home_text,profile_text,setting_text,legal_notice_text,health_dir_text,complaint,ladies_spavce_text,health_care,medicine_text,my_space,diagnosis_text;
    String user_id="",userStatus;
    public  static  TextView text_notification;
    GPSTracker gps;
    private String currentlatitude,currentlongtide;
    private double latDouble, longdouble;
    List<String> moduleNameList = new ArrayList<>();
    private String moduleNameData = "";
    private String userStatus2145,userType="";

    private String healthFlag="0";
    private String pharmacyFlag="0";
    private String diagnosisFlag="0";
    private String MedicineFlag="0";
    private String mySpaceFlag="0";
    private String ladiesSpaceFlag="0";
    private String usefulFlag="0";


    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=MainActivity.this.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","");
        sharedPreferences = MainActivity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);

        user_id=sharedPreferences.getString("person_id","");


        String user_name=sharedPreferences.getString("user_name","");
      //  userType=sharedPreferences.getString("userType","");
        Log.e("check_user_name",user_name+"ok");
        Locale locale2 = new Locale(language);
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        MainActivity.this.getBaseContext().getResources().updateConfiguration(config2, MainActivity.this.getBaseContext().getResources().getDisplayMetrics());
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.findadoc", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.menu);

        FirebaseApp.initializeApp(MainActivity.this);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        dashboard_layout=(RelativeLayout)findViewById(R.id.dashboard_layout);
        profile_layout=(RelativeLayout)findViewById(R.id.profile_layout);
        btnMenu=(ImageView)findViewById(R.id.btnMenu);
        home_text=(TextView) findViewById(R.id.home_text);
        profile_text=(TextView) findViewById(R.id.profile_text);
        health_dir_text=(TextView) findViewById(R.id.health_dir_text);
        health_care=(TextView) findViewById(R.id.health_care);
        diagnosis_text=(TextView) findViewById(R.id.diagnosis_text);
        medicine_text=(TextView) findViewById(R.id.medicine_text);
        my_space=(TextView) findViewById(R.id.my_space);
        ladies_spavce_text=(TextView) findViewById(R.id.ladies_spavce_text);
        complaint=(TextView) findViewById(R.id.complaint);

        setting_text=(TextView)findViewById(R.id.setting_text);
        legal_notice_text=(TextView)findViewById(R.id.legal_notice_text);
        title=(TextView)findViewById(R.id.title);
        my_profile_layout=(RelativeLayout)findViewById(R.id.my_profile_layout);
        logout_layout=(RelativeLayout)findViewById(R.id.logout_layout123);
        help_layout=(RelativeLayout)findViewById(R.id.help_layout);
        text_notification=(TextView)findViewById(R.id.text_notification);


        Medicine_layout=(RelativeLayout)findViewById(R.id.Medicine_layout);
        Diagnosis_layout=(RelativeLayout) findViewById(R.id.Diagnosis_layout);
        setting_layout=(RelativeLayout)findViewById(R.id.setting_layout);
        img_notification=(ImageView)findViewById(R.id.img_notification);
        btnMenu.setColorFilter(Color.WHITE);
        My_Spance_layout=(RelativeLayout)findViewById(R.id.My_Spance_layout);
        pharmacy_layout=(RelativeLayout)findViewById(R.id.pharmacy_layout);
        ladies_spsace=(RelativeLayout)findViewById(R.id.ladies_spsace);
        Complaint_Layout=(RelativeLayout)findViewById(R.id.Complaint_Layout);

        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        userStatus2145=sharedPreferences.getString("userStatus254","");
        userType=sharedPreferences.getString("userType","");




        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

      /*  if(gps_enabled)
        {
            popup();
        }*/



        // Get Current Location
        get_current_lat_long();

        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
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




        if(userStatus2145.equals("staff"))
        {

        }else
        {
            if(user_id.equals(""))
            {

            }else
            {
                module_settings_api();
            }

        }


        my_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), User_Profile_Activity.class);
                startActivity(intent);
            }
        });

        if(!user_name.equals(""))
        {
            logout_layout.setVisibility(View.VISIBLE);
        }else
        {
            logout_layout.setVisibility(View.GONE);
        }

        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignInClient.signOut();

                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("user_name", "");
                editor.putString("last_name", "");
                editor.putString("user_profile", "");

                editor.putString("user_email", "");
                editor.putString("person_id", "");
                editor.putString("staffStatus", "");
                editor.putString("user_mobile", "");
                editor.putString("id", "");
                editor.putString("userStatus254", "");
                editor.putString("userType", "");

                editor.apply();
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        notificauion_count();


        btnMenu.setOnClickListener(this::onClick);
        dashboard_layout.setOnClickListener(this::onClick);
        profile_layout.setOnClickListener(this::onClick);
        pharmacy_layout.setOnClickListener(this::onClick);
        Diagnosis_layout.setOnClickListener(this::onClick);
        Medicine_layout.setOnClickListener(this::onClick);
        My_Spance_layout.setOnClickListener(this::onClick);
        ladies_spsace.setOnClickListener(this::onClick);
        setting_layout.setOnClickListener(this);
        img_notification.setOnClickListener(this::onClick);
        Complaint_Layout.setOnClickListener(this::onClick);

        if(language.equals("ar"))
        {
            my_sliding_window_arabic();
        }
        else
        {
            my_sliding_window();
        }
//        Toast.makeText(mainActivity, language, Toast.LENGTH_SHORT).show();

        help_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Web_View_Activity.class);
                intent.putExtra("key","4");
                startActivity(intent);
            }
        });
        Home_Fragment homeFragment = new Home_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        loadHomeFragmenBack(homeFragment,fragmentManager);
    }

    private void popup() {
         alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Please turn on location");
        alertDialog.setMessage("Location service is required for some operation in this app");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(gps_enabled)
                        {

                        }else
                        {
                            Log.e("fjshdjkfhkjsdf","2 callig..");
                            popup();
                        }
                    }
                });
        alertDialog.show();
    }

    private void get_current_lat_long() {

        gps = new GPSTracker(getApplicationContext());
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
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<ModuleModel> call = Api_client.getClient().moduleModelCall(
                "7c538f486ea815187d12c54c3646d71e",
                countryData,
                stateData,
                cityData,userType);



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
                                /*Log.e("moduleName", String.valueOf(moduleResultList.size()));
                                Log.e("moduleName", moduleNameList.get(i) + " : " + id);*/
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
                                        Toast.makeText(MainActivity.this, "Something went wrong. Please restrt yout app", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion_issue",t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }

    private void my_sliding_window_arabic() {
        try {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            final Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            //int screenWidth = getScreenWidthInPixel();
            final int slidingmenuWidth = (int) (screenWidth - (screenWidth / 3.7) + 20);
            final int offset = Math.max(0, screenWidth - slidingmenuWidth);
            getSlidingMenu().setBehindOffset(offset);
            getSlidingMenu().toggle();
            getSlidingMenu().isVerticalFadingEdgeEnabled();
            getSlidingMenu().isHorizontalFadingEdgeEnabled();
            getSlidingMenu().setMode(SlidingMenu.RIGHT);
            getSlidingMenu().setFadeEnabled(true);
            getSlidingMenu().setFadeDegree(0.5f);
            getSlidingMenu().setFadingEdgeLength(10);
            getSlidingMenu().setEnabled(false);
            int width = display.getWidth(); // deprecated
            int height = display.getHeight();
            mainActivity = MainActivity.this;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void my_sliding_window() {
        try {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            final Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            //int screenWidth = getScreenWidthInPixel();
            final int slidingmenuWidth = (int) (screenWidth - (screenWidth / 3.7) + 20);
            final int offset = Math.max(0, screenWidth - slidingmenuWidth);
            getSlidingMenu().setBehindOffset(offset);
            getSlidingMenu().toggle();
            getSlidingMenu().isVerticalFadingEdgeEnabled();
            getSlidingMenu().isHorizontalFadingEdgeEnabled();
            getSlidingMenu().setMode(SlidingMenu.LEFT);
            getSlidingMenu().setFadeEnabled(true);
            getSlidingMenu().setFadeDegree(0.5f);
            getSlidingMenu().setFadingEdgeLength(10);
            getSlidingMenu().setEnabled(false);

            mainActivity = MainActivity.this;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnMenu:
                try {

                    showMenu();

                    if(userStatus2145.equals("staff"))
                    {

                    }else
                    {
                        if(user_id.equals(""))
                        {

                        }else
                        {
                            module_settings_api();
                        }

                    }

                if (language.equals("en")) {

                    Locale locale2 = new Locale("en");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    MainActivity.this.getBaseContext().getResources().updateConfiguration(config2, MainActivity.this.getBaseContext().getResources().getDisplayMetrics());
                    title.setText(getResources().getString(R.string.home));
                    home_text.setText(getResources().getString(R.string.home));
                    profile_text.setText(getResources().getString(R.string.my_profile));
                    health_dir_text.setText(getResources().getString(R.string.health_directory_sec));
                    health_care.setText(getResources().getString(R.string.pharmacy));
                    diagnosis_text.setText(getResources().getString(R.string.diagnosis));
                    medicine_text.setText(getResources().getString(R.string.medicine));
                    my_space.setText(getResources().getString(R.string.my_space));
                    ladies_spavce_text.setText(getResources().getString(R.string.ladies_space_2));
                    complaint.setText(getResources().getString(R.string.complaint));
                    setting_text.setText(getResources().getString(R.string.settings));
                    legal_notice_text.setText(getResources().getString(R.string.help_and_legal_notice));

                } else if (language.equals("ar")) {

                    Locale locale2 = new Locale("ar");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    MainActivity.this.getBaseContext().getResources().updateConfiguration(config2, MainActivity.this.getBaseContext().getResources().getDisplayMetrics());
                    title.setText(getResources().getString(R.string.home));
                    home_text.setText(getResources().getString(R.string.home));
                    profile_text.setText(getResources().getString(R.string.my_profile));
                    health_dir_text.setText(getResources().getString(R.string.health_directory));
                    health_care.setText(getResources().getString(R.string.pharmacy));
                    diagnosis_text.setText(getResources().getString(R.string.diagnosis));
                    medicine_text.setText(getResources().getString(R.string.medicine));
                    my_space.setText(getResources().getString(R.string.my_space));
                    ladies_spavce_text.setText(getResources().getString(R.string.ladies_space));
                    complaint.setText(getResources().getString(R.string.complaint));
                    setting_text.setText(getResources().getString(R.string.settings));
                    legal_notice_text.setText(getResources().getString(R.string.help_and_legal_notice));


                }
                else if (language.equals("fr")) {

                    Locale locale2 = new Locale("fr");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    MainActivity.this.getBaseContext().getResources().updateConfiguration(config2, MainActivity.this.getBaseContext().getResources().getDisplayMetrics());
                    title.setText(getResources().getString(R.string.home));
                    home_text.setText(getResources().getString(R.string.home));
                    profile_text.setText(getResources().getString(R.string.my_profile));
                    health_dir_text.setText(getResources().getString(R.string.health_directory));
                    health_care.setText(getResources().getString(R.string.pharmacy));
                    diagnosis_text.setText(getResources().getString(R.string.diagnosis));
                    medicine_text.setText(getResources().getString(R.string.medicine));
                    my_space.setText(getResources().getString(R.string.my_space));
                    ladies_spavce_text.setText(getResources().getString(R.string.ladies_space));
                    complaint.setText(getResources().getString(R.string.complaint));
                    setting_text.setText(getResources().getString(R.string.settings));
                    legal_notice_text.setText(getResources().getString(R.string.help_and_legal_notice));

                }

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                }
                catch (NullPointerException e) {
                    Log.e("TAG",e+"");
                }
                break;
            case R.id.dashboard_layout:
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASH_BOARD;
                Home_Fragment my_fav_fragment = new Home_Fragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((LinearLayout)findViewById(R.id.container)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slid_in_right,R.anim.slide_in_left);
                fragmentTransaction.add(R.id.container,my_fav_fragment);
                fragmentTransaction.commit();
                getSlidingMenu().toggle();
                title.setText("Home");
                break;
            case R.id.profile_layout:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {

                   // Log.e("fklskldf","health directory running after ckick");
                    if(healthFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else
                    {

                        // add fragment
                        Health_Directory_Fragment health_directory_fragment = new Health_Directory_Fragment();
                        FragmentManager fragmentManager_12 = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction12 = fragmentManager_12.beginTransaction();
                        fragmentTransaction12.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction12.add(R.id.container, health_directory_fragment);
                        fragmentTransaction12.commit();
                        getSlidingMenu().toggle();

                    }
                }
                break;

            case R.id.Diagnosis_layout:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(diagnosisFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else {
                        Diagnosis_Fragment diagnosis_fragment = new Diagnosis_Fragment();
                        FragmentManager fragmentManager_12 = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction12 = fragmentManager_12.beginTransaction();
                        fragmentTransaction12.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction12.add(R.id.container, diagnosis_fragment);
                        fragmentTransaction12.commit();
                        getSlidingMenu().toggle();
                    }
                }
                break;
            case R.id.pharmacy_layout:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(pharmacyFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else {
                        Pharmacies_All pharmacies_all = new Pharmacies_All();
                        FragmentManager fragmentManager_p = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction_p = fragmentManager_p.beginTransaction();
                        fragmentTransaction_p.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction_p.add(R.id.container, pharmacies_all);
                        fragmentTransaction_p.commit();
                        getSlidingMenu().toggle();
                    }
                }
                break;
            case R.id.Medicine_layout:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(MedicineFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else {
                        Medicine_Fragment my_space_fragmen = new Medicine_Fragment();
                        FragmentManager mys_fragment2 = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction3 = mys_fragment2.beginTransaction();
                        fragmentTransaction3.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction3.add(R.id.container, my_space_fragmen);
                        fragmentTransaction3.commit();
                        getSlidingMenu().toggle();
                    }
                }
                break;

            case R.id.My_Spance_layout:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(mySpaceFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else {
                        My_Space_Fragment my_space_fragment = new My_Space_Fragment();
                        FragmentManager mys_fragment = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction2 = mys_fragment.beginTransaction();
                        fragmentTransaction2.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction2.add(R.id.container, my_space_fragment);
                        fragmentTransaction2.commit();
                        getSlidingMenu().toggle();
                    }
                }
                break;
            case R.id.ladies_spsace:
                if(user_id.equals(""))
                {
                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }else {
                    if(ladiesSpaceFlag.equals("1"))
                    {
                        Toast.makeText(MainActivity.this, "Your access for this module is block by an admin", Toast.LENGTH_SHORT).show();

                    }else {
                        Ladies_Space_Fragment my_space_fragment4 = new Ladies_Space_Fragment();
                        FragmentManager mys_fragment4 = getSupportFragmentManager();
                        ((LinearLayout) findViewById(R.id.container)).removeAllViews();
                        FragmentTransaction fragmentTransaction4 = mys_fragment4.beginTransaction();
                        fragmentTransaction4.setCustomAnimations(R.anim.slid_in_right, R.anim.slide_in_left);
                        fragmentTransaction4.add(R.id.container, my_space_fragment4);
                        fragmentTransaction4.commit();
                        getSlidingMenu().toggle();
                    }
                }
                break;
            case R.id.setting_layout:
                Seeting_Fragments seeting_fragments = new Seeting_Fragments();
                FragmentManager mys_fragmen5= getSupportFragmentManager();

                FragmentTransaction fragmentTransaction5 = mys_fragmen5.beginTransaction();
                fragmentTransaction5.replace(R.id.container, seeting_fragments);
                fragmentTransaction5.addToBackStack(null);
                fragmentTransaction5.commitAllowingStateLoss();
                getSlidingMenu().toggle();
                title.setText("Settings");
                break;

            case R.id.img_notification:
                Notification_Fragments notification_fragments = new Notification_Fragments();
                FragmentManager mys_frad= getSupportFragmentManager();
                FragmentTransaction fragmentTransaction22 = mys_frad.beginTransaction();
//                fragmentTransaction22.setCustomAnimations(R.anim.slid_in_right,R.anim.slide_in_left);
                fragmentTransaction22.replace(R.id.container, notification_fragments);
                fragmentTransaction22.addToBackStack(null);
                fragmentTransaction22.commitAllowingStateLoss();
                title.setText("Notifications");
                break;

            case R.id.Complaint_Layout:
                if(user_id.equals(""))
                {
                    Intent intent=new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Complaint_Fragment Complaint_Fragment = new Complaint_Fragment();
                    FragmentManager fragment= getSupportFragmentManager();
                    FragmentTransaction fragmentTransac = fragment.beginTransaction();
//                fragmentTransaction22.setCustomAnimations(R.anim.slid_in_right,R.anim.slide_in_left);

                    fragmentTransac.replace(R.id.container, Complaint_Fragment);
                    fragmentTransac.addToBackStack(null);
                    fragmentTransac.commitAllowingStateLoss();
                    getSlidingMenu().toggle();
                    title.setText(getResources().getString(R.string.complaint));
                }

        }
    }
/*
    @Override
    public void onBackPressed()
    {
        if (shouldLoadHomeFragOnBackPress)
        {
            if (navItemIndex != 0)
            {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASH_BOARD;
                Home_Fragment homeFragment = new Home_Fragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                loadHomeFragmenBack(homeFragment,fragmentManager);
            }
            else
            {
                if (doubleBackToExitPressedOnce)
                {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
                return;
            }
        }
    }
*/

    public static void loadHomeFragmenBack(Fragment homeFragment, FragmentManager fragmentManager)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commitAllowingStateLoss();
        title.setText("Home");

    }
    public static void load_home_fragment(Fragment homeFragment, FragmentManager fragmentManager)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();


    }

    private void notificauion_count() {

        Call<Count_Model> call = Api_client.getClient().count_data("7c538f486ea815187d12c54c3646d71e",user_id);
        call.enqueue(new Callback<Count_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Count_Model> call, Response<Count_Model> response) {

                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            String count= String.valueOf(response.body().getData());
//                            Toast.makeText(getApplicationContext(), count+"A", Toast.LENGTH_LONG).show();
                            text_notification.setText(count);
                        }

                        else {

//                            Toast.makeText(getApplicationContext(), message+"B", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Count_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    Log.e("dfsdfsfsdf", String.valueOf(t));
//                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        notificauion_count();

        if(userStatus2145.equals("staff"))
        {

        }else
        {
            if(user_id.equals(""))
            {

            }else
            {
                module_settings_api();
            }
        }
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        if(userStatus2145.equals("staff"))
        {

        }else
        {
            if(user_id.equals(""))
            {

            }else
            {
                module_settings_api();
            }
        }
    }
}