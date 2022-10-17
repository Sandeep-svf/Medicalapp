package com.example.findadoc.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.findadoc.Activity.Login_Activity;
import com.example.findadoc.Activity.Map_Activity;
import com.example.findadoc.Activity.Web_View_Activity;
import com.example.findadoc.Adapter.Categories_Space_Adapter;
import com.example.findadoc.Adapter.Days_Adapter;
import com.example.findadoc.Adapter.Doctor_Specialist_Adapter;
import com.example.findadoc.Adapter.Specilist_Adapter;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.Classes.Permission;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Categoried_Model;
import com.example.findadoc.Model.Categories_Response;
import com.example.findadoc.Model.City;
import com.example.findadoc.Model.City_main;
import com.example.findadoc.Model.ClinicSpecialty;
import com.example.findadoc.Model.Clinic_Specialist_Model;
import com.example.findadoc.Model.Days_Model;
import com.example.findadoc.Model.DoctorSpecialty;
import com.example.findadoc.Model.Doctor_specialist_Model;
import com.example.findadoc.Model.dev.sam.City_Model;
import com.example.findadoc.Model.dev.sam.City_Result;
import com.example.findadoc.Model.dev.sam.Country_Model;
import com.example.findadoc.Model.dev.sam.Country_Result;
import com.example.findadoc.Model.dev.sam.State_Model;
import com.example.findadoc.Model.dev.sam.State_Result;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Activity.Get_Current_Lat_Long;
import com.example.findadoc.sam.dev.Model.LocationStatusModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class New_Enrty_Fragment extends Fragment {

    private static final int STATIC_INTEGER_VALUE =14527;
    Spinner category_apinner, speciality_doctor_spinner, speciality_spinner;
    SearchableSpinner city_spinner , spinner_country ,spinner_state , spinner_city;
    double longitudes, latitudes;
    String currentlatitude = "", currentlongtide = "";
    GPSTracker gpsTracker;
    LinearLayout map_layout;
    EditText name_text, experience;
    EditText address, activity, phone, mobile_no, email;
    TextView open_time, close_time;
    SharedPreferences sharedPreferences;
    RelativeLayout specility_Docto_Layout, speciality_layout;
    String language = "";
    private String category_id = "";
    private String specialist_id = "";
    Button button_submit;
    ImageView profile_image1, choose_image;
    CheckBox check_box;
    TextView open_days;
    String name_string = "", address_string = "", keyword_string = "", phone_string = "", mobile_String = "",
            email_string = "", oopen_time_string = "", close_time_string, experience_text = "";


    List<Country_Result> countryList=new ArrayList<>();
    List<State_Result> stateList=new ArrayList<>();
    List<City_Result> cityList2 =new ArrayList<>();
    List<String> city_name_list=new ArrayList<>();
    List<String> country_name_list=new ArrayList<>();
    List<String> state_name_list=new ArrayList<>();
    List<String> city_name_list2 =new ArrayList<>();


    private String sp_id="" , state_sp_id = "" , country_sp_id = "" , city_sp_id = "";



    private Uri uriFilePath;
    Uri tempUri;
    private static final int CAMERA_REQUEST1 = 1880;
    static final int result2 = 3;
    Uri img;
    String imageName = "";
    Bitmap bm, thumbnail;
    public static File photo = null;
    public static File file = null;
    String imageAbsolutePath = "";
    ArrayList<Days_Model> days_models;
    List<String> weeks = new ArrayList<>();
    List<String> id_list = new ArrayList<>();
    private StringBuilder stringBuilder2;
    private String user_id, locationStatus;
    TextView accept_terms;
    private ContentValues values6;
    private String latLng_Data, latLong;
    private String latitude_data , longitude_data;
    RelativeLayout sp_city, sp_state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new__enrty_, container, false);
        map_layout = v.findViewById(R.id.map_layout);
        sp_state = v.findViewById(R.id.sp_state);
        sp_city = v.findViewById(R.id.sp_city);
        button_submit = v.findViewById(R.id.button_submit);
        experience = v.findViewById(R.id.experience);
        check_box = v.findViewById(R.id.check_box);
        spinner_country=v.findViewById(R.id.spinner_country);
        spinner_state=v.findViewById(R.id.spinner_state);
        spinner_city=v.findViewById(R.id.spinner_city);
        category_apinner = v.findViewById(R.id.category_apinner);
        speciality_spinner = v.findViewById(R.id.speciality_spinner);
        speciality_layout = v.findViewById(R.id.speciality_layout);
        profile_image1 = v.findViewById(R.id.profile_image1);
        choose_image = v.findViewById(R.id.choose_image);
        specility_Docto_Layout = v.findViewById(R.id.specility_Docto_Layout);
        speciality_doctor_spinner = v.findViewById(R.id.speciality_doctor_spinner);
        check_box.setChecked(true);
        accept_terms = v.findViewById(R.id.accept_terms);
        sharedPreferences = getActivity().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
      //  Toast.makeText(getActivity(), language, Toast.LENGTH_SHORT).show();
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("person_id", "");

      //  Toast.makeText(getActivity(), "User Id is: "+user_id, Toast.LENGTH_SHORT).show();
        name_text = v.findViewById(R.id.name_text);
        address = v.findViewById(R.id.address);
        activity = v.findViewById(R.id.activity);
        phone = v.findViewById(R.id.phone);
        mobile_no = v.findViewById(R.id.mobile_no);
        email = v.findViewById(R.id.email);
        open_time = v.findViewById(R.id.open_time);
        open_days = v.findViewById(R.id.open_days);
        close_time = v.findViewById(R.id.close_time);
        open_days.setVisibility(View.GONE);
        days_models = new ArrayList<>();
        days_models.add(new Days_Model("Sunday", "0"));
        days_models.add(new Days_Model("Monday", "0"));
        days_models.add(new Days_Model("Tuesday", "0"));
        days_models.add(new Days_Model("Wednesday", "0"));
        days_models.add(new Days_Model("Thursday", "0"));
        days_models.add(new Days_Model("Friday", "0"));
        days_models.add(new Days_Model("Saturday", "0"));

        // getCountrySearch spinner API
        getCountrySearch();

        location_status();

        open_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       String hours = String.valueOf(selectedHour);
                       String minutes = String.valueOf(selectedMinute);
                       if(hours.length() == 1)
                       {
                           hours = "0"+hours;
                       }
                       if(minutes.length() == 1)
                       {
                           minutes = "0"+minutes;
                       }

                       Log.e("dfs","hours: "+hours);
                       Log.e("dfs","minutes: "+minutes);

                        open_time.setText(hours + ":" + minutes);


                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = spinner_country.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_country)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    country_sp_id = String.valueOf(countryList.get(i).getCountryId());

                    getStateSearch();
                    Log.e("LIST_ID_COUNTRY",country_sp_id+"ID");


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // Toast.makeText(getActivity(), "State Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = spinner_state.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_state)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    state_sp_id = String.valueOf(stateList.get(i).getId());

                    getCitySearch();

                    Log.e("LIST_ID_COUNTRY",state_sp_id+"ID");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // Toast.makeText(getActivity(), "################### City Spinner Working ##############", Toast.LENGTH_SHORT).show();

                String item = spinner_city.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_city)))
                {
                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {
                    city_sp_id = String.valueOf(cityList2.get(i).getId());
                    Log.e("LIST_ID_COUNTRY",city_sp_id+"ID");


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        close_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String hours = String.valueOf(selectedHour);
                        String minutes = String.valueOf(selectedMinute);
                        if(hours.length() == 1)
                        {
                            hours = "0"+hours;
                        }
                        if(minutes.length() == 1)
                        {
                            minutes = "0"+minutes;
                        }

                        Log.e("dfs","hours: "+hours);
                        Log.e("dfs","minutes: "+minutes);


                        close_time.setText(hours + ":" + minutes);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        map_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Map_Activity.class);
              //  Intent intent = new Intent(getActivity(), Get_Current_Lat_Long.class);
                intent.putExtra("key", "0");
                intent.putExtra("locationStatus", locationStatus);
                startActivityForResult(intent, STATIC_INTEGER_VALUE);
            }
        });
        gpsTracker = new GPSTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            latitudes = gpsTracker.getLatitude();
            longitudes = gpsTracker.getLongitude();

            currentlatitude = String.valueOf(latitudes);
            currentlongtide = String.valueOf(longitudes);

        }
        accept_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), Web_View_Activity.class);
                mIntent.putExtra("key", "1");
                mIntent.putExtra("isTermsAndCondition", true);
                startActivity(mIntent);
            }
        });


        categories_data_API();
        getSpeialist_Data();
        get_Doctor_Speialist_Data();
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLng_Data  =  Map_Activity.string;
                String temp  =  Map_Activity.lat_long;
                Log.e("latLng_Data","latLng_Data: "+temp);


         /*       String[] parts = latLng_Data.split(":");
                latitude_data = parts[0]; // 004
                longitude_data = parts[1];

                String[] parts2 = longitude_data.split(",");
                latitude_data = parts2[0]; // 004
                longitude_data = parts2[1];

                latitude_data = latitude_data.substring(2,latitude_data.length());
                longitude_data = longitude_data.substring(0,longitude_data.length()-1);

                Log.e("dfsdf",latLng_Data);
                Log.e("dfsdf",latitude_data);
                Log.e("dfsdf",longitude_data);
*/

               // Toast.makeText(getActivity(), "latLng is: " +latLng_Data, Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    validation();
                }
            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.upload_image);
                final TextView gallary = dialog.findViewById(R.id.gallary);
                TextView camera = dialog.findViewById(R.id.camera);

                dialog.show();

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        camera();
                        dialog.dismiss();
                    }
                });

                gallary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        dialog.dismiss();
                    }
                });
            }
        });
        open_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OPen_Days_Dialog(v);
            }
        });

        return v;
    }

    private void location_status() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        if(pd.isShowing()) pd.dismiss();
        pd.show();



        Call<LocationStatusModel> call = Api_client.getClient().locationStatusModelCall("7c538f486ea815187d12c54c3646d71e",user_id);

        call.enqueue(new Callback<LocationStatusModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<LocationStatusModel> call, Response<LocationStatusModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {

                            locationStatus = String.valueOf(response.body().getData());
                            Log.e("check_123", locationStatus);
                          //  Toast.makeText(getActivity(), locationStatus+"ok", Toast.LENGTH_SHORT).show();

                        } else {
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
            public void onFailure(Call<LocationStatusModel> call, Throwable t) {
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


    private void OPen_Days_Dialog(View v) {
        Dialog dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.days_dialog);

        RecyclerView days_recycelr = dialog2.findViewById(R.id.days_recycelr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        days_recycelr.setLayoutManager(linearLayoutManager);
        Days_Adapter days_adapter = new Days_Adapter(getActivity(), days_models);
        days_recycelr.setAdapter(days_adapter);
        days_adapter.setOnClickListener(new Days_Adapter.OnClickListener() {
            @Override
            public void click(String id, String title) {
                Log.e("WEEKEEKEK", id + " " + title);
                weeks = new ArrayList<>();
                id_list = new ArrayList<>();
                for (int i = 0; i < days_models.size(); i++) {
                    if (days_models.get(i).getId().equals("1")) {
                        weeks.add(days_models.get(i).getDays());

                    }
                    id_list.add(days_models.get(i).getId());

                }

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < weeks.size(); i++) {
                    if ((i + 1) < weeks.size()) {
                        stringBuilder.append(weeks.get(i)).append(",");
                    } else {
                        stringBuilder.append(weeks.get(i));

                    }

                }
                stringBuilder2 = new StringBuilder();
                for (int i = 0; i < id_list.size(); i++) {
                    if ((i + 1) < id_list.size()) {
                        stringBuilder2.append(id_list.get(i)).append(",");
                    } else {
                        stringBuilder2.append(id_list.get(i));

                    }

                }
//                Toast.makeText(getActivity(), stringBuilder2+"", Toast.LENGTH_SHORT).show();
                open_days.setText(stringBuilder.toString());

            }
        });
        dialog2.show();

    }


    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void camera() {

        PackageManager packageManager = getActivity().getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal && camera) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                /*File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
                if (!mainDirectory.exists())
                    mainDirectory.mkdirs();
                Calendar calendar = Calendar.getInstance();
                uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));

                 tempUri = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));*/


                values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                tempUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(intent, CAMERA_REQUEST1);


               /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
                startActivityForResult(intent, CAMERA_REQUEST1);*/
            }
        }
    }

    private void gallery() {
        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal || camera) {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, result2);

          /*  Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, result2);*/
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
         //   Toast.makeText(getActivity(), "STATIC_INTEGER_VALUE :" +STATIC_INTEGER_VALUE +" = "  +requestCode, Toast.LENGTH_LONG).show();
            if (requestCode == result2) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                file = new File(cursor.getString(column_index));
                Log.e("userImage1", String.valueOf(profile_image1));
                String selectedImagePath1 = getRealPathFromURIs(selectedImageUri);

                Log.v("Deatils_path","***"+selectedImagePath1);
                Glide.with(getActivity()).load(selectedImagePath1).into(profile_image1);
                Log.e("userImage1", "BB");

           /*     img = data.getData();

                String sel_path = getpath(img);

                try {

                    if (sel_path == null) {
                        Toast.makeText(getActivity(), "Bad image it can not be selected", Toast.LENGTH_SHORT).show();
                    } else {
                        *//*Log.d("selectedImagePath", sel_path);
                        String[] bits = sel_path.split("/");
                        imageName = bits[bits.length - 1];

                        Log.d("imageName", imageName);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(sel_path, options);
                        final int REQUIRED_SIZE = 500;
                        int scale = 1;
                        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                            scale *= 2;
                        options.inSampleSize = scale;
                        options.inJustDecodeBounds = false;
                        bm = BitmapFactory.decodeFile(sel_path, options);
                        // set for profile pic
                        profile_image1.setImageBitmap(bm);
                        Log.e("Imggg",bm+"");
*//*
                        Log.d("selectedImagePath", sel_path);
                        final File filePath = new File(sel_path);
                        Bitmap bitmap = BitmapFactory.decodeFile(sel_path);
                        file = savebitmap(bitmap);

                        Glide.with(getActivity())
                                .asBitmap()
                                .load(filePath)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        profile_image1.setImageBitmap(resource);
                                    }
                                });

                    }
                } catch (Exception e) {

                    Toast.makeText(getActivity(), "bad image", Toast.LENGTH_SHORT).show();
                }*/


            } else if (requestCode == CAMERA_REQUEST1) {


                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(), tempUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //setUserProfile.setImageBitmap(thumbnail);


                file = new File(getRealPathFromURIs(tempUri));

                Glide.with(getActivity())
                        .load(file)
                        .into(profile_image1);

                photo  = new File(getRealPathFromURIs(tempUri));


                /*try {
                    photo = new File(getRealPathFromURIs(tempUri));
                    String tempUriPath = getRealPathFromURIs(tempUri);

                    ExifInterface exif = new ExifInterface(getRealPathFromURIs(tempUri));
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    BitmapFactory.Options options = new BitmapFactory.Options();//
                    options.inSampleSize = 2;//
                    thumbnail = BitmapFactory.decodeFile(getRealPathFromURIs(tempUri), options);//

                    Matrix matrix = new Matrix();

                    thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),//
                            thumbnail.getHeight(), matrix, true);//

                    file = savebitmap(thumbnail);
                    Log.d(" camera Path*********", getRealPathFromURIs(tempUri));//
                    String imageNamePath = getRealPathFromURIs(tempUri);//
                    imageAbsolutePath = imageNamePath;//
                    String[] separated = imageNamePath.split("/");//
                    imageName = separated[separated.length - 1];//

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;

                    }
                    String uri = getRealPathFromURIs(tempUri);
                   // profile_image1.setImageBitmap(thumbnail);

                    Glide.with(getActivity()).load(tempUriPath).into(profile_image1);


                  //  photo = new File(new URI("file://" + uri.replace(" ", "%20")));
                 //   photo = new File(new URI("file://" + uri.replace(" ", "%20")));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }else if (requestCode == STATIC_INTEGER_VALUE)
        {
           // Toast.makeText(getActivity(), "requestCode :" +requestCode+ "STATIC_INTEGER_VALUE :" +STATIC_INTEGER_VALUE, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Map Data is running ..." +
                    "", Toast.LENGTH_SHORT).show();
            String string2 = data.getStringExtra("");
            String bbbb  =  Map_Activity.string;
            Log.e("latLng_Data2541", bbbb);

        }

    }

    private String getpath(Uri img) {
        String[] p = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(img, p, null, null, null);
        int col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String file_path = cursor.getString(col);

        try {
            photo = new File(new URI("file://" + file_path.replace(" ", "%20")));
            // Toast.makeText(this, "ajendra "+photo, Toast.LENGTH_SHORT).show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cursor.getString(col);
    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "testimage.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void validation() {
        name_string = name_text.getText().toString();
        address_string = address.getText().toString();
        keyword_string = activity.getText().toString();
        email_string = email.getText().toString();
        phone_string = phone.getText().toString();
        mobile_String = mobile_no.getText().toString();
        oopen_time_string = open_time.getText().toString();
        close_time_string = close_time.getText().toString();
        experience_text = experience.getText().toString();


        if (name_string.equals("")) {
            name_text.setError(getResources().getString(R.string.enter_name));
            name_text.requestFocus();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(latLng_Data, null))
            {
                Toast.makeText(getActivity(), "Please set location from map.", Toast.LENGTH_SHORT).show();
                return;
            } else if (address_string.equals("")) {
                address.setError(getResources().getString(R.string.enter_Addresd));
                address.requestFocus();
            } else if (keyword_string.equals("")) {
                activity.setError(getResources().getString(R.string.enter_keywords));
                activity.requestFocus();
            } else if (email_string.equals("")) {
                email.requestFocus();
                email.setError(getResources().getString(R.string.enter_email));
            } else if (!email_string.matches("[A-Z0-9a-z._%+-]{2,}+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
                email.setError(getResources().getString(R.string.email_error));
                email.requestFocus();
            } else if (phone_string.equals("")) {
                phone.requestFocus();
                phone.setError(getResources().getString(R.string.enter_phone));
            } else if (mobile_String.equals("")) {
                mobile_no.requestFocus();
                mobile_no.setError(getResources().getString(R.string.enter_mobile));
            } else if (oopen_time_string.equals("")) {
                open_time.requestFocus();
                open_time.setError(getResources().getString(R.string.chosse_open_time));
            } else if (close_time_string.equals("")) {
                close_time.requestFocus();
                close_time.setError(getResources().getString(R.string.choose_close_time));
            } else if (file == null) {
                Toast.makeText(getActivity(), getResources().getString(R.string.select_image), Toast.LENGTH_LONG).show();


            } else if(country_sp_id.equals(""))
            {
                Toast.makeText(getActivity(), getResources().getString(R.string.select_city), Toast.LENGTH_LONG).show();

            }



            else {

                String[] parts = latLng_Data.split(":");
                latitude_data = parts[0]; // 004
                longitude_data = parts[1];

                String[] parts2 = longitude_data.split(",");
                latitude_data = parts2[0]; // 004
                longitude_data = parts2[1];

                latitude_data = latitude_data.substring(2,latitude_data.length());
                longitude_data = longitude_data.substring(0,longitude_data.length()-1);

                Log.e("dfsdf",latLng_Data);
                Log.e("dfsdf",latitude_data);
                Log.e("dfsdf",longitude_data);




                if (category_id.equals("2")) {
                    if (specialist_id.equals("0") || specialist_id.equals("")) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.choose_sepciality), Toast.LENGTH_LONG).show();
                    } else {
                        if (!check_box.isChecked()) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.terms), Toast.LENGTH_LONG).show();
                        } else {
                            new_entry_from_API();
                        }
                    }
                } else if (category_id.equals("3")) {
                    if (specialist_id.equals("0") || specialist_id.equals("")) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.choose_sepciality), Toast.LENGTH_LONG).show();
                    } else if (experience_text.equals("")) {
                        experience.setError(getString(R.string.your_ecp));
                        experience.requestFocus();
                    } else {
                        if (!check_box.isChecked()) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.terms), Toast.LENGTH_LONG).show();
                        } else {
                            new_entry_from_API();
                        }

                    }
                } else {
                    if (!check_box.isChecked()) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.terms), Toast.LENGTH_LONG).show();

                    } else {
                        new_entry_from_API();
                    }
                }

            }
        }

    }

    private void new_entry_from_API() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        MultipartBody.Part body;
        if (file == null) {

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("image", "", requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            Log.e("Photo", file.getName());
        }
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "7c538f486ea815187d12c54c3646d71e");
        RequestBody langage = RequestBody.create(MediaType.parse("text/plain"), language);
        RequestBody name_b = RequestBody.create(MediaType.parse("text/plain"), name_string);
        RequestBody cat_id = RequestBody.create(MediaType.parse("text/plain"), category_id);
        RequestBody addres = RequestBody.create(MediaType.parse("text/plain"), address_string);
        RequestBody keywrd = RequestBody.create(MediaType.parse("text/plain"), keyword_string);
        RequestBody email12 = RequestBody.create(MediaType.parse("text/plain"), email_string);
        RequestBody phonm1 = RequestBody.create(MediaType.parse("text/plain"), phone_string);
        RequestBody mobil1 = RequestBody.create(MediaType.parse("text/plain"), mobile_String);
        RequestBody o_time = RequestBody.create(MediaType.parse("text/plain"), oopen_time_string);
        RequestBody c_time = RequestBody.create(MediaType.parse("text/plain"), close_time_string);
        RequestBody s_id = RequestBody.create(MediaType.parse("text/plain"), specialist_id);
        RequestBody days = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(stringBuilder2));
        RequestBody experienc_data = RequestBody.create(MediaType.parse("text/plain"), experience_text);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody Latitude_data = RequestBody.create(MediaType.parse("text/plain"), latitude_data);
        RequestBody Longitude_data = RequestBody.create(MediaType.parse("text/plain"), longitude_data);
        RequestBody country_sp_idRB = RequestBody.create(MediaType.parse("text/plain"), country_sp_id);
        RequestBody state_sp_idRB = RequestBody.create(MediaType.parse("text/plain"), state_sp_id);
        RequestBody city_sp_idRB = RequestBody.create(MediaType.parse("text/plain"), city_sp_id);

        Call<Advance_Search_Model> call = Api_client.getClient().get_new_entry_data(token,
                langage,
                name_b,country_sp_idRB,
                state_sp_idRB,
                city_sp_idRB,
                cat_id,
                addres,
                keywrd,
                email12,
                phonm1,
                mobil1,
                o_time,
                c_time,
                s_id,
                experienc_data,
                days,
                id,
                Latitude_data,
                Longitude_data,
                body);

        Log.e("new_entry_info"," language: " +language);
        Log.e("new_entry_info"," name_string: " +name_string);
        Log.e("new_entry_info"," category_id: "+ category_id);
        Log.e("new_entry_info ","address_string: " + address_string);
        Log.e("new_entry_info"," keyword_string: "+  keyword_string);
        Log.e("new_entry_info"," email_string: " + email_string);
        Log.e("new_entry_info"," phone_string: "+ phone_string);
        Log.e("new_entry_info: ", "user_id"+ user_id);
        Log.e("new_entry_info ","mobile_String: "  +mobile_String);
        Log.e("new_entry_info ","oopen_time_string: "+ oopen_time_string);
        Log.e("new_entry_info"," close_time_string: "+ close_time_string);
        Log.e("new_entry_info"," specialist_id: " +specialist_id);
        Log.e("new_entry_info"," latitude_data: " +latitude_data);
        Log.e("new_entry_info"," experience_text: " +experience_text);
        Log.e("new_entry_info", String.valueOf(stringBuilder2));
        Log.e("new_entry_info"," longitude_data: "+ longitude_data);
        Log.e("new_entry_info"," country_sp_id: "+ country_sp_id);
        Log.e("new_entry_info"," state_sp_id: "+ state_sp_id);
        Log.e("new_entry_info"," city_sp_id: "+ city_sp_id);


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
                            name_text.setText("");
                            address.setText("");
                            activity.setText("");
                            phone.setText("");
                            mobile_no.setText("");
                            email.setText("");
                            open_time.setText("");
                            close_time.setText("");
                            experience.setText("");
                            Glide.with(getActivity()).load(R.drawable.save).into(profile_image1);

                            file = null;
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        } else {
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
            public void onFailure(Call<Advance_Search_Model> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    private void get_Doctor_Speialist_Data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Doctor_specialist_Model> call = Api_client.getClient().doctore_specialist("7c538f486ea815187d12c54c3646d71e", language);
        call.enqueue(new Callback<Doctor_specialist_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Doctor_specialist_Model> call, Response<Doctor_specialist_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            List<DoctorSpecialty> doctorSpecialties = response.body().getDoctorSpecialty();
//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
                            Doctor_Specialist_Adapter customAdapter = new Doctor_Specialist_Adapter(getActivity(), doctorSpecialties);
                            speciality_doctor_spinner.setAdapter(customAdapter);

                            DoctorSpecialty doctorSpecialty = new DoctorSpecialty();

                            doctorSpecialty.setDoctorSpecialtyName(getResources().getString(R.string.selct_doctor_speclity));
                            doctorSpecialty.setDoctorSpecialityId(0);
                            doctorSpecialties.add(0, doctorSpecialty);
                            speciality_doctor_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parnet, View view, int position, long id) {
                                    specialist_id = String.valueOf(doctorSpecialties.get(position).getDoctorSpecialityId());
                                    String cities_name = doctorSpecialties.get(position).getDoctorSpecialtyName();

                                    ((TextView) view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        } else {
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
            public void onFailure(Call<Doctor_specialist_Model> call, Throwable t) {
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

    private void getSpeialist_Data() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Clinic_Specialist_Model> call = Api_client.getClient().specilist_data("7c538f486ea815187d12c54c3646d71e", language);
        call.enqueue(new Callback<Clinic_Specialist_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Clinic_Specialist_Model> call, Response<Clinic_Specialist_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            List<ClinicSpecialty> clinicSpecialties = response.body().getClinicSpecialty();
//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
                            Specilist_Adapter customAdapter = new Specilist_Adapter(getActivity(), clinicSpecialties);
                            speciality_spinner.setAdapter(customAdapter);

                            ClinicSpecialty clinicSpecialty = new ClinicSpecialty();

                            clinicSpecialty.setClinicSpecialtyName(getResources().getString(R.string.selct_spec));
                            clinicSpecialty.setClinicSpecialId(0);
                            clinicSpecialties.add(0, clinicSpecialty);
                            speciality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parnet, View view, int position, long id) {
                                    specialist_id = String.valueOf(clinicSpecialties.get(position).getClinicSpecialId());
                                    String cities_name = clinicSpecialties.get(position).getClinicSpecialtyName();

                                    ((TextView) view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        } else {
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
            public void onFailure(Call<Clinic_Specialist_Model> call, Throwable t) {
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


    private void   getCitySearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<City_Model> call = Api_client.getClient().get_city_search("7c538f486ea815187d12c54c3646d71e",
                language,
                country_sp_id,
                state_sp_id);
        call.enqueue(new Callback<City_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<City_Model> call, Response<City_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {
                            sp_city.setVisibility(View.VISIBLE);
                            // spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list2);

                            // #########################################################################################################

                            city_name_list2.clear();

                            // #########################################################################################################
                            cityList2 = response.body().getData();

                            City_Result city_result =new City_Result();

                            city_result.setCityNameAr(getResources().getString(R.string.select_city));
                            city_result.setId(0);
                            cityList2.add(0,city_result);
                            for (int j=0;j<cityList2.size();j++)
                            {
                                String category_name = cityList2.get(j).getCityNameAr();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    city_name_list2.add(category_name);
                                }
                                Log.e("city_name_list2",city_name_list2.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, city_name_list2);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(city_name_list2);
                            dAdapter.add(getResources().getString(R.string.select_city));
                            spinner_city.setAdapter(dAdapter);
                            spinner_city.setSelection(dAdapter.getCount());

                        }else {
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
            public void onFailure(Call<City_Model> call, Throwable t) {
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

    private void getStateSearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<State_Model> call = Api_client.getClient().get_province_search("7c538f486ea815187d12c54c3646d71e",language,country_sp_id);
        call.enqueue(new Callback<State_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<State_Model> call, Response<State_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {
                            sp_state.setVisibility(View.VISIBLE);
                            state_name_list.clear();

                            stateList = response.body().getData();
                            State_Result state_result =new State_Result();

                            state_result.setProvinceNameEn(getResources().getString(R.string.select_state));
                            state_result.setId(0);
                            stateList.add(0,state_result);
                            Log.e("stateListSize", String.valueOf(stateList.size()));
                            for (int j=0;j<stateList.size();j++)
                            {
                                String category_name = stateList.get(j).getProvinceNameEn();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    state_name_list.add(category_name);
                                }
                                Log.e("country_name_list",state_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, state_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(state_name_list);
                            dAdapter.add(getResources().getString(R.string.select_state));
                            spinner_state.setAdapter(dAdapter);
                            spinner_state.setSelection(dAdapter.getCount());

                        }else {
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
            public void onFailure(Call<State_Model> call, Throwable t) {
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

    private void getCountrySearch() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Country_Model> call = Api_client.getClient().get_country_search("7c538f486ea815187d12c54c3646d71e",
                language);
        call.enqueue(new Callback<Country_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Country_Model> call, Response<Country_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True"))
                        {

                            country_name_list.clear();
                            countryList = response.body().getData();

                            Country_Result country_result =new Country_Result();
                            country_result.setCountryNameEn(getResources().getString(R.string.select_country));
                            country_result.setCountryId(0);
                            countryList.add(0,country_result);
                            for (int j=0;j<countryList.size();j++)
                            {
                                String category_name = countryList.get(j).getCountryNameEn();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    country_name_list.add(category_name);
                                }
                                Log.e("country_name_list",country_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner_two, country_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(country_name_list);
                            dAdapter.add(getResources().getString(R.string.select_country));
                            spinner_country.setAdapter(dAdapter);
                            spinner_country.setSelection(dAdapter.getCount());

                        }else {
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
            public void onFailure(Call<Country_Model> call, Throwable t) {
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



    private void categories_data_API() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("FDSFSDF",language);

        Call<Categoried_Model> call = Api_client.getClient().categores_model("7c538f486ea815187d12c54c3646d71e",
                language);

        call.enqueue(new Callback<Categoried_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Categoried_Model> call, Response<Categoried_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            List<Categories_Response> cityList = response.body().getData();
//                            Toast.makeText(getActivity(),success,Toast.LENGTH_LONG).show();
                            Categories_Space_Adapter customAdapter = new Categories_Space_Adapter(getActivity(), cityList);
                            category_apinner.setAdapter(customAdapter);

                            Categories_Response city_res = new Categories_Response();

                            city_res.setCategoryName(getResources().getString(R.string.selct_categories));
                            city_res.setCategoryId(0);
                            cityList.add(0, city_res);
                            category_apinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    category_id = String.valueOf(cityList.get(position).getCategoryId());
                                    String cities_name = cityList.get(position).getCategoryName();
                                    if (category_id.equals("2")) {
//                                        "category_name": "Clinic",
//                                                "category_id": 2
                                        speciality_layout.setVisibility(View.VISIBLE);
                                        specility_Docto_Layout.setVisibility(View.GONE);
                                        experience.setVisibility(View.GONE);
                                        open_days.setVisibility(View.VISIBLE);
                                    } else if (category_id.equals("3")) {
//                                        "category_name": "Doctor",
//                                                "category_id": 3
                                        specility_Docto_Layout.setVisibility(View.VISIBLE);
                                        speciality_layout.setVisibility(View.GONE);
                                        experience.setVisibility(View.VISIBLE);
                                        open_days.setVisibility(View.VISIBLE);
                                    } else {
                                        experience.setVisibility(View.GONE);
                                        open_days.setVisibility(View.GONE);
                                        speciality_layout.setVisibility(View.GONE);
                                        specility_Docto_Layout.setVisibility(View.GONE);
                                    }

                                    ((TextView) view).setText(cities_name);


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        } else {
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
            public void onFailure(Call<Categoried_Model> call, Throwable t) {
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


    // ###################################  **************  ##############################

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }


    public class spinnerAdapter extends ArrayAdapter<String>
    {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        location_status();
    }
}