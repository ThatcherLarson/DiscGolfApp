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
public class CreateAccountTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityRule
            = new ActivityTestRule<>(RegisterActivity.class);


    @Test
    public void createAccount() {
        final String firstName = "Test";
        final String lastName = "Test";
        final String email = "test@test.com";
        final String password = "password123";
        onView(withId(R.id.textFirstName)).perform(typeText(firstName), closeSoftKeyboard());
        onView(withId(R.id.textLastName)).perform(typeText(lastName), closeSoftKeyboard());
        onView(withId(R.id.textEmail)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.textPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.textConfirmPass)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.registerBtn)).perform(click());

    }
}