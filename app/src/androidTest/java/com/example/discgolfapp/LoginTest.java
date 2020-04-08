package com.example.discgolfapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void login() {
        // Type text and then press the button.
        //onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("dbondi@wisc.edu"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("pePPer123!"), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        //onView(withId(R.id.btnNewGame)).perform(click());

    }

}