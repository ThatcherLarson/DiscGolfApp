package models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterModel {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public RegisterModel(FirebaseFirestore db, FirebaseAuth auth) {
        this.db = db;
        this.auth = auth;
    }

    public Task<Void> publishUser(User user) {
        FirebaseUser currUser = auth.getCurrentUser();
        if (currUser != null) {
            currUser.updateProfile(buildUserProfile(user.getFirstName() + " " + user.getLastName()));
        }
        return db.collection("users").document(user.getUid()).set(user);
    }

    private UserProfileChangeRequest buildUserProfile(String name) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(name);
        return builder.build();
    }
}
