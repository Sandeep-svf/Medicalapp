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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.findadoc.Adapter.Fav_Model;
import com.example.findadoc.Adapter.Fav_Response;
import com.example.findadoc.Adapter.Hospital_Fav_Adapter;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Hospital_Fav_Fragment extends Fragment {
    ImageView no_Result_icon;
    RecyclerView fav_hospital_recycler;
    SharedPreferences sharedPreferences;
    String language="en";
    String user_id="";
    LinearLayout layout_hospital;
    Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_hospital__fav_, container, false);
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("person_id","");
//        Toast.makeText(getActivity(), user_id+"", Toast.LENGTH_SHORT).show();
        sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
        fav_hospital_recycler=v.findViewById(R.id.fav_hospital_recycler);
        layout_hospital=v.findViewById(R.id.layout_hospital);
        no_Result_icon=v.findViewById(R.id.no_Result_icon);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        fav_hospital_recycler.setLayoutManager(linearLayoutManager);
        getFav_API_Data();
        return v;
    }

    public void getFav_API_Data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("user_id","user_id: "+user_id);

        Call<Fav_Model> call = Api_client.getClient().get_favourite_data( "7c538f486ea815187d12c54c3646d71e",
                language,
                user_id,
                "1");
        call.enqueue(new Callback<Fav_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Fav_Model> call, Response<Fav_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            List<Fav_Response> fav_responses=response.body().getData();
//                            Toast.makeText(getActivity(),fav_responses.size()+"A",Toast.LENGTH_LONG).show();

                                fav_hospital_recycler.setVisibility(View.VISIBLE);
                                layout_hospital.setVisibility(View.GONE);
                                Hospital_Fav_Adapter hospital_fav_adapter=new Hospital_Fav_Adapter(getActivity(),fav_responses,Hospital_Fav_Fragment.this);
                                fav_hospital_recycler.setAdapter(hospital_fav_adapter);

                        }else {
//                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            fav_hospital_recycler.setVisibility(View.GONE);
                            layout_hospital.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<Fav_Model> call, Throwable t) {
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

    @Override
    public void onResume() {
        super.onResume();
        getFav_API_Data();
    }
}