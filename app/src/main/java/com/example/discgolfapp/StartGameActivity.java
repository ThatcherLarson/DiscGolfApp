package com.example.discgolfapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapters.UsernameAdapter;
import controllers.CoursesController;
import models.CoursesModel;
import models.DiscMap;


public class StartGameActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private CoursesController controller;
    private CoursesModel model;
    private FirebaseAuth auth;

    private Button startGame;
    private RecyclerView userList;
    private NumberPicker playerNumber;
    public DiscMap discMap;
    private UsernameAdapter myAdapter;
    private String courseId;

    public ArrayList<String> users = new ArrayList<>();

    String TAG = "StartGameActivity";

    FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_users);
        db = FirebaseFirestore.getInstance();

        controller = new CoursesController(this);
        model = new CoursesModel();
        auth = FirebaseAuth.getInstance();

        startGame = findViewById(R.id.start_game);
        userList = findViewById(R.id.user_list);
        playerNumber = findViewById(R.id.user_spinner);

        Bundle bundleData = getIntent().getExtras();
        discMap = bundleData.getParcelable("Map");




        final NumberPicker np = playerNumber;
        np.setMaxValue(9);
        np.setMinValue(1);
        np.setValue(3);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);


        myAdapter = new UsernameAdapter(np.getValue());

        userList.setAdapter(myAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Instant.now().toEpochMilli(); //Long = 1450879900184
        long secondsSinceEpoch = Instant.now().getEpochSecond();

        courseId = discMap.getId()+"-"+Long.toString(secondsSinceEpoch);


        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.requestLayout();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Map",discMap);
                bundle.putStringArrayList("Names",myAdapter.get_names());
                bundle.putString("CourseId",courseId);
                bundle.putBoolean("LoadDB",false);
                createNewGame(myAdapter.get_names(),discMap);
                Intent intent = new Intent(StartGameActivity.this, HoleActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }


        });

    }
    private void createNewGame(ArrayList<String> names, DiscMap discMap) {

        Map<String, Object> course = new HashMap<>();
        course.put("Location", discMap.getLocation());
        course.put("Pars", discMap.getPars());
        course.put("Yards",discMap.getYards());
        course.put("Names",myAdapter.get_names());


        Map<String, Object> userScores = new HashMap<>();
        ArrayList<Integer> emptyValues = new ArrayList<>();
        System.out.println(discMap.getPars());
        for (int val = 0; val < discMap.getPars().size(); val++){
            emptyValues.add(0);
        }
        userScores.put("Pars", emptyValues);

        for(int i = 1; i <= discMap.getNumPars(); i++){

            userScores.put("Location"+ i,new ArrayList<Object>());
        }

        for(int i = 0; i < myAdapter.get_names().size(); i++){
            course.put("User"+i,userScores);
        }

        DocumentReference courseData = db.collection("users").document(auth.getCurrentUser().getUid()).collection("games").document(courseId);
        courseData.set(course);





    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        myAdapter.update(newVal, oldVal);
        userList.requestLayout();
    }



}
