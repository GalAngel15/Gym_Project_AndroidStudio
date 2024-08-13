package com.example.gymproject.managers;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;

import com.example.gymproject.activities.TimerActivity;
import com.example.gymproject.models.CustomExercise;

public class SetsManager {
    private int setCount = 0;
    private OnRestFinishListener onRestFinishListener;
    private Activity activity;

    public SetsManager(OnRestFinishListener listener, Activity activity) {
        this.onRestFinishListener = listener;
        this.activity = activity;
    }

    public void startNewSet(CustomExercise customExercise) {
        setCount++;
        if (onRestFinishListener != null) {
            onRestFinishListener.onSetCompleted(setCount, customExercise);
        }

        // פתיחת המסך עם הטיימר
        Intent intent = new Intent(activity, TimerActivity.class);
        intent.putExtra("timeLeft", (long)customExercise.getRest()*1000);
        activity.startActivityForResult(intent, 1);
    }

    public interface OnRestFinishListener {
        void onSetCompleted(int setCount, CustomExercise customExercise);
        void onTick(long millisUntilFinished);
        void onRestFinished();
    }
}
