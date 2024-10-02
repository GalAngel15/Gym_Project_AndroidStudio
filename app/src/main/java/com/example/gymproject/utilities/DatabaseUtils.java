package com.example.gymproject.utilities;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gymproject.interfaces.OnExerciseSavedListener;
import com.example.gymproject.interfaces.OnExerciseLoadedListener;
import com.example.gymproject.interfaces.OnProgressImagesLoadedListener;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.models.PartialCustomExercise;
import com.example.gymproject.models.ProgressEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtils {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference exercisesWarehouseRef = database.getReference("exercisesWarehouse");
    private static final DatabaseReference userWorkoutPlansRef = database.getReference("userWorkoutPlans");
    private static final DatabaseReference usersRef = database.getReference("users");


    public static void addExerciseToWarehouse(BuiltExercise exercise) {
        Map<String, Object> exerciseData = new HashMap<>();
        exerciseData.put("mainMuscle", exercise.getMainMuscle());
        exerciseData.put("name", exercise.getName());
        exerciseData.put("imageUrl", exercise.getImageUrl());
        exercisesWarehouseRef.child(exercise.getName()).setValue(exerciseData);
    }

    public static void addCustomUserExercise(String userId, String workoutPlanId, CustomExercise exercise) {
        DatabaseReference customExercisesRef = userWorkoutPlansRef.child(userId).child(workoutPlanId).child("customExercises");
        customExercisesRef.child(exercise.getName()).setValue(exercise);
    }

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
            if (listener != null) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(task.getException());
                }
            }
        });
    }

    public static void loadAllPlans(String userId, ValueEventListener listener) {
        userWorkoutPlansRef.child(userId).addListenerForSingleValueEvent(listener);
    }

    public static void addPlan(String userId, String planName, String planDescription) {
        DatabaseReference plansRef = userWorkoutPlansRef.child(userId).child(planName);
        plansRef.child("description").setValue(planDescription);
    }

    public static void updateCustomExerciseInFirebase(String userId, String planId, CustomExercise exercise, OnCompleteListener<Void> listener) {
        DatabaseReference warehouseRef = userWorkoutPlansRef.child(userId).child(planId).child("WarehouseExercises").child(exercise.getName());
        DatabaseReference customRef = userWorkoutPlansRef.child(userId).child(planId).child("customExercises").child(exercise.getName());

        warehouseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    warehouseRef.setValue(
                            new PartialCustomExercise(exercise.getSets(), exercise.getReps(), exercise.getWeight(), exercise.getRest(), exercise.getOther()))
                            .addOnCompleteListener(listener);
                } else {
                    customRef.setValue(exercise).addOnCompleteListener(listener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseUtils", "Error updating exercise in Firebase: " + error.getMessage());
            }
        });
    }
    public static void deleteExerciseFromFirebase(String userId, String planId, CustomExercise exercise, OnCompleteListener<Void> listener) {
        DatabaseReference warehouseRef = userWorkoutPlansRef.child(userId).child(planId).child("WarehouseExercises").child(exercise.getName());
        DatabaseReference customRef = userWorkoutPlansRef.child(userId).child(planId).child("customExercises").child(exercise.getName());

        warehouseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    warehouseRef.removeValue().addOnCompleteListener(listener);
                } else {
                    customRef.removeValue().addOnCompleteListener(listener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseUtils", "Error updating exercise in Firebase: " + error.getMessage());
            }
        });
    }

    public static void deletePlanFromFirebase(String userId, String planId, OnCompleteListener<Void> listener) {
        DatabaseReference planRef = userWorkoutPlansRef.child(userId).child(planId);

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    planRef.removeValue().addOnCompleteListener(listener);
                } else {
                    Log.e("DatabaseUtils", "Plan with ID " + planId + " does not exist in Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseUtils", "Error updating exercise in Firebase: " + error.getMessage());
            }
        });
    }


    public static void loadProgressImages(String userId, ArrayList<ProgressEntry> progressEntries, OnProgressImagesLoadedListener listener) {
        DatabaseReference progressImagesRef = usersRef.child(userId).child("bodyProgress");

        progressImagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot yearSnapshot : dataSnapshot.getChildren()) {
                    String year = yearSnapshot.getKey();

                    for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                        String month = monthSnapshot.getKey();
                        ArrayList<Uri> imagesForMonth = new ArrayList<>();

                        for (DataSnapshot imageSnapshot : monthSnapshot.getChildren()) {
                            String imageUrl = imageSnapshot.getValue(String.class);
                            Uri imageUri = Uri.parse(imageUrl);
                            imagesForMonth.add(imageUri);
                        }
                        // הוספת התמונות לפי החודש לרשימה
                        progressEntries.add(new ProgressEntry(year + "/" + month, imagesForMonth));
                    }
                }
                listener.onProgressImagesLoaded(progressEntries);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to load data: " + databaseError.getMessage());
            }
        });
    }

    public static void uploadBodyImages(String imageUrl, String userId, String currentYear, String currentMonth, String finalImgId) {
        DatabaseReference progressImagesRef = usersRef.child(userId).child("bodyProgress")
                .child(currentYear).child(currentMonth);
        progressImagesRef.child(finalImgId).setValue(imageUrl)
                .addOnSuccessListener(aVoid -> Log.e("FirebaseDatabase", "URL saved successfully"))
                .addOnFailureListener(e -> Log.e("FirebaseDatabase", "Failed to save URL: " + e.getMessage()));

    }
}
