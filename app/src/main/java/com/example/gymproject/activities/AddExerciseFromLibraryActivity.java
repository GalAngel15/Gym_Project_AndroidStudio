package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymproject.R;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.gson.Gson;


public class AddExerciseFromLibraryActivity extends BaseActivity {
    private Button buttonAddExercise;
    private String selectedMuscle, exerciseName, other;
    private TextView textViewExerciseName;
    private ImageView imageViewExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_exercises);
        initViews();
        initButtons();
    }

    private void initViews() {
        textViewExerciseName= findViewById(R.id.textViewExerciseName);
        imageViewExercise= findViewById(R.id.imageViewExercise);
        buttonAddExercise = findViewById(R.id.btnAddBuiltExercise);
    }

    private void initButtons() {
        onSaveButtonClick();
    }


    public void onSaveButtonClick() {
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }
}
