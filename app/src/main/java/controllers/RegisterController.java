package controllers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterController {
    private FirebaseAuth auth;

    public RegisterController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public boolean verifyInput(String firstName, String lastName, String email, String password, String confirmPass) {
        boolean everythingFilled = (firstName.length() > 0 && lastName.length() > 0 &&
                email.length() > 0 && password.length() > 0 && confirmPass.length() > 0);
        boolean passwordReqs = (password.length() >= 8 && password.equals(confirmPass));
        return everythingFilled && passwordReqs;
    }

    public Task<AuthResult> createUser(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }
}
