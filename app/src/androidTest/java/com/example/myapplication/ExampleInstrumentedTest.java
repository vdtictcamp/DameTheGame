package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.action.KeyEventAction;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.Activities.localGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Test
    public void test(){
        onView(withId(R.id.btnInitnewGame)).perform(click());
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

    }

    @Rule
    public ActivityTestRule<localGame> activityRule
            = new ActivityTestRule<>(
            localGame.class,
            true,     // initialTouchMode
            false);
}
