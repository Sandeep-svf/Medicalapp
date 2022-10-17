package com.example.findadoc.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.findadoc.Model.Pres_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_New_Prescription_Activity extends AppCompatActivity {
    RelativeLayout relatice_back_change_password;
    EditText text_prescription,description;
    Button send_btn;
    private SharedPreferences sharedPreferences;
    private String language="en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__prescription_);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        text_prescription=findViewById(R.id.text_prescription);
        description=findViewById(R.id.description);
        send_btn=findViewById(R.id.send_btn);
        sharedPreferences=Add_New_Prescription_Activity.this.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_presciption_data=text_prescription.getText().toString();
                String text_descrition=description.getText().toString();
                if (text_presciption_data.equals(""))
                {
                    text_prescription.requestFocus();
                    text_prescription.setError(getResources().getString(R.string.text_prescription_da));
                }
               else if (text_descrition.equals(""))
                {
                    description.requestFocus();
                    description.setError(getResources().getString(R.string.enter_diseasse_prescription));
                }
               else
                {
                    Prescription_API(text_presciption_data,text_descrition);
                }
            }
        });

    }

    private void Prescription_API(String text_presciption_data, String text_descrition) {

        final ProgressDialog pd = new ProgressDialog(Add_New_Prescription_Activity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Pres_Model> call = Api_client.getClient().get_Add_prescripiton("7c538f486ea815187d12c54c3646d71e",
                language,
                text_presciption_data,
                text_descrition);
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
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            description.setText("");
                            text_prescription.setText("");
                            finish();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        pd.dismiss();
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
            public void onFailure(Call<Pres_Model> call, Throwable t) {
                pd.dismiss();
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }
            }
        });


    }
}