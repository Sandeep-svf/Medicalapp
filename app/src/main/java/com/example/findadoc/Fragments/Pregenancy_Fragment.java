package com.example.findadoc.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Activity.Hospital_Data_Activities;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Week_Model;
import com.example.findadoc.Model.Week_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Pregenancy_Fragment extends Fragment {




    EditText height_text,weight_before_preg,cur_weight,week;
    Button cpmpute_btn;
    String height="",weight_before="",current_Weit="",week_str="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        String language=sharedPreferences.getString("language_text","en");


        View v= inflater.inflate(R.layout.fragment_pregenancy_, container, false);
        height_text=v.findViewById(R.id.height_text);
        weight_before_preg=v.findViewById(R.id.weight_before_preg);
        cur_weight=v.findViewById(R.id.cur_weight);
        week=v.findViewById(R.id.week);
        cpmpute_btn=v.findViewById(R.id.cpmpute_btn);
        cpmpute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        return v;
    }

    private void validation() {
        String regex = "^\\d{1,3}$";

        height = height_text.getText().toString();
        weight_before = weight_before_preg.getText().toString();
        week_str = week.getText().toString();
        current_Weit = cur_weight.getText().toString();
        if (height.equals(""))
        {
            height_text.setError(getString(R.string.height));
            height_text.requestFocus();
        }else if((!validatePhone(height_text.getText().toString())))
        {
            height_text.setError("Please enter three digit number only");
            height_text.requestFocus();
        }
      else   if (weight_before.equals("") || !validatePhone(weight_before_preg.getText().toString()))
        {
            weight_before_preg.setError(getString(R.string.bef_weight));
            weight_before_preg.requestFocus();
        }
       else if (week_str.equals("")|| !validatePhone(week.getText().toString()))
        {
            week.setError(getString(R.string.week));
            week.requestFocus();
        }
       else if (current_Weit.equals("") || !validatePhone(cur_weight.getText().toString()))
        {
            cur_weight.setError(getString(R.string.cur_weight));
            cur_weight.requestFocus();
        }
       else
        {
            get_week_data();

        }
    }

    private void get_week_data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("test",week_str);

        Call<Week_Model> call = Api_client.getClient().get_week_Data(week_str,
                "7c538f486ea815187d12c54c3646d71e");
        call.enqueue(new Callback<Week_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Week_Model> call, Response<Week_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());



                        if (success.equals("true") || success.equals("True"))
                        {
                            Log.e("test","success");
                            List<Week_Response> week_responses=response.body().getData();
                            String weight_range=week_responses.get(0).getRecommendedWeightGain();
                            String[] weight_min_max=weight_range.split("-");
                            float weight_min= Float.parseFloat(weight_min_max[0]);
                            float weight_max= Float.parseFloat(weight_min_max[1]);
//                            Toast.makeText(getActivity(), weight_min+" "+weight_max, Toast.LENGTH_LONG).show();
                             popup(weight_min,weight_max);
                            //popup2();
                        }
                        else {
                            Log.e("test","failed");
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
            public void onFailure(Call<Week_Model> call, Throwable t) {
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
    private void popup2() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_popup_pregnancy);
        //Button btn_ok=dialog.findViewById(R.id.btn_ok);
        dialog.show();
      /*  btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });*/
    }
    private void popup(float weight_min, float weight_max) {
        Log.e("test","dialog running .....");
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_popup_pregnancy);

        TextView below_text=dialog.findViewById(R.id.below_text);
        TextView weight_loss=dialog.findViewById(R.id.weight_loss);
        TextView text_final_weight=dialog.findViewById(R.id.text_final_weight);
        TextView ideal_weight=dialog.findViewById(R.id.ideal_weight);

        int current_weight= Integer.parseInt(current_Weit);
        int weight_before_pre= Integer.parseInt(weight_before);
        int final_weight=current_weight-weight_before_pre;




        Double ideal_weight_number= Double.valueOf(weight_before_pre+weight_min);
        Double max_ideal_weight_number= Double.valueOf(weight_before_pre+weight_max);

       // ideal_weight_number=Double.parseDouble(new DecimalFormat("##.####").format(ideal_weight_number));
        text_final_weight.setText(getResources().getString(R.string.taken_between)+" "+weight_min+" Kg to"+weight_max+" Kg.");
        ideal_weight.setText(getResources().getString(R.string.idealk)+" "+ideal_weight_number+" Kg.");
        Double current_weight_for_rec= Double.valueOf(current_Weit);
        if (current_weight_for_rec<max_ideal_weight_number)
        {
            below_text.setText(getResources().getString(R.string.you_are_below_recommended_weight));
        }
        else
        {
            below_text.setText(getResources().getString(R.string.you_are_above_recommended_weight));
        }

        if(current_weight>ideal_weight_number)
        {
            weight_loss.setText(getResources().getString(R.string.increased)+final_weight+getResources().getString(R.string.kg));
        }
        else
        {
            weight_loss.setText(getResources().getString(R.string.descreased)+final_weight+getResources().getString(R.string.kg));
        }



        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Log.e("test","dialog closed .....");
            }
        });
    }



    public boolean validatePhone(String strPhone) {
        Matcher matcher;
        String noSpaceRegex = "^\\d{1,3}$";
        Pattern pattern = Pattern.compile(noSpaceRegex);
        matcher = pattern.matcher(strPhone);
        return matcher.matches();
    }

}