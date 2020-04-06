package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        loginBtn.setOnClickListener(this);

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
        if (v.getId() == R.id.login) {
            loadingBar.setVisibility(View.VISIBLE);
            tryLogin(email.getText().toString(), password.getText().toString());
        }
    }

    public void tryLogin(String username, String pass) {
        controller.loginUser(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
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
}
