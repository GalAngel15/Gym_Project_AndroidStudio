package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.gymproject.R;
import com.example.gymproject.managers.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {
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
        isNightMode=sharedPreferencesManager.isNightMode();
        switchMode.setChecked(isNightMode);
        switchMode.setOnClickListener(v->{
            if(isNightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                sharedPreferencesManager.setNightMode(false);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                sharedPreferencesManager.setNightMode(true);
            }
            isNightMode = !isNightMode;
        });
    }



    private void initViews() {
        switchMode = findViewById(R.id.switchToggleTheme);
        switchMode.setChecked(isNightMode);
        btnReturn=findViewById(R.id.btnReturnFromSettingsToHomePage);
    }


    private void initButtons() {

        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        });
    }
}
