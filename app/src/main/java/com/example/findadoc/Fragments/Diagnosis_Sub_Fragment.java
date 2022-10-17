package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findadoc.Activity.Diagnosis_Activity_;
import com.example.findadoc.Adapter.Symtoms_Adapter;
import com.example.findadoc.Model.Symtoms_Model;
import com.example.findadoc.Model.Symtoms_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Diagnosis_Sub_Fragment extends Fragment {

    SharedPreferences sharedPreferences;
    public static RecyclerView list_recycler;
    Button daignosis_search_btn;
    private String language="en";
    EditText symtoms_edit;
    ImageView clear_image;
    Symtoms_Adapter symtoms_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_diagnosis__sub_, container, false);
        daignosis_search_btn=v.findViewById(R.id.daignosis_search_btn);
        list_recycler=v.findViewById(R.id.list_recycler);
            sharedPreferences= getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
            language=sharedPreferences.getString("language_text","en");
        symtoms_edit=v.findViewById(R.id.symtoms_edit);
        clear_image=v.findViewById(R.id.clear_image);
        daignosis_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Diagnosis_Activity_.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        list_recycler.setLayoutManager(linearLayoutManager);
        clear_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symtoms_edit.setText("");
                list_recycler.setVisibility(View.GONE);
            }
        });
        try
        {

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        get_List_Symtoms_API();
        return v;
    }

    private void get_List_Symtoms_API() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Symtoms_Model> call = Api_client.getClient().getsymtoms_list("7c538f486ea815187d12c54c3646d71e",language);
        call.enqueue(new Callback<Symtoms_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Symtoms_Model> call, Response<Symtoms_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True"))
                        {
                            List<Symtoms_Response> symtoms_responses = response.body().getData();
                             symtoms_adapter=new Symtoms_Adapter(symtoms_responses,getActivity(),symtoms_edit,
                                     list_recycler,daignosis_search_btn);
                            list_recycler.setAdapter(symtoms_adapter);

                            symtoms_edit.addTextChangedListener(new TextWatcher()
                            {
                                public void afterTextChanged(Editable s) {


                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                                { }

                                public void onTextChanged(CharSequence s, int start, int before, int count)
                                {
                                    symtoms_adapter.setfilter(s.toString().toLowerCase());


                                    if(symtoms_adapter.getItemCount()==0)
                                    {
                                        Log.e("Test","11");
                                        clear_image.setVisibility(View.GONE);
                                        list_recycler.setVisibility(View.GONE);
                                        symtoms_adapter.notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        if(s.toString().equals(""))
                                        {
                                            list_recycler.setVisibility(View.GONE);
//                                    text_no_record.setVisibility(View.GONE);
                                            clear_image.setVisibility(View.GONE);
                                            Log.e("Test","22");
                                        }
                                        else
                                        {                                            list_recycler.setVisibility(View.VISIBLE);
                                            clear_image.setVisibility(View.VISIBLE);

//                                    text_no_record.setVisibility(View.GONE);
                                            symtoms_adapter.notifyDataSetChanged();
                                            Log.e("Test","33");
                                        }
                                    }
                                }
                /*{
                    if(s.toString().equals(""))
                    {
                        list_recycler.setVisibility(View.GONE);
                        clear_image.setVisibility(View.GONE);
                        symtoms_edit.requestFocus();
                        Log.e("Test","44");

                        if (symtoms_adapter == null || symtoms_adapter.equals("")) {
                        } else {
                            list_recycler.setVisibility(View.GONE);
                            clear_image.setVisibility(View.GONE);
                            symtoms_responses.clear();
//                           get_List_Symtoms_API();
                            symtoms_adapter.notifyDataSetChanged();
                            Log.e("Test", "55");
                        }


                    }
                    else
                    {
                        list_recycler.setVisibility(View.VISIBLE);
                        clear_image.setVisibility(View.VISIBLE);
                        Log.e("Test","66");
                    }
                }*/
                            });
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
            public void onFailure(Call<Symtoms_Model> call, Throwable t) {
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
}