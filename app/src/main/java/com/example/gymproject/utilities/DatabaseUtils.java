package com.example.gymproject.utilities;

import com.example.gymproject.interfaces.OnExerciseSavedListener;
import com.example.gymproject.interfaces.OnExerciseLoadedListener ;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.models.PartialCustomExercise;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUtils {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference exercisesWarehouseRef = database.getReference("exercisesWarehouse");
    private static final DatabaseReference userWorkoutPlansRef = database.getReference("userWorkoutPlans");


    public static void addExerciseToWarehouse(BuiltExercise exercise) {
        Map<String, Object> exerciseData = new HashMap<>();
        exerciseData.put("mainMuscle", exercise.getMainMuscle());
        exerciseData.put("name", exercise.getName());
        exerciseData.put("imageUrl", exercise.getImageUrl());
        exercisesWarehouseRef.child(exercise.getId()).setValue(exerciseData);
    }

    public static void addCustomUserExercise(String userId, String workoutPlanId, String mainMuscle, String name, String imageUrl, int sets, int reps, int weight, int rest, String other) {
        DatabaseReference customExercisesRef = userWorkoutPlansRef.child(userId).child(workoutPlanId).child("customExercises");
        CustomExercise exercise = new CustomExercise(mainMuscle, name, imageUrl, sets, reps, weight, rest, other);
        customExercisesRef.child(name).setValue(exercise);
    }

    // פונקציה לטעינת תוכנית אימונים של משתמש
    public static void loadUserWorkoutPlan(String userId, String workoutPlanId, ValueEventListener listener) {
        userWorkoutPlansRef.child(userId).child(workoutPlanId).child("customExercises").addListenerForSingleValueEvent(listener);
    }

    public static void loadUserWorkoutWarehousePlan(String userId, String workoutPlanId, ValueEventListener listener) {
        userWorkoutPlansRef.child(userId).child(workoutPlanId).child("WarehouseExercises").addListenerForSingleValueEvent(listener);
    }

    public static void loadExerciseFromWarehouse(String exerciseId, final OnExerciseLoadedListener listener) {
        exercisesWarehouseRef.child(exerciseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BuiltExercise builtExercise = dataSnapshot.getValue(BuiltExercise.class);
                if (builtExercise != null) {
                    listener.onExerciseLoaded(builtExercise);
                } else {
                    listener.onFailure(new Exception("Built exercise is null"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    // פונקציה לטעינת כל התרגילים במחסן
    public static void loadAllWarehouseExercises(ValueEventListener listener) {
        exercisesWarehouseRef.addListenerForSingleValueEvent(listener);
    }

    // פונקציה להוספת תרגיל מהמחסן לתוכנית אימונים של משתמש
    public static void saveCustomUserExerciseFromLibrary(String userId, String workoutPlanId, PartialCustomExercise exercise, String exerciseId, OnExerciseSavedListener listener) {
        DatabaseReference customExercisesRef = userWorkoutPlansRef.child(userId).child(workoutPlanId).child("WarehouseExercises").child(exerciseId);
        customExercisesRef.setValue(exercise).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onSuccess();
            } else {
                listener.onFailure(task.getException());
            }
        });
    }

    public static void loadAllPlans(String userId,ValueEventListener listener) {
        userWorkoutPlansRef.child(userId).addListenerForSingleValueEvent(listener);
    }
    public static void addPlan(String userId, String planName, String planDescription) {
        DatabaseReference plansRef = userWorkoutPlansRef.child(userId).child(planName);
        plansRef.child("description").setValue(planDescription);
    }

    public static void updateCustomExerciseInFirebase(String userId, String planId, CustomExercise exercise, OnCompleteListener<Void> listener) {
                userWorkoutPlansRef
                .child(userId)
                .child(planId)
                .child("customExercises")
                .child(exercise.getName()).
                setValue(exercise).addOnCompleteListener(listener);
    }
}
