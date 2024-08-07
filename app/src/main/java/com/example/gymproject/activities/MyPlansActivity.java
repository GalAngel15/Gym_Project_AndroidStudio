package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.WorkoutPlanAdapter;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.WorkoutPlan;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlansActivity extends BaseActivity {
    private MaterialButton btnLogout, buttonOpenSettings;
    private RecyclerView recyclerView;
    private WorkoutPlanAdapter adapter;
    private List<WorkoutPlan> workoutPlans;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        btnLogout = findViewById(R.id.btnLogout);
        buttonOpenSettings = findViewById(R.id.buttonOpenSettings);
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
    }

    public void onPlanClick(String workoutPlanId) {
        Intent intent = new Intent(this, PlanPageActivity.class);
        intent.putExtra("planId", workoutPlanId);
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
                    String id = planSnapshot.getKey();
                    String name = "Workout Plan " + id;
                    WorkoutPlan workoutPlan = new WorkoutPlan(id, name, "Last Date", 0, "Description");
                    workoutPlans.add(workoutPlan);
                }
                adapter.notifyItemRangeInserted(0, workoutPlans.size());            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Exercise", "Database error: " + error.getMessage());
            }
        });
    }

}