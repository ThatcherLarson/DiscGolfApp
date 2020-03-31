package controllers;

import android.content.Context;
import android.content.Intent;

import com.example.discgolfapp.CoursesActivity;
import com.example.discgolfapp.GamesActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CoursesController {
    private Context context;
    private FirebaseAuth auth;


    public CoursesController(Context context) {
        this.context = context;
        this.auth = auth;
    }

    public Intent clickNewGame() {
        return new Intent(context, GamesActivity.class);
    }
}
