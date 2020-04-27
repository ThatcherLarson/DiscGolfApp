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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class ScorecardActivityTests {
    ScorecardActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(ScorecardActivity.class).create().get();
    }

    @Test
    public void nullVals_shouldStartFindCourseActivity() {
        activity.initTable(null, null, null);
        Intent exp = new Intent(activity, FindCourseActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        assertEquals(exp.getComponent(), actual.getComponent());
    }

    @Test
    public void clickDone_shouldStartFindCourseActivity() {
        ArrayList<String> players = new ArrayList<>();
        ArrayList<ArrayList<Integer>> scores = new ArrayList<>(3);
        ArrayList<Integer> pars = new ArrayList<>();
        players.add("Test1");
        players.add("Test2");
        players.add("Test3");
        pars.add(3);
        pars.add(3);
        pars.add(3);
        scores.add(new ArrayList<Integer>(3));
        scores.add(new ArrayList<Integer>(3));
        scores.add(new ArrayList<Integer>(3));
        scores.get(0).add(4);
        scores.get(0).add(4);
        scores.get(0).add(4);
        scores.get(1).add(4);
        scores.get(1).add(4);
        scores.get(1).add(4);
        scores.get(2).add(4);
        scores.get(2).add(4);
        scores.get(2).add(4);

        activity.initTable(players, scores, pars);
        activity.findViewById(R.id.btnDone).performClick();
        Intent exp = new Intent(activity, FindCourseActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();

        assertEquals(exp.getComponent(), actual.getComponent());
    }
}
