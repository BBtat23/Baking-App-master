package com.example.bea.bakingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bea.bakingapp.AsyncResponse;
import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.RecipeAsyncTask;
import com.example.bea.bakingapp.activities.IngredientsStepsActivity;
import com.example.bea.bakingapp.adapter.RecipeAdapter;
import com.example.bea.bakingapp.data.Recipe;

import java.util.ArrayList;

// This fragment displays the MediaPlayer, Recipe step instruction and Navigation in one large list
public class RecipeFragment extends Fragment implements RecipeAdapter.ListItemClickListener, AsyncResponse {
    RecyclerView recyclerView;
    RecipeAdapter mAdapter;
    ArrayList<Recipe> recipeArrayList;
    RecipeAsyncTask recipeAsyncTask = new RecipeAsyncTask();
    public static final String RECIPE_LIST = "recipeList";
    public static final String POSITION = "position";
    //Mandatory empty constructor
    public RecipeFragment(){};

    //Inflates views

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler_view);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(RECIPE_LIST)){
                recipeArrayList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
            }
        }
        //Check if the recipeArrayList is null, if not run the task
        if (recipeArrayList == null){
            recipeAsyncTask.delegate = this;
            recipeAsyncTask.execute();
        }else {
            loadRecyclerView(recipeArrayList);
        }

    }

    //Method to load the RecyclerView
    public void loadRecyclerView(ArrayList<Recipe> recipeArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecipeAdapter(getContext(),recipeArrayList,this);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getContext(), IngredientsStepsActivity.class);
        intent.putParcelableArrayListExtra(RECIPE_LIST,recipeArrayList);
        intent.putExtra(POSITION,clickedItemIndex);
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST,recipeArrayList);
    }

    //Getting recipeArrayList from the RecipeAsyncTask
    @Override
    public void processFinish(ArrayList<Recipe> recipeOutput) {
    recipeArrayList = recipeOutput;
    loadRecyclerView(recipeArrayList);
    }
}
