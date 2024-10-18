package com.example.gymproject.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.interfaces.OnExerciseEditedListener;
import com.example.gymproject.managers.WorkoutPlanManager;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.ImageLoader;


import java.util.List;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ExerciseViewHolder> {

    private final Context context;
    private List<CustomExercise> exerciseList;
    private WorkoutPlanManager workoutPlanManager;
    private OnExerciseEditedListener onExerciseEditedListener, onExerciseDeletitedListener, onDoneSetListener;


    public CustomExerciseAdapter(Context context, List<CustomExercise> exerciseList, WorkoutPlanManager workoutPlanManager) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.workoutPlanManager = workoutPlanManager;
    }

    public void setOnExerciseEditedListener(OnExerciseEditedListener listener) {
        this.onExerciseEditedListener = listener;
    }

    public void setOnExerciseDeletedListener(OnExerciseEditedListener listener) {
        this.onExerciseDeletitedListener = listener;
    }

    public void setOnDoneSetListener(OnExerciseEditedListener listener) {
        this.onDoneSetListener = listener;
    }

    private void setButtonColors(ExerciseViewHolder holder, int color) {
        holder.btnDoneSet.setColorFilter(ContextCompat.getColor(context, color));
        holder.btnDelete.setColorFilter(ContextCompat.getColor(context, color));
        holder.btnEdit.setColorFilter(ContextCompat.getColor(context, color));
    }


    public void setTableRowBorderColor(View tableRow, int color) {
        // קבלת ה-drawable הנוכחי של הרקע
        GradientDrawable background = (GradientDrawable) tableRow.getBackground();

        // שינוי צבע ה-stroke (המסגרת)
        background.setStroke(3, ContextCompat.getColor(context, color)); // 3dp רוחב הקו

        // עדכון הרקע
        tableRow.setBackground(background);
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
        int completedSets = workoutPlanManager.getSetsForExercise(exercise.getName());

        holder.imageViewExercise.setImageDrawable(null);
        if (!exercise.getImageUrl().isEmpty())
            ImageLoader.getInstance().load(exercise.getImageUrl(), holder.imageViewExercise);

        if (completedSets == 0) {
            setButtonColors(holder, R.color.red); // צבע התחלה
            setTableRowBorderColor(holder.itemView, R.color.red); // צבע התחלה
        } else if (completedSets < exercise.getSets()) {
            setButtonColors(holder, R.color.yellow); // צבע בזמן ביצוע סטים
            setTableRowBorderColor(holder.itemView, R.color.yellow); // צבע התחלה
        } else {
            setButtonColors(holder, R.color.green); // צבע כשכל הסטים הושלמו
            setTableRowBorderColor(holder.itemView, R.color.green); // צבע התחלה
        }

        holder.textViewExerciseName.setText(exercise.getName());
        holder.textViewSets.setText(String.valueOf(exercise.getSets()));
        holder.textViewReps.setText(String.valueOf(exercise.getReps()));
        holder.textViewWeight.setText(String.valueOf(exercise.getWeight()));
        holder.textViewRest.setText(String.valueOf(exercise.getRest()));
        holder.additionalComments.setText(exercise.getOther());
        holder.btnEdit.setOnClickListener(v -> {
            onExerciseEditedListener.onExerciseEdited(exercise, position);
        });
        holder.btnDelete.setOnClickListener(v -> {
            onExerciseDeletitedListener.onExerciseEdited(exercise, position);
        });
        holder.btnDoneSet.setOnClickListener(v->{
            onDoneSetListener.onExerciseEdited(exercise, position);
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
        ImageButton btnEdit, btnDelete, btnDoneSet;

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
            btnDoneSet = itemView.findViewById(R.id.btnDoneSet);
        }
    }
}
