package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import controllers.LoginController;
import models.LoginModel;

public class CoursesActivity extends AppCompatActivity {
    private LoginController controller;
    private LoginModel model;
    private FirebaseAuth auth;

    private Button btnNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        controller = new LoginController(this);
        model = new LoginModel();
        auth = FirebaseAuth.getInstance();

        // initialize view elements
        btnNewGame = findViewById(R.id.btnNewGame);
    }
}
