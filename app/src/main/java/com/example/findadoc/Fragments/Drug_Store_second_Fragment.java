package com.example.findadoc.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.findadoc.Activity.Hospital_Data_Activities;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.Model.City;
import com.example.findadoc.Model.City_main;
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


public class Drug_Store_second_Fragment extends Fragment {



    double longitudes,latitudes;
    String currentlatitude="", currentlongtide="";
    Button search_button;
    Switch around_switch;
    SearchableSpinner city_spinner;
    GPSTracker gps;
    List<City> cityList=new ArrayList<>();
    List<String> city_name_list=new ArrayList<>();
    SharedPreferences sharedPreferences;
    String language="en";
    private String sp_id="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences=getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        View v= inflater.inflate(R.layout.fragment_drug__store_second_, container, false);

        search_button=v.findViewById(R.id.search_button);
        around_switch=v.findViewById(R.id.around_switch);
        city_spinner=v.findViewById(R.id.city_spinner);
        around_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(around_switch.isChecked())
                {
                    gps = new GPSTracker(getActivity());
                    if (gps.canGetLocation())
                    {
                        latitudes = gps.getLatitude();
                        longitudes = gps.getLongitude();

                        currentlatitude = String.valueOf(latitudes);
                        currentlongtide = String.valueOf(longitudes);

                    }
                    else
                    {
                        gps.showSettingsAlert(around_switch);
                    }
                    city_spinner.setSelection(0);
                    sp_id="";
//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();




                }
                else
                {


                    currentlatitude = "";
                    currentlongtide ="";

//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();

                }

            }
        });
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = city_spinner.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_city)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }
                else
                {
                    sp_id = String.valueOf(cityList.get(position).getMunicipalId());
                    Log.e("LIST_ID",sp_id+"ID");
                    around_switch.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(around_switch.isChecked())
                {
                    gps = new GPSTracker(getActivity());
                    if (gps.canGetLocation())
                    {
                        latitudes = gps.getLatitude();
                        longitudes = gps.getLongitude();

                        currentlatitude = String.valueOf(latitudes);
                        currentlongtide = String.valueOf(longitudes);

                    }
//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();


                }
                else
                {
                    currentlatitude = "";
                    currentlongtide ="";

//                    Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();

                }



                Intent intent=new Intent(getActivity(), Hospital_Data_Activities.class);
                intent.putExtra("sp_id",sp_id);
                intent.putExtra("lat",currentlatitude);
                intent.putExtra("long",currentlongtide);
                intent.putExtra("recomend","");
                intent.putExtra("search","");
                intent.putExtra("specialist_id","");
                intent.putExtra("filter_key","10");
                startActivity(intent);

            }
        });
        getCity();
        return v;
    }
    private void getCity() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<City_main> call = Api_client.getClient().get_city("7c538f486ea815187d12c54c3646d71e",language);
        call.enqueue(new Callback<City_main>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<City_main> call, Response<City_main> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True"))
                        {
                            cityList=response.body().getCity();
                            City city_res =new City();

                            city_res.setMunicipalName(getResources().getString(R.string.select_city));
                            city_res.setMunicipalId(0);
                            cityList.add(0,city_res);
                            for (int j=0;j<cityList.size();j++)
                            {
                                String category_name = cityList.get(j).getMunicipalName();
                                if(category_name!=null)
                                {
                                    city_name_list.add(category_name);
                                }

                                Log.e("LIST",city_name_list.get(j)+"S");
                            }
                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(city_name_list);
                            dAdapter.add(getResources().getString(R.string.select_city));
                            city_spinner.setAdapter(dAdapter);
                            city_spinner.setSelection(dAdapter.getCount());


//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
/*                            CustomAdapter customAdapter=new CustomAdapter(getActivity(),cityList);
                            city_spinner.setAdapter(customAdapter);

                            City city_res =new City();

                            city_res.setMunicipalName(getResources().getString(R.string.select_city));
                            city_res.setMunicipalId(0);
                            cityList.add(0,city_res);
                            city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    around_switch.setChecked(false);
                                    sp_id= String.valueOf(cityList.get(position).getMunicipalId());
                                    String cities_name=cityList.get(position).getMunicipalName();

                                    ((TextView)view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });*/

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
            public void onFailure(Call<City_main> call, Throwable t) {
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

}