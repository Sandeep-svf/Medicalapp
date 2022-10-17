package com.example.findadoc.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findadoc.Adapter.Comment_Adapter;
import com.example.findadoc.Adapter.Fav_Model;
import com.example.findadoc.Model.Comment_Model_Data;
import com.example.findadoc.Model.Comment_Response;
import com.example.findadoc.Model.Doctor_Profile_Model;
import com.example.findadoc.Model.Doctor_Profile_REsponse;
import com.example.findadoc.Model.Notifiation_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doctor_Profile extends AppCompatActivity {
    RelativeLayout relatice_back_change_password,recommend_layout;
    ImageView share_icon;
    ImageView send_location_data,send_number,send_mobile_no;
    private String dr_idd="";
    SharedPreferences sharedPreferences;
    String language="en";
    ImageView image_specialist;
    TextView days_text;
    TextView commnt_count_text,like_count_text,view_count;
    TextView dr_name,specialist,experience_text,address_text,email_text,phone_two,phone_one,timing;
    RelativeLayout like_layout,comment_layout;
    ImageView like_img,com_image;
    private String person_id="";
    private PopupWindow popupWindow;
    private Comment_Adapter comment_adapter;
    Button yes_recommend_btn,no_recoomend_btn;
    public static final int PERMISSION_READ = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__profile);
        share_icon=findViewById(R.id.share_icon);
        send_number=findViewById(R.id.send_number);
        send_mobile_no=findViewById(R.id.send_mobile_no);
        send_location_data=findViewById(R.id.send_location_data);
        specialist=findViewById(R.id.specialist);
        image_specialist=findViewById(R.id.image_specialist);
        experience_text=findViewById(R.id.experience_text);
        comment_layout=findViewById(R.id.comment_layout);
        address_text=findViewById(R.id.address_text);
        days_text=findViewById(R.id.days_text);
        yes_recommend_btn=findViewById(R.id.yes_recommend_btn);
        no_recoomend_btn=findViewById(R.id.no_recoomend_btn);

        phone_two=findViewById(R.id.phone_two);
        phone_one=findViewById(R.id.phone_one);
        com_image=findViewById(R.id.comnt_image);
        like_img=findViewById(R.id.like_img);
        email_text=findViewById(R.id.email_text);
        like_layout=findViewById(R.id.like_layout);
        dr_name=findViewById(R.id.dr_name);
        timing=findViewById(R.id.timing);
        view_count=findViewById(R.id.view_count);
        recommend_layout=findViewById(R.id.recommend_layout);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        person_id=sharedPreferences.getString("person_id","");

        like_count_text=findViewById(R.id.like_count_text);
        commnt_count_text=findViewById(R.id.commnt_count_text);
        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language=sharedPreferences.getString("language_text","en");
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dr_idd=getIntent().getStringExtra("dr_id");


/*
        share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, "");
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
*/


            get_doctor_profile();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void Pop_Up(String my_dr_id) {
        LayoutInflater layoutInflater = (LayoutInflater)Doctor_Profile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.comment_layout,null);
        ImageView cancle_image=customView.findViewById(R.id.cancle_image);
        RecyclerView comment_data_recy=customView.findViewById(R.id.comment_data_recy);
        EditText text_message=customView.findViewById(R.id.text_message);
        popupWindow=new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
//               popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(customView, Gravity.BOTTOM, 40, 60);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        comment_data_recy.setLayoutManager(linearLayoutManager);

       getComment_API(my_dr_id,comment_data_recy);

        text_message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (text_message.getRight() - text_message.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                       String edit_mess_data=text_message.getText().toString();
                       if(edit_mess_data.equals(""))
                       {
                           text_message.setError(getResources().getString(R.string.comnt_type));
                       }
                       else 
                       {
                           if (person_id.equals(""))
                           {

                           }
                           else
                           {
                               send_Message_APi(edit_mess_data,my_dr_id,text_message,comment_data_recy,my_dr_id);

                           }

                       }

                        return true;
                    }
                }
                return false;
            }
        });
        cancle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    private void send_Message_APi(String edit_mess_data, String my_dr_id, EditText text_message, RecyclerView comment_data_recy, String myDrId) {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().comment_api(
                "7c538f486ea815187d12c54c3646d71e",
                person_id,
                my_dr_id,
                language,edit_mess_data);
        call.enqueue(new Callback<Fav_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Fav_Model> call, Response<Fav_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            text_message.setText("");
                            get_doctor_profile();
                            getComment_API(my_dr_id,comment_data_recy);

                        }else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
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
            public void onFailure(Call<Fav_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }


    private void like_API(String like_count, String dr_id) {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().do_like_unlike(
                "7c538f486ea815187d12c54c3646d71e",
                person_id,
                dr_id,
                like_count);
        call.enqueue(new Callback<Fav_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Fav_Model> call, Response<Fav_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            if(message.equals("Like successfully"))
                            {
                                like_img.setImageResource(R.drawable.ic_baseline_favorite_24);

                            }
                            else
                            {
                                like_img.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                            }
                            get_doctor_profile();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
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
            public void onFailure(Call<Fav_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void getComment_API(String dr_id, RecyclerView comment_data_recy) {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Comment_Model_Data> call = Api_client.getClient().get_comment("7c538f486ea815187d12c54c3646d71e",person_id,dr_id);
        call.enqueue(new Callback<Comment_Model_Data>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Comment_Model_Data> call, Response<Comment_Model_Data> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True"))
                        {
                            List<Comment_Response> comment_responses=response.body().getComment();

                            if (comment_adapter != null) {
                                comment_adapter.setComments(comment_responses);
                            }
                            comment_adapter=new Comment_Adapter(Doctor_Profile.this,comment_responses,dr_id);
                            comment_data_recy.setAdapter(comment_adapter);

                        }else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
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
            public void onFailure(Call<Comment_Model_Data> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_doctor_profile() {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Doctor_Profile_Model> call = Api_client.getClient().get_Doctor_profile(dr_idd,language,"7c538f486ea815187d12c54c3646d71e",person_id);
        call.enqueue(new Callback<Doctor_Profile_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Doctor_Profile_Model> call, Response<Doctor_Profile_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
//                            Toast.makeText(getApplicationContext(), message+success, Toast.LENGTH_SHORT).show();
                            List<Doctor_Profile_REsponse> doctor_responses=response.body().getData();

                            String dr_id= String.valueOf(doctor_responses.get(0).getDrId());
                            String name=doctor_responses.get(0).getDrName();
                            String image=doctor_responses.get(0).getDrImage();
                            String experience_str=doctor_responses.get(0).getDrExperience();
                            String phone=doctor_responses.get(0).getDrOtherPhone();
                            String recommend=doctor_responses.get(0).getDrRecommend();
                            String dr_phone=doctor_responses.get(0).getDrPhone();
                            String timings=doctor_responses.get(0).getDrTiming();
                            String email=doctor_responses.get(0).getDrEmail();
                            String days=doctor_responses.get(0).getDays();
                            String address=doctor_responses.get(0).getDrAddress();
                            String city=doctor_responses.get(0).getDrCityName();
                            Integer like_count= doctor_responses.get(0).getDrLike();
                            String comment_count= String.valueOf(doctor_responses.get(0).getCommentCount());
                            String like_status= String.valueOf(doctor_responses.get(0).getLikeStatus());
                            String view_counts= String.valueOf(doctor_responses.get(0).getDr_view());
                            String doctor_specility=doctor_responses.get(0).getDoctor_speciality();

//                            Toast.makeText(Doctor_Profile.this,final_days+":DAYS" , Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getApplicationContext(),like_count+" like",Toast.LENGTH_LONG).show();

                            like_count_text.setText("("+like_count+")");
                            commnt_count_text.setText("("+comment_count+")");
                            view_count.setText("("+view_counts+")");
                            specialist.setText(doctor_specility);
                            view_count_API(view_counts);
                            dr_name.setText(name);


                            if(image!=null)
                            {
                                Glide.with(getApplicationContext()).load(Api_client.BASE_IMAGE+image).into(image_specialist);

                            }
                            else
                            {
                                Glide.with(getApplicationContext()).load(R.drawable.profile).into(image_specialist);

                            }
                            share_icon.setOnClickListener(new View.OnClickListener() {
                                final String image_url=Api_client.BASE_IMAGE+image;
                                final String doctor_name=name;
                                final String dr_specility=doctor_specility;
                                final String address_text=address;
                                final String contact=dr_phone;

                                Uri uri=Uri.parse(image_url);
                                @Override
                                public void onClick(View v) {
                                    {
                                        checkPermission();
                                        Picasso.get().load(image_url).into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                try {
                                                    ShareCompat.IntentBuilder shareIntent = ShareCompat.IntentBuilder.from(Doctor_Profile.this);
                                                    shareIntent.setText(getResources().getString(R.string.name)+":-"+doctor_name+"\n"+getResources().getString(R.string.specilist)+":-"+dr_specility+"\n"+getResources().getString(R.string.address)+":-"+address_text+"\n"+getResources().getString(R.string.phone)+":-"+contact);
                                                    shareIntent.setSubject(getResources().getString(R.string.about));
                                                    String name = System.currentTimeMillis() + ".png";
//                               File file = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "Share");
                                                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                                    Log.e("Book_Detail_Activity",file+"");

                                                    /*if (!file.exists()) if (file.mkdir()) ;*/

                                                    File shareFile = new File(file  , name);
                                                    Log.e("Book_Detail_Activity",shareFile+"");
                                                    if (!shareFile.exists()) {
                                                        Log.e("Book_Detail_Activity",   " mainDirectory " );
                                                        shareFile.getParentFile().mkdirs();
                                                        Log.e("Book_Detail_Activity",   " mainDirectory " );
                                                        shareFile.createNewFile();
                                                    }

                                                    FileOutputStream fileOutputStream = new FileOutputStream(shareFile);
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, fileOutputStream);

                                                    fileOutputStream.close();
                                                    Log.e("Book_Detail_Activity",   " fileOutputStream close " + shareFile.exists());

                                                    if (shareFile != null) {
                                                        if (shareFile.exists()) {
                                                            shareIntent.setStream(Uri.fromFile(shareFile));
                                                            shareIntent.setType("image/*");
                                                        }
                                                    } else {
                                                        shareIntent.setType("*.*");
                                                    }
                                                    Intent intent = shareIntent.getIntent();
                                                    startActivity(intent);


                                                } catch (IOException e) {
                                                    Log.e("Book_Detail_Activity",e+"");
                                                }

                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        });



                /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);


                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM,uri);

                Log.e("")
                sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, str_content);




                //sharingIntent.setPackage("com.facebook.katana");

                startActivity(Intent.createChooser(sharingIntent, "Share via"));
*/
//                try
//                {
//                    startActivity(Intent.createChooser(intent, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(Company_Details_Activity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
                                    }
                                }
                            });


                            if(experience_str!=null)
                            {
                                experience_text.setText(experience_str+" "+getString(R.string.ex));
                            }
                            else
                            {
                                experience_text.setText("0 "+getString(R.string.ex));
                            }

                            address_text.setText(address);
                            email_text.setText(email);
                            phone_one.setText(phone);
                            phone_two.setText(dr_phone);
                            timing.setText(timings);
                            send_number.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+phone));//change the number
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(callIntent);
                                }
                            });

                            send_mobile_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+dr_phone));//change the number
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(callIntent);
                                }
                            });
                            if(like_status.equals("1"))
                            {
                                like_img.setImageResource(R.drawable.ic_baseline_favorite_24);

                            }
                            else
                            {
                                like_img.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                            }
                            like_layout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(person_id.equals(""))
                                    {
                                        Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        if(like_status.equals("0")){
                                            if (like_count > 0) {


//                                            int like_cnt=like_count+1;
//                                            doctor_responses.get(0).setDrLike(like_cnt);
//                                            doctor_responses.get(0).setLikeStatus(1);

//                                            like_count_text.setText(like_cnt+"");
                                                like_API(like_count + "", dr_id);


//                                            like_count_text.setText(like_count+1+"");
                                            }else {

//                                            int add_data=0+1;
//                                            doctor_responses.get(0).setDrLike(add_data);
//                                            doctor_responses.get(0).setLikeStatus(1);
//                                            like_count_text.setText(add_data+"");
                                                like_API(0 + "", dr_id);
//                                            like_count_text.setText(0+1+"");
                                            }
                                        }else{

                                            int like_cnt=doctor_responses.get(0).getDrLike();
                                            like_cnt=like_cnt-1;
//                                        doctor_responses.get(0).setDrLike(like_cnt);
//                                        doctor_responses.get(0).setLikeStatus(0);
//                                        like_count_text.setText(like_cnt+"");
                                            like_API(like_count+ "", dr_id);
//                                        like_count_text.setText(like_count-1+"");

                                        }
                                    }

                                }
                            });


                            send_location_data.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent    share = new Intent(android.content.Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                                    // Add data to the intent, the receiving app will decide
                                    // what to do with it.
                                    share.putExtra(Intent.EXTRA_SUBJECT, "Address");
                                    share.putExtra(Intent.EXTRA_TEXT, address);
//                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(Intent.createChooser(share, "Share link!"));
                                }
                            });

                            comment_layout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Comment_Response> comments_list=doctor_responses.get(0).getComment();
                                    String dr_id= String.valueOf(doctor_responses.get(0).getDrId());
                                    if(person_id.equals(""))
                                    {
                                        Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Pop_Up(dr_id);
                                    }

                                }
                            });

                            yes_recommend_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(person_id.equals(""))
                                    {
                                        Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Recommend_API(recommend);
                                    }

                                }
                            });

                            no_recoomend_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    recommend_layout.setVisibility(View.GONE);
                                }
                            });

                               List<String> my_list= Arrays.asList(days.split(","));
                            String day_one,day_two,day_three,day_four,day_five,day_six,day_seven;
                            if (my_list.get(0).equals("1"))
                            {
                                 day_one="Sunday,";
                            }
                            else
                            {
                                 day_one="";
                            }
                            if (my_list.get(1).equals("1"))
                            {
                                day_two="Monday,";
                            }
                            else
                            {
                                day_two="";
                            }
                            if (my_list.get(2).equals("1"))
                            {
                                day_three="Tuesday,";
                            }
                            else
                            {
                                day_three="";
                            }
                            if (my_list.get(3).equals("1"))
                            {
                                day_four="Wednesday,";
                            }
                            else
                            {
                                day_four="";
                            }
                            if (my_list.get(4).equals("1"))
                            {
                                day_five="Thursday,";
                            }
                            else
                            {
                                day_five="";
                            }
                            if (my_list.get(5).equals("1"))
                            {
                                day_six="Friday,";
                            }
                            else
                            {
                                day_six="";
                            }
                            if (my_list.get(6).equals("1"))
                            {
                                day_seven="Saturday,";
                            }
                            else
                            {
                                day_seven="";
                            }

                            String final_days=day_one+day_two+day_three+day_four+day_five+day_six+day_seven;

                            days_text.setText(final_days.substring(0,final_days.length()-1)+"");

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
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
            public void onFailure(Call<Doctor_Profile_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void view_count_API(String view_counts) {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Notifiation_Model> call = Api_client.getClient().view_doctor(view_counts,dr_idd,"7c538f486ea815187d12c54c3646d71e");
        call.enqueue(new Callback<Notifiation_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Notifiation_Model> call, Response<Notifiation_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
//                            Toast.makeText(Doctor_Profile.this, "Recommended Successfully", Toast.LENGTH_SHORT).show();



                        } else {
                            Toast.makeText(Doctor_Profile.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Doctor_Profile.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Doctor_Profile.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Doctor_Profile.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Doctor_Profile.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Doctor_Profile.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Doctor_Profile.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Doctor_Profile.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Doctor_Profile.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Doctor_Profile.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Doctor_Profile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Notifiation_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(Doctor_Profile.this, "This is an actual network failure :( inform the user and possibly retry)"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Doctor_Profile.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void Recommend_API(String recommend) {

        final ProgressDialog pd = new ProgressDialog(Doctor_Profile.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Fav_Model> call = Api_client.getClient().get_recommend("7c538f486ea815187d12c54c3646d71e","3",dr_idd,person_id,recommend,language);
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
                            Toast.makeText(Doctor_Profile.this, "Recommended Successfully", Toast.LENGTH_SHORT).show();
                            recommend_layout.setVisibility(View.GONE);


                        } else {
                            Toast.makeText(Doctor_Profile.this, message, Toast.LENGTH_LONG).show();
                            recommend_layout.setVisibility(View.GONE);
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Doctor_Profile.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(Doctor_Profile.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Doctor_Profile.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Doctor_Profile.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Doctor_Profile.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Doctor_Profile.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Doctor_Profile.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Doctor_Profile.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Doctor_Profile.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(Doctor_Profile.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Doctor_Profile.this, "This is an actual network failure :( inform the user and possibly retry)"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(Doctor_Profile.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }


}