package com.example.discgolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import controllers.ProfileController;
import models.ProfileModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ProfileController controller;
    private ProfileModel model;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        controller = new ProfileController(auth);
        model = new ProfileModel();

        btnSignOut = findViewById(R.id.signOutBtn);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signOutBtn) {
            controller.signOutUser();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }
}
