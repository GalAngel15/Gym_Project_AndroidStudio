package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.managers.WorkoutPlanManager;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlanPageActivity extends AppCompatActivity {
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView textViewUsername;
    private WorkoutPlanManager planManager;
    private FirebaseUser currentUser;
    private MaterialButton addExerciseFromLibrary, addCustomExercise, buttonOpenSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_page);

        initView();
        initButtons();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String planId = getIntent().getStringExtra("planId");
        //addExercise();
        if (currentUser != null) {
            String userName = currentUser.getDisplayName();
            // הצגת שם המשתמש שהתקבל מ-FirebaseAuth
            textViewUsername.setText("Welcome, " + userName + "!");
        }
        planManager = new WorkoutPlanManager(this, currentUser);
        planManager.loadAllUserExercises(planId);
    }

    private void addExercise() {
        BuiltExercise exercise1=new BuiltExercise("E1","chest","bench press","https://i0.wp.com/www.muscleandfitness.com/wp-content/uploads/2019/04/10-Exercises-Build-Muscle-Bench-Press.jpg?quality=86&strip=all");
        BuiltExercise exercise2=new BuiltExercise("E2","back","pull-ups","https://youfit.com/wp-content/uploads/2022/11/pull-ups-for-beginners.jpg");
        DatabaseUtils.addExerciseToWarehouse(exercise1);
        DatabaseUtils.addExerciseToWarehouse(exercise2);
    }

    private void initView() {
        textViewUsername = findViewById(R.id.textViewWelcome);
        addExerciseFromLibrary = findViewById(R.id.addExerciseFromLibrary);
        addCustomExercise = findViewById(R.id.addCustomExercise);
        btnLogout = findViewById(R.id.btnLogout);
        buttonOpenSettings = findViewById(R.id.buttonOpenSettings);
    }


    private void initButtons() {
        //logout button
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(PlanPageActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PlanPageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        //add exercise from library button
        addExerciseFromLibrary.setOnClickListener(v -> {

            Intent intent = new Intent(PlanPageActivity.this, AddExerciseFromLibraryActivity.class);
            startActivity(intent);
        });

        //add custom exercise button
        addCustomExercise.setOnClickListener(v -> {
            Intent intent = new Intent(PlanPageActivity.this, CustomExerciseActivity.class);
            startActivity(intent);
        });

        buttonOpenSettings.setOnClickListener(v->{
            Intent intent = new Intent(PlanPageActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
