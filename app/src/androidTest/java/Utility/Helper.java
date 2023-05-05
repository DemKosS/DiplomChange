package Utility;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;

import android.os.SystemClock;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;


public class Helper {
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }

        };
    }
    public static void performRecycler(int recycler, int n) {
        switch (recycler) {
            case 1:
                onView(allOf(withId(R.id.news_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                break;
            case 2:
                onView(allOf(withId(R.id.claim_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                break;
            case 3:
                onView(allOf(withId(R.id.our_mission_item_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                break;
            case 4:
                onView(allOf(withId(R.id.claim_comments_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                break;
        }

    }
    public static boolean isDisplayedWithSwipe(ViewInteraction locator, int recycler, boolean finishSwipe) {
        try {
            locator.check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException ignored) {
        }
        boolean invisible = true;
        int n = 1;
        while (invisible) {
            try {
                performRecycler(recycler, n);
                try {
                    locator.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));//.check(matches(isDisplayed()));
                    invisible = false;
                } catch (NoMatchingViewException ignored) {
                }
                n++;
                if (finishSwipe && !invisible) {
                    performRecycler(recycler, n);
                }
                if (n > 700) {
                    return false;
                }
                SystemClock.sleep(500);
            } catch (PerformException e) {
                return false;
            }

        }
        return true;
    }

}





