package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;

public class OurMissionPage {
    public static Matcher<View> cardOpen = withId(R.id.our_mission_item_open_card_image_button);
    public static Matcher<View> description = withId(R.id.our_mission_item_description_text_view);

}
