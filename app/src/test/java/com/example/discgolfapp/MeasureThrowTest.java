package com.example.discgolfapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import android.location.Location;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class MeasureThrowTest {
    private MeasureThrowActivity activity;
    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MeasureThrowActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }

    @Test
    public void notNull() {
        assertNotNull(activity);
    }

    @Test
    public void measureThrow() throws NullPointerException {

        activity.findViewById(R.id.startBtn).performClick();
        activity.findViewById(R.id.endBtn).performClick();

        activity.findViewById(R.id.calcDistance).performClick();

        GoogleMap gmap = null;
        //activity.findViewById(R.id.clearBtn).performClick();
        TextView distance = (TextView) activity.findViewById(R.id.distTextView);
        String dist = distance.getText().toString();
        Location location = new Location("");

        //activity.onMapReady(gmap);
        //activity.onLocationChanged(location);
        activity.onStatusChanged("", 0, null);
        Bundle outstate = new Bundle();
        activity.onSaveInstanceState(outstate);
        activity.onProviderEnabled("test");
        activity.onProviderDisabled("test");
        activity.onLowMemory();
        activity.getLocation();
        activity.onPause();
        activity.onStop();
        activity.onDestroy();

        assertEquals("Distance: 0.0 feet", dist);

    }

    @Test
    public void clickProfileFromMenuLaunchesProfileActivity() {
        MenuItem menuItem = new RoboMenuItem(R.id.profile);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, ProfileActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickBagFromMenuLaunchesBagActivity() {
        MenuItem menuItem = new RoboMenuItem(R.id.bag);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, MyBagActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickMoreFromMenuLaunchesMoreActivity() {
        MenuItem menuItem = new RoboMenuItem(R.id.more);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, MoreActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickFindFromMenuLaunchesFindActivity() {
        MenuItem menuItem = new RoboMenuItem(R.id.find);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, FindCourseActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }
}
