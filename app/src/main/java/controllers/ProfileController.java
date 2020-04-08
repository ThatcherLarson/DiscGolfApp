package controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileController {
    private FirebaseAuth auth;

    public ProfileController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public void signOutUser() {
        auth.signOut();
    }

    public Task<Void> resetPassword() {
        return auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail());
    }
}
