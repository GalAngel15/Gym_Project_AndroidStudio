package com.example.gymproject.interfaces;

import com.example.gymproject.models.BuiltExercise;

public interface OnExerciseLoadedListener {
    void onExerciseLoaded(BuiltExercise builtExercise);
    void onFailure(Exception e);
}
