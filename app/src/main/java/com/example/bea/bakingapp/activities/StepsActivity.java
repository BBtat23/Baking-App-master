package com.example.bea.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.fragment.RecipeFragment;
import com.example.bea.bakingapp.fragment.StepsDetailFragment;

public class StepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_details_main);

        if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_frame, stepsDetailFragment)
                        .commit();
        }
    }
}
