package controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import util.Constants;

public class ProfileController {
    private FirebaseAuth auth;
    private Context context;

    public ProfileController(FirebaseAuth auth, Context context) {
        this.context = context;
        this.auth = auth;
    }

    public void signOutUser() {
        auth.signOut();
    }

    public Task<Void> resetPassword() {
        return auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail());
    }

    public Task<Void> updateProfile(int field, String value) {
        Log.d("ProfileActivity", value);
        Task<Void> result;
        switch (field) {
            case Constants.EMAIL:
                result = auth.getCurrentUser().verifyBeforeUpdateEmail(value);
                break;

            case Constants.NAME:
                result = auth.getCurrentUser().updateProfile(changeProfileName(value));
                break;

            case Constants.PICTURE:
                result = auth.getCurrentUser().updateProfile(changeProfilePic(Uri.parse(value)));
                break;

            default:
                result = null;
        }

        return result;
    }

    public Intent openPhotoGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    public Task<Void> updateProfilePic(Uri imgUri) {
        return auth.getCurrentUser().updateProfile(changeProfilePic(imgUri));
    }

    private UserProfileChangeRequest changeProfileName(String newVal) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(newVal);
        return builder.build();
    }

    private UserProfileChangeRequest changeProfilePic(Uri uri) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setPhotoUri(uri);
        return builder.build();
    }
}
