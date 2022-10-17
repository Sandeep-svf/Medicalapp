package com.example.findadoc.Fragments;

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
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.findadoc.Activity.Profile_Detail_Activity;
import com.example.findadoc.Classes.Permission;
import com.example.findadoc.Model.UseFul_Number_Model;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Complaint_Fragment extends Fragment {

    EditText mobile_number_text, name_text, email, description_edit;
    ImageView camera_btn;
    private Uri uriFilePath;
    private static final int CAMERA_REQUEST1 = 1550;
    static final int result2 = 3;
    Uri img;
    Bitmap bm, thumbnail;
    public static File photo = null;
    public static File file = null;
    String imageAbsolutePath = "";
    ImageView show_image;
    String imageName = "";
    Button send_btn;
    private ContentValues values8;
    private Uri imageUri8;

    String user_name, user_email, user_id = "", user_mobile = "", user_description = "",staffStatus,staffStatusData;
    CheckBox check_box;
    SharedPreferences sharedPreferences;
    private Bitmap thumbnail8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_complaint_, container, false);
        camera_btn = v.findViewById(R.id.camera_btn);
        show_image = v.findViewById(R.id.show_image);
        mobile_number_text = v.findViewById(R.id.mobile_number_text);
        name_text = v.findViewById(R.id.name_text);
        email = v.findViewById(R.id.email);
        description_edit = v.findViewById(R.id.description);
        send_btn = v.findViewById(R.id.send_btn);
        check_box = v.findViewById(R.id.check_box);
        check_box.setChecked(false);
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("person_id", "");


        staffStatus = sharedPreferences.getString("userStatus254", "");

        if(staffStatus.equals("staff"))
        {
            staffStatusData = staffStatus;
        }else
        {
            staffStatusData = "user";
        }

        Log.e("staffStatusfjkld",staffStatus+"ok");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        camera_btn.setOnClickListener(new View.OnClickListener() {
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

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
            }
        });


        return v;
    }

    private void Validation() {
        user_email = email.getText().toString();
        user_description = description_edit.getText().toString();
        user_mobile = mobile_number_text.getText().toString();
        user_name = name_text.getText().toString();
        if (user_mobile.equals("")) {
            mobile_number_text.setError(getResources().getString(R.string.mo));
            mobile_number_text.requestFocus();
        } else if (user_name.equals("")) {
            name_text.setError(getResources().getString(R.string.name_enter));
            name_text.requestFocus();
        } else if (user_email.equals("")) {
            email.setError(getResources().getString(R.string.ema));
            email.requestFocus();
        } else if (!user_email.matches("[A-Z0-9a-z._%+-]{2,}+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
            email.setError(getResources().getString(R.string.email_error));
            email.requestFocus();
        } else if (user_description.equals("")) {
            description_edit.setError(getResources().getString(R.string.desc));
            description_edit.requestFocus();
        } else if (file == null) {
            Toast.makeText(getActivity(), getResources().getString(R.string.se), Toast.LENGTH_LONG).show();
        } else if (!check_box.isChecked()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.pleas_Read), Toast.LENGTH_LONG).show();
        } else {


            Complaint_API();


        }

    }

    private void Complaint_API() {

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
        RequestBody name_b = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody u_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody email12 = RequestBody.create(MediaType.parse("text/plain"), user_email);
        RequestBody phonm1 = RequestBody.create(MediaType.parse("text/plain"), user_mobile);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), user_description);
        RequestBody Staff = RequestBody.create(MediaType.parse("text/plain"), staffStatusData);
        Log.e("user_id", user_id);

        Call<UseFul_Number_Model> call = Api_client.getClient().complaint(token,
                phonm1,
                name_b,
                email12,
                description,
                u_id,Staff,
                body);
        call.enqueue(new Callback<UseFul_Number_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UseFul_Number_Model> call, Response<UseFul_Number_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(getActivity(), "Complaint registered successfully", Toast.LENGTH_LONG).show();
                            mobile_number_text.setText("");
                            name_text.setText("");
                            email.setText("");
                            description_edit.setText("");
                            Glide.with(getActivity()).load("abc.png").into(show_image);

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
            public void onFailure(Call<UseFul_Number_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.e("thj", String.valueOf(t));
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

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void camera() {


        PackageManager packageManager = getActivity().getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal && camera) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                values8 = new ContentValues();
                values8.put(MediaStore.Images.Media.TITLE, "New Picture");
                values8.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri8 = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values8);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri8);
                startActivityForResult(intent, CAMERA_REQUEST1);



      /*  PackageManager packageManager = getActivity().getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal || camera) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
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


       /* boolean readExternal = Permission.checkPermissionReadExternal(Profile_Detail_Activity.this);
        boolean camera = Permission.checkPermissionCamera(Profile_Detail_Activity.this);
        if (readExternal || camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, result2);
        }*/

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal && camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, result2);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("imageUri8", "requestCode :" +requestCode);
        Log.e("imageUri8", "resultCode :" +resultCode+" "+RESULT_OK);
        //Log.e("imageUri8", "RESULT_OK :" +RESULT_OK);


        if (resultCode == RESULT_OK) {
            if (requestCode == result2) {


                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                file = new File(cursor.getString(column_index));
                Log.e("userImage1", String.valueOf(file));
                String selectedImagePath1 = getpath(selectedImageUri);

                Log.v("Deatils_path", "***" + selectedImagePath1);
                Glide.with(this).load(selectedImagePath1).into(show_image);
                Log.e("userImage1", "BB");

            }


         else if (requestCode == CAMERA_REQUEST1) {

                file = new File(getRealPathFromURIs(imageUri8));

                Log.e("imageUri8", String.valueOf(imageUri8) + "ok");
                Log.e("imageUri8", "requestCode :" + requestCode + "ok");
                Log.e("imageUri8", "CAMERA_REQUEST1 :" + CAMERA_REQUEST1 + "ok");

                Glide.with(getActivity())
                        .load(file)
                        .into(show_image);

            }
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

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);


    }

}