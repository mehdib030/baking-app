package com.e.baking;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule(MainActivity.class,true,true);


    @Test
    public void testRecyclerVisible() {
        onView(ViewMatchers.withId(R.id.recipe_recycler_phone_view))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCaseForRecyclerClick() {
        onView(ViewMatchers.withId(R.id.recipe_recycler_phone_view))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

   /* @Test
    public void testCaseForRecyclerScroll() {

        // Get total item of RecyclerView
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.recipe_recycler_phone_view);
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> adapter = recyclerView.getAdapter();
        int itemCount = adapter.getItemCount();


        // Scroll to end of page with position
        onView(ViewMatchers.withId(R.id.recipe_recycler_phone_view))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }


    @Test
    public void testCaseForRecyclerItemView() {

        onView(ViewMatchers.withId(R.id.recipe_recycler_phone_view))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(withViewAtPosition(1, Matchers.allOf(
                        ViewMatchers.withId(R.id.recipe_name), isDisplayed()))));
    }


    public Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }*/

}
