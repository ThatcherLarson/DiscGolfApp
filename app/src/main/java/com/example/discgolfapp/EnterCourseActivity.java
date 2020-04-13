package com.example.discgolfapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ParAdapter;
import controllers.CoursesController;
import models.CoursesModel;
import models.DiscMap;

import static util.Constants.MAPVIEW_BUNDLE_KEY;

public class EnterCourseActivity extends AppCompatActivity implements OnMapReadyCallback,NumberPicker.OnValueChangeListener {
    private CoursesController controller;
    private CoursesModel model;
    private FirebaseAuth auth;
    private double longitude;
    private double latitude;
    private GoogleMap googleMap;
    private MapView mMapView;
    private Button saveBtn;
    private RecyclerView parList;
    private NumberPicker parNumPick;
    private ParAdapter myAdapter;
    public ArrayList<DiscMap> mMapList = new ArrayList<>();

    String TAG = "EnterCourseActivity";
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

        loadDataWithMiles(new EnterCourseActivity.FirestoreCallBack() {
            @Override
            public void onCallback(ArrayList<DiscMap> list) {
                mMapList = list;
                for (DiscMap course : mMapList) {
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(course.getLatitude(), course.getLongitude()));
                    marker.title(course.getTitle());
                    marker.snippet(course.getDescription());
                    googleMap.addMarker(marker);
                }
            }
        });

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                save_course();
        }
        });






        //MapController myMapController = myMapView.getController();

    }


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
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        final LatLng Madison = new LatLng(43.073929, -89.385239);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Madison, 4.0f));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            return;
        }
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


    //TODO check if pars and yards are numbers
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save_course(){


        final int firstVisibleItemPosition = 0;
        final int lastVisibleItemPosition = parNumPick.getValue();
        parList.requestLayout();
        ArrayList<Integer> pars = new ArrayList<>();
        ArrayList<Double> yards = new ArrayList<>();

        for (String yard: myAdapter.get_yards()){
            if(yard.equals("")){
                yards.add(0.0);
            }
            else {
                yards.add(Double.parseDouble(yard));
            }
        }

        for (String par: myAdapter.get_pars()){
            if(par.equals("")){
                pars.add(0);
            }
            else {
                pars.add(Integer.parseInt(par));
            }
        }


        int mWidth= mMapView.getResources().getDisplayMetrics().widthPixels;
        int mHeight= mMapView.getResources().getDisplayMetrics().heightPixels;

        Point x_y_points = new Point(mWidth, mHeight);
        LatLng latLng = googleMap.getCameraPosition().target;//googleMap.getProjection().fromScreenLocation(x_y_points);
        longitude = latLng.longitude;
        latitude = latLng.latitude;

        String courseTitleData = ((TextView)findViewById(R.id.courseTitle)).getText().toString();
        String courseDescriptionData = ((TextView)findViewById(R.id.courseDescription)).getText().toString();
        ImageView courseImage = (ImageView) findViewById(R.id.courseImage);
        Matrix courseImageData = courseImage.getImageMatrix();

        // Create a new course object with information
        Map<String, Object> course = new HashMap<>();
        course.put("Description", courseDescriptionData);
        course.put("Location", new GeoPoint(latitude,longitude));
        course.put("Pars", pars);
        course.put("Title", courseTitleData);
        course.put("Yards", yards);


        Instant.now().toEpochMilli(); //Long = 1450879900184
        long secondsSinceEpoch = Instant.now().getEpochSecond();


        DocumentReference courseData = db.collection("courses").document(String.valueOf(secondsSinceEpoch));
        courseData.set(course);

    }



    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    private void loadDataWithMiles(final EnterCourseActivity.FirestoreCallBack firestoreCallBack){
        db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> favIds = (ArrayList<String>) task.getResult().get("fav_courses");
                loadCourses(firestoreCallBack, favIds);
            }
        });

    }

    private interface FirestoreCallBack{
        void onCallback(ArrayList<DiscMap> list);
    }


    private void loadCourses(final EnterCourseActivity.FirestoreCallBack callback, final ArrayList<String> ids) {
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<DiscMap> mMapTempList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for (DocumentSnapshot dfCourse : myListOfDocuments) {
                                String documentId = dfCourse.getId();
                                if (isInteger(documentId) && documentId.length() == 10) {
                                    String description = dfCourse.getString("Description");
                                    GeoPoint location = dfCourse.getGeoPoint("Location");
                                    ArrayList<Integer> pars = (ArrayList<Integer>) dfCourse.get("Pars");
                                    String title = dfCourse.getString("Title");
                                    ArrayList<Integer> yards = (ArrayList<Integer>) dfCourse.get("Yards");
                                    DiscMap newCourse = new DiscMap(documentId, title, description, location, pars, yards);
                                    if (ids != null) {
                                        if (ids.size() > 0) {
                                            newCourse.setFavorite(ids.contains(documentId));
                                        }
                                    }
                                    mMapTempList.add(newCourse);
                                }
                            }
                            callback.onCallback(mMapTempList);
                        }
                        else{
                            Log.d(TAG,"Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


       myAdapter.update(newVal,oldVal);
       parList.requestLayout();
        //parList.setAdapter(myAdapter);
        //parList.setLayoutManager(new LinearLayoutManager(this));
    }



}
