package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class CreatingNewsPage {
    public static ViewInteraction categoryNews = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static ViewInteraction titleNews = onView(withId(R.id.news_item_title_text_input_edit_text));
    public static ViewInteraction publicationDate = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public static ViewInteraction timeNews = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public static ViewInteraction descriptionNews = onView(withId(R.id.news_item_description_text_input_edit_text));
    public static ViewInteraction saveButton = onView(withId(R.id.save_button));
    public static ViewInteraction cancelButton = onView(withId(R.id.cancel_button));
    public static ViewInteraction errorMassage = onView(withText("Fill empty fields"));
    public static ViewInteraction okButton = onView(withText("OK"));
}
