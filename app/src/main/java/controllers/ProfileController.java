package controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    public Task<Void> updateProfile(boolean isEmail, String value) {
        Task<Void> result;
        if (isEmail) {
            result = auth.getCurrentUser().verifyBeforeUpdateEmail(value);
        } else {
            result = auth.getCurrentUser().updateProfile(changeProfileName(value));
        }

        return result;
    }

    private UserProfileChangeRequest changeProfileName(String newVal) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(newVal);
        return builder.build();
    }
}
