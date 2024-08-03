package com.example.gymproject.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFERENCES_NAME = "Mode";
    private static final String NIGHT_MODE_KEY = "night_mode";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setNightMode(boolean isNightMode) {
        editor.putBoolean(NIGHT_MODE_KEY, isNightMode);
        editor.apply();
    }

    public boolean isNightMode() {
        return sharedPreferences.getBoolean(NIGHT_MODE_KEY, false);
    }
}
