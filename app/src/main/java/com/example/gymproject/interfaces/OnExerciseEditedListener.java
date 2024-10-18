package com.example.gymproject.interfaces;

import com.example.gymproject.models.CustomExercise;

public interface OnExerciseEditedListener {
    void onExerciseEdited(CustomExercise editedExercise, int position);
}
