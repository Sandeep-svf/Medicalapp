package com.example.findadoc.Activity;

import static com.example.findadoc.Retrofir.Api_client.BASE_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.StaffProfileDetailsModel;
import com.example.findadoc.sam.dev.Model.StaffProfileDetailsResult;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class User_Profile_Activity extends AppCompatActivity {
    RelativeLayout privacy_policies_layout,about_us_relative,term_condition_layout;
    RelativeLayout relatice_back_change_password;
    TextView share_btn;
    Button login_text;

    SharedPreferences sharedPreferences;
    CircleImageView profile_image;
    RelativeLayout logout_layout,personal_info_layout,our_partner_layout;
    private String user_id, language,staffStatus, photo2;
    String photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile_);
        sharedPreferences = User_Profile_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        String user_name=sharedPreferences.getString("user_name","");
        photo=sharedPreferences.getString("user_profile","");
        String family_name=sharedPreferences.getString("last_name","");
        user_id= sharedPreferences.getString("person_id","");
        language = sharedPreferences.getString("language_text", "en");
        staffStatus=sharedPreferences.getString("staffStatus","");


        Log.e("user_name",user_name);
        Log.e("user_name",family_name);



        privacy_policies_layout=findViewById(R.id.privacy_policies_layout);
        term_condition_layout=findViewById(R.id.term_condition_layout);
        about_us_relative=findViewById(R.id.about_us_relative);

        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        login_text=findViewById(R.id.login_text);
        share_btn=findViewById(R.id.share_btn);
        profile_image=findViewById(R.id.profile_image);
        logout_layout=findViewById(R.id.logout_layout);
        personal_info_layout=findViewById(R.id.personal_info_layout);
        our_partner_layout=findViewById(R.id.our_partner_layout);


        // #########################################################
       // Toast.makeText(User_Profile_Activity.this, staffStatus, Toast.LENGTH_SHORT).show();
        if(staffStatus.equals("1"))
        {
            staff_profile_details_api();
        }
        // #########################################################


        about_us_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Web_View_Activity.class);
                intent.putExtra("key","3");
                startActivity(intent);
            }
        });

        term_condition_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Web_View_Activity.class);
                intent.putExtra("key","1");
                startActivity(intent);
            }
        });
        privacy_policies_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Web_View_Activity.class);
                intent.putExtra("key","2");
                startActivity(intent);
            }
        });



        if(!user_name.equals(""))
        {


            login_text.setText(user_name+" "+family_name);

            personal_info_layout.setVisibility(View.VISIBLE);
           // logout_layout.setVisibility(View.VISIBLE);
            logout_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logOut();
                    GoogleSignInOptions gso = new GoogleSignInOptions.
                            Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                            build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                    googleSignInClient.signOut();

                    sharedPreferences = User_Profile_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
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
            });

        }
        else
        {
            personal_info_layout.setVisibility(View.GONE);
            logout_layout.setVisibility(View.GONE);
            login_text.setText(getResources().getString(R.string.sign_up_sign_in));
            login_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(User_Profile_Activity.this,Login_Activity.class);
                    startActivity(intent);

                }
            });
        }
        our_partner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Sponsers_Activity.class);
                startActivity(intent);
            }
        });

        personal_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Profile_Detail_Activity.class);
                startActivity(intent);
            }
        });

        if(staffStatus.equals("1"))
        {
            Glide.with(User_Profile_Activity.this).load(photo2).into(profile_image);
           // Toast.makeText(User_Profile_Activity.this, "Running /././././.", Toast.LENGTH_SHORT).show();
        }else
        {
            if(photo.equals(""))
            {
                Glide.with(getApplicationContext()).load(R.drawable.save).into(profile_image);
            }
            else
            {
                Glide.with(getApplicationContext()).load(photo).into(profile_image);
            }
        }







        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl();
            }
        });
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void staff_profile_details_api() {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(User_Profile_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        Log.e("user_id",user_id);

       // Toast.makeText(User_Profile_Activity.this, "staff_profile_details_api Running **********", Toast.LENGTH_SHORT).show();

        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<StaffProfileDetailsModel> call = Api_client.getClient().staffProfileDetails("7c538f486ea815187d12c54c3646d71e",
                language,
                user_id);

        //calling the api
        call.enqueue(new Callback<StaffProfileDetailsModel>() {
            @Override
            public void onResponse(Call<StaffProfileDetailsModel> call, retrofit2.Response<StaffProfileDetailsModel> response)
            {

                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(), response.code()+"", Toast.LENGTH_LONG).show();

                try
                {
                    if (response.isSuccessful())
                    {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True"))
                        {
                            List<StaffProfileDetailsResult> resps=response.body().getData();
                            String user_id= String.valueOf(resps.get(0).getId());
                            String username=resps.get(0).getName();
                            String user_email=resps.get(0).getEmail();
                            String user_mobile= String.valueOf(resps.get(0).getMobile());
                            String user_imnage= BASE_IMAGE+resps.get(0).getStaffImage();

                            photo2 = user_imnage;
                            Glide.with(User_Profile_Activity.this).load(photo2).into(profile_image);

                           // Toast.makeText(getApplicationContext(), photo2, Toast.LENGTH_LONG).show();


                            sharedPreferences = User_Profile_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_name", username);
                            editor.putString("last_name", "");
                            editor.putString("user_profile", user_imnage);

                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);

                            editor.apply();

                            //Toast.makeText(Profile_Detail_Activity.this,user_imnage , Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(User_Profile_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(User_Profile_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(User_Profile_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(User_Profile_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(User_Profile_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(User_Profile_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(User_Profile_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(User_Profile_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(User_Profile_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(User_Profile_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<StaffProfileDetailsModel> call, Throwable t)
            {
                progressDialog.dismiss();
                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException)
                {
                    Toast.makeText(User_Profile_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion_issue",t.getMessage());
                    Toast.makeText(User_Profile_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }


    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
        finish();

    }
}