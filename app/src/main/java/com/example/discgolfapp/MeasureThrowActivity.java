package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
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

import controllers.CoursesController;
import controllers.MeasureThrowController;
import models.CoursesModel;
import models.MeasureThrowModel;

public class MeasureThrowActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MeasureThrowController controller;
    private MeasureThrowModel model;
    private FirebaseAuth auth;

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    TextView distTextView;

    double initLatitude = 0;
    double initLongitude = 0;
    double endLatitude = 0;
    double endLongitude = 0;
    float distanceInMeters = 0;
    Location loc1 = new Location("");
    Location loc2 = new Location("");

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
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
                getLastLocationStart();
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocationEnd();
            }
        });

        calcDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loc1.setLatitude(initLatitude);
                loc1.setLongitude(initLongitude);
                loc2.setLatitude(endLatitude);
                loc2.setLongitude(endLongitude);
                if (initLatitude != 0 && endLatitude != 0) {
                    LatLngBounds THROW = new LatLngBounds(new LatLng(initLatitude, initLongitude), new LatLng(endLatitude, endLongitude));
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(THROW.getCenter(), 19));
                }
                distanceInMeters = loc1.distanceTo(loc2);
                distTextView.setText("Distance: " + distanceInMeters + " meters");
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmap.clear();
                initLatitude = 0;
                initLongitude = 0;
                endLatitude = 0;
                endLongitude = 0;
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
                            ids.add(distanceInMeters + " meters");
                            Map<String, Object> data = new HashMap<>();
                            data.put("throws", ids);
                            userDoc.set(data, SetOptions.merge());
                        }
                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocationStart(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    initLatitude = location.getLatitude();
                                    initLongitude = location.getLongitude();
                                    latTextView.setText("Latitude: " + location.getLatitude()+"");
                                    lonTextView.setText("Longitude: " + location.getLongitude()+"");
                                    gmap.setMinZoomPreference(19);
                                    gmap.setMapType(gmap.MAP_TYPE_HYBRID);
                                    LatLng init = new LatLng(initLatitude, initLongitude);
                                    gmap.addMarker(new MarkerOptions().position(init));
                                    gmap.moveCamera(CameraUpdateFactory.newLatLng(init));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocationEnd(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    endLatitude = location.getLatitude();
                                    endLongitude = location.getLongitude();
                                    latTextView.setText("Latitude: " + location.getLatitude()+"");
                                    lonTextView.setText("Longitude: " + location.getLongitude()+"");
                                    gmap.setMinZoomPreference(19);
                                    gmap.setMapType(gmap.MAP_TYPE_HYBRID);
                                    LatLng end = new LatLng(initLatitude, initLongitude);
                                    gmap.addMarker(new MarkerOptions().position(end));
                                    gmap.moveCamera(CameraUpdateFactory.newLatLng(end));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latTextView.setText(mLastLocation.getLatitude()+"");
            lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocationStart();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocationStart();
            mapView.onResume();
        }

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
}
