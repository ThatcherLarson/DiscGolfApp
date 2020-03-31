package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import controllers.WelcomeController;
import models.WelcomeModel;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private WelcomeController controller;
    private WelcomeModel model;

    private FirebaseAuth auth;

    private Button loginBtn;
    private Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // initialize model and controller to handle view events
        auth = FirebaseAuth.getInstance();
        controller = new WelcomeController(this, auth);
        model = new WelcomeModel();

        // initialize view elements
        loginBtn = findViewById(R.id.btnLogin);
        createAccountBtn = findViewById(R.id.btnSignUp);

        loginBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);

        // try to log the user in if they have previously
        if (controller.tryAutoLogin()) {
            Intent intent = new Intent(this, CoursesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnLogin:
                intent = controller.clickLogin();
                startActivity(intent);
                break;

            case R.id.btnSignUp:
                intent = controller.clickCreate();
                startActivity(intent);
                break;

        }
    }
}
