package com.example.gymproject.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.gymproject.R;
import com.example.gymproject.interfaces.AddPlanCallback;
import com.example.gymproject.interfaces.OnExerciseEditedListener;
import com.example.gymproject.interfaces.OnExerciseSavedListener;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.models.WorkoutPlan;

import java.util.List;

public class DialogUtils {

    public static void showAddWorkoutPlanDialog(Context context, List<WorkoutPlan> workoutPlans, AddPlanCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter the Plan's Name");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputName = new EditText(context);
        inputName.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        inputName.setHint("Enter the Plan's Name");
        layout.addView(inputName);

        final TextView errorText = new TextView(context);
        errorText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        errorText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        errorText.setVisibility(View.GONE);
        layout.addView(errorText);

        final EditText inputDesc = new EditText(context);
        inputDesc.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        inputDesc.setHint("Enter the Plan's Description (optional)");
        layout.addView(inputDesc);
        builder.setView(layout);

        builder.setPositiveButton("OK", null); // Will be created
        builder.setNegativeButton("Cancel", (dialog, which) ->{
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();

        // Positive btn (OK)
        dialog.setOnShowListener(dlg -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String planName = inputName.getText().toString();
                String desc = inputDesc.getText().toString();
                if (planName.isEmpty()) {
                    errorText.setText("Must enter a name");
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    for(WorkoutPlan plan: workoutPlans){
                        if (plan.getName().equals(planName)) {
                            errorText.setText("A workout plan with this name already exists");
                            errorText.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    callback.onAddPlan(planName, desc);
                    dialog.dismiss();
                }
            });
        });

        dialog.show();

    }

    public static void showEditExerciseDialog(Context context, CustomExercise exercise, OnExerciseEditedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Exercise");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.edit_exercise_dialog, null);
        EditText inputSets = viewInflated.findViewById(R.id.editExerciseSets);
        EditText inputReps = viewInflated.findViewById(R.id.editExerciseReps);
        EditText inputWeight = viewInflated.findViewById(R.id.editExerciseWeight);
        EditText inputRestTime = viewInflated.findViewById(R.id.editExerciseRestTime);
        EditText inputNotes = viewInflated.findViewById(R.id.editExerciseNotes);

        inputSets.setText(String.valueOf(exercise.getSets()));
        inputReps.setText(String.valueOf(exercise.getReps()));
        inputWeight.setText(String.valueOf(exercise.getWeight()));
        inputRestTime.setText(String.valueOf(exercise.getRest()));
        inputNotes.setText(exercise.getOther());

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", (dialog, which) -> {
            //להוסיף בדיקה
            exercise.setSets(Integer.parseInt(inputSets.getText().toString()));
            exercise.setReps(Integer.parseInt(inputReps.getText().toString()));
            exercise.setWeight(Double.parseDouble(inputWeight.getText().toString()));
            exercise.setRest(Integer.parseInt(inputRestTime.getText().toString()));
            exercise.setOther(inputNotes.getText().toString());
            listener.onExerciseEdited(exercise);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
