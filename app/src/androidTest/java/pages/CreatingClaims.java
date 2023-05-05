package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class CreatingClaims {
    public static ViewInteraction title = onView(withId(R.id.title_edit_text));
    public static ViewInteraction executor = onView(withId(R.id.executor_drop_menu_auto_complete_text_view));
    public static ViewInteraction datePlane = onView(withId(R.id.date_in_plan_text_input_edit_text));
    public static ViewInteraction time = onView(withId(R.id.time_in_plan_text_input_edit_text));
    public static ViewInteraction description = onView(withId(R.id.description_edit_text));
    public static ViewInteraction saveButton = onView(withId(R.id.save_button));
    public static ViewInteraction canselButton = onView(withId(R.id.cancel_button));

}
