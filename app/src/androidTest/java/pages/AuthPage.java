package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AuthPage {
    public static ViewInteraction loginInput = onView(withId(R.id.log_id));
    public static ViewInteraction passwordInput = onView((withId(R.id.pass_id)));
    public static ViewInteraction signInButton = onView(withId(R.id.enter_button));
    public static ViewInteraction errorMassage = onView(withText(R.string.empty_login_or_password));
}
