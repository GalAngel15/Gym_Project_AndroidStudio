package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gymproject.R;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.ExercisesUtiles;
import com.google.gson.Gson;


public class CustomExerciseActivity extends BaseActivity {
    private EditText editTextExerciseName , editTextSets , editTextReps, editTextWeight, editTextRest, additionalComments;
    private Spinner typeExerciseName;
    private Button buttonAddExercise, btnReturnToHomePage;
    private String selectedMuscle, exerciseName, other;
    private int sets,reps, weight, rest;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_exercise);

        gson= new Gson();
        initViews();
        initButtons();
    }

    private void initViews() {
        typeExerciseName = findViewById(R.id.typeExerciseName);
        editTextExerciseName = findViewById(R.id.editTextExerciseName);
        editTextSets = findViewById(R.id.editTextSets);
        editTextReps = findViewById(R.id.editTextReps);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextRest= findViewById(R.id.editTextRest);
        additionalComments= findViewById(R.id.additionalComments);
        buttonAddExercise = findViewById(R.id.buttonAddExercise);
        btnReturnToHomePage = findViewById(R.id.btnReturnToHomePage);
    }

    private void initButtons() {
        onMuscleSelected();
        onSaveButtonClick();
        onReturnClicked();
    }

    public void onMuscleSelected() {
        typeExerciseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedMuscle = null;
                    Toast.makeText(getApplicationContext(), "בחר שריר מרכזי", Toast.LENGTH_SHORT).show();
                } else {
                    selectedMuscle = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMuscle = null;
            }
        });
    }

    public void onSaveButtonClick() {
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValues();
                if (!checkValues()) {
                    Toast.makeText(CustomExerciseActivity.this, "invalid inputs", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseUtils.addCustomUserExercise(
                            currentUser.getUid(),
                            "1",
                            selectedMuscle,
                            exerciseName,
                            "custom_url_to_image",
                            sets,
                            reps,
                            weight,
                            rest,
                            other
                    );
                    Toast.makeText(CustomExerciseActivity.this, "התרגיל נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomExerciseActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkValues() {
        exerciseName = editTextExerciseName.getText().toString();
        String setsString = editTextSets.getText().toString();
        String repsString = editTextReps.getText().toString();
        String weightString = editTextWeight.getText().toString();
        String restString= editTextRest.getText().toString();
        other = additionalComments.getText().toString();
        if (!ExercisesUtiles.checkValuesForExercise(selectedMuscle, exerciseName, setsString, repsString, weightString, restString)) {
            return false;
        }
        sets=Integer.parseInt(setsString);
        reps=Integer.parseInt(repsString);
        weight=Integer.parseInt(weightString);
        rest=Integer.parseInt(restString);
        return true;
    }
    public void onReturnClicked() {
        btnReturnToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomExerciseActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}