package com.example.gymproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;

public class AddExerciseActivity extends AppCompatActivity {
    private EditText editTextExerciseName;
    private EditText editTextSets;
    private EditText editTextReps;
    private EditText editTextWeight;
    private Button buttonAddExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_exercise);

        editTextExerciseName = findViewById(R.id.editTextExerciseName);
        editTextSets = findViewById(R.id.editTextSets);
        editTextReps = findViewById(R.id.editTextReps);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonAddExercise = findViewById(R.id.buttonAddExercise);

        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseName = editTextExerciseName.getText().toString();
                String sets = editTextSets.getText().toString();
                String reps = editTextReps.getText().toString();
                String weight = editTextWeight.getText().toString();

                if (exerciseName.isEmpty() || sets.isEmpty() || reps.isEmpty() || weight.isEmpty()) {
                    Toast.makeText(AddExerciseActivity.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                } else {
                    // שמירת התרגיל (לדוגמה, הוספה לרשימה, שמירה במסד נתונים וכו')
                    Toast.makeText(AddExerciseActivity.this, "התרגיל נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
