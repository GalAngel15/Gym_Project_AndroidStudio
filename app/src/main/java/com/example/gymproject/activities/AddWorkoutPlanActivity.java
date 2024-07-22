package com.example.gymproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutPlanActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBodyParts;
    private Button buttonSaveWorkoutPlan;
    private List<String> bodyParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout_plan);

//        recyclerViewBodyParts = findViewById(R.id.recyclerViewBodyParts);
//        buttonSaveWorkoutPlan = findViewById(R.id.buttonSaveWorkoutPlan);

        // יצירת רשימה מדומה של חלקי גוף
        bodyParts = new ArrayList<>();
        bodyParts.add("גב");
        bodyParts.add("חזה");
//        bodyParts.add(new BodyPart("רגליים"));
//        bodyParts.add(new BodyPart("ידיים"));
//        bodyParts.add(new BodyPart("כתפיים"));

//        BodyPartAdapter adapter = new BodyPartAdapter(this, bodyParts);
//        recyclerViewBodyParts.setAdapter(adapter);
//        recyclerViewBodyParts.setLayoutManager(new LinearLayoutManager(this));

//        buttonSaveWorkoutPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveWorkoutPlan();
//            }
//        });
    }

//    private void saveWorkoutPlan() {
//        for (BodyPart bodyPart : bodyParts) {
//            for (Exercise exercise : bodyPart.getExercises()) {
//                // לוגיקה לשמירת התרגילים
//                // לדוגמה: ניתן לשמור במסד נתונים או לשלוח לשרת
//                Toast.makeText(this, "תוכנית האימונים נשמרה", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
