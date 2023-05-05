package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static pages.AuthPage.signInButton;
import static ru.iteco.fmhandroid.ui.LogInTest.logIn;
import static ru.iteco.fmhandroid.ui.LogInTest.logOut;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import Utility.Helper;
import pages.MainPage;
import pages.OurMissionPage;

public class QuoteTest {
    @Before
    public void sleep() throws InterruptedException {
        Thread.sleep(5000);
        try {
            signInButton.check(matches(isDisplayed()));
            LogInTest.logIn("login2", "password2");
        } catch (Exception ex) {
        }

        Thread.sleep(3000);
    }
    @After
    public void Out() {
        logOut();
    }


    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    @Test
    public void QuoteOpen(){
        MainPage.ourMissionButton.perform(click());
        onView(Helper.withIndex(OurMissionPage.cardOpen,1)).perform(click());
        onView(Helper.withIndex(OurMissionPage.description,1)).check(matches(isDisplayed()));

    }
}
