package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import Utility.Helper;
import ru.iteco.fmhandroid.R;

public class MainPage {
    public static ViewInteraction authorizationButton = onView(withId(R.id.authorization_image_button));
    public static ViewInteraction ourMissionButton = onView(withId(R.id.our_mission_image_button));
    public static ViewInteraction menuButton = onView(withId(R.id.main_menu_image_button));
    public static ViewInteraction authorizationLogOut = onView(withText("Log out"));
    public static ViewInteraction menuMain = onView(withId(R.id.menu_item_main));
    public static ViewInteraction menuClaims = onView(withText("Claims"));
    public static ViewInteraction menuNews = onView(withText("News"));
    public static ViewInteraction menuAbout = onView(withId(R.id.menu_item_about));
    public static ViewInteraction allNews = onView(Helper.withIndex(withId(R.id.all_news_text_view),0));
}
