package com.example.findadoc.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.findadoc.MainActivity;
import com.example.findadoc.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.util.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class Splash_Activity extends AppCompatActivity {
        private static int SPLASH_TIME_OUT = 1000;
        public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

        boolean gps_enabled = false;
        boolean network_enabled = false;

        SharedPreferences sharedpreferences;
        public static final String MyPREFERENCES = "MyPrefs";
        public static  boolean isFirstRun = true;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_);


            LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}


            if (isOnline())
            {
                if (checkAndRequestPermissions())
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            SharedPreferences sharedpreference_splash = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);

                            {
                                if(gps_enabled) {
                                    Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                                    intent.putExtra("user_type", "1");
                                    startActivity(intent);
                                    finish();
                                }else
                                {
                                    gps_error_message();
                                }
                            }


                        }
                    }, SPLASH_TIME_OUT);

                }
            }
            else
            {
                Toast.makeText(Splash_Activity.this, "There are no internet connection", Toast.LENGTH_LONG).show();
            }
        }

        private void gps_error_message() {
            AlertDialog alertDialog = new AlertDialog.Builder(Splash_Activity.this).create();
            alertDialog.setTitle("Please enale Location service");
            alertDialog.setMessage("Please turn on Location");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            boolean gps_enabled2 = false;
                            boolean network_enabled = false;

                            LocationManager lm2 = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                            try {
                                gps_enabled2 = lm2.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            } catch(Exception ex) {}

                           if(gps_enabled2)
                           {

                               Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                               intent.putExtra("user_type", "1");
                               startActivity(intent);
                               finish();
                               alertDialog.dismiss();
                           }else

                           {
                               gps_error_message();
                           }
                        }
                    });
            alertDialog.show();

        }

        public boolean isOnline()
        {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();

        }
        public boolean checkAndRequestPermissions() {
            int camerapermission = ContextCompat.checkSelfPermission(Splash_Activity.this, Manifest.permission.CAMERA);
            int permissiongGallary = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

            List<String> listPermissionsNeeded = new ArrayList<>();

            if (camerapermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }

            if (permissiongGallary != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (permissionWrite != PackageManager.PERMISSION_GRANTED) {

                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (loc2 != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (loc != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
            String TAG = "tag";
            Log.d(TAG, "Permission callback called-------");
            switch (requestCode) {
                case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                    Map<String, Integer> perms = new HashMap<>();
                    // Initialize the map with both permissions
                    perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                    perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                    perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                    perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                    perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

                    // Fill with actual results from user
                    if (grantResults.length > 0) {
                        for (int i = 0; i < permissions.length; i++)
                            perms.put(permissions[i], grantResults[i]);
                        // Check for both permissions
                        if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED  && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "permission granted");
                            // process the normal flow

                            SharedPreferences sharedpreference_splash = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);


                              {

                                  if(gps_enabled) {
                                      Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                                      startActivity(intent);
                                      finish();

                                  }else
                                  {
                                      gps_error_message();
                                  }


                            }
    //                        else if (!(sharedPreference_main.getuserId().equals("") && (!(sharedPreference_main.getCatId().equals(""))))){
    //                            Intent intent = new Intent(SplashScreen.this, DashBoardActivity.class);
    //                            startActivity(intent);
    //
    //                        }

                        }

                        //else any one or both the permissions are not granted

                        else {
                            Log.d(TAG, "Some permissions are not granted ask again ");
                            //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
    //                        // shouldShowRequestPermissionRationale will return true
                            //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(Splash_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                showDialogOK(
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        checkAndRequestPermissions();
                                                        break;
                                                    case DialogInterface.BUTTON_NEGATIVE:
                                                        // proceed with logic by disabling the related features or quit the app.
                                                        finish();
                                                        break;
                                                }
                                            }
                                        });
                            }
                            //permission is denied (and never ask again is  checked)
                            //shouldShowRequestPermissionRationale will return false
                            else {
                                explain();
                                //                            //proceed with logic by disabling the related features or quit the app.
                            }
                        }
                    }
                }
            }
        }


        private void showInternetDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(Splash_Activity.this).create();
            alertDialog.setTitle("Internet Not Connected");
            alertDialog.setMessage("Please turn on Internet");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isOnline();
                        }
                    });
            alertDialog.show();
        }

        private void showDialogOK(DialogInterface.OnClickListener okListener) {
            new AlertDialog.Builder(Splash_Activity.this)
                    .setMessage("Permissions are required for this app")
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", okListener)
                    .create()
                    .show();
        }

        private void explain() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setMessage("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            //  permissionsclass.requestPermission(type,code);
                            Intent in=new  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +getPackageName()));
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            finish();
                        }
                    });

            dialog.show();

        }

        @Override
        protected void onRestart() {
            super.onRestart();
            if (isOnline())
            {
                if (checkAndRequestPermissions())
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            SharedPreferences sharedpreference_splash = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);




                            {

                                if(gps_enabled) {
                                    Intent intent=new Intent(Splash_Activity.this, MainActivity.class);
                                    intent.putExtra("user_type","2");
                                    startActivity(intent);
                                    finish();

                                }else
                                {
                                    gps_error_message();
                                }



                            }
                        }
                    }, SPLASH_TIME_OUT);

                }
            }

        }


    }