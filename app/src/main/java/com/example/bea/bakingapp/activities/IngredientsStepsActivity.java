package com.example.bea.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.fragment.IngredientsStepsFragment;
import com.example.bea.bakingapp.fragment.StepsDetailFragment;

public class IngredientsStepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_steps_activity);
        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (RecipeMainActivity.twoPane){
                IngredientsStepsFragment ingredientsStepsFragment = new IngredientsStepsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredients_detail_activity,ingredientsStepsFragment)
                        .commit();

                StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_frame,stepsDetailFragment)
                        .commit();
            }else{
                IngredientsStepsFragment ingredientsStepsFragment = new IngredientsStepsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredients_detail_activity,ingredientsStepsFragment)
                        .commit();
        }
        }
    }

}
