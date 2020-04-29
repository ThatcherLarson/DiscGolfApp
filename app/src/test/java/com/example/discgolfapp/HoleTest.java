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
public class HoleTest {
    private HoleActivity activity;

    @Before
    public void setup() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<Integer> parsAndYards = new ArrayList<Integer>(1);
        parsAndYards.add(1);
        ArrayList<String> names = new ArrayList<String>(1);
        names.add("Name");
        DiscMap discMap = new DiscMap("1234567890","Title","Description",new GeoPoint(0.0,0.0), parsAndYards,parsAndYards);
        Intent intent = new Intent(appContext,HoleActivity.class);
        intent.putExtra("Map",discMap);
        intent.putExtra("LoadDB",false);
        intent.putExtra("Names",names);
        intent.putExtra("CourseId","1234567890");
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
