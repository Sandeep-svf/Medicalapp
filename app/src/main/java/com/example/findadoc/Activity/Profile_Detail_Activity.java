package com.example.findadoc.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.findadoc.Classes.Permission;
import com.example.findadoc.MainActivity;
import com.example.findadoc.Model.Advance_Search_Model;
import com.example.findadoc.Model.Login_Resp;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.StaffProfileDetailsModel;
import com.example.findadoc.sam.dev.Model.StaffProfileDetailsResult;
import com.example.findadoc.sam.dev.Model.StaffProfileUpdateModel;
import com.example.findadoc.sam.dev.Model.StaffProfileUpdateResult;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Profile_Detail_Activity extends AppCompatActivity {
    RelativeLayout relatice_back_change_password;
    CircleImageView profile_image1;
    ImageView choose_image;
    TextView Name;
    SharedPreferences sharedPreferences;
    EditText first_name,contact_no;
    Button update_btn;
    private Uri uriFilePath;
    private static final int CAMERA_REQUEST1 = 1880;
    static final int result2 = 3;
    Uri img;
    String imageName = "";
    Bitmap bm, thumbnail;
    public static File photo=null;
    public static File file=null;
    String imageAbsolutePath = "";
    private LocationManager locationManager;
    private Location location;
    TextView addressda;
    TextView email_addres;
    private String user_id,staffStatus,staffUserId;
    private String user_name;
    private String mobile_no;
    private String language;
    private String photos;
    private ContentValues values7;
    private Uri imageUri7;
    private Bitmap thumbnail7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__detail_);
        sharedPreferences= getApplicationContext().getSharedPreferences("LANGUAGE_NAME", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language_text", "en");
        sharedPreferences = Profile_Detail_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
         user_name=sharedPreferences.getString("user_name","");
        staffStatus=sharedPreferences.getString("staffStatus","");
        staffUserId=sharedPreferences.getString("person_id","");



         photos=sharedPreferences.getString("user_profile","");
        String family_name=sharedPreferences.getString("last_name","");
        String email=sharedPreferences.getString("user_email","");
        mobile_no = sharedPreferences.getString("user_mobile", "");
        user_id= sharedPreferences.getString("person_id","");



        relatice_back_change_password=findViewById(R.id.relatice_back_change_password);
        profile_image1=findViewById(R.id.profile_image1);
        addressda=findViewById(R.id.address);
        choose_image=findViewById(R.id.choose_image);
        Name=findViewById(R.id.Name);
        first_name=findViewById(R.id.first_name);
//        last_name=findViewById(R.id.last_name);
        email_addres=findViewById(R.id.email_addres);
        contact_no=findViewById(R.id.contact_no);
        update_btn=findViewById(R.id.update_btn);

        Name.setText(user_name+" "+family_name);
        first_name.setText(user_name+" "+family_name);
//        last_name.setText(family_name);
        email_addres.setText(email);
        contact_no.setText(mobile_no);
//        Toast.makeText(getApplicationContext(),user_id+"",Toast.LENGTH_LONG).show();
        if (photos.equals(""))
        {
            Glide.with(getApplicationContext()).load(R.drawable.save).into(profile_image1);

        }
        else
        {
            Glide.with(getApplicationContext()).load(photos).into(profile_image1);
        }


        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                final Dialog dialog = new Dialog(Profile_Detail_Activity.this);
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
    update_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (first_name.getText().toString().equals(""))
            {
                first_name.setError(getResources().getString(R.string.enter_name));
                first_name.requestFocus();
            }
            else if(contact_no.getText().toString().equals(""))
            {
                contact_no.requestFocus();
                contact_no.setError(getResources().getString(R.string.enter_phone));
            }
            else
            {
                if(staffStatus.equals("1"))
                {
                    //Toast.makeText(Profile_Detail_Activity.this, "staff_profile_update_api Running ................", Toast.LENGTH_SHORT).show();
                    staff_profile_update_api();
                }else
                {
                   // Toast.makeText(Profile_Detail_Activity.this, "Update_API Running ###################", Toast.LENGTH_SHORT).show();

                    Update_API();
                }

            }

        }
    });
    setCurrentLocation();
    }



    private void staff_profile_update_api() {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(Profile_Detail_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        MultipartBody.Part body;
        if (file == null) {

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("image", Api_client.BASE_IMAGE+photos, requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("image", file.getName() , requestFile);
            Log.e("Photo",file.getName());
        }
        RequestBody UserName = RequestBody.create(MediaType.parse("text/plain"),first_name.getText().toString());
        RequestBody user_ids = RequestBody.create(MediaType.parse("text/plain"), staffUserId);
        RequestBody UserPhone = RequestBody.create(MediaType.parse("text/plain"), contact_no.getText().toString());
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "7c538f486ea815187d12c54c3646d71e");
        RequestBody lang = RequestBody.create(MediaType.parse("text/plain"), language);


        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<StaffProfileUpdateModel> call = Api_client.getClient().staffProfileUPdate(token,
                lang,
                user_ids,
                UserPhone,
                UserName,
                body);

        //calling the api
        call.enqueue(new Callback<StaffProfileUpdateModel>() {
            @Override
            public void onResponse(Call<StaffProfileUpdateModel> call, retrofit2.Response<StaffProfileUpdateModel> response)
            {

                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), response.code()+"", Toast.LENGTH_LONG).show();

                try
                {
                    if (response.isSuccessful())
                    {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True"))
                        {
                            List<StaffProfileUpdateResult> resps=response.body().getData();
                            String user_id= String.valueOf(resps.get(0).getId());
                            String username=resps.get(0).getName();
                            String user_email=resps.get(0).getEmail();
                            String user_mobile= String.valueOf(resps.get(0).getMobile());
                            String user_imnage=Api_client.BASE_IMAGE+resps.get(0).getStaffImage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            sharedPreferences = Profile_Detail_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_name", username);
                            editor.putString("last_name", "");
                            editor.putString("user_profile", user_imnage);

                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);

                            editor.apply();
                          //  file = null;

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Profile_Detail_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Profile_Detail_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Profile_Detail_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Profile_Detail_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Profile_Detail_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Profile_Detail_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Profile_Detail_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Profile_Detail_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Profile_Detail_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Profile_Detail_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }
                catch (JsonSyntaxException exception )
                {
                    exception.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<StaffProfileUpdateModel> call, Throwable t)
            {
                progressDialog.dismiss();
                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException)
                {
                    Toast.makeText(Profile_Detail_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion_issue",t.getMessage());
                    Toast.makeText(Profile_Detail_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });
    }

    private void Update_API() {
        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(Profile_Detail_Activity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        MultipartBody.Part body;


        Log.e("user_id",user_id);


        if (file == null) {

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            body = MultipartBody.Part.createFormData("image", Api_client.BASE_IMAGE+photos, requestFile);
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("image", file.getName() , requestFile);
            Log.e("Photo",file.getName());
        }
        RequestBody UserName = RequestBody.create(MediaType.parse("text/plain"),first_name.getText().toString());
        RequestBody user_ids = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody UserPhone = RequestBody.create(MediaType.parse("text/plain"), contact_no.getText().toString());
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "7c538f486ea815187d12c54c3646d71e");
        RequestBody lang = RequestBody.create(MediaType.parse("text/plain"), language);

        // String strDeviceToken=DeviceTokenNav.getStringData(Login_Activity.this,DeviceTokenNav.KEY_TOKENNAV);
        Call<Advance_Search_Model> call = Api_client.getClient().update_profile(token,lang,user_ids,UserPhone,UserName,body);

        //calling the api
        call.enqueue(new Callback<Advance_Search_Model>() {
            @Override
            public void onResponse(Call<Advance_Search_Model> call, retrofit2.Response<Advance_Search_Model> response)
            {

                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), response.code()+"", Toast.LENGTH_LONG).show();

                try
                {
                    if (response.isSuccessful())
                    {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True"))
                        {
                            List<Login_Resp> resps=response.body().getData();
                            String user_id= String.valueOf(resps.get(0).getId());
                            String username=resps.get(0).getUserName();
                            String user_email=resps.get(0).getUserEmail();
                            String user_mobile=resps.get(0).getUserMobile();
                            String user_imnage=Api_client.BASE_IMAGE+resps.get(0).getUserImage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                           sharedPreferences = Profile_Detail_Activity.this.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_name", username);
                            editor.putString("last_name", "");
                            editor.putString("user_profile", user_imnage);

                            editor.putString("user_email", user_email);
                            editor.putString("person_id", user_id);
                            editor.putString("user_mobile",user_mobile);

                            editor.apply();
                            //file = null;


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Profile_Detail_Activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code())
                            {
                                case 400:
                                    Toast.makeText(Profile_Detail_Activity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Profile_Detail_Activity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(Profile_Detail_Activity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(Profile_Detail_Activity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(Profile_Detail_Activity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(Profile_Detail_Activity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(Profile_Detail_Activity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Profile_Detail_Activity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Profile_Detail_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (IllegalStateException e)
                {
                    e.printStackTrace();
                }
                catch (JsonSyntaxException exception )
                {
                    exception.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Call<Advance_Search_Model> call, Throwable t)
            {
                progressDialog.dismiss();
                // Toast.makeText(Login_Activity.this, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Login_Activity.this,t.getMessage(), Toast.LENGTH_LONG).show();

                if (t instanceof IOException)
                {
                    Toast.makeText(Profile_Detail_Activity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.e("conversion_issue",t.getMessage());
                    Toast.makeText(Profile_Detail_Activity.this, "Please Check your Internet Connection....", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(Login_Activity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }

            }
        });

    }

    public void setCurrentLocation() {
       /* if (UtilityMethods.isGPSEnabled(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                // return;
            } else {

            }*/
            getCurrentAddress();
        } /*else {
//            alertbox("Gps Status", "Your Device's GPS is Disable", getApplicationContext());
        }*/

    public void getCurrentAddress() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            try {

                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        2000, (LocationListener) this);
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String locality = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    String currentLocation;

                    Log.e("My_Location",address+"A");
                    Log.e("My_Location",locality+"B");
                    Log.e("My_Location",subLocality+"C");
                    Log.e("My_Location",state+"D");
                    Log.e("My_Location",country+"E");
                    Log.e("My_Location",postalCode+"F");
                    Log.e("My_Location",knownName+"G");
                    Log.e("My_Location",locality+"H");

//                    addressda.setText(address+"A"+locality+"B"+subLocality+"c"+state+"D\n"+country+"E"+postalCode+"F"+knownName+"AD"+locality);
                    if (subLocality != null) {

                        currentLocation = locality + "," + subLocality;
                    } else {

                        currentLocation = locality;
                    }
                    String current_locality = locality;
                }

            } catch (Exception e) {
                e.printStackTrace();
              //  Toast.makeText(Profile_Detail_Activity.this, "Constants.ToastConstatnts.ERROR_FETCHING_LOCATION"+e, Toast.LENGTH_SHORT).show();
               // Log.e("message", String.valueOf(e));
            }
        }
    }
    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void camera() {

        PackageManager packageManager = Profile_Detail_Activity.this.getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(Profile_Detail_Activity.this);
        boolean camera = Permission.checkPermissionCamera(Profile_Detail_Activity.this);
        if (readExternal && camera) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                values7 = new ContentValues();
                values7.put(MediaStore.Images.Media.TITLE, "New Picture");
                values7.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri7 = Profile_Detail_Activity.this.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values7);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri7);
                startActivityForResult(intent, CAMERA_REQUEST1);


            /*    File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
                if (!mainDirectory.exists())
                    mainDirectory.mkdirs();
                Calendar calendar = Calendar.getInstance();
                uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
                startActivityForResult(intent, CAMERA_REQUEST1);*/
            }
        }
    }
    private void gallery() {
        boolean readExternal = Permission.checkPermissionReadExternal(Profile_Detail_Activity.this);
        boolean camera = Permission.checkPermissionCamera(Profile_Detail_Activity.this);
        if (readExternal || camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, result2);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == result2) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                file = new File(cursor.getString(column_index));
                Log.e("userImage1", String.valueOf(file));
                String selectedImagePath1 = getPath(selectedImageUri);

                Log.v("Deatils_path","***"+selectedImagePath1);
                Glide.with(this).load(selectedImagePath1).into(profile_image1);
                Log.e("userImage1", "BB");


              /*  img = data.getData();

                String sel_path = getpath(img);

                try {

                    if (sel_path == null) {
                        Toast.makeText(this, "Bad image it can not be selected", Toast.LENGTH_SHORT).show();
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
                        file=savebitmap(bitmap);

                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(filePath)
                                .into(new SimpleTarget<Bitmap>()
                                {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        profile_image1.setImageBitmap(resource);
                                    }
                                });

                    }
                } catch (Exception e) {

                    Toast.makeText(this, "bad image", Toast.LENGTH_SHORT).show();
                }*/


            } else if (requestCode == CAMERA_REQUEST1) {

                try {
                    thumbnail7 = MediaStore.Images.Media.getBitmap(
                            Profile_Detail_Activity.this.getContentResolver(), imageUri7);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //setUserProfile.setImageBitmap(thumbnail);


                file = new File(getRealPathFromURIs(imageUri7));

                Glide.with(Profile_Detail_Activity.this)
                        .load(file)
                        .into(profile_image1);




               /* try {
                    ExifInterface exif = new ExifInterface(uriFilePath.getPath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    BitmapFactory.Options options = new BitmapFactory.Options();//
                    options.inSampleSize = 2;//
                    thumbnail = BitmapFactory.decodeFile(uriFilePath.getPath(), options);//

                    Matrix matrix = new Matrix();

                    thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),//
                            thumbnail.getHeight(), matrix, true);//

                    file=savebitmap(thumbnail);
                    Log.d(" camera Path*********", uriFilePath.getPath());//
                    String imageNamePath = uriFilePath.getPath();//
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
                    String uri = uriFilePath.getPath();
                    profile_image1.setImageBitmap(thumbnail);


                    photo = new File(new URI("file://" + uri.replace(" ", "%20")));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }

    }

    public String getPath(Uri uri)
    {
        Cursor cursor=null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

   /* private String getpath(Uri img) {
        String[] p = {MediaStore.Images.Media.DATA};
        Cursor cursor = Profile_Detail_Activity.this.getContentResolver().query(img, p, null, null, null);
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
    }*/
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

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);


    }
}