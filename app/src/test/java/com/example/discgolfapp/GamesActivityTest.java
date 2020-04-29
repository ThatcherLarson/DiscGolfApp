package com.example.discgolfapp;

import android.os.Build;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class GamesActivityTest {
    private GamesActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(GamesActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void notNull() {
        assertNotNull(activity);
    }
}
