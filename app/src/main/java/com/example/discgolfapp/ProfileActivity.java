package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
    private TextView textName;
    private TextView textEmail;
    private Button btnResetPass;
    private ImageButton btnEditName;
    private ImageButton btnEditEmail;
    private ProgressBar update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        controller = new ProfileController(auth);
        model = new ProfileModel();

        btnSignOut = findViewById(R.id.signOutBtn);
        btnEditName = findViewById(R.id.btnEditName);
        btnEditEmail = findViewById(R.id.btnEditEmail);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmailAddr);
        btnResetPass = findViewById(R.id.btnResetPassword);
        update = findViewById(R.id.updateBar);

        btnSignOut.setOnClickListener(this);
        btnEditName.setOnClickListener(this);
        btnEditEmail.setOnClickListener(this);
        btnResetPass.setOnClickListener(this);

        update.setVisibility(View.INVISIBLE);

        FirebaseUser user = auth.getCurrentUser();

        updateUi(user);
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {
            textName.setText(user.getDisplayName());
            textEmail.setText(user.getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOutBtn:
                signOut();
                break;

            case R.id.btnResetPassword:
                update.setVisibility(View.VISIBLE);
                controller.resetPassword().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        update.setVisibility(View.INVISIBLE);
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

            case R.id.btnEditName:
                buildEditDialog(false);
                break;

            case R.id.btnEditEmail:
                buildEditDialog(true);
                break;

            default:
        }
    }

    private void signOut() {
        controller.signOutUser();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void buildEditDialog(final boolean editEmail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_profile, null);
        builder.setView(view);
        final EditText value = view.findViewById(R.id.editField);
        String title = getResources().getString(R.string.string_change) + " ";
        if (editEmail) {
            title +=  getResources().getString(R.string.prompt_email);
        } else {
            title += getResources().getString(R.string.string_name);
        }

        builder.setTitle(title);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update.setVisibility(View.VISIBLE);
                controller.updateProfile(editEmail, value.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        update.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful() && editEmail) {
                            Toast.makeText(getApplicationContext(), "A verification email has been sent to your new email. Please verify your new email.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                        } else if (task.isSuccessful()) {
                            updateUi(auth.getCurrentUser());
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "An error has occurred: " + message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
