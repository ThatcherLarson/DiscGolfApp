package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.MeasureThrowController;
import models.MeasureThrowModel;

public class MeasureThrowActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
    LocationManager locationManager;
    private MeasureThrowController controller;
    private MeasureThrowModel model;
    private FirebaseAuth auth;

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    TextView latTextView, lonTextView;
    TextView distTextView;

    private double lat = 0;
    private double lon = 0;
    float distanceInMeters = 0;
    float distanceInFeet = 0;
    String distFeet = "";
    Location locA = new Location("");
    Location locB = new Location("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_throw);

        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button endBtn = (Button) findViewById(R.id.endBtn);
        Button clearBtn = (Button) findViewById(R.id.clearBtn);
        Button calcDistance = (Button) findViewById(R.id.calcDistance);
        Button saveThrowBtn = (Button) findViewById(R.id.saveThrowBtn);
        latTextView = findViewById(R.id.latTextView);
        lonTextView = findViewById(R.id.lonTextView);
        distTextView = findViewById(R.id.distTextView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mMapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        locA.setLatitude(lat);
                        locA.setLongitude(lon);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        locB.setLatitude(lat);
                        locB.setLongitude(lon);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

            }
        });

        calcDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        distanceInMeters = locA.distanceTo(locB);
                        distanceInFeet = distanceInMeters * (float) (1 / .3048);
                        distTextView.setText("Distance: " + Math.round(distanceInFeet) + " feet");
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmap.clear();
                lat = 0;
                lon = 0;
                distanceInFeet = 0;
                distanceInMeters = 0;
                distFeet = "";
            }
        });

        saveThrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference userDoc = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> ids = (ArrayList<String>) task.getResult().get("throws");
                            if (ids == null) {
                                ids = new ArrayList<String>();
                            }
                            ids.add(distFeet + " feet");
                            Map<String, Object> data = new HashMap<>();
                            data.put("throws", ids);
                            userDoc.set(data, SetOptions.merge());
                            Toast.makeText(getApplicationContext(), "Throw successfully saved!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        LatLng spot = new LatLng(lat, lon);
        latTextView.setText("Latitude: " + lat + "");
        lonTextView.setText("Longitude: " + lon + "");
        gmap.setMinZoomPreference(19);
        gmap.setMapType(gmap.MAP_TYPE_HYBRID);
        gmap.addMarker(new MarkerOptions().position(spot));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(spot));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MeasureThrowActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        gmap.setMapType(gmap.MAP_TYPE_HYBRID);
        gmap.setMinZoomPreference(12);
        LatLng start = new LatLng(43.073051, -89.401230);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(start));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find:
                startActivity(new Intent(this, FindCourseActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.more:
                startActivity(new Intent(this, MoreActivity.class));
                return true;
            case R.id.bag:
                startActivity(new Intent(this, MyBagActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
