package com.example.gymproject.utilities;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.gymproject.managers.SharedPreferencesManager;

public class ThemeUtil {

    public static void applyTheme(Context context) {
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        boolean isNightMode = sharedPreferencesManager.isNightMode();
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
