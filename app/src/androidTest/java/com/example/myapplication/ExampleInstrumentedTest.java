package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.action.KeyEventAction;
import androidx.test.rule.ActivityTestRule;
import com.example.myapplication.Activities.LoginActivity;
import com.example.myapplication.Activities.RegisterActivity;
import com.example.myapplication.Activities.localGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


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

    @Rule
    public ActivityTestRule<RegisterActivity> registerRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    //tHIS
    @Test
    public void RegisterProcessTest(){
        onView(withId(R.id.btnToRegister)).perform(click());
        onView(withId(R.id.txtUsername)).perform(typeText("DefalutName@home.ch"));
        onView(withId(R.id.txtPassword)).perform(typeText("Welcome"));
        onView(withId(R.id.txtPasswordRepeat)).perform(typeText("Welcome"));
    }

    @Rule
    public ActivityTestRule<LoginActivity> loginRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    //This Test tests if the user can write a Name into the textfield of the Login vire
    @Test
    public void userCanWriteLoginName(){
        onView(withId(R.id.txtLoginUsername)).perform(typeText("DefaultName"));
    }

}
