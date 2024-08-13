package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.WorkoutPlanAdapter;
import com.example.gymproject.models.WorkoutPlan;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.DialogUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlansActivity extends BaseActivity {
    private MaterialButton btnLogout, buttonOpenSettings, btnAddWorkoutPlan;
    private RecyclerView recyclerView;
    private WorkoutPlanAdapter adapter;
    private List<WorkoutPlan> workoutPlans;
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plans);

        initRecyclerView();
        initView();
        initButtons();
        loadWorkoutPlans();

    }

    private void initRecyclerView() {
        workoutPlans = new ArrayList<>();
        recyclerView = findViewById(R.id.myPlansRecyclerView);
        adapter = new WorkoutPlanAdapter(this, workoutPlans);
        adapter.setOnPlanClickListener(this::onPlanClick);
        adapter.setOnPlanDeletedListener(this::onPlanDeleted);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        textViewUsername = findViewById(R.id.textViewWelcome);
        textViewUsername.setText("Welcome, " + currentUser.getDisplayName() + "!");
        btnLogout = findViewById(R.id.btnLogout);
        buttonOpenSettings = findViewById(R.id.buttonOpenSettings);
        btnAddWorkoutPlan = findViewById(R.id.addWorkoutPlan);
    }

    private void initButtons() {
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        buttonOpenSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
        btnAddWorkoutPlan.setOnClickListener(v -> {
            DialogUtils.showAddWorkoutPlanDialog(this, workoutPlans, this::onAddPlan);
        });

    }

    public void onPlanClick(WorkoutPlan workoutPlanId) {
        Intent intent = new Intent(this, PlanPageActivity.class);
        intent.putExtra("planId", workoutPlanId.getName());
        startActivity(intent);
    }

    private void loadWorkoutPlans() {
        DatabaseUtils.loadAllPlans(currentUser.getUid(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workoutPlans.clear();
                if (!dataSnapshot.exists()) {
                    Log.e("workoutPlans", "snapshot don't exists");
                    return;
                }

                for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                    String name = planSnapshot.getKey();
                    String description = planSnapshot.child("description").getValue(String.class);
                    WorkoutPlan workoutPlan = new WorkoutPlan(name, "Last Date", 0, description);
                    workoutPlans.add(workoutPlan);
                }
                adapter.notifyItemRangeInserted(0, workoutPlans.size());            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Exercise", "Database error: " + error.getMessage());
            }
        });
    }

    public void onAddPlan(String planName, String planDescription) {
            DatabaseUtils.addPlan(currentUser.getUid(), planName, planDescription);
            WorkoutPlan plan = new WorkoutPlan(planName, planDescription);
            onPlanClick(plan);
    }
    public void onPlanDeleted(WorkoutPlan plan) {
        DialogUtils.showDeletePlanDialog(this, plan, deletedPlan -> {
            DatabaseUtils.deletePlanFromFirebase(currentUser.getUid(), plan.getName(), task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Exercise deleted", Toast.LENGTH_SHORT).show();
                    int index = workoutPlans.indexOf(plan);
                    if (index != -1) {
                        workoutPlans.remove(index);
                        adapter.notifyItemRemoved(index);
                    }
                } else {
                    Toast.makeText(this, "Failed to delete exercise", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}