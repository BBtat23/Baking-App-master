package com.example.bea.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.data.Ingredients;
import com.example.bea.bakingapp.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ary on 10/9/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
	public static final String SELECTED_RECIPE = "com.example.ary.mimobakingapp.SELECTED_RECIPE";

	private Context context;
	private Intent intent;
	private List<Ingredients> ingredientList = new ArrayList<>();
	private List<Recipe> recipeList = new ArrayList<>();

	public WidgetDataProvider(Context context, Intent intent){
		this.context = context;
		this.intent = intent;
	}

	void initData(){
		String sRecipe = intent.getStringExtra(SELECTED_RECIPE);
		Recipe recipe = new GsonBuilder().create().fromJson(sRecipe, Recipe.class);
		ingredientList.addAll(recipe.getIngredientsArrayList());
	}

	@Override
	public void onCreate() {
		initData();
	}

	@Override
	public void onDataSetChanged() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public int getCount() {
		return ingredientList.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		Ingredients ingredient = ingredientList.get(position);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_row);
		views.setTextViewText(R.id.ingredient_name_text_view,ingredient.getIngredient());
		return views;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}