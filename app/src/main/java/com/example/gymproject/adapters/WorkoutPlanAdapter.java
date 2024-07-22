package com.example.gymproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.models.WorkoutPlan;

import java.util.List;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.ViewHolder> {
    private List<WorkoutPlan> workoutPlans;

    public WorkoutPlanAdapter(List<WorkoutPlan> workoutPlans) {
        this.workoutPlans = workoutPlans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutPlan workoutPlan = workoutPlans.get(position);
        holder.tvWorkoutPlanName.setText(workoutPlan.getName());
        holder.tvLastDate.setText("Last Workout Date: " + workoutPlan.getLastDate());
        holder.tvTimesDone.setText("Times Done: " + workoutPlan.getTimesDone());
        holder.tvDescription.setText("Description: " + workoutPlan.getDescription());
    }

    @Override
    public int getItemCount() {
        return workoutPlans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWorkoutPlanName;
        public TextView tvLastDate;
        public TextView tvTimesDone;
        public TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWorkoutPlanName = itemView.findViewById(R.id.workout_LBL_name);
            tvLastDate = itemView.findViewById(R.id.workout_LBL_last_date);
            tvTimesDone = itemView.findViewById(R.id.workout_LBL_times_done);
            tvDescription = itemView.findViewById(R.id.workout_LBL_description);
        }
    }
}
