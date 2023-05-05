package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ControlPanelNewsPage {
    public static ViewInteraction sortNews = onView(withId(R.id.sort_news_material_button));
    public static ViewInteraction filterNewsButton = onView(withId(R.id.filter_news_material_button));
    public static ViewInteraction addNewsButton = onView(withId(R.id.add_news_image_view));
    public static ViewInteraction deleteNewsButton = onView(withId(R.id.delete_news_item_image_view));
    public static ViewInteraction editNewsButton = onView(withId(R.id.edit_news_item_image_view));
    public static ViewInteraction viewButton = onView(withId(R.id.view_news_item_image_view));
}
