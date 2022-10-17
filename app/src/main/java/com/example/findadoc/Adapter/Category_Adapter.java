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

import com.example.findadoc.Activity.Advance_Search_Data_Activityies;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Advance_Search_Response;
import com.example.findadoc.Model.MedicineCategory_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.My_View_Holder> {
    Context context;
    List<MedicineCategory_Response> medicineCategory_responses;
    EditText category;
    Button advance_search;
    SharedPreferences sharedPreferences;
    String language = "en";
    RecyclerView category_recycler;
    private List<MedicineCategory_Response> filteredRecordList;
    private String name;
    String bes_price="0";
    String refund_status="0";
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public Category_Adapter(List<MedicineCategory_Response> category_responses, Context context, EditText category, RecyclerView category_recycler, Button search_btn_advance, String best_price_text, String refound_status) {
        this.medicineCategory_responses = category_responses;
        this.filteredRecordList = new ArrayList<MedicineCategory_Response>();
        this.filteredRecordList.addAll(medicineCategory_responses);
        this.category = category;
        this.context = context;
        this.category_recycler = category_recycler;
        this.advance_search = search_btn_advance;
        this.bes_price=best_price_text;
        this.refund_status=refound_status;
    }
    public interface OnClickListener {
        void click(String id,String name);
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
        holder.textView.setText(medicineCategory_responses.get(position).getCategoryMedicamentEn());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /* Log.e("Text", symtoms_responses.get(position).getSymptomEn());
            Log.e("Text", symtoms_responses.get(position).getSymptomId() + "");*/
                Log.e("Text", medicineCategory_responses.get(position).getCategoryMedicamentEn() + "");
              name=  medicineCategory_responses.get(position).getCategoryMedicamentEn();
                category.setText(medicineCategory_responses.get(position).getCategoryMedicamentEn());
                //String symtoms_id = symtoms_responses.get(position).getSymptomId() + "";
                category_recycler.setVisibility(View.GONE);
                category.setSelection(category.getText().length());
                onClickListener.click(name,category.getText().toString());

            }
        });
/*
        if (medicineCategory_responses != null){
            advance_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cate_data=category.getText().toString();
                    if(cate_data.equals(""))
                    {
                        category.setError(context.getResources().getString(R.string.please_select));
                        category.requestFocus();
                    }
                    else
                    {
                        Advace_Search_Data(name);
                    }


                }
            });
        }
*/
    }

    private void Advace_Search_Data(String name) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Advance_Search_Model> call = Api_client.getClient().getAdvance_Search("7c538f486ea815187d12c54c3646d71e","",language, bes_price,name,refund_status);
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

//                            Toast.makeText(context, bes_price+"AAA", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, Advance_Search_Data_Activityies.class);
                            intent.putExtra("my_list", (Serializable) search_responses);
                            intent.putExtra("best_price",bes_price);

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
            public void onFailure(Call<Advance_Search_Model> call, Throwable t) {
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
        return medicineCategory_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView textView;
        public My_View_Holder(@NonNull View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
    public void setfilter(String charText) {

        medicineCategory_responses = new ArrayList<>();

        if (charText.length() == 0) {
            Log.e("Test", "all");
            medicineCategory_responses.addAll(filteredRecordList);
        } else {
            for (MedicineCategory_Response allRecordModelList : filteredRecordList) {
                if (allRecordModelList.getCategoryMedicamentEn() != null) {
                    if (allRecordModelList.getCategoryMedicamentEn().toLowerCase().contains(charText)) {
                        Log.e("Test", "name");
                        medicineCategory_responses.add(allRecordModelList);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

}
