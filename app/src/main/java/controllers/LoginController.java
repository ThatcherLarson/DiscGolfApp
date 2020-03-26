package controllers;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController {
    private FirebaseAuth auth;
    private Context context;

    public LoginController(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }
}
