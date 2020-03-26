package models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterModel {

    private FirebaseFirestore db;

    public RegisterModel(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<Void> publishUser(User user) {
        return db.collection("users").document(user.getUid()).set(user);
    }
}
