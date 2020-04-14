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
public class MeasureThrowTest {

    @Rule
    public ActivityTestRule<MeasureThrowActivity> activityRule
            = new ActivityTestRule<>(MeasureThrowActivity.class);


    @Test
    public void calculateDistance() {
        onView(withId(R.id.startBtn)).perform(click());
        onView(withId(R.id.endBtn)).perform(click());
        onView(withId(R.id.calcDistance)).perform(click());
        onView(withId(R.id.saveThrowBtn)).perform(click());
    }

}
