package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class FilterNewsPage {
    public static ViewInteraction categoryNews = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static ViewInteraction dateEndCalendar = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    public static ViewInteraction dateStartCalendar = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    public static ViewInteraction filterButton = onView(withId(R.id.filter_button));
    public static ViewInteraction cancelButton = onView(withId(R.id.cancel_button));
    public static ViewInteraction activeButton = onView(withId(R.id.filter_news_active_material_check_box));
    public static ViewInteraction notActiveButton = onView(withId(R.id.filter_news_inactive_material_check_box));
}
