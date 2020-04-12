package com.example.discgolfapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import controllers.LoginController;
import models.LoginModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginController controller;
    private LoginModel model;
    private FirebaseAuth auth;

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private ProgressBar loadingBar;
    private TextView incorrectLoginText;
    private CheckBox remember;
    private Button btnForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        controller = new LoginController(auth, this);
        model = new LoginModel();

        // initialize view elements
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        loadingBar = findViewById(R.id.loading);
        incorrectLoginText = findViewById(R.id.textIncorrectLogin);
        remember = findViewById(R.id.btnRemember);
        btnForgot = findViewById(R.id.btnForgot);

        loginBtn.setOnClickListener(this);
        btnForgot.setOnClickListener(this);

        // initially hide some view elements
        loadingBar.setVisibility(View.INVISIBLE);
        incorrectLoginText.setVisibility(View.INVISIBLE);

        // get user's login info if saved
        String [] loginInfo = controller.getLoginInfo();
        if (loginInfo != null) {
            email.setText(loginInfo[0], TextView.BufferType.EDITABLE);
            password.setText(loginInfo[1], TextView.BufferType.EDITABLE);
            remember.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                loadingBar.setVisibility(View.VISIBLE);
                tryLogin(email.getText().toString(), password.getText().toString());
                break;

            case R.id.btnForgot:
                btnForgot.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                buildForgotPassDialog();
        }
    }

    public void tryLogin(String username, String pass) {
        controller.loginUser(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), FindCourseActivity.class);
                    startActivity(intent);
                    controller.saveUserandPass(email.getText().toString(), password.getText().toString(), remember.isChecked());
                } else {
                    incorrectLoginText.setVisibility(View.VISIBLE);
                    email.getText().clear();
                    password.getText().clear();
                }
            }
        });
    }

    private void buildForgotPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_forgot_password, null);
        builder.setView(view);
        final EditText email = view.findViewById(R.id.editForgotEmail);
        builder.setTitle(R.string.string_enter_email);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                forgotPass(email.getText().toString());
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

    public void forgotPass(String email) {
        controller.resetPassword(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "A password reset email has been sent to your account email.", Toast.LENGTH_LONG).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "An exception occurred: " + message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
