package com.example.gymproject.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gymproject.R;
import com.example.gymproject.utilities.FullScreenManager;

public class VisitPageActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_page);

        FullScreenManager.getInstance().fullScreen(getWindow());
        // בקשת הרשאות להצגת התראות
        checkAndRequestNotificationPermission();
    }

    // פונקציה ראשית לבדיקת הרשאה ולבקשתה אם יש צורך
    private void checkAndRequestNotificationPermission() {
        // בקשת הרשאה רק אם מדובר ב-Android 13 ומעלה
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (isNotificationPermissionGranted()) {
                // אם יש כבר הרשאה, הצג התראה
                showNotification();
            } else {
                // אם אין הרשאה, בקש מהמשתמש
                requestNotificationPermission();
            }
        } else {
            // בגרסאות מתחת ל-Android 13 אין צורך בהרשאה, הצג התראה
            showNotification();
        }
    }

    // פונקציה לבדיקת הרשאה
    private boolean isNotificationPermissionGranted() {
        // בדיקת ההרשאה בפועל
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    // פונקציה לבקשת הרשאה
    private void requestNotificationPermission() {
        // בקשה להרשאה רק אם לא ניתנה
        if (!isNotificationPermissionGranted()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    NOTIFICATION_PERMISSION_REQUEST_CODE);
        }
    }

    // טיפול בתוצאות הבקשה
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // המשתמש אישר את ההרשאה, הצג התראה
                showNotification();
            } else {
                // המשתמש דחה את ההרשאה
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // פונקציה לדוגמה להצגת התראה
    private void showNotification() {
        // כאן תוכל להוסיף את קוד הצגת ההתראה
        Toast.makeText(this, "Notification will be shown here", Toast.LENGTH_SHORT).show();
    }

    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void navigateToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
