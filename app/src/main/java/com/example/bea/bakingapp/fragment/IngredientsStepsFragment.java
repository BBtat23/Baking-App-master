package com.example.bea.bakingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.activities.RecipeMainActivity;
import com.example.bea.bakingapp.activities.StepsActivity;
import com.example.bea.bakingapp.adapter.IngredientsAdapter;
import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.adapter.StepsAdapter;
import com.example.bea.bakingapp.data.Ingredients;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.data.Steps;

import java.util.ArrayList;

public class IngredientsStepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {
    RecyclerView ingredientsRecyclerView;
    RecyclerView stepsRecyclerView;
    ArrayList<Steps> stepsArrayList;
    ArrayList<Ingredients> ingredientsArrayList;
    ArrayList<Recipe> recipeArrayList;
    int position;
    public IngredientsStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_ingredients_detail,container,false);
        //Creating RecyclerView instance
        ingredientsRecyclerView = (RecyclerView)rootView.findViewById(R.id.ingredients_list);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.steps_list_details);

        //Setting layoutManager
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Getting data in recipeList with position
        recipeArrayList = getActivity().getIntent().getParcelableArrayListExtra(RecipeFragment.RECIPE_LIST);
        position = getActivity().getIntent().getExtras().getInt(RecipeFragment.POSITION);

        //Getting steps and ingredients from the recipeArrayList
        stepsArrayList = recipeArrayList.get(position).getStepsArrayList();
        ingredientsArrayList = recipeArrayList.get(position).getIngredientsArrayList();


        //Ingredients and steps Adapter with RecyclerViews
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientsArrayList);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        StepsAdapter stepsAdapter = new StepsAdapter(this, stepsArrayList);
        stepsRecyclerView.setAdapter(stepsAdapter);

        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItenIndex) {
        if (!RecipeMainActivity.twoPane){
            Intent intent = new Intent(getActivity(), StepsActivity.class);
            intent.putParcelableArrayListExtra(RecipeFragment.RECIPE_LIST,stepsArrayList);
            intent.putExtra(RecipeFragment.POSITION,clickedItenIndex);
            startActivity(intent);
        }else{
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.position = clickedItenIndex;
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_frame, stepsDetailFragment)
                    .commit();
        }
    }
}
