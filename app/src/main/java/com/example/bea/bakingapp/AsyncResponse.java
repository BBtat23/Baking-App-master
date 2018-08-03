package com.example.bea.bakingapp;

import com.example.bea.bakingapp.data.Recipe;

import java.util.ArrayList;

public interface AsyncResponse{
    void processFinish(ArrayList<Recipe> recipeOutput);
}
