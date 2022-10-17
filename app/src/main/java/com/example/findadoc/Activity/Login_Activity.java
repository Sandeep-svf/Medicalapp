package com.example.findadoc.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Activity.StaffUserLoginActivity;
import com.example.findadoc.sam.dev.Model.LoginModel2;
import com.example.findadoc.sam.dev.Model.LoginResponse2;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    private static int PRIORITY_HIGH_ACCURACY;
    ImageView image_back;
    CallbackManager callbackManager;
    com.facebook.login.widget.LoginButton fblogin;
    String personGivenName="",personFamilyName="",personImage="",personName="",personEmail="",fbpersonId="",googlepersonId="";
    private String gender;
    private String birthday;
    private String city;
    private String location;
    PackageInfo info;
    private String photo_id;
    SharedPreferences sharedPreferences;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=2001;
    GoogleSignInAccount acct;
    String language="en";
    String persion_id="";
    private String user_mobile="",user_address="";
    private String device_token="";
    CardView staff_user_login;
    private String cityData, countryData, subLocality, stateData;
    GPSTracker gps;
    private String currentlatitude,currentlongtide;
    private double latDouble, longdouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // FacebookSdk.sdkInitialize(Login_Activity.this);

        setContentView(R.layout.activity_login_);
        image_back=findViewById(R.id.image_back_btn);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        CardView staff_user_login = findViewById(R.id.staff_user_login);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");


        // Fetch location.....
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

        staff_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this,StaffUserLoginActivity.class);
                startActivity(intent);
            }
        });

//        Toast.makeText(this, language+"AA", Toast.LENGTH_SHORT).show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            info = getPackageManager().getPackageInfo("com.example.findadoc", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash_key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ritik", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("tokenn","tok"+device_token);
                        Log.e("tokkkk", device_token.length()+"");
//                        Toast.makeText(Login_Activity.this, device_token, Toast.LENGTH_SHORT).show();

//                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
//                                edit.setText(token);
                    }
                });

        findViewById(R.id.sign_in_button).setOnClickListener(Login_Activity.this);


      //  FacebookSdk.sdkInitialize(this.getApplicationContext());


        callbackManager = CallbackManager.Factory.create();
        fblogin=findViewById(R.id.login_button);
        fblogin.setReadPermissions("email","public_profile","user_friends","user_birthday");

        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request =   GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                loginResult.getAccessToken().getPermissions();
                                if (response.getError() != null) {
                                    // handle error

                                } else {

//                                    Toast.makeText(getApplicationContext(),response+"",Toast.LENGTH_LONG).show();

                                    // get email and id of the user
//                                    personId = response.getJSONObject().optString("email");
                                    try {
                                        persion_id = me.getString("id");
                                        personGivenName = me.optString("first_name");
                                        personFamilyName = me.optString("last_name");
                                        personImage = "http://graph.facebook.com/"+persion_id+"/picture?type=large";
                                        gender=me.optString("gender");
                                        personEmail = me.optString("email");
                                       // Log.e("email",email);
                                        birthday=me.optString("birthday");
                                        JSONObject jsonObject1=me.getJSONObject("hometown");
                                        city=  jsonObject1.getString("name");
                                        JSONObject json2=me.getJSONObject("location");
                                        location=  json2.getString("name");

                                        JSONObject photo_object=me.getJSONObject("photos");
                                        JSONArray jsonArray=photo_object.getJSONArray("data");
                                        for(int i=0;i<=jsonArray.length();i++)
                                        {
                                            photo_id=jsonArray.getJSONObject(i).getString("id");
                                        }


                                      /*  if (me.has("email")) {

                                            personEmail = me.getString("email");
//                                            Toast.makeText(getApplicationContext(),"jkjss",Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Login_API();
//                                            Toast.makeText(getApplicationContext(),"jkjss",Toast.LENGTH_LONG).show();
//                                            personEmail=me.getString("id");
                                           *//* Intent intent=new Intent(getApplicationContext(),Sign_Up_Activity.class);
                                            startActivity(intent);*//*
                                        }*/
                                    }catch (Exception e)
                                    {

                                    }

                                    Log.e("tokenn",fbpersonId);
                                    Log.e("tag",personGivenName);
                                    Log.e("tag",personFamilyName);
                                    Log.e("tag",personImage);
                                    Log.e("tag",gender+"");
                                    Log.e("tag",personEmail+"email");
                                    Log.e("tag",birthday+"");
                                    Log.e("tag",city+"");
                                    Log.e("tag",location+"");
                                    Log.e("tag",photo_id+"");
                                    Login_API();
                                    /* Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                   sharedPreferences = Login_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("user_name", personGivenName);
                                    editor.putString("last_name", personFamilyName);
                                    editor.putString("user_profile", personImage);

                                    editor.putString("user_email", personEmail);

                                    editor.apply();*/
                                   /*  Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    SharedPreferences sharedpreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("user_id", "39");
                                    editor.putString("ima",personImage);
                                    editor.apply();
                                    intent.putExtra("terms_key","login_value");
                                    startActivity(intent);
                                    finish();*/



                                }
                            }
                        });

               /* Bundle parameters = new Bundle();
                parameters.putString("fields", "id,last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Insert your code here
                            }
                        });*/

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void get_current_lat_long() {

        gps = new GPSTracker(Login_Activity.this);
        if (gps.canGetLocation()) {
            double latitudes = gps.getLatitude();
            double longitudes = gps.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

            latDouble = latitudes;
            longdouble = longitudes;

            user_address = currentlatitude+","+currentlongtide;
            Log.e("latlong", "latitudes: " + latitudes);
            Log.e("latlong", "longitudes: " + longitudes);
        }
    }

    private void fdfdf() {
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("code",requestCode+":"+RC_SIGN_IN);
        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);
            acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null)
            {
                personName = acct.getDisplayName();
                personGivenName = acct.getGivenName();
                personFamilyName = acct.getFamilyName();
                personEmail = acct.getEmail();
                persion_id = acct.getId();
                personImage= String.valueOf(acct.getPhotoUrl());


               /* String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();*/

                Log.d("photo", String.valueOf(acct.getPhotoUrl()) + personGivenName +personFamilyName);

                Log.e("img", String.valueOf(acct.getPhotoUrl()));
                Log.e("img", String.valueOf(persion_id)+"ID");

                Log.e("img", Resources.getSystem().getConfiguration().locale.getLanguage());

            }
            Login_API();


/*
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            SharedPreferences sharedpreferences = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("user_id", "39");
            editor.apply();
            intent.putExtra("terms_key","login_value");
            intent.putExtra("logout_key","2");

            startActivity(intent);
            finish();
*/
            // Signed in successfully, show authenticated UI.

        }
        catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("Google Sign in error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(),"failed" ,Toast.LENGTH_SHORT).show();
        }
    }
    public  void Login_API()
    {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(Login_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<LoginModel2> call = Api_client.getClient().login_api("7c538f486ea815187d12c54c3646d71e",
                language,
                personGivenName,
                user_address,
                user_mobile,
                persion_id,
                device_token,
                personEmail,countryData,stateData,cityData);

        Log.e("address_data",countryData+stateData+cityData);

        //calling the api
        call.enqueue(new Callback<LoginModel2>() {
            @Override
            public void onResponse(Call<LoginModel2> call, retrofit2.Response<LoginModel2> response)
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

                           // List<LoginResponse2> resps= (List<LoginResponse2>) response.body().getData();

                            LoginModel2 loginModel2 = response.body();
                            LoginResponse2 resps = loginModel2.getData();

//                            Toast.makeText(Login_Activity.this, resps.size()+"", Toast.LENGTH_SHORT).show();
                            String user_id= String.valueOf(resps.getId());
                            String username=resps.getUserName();
                            String user_email=resps.getUserEmail();
                            String id = String.valueOf(resps.getId());
                            String state  = resps.getRname();
                            String userType = String.valueOf(resps.getUserType());

                            Log.e("userType",userType);


                            Log.e("print_user_data",user_id+username+user_email+id+state);
//                            Toast.makeText(Login_Activity.this, user_id+"A", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(Login_Activity.this, username+"B", Toast.LENGTH_SHORT).show();
                            sharedPreferences = Login_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("id", id);
                            editor.putString("user_name", username);
                            editor.putString("last_name", personFamilyName);
                            editor.putString("user_profile", personImage);

                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);
                            editor.putString("userType",userType);

                            editor.apply();
                          //  Toast.makeText(Login_Activity.this, "working fine ....", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            String errorType = response.body().getGetErrortype();
                            Log.e("errorType",errorType+"Ok");
                            if(errorType.equals("0"))
                            {
                                Intent intent=new Intent(getApplicationContext(),Email_Details_Activity.class);
                                intent.putExtra("p_id",persion_id);
                                intent.putExtra("p_name",personGivenName);
                                intent.putExtra("p_token",device_token);
                                intent.putExtra("p_mobile",user_mobile);
                                intent.putExtra("address",user_address);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                LoginManager.getInstance().logOut();
                                GoogleSignInOptions gso = new GoogleSignInOptions.
                                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                        build();
                                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                                googleSignInClient.signOut();

                                sharedPreferences = Login_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
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

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Login_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Login_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Login_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Login_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Login_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Login_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Login_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Login_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Login_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Login_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Login_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion issue",t.getMessage());
                    Toast.makeText(Login_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }

}