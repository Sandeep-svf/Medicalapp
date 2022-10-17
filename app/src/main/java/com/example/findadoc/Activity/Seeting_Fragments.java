package com.example.findadoc.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.MainActivity;
import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Seeting_Fragments extends Fragment {


    RadioGroup language_group;
    RadioButton french_lang,english_lang,arbic_lang;
    Locale locale2;
    private String str_lanuage="1";
    TextView text_noti,text_language;
    SharedPreferences sharedPreferences;
    private String status="1";
    private String shared_status="1";
    Switch switch_recommend;
    private String device_token="";
    private String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_seeting__fragments, container, false);

        language_group=v.findViewById(R.id.language_group);
        french_lang=v.findViewById(R.id.french_lang);
        english_lang=v.findViewById(R.id.english_lang);
        arbic_lang=v.findViewById(R.id.arbic_lang);
        text_noti=v.findViewById(R.id.text);
        text_language=v.findViewById(R.id.text_language);
        switch_recommend=v.findViewById(R.id.switch_rec);

         sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        str_lanuage=sharedPreferences.getString("language_id","");
//        Toast.makeText(getActivity(), str_lanuage+"", Toast.LENGTH_SHORT).show();
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        shared_status=sharedPreferences.getString("notistatus","");
        user_id=sharedPreferences.getString("person_id","");


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
                        Log.e("tokenn",""+device_token);
                        Log.e("tokkkk", device_token.length()+"");
//                        Toast.makeText(Sign_In_Activity.this, device_token, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
//                                edit.setText(token);
                    }
                });

        switch_recommend.setChecked(true);
        if (shared_status.equals("1"))
        {
            switch_recommend.setChecked(true);


        }
        else if (shared_status.equals("0"))
        {
            switch_recommend.setChecked(false);

        }

        switch_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_recommend.isChecked())
                {

                    status="1";
                    sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("notistatus",status);
                    editor.apply();

                    noti_change_api(device_token);
                }
                else
                {
                    status="0";
                    sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("notistatus",status);
                    noti_change_api("1");
                    editor.apply();

                }
            }
        });

        if(str_lanuage.equals("1"))
        {
            english_lang.setChecked(true);
            french_lang.setChecked(false);
            arbic_lang.setChecked(false);
        }
        else if(str_lanuage.equals("2"))
        {
            french_lang.setChecked(true);
            english_lang.setChecked(false);
            arbic_lang.setChecked(false);
        }
        else if(str_lanuage.equals("3"))
        {
            arbic_lang.setChecked(true);
            french_lang.setChecked(false);
            english_lang.setChecked(false);

        }
        else
        {
            english_lang.setChecked(true);
            french_lang.setChecked(false);
            arbic_lang.setChecked(false);
        }

        english_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = language_group.getCheckedRadioButtonId();
                english_lang = (RadioButton)v.findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {


                    locale2 = new Locale("en");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getActivity().getBaseContext().getResources().updateConfiguration(config2, getActivity().getBaseContext().getResources().getDisplayMetrics());

                    text_noti.setText(getResources().getString(R.string.daily_notification));

                    text_language.setText(getResources().getString(R.string.languages));
                    arbic_lang.setText(getResources().getString(R.string.arabic));

                    english_lang.setText(getResources().getString(R.string.english));
                    french_lang.setText(getResources().getString(R.string.french));
                   String str_lanuage = "1";
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language_text", "en");
                    editor.putString("language_id", str_lanuage);
                    editor.apply();

                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        french_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = language_group.getCheckedRadioButtonId();
                french_lang = (RadioButton)v.findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {


                    locale2 = new Locale("fr");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getActivity().getBaseContext().getResources().updateConfiguration(config2, getActivity().getBaseContext().getResources().getDisplayMetrics());


                    text_noti.setText(getResources().getString(R.string.daily_notification));

                    text_language.setText(getResources().getString(R.string.languages));
                    arbic_lang.setText(getResources().getString(R.string.arabic));
                   String str_lanuage = "2";
                    english_lang.setText(getResources().getString(R.string.english));
                    french_lang.setText(getResources().getString(R.string.french));
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language_text", "fr");
                    editor.putString("language_id", str_lanuage);
                    editor.apply();


                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });
        arbic_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = language_group.getCheckedRadioButtonId();
                arbic_lang = (RadioButton)v.findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {


                    locale2 = new Locale("ar");
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getActivity().getBaseContext().getResources().updateConfiguration(config2, getActivity().getBaseContext().getResources().getDisplayMetrics());

                    text_noti.setText(getResources().getString(R.string.daily_notification));

                    text_language.setText(getResources().getString(R.string.languages));
                    arbic_lang.setText(getResources().getString(R.string.arabic));

                    english_lang.setText(getResources().getString(R.string.english));
                    french_lang.setText(getResources().getString(R.string.french));
                   String  str_lanuage = "3";
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language_text", "ar");
                    editor.putString("language_id", str_lanuage);
                    editor.apply();

                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });


        return v;


    }

    private void noti_change_api(String token) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pres_Model> call = Api_client.getClient().noti_disable("7c538f486ea815187d12c54c3646d71e",str_lanuage,user_id,token);
        call.enqueue(new Callback<Pres_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Pres_Model> call, Response<Pres_Model> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        }
                        else {
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
            public void onFailure(Call<Pres_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }
            }
        });


    }

}