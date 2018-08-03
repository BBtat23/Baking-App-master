package com.example.bea.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.test.rule.ActivityTestRule;

import com.example.bea.bakingapp.activities.RecipeMainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeMainActivity> mRecipeActivityTest =
            new ActivityTestRule<>(RecipeMainActivity.class);

    @Test
    public void recyclerViewVisible(){
        onView(withId(R.id.recipe_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerViewItem_OpenActivityListIngredientsSteps(){
        onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.ingredients_list))
                .check(matches(isDisplayed()));

        onView(withId(R.id.steps_list_details))
                .check(matches(isDisplayed()));
    }
}
