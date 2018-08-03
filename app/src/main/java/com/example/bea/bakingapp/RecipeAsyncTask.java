package com.example.bea.bakingapp;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.utils.JSONUtils;
import com.example.bea.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

//Create a new AsyncTask
public class RecipeAsyncTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {
    public AsyncResponse delegate =null;
    @Override
    protected ArrayList<Recipe> doInBackground(Void... voids) {

        //Create URL
        URL urlApi = NetworkUtils.buildUrlApi();
        //Pass URL to the connection of internet
        ArrayList<Recipe> recipeJson = null;
        try {
            String urlApiConnection = NetworkUtils.getResponseFromHttpUrl(urlApi);
            //Pass the string above to get RecipeJSON
            recipeJson = JSONUtils.getRecipeJSONUtils(urlApiConnection);
            Log.i("AsyncTask", "recipe count: " + recipeJson.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeJson;
    }
    @Override
    protected void onPostExecute(ArrayList<Recipe> recipeArrayList) {
    delegate.processFinish(recipeArrayList);
    }
}

//    public class IngredientsAsynctask extends AsyncTask<Void, Void, List<Ingredients>> {
//
//        @Override
//        protected List<Ingredients> doInBackground(Void... voids) {
//
//            URL urlApi = NetworkUtils.buildUrlApi();
//
//            try {
//                String urlApiConnection = NetworkUtils.getResponseFromHttpUrl(urlApi);
//                List<Ingredients> ingredientsJson = JSONUtils.getIngredientsJSONUtils(urlApiConnection);
//
//                return ingredientsJson;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<Ingredients> ingredients) {
//            super.onPostExecute(ingredients);
//        }
//    }
//
//    public class StepsAsynctask extends AsyncTask<Void, Void, List<Steps>> {
//
//        @Override
//        protected List<Steps> doInBackground(Void... voids) {
//
//            URL urlApi = NetworkUtils.buildUrlApi();
//
//            try {
//                String urlApiConnection = NetworkUtils.getResponseFromHttpUrl(urlApi);
//                List<Steps> stepsJson = JSONUtils.getStepsJSONUtils(urlApiConnection);
//                Log.i("AsyncTask", "steps count: " + stepsJson.size());
//                return stepsJson;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<Steps> steps) {
//            super.onPostExecute(steps);
//        }
//    }


