package com.example.discgolfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class HoleTest {
    private HoleActivity activity;

    @Before
    public void setup() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(appContext,HoleActivity.class);
        FirebaseApp.initializeApp(appContext);
        activity = Robolectric.buildActivity(HoleActivity.class,intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();

    }

    @Test
    public void holeActivity() {

        assertEquals(true, true);
    }
}
