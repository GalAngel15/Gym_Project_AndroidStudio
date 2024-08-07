package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gymproject.R;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.ExercisesUtiles;
import com.google.android.material.button.MaterialButton;



public class AddCustomExerciseActivity extends BaseActivity {
    private EditText editTextExerciseName , editTextSets , editTextReps, editTextWeight, editTextRest, additionalComments;
    private Spinner typeExerciseName;
    private MaterialButton buttonAddExercise, btnReturnToHomePage;
    private String selectedMuscle, exerciseName, other;
    private int sets,reps, weight, rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_exercise);

        planName= getIntent().getStringExtra("planId");
        initViews();
        initButtons();
        setupSpinner();
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

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.muscles, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        typeExerciseName.setAdapter(adapter);
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
        buttonAddExercise.setOnClickListener(v->{
                checkValues();
                if (!checkValues()) {
                    Toast.makeText(AddCustomExerciseActivity.this, "invalid inputs", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseUtils.addCustomUserExercise(
                            currentUser.getUid(),
                            planName,
                            selectedMuscle,
                            exerciseName,
                            "",
                            sets,
                            reps,
                            weight,
                            rest,
                            other
                    );
                    Toast.makeText(AddCustomExerciseActivity.this, "התרגיל נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCustomExerciseActivity.this, PlanPageActivity.class);
                    intent.putExtra("planId", planName);
                    startActivity(intent);
                    finish();
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
        btnReturnToHomePage.setOnClickListener(v->{
                Intent intent = new Intent(AddCustomExerciseActivity.this, PlanPageActivity.class);
                intent.putExtra("planId", planName);
                startActivity(intent);
                finish();
        });
    }

}