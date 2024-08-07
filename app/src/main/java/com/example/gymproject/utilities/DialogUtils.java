package com.example.gymproject.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.gymproject.interfaces.AddPlanCallback;
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

}
