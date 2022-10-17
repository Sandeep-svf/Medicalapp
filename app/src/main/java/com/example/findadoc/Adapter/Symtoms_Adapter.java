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

import com.example.findadoc.Activity.Diagnosis_Activity_;
import com.example.findadoc.Model.Search_Diagnosis_Model;
import com.example.findadoc.Model.Search_Diagnosis_Response;
import com.example.findadoc.Model.Symtoms_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Symtoms_Adapter extends RecyclerView.Adapter<Symtoms_Adapter.My_View_Holder> {
    List<Symtoms_Response> symtoms_responses;
    private List<Symtoms_Response> filteredRecordList;
    Context context;
    EditText symtoms_edit;
    RecyclerView list_recycler;
    Button daignosis_search_btn;
        SharedPreferences sharedPreferences;
    String language = "en";
    private Integer symtoms_id;

    public Symtoms_Adapter(List<Symtoms_Response> symtoms_responses, Context context, EditText symtoms_edit, RecyclerView list_recycler, Button daignosis_search_btn) {
        this.symtoms_responses = symtoms_responses;
        this.filteredRecordList = new ArrayList<Symtoms_Response>();
        this.filteredRecordList.addAll(symtoms_responses);
        this.symtoms_edit = symtoms_edit;
        this.context = context;
        this.list_recycler = list_recycler;
        this.daignosis_search_btn = daignosis_search_btn;
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
        holder.textView.setText(symtoms_responses.get(position).getSymptomEn());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* Log.e("Text", symtoms_responses.get(position).getSymptomEn());
            Log.e("Text", symtoms_responses.get(position).getSymptomId() + "");*/
                Log.e("Text", symtoms_responses.get(position).getSymptomId() + "");
                symtoms_id = symtoms_responses.get(position).getSymptomId();

                symtoms_edit.setText(symtoms_responses.get(position).getSymptomEn());
                //String symtoms_id = symtoms_responses.get(position).getSymptomId() + "";
                list_recycler.setVisibility(View.GONE);
                symtoms_edit.setSelection(symtoms_edit.getText().length());

            }
        });
        if (symtoms_responses != null){
            daignosis_search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Text",  symtoms_id+ "");
                    Diagnosis_API(symtoms_id+"");
                }
            });
        }

    }

    private void Diagnosis_API(String symtoms_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Search_Diagnosis_Model> call = Api_client.getClient().getsearch_diagnosis_details(language, symtoms_id, "7c538f486ea815187d12c54c3646d71e");
        call.enqueue(new Callback<Search_Diagnosis_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Search_Diagnosis_Model> call, Response<Search_Diagnosis_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            List<Search_Diagnosis_Response> symtoms_responses = response.body().getData();


                            Intent intent = new Intent(context, Diagnosis_Activity_.class);
                            intent.putExtra("symtoms_id", symtoms_id);
                            intent.putExtra("related_disease", symtoms_responses.get(0).getRelatedDisease());
                            intent.putExtra("discription", symtoms_responses.get(0).getSymptomDescription());
                            intent.putExtra("symtomps", symtoms_responses.get(0).getSymptom());

                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Search_Diagnosis_Model> call, Throwable t) {
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

    @Override
    public int getItemCount() {
        return symtoms_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public My_View_Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void setfilter(String charText) {

        symtoms_responses = new ArrayList<>();

        if (charText.length() == 0) {
            Log.e("Test", "all");
            symtoms_responses.addAll(filteredRecordList);
        } else {
            for (Symtoms_Response allRecordModelList : filteredRecordList) {
                if (allRecordModelList.getSymptomEn() != null) {
                    if (allRecordModelList.getSymptomEn().toLowerCase().contains(charText)) {
                        Log.e("Test", "name");
                        symtoms_responses.add(allRecordModelList);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

}
