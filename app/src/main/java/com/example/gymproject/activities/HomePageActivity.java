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
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePageActivity extends AppCompatActivity {
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView textViewUsername;
    private WorkoutPlanManager planManager;
    private FirebaseUser currentUser;
    private MaterialButton addExerciseFromLibrary, addCustomExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initView();
        initButtons();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        addExercise();
        if (currentUser != null) {
            String userName = currentUser.getDisplayName();
            // הצגת שם המשתמש שהתקבל מ-FirebaseAuth
            textViewUsername.setText("Welcome, " + userName + "!");
        }
        planManager = new WorkoutPlanManager(this, currentUser);
    }

    private void addExercise() {
        BuiltExercise exercise1=new BuiltExercise("chest","bench press","https://i0.wp.com/www.muscleandfitness.com/wp-content/uploads/2019/04/10-Exercises-Build-Muscle-Bench-Press.jpg?quality=86&strip=all");
        BuiltExercise exercise2=new BuiltExercise("back","pull-ups","https://youfit.com/wp-content/uploads/2022/11/pull-ups-for-beginners.jpg");
        DatabaseUtils.addExerciseToWarehouse2("E1",exercise1);
        DatabaseUtils.addExerciseToWarehouse2("E2",exercise2);
    }

    private void initView() {
        textViewUsername = findViewById(R.id.textViewWelcome);
        addExerciseFromLibrary = findViewById(R.id.addExerciseFromLibrary);
        addCustomExercise = findViewById(R.id.addCustomExercise);
        btnLogout = findViewById(R.id.btnLogout);
    }


    private void initButtons() {
        //logout button
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(HomePageActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        //add exercise from library button
        addExerciseFromLibrary.setOnClickListener(v -> {

            Intent intent = new Intent(HomePageActivity.this, AddExerciseFromLibraryActivity.class);
            startActivity(intent);
        });

        //add custom exercise button
        addCustomExercise.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, AddExerciseActivity.class);
            startActivity(intent);
        });
    }
}
