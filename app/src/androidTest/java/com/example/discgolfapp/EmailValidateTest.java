package com.example.discgolfapp;

import android.content.Context;
import android.provider.Settings;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;

public class EmailValidateTest {



    @Test
    public void emailValidationUnitTest() {
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