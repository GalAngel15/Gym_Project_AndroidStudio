package com.example.gymproject.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymproject.R;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private TextView timerTextView;
    private Button finishEarlyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initViews();
        initOnBackPressedHandler();

        // קבלת הזמן שנותר
        timeLeftInMillis = getIntent().getLongExtra("timeLeft", 0);
        startTimer();
    }

    private void initViews() {
        timerTextView = findViewById(R.id.timerTextView);
        finishEarlyButton = findViewById(R.id.finishEarlyButton);
        finishEarlyButton.setOnClickListener(v -> finishEarly());
    }

    private void initOnBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishEarly();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                setResult(RESULT_OK);
                finish();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    private void finishEarly() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // עצירת הטיימר בעת הפסקה
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // עצירת פעולות זמניות
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // שחרור הטיימר אם נדרש
        }
    }
}
