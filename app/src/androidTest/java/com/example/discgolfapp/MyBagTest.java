package com.example.discgolfapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyBagTest {

    @Rule
    public ActivityTestRule<MyBagActivity> activityRule
            = new ActivityTestRule<>(MyBagActivity.class);


    @Test
    public void newDisk() {
        onView(withId(R.id.addDiscBtn)).perform(click());

    }

}