package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.models.Exercise;
import com.google.gson.Gson;


public class AddExerciseActivity extends AppCompatActivity {
    private EditText editTextExerciseName , editTextSets , editTextReps, editTextWeight, additionalComments;
    private Spinner typeExerciseName;
    private Button buttonAddExercise;
    private String selectedMuscle, exerciseName, other;
    private int sets,reps, weight;
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
        additionalComments= findViewById(R.id.additionalComments);
        buttonAddExercise = findViewById(R.id.buttonAddExercise);
    }

    private void initButtons() {
        onMuscleSelected();
        onSaveButtonClick();
    }

    public void onMuscleSelected() {
        typeExerciseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMuscle = parent.getItemAtPosition(position).toString();
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
                    Toast.makeText(AddExerciseActivity.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                } else {
                    //save successfully
                    Exercise exercise= new Exercise(exerciseName, "", sets,reps,weight,0,other);
                    Toast.makeText(AddExerciseActivity.this, "התרגיל נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddExerciseActivity.this, HomePageActivity.class);
                    String json=gson.toJson(exercise);
                    intent.putExtra("newExercise", json);
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
        other = additionalComments.getText().toString();
        if (exerciseName.isEmpty() || setsString.isEmpty() || repsString.isEmpty() || weightString.isEmpty()) {
            return false;
        }
        sets=Integer.parseInt(setsString);
        reps=Integer.parseInt(repsString);
        weight=Integer.parseInt(weightString);
        return true;
    }

}
