package com.example.discgolfapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import adapters.ParAdapter;
import controllers.CoursesController;
import models.CoursesModel;

import static util.Constants.MAPVIEW_BUNDLE_KEY;

public class EnterCourseActivity extends AppCompatActivity implements OnMapReadyCallback,NumberPicker.OnValueChangeListener {
    private CoursesController controller;
    private CoursesModel model;
    private FirebaseAuth auth;

    private MapView mMapView;
    private Button saveBtn;
    private RecyclerView parList;
    private NumberPicker parNumPick;
    private ParAdapter myAdapter;

    private ArrayList<String> pars;
    private ArrayList<String> yards;

    FirebaseFirestore db;
    //vars


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_course);
        db = FirebaseFirestore.getInstance();
        controller = new CoursesController(this);
        model = new CoursesModel();
        auth = FirebaseAuth.getInstance();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.map_new_game);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        saveBtn = findViewById(R.id.saveCourse);
        parList = findViewById(R.id.pars);
        parNumPick = findViewById(R.id.npPars);




        final NumberPicker np = (NumberPicker) parNumPick;
        np.setMaxValue(36);
        np.setMinValue(1);
        np.setValue(9);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);


        myAdapter = new ParAdapter(np.getValue());

        parList.setAdapter(myAdapter);
        parList.setLayoutManager(new LinearLayoutManager(this));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_course();
        }
        });


        //MapController myMapController = myMapView.getController();

    }

    /*
    private void initUserListRecyclerView() {
        mMapAdapter = new MapAdapter(mMapList);
        mMapRecyclerView.setAdapter(mMapAdapter);
        mMapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

    }

     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.addMarker(new MarkerOptions().position(new LatLng(43.073929,-89.385239)).title("Marker"));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.animateCamera(CameraUpdateFactory.zoomTo( 5.0f ) );
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        map.setMyLocationEnabled(true);


    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    //TODO save course
    public void save_course(){

        ArrayList<String> pars = myAdapter.get_pars();
        ArrayList<String> yards = myAdapter.getYards();
        TextView courseTitle = (TextView)findViewById(R.id.courseTitle);
        TextView courseDescription = (TextView)findViewById(R.id.courseDescription);
        ImageView courseImage = (ImageView) findViewById(R.id.courseImage);
        String courseTitleData = courseTitle.toString();
        String courseDescriptionData = courseDescription.toString();
        Matrix courseImageData = courseImage.getImageMatrix();

    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


       myAdapter.update(newVal,oldVal);
       parList.requestLayout();
        //parList.setAdapter(myAdapter);
        //parList.setLayoutManager(new LinearLayoutManager(this));
    }



}