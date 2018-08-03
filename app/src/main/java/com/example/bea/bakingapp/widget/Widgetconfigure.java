package com.example.bea.bakingapp.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.data.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Widgetconfigure extends AppCompatActivity {
	private int mAppWidgetId;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		setContentView(R.layout.widget_recipe);

		RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String json = preferences.getString(RecipeAdapter.SHARED_PREFS_KEY, "");
		if (!json.equals("")) {
			Gson gson = new Gson();
			Recipe recipes = gson.fromJson(json, new TypeToken<Recipe>() {
			}.getType());
		}
		// rv.setAdapter(new RecyclerViewWidgetAdapter(recipes));
		rv.setLayoutManager(new LinearLayoutManager(this));
	}

	private void execute(Recipe recipe){
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		RemoteViews views = initViews(this, appWidgetManager, mAppWidgetId, recipe);
		appWidgetManager.updateAppWidget(mAppWidgetId, views);

		Intent resultValue = new Intent(this, MyWidgetRemoteViewsService.class);
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

		setResult(RESULT_OK, resultValue);

		finish();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private RemoteViews initViews(Context context, AppWidgetManager widgetManager, int widgetId, Recipe recipe) {

		RemoteViews mView = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);

		Intent intent = new Intent(context,MyWidgetRemoteViewsService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

		String sRecipe = new GsonBuilder().create().toJson(recipe);

		intent.putExtra(WidgetDataProvider.SELECTED_RECIPE, sRecipe);
		mView.setRemoteAdapter(widgetId, R.id.widget_list_view, intent);

		return mView;
	}


	class RecyclerViewWidgetAdapter extends RecyclerView.Adapter<RecyclerViewWidgetAdapter.WidgetAdapter>{

		private Context context;
		List<Recipe> recipes = new ArrayList<>();

		public RecyclerViewWidgetAdapter(ArrayList<Recipe> recipes){
			this.recipes = recipes;
		}

		@Override
		public WidgetAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
			context = parent.getContext();
			View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
			WidgetAdapter widgetAdapter = new WidgetAdapter(view);
			return widgetAdapter ;
		}

		@Override
		public void onBindViewHolder(WidgetAdapter holder, int position) {
			final Recipe recipe = recipes.get(position);
			holder.tv.setText(recipe.getName());

			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					execute(recipe);
				}
			});
		}

		@Override
		public int getItemCount() {
			if (recipes != null)
				return recipes.size();
			else return 0;
		}

		class WidgetAdapter extends RecyclerView.ViewHolder{
			@BindView(android.R.id.text1)
			TextView tv;
			public WidgetAdapter(View itemView) {
				super(itemView);
				ButterKnife.bind(this, itemView);
			}
		}

	}
}
