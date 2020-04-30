package com.example.discgolfapp;


import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O)
public class RegisterActivityTest {

    private RegisterActivity activity;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button registerBtn;
    private TextView passMatch;
    private TextView passReq;

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp.initializeApp(appContext);
        activity = Robolectric.buildActivity(RegisterActivity.class).create().get();

        firstName = activity.findViewById(R.id.textFirstName);
        lastName = activity.findViewById(R.id.textLastName);
        email = activity.findViewById(R.id.textEmail);
        password = activity.findViewById(R.id.textPassword);
        confirmPassword = activity.findViewById(R.id.textConfirmPass);
        registerBtn = activity.findViewById(R.id.registerBtn);
        passMatch = activity.findViewById(R.id.textPasswordMatch);
        passReq = activity.findViewById(R.id.textPasswordReqs);
    }

    @Test
    public void notNull() {
        assertNotNull(activity);
    }

    @Test
    public void noFirstName_shouldDisableRegister() {
        String first = "";
        String last = "Test";
        String textEmail = "email@email.com";
        String pass = "testpass";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(pass);

        assertFalse(registerBtn.isEnabled());

    }

    @Test
    public void noLastName_shouldDisableRegister() {
        String first = "Test";
        String last = "";
        String textEmail = "email@email.com";
        String pass = "testpass";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(pass);

        assertFalse(registerBtn.isEnabled());
    }

    @Test
    public void noEmail_shouldDisableRegister() {
        String first = "Test";
        String last = "Test";
        String textEmail = "";
        String pass = "testpass";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(pass);

        assertFalse(registerBtn.isEnabled());
    }

    @Test
    public void noPass_shouldDisableRegister() {
        String first = "Test";
        String last = "Test";
        String textEmail = "email@email.com";
        String pass = "";
        String textPassMatch = "testpass";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(textPassMatch);

        assertFalse(registerBtn.isEnabled());
        assertEquals(passReq.getVisibility(), View.VISIBLE);
        assertEquals(passMatch.getVisibility(), View.VISIBLE);
    }

    @Test
    public void noPassMatch_shouldDisplayTextandDisable() {
        String first = "Test";
        String last = "Test";
        String textEmail = "email@email.com";
        String pass = "testpass";
        String textPassMatch = "notmatching";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(textPassMatch);

        assertFalse(registerBtn.isEnabled());
        assertEquals(passMatch.getVisibility(), View.VISIBLE);
    }

    @Test
    public void invalidPassLength_shouldDisplayTextandDisable() {
        String first = "Test";
        String last = "Test";
        String textEmail = "email@email.com";
        String pass = "test";
        String textPassMatch = "test";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(pass);

        assertFalse(registerBtn.isEnabled());
        assertEquals(passReq.getVisibility(), View.VISIBLE);
    }

    @Test
    public void goodInput() {
        String first = "Test";
        String last = "Test";
        String textEmail = "email@email.com";
        String pass = "testpass";
        String textPassMatch = "testpass";

        firstName.setText(first);
        lastName.setText(last);
        email.setText(textEmail);
        password.setText(pass);
        confirmPassword.setText(textPassMatch);

        assertTrue(registerBtn.isEnabled());
    }
}
