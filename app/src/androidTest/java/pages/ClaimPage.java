package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;

public class ClaimPage {

    public static ViewInteraction closeButton = onView(withId(R.id.close_image_button));
    public static ViewInteraction addComment = onView(withId(R.id.add_comment_image_button));
    public static ViewInteraction statusButton = onView(withId(R.id.status_processing_image_button));
    public static ViewInteraction editButton = onView(withId(R.id.edit_processing_image_button));
    public static ViewInteraction editCommentButton = onView(withId(R.id.edit_comment_image_button));
    public static ViewInteraction takeToWork = onView(withId(R.id.in_progress_list_item));
    public static ViewInteraction cancel = onView(withId(R.id.cancel_list_item));
    public static ViewInteraction throwOff = onView(withId(R.id.throw_off_list_item));
    public static ViewInteraction execute = onView(withId(R.id.executes_list_item));

}
