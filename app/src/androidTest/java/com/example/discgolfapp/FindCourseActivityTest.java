package com.example.discgolfapp;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.view.View;

import static org.junit.Assert.*;

public class FindCourseActivityTest {

    @Rule
    public ActivityTestRule<FindCourseActivity> activityRule
            = new ActivityTestRule<>(FindCourseActivity.class);

    private FindCourseActivity findCourseActivity = null;

    @Before
    public void setUp() throws Exception {
        findCourseActivity = activityRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = findCourseActivity.findViewById(R.id.course_list_container);

        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        findCourseActivity = null;
    }
}