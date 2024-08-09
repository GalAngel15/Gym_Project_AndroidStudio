package com.example.gymproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.interfaces.OnExerciseEditedListener;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.ImageLoader;


import java.util.List;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ExerciseViewHolder> {

    private Context context;
    private List<CustomExercise> exerciseList;
    private OnExerciseEditedListener onExerciseEditedListener, onExerciseDeletitedListener;

    public CustomExerciseAdapter(Context context, List<CustomExercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }
    public void setOnExerciseEditedListener(OnExerciseEditedListener listener) {
        this.onExerciseEditedListener = listener;
    }
    public void onExerciseDeletedListener(OnExerciseEditedListener listener) {
        this.onExerciseDeletitedListener = listener;
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

        holder.imageViewExercise.setImageDrawable(null);
        if (!exercise.getImageUrl().isEmpty())
            ImageLoader.getInstance().load(exercise.getImageUrl(), holder.imageViewExercise);

        holder.textViewExerciseName.setText(exercise.getName());
        holder.textViewSets.setText(String.valueOf(exercise.getSets()));
        holder.textViewReps.setText(String.valueOf(exercise.getReps()));
        holder.textViewWeight.setText(String.valueOf(exercise.getWeight()));
        holder.textViewRest.setText(String.valueOf(exercise.getRest()));
        holder.additionalComments.setText(exercise.getOther());
        holder.btnEdit.setOnClickListener(v -> {
            onExerciseEditedListener.onExerciseEdited(exercise);
        });
        holder.btnDelete.setOnClickListener(v -> {
            onExerciseDeletitedListener.onExerciseEdited(exercise);
        });

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
        TextView additionalComments;
        ImageButton btnEdit, btnDelete;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExercise = itemView.findViewById(R.id.imageViewExercise);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewSets = itemView.findViewById(R.id.textViewSets);
            textViewReps = itemView.findViewById(R.id.textViewReps);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewRest = itemView.findViewById(R.id.textViewRest);
            additionalComments = itemView.findViewById(R.id.additionalComments);
            btnEdit = itemView.findViewById(R.id.btnEditExercise);
            btnDelete = itemView.findViewById(R.id.btnDeleteExercise);
        }
    }
}
