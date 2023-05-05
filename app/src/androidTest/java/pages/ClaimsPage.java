package pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ClaimsPage {
    public static ViewInteraction filterButton = onView(withId(R.id.filters_material_button));
    public static ViewInteraction addButton = onView(withId(R.id.add_new_claim_material_button));
    public static ViewInteraction claimCard = onView(withId(R.id.claim_list_card));
    public static ViewInteraction openButton = onView(withId(R.id.item_filter_open));
    public static ViewInteraction progressButton = onView(withId(R.id.item_filter_in_progress));
    public static ViewInteraction executedButton = onView(withId(R.id.item_filter_executed));
    public static ViewInteraction cancelledButton = onView(withId(R.id.item_filter_cancelled));
    public static ViewInteraction okButton = onView(withId(R.id.claim_list_filter_ok_material_button));
    public static ViewInteraction cancelButton = onView(withId(R.id.claim_filter_cancel_material_button));
}
