package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Adapter.Notification_Adapter;
import com.example.findadoc.Adapter.Specilist_Adapter;
import com.example.findadoc.MainActivity;
import com.example.findadoc.Model.ClinicSpecialty;
import com.example.findadoc.Model.Clinic_Specialist_Model;
import com.example.findadoc.Model.Count_Model;
import com.example.findadoc.Model.Notifiation_Model;
import com.example.findadoc.Model.NotificationRS;
import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notification_Fragments extends Fragment {

        RecyclerView notification_recycelr;
        SharedPreferences sharedPreferences;
        String user_id="";
        LinearLayout layout_doctor;
        TextView clear_btn;
    private String language;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_notification__fragments, container, false);
        notification_recycelr=v.findViewById(R.id.notification_recycelr);
        layout_doctor=v.findViewById(R.id.layout_doctor);
        clear_btn=v.findViewById(R.id.clear_btn);
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("person_id","");
        language = sharedPreferences.getString("language_text", "en");

       // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        notification_recycelr.setLayoutManager(linearLayoutManager);


        if (user_id.equals(""))
        {
            notification_recycelr.setVisibility(View.GONE);
            layout_doctor.setVisibility(View.VISIBLE);
            clear_btn.setVisibility(View.GONE);
        }
        else
        {
            notification_recycelr.setVisibility(View.VISIBLE);
            layout_doctor.setVisibility(View.GONE);
            clear_btn.setVisibility(View.VISIBLE);
            Notification_API();
        }



        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification_delete_api();
            }
        });
        return v;
    }

    private void Notification_API() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Notifiation_Model> call = Api_client.getClient().notification_data("7c538f486ea815187d12c54c3646d71e",
                user_id);
        call.enqueue(new Callback<Notifiation_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Notifiation_Model> call, Response<Notifiation_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True"))
                        {
                          //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            List<NotificationRS> notificationRS=response.body().getData();

                            if (notificationRS.size()==0)
                            {
                                notification_recycelr.setVisibility(View.GONE);
                                layout_doctor.setVisibility(View.VISIBLE);
                                clear_btn.setVisibility(View.GONE);
                            }
                            else
                            {
                                notification_recycelr.setVisibility(View.VISIBLE);
                                layout_doctor.setVisibility(View.GONE);
                                clear_btn.setVisibility(View.VISIBLE);
                                Notification_Adapter notification_adapter=new Notification_Adapter(getActivity(),notificationRS,language);
                                notification_recycelr.setAdapter(notification_adapter);
                            }


                        }else {
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
                    Log.e("e", String.valueOf(e));
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Notifiation_Model> call, Throwable t) {
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
    private void notification_delete_api() {

        Call<Pres_Model> call = Api_client.getClient().notification_delete_All("7c538f486ea815187d12c54c3646d71e",
                user_id,language);
        call.enqueue(new Callback<Pres_Model>() {
            @Override
            public void onResponse(Call<Pres_Model> call, Response<Pres_Model> response) {

                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            notificauion_coun();
                            Notification_API();

                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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

                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    private void notificauion_coun() {



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
                            MainActivity.text_notification.setText(count);


                        }

                        else {

//                            Toast.makeText(getApplicationContext(), message+"B", Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<Count_Model> call, Throwable t) {
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