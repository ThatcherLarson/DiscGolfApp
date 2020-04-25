package com.example.discgolfapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ParAdapter;
import controllers.HoleController;
import models.CourseThrows;
import models.DiscMap;
import models.HoleModel;
import models.LocalUser;
import models.Throw;
import models.UserCourse;

import static util.Constants.MAPVIEW_BUNDLE_KEY;

public class HoleActivity extends AppCompatActivity implements OnMapReadyCallback, OnItemSelectedListener, AdapterView.OnItemClickListener  {
    private HoleController controller;
    private HoleModel model;
    private FirebaseAuth auth;
    private double longitude;
    private double latitude;
    private GoogleMap googleMap;
    private MapView mMapView;
    private Button nextHole;
    private Button previousHole;
    private RecyclerView parList;
    private NumberPicker parNumPick;

    private boolean start;
    private boolean end;
    private Button startHole;
    private Button endHole;
    private Button clearMap;
    private Button undoThrow;
    private Button addThrow;
    private EditText enterPar;
    private ParAdapter myAdapter;
    private DiscMap discMap;
    private TextView parForCourse;
    private TextView yardage;
    private TextView textLongitude;
    private TextView textLatitude;
    private TextView distance;
    private Spinner playerNames;
    private int playerPosition;
    private Integer parPosition;
    private Marker startMarker;
    private Marker endMarker;
    private String courseId;
    private TextView holeNum;

    String TAG = "HoleActivity";
    private GeoPoint startLocation;
    private GeoPoint endLocation;

    private ArrayList<Integer> parVals;
    private ArrayList<Integer> yardVals;

    private ArrayList<String> players;
    private ArrayList<LocalUser> localPlayers;
    //the outer mapping maps holes to player hole throws
    //the inner mapping maps players to their course throws
    private Map<Integer, UserCourse> playersAndThrows;
    List<Polyline> polylines;

    FirebaseFirestore db;

    public Context getContext() {
        return (Context) this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole);
        db = FirebaseFirestore.getInstance();

        controller = new HoleController(this);
        model = new HoleModel();
        auth = FirebaseAuth.getInstance();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.hole_view);

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        Bundle bundleData = getIntent().getExtras();
        discMap = getIntent().getParcelableExtra("Map");
        boolean loadDB = bundleData.getBoolean("LoadDB");
        courseId = bundleData.getString("CourseId");

        parVals=discMap.getPars();
        yardVals=discMap.getYards();

        parPosition = (int)0;



        polylines = new ArrayList<Polyline>();
        start = false;
        end = false;


        //link
        nextHole = findViewById(R.id.next_hole);
        previousHole = findViewById(R.id.previous_hole);

        addThrow = findViewById(R.id.addThrow);
        startHole = findViewById(R.id.startMeasurement);
        endHole = findViewById(R.id.endMeasurement);


        yardage = findViewById(R.id.yardage);
        parForCourse = findViewById(R.id.parNumbers);
        holeNum = findViewById(R.id.holeNum);

        holeNum.setText(Integer.toString(parPosition + 1));

        distance = findViewById(R.id.distance);
        clearMap = findViewById(R.id.clearMap);

        playerNames = findViewById(R.id.playerNames);
        undoThrow = findViewById(R.id.undo_throw);
        enterPar = findViewById(R.id.enterPar);



        previousHole.setVisibility(View.GONE);

        playerPosition = 0;


        final Spinner sp = playerNames;



        if(loadDB) {
            //disable loading buttons during initial async load
            nextHole.setEnabled(false);
            playerNames.setEnabled(false);


            loadDataOnCourse(new HoleActivity.FirestoreCallBack() {
                @Override
                public void onCallback(Map<Integer, UserCourse> courseThrows) {
                    playersAndThrows = courseThrows;

                    ArrayList<String> playerArray = new ArrayList<>();

                    UserCourse holeThrows = courseThrows.get(playerPosition);
                    for (int i = 0; i < playersAndThrows.size(); i++){
                        playerArray.add(playersAndThrows.get(i).getName());
                    }

                    CourseThrows playerThrows = holeThrows.getUserThrows(parPosition);
                    for (int i = 0; i < playerThrows.numberOfThrows(); i++) {
                        Throw t = playerThrows.getThrow(i);
                        LatLng lstart = new LatLng(t.get_start().getLatitude(),t.get_start().getLongitude());
                        LatLng lend = new LatLng(t.get_end().getLatitude(),t.get_end().getLongitude());

                        Polyline line = googleMap.addPolyline(new PolylineOptions().add(lstart,lend).width(5).color(Color.RED));
                        polylines.add(line);


                    }
                    //enable interaction after data loads
                    nextHole.setEnabled(true);
                    playerNames.setEnabled(true);

                    UserCourse userP = playersAndThrows.get(playerPosition);
                    Integer pp = ((Number)parPosition).intValue();
                    Integer yar = ((Number)yardVals.get(pp)).intValue();
                    Integer pfc = ((Number)parVals.get(pp)).intValue();
                    Integer ugr = ((Number)userP.getParResults().get(pp)).intValue();

                    yardage.setText(Integer.toString(yar));
                    parForCourse.setText(Integer.toString(pfc));
                    enterPar.setText(Integer.toString(ugr));


                    players =playerArray;

                    //Spinner adapter
                    ArrayAdapter<String> playerDataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, playerArray);

                    playerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp.setAdapter(playerDataAdapter);


                }



            });


        }
        else{

            players = bundleData.getStringArrayList("Names");

            //create playersAndThrows
            playersAndThrows = new HashMap<Integer, UserCourse>();


            List<String> playerArray = new ArrayList<>();
            for (String player:players){
                playerArray.add(player);
            }

            //Spinner adapter
            ArrayAdapter<String> playerDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playerArray);

            playerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sp.setAdapter(playerDataAdapter);


            int it = 0;
            for(String name:players){
                //create UserCourse
                UserCourse myCourse = new UserCourse(discMap.getNumPars(), name);
                playersAndThrows.put(it,myCourse);

                it = it +1;
            }
            ArrayList<Integer> t = discMap.getPars();
            System.out.println(parPosition);
            //Integer a = t.get(Integer.getInteger(Long.toString(parPosition)));
            Integer pp = ((Number)parPosition).intValue();
            Integer yar = ((Number)yardVals.get(pp)).intValue();
            Integer pfc = ((Number)parVals.get(pp)).intValue();
            yardage.setText(Integer.toString(yar));
            parForCourse.setText(Integer.toString(pfc));


        }

        enterPar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {

                    final CharSequence tex = s;
                    playersAndThrows.get(playerPosition).setPar(parPosition,Integer.valueOf(tex.toString()));

                    final DocumentReference courseData = db.collection("users").document(auth.getCurrentUser().getUid()).collection("games").document(courseId);
                    courseData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                Map<String,Object> document = task.getResult().getData();
                                Map<String,Object> user = (Map<String, Object>) document.get("User"+playerPosition);
                                ArrayList<Object> pars = (ArrayList<Object>)user.get("Pars");

                                pars.set(parPosition, Integer.valueOf(Integer.valueOf(tex.toString())));
                                user.put("Pars",pars);
                                document.put("User"+playerPosition,user);

                                //set location
                                courseData.set(document);
                            }

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (start){
                    startMarker.remove();
                }

                start = true;
                int mWidth= mMapView.getResources().getDisplayMetrics().widthPixels;
                int mHeight= mMapView.getResources().getDisplayMetrics().heightPixels;
                Point x_y_points = new Point(mWidth, mHeight);
                LatLng latLng = googleMap.getCameraPosition().target;//googleMap.getProjection().fromScreenLocation(x_y_points);
                MarkerOptions sMarker = new MarkerOptions();
                sMarker.position(latLng);
                sMarker.title("");
                sMarker.snippet("");
                startMarker = googleMap.addMarker(sMarker);

                if(end && start){
                    Location locStart = new Location("");

                    LatLng stLatLng = startMarker.getPosition();
                    locStart.setLatitude(stLatLng.latitude);
                    locStart.setLongitude(stLatLng.longitude);

                    Location locEnd = new Location("");

                    LatLng enLatLng = endMarker.getPosition();
                    locEnd.setLatitude(enLatLng.latitude);
                    locEnd.setLongitude(enLatLng.longitude);


                    float distanceInMeters = locStart.distanceTo(locEnd);
                    float distanceInFeet = distanceInMeters * (float)(1/.3048);
                    distance.setText(Float.toString(distanceInFeet));
                }

            }
        });

        endHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (end){
                    endMarker.remove();
                }
                end = true;
                int mWidth= mMapView.getResources().getDisplayMetrics().widthPixels;
                int mHeight= mMapView.getResources().getDisplayMetrics().heightPixels;
                Point x_y_points = new Point(mWidth, mHeight);
                LatLng latLng = googleMap.getCameraPosition().target;//googleMap.getProjection().fromScreenLocation(x_y_points);
                MarkerOptions eMarker = new MarkerOptions();
                eMarker.position(latLng);
                endMarker = googleMap.addMarker(eMarker);


                if(end && start){
                    Location locStart = new Location("");

                    LatLng stLatLng = startMarker.getPosition();
                    locStart.setLatitude(stLatLng.latitude);
                    locStart.setLongitude(stLatLng.longitude);

                    Location locEnd = new Location("");

                    LatLng enLatLng = endMarker.getPosition();
                    locEnd.setLatitude(enLatLng.latitude);
                    locEnd.setLongitude(enLatLng.longitude);


                    float distanceInMeters = locStart.distanceTo(locEnd);
                    float distanceInFeet = distanceInMeters * (float)(1/.3048);
                    distance.setText(Float.toString(distanceInFeet));
                }

            }
        });

        addThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(end && start) {
                    Polyline line = googleMap.addPolyline(new PolylineOptions().add(startMarker.getPosition(), endMarker.getPosition()).width(5).color(Color.RED));
                    polylines.add(line);

                    GeoPoint startGeo = new GeoPoint(startMarker.getPosition().latitude,startMarker.getPosition().longitude);
                    GeoPoint endGeo = new GeoPoint(endMarker.getPosition().latitude,endMarker.getPosition().longitude);
                    Throw tr = new Throw(startGeo,endGeo);
                    playersAndThrows.get(playerPosition).addThrow(tr,parPosition);

                    final DocumentReference courseData = db.collection("users").document(auth.getCurrentUser().getUid()).collection("games").document(courseId);
                    courseData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot location = task.getResult();
                                Map<String,Object> mapLocation = location.getData();
                                Map<String,Object> users = ((Map<String,Object>)mapLocation.get("User"+playerPosition));
                                ArrayList<Object> geoPoints = (ArrayList<Object>)users.get("Location"+Integer.toString(parPosition+1));

                                //create new location
                                Map<String,GeoPoint> locationMap = new HashMap<String, GeoPoint>() ;
                                GeoPoint startGeo = new GeoPoint(startMarker.getPosition().latitude,startMarker.getPosition().longitude);
                                GeoPoint endGeo = new GeoPoint(endMarker.getPosition().latitude,endMarker.getPosition().longitude);
                                locationMap.put("Start",startGeo);
                                locationMap.put("End",endGeo);
                                //Add location start and end
                                geoPoints.add(locationMap);

                                //get data
                                //update par start and end
                                users.put("Location" + Integer.toString(parPosition+1),geoPoints);
                                mapLocation.put("User"+playerPosition,users);
                                //set location
                                courseData.set(mapLocation);
                            }

                        }
                    });
                    startMarker.remove();
                    endMarker.remove();
                }

            }
        });

        clearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Polyline line:polylines){
                    line.remove();
                }
                //reset playersAndThrows markerings
                playersAndThrows.get(playerPosition).resetPar(parPosition);
                if(end){
                    endMarker.remove();
                }
                if(start){
                    startMarker.remove();
                }

                //reset database
                final DocumentReference courseData = db.collection("users").document(auth.getCurrentUser().getUid()).collection("games").document(courseId);

                courseData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot location = task.getResult();
                            Map<String,Object> mapLocation = location.getData();
                            Map<String,Object> users = ((Map<String,Object>)mapLocation.get("User"+playerPosition));
                            ArrayList<GeoPoint> geoPoints = new ArrayList<>();
                            users.put("Location" + Integer.toString(parPosition+1),geoPoints);

                            mapLocation.put("User"+playerPosition,users);

                            courseData.set(mapLocation);
                        }

                    }
                });
            }
        });






        nextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parPosition = parPosition + 1;
                if (!discMap.getNumPars().equals(parPosition)) {
                    holeNum.setText(Integer.toString(parPosition + 1));
                }
                if (parPosition == 1){
                    previousHole.setVisibility(View.VISIBLE);
                }
                if((parPosition+1)==discMap.getNumPars()){
                    // nextHole.setVisibility(View.GONE);
                    nextHole.setText(R.string.finish);
                }
                if (discMap.getNumPars().equals(parPosition)) {
                    goToScorecard();

                }
                for(Polyline ref: polylines){
                    ref.remove();
                }
                polylines = new ArrayList<Polyline>();

                UserCourse userP = playersAndThrows.get(playerPosition);
                Map<Integer, CourseThrows> holeThrows = userP.getThrowList();

                if(start){
                    startMarker.remove();
                }
                if(end){
                    endMarker.remove();
                }
                Integer pp = ((Number) parPosition).intValue();
                if (!discMap.getNumPars().equals(parPosition)) {
                    Integer yar = ((Number) yardVals.get(pp)).intValue();
                    Integer pfc = ((Number) parVals.get(pp)).intValue();
                    Integer ugr = ((Number)userP.getParResults().get(pp)).intValue();
                    yardage.setText(Integer.toString(yar));
                    parForCourse.setText(Integer.toString(pfc));
                    enterPar.setText(Integer.toString(ugr));
                }


                CourseThrows playerThrows = holeThrows.get(pp);
                if (playerThrows != null) {
                    for (int i = 0; i < playerThrows.numberOfThrows(); i++) {
                        Throw t = playerThrows.getThrow(i);
                        LatLng lstart = new LatLng(t.get_start().getLatitude(), t.get_start().getLongitude());
                        LatLng lend = new LatLng(t.get_end().getLatitude(), t.get_end().getLongitude());

                        Polyline line = googleMap.addPolyline(new PolylineOptions().add(lstart, lend).width(5).color(Color.RED));
                        polylines.add(line);
                    }
                }


            }

        });


        previousHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parPosition = parPosition - 1;
                holeNum.setText(Integer.toString(parPosition + 1));
                if (parPosition == 0){
                    previousHole.setVisibility(View.GONE);
                }
                if((parPosition+1)==discMap.getNumPars()-1){
                    // nextHole.setVisibility(View.VISIBLE);
                    nextHole.setText(R.string.next_hole);
                }
                for(Polyline ref: polylines){
                    ref.remove();
                }
                polylines = new ArrayList<Polyline>();

                UserCourse userP = playersAndThrows.get(playerPosition);
                Map<Integer, CourseThrows> holeThrows = userP.getThrowList();


                Integer pp = ((Number)parPosition).intValue();
                Integer yar = ((Number)yardVals.get(pp)).intValue();
                Integer pfc = ((Number)parVals.get(pp)).intValue();
                Integer ugr = ((Number)userP.getParResults().get(pp)).intValue();
                yardage.setText(Integer.toString(yar));
                parForCourse.setText(Integer.toString(pfc));
                enterPar.setText(Integer.toString(ugr));

                if(start){
                    startMarker.remove();
                }
                if(end){
                    endMarker.remove();
                }

                CourseThrows playerThrows = holeThrows.get(pp);
                if (playerThrows != null) {
                    for (int i = 0; i < playerThrows.numberOfThrows(); i++){
                        Throw t = playerThrows.getThrow(i);
                        LatLng lstart = new LatLng(t.get_start().getLatitude(),t.get_start().getLongitude());
                        LatLng lend = new LatLng(t.get_end().getLatitude(),t.get_end().getLongitude());

                        Polyline line = googleMap.addPolyline(new PolylineOptions().add(lstart,lend).width(5).color(Color.RED));
                        polylines.add(line);
                    }
                }


            }

        });

        playerNames.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                playerPosition = position;

                for(Polyline ref: polylines){
                    ref.remove();
                }
                polylines = new ArrayList<Polyline>();

                UserCourse userP = playersAndThrows.get(playerPosition);
                Map<Integer, CourseThrows> holeThrows = userP.getThrowList();


                Integer pp = ((Number)parPosition).intValue();
                Integer yar = ((Number)yardVals.get(pp)).intValue();
                Integer pfc = ((Number)parVals.get(pp)).intValue();
                Integer ugr = ((Number)userP.getParResults().get(pp)).intValue();
                yardage.setText(Integer.toString(yar));
                parForCourse.setText(Integer.toString(pfc));
                enterPar.setText(Integer.toString(ugr));


                if(start){
                    startMarker.remove();
                }
                if(end){
                    endMarker.remove();
                }

                CourseThrows playerThrows = holeThrows.get(pp);
                if (playerThrows != null) {
                    for (int i = 0; i < playerThrows.numberOfThrows(); i++){
                        Throw t = playerThrows.getThrow(i);
                        LatLng lstart = new LatLng(t.get_start().getLatitude(),t.get_start().getLongitude());
                        LatLng lend = new LatLng(t.get_end().getLatitude(),t.get_end().getLongitude());

                        Polyline line = googleMap.addPolyline(new PolylineOptions().add(lstart,lend).width(5).color(Color.RED));
                        polylines.add(line);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        undoThrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 System.out.println("Size");
                 System.out.println(polylines.size());

                 if(polylines.size() != 0) {
                     polylines.get(polylines.size()-1).remove();
                     polylines.remove(polylines.size()-1);

                     playersAndThrows.get(playerPosition).getUserThrows(parPosition).removeLast();

                     final DocumentReference courseData = db.collection("users").document(auth.getCurrentUser().getUid()).collection("games").document(courseId);
                     courseData.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                             if (task.isSuccessful()) {
                                 //Start
                                 DocumentSnapshot document = task.getResult();
                                 Map<String,Object> doc = document.getData();
                                 Map<String,Object> playerThrows = ((Map<String,Object> )doc.get("User"+playerPosition));
                                 ArrayList<Object> geoPoints = (ArrayList<Object>) playerThrows.get("Location" + Integer.toString(parPosition+1));


                                 //get data
                                 geoPoints.remove(geoPoints.size()-1);
                                 playerThrows.put("Location" + Integer.toString(parPosition+1),geoPoints);
                                 doc.put("User"+playerPosition,playerThrows);
                                 //update par start and end
                                 //set location
                                 courseData.set(doc);
                             }

                         }
                     });
                 }
             }
         });





    getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(HoleActivity.this, FindCourseActivity.class);
        startActivity(setIntent);
    }

    private void loadDataOnCourse(final HoleActivity.FirestoreCallBack firestoreCallBack) {
        loadGames(firestoreCallBack);
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

        LatLng location = new LatLng(discMap.getLatitude(),discMap.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
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

    public void goToScorecard() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Names", players);
        bundle.putIntegerArrayList("Pars", parVals);
        int count = 0;
        for (String name:players) {
            bundle.putIntegerArrayList(name + "scores", playersAndThrows.get(count).getParResults());
            count++;
        }

        Intent intent = new Intent(this, ScorecardActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        playerPosition = position;

        for(Polyline ref: polylines){
            ref.remove();
        }
        polylines = new ArrayList<Polyline>();

        Map<Integer, CourseThrows> holeThrows = playersAndThrows.get(playerPosition).getThrowList();
        Integer pp = ((Number)parPosition).intValue();
        CourseThrows playerThrows = holeThrows.get(pp);
        if (playerThrows != null) {
            for (int i = 0; i < playerThrows.numberOfThrows(); i++) {
                Throw t = playerThrows.getThrow(i);
                LatLng lstart = new LatLng(t.get_start().getLatitude(), t.get_start().getLongitude());
                LatLng lend = new LatLng(t.get_end().getLatitude(), t.get_end().getLongitude());

                Polyline line = googleMap.addPolyline(new PolylineOptions().add(lstart, lend).width(5).color(Color.RED));
                polylines.add(line);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private interface FirestoreCallBack{
        void onCallback(Map<Integer, UserCourse> courseThrows);
    }

    private void loadGames(final HoleActivity.FirestoreCallBack callback) {
        db.collection("users").document(auth.getUid()).collection("games").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String,Object> loadGame = task.getResult().getData();
                ArrayList<String> names = (ArrayList<String>)loadGame.get("Names");

                Map<Integer, UserCourse> gameData = new HashMap<>();
                for (int i = 0; i < names.size(); i++){

                    ArrayList<Integer> strokes = (ArrayList<Integer>) ((Map<String,Object>)loadGame.get("User"+i)).get("Pars");
                    Map<Integer,CourseThrows> courseThrows = new HashMap<>();
                    for(int j = 0; j < strokes.size(); j++){
                        CourseThrows ct = new CourseThrows();
                        ArrayList<Object> parThrows = (ArrayList<Object>) ((Map<String,Object>)loadGame.get("User"+i)).get("Location"+Integer.toString(j+1));

                        for(Object parThrow: parThrows){
                            Map<String,GeoPoint> geoStartEnd = (Map<String, GeoPoint>) parThrow;
                            GeoPoint startGeo = geoStartEnd.get("Start");
                            GeoPoint endGeo = geoStartEnd.get("End");
                            Throw singleThrow = new Throw(startGeo,endGeo);
                            ct.addThrowEnd(singleThrow);
                        }


                        courseThrows.put(j,ct);
                    }

                    UserCourse myCourse = new UserCourse(strokes,courseThrows, names.get(i));
                    gameData.put(i,myCourse);


                }
                callback.onCallback(gameData);

            }

        });

    }



}
