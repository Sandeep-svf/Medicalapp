package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Add_New_Prescription_Activity;
import com.example.findadoc.Adapter.Prescription_Adapter;
import com.example.findadoc.Model.City;
import com.example.findadoc.Model.City_main;
import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.Model.Pres_Response;
import com.example.findadoc.Model.Prescription_Data_Model;
import com.example.findadoc.Model.Prescription_Data_response;
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


public class Prescription_Fragment extends Fragment {

    CardView card_view_prescription;
    TextView prescription_content,disease_name;
    SearchableSpinner category;
    SharedPreferences sharedPreferences;
    String language="en";
    RelativeLayout pres_layout;
    List<Pres_Response> dosease_list=new ArrayList<>();
    List<String> diseases_name=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_prescription_, container, false);
        sharedPreferences=getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        pres_layout=v.findViewById(R.id.pres_layout);
        card_view_prescription=v.findViewById(R.id.card_view_prescription);
        prescription_content=v.findViewById(R.id.prescription_content);
        disease_name=v.findViewById(R.id.disease_name);
        category=v.findViewById(R.id.category);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = category.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_disease)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }
                else
                {
                    String sp_id = String.valueOf(dosease_list.get(position).getPreId());
                    Log.e("LIST_ID",sp_id+"ID");
                    get_prescription(sp_id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
       /* prescription_recycler.setLayoutManager(linearLayoutManager);
        Prescription_Adapter prescription_adapter=new Prescription_Adapter(getActivity());
        prescription_recycler.setAdapter(prescription_adapter);
        pres_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Add_New_Prescription_Activity.class);
                startActivity(intent);
            }
        });*/
        pres_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(getActivity(),Add_New_Prescription_Activity.class);
            startActivity(intent);
            }
        });
        get_disease();
        return v;
    }

    private void get_prescription(String sp_id) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Prescription_Data_Model> call = Api_client.getClient().get_prescription("7c538f486ea815187d12c54c3646d71e",
                language,
                sp_id);
        call.enqueue(new Callback<Prescription_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Prescription_Data_Model> call, Response<Prescription_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))
                        {
                                Prescription_Data_response prescription_data_response=response.body().getData();
                                card_view_prescription.setVisibility(View.VISIBLE);
                              String diseasename=  prescription_data_response.getDisease();
                              String prescription= prescription_data_response.getPrescription();
                              disease_name.setText(diseasename);
                              prescription_content.setText(Html.fromHtml(prescription));
                        }
                            else {
                            card_view_prescription.setVisibility(View.GONE);
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
            public void onFailure(Call<Prescription_Data_Model> call, Throwable t) {
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

    private void get_disease() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pres_Model> call = Api_client.getClient().get_disease("7c538f486ea815187d12c54c3646d71e",
                language);
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
                            dosease_list=response.body().getData();
                            Pres_Response pres_response =new Pres_Response();
                            List<String> diseases_name=new ArrayList<>();
                            pres_response.setDisease(getResources().getString(R.string.select_diseases));
                            pres_response.setPreId(0);
                            dosease_list.add(0,pres_response);
                            for (int j=0;j<dosease_list.size();j++)
                            {
                                String category_name = dosease_list.get(j).getDisease();
                                if(category_name!=null)
                                {
                                    diseases_name.add(category_name);
                                }
                                Log.e("LIST",diseases_name.get(j)+"S");

                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, diseases_name);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(diseases_name);
                            dAdapter.add(getResources().getString(R.string.select_disease));
                            category.setAdapter(dAdapter);
                            category.setSelection(dAdapter.getCount());

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
            public void onFailure(Call<Pres_Model> call, Throwable t) {
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

    @Override
    public void onResume() {
        super.onResume();
        get_disease();
    }
}