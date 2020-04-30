package models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterModel {

    private FirebaseAuth auth;

    public RegisterModel(FirebaseAuth auth) {
        this.auth = auth;
    }

    public Task<Void> publishUser(User user) {
        FirebaseUser currUser = auth.getCurrentUser();
        return currUser.updateProfile(buildUserProfile(user.getFirstName() + " " + user.getLastName()));
    }

    private UserProfileChangeRequest buildUserProfile(String name) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(name);
        return builder.build();
    }
}
