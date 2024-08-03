package com.example.gymproject.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.gymproject.R;

public class SettingsActivity extends AppCompatActivity {
    private Button buttonToggleTheme, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        initButtons();
    }

    private void initViews() {
        buttonToggleTheme = findViewById(R.id.buttonToggleTheme);
        btnReturn=findViewById(R.id.btnReturnFromSettingsToHomePage);
    }

    private void initButtons() {
        buttonToggleTheme.setOnClickListener(v->{
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate(); // Recreate activity to apply theme change
            });

        btnReturn.setOnClickListener(v->{
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        });
    }
}