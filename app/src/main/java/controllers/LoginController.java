package controllers;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController {
    private FirebaseAuth auth;
    private Context context;

    public LoginController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public Task<AuthResult> loginUser(String email, String pass) {
        return auth.signInWithEmailAndPassword(email, pass);
    }
}
