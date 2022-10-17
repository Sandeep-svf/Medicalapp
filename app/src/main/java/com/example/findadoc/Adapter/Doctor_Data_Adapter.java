package com.example.findadoc.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.Edits;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findadoc.Activity.Add_New_Prescription_Activity;
import com.example.findadoc.Activity.Doctor_Profile;
import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.Map_Activity;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Doctor_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.google.api.client.util.StringUtils;
import com.google.common.base.Splitter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doctor_Data_Adapter extends RecyclerView.Adapter<Doctor_Data_Adapter.MyViewHolder> {
   Context context;
   List<Doctor_Response> doctor_responses;
    SharedPreferences sharedPreferences;
    String person_id="";

    private String Sun, Mon, Tue, Wed , Thu , Fri , Sat , bulkWeekendData;

    private String language="en";
    public Doctor_Data_Adapter(Context context, List<Doctor_Response> doctor_responses) {
        this.context = context;
        this.doctor_responses = doctor_responses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_adapter, parent, false);
      MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");
        sharedPreferences= context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");

        if(doctor_responses.get(position).getDrImage()!=null)
        {
            Glide.with(context).load(Api_client.BASE_IMAGE+doctor_responses.get(position).getDrImage()).into(holder.image);
        }
        else
        {
            Glide.with(context).load(
                    R.drawable.profile).into(holder.image);
        }

        holder.dev_sam.setVisibility(View.VISIBLE);
        bulkWeekendData = doctor_responses.get(position).getDays();

       /* String string = "004-034556";
        String[] parts = string.split("-");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556*/

        String[] parts = bulkWeekendData.split(",");
        Sun = parts[0];
        Mon = parts[1];
        Tue = parts[2];
        Wed = parts[3];
        Thu = parts[4];
        Fri = parts[5];
        Sat = parts[6];

        holder.sun.setText( check_open_close(Sun));
        holder.mon.setText( check_open_close(Mon));
        holder.tue.setText( check_open_close(Tue));
        holder.wed.setText( check_open_close(Wed));
        holder.thu.setText( check_open_close(Thu));
        holder.fri.setText( check_open_close(Fri));
        holder.sat.setText( check_open_close(Sat));




     /*     Iterable<String> result = Splitter.on(",").split(bulkWeekendData);
        Log.e("result", String.valueOf(result));

      int sum = 0;

        Iterator it = result.iterator();
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        Log.e("result", String.valueOf(sum));

        for(int i = 0 ; i >= sum ; i++)
        {

        }*/


        holder.number.setText(doctor_responses.get(position).getDrPhone());
        holder.address.setText(doctor_responses.get(position).getDrAddress());
        holder.hospital_timing.setText(doctor_responses.get(position).getDrTiming());
        holder.heading_text.setText(doctor_responses.get(position).getDrName());

        String temp = doctor_responses.get(position).getDrTiming();
        if(temp.contains("00:00-00:00"))
        {
            holder.hospital_timing.setText("24 Hours");
        }

        holder.call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+doctor_responses.get(position).getDrPhone()));//change the number
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
        holder.recommen_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctor_id=doctor_responses.get(position).getDrId().toString();
                String prevoud_count= String.valueOf(doctor_responses.get(position).getDrRecommend());
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {
                    String fav_status= String.valueOf(doctor_responses.get(position).getFavourite());
                    Recommend_API(doctor_id,prevoud_count,person_id);

                }

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
                share.putExtra(Intent.EXTRA_TEXT, doctor_responses.get(position).getDrName()+"\n"+doctor_responses.get(position).getDrAddress()+"\n"+doctor_responses.get(position).getDrPhone());
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
        holder.location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Map_Activity.class);
                intent.putExtra("lattitude",doctor_responses.get(position).getDrLatitude());
                intent.putExtra("longitude",doctor_responses.get(position).getDrLongitude());
                intent.putExtra("city",doctor_responses.get(position).getDrCityName());
                intent.putExtra("key","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        String fav_result=doctor_responses.get(position).getFavourite()+"";
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
                    String fav_status= String.valueOf(doctor_responses.get(position).getFavourite());
                    add_favourite_API(position,doctor_id,holder.add_fav);

                }

            }
        });
        holder.layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dr_id= String.valueOf(doctor_responses.get(position).getDrId());
                Intent intent=new Intent(context, Doctor_Profile.class);
                intent.putExtra("dr_id",dr_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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

    private void Recommend_API(String doctor_id, String prevoud_count, String person_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().get_recommend("7c538f486ea815187d12c54c3646d71e",
                "3",
                doctor_id,
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

    private void add_favourite_API(int position, String doctor_id, ImageView add_fav) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Advance_Search_Model> call = Api_client.getClient().save_as_favourite_API( "7c538f486ea815187d12c54c3646d71e",person_id,"3",doctor_id,language);
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
        return doctor_responses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading_text,address,number , hospital_timing , sun , mon , tue, wed , thu , fri , sat;
        RelativeLayout layout_one;
        LinearLayout dev_sam;
        ImageView call_icon,location_icon,add_fav,recommen_icon,share_icon,image;
        public MyViewHolder(@NonNull View view) {
            super(view);
            heading_text=view.findViewById(R.id.heading_text);
            address=view.findViewById(R.id.sub_heading);
            number=view.findViewById(R.id.sub_text);
            hospital_timing=view.findViewById(R.id.hospital_timing);
            call_icon=view.findViewById(R.id.call_icon);
            location_icon=view.findViewById(R.id.location_icon);
            add_fav=view.findViewById(R.id.add_fav);
            recommen_icon=view.findViewById(R.id.recommen_icon);
            share_icon=view.findViewById(R.id.share_icon);
            image=view.findViewById(R.id.image);
            layout_one=view.findViewById(R.id.layout_one);
            sun=view.findViewById(R.id.sun);
            mon=view.findViewById(R.id.mon);
            tue=view.findViewById(R.id.tue);
            wed=view.findViewById(R.id.wed);
            thu=view.findViewById(R.id.thu);
            fri=view.findViewById(R.id.fri);
            sat=view.findViewById(R.id.sat);
            dev_sam=view.findViewById(R.id.dev_sam);
        }
    }
}
