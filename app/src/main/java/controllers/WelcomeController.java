package controllers;

import android.content.Context;
import android.content.Intent;

import com.example.discgolfapp.LoginActivity;
import com.example.discgolfapp.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeController {
    private Context context;
    private FirebaseAuth auth;


    public WelcomeController(Context context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    public Intent clickLogin() {
        return new Intent(context, LoginActivity.class);
    }

    public Intent clickCreate() {
        return new Intent(context, RegisterActivity.class);
    }

    public boolean tryAutoLogin() {
        return auth.getCurrentUser() != null;
    }
}
