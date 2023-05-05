package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static pages.AuthPage.signInButton;
import static ru.iteco.fmhandroid.ui.LogInTest.logIn;
import static ru.iteco.fmhandroid.ui.LogInTest.logOut;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import Utility.Helper;
import hilt_aggregated_deps._ru_iteco_fmhandroid_ui_ClaimListFilteringDialogFragment_GeneratedInjector;
import pages.AuthPage;
import pages.ControlPanelNewsPage;
import pages.CreatingNewsPage;
import pages.FilterNewsPage;
import pages.MainPage;
import pages.NewsPage;
import ru.iteco.fmhandroid.R;

public class NewsTest {
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

    public static void GoToNewsBlock() throws InterruptedException {
        MainPage.menuButton.perform(click());
        MainPage.menuNews.check(matches(isDisplayed()));
        MainPage.menuNews.perform(click());
        Thread.sleep(2000);


    }

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Test
    public void newsBlockTest() {
        MainPage.allNews.perform(click());
        NewsPage.openNewsButton.check(matches(isDisplayed()));

    }

    @Test
    public void newsBlockNestWithMenuButton()throws InterruptedException {
        GoToNewsBlock();
        NewsPage.editNewsButton.check(matches(isDisplayed()));

    }

    @Test
    public void FilterNews()throws InterruptedException {
        GoToNewsBlock();
        NewsPage.filterOpenButton.perform(click());
        FilterNewsPage.filterButton.check(matches(isDisplayed()));
        FilterNewsPage.categoryNews.perform(replaceText("Объявление"));
        FilterNewsPage.dateStartCalendar.perform(replaceText("10.07.2022"));
        FilterNewsPage.dateEndCalendar.perform(replaceText("20.02.2023"));
        FilterNewsPage.filterButton.perform(click());

    }

    @Test
    public void addNews() throws InterruptedException {
        Random random = new Random();
        String title = "Тест Объявления" + random.nextInt(100000) % DateTime.now().getSecondOfDay() * 10;
        String description = "Description" + random.nextInt(100000) % DateTime.now().getSecondOfDay() * 10;;
        GoToNewsBlock();
        NewsPage.editNewsButton.check(matches(isDisplayed()));
        NewsPage.editNewsButton.perform(click());
        ControlPanelNewsPage.addNewsButton.perform(click());
        CreatingNewsPage.categoryNews.perform(replaceText("Объявление"));
        CreatingNewsPage.titleNews.perform(replaceText(title));
        CreatingNewsPage.publicationDate.perform(replaceText(DateTime.now().toString("dd.MM.yyyy")));
        CreatingNewsPage.timeNews.perform(replaceText(DateTime.now().toString("HH:mm")));
        CreatingNewsPage.descriptionNews.perform(replaceText(description));
        CreatingNewsPage.saveButton.perform(click());




        GoToNewsBlock();
        onView(withText(title)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void addEmptyNews()throws InterruptedException {
        GoToNewsBlock();
        NewsPage.editNewsButton.check(matches(isDisplayed()));
        NewsPage.editNewsButton.perform(click());
        ControlPanelNewsPage.addNewsButton.perform(click());
        CreatingNewsPage.saveButton.perform(click());
        CreatingNewsPage.errorMassage.inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()
        )))).check(matches(withText("Fill empty fields")));
        CreatingNewsPage.cancelButton.perform(click());
        CreatingNewsPage.okButton.perform(click());

    }

//    @ Test // Проблема с проверкой календаря не кликабельной даты
//    public void addEarlyDate() throws InterruptedException {
//        GoToNewsBlock();
//        NewsPage.editNewsButton.check(matches(isDisplayed()));
//        NewsPage.editNewsButton.perform(click());
//        ControlPanelNewsPage.addNewsButton.perform(click());
//        CreatingNewsPage.publicationDate.perform(click());
//        onView(withText(String.valueOf(DateTime.now().getDayOfMonth() - 1))).check(matches(isNotEnabled()));
//        CreatingNewsPage.okButton.perform(click());
//        Thread.sleep(7000);
//        CreatingNewsPage.cancelButton.perform(click());
//        CreatingNewsPage.okButton.perform(click());
//
//
//    }

}





