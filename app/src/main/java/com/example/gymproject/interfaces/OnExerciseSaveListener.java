package com.example.gymproject.interfaces;

import android.widget.LinearLayout;

import com.example.gymproject.models.BuiltExercise;

public interface OnExerciseSaveListener {
    void onSaveExercise(BuiltExercise exercise, int position, LinearLayout inputDetailsLayout);
}
