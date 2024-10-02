package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.gymproject.R;
import com.example.gymproject.managers.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends BaseActivity {
    private MaterialButton btnReturn;
    private SwitchCompat switchMode;
    private SharedPreferencesManager sharedPreferencesManager;
    private boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        setContentView(R.layout.activity_settings);

        initViews();
        initButtons();
        setupSwitchMode();
    }

    private void initViews() {
        switchMode = findViewById(R.id.switchToggleTheme);
        btnReturn=findViewById(R.id.btnReturnFromSettings);
    }

    private void initButtons() {
        btnReturn.setOnClickListener(v -> navigateBackToPlans());
    }

    private void setupSwitchMode() {
        isNightMode = sharedPreferencesManager.isNightMode();
        switchMode.setChecked(isNightMode);
        switchMode.setOnClickListener(v -> toggleNightMode());
    }

    private void toggleNightMode() {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sharedPreferencesManager.setNightMode(false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sharedPreferencesManager.setNightMode(true);
        }
        isNightMode = !isNightMode;
    }

    private void navigateBackToPlans() {
        Intent intent = new Intent(this, MyPlansActivity.class);
        startActivity(intent);
    }
}
