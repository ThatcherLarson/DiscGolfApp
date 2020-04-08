package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import controllers.ProfileController;
import models.ProfileModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ProfileController controller;
    private ProfileModel model;
    private Button btnSignOut;
    private Button editNameBtn;
    private TextView textName;
    private Button btnResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        controller = new ProfileController(auth);
        model = new ProfileModel();

        btnSignOut = findViewById(R.id.signOutBtn);
        editNameBtn = findViewById(R.id.btnEditName);
        textName = findViewById(R.id.textName);
        btnResetPass = findViewById(R.id.btnResetPassword);

        btnSignOut.setOnClickListener(this);
        editNameBtn.setOnClickListener(this);
        btnResetPass.setOnClickListener(this);

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            textName.setText(user.getDisplayName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOutBtn:
                signOut();
                break;

            case R.id.btnEditName:
                Toast.makeText(this, "EditName Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnResetPassword:
                controller.resetPassword().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "A password reset email has been sent to your account email.", Toast.LENGTH_LONG).show();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "An exception occurred: " + message, Toast.LENGTH_LONG).show();
                        }
                        signOut();
                    }
                });
                break;
            default:
        }
    }

    public void signOut() {
        controller.signOutUser();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


}
