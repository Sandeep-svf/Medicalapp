package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.LoginModel2;
import com.example.findadoc.sam.dev.Model.LoginResponse2;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Email_Details_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText email_text,mobileno;
    String email_strig="";
    Button send_btn;
    String p_name,p_id,p_mobile,p_address,device_token,mobile_number="";
    private String language;
    GPSTracker gps;
    private String currentlatitude,currentlongtide;
    private double latDouble, longdouble;
    private String cityData, countryData, subLocality, stateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_email__details_);
        email_text=findViewById(R.id.email_text123);
        send_btn=findViewById(R.id.send_btn123);
        mobileno=findViewById(R.id.mobileno123);
        p_id=getIntent().getStringExtra("p_id");
        p_name=getIntent().getStringExtra("p_name");
        device_token=getIntent().getStringExtra("p_token");

        p_address=getIntent().getStringExtra("address");
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");

        Log.e("flag_5465","EMAIL ACTIVITY CALLING...");

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


            Log.e("fdsdfsfdf",cityData+" "+countryData+" "+stateData+" "+subLocality);

        }
        else {
            // do your stuff
        }

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("flag_5465","CLICK LISTENER CALLING...");

                email_strig=email_text.getText().toString();
                mobile_number=mobileno.getText().toString();

                if(email_strig.equals(""))
                {
                    email_text.requestFocus();
                    email_text.setError(getResources().getString(R.string.enter_email));
                }
                else if(!email_strig.matches("[A-Z0-9a-z._%+-]{2,}+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"))
                {
                    email_text.setError(getResources().getString(R.string.email_error));
                    email_text.requestFocus();
                }
                else if(mobile_number.equals(""))
                {
                    mobileno.requestFocus();
                    mobileno.setError(getResources().getString(R.string.enter_phone));
                }
                else
                {
                    Log.e("flag_5465","API METHOD CALLING...");
                    login_API();
                }
            }
        });

    }

    private void get_current_lat_long() {

        gps = new GPSTracker(Email_Details_Activity.this);
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

    private void login_API() {

        Log.e("flag_5465","API CALLING.......");

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(Email_Details_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();



        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<LoginModel2> call = Api_client.getClient().login_api("7c538f486ea815187d12c54c3646d71e",
                language,
                p_name,
                p_address,
                mobile_number,
                p_id,
                device_token,
                email_strig,countryData,stateData,cityData);

        Log.e("address",p_address+"ok");

        //calling the api
        call.enqueue(new Callback<LoginModel2>() {
            @Override
            public void onResponse(Call<LoginModel2> call, Response<LoginModel2> response)
            {

                progressDialog.dismiss();

                try
                {
                    if (response.isSuccessful())
                    {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True"))
                        {

                            Log.e("flag_5465","API RESPONSE IS SUCCESSFUL");

                           /* List<LoginResponse2> resps= (List<LoginResponse2>) response.body().getData();
                            String user_id= String.valueOf(resps.get(0).getId());
                            String username=resps.get(0).getUserName();
                            String user_email=resps.get(0).getUserEmail();
                            String user_mobile=resps.get(0).getUserMobile();*/

                            LoginModel2 loginModel2 = response.body();
                            LoginResponse2 resps = loginModel2.getData();

//                            Toast.makeText(Login_Activity.this, resps.size()+"", Toast.LENGTH_SHORT).show();
                            String user_id= String.valueOf(resps.getId());
                            String username=resps.getUserName();
                            String user_email=resps.getUserEmail();
                            String id = String.valueOf(resps.getId());
                            String state  = resps.getRname();
                            String user_mobile = resps.getUserMobile();


                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            sharedPreferences = Email_Details_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_name", username);
                            editor.putString("last_name", "");
                            editor.putString("user_profile", "");

                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);

                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Log.e("flag_5465","SOMETHING WENT WRONG...");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                           /* Intent intent=new Intent(getApplicationContext(),Email_Details_Activity.class);
                            startActivity(intent);*/
                            LoginManager.getInstance().logOut();
                            GoogleSignInOptions gso = new GoogleSignInOptions.
                                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                    build();
                            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                            googleSignInClient.signOut();

                            sharedPreferences = Email_Details_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_name", "");
                            editor.putString("last_name", "");
                            editor.putString("user_profile", "");

                            editor.putString("user_email", "");
                            editor.putString("person_id", "");
                            editor.putString("staffStatus", "");
                            editor.putString("user_mobile", "");

                            editor.apply();
                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Email_Details_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Email_Details_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Email_Details_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Email_Details_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Email_Details_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Email_Details_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Email_Details_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Email_Details_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Email_Details_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Email_Details_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<LoginModel2> call, Throwable t)
            {
                progressDialog.dismiss();
                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException)
                {
                    Toast.makeText(Email_Details_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(Email_Details_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }


}