package com.example.gymproject.managers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.activities.PlanPageActivity;
import com.example.gymproject.adapters.CustomExerciseAdapter;
import com.example.gymproject.interfaces.OnExerciseLoadedListener;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.DialogUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.util.Log;
import android.widget.Toast;


public class WorkoutPlanManager {
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private CustomExerciseAdapter adapter;
    private List<CustomExercise> exerciseList;
    private Context context;
    private String workoutPlanId;

    public WorkoutPlanManager(Context context, FirebaseUser currentUser, String workoutPlanId) {
        this.currentUser = currentUser;
        this.context = context;
        this.exerciseList = new ArrayList<>();
        this.workoutPlanId = workoutPlanId;
        initRecyclerView();
    }

    public void initRecyclerView() {
        this.recyclerView = ((PlanPageActivity) context).findViewById(R.id.myPlansRecyclerView);
        adapter = new CustomExerciseAdapter(context, exerciseList);

        adapter.setOnExerciseEditedListener(exercise -> {
            DialogUtils.showEditExerciseDialog(context, exercise, updatedExercise -> {
                String userId = currentUser.getUid();
                DatabaseUtils.updateCustomExerciseInFirebase(userId, workoutPlanId, updatedExercise, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Exercise updated", Toast.LENGTH_SHORT).show();
                        int index = exerciseList.indexOf(exercise);
                        if (index != -1) {
                            exerciseList.set(index, updatedExercise);
                            adapter.notifyItemChanged(index);
                        }
                    } else {
                        Toast.makeText(context, "Failed to update exercise", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public void loadAllUserExercises(String workoutPlanId){
        List<CustomExercise> tempList = new ArrayList<>();
        loadCustomExercisesList(workoutPlanId, tempList);
    }

    private void loadCustomExercisesList(String workoutPlanId, List<CustomExercise> tempList) {
        String userId = currentUser != null ? currentUser.getUid() : null;
        if (userId == null || userId.isEmpty()) {
            Log.e("WorkoutPlanManager", "Error: User ID is null or empty");
            return;
        }
        if (workoutPlanId == null || workoutPlanId.isEmpty()) {
            Log.e("WorkoutPlanManager", "Error: Workout Plan ID is null or empty");
            return;
        }

        DatabaseUtils.loadUserWorkoutPlan(currentUser.getUid(), workoutPlanId, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CustomExercise exercise = snapshot.getValue(CustomExercise.class);
                        if (exercise != null) {
                            tempList.add(exercise);
                        }
                    }
                }
                // לאחר טעינת כל התרגילים המותאמים אישית, נטעין את תרגילי המחסן
                loadUserWorkoutWarehousePlan(workoutPlanId, tempList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutPlanManager", "Error loading custom exercises: " + databaseError.getMessage());
            }
        });
    }

    private void loadUserWorkoutWarehousePlan(String workoutPlanId, List<CustomExercise> tempList) {
        DatabaseUtils.loadUserWorkoutWarehousePlan(currentUser.getUid(), workoutPlanId, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int totalItems = (int) dataSnapshot.getChildrenCount();
                    int[] loadedItems = {0};

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CustomExercise exercise = snapshot.getValue(CustomExercise.class);
                        if (exercise != null) {
                            DatabaseUtils.loadExerciseFromWarehouse(snapshot.getKey(), new OnExerciseLoadedListener() {
                                @Override
                                public void onExerciseLoaded(BuiltExercise builtExercise) {
                                    exercise.setMainMuscle(builtExercise.getMainMuscle());
                                    exercise.setName(builtExercise.getName());
                                    exercise.setImageUrl(builtExercise.getImageUrl());
                                    tempList.add(exercise);
                                    loadedItems[0]++;

                                    if (loadedItems[0] == totalItems) {
                                        // מיון ועדכון ה-RecyclerView לאחר טעינת כל הפריטים מהמחסן
                                        updateAndSortExercises(tempList);
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.e("WorkoutPlanManager", "Error loading exercise from warehouse: " + e.getMessage());
                                    loadedItems[0]++;

                                    if (loadedItems[0] == totalItems) {
                                        // מיון ועדכון ה-RecyclerView לאחר טעינת כל הפריטים מהמחסן
                                        updateAndSortExercises(tempList);
                                    }
                                }
                            });
                        } else {
                            loadedItems[0]++;
                            if (loadedItems[0] == totalItems) {
                                // מיון ועדכון ה-RecyclerView לאחר טעינת כל הפריטים מהמחסן
                                updateAndSortExercises(tempList);
                            }
                        }
                    }
                } else {
                    // אם אין פריטים במחסן, נעדכן ונמיין את ה-RecyclerView עם התרגילים המותאמים אישית בלבד
                    updateAndSortExercises(tempList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WorkoutPlanManager", "Error loading warehouse exercises: " + databaseError.getMessage());
            }
        });
    }

    private void updateAndSortExercises(List<CustomExercise> tempList) {
        tempList.sort(new Comparator<CustomExercise>() {
            @Override
            public int compare(CustomExercise o1, CustomExercise o2) {
                return o1.getMainMuscle().compareTo(o2.getMainMuscle());
            }
        });
        exerciseList.addAll(tempList);
        adapter.notifyDataSetChanged();
    }
}
