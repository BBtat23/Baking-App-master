package com.example.bea.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.data.Ingredients;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<Ingredients> ingredientsArrayList;

    public IngredientsAdapter(ArrayList<Ingredients> ingredientsArrayList){
        this.ingredientsArrayList = ingredientsArrayList;
    }
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_list_item,parent,false);
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {
        holder.ingredientsTextView.setText(ingredientsArrayList.get(position).getIngredient());
        holder.quantityTextView.setText(Integer.toString(ingredientsArrayList.get(position).getQuantity()));
        holder.measureTextView.setText(ingredientsArrayList.get(position).getMeasure());
    }


    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientsTextView;
        TextView quantityTextView;
        TextView measureTextView;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.ingredients_text_view);
            quantityTextView = (TextView) itemView.findViewById(R.id.quantity_text_view);
            measureTextView = (TextView) itemView.findViewById(R.id.measure_text_view);
        }
    }
}
