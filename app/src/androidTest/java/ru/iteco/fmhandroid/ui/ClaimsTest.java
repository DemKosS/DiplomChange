package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static pages.AuthPage.signInButton;
import static ru.iteco.fmhandroid.ui.LogInTest.logIn;
import static ru.iteco.fmhandroid.ui.LogInTest.logOut;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import Utility.Helper;
import pages.ClaimPage;
import pages.ClaimsPage;
import pages.MainPage;
import ru.iteco.fmhandroid.R;

public class ClaimsTest {
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
    public void GoToClaimsBlock(){
        MainPage.menuButton.perform(click());
        MainPage.menuClaims.perform(click());
    }

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);
    @Test
    public void claimsNewTest(){
        GoToClaimsBlock();
        ClaimsPage.addButton.check(matches(isDisplayed()));

    }
    @Test
    public void ClaimFilter() throws InterruptedException {
        GoToClaimsBlock();
        ClaimsPage.filterButton.check(matches(isDisplayed()));
        ClaimsPage.filterButton.perform(click());
        ClaimsPage.openButton.perform(click());
        ClaimsPage.progressButton.perform(click());
        ClaimsPage.executedButton.perform(click());
        ClaimsPage.okButton.perform(click());
        Thread.sleep(2000);
        for(int i = 0; i < 3 ; i++){
            onView(Helper.withIndex(withId(R.id.claim_list_card), i)).perform(click());
            onView(withText("Executed")).check(matches(isDisplayed()));
            Espresso.pressBack();
        }



    }
    @Test
    public void DeleteFilterClaim(){
        GoToClaimsBlock();
        ClaimsPage.filterButton.check(matches(isDisplayed()));
        ClaimsPage.filterButton.perform(click());
        ClaimsPage.openButton.perform(click());
        ClaimsPage.progressButton.perform(click());
        ClaimsPage.okButton.perform(click());
        onView(withText("REFRESH")).check(matches(isDisplayed()));

    }

}
