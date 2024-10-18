package com.example.gymproject.managers;

import android.app.Activity;
import android.content.Intent;

import com.example.gymproject.activities.TimerActivity;
import com.example.gymproject.models.CustomExercise;

public class SetsManager {
    private int setCount = 0;
    private final OnRestFinishListener onRestFinishListener;
    private final Activity activity;
    private CustomExercise currentExercise;

    public SetsManager(OnRestFinishListener listener, Activity activity) {
        this.onRestFinishListener = listener;
        this.activity = activity;
    }

    public void startNewSet(CustomExercise exercise, int position) {
        if (currentExercise == null || !isSameExercise(exercise)) {
            setCount = 0;
            currentExercise = exercise; // עדכון התרגיל הנוכחי
        }

        setCount++;
        if (onRestFinishListener != null) {
            onRestFinishListener.onSetCompleted(exercise, position);
        }

        // פתיחת המסך עם הטיימר
        Intent intent = new Intent(activity, TimerActivity.class);
        intent.putExtra("timeLeft", (long)exercise.getRest()*1000);
        activity.startActivityForResult(intent, 1);
    }
    private boolean isSameExercise(CustomExercise newExercise) {
        return newExercise.getName().equals(currentExercise.getName());
    }

    public interface OnRestFinishListener {
        void onSetCompleted(CustomExercise customExercise, int position);
        //void onTick(long millisUntilFinished);
        void onRestFinished();
    }
}
