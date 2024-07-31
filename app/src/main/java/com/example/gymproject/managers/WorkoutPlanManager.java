package com.example.gymproject.managers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.activities.HomePageActivity;
import com.example.gymproject.adapters.CustomExerciseAdapter;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


public class WorkoutPlanManager {
    FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private CustomExerciseAdapter adapter;
    private List<CustomExercise> exerciseList;
    private Context context;

    public WorkoutPlanManager(Context context, FirebaseUser currentUser) {
        this.currentUser = currentUser;
        this.context = context;
        exerciseList = new ArrayList<>();
        init();
    }

    public void init() {
        this.recyclerView = ((HomePageActivity) context).findViewById(R.id.recyclerView);
        adapter = new CustomExerciseAdapter(context, exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        loadExerciseList("1"); // העברת ה-ID של תוכנית האימונים הנוכחית
    }

    private void loadExerciseList(String workoutPlanId) {
        exerciseList.clear();
        DatabaseUtils.loadUserWorkoutPlan(currentUser.getUid(), workoutPlanId, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    System.out.println("No exercises found in the warehouse.");
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CustomExercise exercise = snapshot.getValue(CustomExercise.class);
                    if (exercise != null) {
                        Log.e("Exercise", "Loaded exercise: " + exercise.getName() + ", Sets: " + exercise.getSets() + ", Reps: " + exercise.getReps());
                        exerciseList.add(exercise);
                    } else {
                        Log.e("Exercise", "Exercise is null");
                    }
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutPlanManager", "Error loading exercise: " + databaseError.getMessage());
            }
        });
    }

}