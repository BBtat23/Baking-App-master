package com.example.bea.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.fragment.RecipeFragment;
import com.example.bea.bakingapp.widget.WidgetDataProvider;

import java.util.ArrayList;

public class RecipeMainActivity extends AppCompatActivity{
    private static final String PARCEL_KEY = "RECIPE LIST";
    ArrayList<Recipe> mRecipe;
    public static boolean twoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            if (findViewById(R.id.tablet_layout) != null) {
                twoPane = true;
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeFragment recipeFragment = new RecipeFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.tablet_layout, recipeFragment)
                        .commit();
            } else {
                if (findViewById(R.id.phone_layout) != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    RecipeFragment recipeFragment = new RecipeFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.phone_layout, recipeFragment)
                            .commit();
                }
            }
        }

    }

    public void updateWidget() {
        Intent intent = new Intent(this, WidgetDataProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),
                WidgetDataProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra(PARCEL_KEY, mRecipe);
        sendBroadcast(intent);
    }

}