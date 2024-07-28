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
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.ImageLoader;

import java.util.List;

public class BuiltExerciseAdapter extends RecyclerView.Adapter<BuiltExerciseAdapter.BuiltExerciseViewHolder> {

    private Context context;
    private List<BuiltExercise> exerciseList;

    public BuiltExerciseAdapter(Context context, List<BuiltExercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public BuiltExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout_exercise, parent, false);
        return new BuiltExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuiltExerciseViewHolder holder, int position) {
        BuiltExercise exercise = exerciseList.get(position);
        String imageUrl = exercise.getImageUrl();
        holder.textViewExerciseName.setText(exercise.getName());

        ImageLoader.getInstance().load(imageUrl, holder.imageViewExercise);

//        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // קריאה לפונקציה שתבצע את הפעולה הרצויה
//                onAddButtonClick(exercise);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class BuiltExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewExercise;
        TextView textViewExerciseName;
        ImageButton btnAdd;


        public BuiltExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExercise = itemView.findViewById(R.id.imageViewExercise);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
    private void onAddButtonClick(BuiltExercise exercise) {
        return;
    }
}
