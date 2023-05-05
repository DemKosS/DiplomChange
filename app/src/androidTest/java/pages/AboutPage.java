package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AboutPage {
    public static ViewInteraction privacyLink = onView(withId(R.id.about_privacy_policy_value_text_view));
    public static ViewInteraction termsLink = onView(withId(R.id.about_terms_of_use_value_text_view));
    public static ViewInteraction backButton = onView(withId(R.id.about_back_image_button));
}
