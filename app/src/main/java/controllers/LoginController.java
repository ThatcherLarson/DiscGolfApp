package controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.discgolfapp.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController {
    private FirebaseAuth auth;
    private Context context;

    public LoginController(FirebaseAuth auth, Context context) {
        this.auth = auth;
        this.context = context;
    }

    public Task<AuthResult> loginUser(String email, String pass) {
        return auth.signInWithEmailAndPassword(email, pass);
    }

    public void saveUserandPass(String user, String pass, boolean remember) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_login_details), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remember", remember);
        editor.putString(context.getString(R.string.prompt_email), user);
        editor.putString(context.getString(R.string.prompt_password), pass);
        editor.apply();
    }

    public String[] getLoginInfo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_login_details), Context.MODE_PRIVATE);
        boolean rem = sharedPreferences.getBoolean("remember", false);
        if (rem) {
            String email = sharedPreferences.getString(context.getString(R.string.prompt_email), "");
            String pass = sharedPreferences.getString(context.getString(R.string.prompt_password), "");
            return new String[] {email, pass};
        }
        return null;
    }
}
