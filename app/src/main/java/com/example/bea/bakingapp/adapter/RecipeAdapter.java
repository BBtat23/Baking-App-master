package com.example.bea.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.activities.RecipeMainActivity;
import com.example.bea.bakingapp.data.Recipe;
import com.example.bea.bakingapp.widget.Provider;
import com.example.bea.bakingapp.widget.WidgetDataProvider;
import com.google.gson.Gson;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeData;
    final private ListItemClickListener mOnclicklistener;
    Context mContext;
    public static final String SHARED_PREFS_KEY = "Preferences";

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public RecipeAdapter(Context context,List listRecipe, ListItemClickListener listener){
        this.mRecipeData = listRecipe;
        this.mOnclicklistener = listener;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.list_item,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        holder.nameRecipe.setText(mRecipeData.get(position).getName());
        holder.mImageRecipe.setImageResource(R.mipmap.ic_launcher_foreground);
//        String videoURL = mStepsData.get(position).getVideoURL();

//        Picasso.get().load("")
    }

    @Override
    public int getItemCount() {
        return mRecipeData.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView nameRecipe;
        ImageView mImageRecipe;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            nameRecipe = itemView.findViewById(R.id.titleRecipe);
            mImageRecipe = itemView.findViewById(R.id.imageRecipe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!=RecyclerView.NO_POSITION){
                        Recipe clickedataItem = mRecipeData.get(pos);
                        Intent intent = new Intent(mContext, RecipeMainActivity.class);
                        intent.putExtra("recipe",clickedataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);


                        Gson gson = new Gson();
                        String json = gson.toJson(clickedataItem);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SHARED_PREFS_KEY,json).commit();

                        Intent widgetIntent = new Intent(mContext, Provider.class);
                        mOnclicklistener.onListItemClick(pos);
                        widgetIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE\\");
                    }
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            int clickedPosition = getAdapterPosition();
//            mOnclicklistener.onListItemClick(clickedPosition);
//
//        }
    }


    public void swapData(List<Recipe> movieObjectArrayList) {
        if (movieObjectArrayList == null || movieObjectArrayList.size() == 0)
            return;
        if (mRecipeData != null && mRecipeData.size() > 0)
            mRecipeData.clear();
        mRecipeData.addAll(movieObjectArrayList);
        notifyDataSetChanged();
    }
}
