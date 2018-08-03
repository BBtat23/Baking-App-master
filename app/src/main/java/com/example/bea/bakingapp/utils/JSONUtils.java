package com.example.bea.bakingapp.utils;

import android.util.Log;

import com.example.bea.bakingapp.data.Ingredients;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    //Get recipe object from Json
    public static ArrayList<Recipe> getRecipeJSONUtils(String recipeURLString) throws JSONException {
        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String INGREDIENTS_QUANTITY = "quantity";
        final String INGREDIENTS_MEASURE = "measure";
        final String INGREDIENTS_INGREDIENT = "ingredient";
        final String RECIPE_STEPS = "steps";
        final String STEPS_ID = "id";
        final String STEPS_SHORTDESCRIPTION = "shortDescription";
        final String STEPS_DESCRIPTION = "description";
        final String STEPS_VIDEOURL = "videoURL";
        final String STEPS_THUMBNAIL = "thumbnailURL";
        final String RECIPE_SERVINGS = "servings";
        int idRecipe;
        String nameRecipe;
        int servingsRecipe;
        //Array{} || Object[]
        // We start with an Array so we create a new JSONArray
        JSONArray jsonArrayRecipe = new JSONArray(recipeURLString);
        //ArrayList recipe,steps,ingredients.
        ArrayList<Recipe> recipes = new ArrayList<>();
        //We get the object of the Array (id,name,servings,steps,ingredients)
        for (int i = 0; i < jsonArrayRecipe.length(); i++) {
            JSONObject recipeObject = jsonArrayRecipe.optJSONObject(i);
            idRecipe = recipeObject.getInt(RECIPE_ID);
            nameRecipe = recipeObject.getString(RECIPE_NAME);
            servingsRecipe = recipeObject.getInt(RECIPE_SERVINGS);
            //List Ingredients and Steps if I put them outside this for loop. I have all the ingredients together an the same for the steps
            ArrayList<Ingredients> ingredients = new ArrayList<>();
            ArrayList<Steps> steps = new ArrayList<>();
            //Ingredients JSON Array
            JSONArray jsonArrayIngredients = recipeObject.optJSONArray(RECIPE_INGREDIENTS);
            //Iterate through the IngredientArray
            for (int j = 0; j < jsonArrayIngredients.length(); j++) {
                JSONObject ingredientsJSONObject = jsonArrayIngredients.getJSONObject(j);
                int ingredientsQuantity = ingredientsJSONObject.getInt(INGREDIENTS_QUANTITY);
                String ingredientsMeasure = ingredientsJSONObject.getString(INGREDIENTS_MEASURE);
                String ingredientsIngredient = ingredientsJSONObject.getString(INGREDIENTS_INGREDIENT);

                //Create Ingredients Object
                Ingredients ingredientObject = new Ingredients(ingredientsQuantity, ingredientsMeasure, ingredientsIngredient);
                Log.d("JSONUtils","ingredients count: " + ingredients.size());
                //Add ingredientObject to the ArrayList<Ingredients>
                ingredients.add(ingredientObject);
            }

            //Ingredients JSON Array
            JSONArray jsonArraySteps = recipeObject.optJSONArray(RECIPE_STEPS);
            //Iterate through the StepsArray
            for (int k = 0; k < jsonArraySteps.length(); k++) {
                JSONObject stepsJSONObject = jsonArraySteps.getJSONObject(k);
                int stepsId = stepsJSONObject.getInt(STEPS_ID);
                String stepsShortDescription = stepsJSONObject.getString(STEPS_SHORTDESCRIPTION);
                String stepsDescription = stepsJSONObject.getString(STEPS_DESCRIPTION);
                String stepsVideoURL = stepsJSONObject.getString(STEPS_VIDEOURL);
                String stepsThumbnailURL = stepsJSONObject.getString(STEPS_THUMBNAIL);
                Log.d("JSONUtils","recipes count: " + steps.size());
                //Create Steps Object
                Steps stepsObject = new Steps(stepsId, stepsShortDescription, stepsDescription, stepsVideoURL, stepsThumbnailURL);
                //Add stepsObject to the ArrayList<Steps>
                steps.add(stepsObject);
            }
            //Create new Recipe Object to all data
            Recipe recipeObj = new Recipe(idRecipe, nameRecipe, servingsRecipe, ingredients, steps);
            //Add recipeObj to the ArrayList<Recipe>
            Log.d("JSONUtils","recipes count: " + recipes.size());
            recipes.add(recipeObj);
        }
        return recipes;
    }
}
//            //Convert JSONArray to ArrayList<Steps>
//            for (int j = 0; j < jsonArraySteps.length(); j++){
//                steps.add(jsonArraySteps.getJSONObject(j));
//            }
//            //We create a new Recipe object with the values that we get from json
//            objectRecipe = new Recipe(idRecipe,nameRecipe,servingsRecipe,jsonArraySteps);
//            //We add the object to the ArrayList
//            recipes.add(objectRecipe);
//        }
//        //We return ArrayList
//        return recipes;
//    }
//
//    //Get ingredients object from Json
//    public static List<Ingredients> getIngredientsJSONUtils(String ingredientsURLString) throws JSONException {
//        int quantityIngredients;
//        String measureIngredients;
//        String ingredientIngredients;
//        //Array{} || Object[]
//        // We start with an Array so we create a new JSONArray
//        JSONArray jsonArrayRecipe = new JSONArray(ingredientsURLString);
//        //We create the object that we will add to the ArrayList
//        Ingredients objectIngredients = null;
//        //We create an ArrayList that will be an ArrayList of Ingredients objects
//        List<Ingredients> ingredients = new ArrayList<>();
//        //We get the object of the Array (ingredients)
//        for (int i = 0; i < jsonArrayRecipe.length(); i++){
//            JSONObject ingredientsObject = jsonArrayRecipe.getJSONObject(i);
//            //We get another Array from ingredients object
//            JSONArray ingredientsArray = ingredientsObject.getJSONArray("ingredients");
//            for (int j = 0; j < ingredientsArray.length(); j++){
//                //We get the object of the Array (quantity, measure, ingredient)
//                JSONObject ingredientsJSONObject = ingredientsArray.getJSONObject(j);
//                quantityIngredients = ingredientsJSONObject.getInt("quantity");
//                measureIngredients = ingredientsJSONObject.getString("measure");
//                ingredientIngredients = ingredientsJSONObject.getString("ingredient");
//                //We create a new Ingredients object with the values that we get from json
//                objectIngredients = new Ingredients(quantityIngredients,measureIngredients,ingredientIngredients);
//                //We add the object to the ArrayList
//                ingredients.add(objectIngredients);
//            }
//        }
//        //We return ArrayList
//        return ingredients;
//    }
//
//    public static List<Steps> getStepsJSONUtils(String stepsURLString) throws JSONException {
//        int idSteps;
//        String shortDescriptionSteps;
//        String descriptionSteps;
//        String videoUrlSteps;
//        String thumbnailUrlSteps;
//
//        JSONArray jsonArraySteps = new JSONArray(stepsURLString);
//        Steps objectSteps = null;
//        List<Steps> steps = new ArrayList<>();
//        for (int i = 0; i < jsonArraySteps.length(); i++ ){
//            JSONObject stepsJsonObject = jsonArraySteps.getJSONObject(i);
//            JSONArray stepsJsonArray = stepsJsonObject.getJSONArray("steps");
//            Log.i("JSONUtil", "steps count: " + stepsJsonArray.length());
//            for (int j = 0; j < stepsJsonArray.length(); j++){
//                JSONObject stepsObject = stepsJsonArray.getJSONObject(j);
//                idSteps = stepsObject.getInt("id");
//                shortDescriptionSteps = stepsObject.getString("shortDescription");
//                descriptionSteps = stepsObject.getString("description");
//                videoUrlSteps = stepsObject.getString("videoURL");
//                thumbnailUrlSteps = stepsObject.getString("thumbnailURL");
//
//                objectSteps = new Steps(idSteps,shortDescriptionSteps,descriptionSteps,videoUrlSteps,thumbnailUrlSteps);
//                steps.add(objectSteps);
//            }
//
//        }
//        return steps;
//    }

