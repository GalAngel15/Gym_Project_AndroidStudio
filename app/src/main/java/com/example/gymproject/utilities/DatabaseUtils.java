package com.example.gymproject.utilities;

import com.example.gymproject.models.CustomExercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseUtils {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference exercisesWerehouseRef = database.getReference("exercisesWarehouse");
    private static DatabaseReference userWorkoutPlansRef = database.getReference("userWorkoutPlans");

    // פונקציה להוספת תרגיל למחסן התרגילים
    public static void addExerciseToWarehouse(String exerciseId, String mainMuscle, String name, String imageUrl) {
        CustomExercise exercise = new CustomExercise(mainMuscle, name, imageUrl);
        exercisesWerehouseRef.child(exerciseId).setValue(exercise);
    }

    public static void addCustomUserExercise(String userId, String workoutPlanId, String mainMuscle, String name, String imageUrl, int sets, int reps, int weight, int rest, String other) {
        DatabaseReference customExercisesRef = userWorkoutPlansRef.child(userId).child(workoutPlanId).child("customExercises");
        String exerciseId = customExercisesRef.push().getKey(); // יצירת מזהה ייחודי
        CustomExercise exercise = new CustomExercise(mainMuscle, name, imageUrl, sets, reps, weight, rest, other);
        customExercisesRef.child(exerciseId).setValue(exercise);
    }

    // פונקציה להוספת תרגיל מהמחסן לתוכנית אימונים של משתמש
    public static void addWarehouseUserExercise(String userId, String workoutPlanId, String exerciseId, int sets, int reps, int weight, int rest, String other) {
        DatabaseReference exerciseRef = exercisesWerehouseRef.child(exerciseId);
        exerciseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CustomExercise warehouseExercise = dataSnapshot.getValue(CustomExercise.class);
                if (warehouseExercise != null) {
                    warehouseExercise.setSets(sets);
                    warehouseExercise.setReps(reps);
                    warehouseExercise.setWeight(weight);
                    warehouseExercise.setRest(rest);
                    warehouseExercise.setOther(other);
                    userWorkoutPlansRef.child(userId).child(workoutPlanId).child("warehouseExercises").child(exerciseId).setValue(warehouseExercise);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    // פונקציה לטעינת תוכנית אימונים של משתמש
    public static void loadUserWorkoutPlan(String userId, String workoutPlanId, ValueEventListener listener) {
        userWorkoutPlansRef.child(userId).child(workoutPlanId).child("customExercises").addListenerForSingleValueEvent(listener);
    }

    // פונקציה לטעינת כל התרגילים במחסן
    public static void loadAllWarehouseExercises(ValueEventListener listener) {
        exercisesWerehouseRef.addListenerForSingleValueEvent(listener);
    }
}
