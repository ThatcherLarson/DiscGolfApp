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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        controller = new RegisterController(auth);
        model = new RegisterModel(db, auth);

        firstName = findViewById(R.id.textFirstName);
        lastName = findViewById(R.id.textLastName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        confirmPassword = findViewById(R.id.textConfirmPass);
        registerBtn = findViewById(R.id.registerBtn);
        passMatch = findViewById(R.id.textPasswordMatch);
        passReq = findViewById(R.id.textPasswordReqs);
        formError = findViewById(R.id.textFormError);
        loadingBar = findViewById(R.id.regisLoading);

        passMatch.setVisibility(View.INVISIBLE);
        passReq.setVisibility(View.INVISIBLE);
        formError.setVisibility(View.GONE);
        loadingBar.setVisibility(View.INVISIBLE);

        registerBtn.setOnClickListener(this);
        registerBtn.setEnabled(false);

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

                registerBtn.setEnabled(
                        controller.verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())
                );
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
                } else {
                    passMatch.setVisibility(View.INVISIBLE);
                }

                registerBtn.setEnabled(
                        controller.verifyInput(firstName.getText().toString(), lastName.getText().toString(),
                                email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())
                );
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerBtn) {
            loadingBar.setVisibility(View.VISIBLE);
            final String first = firstName.getText().toString();
            final String last = lastName.getText().toString();
            final String uName = email.getText().toString();
            final String pass = password.getText().toString();
            controller.createUser(uName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(first, last, uName, auth.getCurrentUser().getUid());
                        addUser(user);  // add a document in the DB for the created user
                    } else {
                        loadingBar.setVisibility(View.INVISIBLE);
                        String errMsg = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "User unable to be created: " + errMsg, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private void addUser(User user) {
        model.publishUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
                    startActivity(intent);
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "An error occurred: " + message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
