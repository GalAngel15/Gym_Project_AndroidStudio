package com.example.gymproject.interfaces;

import com.example.gymproject.models.BuiltExercise;

public interface OnExerciseSaveListener {
    void onSaveExercise(BuiltExercise exercise, int position);
}
