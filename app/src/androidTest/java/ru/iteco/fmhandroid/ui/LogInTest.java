package ru.iteco.fmhandroid.ui;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import pages.AuthPage;
import pages.MainPage;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static pages.AuthPage.loginInput;
import static pages.AuthPage.passwordInput;
import static pages.AuthPage.signInButton;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogInTest {
    @Before
    public void sleep() throws InterruptedException {
        Thread.sleep(7000);
        try{
            AuthPage.signInButton.check(matches(isDisplayed()));
        }catch (Exception e){
            logOut();

        }
    }

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Test
    public void logInTest() throws InterruptedException {
        logIn("login2","password2");
        MainPage.menuButton.check(matches(isDisplayed()));
        logOut();



    }
    @Test
    public void logInFailedTest(){
        signInButton.perform(click());
        AuthPage.errorMassage.inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()
        )))).check(matches(withText(R.string.empty_login_or_password)));
    }
    public static void logIn(String login, String password){
        loginInput.perform(replaceText(login));
        passwordInput.perform(replaceText(password));
        signInButton.perform(click());
    }

    public static void logOut(){
        MainPage.authorizationButton.check(matches(isDisplayed()));
        MainPage.authorizationButton.perform(click());
        MainPage.authorizationLogOut.check(matches(isDisplayed()));
        MainPage.authorizationLogOut.perform(click());

    }
}
