package com.example.discgolfapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class EmailValidateTest {


    @Test
    public void emailValidationUnitTest() {
        //Testing credentials
        final String email = "dbondi@wisc.edu";
        final String password = "pePPer123!";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FirebaseApp.initializeApp(appContext);

        ActivityController activity = Robolectric.buildActivity(LoginActivity.class);

        Activity mActivity = (Activity) activity.create().get();
        Button loginBtn = mActivity.findViewById(R.id.login);
        EditText textEmail = mActivity.findViewById(R.id.username);
        EditText textPass = mActivity.findViewById(R.id.password);

        textEmail.setText(email);
        textPass.setText(password);

        String testLabSetting = Settings.System.getString(appContext.getContentResolver(), "firebase.test.lab");
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ("true".equals(testLabSetting)) {
            loginBtn.performClick();
            //Test if user is exists
            Assert.assertTrue(auth.getCurrentUser() != null);
        }
    }

    @Test
    public void testInvalidLogin() {
        //Testing credentials
        final String email = "bad@email.com";
        final String password = "badpassword";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FirebaseApp.initializeApp(appContext);

        ActivityController activity = Robolectric.buildActivity(LoginActivity.class);

        activity.create();

        String testLabSetting = Settings.System.getString(appContext.getContentResolver(), "firebase.test.lab");
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ("true".equals(testLabSetting)) {;
            auth.signInWithEmailAndPassword(email, password);
            Assert.assertTrue(auth.getCurrentUser() == null);
        }

        activity.destroy();
    }

    @Test
    public void testRegisterAccount() {
        //Testing credentials
        final String email = "testregister@email.com";
        final String password = "testpass";
        final String first = "TestFirst";
        final String last = "TestLast";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        FirebaseApp.initializeApp(appContext);

        ActivityController activity = Robolectric.buildActivity(RegisterActivity.class);

        activity.create();


        String testLabSetting = Settings.System.getString(appContext.getContentResolver(), "firebase.test.lab");
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ("true".equals(testLabSetting)) {;
            auth.createUserWithEmailAndPassword(email, password);
            Assert.assertTrue(auth.getCurrentUser() != null);
            auth.getCurrentUser().delete();
        }

        activity.destroy();
    }

    public void signIn() {
        //Testing credentials
        final String email = "dbondi@wisc.edu";
        final String password = "pePPer123!";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        String testLabSetting = Settings.System.getString(appContext.getContentResolver(), "firebase.test.lab");
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ("true".equals(testLabSetting)) {;
            auth.signInWithEmailAndPassword(email, password);
            //Test if user is exists
            Assert.assertTrue(auth.getCurrentUser() != null);
        }
    }

    public void createAccount() {
        //Testing credentials
        final String email = "dbondi@wisc.edu";
        final String password = "pePPer123!";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        String testLabSetting = Settings.System.getString(appContext.getContentResolver(), "firebase.test.lab");
        FirebaseApp.initializeApp(appContext);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if ("true".equals(testLabSetting)) {;
            auth.signInWithEmailAndPassword(email, password);
            //Test if user is exists
            Assert.assertTrue(auth.getCurrentUser() != null);
        }
    }

}