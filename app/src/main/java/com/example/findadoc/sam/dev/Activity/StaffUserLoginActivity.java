package com.example.findadoc.sam.dev.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Adapter.Clicnic_Fav_Adapter;
import com.example.findadoc.Adapter.Fav_Model;
import com.example.findadoc.Adapter.Fav_Response;
import com.example.findadoc.Fragments.Clinic_Fav_Fragment;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.StaffLoginModel;
import com.example.findadoc.sam.dev.Model.StaffLoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffUserLoginActivity extends AppCompatActivity {

    AppCompatImageView back_image_layout;
    AppCompatEditText staff_user_id, staff_password_id;
    AppCompatButton staff_user_login_layout;
    private  String staffUserIdData, staffUserPasswordData, language, device_token,userStatus;
    SharedPreferences sharedPreferences;
    List<StaffLoginResult> staffLoginResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_user_login);

        inits();

        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");

        Log.e("language", "language: "+language);


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

//                        Toast.makeText(Login_Activity.this, device_token, Toast.LENGTH_SHORT).show();

//                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
//                                edit.setText(token);
                    }
                });


        staff_user_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_user_form_data();
                if(validation())
                {
                    // Staff User Login API
                    login_api();
                }
            }
        });



        back_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void login_api() {
        final ProgressDialog pd = new ProgressDialog(StaffUserLoginActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<StaffLoginModel> call = Api_client.getClient().staffLogin( "7c538f486ea815187d12c54c3646d71e",
                staffUserIdData,
                staffUserPasswordData,
                language,
                device_token);
        call.enqueue(new Callback<StaffLoginModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<StaffLoginModel> call, Response<StaffLoginModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                         userStatus = response.body().getUsertpye();


                        if (success.equals("true") || success.equals("True"))
                        {


                           StaffLoginModel staffLoginModel = response.body();
                           StaffLoginResult staffLoginResult = staffLoginModel.getStaffLoginResult();


                            String staffStatus = String.valueOf( staffLoginResult.getStaffStatus());
                            String user_id = String.valueOf(staffLoginResult.getId());
                            String user_email = String.valueOf(staffLoginResult.getEmail());
                            String user_mobile = String.valueOf(staffLoginResult.getMobile());
                            String personFamilyName = String.valueOf(staffLoginResult.getName());
                            String locationStatus = String.valueOf(staffLoginResult.getLocation());

                            Log.e("Staff_user_data","staffStatus: "+staffStatus);
                            Log.e("Staff_user_data","user_id: "+user_id);
                            Log.e("Staff_user_data","user_email: "+user_email);
                            Log.e("Staff_user_data","user_mobile: "+user_mobile);
                            Log.e("Staff_user_data","personFamilyName: "+personFamilyName);
                            Log.e("Staff_user_data","locationStatus: "+locationStatus);

                            sharedPreferences = StaffUserLoginActivity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("staffStatus", staffStatus);
                            editor.putString("userStatus254", userStatus);
                            editor.putString("user_name", personFamilyName);
                          //  editor.putString("user_profile", personImage);
                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);
                            editor.putString("locationStatus",locationStatus);
                           // intent.putExtra("p_token",device_token);
                            editor.apply();
                          //   Toast.makeText(StaffUserLoginActivity.this, "working fine ....", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(StaffUserLoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            Toast.makeText(StaffUserLoginActivity.this, message, Toast.LENGTH_SHORT).show();

                        }else {
                            pd.dismiss();
                            Toast.makeText(StaffUserLoginActivity.this, message, Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<StaffLoginModel> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    private boolean validation() {
        if(staffUserIdData.equals(""))
        {
            Toast.makeText(StaffUserLoginActivity.this, "Please enter user id", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(staffUserPasswordData.equals(""))
        {
            Toast.makeText(StaffUserLoginActivity.this, "Please enter user password", Toast.LENGTH_SHORT).show();
            return  false;
        }
            return  true;
    }

    private void get_user_form_data() {
        staffUserIdData = staff_user_id.getText().toString();
        staffUserPasswordData = staff_password_id.getText().toString();
    }

    private void inits() {
        back_image_layout = findViewById(R.id.back_image_layout);
        staff_user_id = findViewById(R.id.staff_user_id);
        staff_password_id = findViewById(R.id.staff_password_id);
        staff_user_login_layout = findViewById(R.id.staff_user_login_layout);
    }
}