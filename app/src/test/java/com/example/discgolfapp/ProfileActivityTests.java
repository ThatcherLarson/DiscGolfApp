package com.example.discgolfapp;

import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class ProfileActivityTests {
    ActivityController<ProfileActivity> activityCont;
    FirebaseAuth auth;

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FirebaseApp fapp = FirebaseApp.initializeApp(appContext);
        auth = FirebaseAuth.getInstance(fapp);
        activityCont = Robolectric.buildActivity(ProfileActivity.class);
    }

    @Test
    public void editName_shouldOpenDialog() {
        ProfileActivity activity = activityCont.create().get();
        activity.findViewById(R.id.btnEditName).performClick();
        AlertDialog actual = activity.dialog;

        assertNotNull(actual);

        activityCont.destroy();
    }

    @Test
    public void editEmail_shouldOpenDialog() {
        ProfileActivity activity = activityCont.create().get();
        activity.findViewById(R.id.btnEditEmail).performClick();
        AlertDialog actual = activity.dialog;

        assertNotNull(actual);

        activityCont.destroy();
    }

    @Test
    public void editProfPic_shouldOpenDialog() {
        ProfileActivity activity = activityCont.create().get();
        activity.findViewById(R.id.imageProfilePic).performClick();
        AlertDialog actual = activity.dialog;

        assertNotNull(actual);

        activityCont.destroy();
    }

    @Test
    public void clickProfileFromMenuLaunchesProfileActivity() {
        ProfileActivity activity = activityCont.create().get();
        MenuItem menuItem = new RoboMenuItem(R.id.profile);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, ProfileActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
        activityCont.destroy();
    }

    @Test
    public void clickBagFromMenuLaunchesBagActivity() {
        ProfileActivity activity = activityCont.create().get();
        MenuItem menuItem = new RoboMenuItem(R.id.bag);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, MyBagActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
        activityCont.destroy();
    }

    @Test
    public void clickMoreFromMenuLaunchesMoreActivity() {
        ProfileActivity activity = activityCont.create().get();
        MenuItem menuItem = new RoboMenuItem(R.id.more);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, MoreActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
        activityCont.destroy();
    }

    @Test
    public void clickFindFromMenuLaunchesFindActivity() {
        ProfileActivity activity = activityCont.create().get();
        MenuItem menuItem = new RoboMenuItem(R.id.find);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expIntent = new Intent(activity, FindCourseActivity.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
        activityCont.destroy();
    }

//    @Test
//    public void confirmEditProfPic_shouldOpenGallery() {
//        ProfileActivity activity = activityCont.create().get();
//        activity.findViewById(R.id.imageProfilePic).performClick();
//        AlertDialog dialog = activity.dialog;
//
//        assertNotNull(dialog);
//
//        dialog.getButton(Dialog.BUTTON_POSITIVE).performClick();
//
//        Intent exp = new Intent(Intent.ACTION_PICK);
//
//        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
//
//        assertEquals(exp.getComponent(), actual.getComponent());
//
//        activityCont.destroy();
//    }
}
