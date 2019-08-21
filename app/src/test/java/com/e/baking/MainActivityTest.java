package com.e.baking;



import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsAnything.anything;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  //@Rule
    //public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void clickGridViewItem_OpensOrderActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
       // onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click());

        // Checks that the OrderActivity opens with the correct tea name displayed
        //onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));*/


    }

}
