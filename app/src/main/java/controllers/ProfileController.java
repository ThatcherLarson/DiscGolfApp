package controllers;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileController {
    private FirebaseAuth auth;

    public ProfileController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public void signOutUser() {
        auth.signOut();
    }
}
