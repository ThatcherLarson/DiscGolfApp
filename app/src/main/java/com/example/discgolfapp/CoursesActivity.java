package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import controllers.CoursesController;
import models.CoursesModel;
import models.RVAdapter;

public class CoursesActivity extends AppCompatActivity {
    private CoursesController controller;
    private CoursesModel model;
    private FirebaseAuth auth;

    private Button btnNewGame;
    private RecyclerView course_list;

    private String s1[], s2[];
    private int images[] = {R.drawable.disc_golf_basic};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        controller = new CoursesController(this);
        model = new CoursesModel();
        auth = FirebaseAuth.getInstance();

        // initialize view elements
        btnNewGame = findViewById(R.id.btnNewGame);
        course_list = findViewById(R.id.rvCourses);

        s1 = getResources().getStringArray(R.array.course_names);
        s2 = getResources().getStringArray(R.array.course_descriptions);

        RVAdapter myAdapter = new RVAdapter(this, s1, s2, images);
        course_list.setAdapter(myAdapter);
        course_list.setLayoutManager(new LinearLayoutManager(this));


        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoursesActivity.this, GamesActivity.class));
            }
        });
    }
}
