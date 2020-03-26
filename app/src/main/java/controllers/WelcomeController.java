package controllers;

import android.content.Context;
import android.content.Intent;

import com.example.discgolfapp.LoginActivity;
import com.example.discgolfapp.RegisterActivity;

public class WelcomeController {
    Context context;

    public WelcomeController(Context context) {
        this.context = context;
    }

    public Intent clickLogin() {
        return new Intent(context, LoginActivity.class);
    }

    public Intent clickCreate() {
        return new Intent(context, RegisterActivity.class);
    }
}
