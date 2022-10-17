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
import com.example.findadoc.Model.Hospital_Resposne;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;
import org.mortbay.io.nio.SelectorManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hospital_Adapter extends RecyclerView.Adapter<Hospital_Adapter.MyView_Holder> {
    Context context;
    List<Hospital_Resposne> hospital_resposnes;
    String person_id="";
    SharedPreferences sharedPreferences;
    private String language="en";
    private String Sun, Mon, Tue, Wed , Thu , Fri , Sat ;

    public  Hospital_Adapter(Context context, List<Hospital_Resposne> hospital_resposnes) {
        this.context = context;
        this.hospital_resposnes = hospital_resposnes;
    }

    @NonNull
    @Override
    public MyView_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_adapter, parent, false);
        MyView_Holder myViewHolder = new MyView_Holder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView_Holder holder, @SuppressLint("RecyclerView") int position) {
        sharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");
        Log.e("tag", "person id is:" +person_id);
        sharedPreferences= context.getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        Log.e("DTATTATA",person_id);



        String fav_result1 = "";
        fav_result1 = String.valueOf(hospital_resposnes.get(position).getFavourite());

       // Toast.makeText(context, fav_result1, Toast.LENGTH_SHORT).show();
        if(fav_result1.equals("1"))
        {
            Log.e("fev","red>>>>>"+fav_result1);
            holder.add_fav.setImageResource(R.drawable.ic_baseline_star_rate_24);
            holder.add_fav.setColorFilter(Color.RED);
        }
        else
        {
            Log.e("fev","black>>>>>"+fav_result1);
            holder.add_fav.setImageResource(R.drawable.fav);
            holder.add_fav.setColorFilter(Color.BLACK);
        }


        if(hospital_resposnes.get(position).getHospitalImage()!=null)
        {
            Glide.with(context).load(
                    Api_client.BASE_IMAGE+hospital_resposnes.get(position).
                            getHospitalImage()).into(holder.image);
        }
        else
        {
            Glide.with(context).load(
                    R.drawable.profile).into(holder.image);
        }


        Sun = String.valueOf(hospital_resposnes.get(position).getSun());
        Mon = String.valueOf(hospital_resposnes.get(position).getMon());
        Tue = String.valueOf(hospital_resposnes.get(position).getTue());
        Wed = String.valueOf(hospital_resposnes.get(position).getWed());
        Thu = String.valueOf(hospital_resposnes.get(position).getThu());
        Fri = String.valueOf(hospital_resposnes.get(position).getFri());
        Sat = String.valueOf(hospital_resposnes.get(position).getSat());


        holder.sun.setText( check_open_close(Sun));
        holder.mon.setText( check_open_close(Mon));
        holder.tue.setText( check_open_close(Tue));
        holder.wed.setText( check_open_close(Wed));
        holder.thu.setText( check_open_close(Thu));
        holder.fri.setText( check_open_close(Fri));
        holder.sat.setText( check_open_close(Sat));

        holder.dev_sam.setVisibility(View.VISIBLE);
        holder.number.setText(hospital_resposnes.get(position).getHospitalPhone());
    holder.address.setText(hospital_resposnes.get(position).getHospitalAddress());
    holder.hospital_timing.setText(hospital_resposnes.get(position).getHospitalTiming());
    String temp = hospital_resposnes.get(position).getHospitalTiming();
    if(temp.contains("00:00-00:00"))
    {
        holder.hospital_timing.setText("24 Hours");
        holder.dev_sam.setVisibility(View.GONE);
    }

    holder.heading_text.setText(hospital_resposnes.get(position).getHospitalName());
        holder.call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+hospital_resposnes.get(position).getHospitalPhone()));//change the number
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
                share.putExtra(Intent.EXTRA_TEXT, hospital_resposnes.get(position).getHospitalName()+"\n"+hospital_resposnes.get(position).getHospitalAddress()+"\n"+hospital_resposnes.get(position).getHospitalPhone());
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        holder.location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("lat_lng","lattitude: "+hospital_resposnes.get(position).getHospitalLatitude());
                Log.e("lat_lng","longitude: "+hospital_resposnes.get(position).getHospitalLongitude());

                Intent intent=new Intent(context, Map_Activity.class);
                intent.putExtra("lattitude",hospital_resposnes.get(position).getHospitalLatitude());
                intent.putExtra("longitude",hospital_resposnes.get(position).getHospitalLongitude());
                intent.putExtra("city",hospital_resposnes.get(position).getHospitalCityName());
                intent.putExtra("key","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hospital_id= String.valueOf(hospital_resposnes.get(position).getHospitalId());
                Log.e("tag", "hospital_id is:" +hospital_id);
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {
                    String fav_status= String.valueOf(hospital_resposnes.get(position).getFavourite());

                        add_favourite_API(position,hospital_id,holder.add_fav);

                }
                Log.e("Dataa",hospital_id+"AA");


                Log.e("Dataa",person_id+"AA");
            }
        });

        holder.recommen_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hosp_id=hospital_resposnes.get(position).getHospitalId().toString();
                String prevoud_count= String.valueOf(hospital_resposnes.get(position).getHospitalRecommend());
                if(person_id.equals(""))
                {
                    Intent intent=new Intent(context, Login_Activity.class);
                    context.startActivity(intent);

                }
                else
                {

                    Log.e("Recommend",hosp_id);
                    Log.e("Recommend",prevoud_count);
                    Log.e("Recommend",person_id);

                    Recommend_API(hosp_id,prevoud_count,person_id);

                }

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

    private void Recommend_API(String hosp_id, String prevoud_count, String person_id) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        Log.e("fgdfgd","hosp_id: "+hosp_id);
        Log.e("fgdfgd","person_id: "+person_id);
        Log.e("fgdfgd","prevoud_count: "+prevoud_count);

        Call<Fav_Model> call = Api_client.getClient().get_recommend("7c538f486ea815187d12c54c3646d71e",
                "1",
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

        Call<Advance_Search_Model> call = Api_client.getClient().save_as_favourite_API( "7c538f486ea815187d12c54c3646d71e",person_id,"1",hospital_id,language);
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
        return hospital_resposnes.size();
    }

    public class MyView_Holder extends RecyclerView.ViewHolder {
        TextView heading_text,address,number, hospital_timing ,  sun , mon , tue, wed , thu , fri , sat;
        ImageView call_icon,location_icon,add_fav,recommen_icon,share_icon,image;
            LinearLayout dev_sam ;
        public MyView_Holder(View view) {
            super(view);
            heading_text=view.findViewById(R.id.heading_text);
            hospital_timing=view.findViewById(R.id.hospital_timing);
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

        }
    }
}
