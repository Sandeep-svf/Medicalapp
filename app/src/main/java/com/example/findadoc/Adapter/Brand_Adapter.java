package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findadoc.Activity.Indications_and_Dosage_Sub_Activity;
import com.example.findadoc.Model.Brand_Med_Response;
import com.example.findadoc.Model.Medication_Data_Model;
import com.example.findadoc.Model.Medication_Data_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Brand_Adapter extends RecyclerView.Adapter<Brand_Adapter.My_View_Holder> {
    Context context;
    List<Brand_Med_Response> brand_med_responses;
    private List<Brand_Med_Response> filteredRecordList;
    SharedPreferences sharedPreferences;
    String language = "en";
    private Integer brand_id;
    EditText edit_brand;
    RecyclerView brand_recycler;
    Button ii_search;

    public Brand_Adapter(List<Brand_Med_Response> brand_med_responses, Context context, EditText edit_brand, RecyclerView brand_recycler, Button ii_search) {
        this.brand_med_responses = brand_med_responses;
        this.filteredRecordList = new ArrayList<Brand_Med_Response>();
        this.filteredRecordList.addAll(brand_med_responses);
        this.edit_brand = edit_brand;
        this.context = context;
        this.brand_recycler = brand_recycler;
        this.ii_search = ii_search;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_two, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, @SuppressLint("RecyclerView") int position) {
        sharedPreferences = context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
        holder.textView.setText(brand_med_responses.get(position).getDrugNameEn());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* Log.e("Text", symtoms_responses.get(position).getSymptomEn());
            Log.e("Text", symtoms_responses.get(position).getSymptomId() + "");*/
                Log.e("Text", brand_med_responses.get(position).getDrugNameEn() + "");
                brand_id = brand_med_responses.get(position).getDrugId();

                edit_brand.setText(brand_med_responses.get(position).getDrugNameEn());
                //String symtoms_id = symtoms_responses.get(position).getSymptomId() + "";
                brand_recycler.setVisibility(View.GONE);
                edit_brand.setSelection(edit_brand.getText().length());

            }
        });
        if (brand_med_responses != null){
            ii_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Text",  brand_id+ "");
                    String brand_text=edit_brand.getText().toString();
                    if(brand_text.equals(""))
                    {
                        edit_brand.setError(context.getResources().getString(R.string.please_select_br));
                        edit_brand.requestFocus();
                    }
                    else
                    {
                        Indiation_Medicin_Search(brand_id+"");
                    }

                }
            });
        }
    }

    private void Indiation_Medicin_Search(String id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Medication_Data_Model> call = Api_client.getClient().get_medicine_data( "7c538f486ea815187d12c54c3646d71e",language, id);
        call.enqueue(new Callback<Medication_Data_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Medication_Data_Model> call, Response<Medication_Data_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();

//                        Toast.makeText(context, message+success, Toast.LENGTH_LONG).show();
                        if (success.equals("true") || success.equals("True")) {
                            List<Medication_Data_Response> symtoms_responses = response.body().getDrugData();
//                            Toast.makeText(context, success+"SS", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, Indications_and_Dosage_Sub_Activity.class);
                           if (symtoms_responses != null && symtoms_responses.size() != 0){
                               intent.putExtra("brand_id", brand_id);
                               intent.putExtra("drug_name_en", symtoms_responses.get(0).getDrugNameEn());
                               intent.putExtra("drug_price", symtoms_responses.get(0).getDrugPrice());
                               intent.putExtra("presentation_medicament_en", symtoms_responses.get(0).getPresentationMedicamentEn());
                               intent.putExtra("effect_medicament", symtoms_responses.get(0).getEffectMedicamentEn());
                               intent.putExtra("precaution_medicament", symtoms_responses.get(0).getPrecautionMedicamentEn());
                               intent.putExtra("pregnancy", symtoms_responses.get(0).getDrug_pregnancy_en());
                           }
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, message+success, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Medication_Data_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_Data_Intent(Intent intent) {
    }

    @Override
    public int getItemCount() {
        return brand_med_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView textView;
        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
                textView = itemView.findViewById(R.id.textView);
        }
    }
    public void setfilter(String charText) {

        brand_med_responses = new ArrayList<>();

        if (charText.length() == 0) {
            Log.e("Test", "all");
            brand_med_responses.addAll(filteredRecordList);
        } else {
            for (Brand_Med_Response allRecordModelList : filteredRecordList) {
                if (allRecordModelList.getDrugNameEn() != null) {
                    if (allRecordModelList.getDrugNameEn().toLowerCase().contains(charText)) {
                        Log.e("Test", "name");
                        brand_med_responses.add(allRecordModelList);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

}
