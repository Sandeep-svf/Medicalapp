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
import android.widget.Switch;
import android.widget.Toast;

import com.example.findadoc.Activity.Advance_Search_Data_Activityies;
import com.example.findadoc.Adapter.Category_Adapter;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Advance_Search_Response;
import com.example.findadoc.Model.Med_Category_Model;
import com.example.findadoc.Model.MedicineCategory_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class    Advance_Search extends Fragment {


    Button refund_btn,all_btn,search_btn_Advance;
    Switch best_price;
    EditText keyword,category;
    ImageView clear_image;
    RecyclerView category_recycler;
    String best_price_text="0";
    SharedPreferences sharedPreferences;
    String language="en";
    String refound_status="0";
    String drug_name="";
    private String catName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Category_Adapter symtoms_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_advance__search, container, false);
        refund_btn=v.findViewById(R.id.refund_btn);
        all_btn=v.findViewById(R.id.all_btn);
        best_price=v.findViewById(R.id.best_price);
        keyword=v.findViewById(R.id.keyword);
        category=v.findViewById(R.id.category);
        clear_image=v.findViewById(R.id.clear_image);
        category_recycler=v.findViewById(R.id.category_recycler);
        search_btn_Advance=v.findViewById(R.id.search_btn_Advance);
        sharedPreferences= getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");

        best_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(best_price.isChecked())
            {
                best_price_text="1";
//                Toast.makeText(getActivity(),best_price_text+"A",Toast.LENGTH_LONG).show();

            }
            else
            {
                best_price_text="0";
//                Toast.makeText(getActivity(),best_price_text+"B",Toast.LENGTH_LONG).show();

            }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        category_recycler.setLayoutManager(linearLayoutManager);
        clear_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.setText("");
                category_recycler.setVisibility(View.GONE);
            }
        });

        refund_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refund_btn.setBackgroundResource(R.drawable.buton_background);
                all_btn.setBackgroundResource(R.drawable.button_diable_);
                refound_status="1";
            }
        });
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_btn.setBackgroundResource(R.drawable.buton_background);
                refund_btn.setBackgroundResource(R.drawable.button_diable_);
                refound_status="0";
            }
        });
        get_Category_List_Data();


        return v;
    }

    private void get_Category_List_Data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Med_Category_Model> call = Api_client.getClient().get_category_data("7c538f486ea815187d12c54c3646d71e",language);
        call.enqueue(new Callback<Med_Category_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Med_Category_Model> call, Response<Med_Category_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equalsIgnoreCase("True"))
                        {
                            List<MedicineCategory_Response> symtoms_responses = response.body().getMedicineCategory();
                          symtoms_adapter=new Category_Adapter(symtoms_responses,getActivity(),category,
                                    category_recycler,search_btn_Advance,best_price_text,refound_status);
                            category_recycler.setAdapter(symtoms_adapter);

                            search_btn_Advance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(drug_name.equals(""))
                                    {
                                        keyword.requestFocus();
                                        keyword.setError(getResources().getString(R.string.enter_drugname));
                                    }
                                }
                            });

                            symtoms_adapter.setOnClickListener(new Category_Adapter.OnClickListener() {
                                @Override
                                public void click(String name,String category_data) {
//                                    Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();

                                    catName = name;

                                }
                            });



                            search_btn_Advance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    drug_name=keyword.getText().toString();
                                    if(drug_name.equals(""))
                                    {
                                        keyword.requestFocus();
                                        keyword.setError(getResources().getString(R.string.enter_drugname));
                                    }
                                    else if(catName.equals(""))
                                    {
                                        category.setError(getResources().getString(R.string.please_select));
                                        category.requestFocus();
                                    }
                                    else
                                    {
                                        Advace_Search_Data(catName);
                                    }
                                }
                            });

                            category.addTextChangedListener(new TextWatcher()
                            {
                                public void afterTextChanged(Editable s) {


                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                                {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count)
                                {
                                    symtoms_adapter.setfilter(s.toString().toLowerCase());


                                    if(symtoms_adapter.getItemCount()==0)
                                    {
                                        Log.e("Test","11");
                                        clear_image.setVisibility(View.GONE);
                                        category_recycler.setVisibility(View.GONE);
                                        symtoms_adapter.notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        if(s.toString().equals(""))
                                        {
                                            category_recycler.setVisibility(View.GONE);
//                                    text_no_record.setVisibility(View.GONE);
                                            clear_image.setVisibility(View.GONE);
                                            Log.e("Test","22");
                                        }
                                        else
                                        {                                            category_recycler.setVisibility(View.VISIBLE);
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
                    Log.e("message_e: ", String.valueOf(e));
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Med_Category_Model> call, Throwable t) {
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
    private void Advace_Search_Data(String name) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Log.e("Advace_Data","drug_name: "+drug_name);
        Log.e("Advace_Data","best_price_text: "+best_price_text);
        Log.e("Advace_Data","refound_status: "+refound_status);
        Log.e("Advace_Data","name: "+name);

        //Toast.makeText(getActivity(),"Running ............", Toast.LENGTH_SHORT).show();

        Call<Advance_Search_Model> call = Api_client.getClient().getAdvance_Search("7c538f486ea815187d12c54c3646d71e",
                drug_name,
                language,
                best_price_text,
                name,
                refound_status);
        call.enqueue(new Callback<Advance_Search_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Advance_Search_Model> call, Response<Advance_Search_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            List<Advance_Search_Response> search_responses = response.body().getDrugData();

                            Intent intent = new Intent(getActivity(), Advance_Search_Data_Activityies.class);
                            intent.putExtra("my_list", (Serializable) search_responses);
                            intent.putExtra("best_price",best_price_text);

                            getActivity().startActivity(intent);
                        } else {
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
            public void onFailure(Call<Advance_Search_Model> call, Throwable t) {
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