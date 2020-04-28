package com.example.discgolfapp;

import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class TutorialsTest {
    private TutorialsActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(TutorialsActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void notNull() {
        assertNotNull(activity);
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
