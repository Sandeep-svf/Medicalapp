package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.Map_Activity;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Duty_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Duty_Adapter extends RecyclerView.Adapter<Duty_Adapter.My_View_Holder> {
    Context context;
    List<Duty_Response> duty_responses;
    String person_id="";
    SharedPreferences sharedPreferences;
    private String language="en";
    private String key;
    private String Sun, Mon, Tue, Wed , Thu , Fri , Sat ;

    public Duty_Adapter(Context context, List<Duty_Response> duty_responses, String key) {
        this.context = context;
        this.duty_responses = duty_responses;
        this.key = key;
    }

    @NonNull
    @Override
    public My_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_adapter, parent, false);
        My_View_Holder myViewHolder = new My_View_Holder(view);
        return myViewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull My_View_Holder holder, @SuppressLint("RecyclerView") int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");
        sharedPreferences= context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");

        if(key.contains("1"))
        {
            holder.recommen_icon_layout.setVisibility(View.GONE);
        }

        if(duty_responses.get(position).getPharmacyGuardImage()!=null)
        {
            Glide.with(context).load(
                    Api_client.BASE_IMAGE+duty_responses.get(position).
                            getPharmacyGuardImage()).into(holder.image);
        }
        else
        {
            Glide.with(context).load(
                    R.drawable.profile).into(holder.image);
        }


        Sun = String.valueOf(duty_responses.get(position).getSun());
        Mon = String.valueOf(duty_responses.get(position).getMon());
        Tue = String.valueOf(duty_responses.get(position).getTue());
        Wed = String.valueOf(duty_responses.get(position).getWed());
        Thu = String.valueOf(duty_responses.get(position).getThu());
        Fri = String.valueOf(duty_responses.get(position).getFri());
        Sat = String.valueOf(duty_responses.get(position).getSat());


        holder.sun.setText( check_open_close(Sun));
        holder.mon.setText( check_open_close(Mon));
        holder.tue.setText( check_open_close(Tue));
        holder.wed.setText( check_open_close(Wed));
        holder.thu.setText( check_open_close(Thu));
        holder.fri.setText( check_open_close(Fri));
        holder.sat.setText( check_open_close(Sat));

        holder.dev_sam.setVisibility(View.VISIBLE);


        holder.number.setText(duty_responses.get(position).getPharmacyGuardPhone()+"");
        holder.address.setText(duty_responses.get(position).getPharmacyGuardAdd());
        holder.heading_text.setText(duty_responses.get(position).getPharmacyGuardName());
        holder.hospital_timing.setText(duty_responses.get(position).getPharmacyGuardTiming());
        String temp = duty_responses.get(position).getPharmacyGuardTiming();
        if(temp.contains("00:00-00:00"))
        {
            holder.hospital_timing.setText("24 Hours");
            holder.dev_sam.setVisibility(View.GONE);
        }

        holder.call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+duty_responses.get(position).getPharmacyGuardPhone()));//change the number
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
                share.putExtra(Intent.EXTRA_TEXT, duty_responses.get(position).getPharmacyGuardName()+"\n"+duty_responses.get(position).getPharmacyGuardAdd()+"\n"+duty_responses.get(position).getPharmacyGuardPhone());
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        holder.location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Map_Activity.class);
                intent.putExtra("lattitude",duty_responses.get(position).getPharmacyGuardLatitude());
                intent.putExtra("longitude",duty_responses.get(position).getPharmacyGuardLongitude());
                intent.putExtra("city",duty_responses.get(position).getPharmacyGuardCityName());
                intent.putExtra("key","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        String fav_result=duty_responses.get(position).getFavourite()+"";
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
                String hospital_id= String.valueOf(duty_responses.get(position).getPharmacyGuardId());
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {
                    String fav_status= String.valueOf(duty_responses.get(position).getFavourite());

                    add_favourite_API(position,hospital_id,holder.add_fav);

                }
                Log.e("Dataa",hospital_id+"AA");


                Log.e("Dataa",person_id+"AA");
            }
        });

     /*   holder.recommen_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hosp_id=duty_responses.get(position).getPharmacyGuardId().toString();
                String prevoud_count= String.valueOf(duty_responses.get(position).getPharmacyGuardRecommend());
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {

                    Recommend_API(hosp_id,prevoud_count,person_id);


                }

            }
        });*/

    }

    private String check_open_close(String day) {
        String status = null ;
        if(day.contains("1"))
        {
            status = "open" ;
        }else
        {
            status = "close" ;
        }
        return status;
    }

    private void Recommend_API(String hosp_id, String prevoud_count, String person_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().get_recommend("7c538f486ea815187d12c54c3646d71e",
                "11",
                hosp_id,
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

    private void add_favourite_API(int position, String hospital_id, ImageView add_fav) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Advance_Search_Model> call = Api_client.getClient().save_as_favourite_API( "7c538f486ea815187d12c54c3646d71e",person_id,"11",hospital_id,language);
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
                            if(message.equals("favourite added successfully"))
                            {
                                add_fav.setImageResource(R.drawable.ic_baseline_star_rate_24);
                                add_fav.setColorFilter(Color.RED);
                            }
                            else
                            {
                                add_fav.setImageResource(R.drawable.fav);
                                add_fav.setColorFilter(Color.BLACK);
                            }




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
        return duty_responses.size();
    }

    public class My_View_Holder extends RecyclerView.ViewHolder {
        TextView heading_text,address,number ,  hospital_timing ,  sun , mon , tue, wed , thu , fri , sat;
        ImageView call_icon,location_icon,add_fav,recommen_icon,share_icon,image;
        LinearLayout recommen_icon_layout , dev_sam;
        public My_View_Holder(@NonNull View view) {
            super(view);
            heading_text=view.findViewById(R.id.heading_text);
            recommen_icon_layout=view.findViewById(R.id.recommen_icon_layout);
            address=view.findViewById(R.id.sub_heading);
            number=view.findViewById(R.id.sub_text);
            call_icon=view.findViewById(R.id.call_icon);
            location_icon=view.findViewById(R.id.location_icon);
            add_fav=view.findViewById(R.id.add_fav);
            recommen_icon=view.findViewById(R.id.recommen_icon);
            share_icon=view.findViewById(R.id.share_icon);
            image=view.findViewById(R.id.image);
            sun=view.findViewById(R.id.sun);
            mon=view.findViewById(R.id.mon);
            tue=view.findViewById(R.id.tue);
            wed=view.findViewById(R.id.wed);
            thu=view.findViewById(R.id.thu);
            fri=view.findViewById(R.id.fri);
            sat=view.findViewById(R.id.sat);
            dev_sam=view.findViewById(R.id.dev_sam);
            hospital_timing=view.findViewById(R.id.hospital_timing);
        }

    }
}
