package com.example.gymproject.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.gymproject.R;
import com.example.gymproject.utilities.MediaPlayerUtil;

import java.util.Locale;

public class CountdownService extends Service {

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;  // ברירת מחדל 60 שניות
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // יצירת ערוץ התראה (Notification Channel) אם המכשיר משתמש בגרסה מעל Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "CountdownChannel",
                    "ספירה לאחור",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // קבלת זמן הספירה מה-Intent
        timeLeftInMillis = intent.getLongExtra("timeLeft", 60000);

        notificationBuilder = new NotificationCompat.Builder(this, "CountdownChannel")
                .setContentTitle("ספירה לאחור")
                .setSmallIcon(R.drawable.fitness_app_icon)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true);  // הופך את ההתראה לכזו שלא ניתן לבטל

        // הפעלת התראה בשירות Foreground
        startForeground(1, getNotification(timeLeftInMillis));

        // התחלת ספירה לאחור
        startCountDownTimer();

        return START_STICKY;
    }

    // יצירת ההתראה עם הזמן הנותר
    private Notification getNotification(long timeLeftInMillis) {
        return notificationBuilder
                .setContentText(formatTimeLeft(timeLeftInMillis))  // הצגת הזמן הנותר
                .build();
    }

    // הפעלת הטיימר
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { //עם מתחיל timeLeftInMillis ויורד בכל שנייה
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;

                // עדכון ההתראה עם הזמן הנותר
                updateNotification(timeLeftInMillis);

                // שליחת שדר ל-Activity כדי לעדכן את ה-UI
                Intent intent = new Intent("TIMER_UPDATED");
                intent.putExtra("timeLeft", timeLeftInMillis);
                sendBroadcast(intent);
            }

            public void onFinish() {
                Intent intent = new Intent("TIMER_FINISHED");
                sendBroadcast(intent);
                stopSelf();
            }
        }.start();
    }

//    // פונקציה לעדכון ההתראה עם הזמן הנותר
    private void updateNotification(long timeLeftInMillis) {
        notificationManager.notify(NOTIFICATION_ID, getNotification(timeLeftInMillis));
    }

    // פורמט לזמן הנותר (דקות:שניות)
    private String formatTimeLeft(long millis) {
        int minutes = (int) (millis / 1000) / 60;
        int seconds = (int) (millis / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
