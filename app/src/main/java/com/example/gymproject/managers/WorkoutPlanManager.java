package com.example.gymproject.managers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.activities.HomePageActivity;
import com.example.gymproject.adapters.CustomExerciseAdapter;
import com.example.gymproject.interfaces.OnExerciseLoadedListener;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;


public class WorkoutPlanManager {
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private CustomExerciseAdapter adapter;
    private List<CustomExercise> exerciseList;
    private Context context;

    public WorkoutPlanManager(Context context, FirebaseUser currentUser) {
        this.currentUser = currentUser;
        this.context = context;
        this.exerciseList = new ArrayList<>();
        initRecyclerView();
        loadAllUserExercises("1");
    }

    public void initRecyclerView() {
        this.recyclerView = ((HomePageActivity) context).findViewById(R.id.recyclerView);
        adapter = new CustomExerciseAdapter(context, exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    private void loadAllUserExercises(String workoutPlanId){
        List<CustomExercise> tempList = new ArrayList<>();
        loadCustomExercisesList(workoutPlanId, tempList);
    }

    private void loadCustomExercisesList(String workoutPlanId, List<CustomExercise> tempList) {
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
