package com.example.findadoc.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Activity.Map_Activity;
import com.example.findadoc.Fragments.Pharmacy_fav_Support_Fragment;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pharmacy_Supoort_Fav_Adapter extends RecyclerView.Adapter<Pharmacy_Supoort_Fav_Adapter.My_View_Holder> {

    Context context;
    List<Fav_Response> fav_responses;
    SharedPreferences sharedPreferences;
    String person_id="", pharmacy_id,prevoud_count ;
    Pharmacy_fav_Support_Fragment pharmacy_fav_support_fragment;
    private String language="en";
    AlertDialog dialogs;

    public Pharmacy_Supoort_Fav_Adapter(Context context, List<Fav_Response> fav_responses, Pharmacy_fav_Support_Fragment pharmacy_fav_support_fragment) {
        this.context = context;
        this.fav_responses = fav_responses;
        this.pharmacy_fav_support_fragment=pharmacy_fav_support_fragment;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_adapter2, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");
        sharedPreferences= context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        if(fav_responses.get(position).getPharmacyImage()!=null)
        {
            Glide.with(context).load(Api_client.BASE_IMAGE+fav_responses.get(position).getPharmacyImage()).into(holder.image);
        }
        else
        {
            Glide.with(context).load(R.drawable.profile).into(holder.image);
        }
        holder.number.setText(fav_responses.get(position).getPharmacyPhone());
        holder.address.setText(fav_responses.get(position).getPharmacyAdd());
        holder.heading_text.setText(fav_responses.get(position).getPharmacyName());
        holder.call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+fav_responses.get(position).getPharmacyPhone()));//change the number
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
        holder.share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, fav_responses.get(position).getPharmacyName()+"\n"+fav_responses.get(position).getPharmacyAdd()+"\n"+fav_responses.get(position).getPharmacyPhone());
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
        holder.location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Map_Activity.class);
                intent.putExtra("lattitude",fav_responses.get(position).getPharmacyLatitude());
                intent.putExtra("longitude",fav_responses.get(position).getPharmacyLongitude());
                intent.putExtra("city",fav_responses.get(position).getPharmacyCityName());
                intent.putExtra("key","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
      /*  String fav_result=doctor_responses.get(position).getFavourite()+"";
        Log.e("favourite",fav_result);
        if(fav_result.equals("1"))
        {
            holder.add_fav.setImageResource(R.drawable.ic_baseline_star_rate_24);
            holder.add_fav.setColorFilter(Color.RED);
        }
        else
        {
            holder.add_fav.setImageResource(R.drawable.fav);
            holder.add_fav.setColorFilter(Color.BLACK);
        }
        holder.add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctor_id=doctor_responses.get(position).getDrId().toString();
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {
                    String fav_status=doctor_responses.get(position).getFavourite();


                    holder.add_fav.setImageResource(R.drawable.fav);



                }

            }
        });
*/
     /*   holder.recommen_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Recommend_API(pharmacy_id,prevoud_count,person_id);

            }
        });*/

        holder.add_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h_id= String.valueOf(fav_responses.get(position).getPharmacyId());
                alert_dialog_message(context, h_id,position);
            }
        });
    }
    private void alert_dialog_message(Context context, String h_id, Integer position) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final LinearLayout noDialogLogout = alertLayout.findViewById(R.id.noDialogLogout);
        final LinearLayout yesDialogLogout = alertLayout.findViewById(R.id.yesDialogLogout);


        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        /*dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        yesDialogLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_favourite_API(position,h_id);
                dialogs.dismiss();
            }
        });

        noDialogLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }
    private void Recommend_API(String pharmacy_id, String prevoud_count, String person_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().get_recommend("7c538f486ea815187d12c54c3646d71e",
                "6",
                pharmacy_id,
                person_id,
                prevoud_count,
                language);
        call.enqueue(new Callback<Fav_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Fav_Model> call, Response<Fav_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();



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
            public void onFailure(Call<Fav_Model> call, Throwable t) {
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

    public void removeItem(int position)
    {
        try
        {
            fav_responses.remove(position);
            notifyDataSetChanged();
        }
        catch (IndexOutOfBoundsException index)
        {
            index.printStackTrace();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }
    private void add_favourite_API(int position, String doctor_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Advance_Search_Model> call = Api_client.getClient().save_as_favourite_API( "7c538f486ea815187d12c54c3646d71e",person_id,"6",doctor_id,language);
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
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            removeItem(position);
                            pharmacy_fav_support_fragment.getFav_API_Data();


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
        return fav_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        public ImageView image;
        TextView heading_text,address,number;
        ImageView call_icon,location_icon,add_remove,recommen_icon,share_icon;
        public My_View_Holder(@NonNull View view) {
            super(view);
            heading_text=view.findViewById(R.id.heading_text);
            address=view.findViewById(R.id.sub_heading);
            number=view.findViewById(R.id.sub_text);
            call_icon=view.findViewById(R.id.call_icon);
            location_icon=view.findViewById(R.id.location_icon);
            add_remove=view.findViewById(R.id.add_remove);
            recommen_icon=view.findViewById(R.id.recommen_icon);
            share_icon=view.findViewById(R.id.share_icon);
            image=view.findViewById(R.id.image);
        }
    }
}
