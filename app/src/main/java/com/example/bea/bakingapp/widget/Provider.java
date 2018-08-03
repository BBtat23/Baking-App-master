package com.example.bea.bakingapp.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.activities.RecipeMainActivity;
import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.data.Ingredients;
import com.example.bea.bakingapp.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander on 9/17/2017.
 */

public class Provider extends AppWidgetProvider {

    Gson gson;
    private ArrayList<Recipe> mRecipe;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);
            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(RecipeAdapter.SHARED_PREFS_KEY, "");

        Type type = new TypeToken<Recipe>(){}.getType();


        Recipe recipes = new GsonBuilder().create().fromJson(json, type);

//        Recipe recipe = recipes.get(0);
        String sRecipe = new GsonBuilder().create().toJson(recipes);
//
        intent.putExtra(WidgetDataProvider.SELECTED_RECIPE, String.valueOf(sRecipe));
        mView.setRemoteAdapter(widgetId, R.id.widget_list_view, intent);

        return mView;
    }
}
// Main Recipe Title String after conversion