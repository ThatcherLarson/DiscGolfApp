package com.example.discgolfapp;

import android.content.Intent;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class WelcomeActivityTests {
    private WelcomeActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(WelcomeActivity.class);
    }

    @Test
    public void clickingLogin_shouldStartLoginActivity() {
        activity.findViewById(R.id.btnLogin).performClick();

        Intent expIntent = new Intent(activity, LoginActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickingRegister_shouldStartRegisterActivity() {
        activity.findViewById(R.id.btnSignUp).performClick();

        Intent expIntent = new Intent(activity, RegisterActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expIntent.getComponent(), actual.getComponent());
    }
}
