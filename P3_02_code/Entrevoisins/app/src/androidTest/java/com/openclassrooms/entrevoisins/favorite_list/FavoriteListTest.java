package com.openclassrooms.entrevoisins.favorite_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;


/**
 * Test class for list of favorite neighbours
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavoriteListTest {

    // This is fixed
    private static int NO_ITEM = 0;
    private static int ITEM_COUNT = 1;
    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        /**  Init the Neighbour list  **/
        mActivity = mActivityRule.getActivity();
        mActivity.mNeighbours = DI.getNewInstanceApiService().getNeighbours();
    }

    /**
     * We ensure that our favorite list is displaying after click
     */
    @Test
    public void goToFavoriteList() {
        onView(withText("FAVORITES")).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite));
    }

    /**
     * We ensure that our favorite list is displaying with only favorites items
     */
    @Test
    public void onlyFavoritesItems() {

        /** We ensure the favorite list is empty **/
        onView(withText("FAVORITES")).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(NO_ITEM));

        /** Make one Neighbour Favorite and we ensure that favorite button working **/
        onView(withText("MY NEIGHBOURS")).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.favoriteActionButton)).perform(click());
        pressBack();

        /** Checks if the favorites list contains the added neighbour **/
        onView(withText("FAVORITES")).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(ITEM_COUNT));
    }

    /**
     * When we delete an favorite item, the item is no more shown
     */
    @Test
    public void shouldRemoveFavoriteItem() {

        /** Make one Neighbour Favorite and we ensure that favorite button working **/
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.favoriteActionButton)).perform(click());
        pressBack();

        /** Checks if the favorites list contains the added neighbour **/
        onView(withText("FAVORITES")).perform(click());
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(ITEM_COUNT));

        /** Checks if the user is well removed after the click on delete button **/
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(ViewMatchers.withId(R.id.list_neighboursFavorite)).check(withItemCount(NO_ITEM));
    }


}