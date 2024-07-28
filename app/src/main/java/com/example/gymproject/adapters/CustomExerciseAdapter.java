package com.example.gymproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.models.CustomExercise;


import java.util.List;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ExerciseViewHolder> {

    private Context context;
    private List<CustomExercise> exerciseList;

    public CustomExerciseAdapter(Context context, List<CustomExercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        CustomExercise exercise = exerciseList.get(position);
        holder.textViewExerciseName.setText(exercise.getName());
        holder.textViewSets.setText(String.valueOf(exercise.getSets()));
        holder.textViewReps.setText(String.valueOf(exercise.getReps()));
        holder.textViewWeight.setText(String.valueOf(exercise.getWeight()));
        holder.textViewRest.setText(String.valueOf(exercise.getRest()));

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewExercise;
        TextView textViewExerciseName;
        TextView textViewSets;
        TextView textViewReps;
        TextView textViewWeight;
        TextView textViewRest;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExercise = itemView.findViewById(R.id.imageViewExercise);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewSets = itemView.findViewById(R.id.textViewSets);
            textViewReps = itemView.findViewById(R.id.textViewReps);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewRest = itemView.findViewById(R.id.textViewRest);
        }
    }
}
