package com.example.bea.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.bakingapp.R;
import com.example.bea.bakingapp.data.Steps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    ArrayList<Steps> stepsArrayList;
    ListItemClickListener listItemClickListener;

    public StepsAdapter(ListItemClickListener listItemClickListener, ArrayList<Steps> stepsArrayList){
        this.stepsArrayList = stepsArrayList;
        this.listItemClickListener = listItemClickListener;
    }
    @NonNull
    @Override
    public StepsAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.steps_list_item,parent,false);
        StepViewHolder viewHolder = new StepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepViewHolder holder, int position) {
    holder.idTextView.setText(String.valueOf(stepsArrayList.get(position).getId()));
    holder.shortDescriptionTextView.setText(stepsArrayList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItenIndex);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView idTextView;
        TextView shortDescriptionTextView;
        public StepViewHolder(View itemView) {
            super(itemView);

            idTextView = (TextView) itemView.findViewById(R.id.list_steps_id);
            shortDescriptionTextView = (TextView) itemView.findViewById(R.id.list_step_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
