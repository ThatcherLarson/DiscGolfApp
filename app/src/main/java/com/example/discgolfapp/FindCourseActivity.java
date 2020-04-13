package com.example.discgolfapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.MapAdapter;
import controllers.CoursesController;
import models.CoursesModel;
import models.DiscMap;

import static util.Constants.MAPVIEW_BUNDLE_KEY;

public class FindCourseActivity extends AppCompatActivity implements OnMapReadyCallback {
    private CoursesController controller;
    private CoursesModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;
    private MapView mMapView;
    private RecyclerView mMapRecyclerView;
    private Button createMap;
    private Button btnNewGame;
    GoogleMap googleMap;
    //vars
    public ArrayList<DiscMap> mMapList = new ArrayList<>();
    private MapAdapter myAdapter;
    private RecyclerView mapList;
    private static final String TAG = "FindCourseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_course);

        controller = new CoursesController(this);
        model = new CoursesModel();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        loadDataWithMiles(new FirestoreCallBack() {
            @Override
            public void onCallback(ArrayList<DiscMap> list) {
                mMapList = list;
                myAdapter.update(mMapList);
                myAdapter.notifyDataSetChanged();
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
        createMap = findViewById(R.id.addCourse);

        myAdapter = new MapAdapter(mMapList);
        mapList = findViewById(R.id.course_list_container);
        mapList.setAdapter(myAdapter);
        mapList.setLayoutManager(new LinearLayoutManager(this));
        btnNewGame = findViewById(R.id.btnNewGame);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        createMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindCourseActivity.this, EnterCourseActivity.class));
            }
        });
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindCourseActivity.this, GamesActivity.class));
            }
        });



        //MapController myMapController = myMapView.getController();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    private void loadDataWithMiles(final FirestoreCallBack firestoreCallBack){
        db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> favIds = (ArrayList<String>) task.getResult().get("fav_courses");
                loadCourses(firestoreCallBack, favIds);
            }
        });

    }

    private void loadCourses(final FirestoreCallBack callback, final ArrayList<String> ids) {
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
                                    System.out.println(documentId);
                                    String description = dfCourse.getString("Description");
                                    GeoPoint location = dfCourse.getGeoPoint("Location");
                                    ArrayList<Integer> pars = (ArrayList<Integer>) dfCourse.get("Pars");
                                    String title = dfCourse.getString("Title");
                                    ArrayList<Integer> yards = (ArrayList<Integer>) dfCourse.get("Yards");
                                    DiscMap newCourse = new DiscMap(documentId, title, description, location, pars, yards);
                                    newCourse.setMilesAway(currentLongitude, currentLatitude);
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


    private interface FirestoreCallBack{
        void onCallback(ArrayList<DiscMap> list);
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
        loadDataWithMiles(new FirestoreCallBack() {
            @Override
            public void onCallback(ArrayList<DiscMap> list) {
                mMapList = list;
                myAdapter.update(mMapList);
                myAdapter.notifyDataSetChanged();
                for (DiscMap course : mMapList) {
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(course.getLatitude(), course.getLongitude()));
                    marker.title(course.getTitle());
                    marker.snippet(course.getDescription());
                    googleMap.addMarker(marker);

                }
            }
        });
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
        googleMap = map;


        final LatLng Madison = new LatLng(43.073929, -89.385239);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Madison, 4.0f));

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        //respond to menu item selection
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
