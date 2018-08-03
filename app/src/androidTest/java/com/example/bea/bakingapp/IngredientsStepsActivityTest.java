package com.example.bea.bakingapp;

import android.content.res.Resources;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.bea.bakingapp.activities.IngredientsStepsActivity;
import com.example.bea.bakingapp.activities.RecipeMainActivity;
import com.example.bea.bakingapp.fragment.RecipeFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class IngredientsStepsActivityTest {
    private IdlingResource mIdlingResource;
    @Rule
    public ActivityTestRule<RecipeMainActivity> mRecipeActivityTest =
            new ActivityTestRule<>(RecipeMainActivity.class);

    @Before
    public void register() {
        mIdlingResource = (IdlingResource) mRecipeActivityTest.getActivity().getResources();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void recyclerViewVisible(){
        onView(withId(R.id.recipe_recycler_view))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregister(){
        if (mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

//    @Test
//    public void IngredientsStepsActivity_Display(){
//            onView(withId(R.id.recipe_recycler_view)).check(matches(isDisplayed()));
//    }


}
