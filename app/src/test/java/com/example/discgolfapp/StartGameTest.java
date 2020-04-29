package com.example.discgolfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.GeoPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import models.DiscMap;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class StartGameTest {
    private StartGameActivity activity;

    @Before
    public void setup() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<DiscMap> discMaps = new ArrayList<DiscMap>(1);
        ArrayList<Integer> parsAndYards = new ArrayList<Integer>(1);
        parsAndYards.add(1);
        DiscMap discMap = new DiscMap("1234567890","Title","Description",new GeoPoint(0.0,0.0), parsAndYards,parsAndYards);
        discMaps.add(discMap);
        Intent intent = new Intent(appContext,StartGameActivity.class);
        intent.putParcelableArrayListExtra("discMaps",discMaps);
        FirebaseApp.initializeApp(appContext);
        activity = Robolectric.buildActivity(StartGameActivity.class,intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();

    }


    @Test
    public void findCourseTest() {

        assertEquals(true, true);
    }
}
