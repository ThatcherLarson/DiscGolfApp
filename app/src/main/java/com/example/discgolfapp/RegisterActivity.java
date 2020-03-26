package com.example.discgolfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import controllers.RegisterController;
import models.RegisterModel;
import models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterController controller;
    private RegisterModel model;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button registerBtn;
    private TextView passMatch;
    private TextView passReq;
    private TextView formError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new RegisterController(auth);
        model = new RegisterModel(db);

        firstName = findViewById(R.id.textFirstName);
        lastName = findViewById(R.id.textLastName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        confirmPassword = findViewById(R.id.textConfirmPass);
        registerBtn = findViewById(R.id.registerBtn);
        passMatch = findViewById(R.id.textPasswordMatch);
        passReq = findViewById(R.id.textPasswordReqs);
        formError = findViewById(R.id.textFormError);

        passMatch.setVisibility(View.INVISIBLE);
        passReq.setVisibility(View.INVISIBLE);
        formError.setVisibility(View.GONE);

        registerBtn.setOnClickListener(this);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() < 8) {
                    passReq.setVisibility(View.VISIBLE);
                } else {
                    passReq.setVisibility(View.INVISIBLE);
                }
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(password.getText().toString())) {
                    passMatch.setVisibility(View.VISIBLE);
                    registerBtn.setEnabled(false);
                } else {
                    passMatch.setVisibility(View.INVISIBLE);
                    if (password.getText().toString().length() >= 8) {
                        registerBtn.setEnabled(true);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn) {
            boolean formVal = controller.verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                    email.getText().toString(), password.getText().toString());
            if (formVal) {
                final String first = firstName.getText().toString();
                final String last = lastName.getText().toString();
                final String uName = email.getText().toString();
                final String pass = password.getText().toString();
                formError.setVisibility(View.GONE);
                controller.createUser(uName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(first, last, uName, auth.getCurrentUser().getUid());
                            addUser(user);  // add a document in the DB for the created user
                        }
                    }
                });
            } else {
                formError.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addUser(User user) {
        model.publishUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), GamesActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
