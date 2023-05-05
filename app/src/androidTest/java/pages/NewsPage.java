package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import Utility.Helper;
import ru.iteco.fmhandroid.R;

public class NewsPage {
    public static ViewInteraction sortButton = onView(withId(R.id.sort_news_material_button));
    public static ViewInteraction filterOpenButton = onView(withId(R.id.filter_news_material_button));
    public static ViewInteraction editNewsButton = onView(withId(R.id.edit_news_material_button));
    public static ViewInteraction openNewsButton = onView(Helper.withIndex(withId(R.id.view_news_item_image_view),0));

}
