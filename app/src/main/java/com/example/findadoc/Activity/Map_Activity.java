package com.example.findadoc.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findadoc.Adapter.Categories_Space_Adapter;
import com.example.findadoc.Classes.GPSTracker;
import com.example.findadoc.Model.Categoried_Model;
import com.example.findadoc.Model.Categories_Response;
import com.example.findadoc.R;
import com.example.findadoc.Retrofir.Api_client;
import com.example.findadoc.sam.dev.Model.LocationStatusModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map_Activity extends AppCompatActivity implements OnMapReadyCallback {
    double longitudes = 0.00, latitudes = 0.00;
    String currentlatitude = "0.00", currentlongtide = "0.00";
    GPSTracker gps;
    private GoogleMap mMap;
    RelativeLayout relatice_back_change_password;
    private String key, locationStatus,user_id;
    private double mLatitude, mLongitude;
    public static String string;
    public static String lat_long;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("person_id", "");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        relatice_back_change_password = findViewById(R.id.relatice_back_change_password);
        relatice_back_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("latLng_data12", string);
                resultIntent.putExtra("lat_long", lat_long);
                setResult(Map_Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        key = getIntent().getStringExtra("key");
        locationStatus = getIntent().getStringExtra("locationStatus");

        //Log.e("locationStatus_check",locationStatus);

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        my_marker();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String title = marker.getTitle();

                Toast.makeText(getApplicationContext(), title + "", Toast.LENGTH_LONG).show();

            }
        });


//        personClusterManager.addItem(new Person(latLng,comppanyname,comppanyname));


    }

    private void my_marker() {
        if (key.equals("0")) {
            gps = new GPSTracker(getApplicationContext());
            if (gps.canGetLocation()) {

                latitudes = gps.getLatitude();
                longitudes = gps.getLongitude();
                currentlatitude = String.valueOf(latitudes);
                currentlongtide = String.valueOf(longitudes);
                createMarker(latitudes, longitudes, "My Location");
//            Toast.makeText(getActivity(),currentlatitude+"LL"+currentlongtide,Toast.LENGTH_LONG).show();

            }
        } else {
            try {
                latitudes = Double.parseDouble(getIntent().getStringExtra("lattitude"));
                longitudes = Double.parseDouble(getIntent().getStringExtra("longitude"));

                String city = getIntent().getStringExtra("city");


                createMarker(latitudes, longitudes, city);


            } catch (Exception e) {

            }
        }


    }


    public void createMarker(double latitude, double longitude, String comppanyname) {
        LatLng latLng = new LatLng(latitude, longitude);
        lat_long = latitude + "," + longitude;
//        personClusterManager.addItem(new Person(latLng,comppanyname,comppanyname));
        if (key.equals("1")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(), R.drawable.placeholder2x));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        } else if (key.equals("2")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(), R.drawable.clinic_icon));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);
        } else {

            string = String.valueOf(latLng);
            Log.e("latLng_Data", string);
            MarkerOptions options = new MarkerOptions().position(latLng).title(comppanyname).icon(BitmapFromVector(getApplicationContext(), R.drawable.placeholder2x));
            mMap.addMarker(options);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 13.0F);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(yourLocation);

              /*  Log.e("latLng" , String.valueOf(latLng));
           addMarker(latLng);*/

            if (locationStatus.equals("0")) {

            }else{

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        string = String.valueOf(latLng);
                        Log.e("latLng_Data", string);
                        // Setting the position for the marker
                        markerOptions.position(latLng);
                        Log.e("Lat_Long", String.valueOf(latLng));
                        // Setting the title for the marker.
                        // This will be displayed on taping the marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                        // Clears the previously touched position
                        mMap.clear();

                        // Animating to the touched position
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0F));

                        // Placing a marker on the touched position
                        mMap.addMarker(markerOptions);
                    }
                });
            }

        }
//        personClusterManager.cluster();
/*
        personClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Person>() {
            @Override
            public boolean onClusterClick(Cluster<Person> cluster) {
                for (Person p:
                     cluster.getItems()) {
                    Toast.makeText(getActivity(), ""+p.getTitle(), Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
*/

    }
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        //vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        //Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        // below line is use to add bitmap in our canvas.
        //Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        //vectorDrawable.draw(canvas);

        Bitmap sccale = Bitmap.createScaledBitmap(((BitmapDrawable)vectorDrawable).getBitmap(), 96, 96, false);


        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(sccale);
    }



    private void addMarker(LatLng coordinate) {
        mLatitude = coordinate.latitude;
        mLongitude = coordinate.longitude;
        if (mMap != null) {
            LatLng latLng = new LatLng(mLatitude, mLongitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
        }
    }
}