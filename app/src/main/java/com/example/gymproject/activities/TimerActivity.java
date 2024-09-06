package com.example.gymproject.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gymproject.services.CountdownService;
import com.example.gymproject.R;
import com.example.gymproject.utilities.MediaPlayerUtil;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button finishEarlyButton;
    private BroadcastReceiver timerReceiver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initializeViews();
        startCountdownService();
        registerTimerReceiver();

        setupFinishEarlyButton();
    }

    private void initializeViews() {
        timerTextView = findViewById(R.id.timerTextView);
        finishEarlyButton = findViewById(R.id.finishEarlyButton);
    }

    //  פונקציה להפעלת שירות הספירה לאחור
    private void startCountdownService() {
        Intent intent = new Intent(this, CountdownService.class);
        intent.putExtra("timeLeft", getIntent().getLongExtra("timeLeft", 60000));
        ContextCompat.startForegroundService(this, intent);
    }

    // פונקציה לרישום ה-BroadcastReceiver
    private void registerTimerReceiver() {
        timerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("TIMER_UPDATED".equals(intent.getAction())) {
                    long timeLeft = intent.getLongExtra("timeLeft", 0);
                    updateTimer(timeLeft);
                } else if ("TIMER_FINISHED".equals(intent.getAction())) {
                    finishRest();  // סיום זמן המנוחה
                }
            }
        };

        // רישום ה-BroadcastReceiver להאזין לשני השדרים: עדכוני זמן וסיום זמן
        IntentFilter filter = new IntentFilter();
        filter.addAction("TIMER_UPDATED");
        filter.addAction("TIMER_FINISHED");
        registerReceiver(timerReceiver, filter);
    }

    private void setupFinishEarlyButton() {
        finishEarlyButton.setOnClickListener(v -> {
            stopCountdownService();
            Intent intent = new Intent("TIMER_FINISHED");
            sendBroadcast(intent);
        });
    }

    // פונקציה לעצירת השירות
    private void stopCountdownService() {
        Intent intent = new Intent(this, CountdownService.class);
        stopService(intent);
    }

    private void updateTimer(long timeLeftInMillis) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    private void finishRest() {
        MediaPlayerUtil.playSound(TimerActivity.this, R.raw.ouchsound);
        finish(); //חזרה לתוכנית אימונים
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timerReceiver);
    }
}
