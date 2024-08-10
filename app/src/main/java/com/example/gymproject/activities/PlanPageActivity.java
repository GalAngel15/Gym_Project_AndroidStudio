package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.managers.WorkoutPlanManager;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlanPageActivity extends BaseActivity {
    private TextView textViewUsername,textViewPlanName;
    private WorkoutPlanManager planManager;
    private MaterialButton addExerciseFromLibrary, addCustomExercise, buttonOpenSettings, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_page);
        initView();
        initButtons();

        addExercise();
        if (currentUser != null) {
            String userName = currentUser.getDisplayName();
            // הצגת שם המשתמש שהתקבל מ-FirebaseAuth
            textViewUsername.setText("Welcome, " + userName + "!");
        }
        planManager = new WorkoutPlanManager(this, currentUser,planName);
        planManager.loadAllUserExercises(planName);
    }

    private void addExercise() {
        BuiltExercise[] exercises = {
                new BuiltExercise("Chest","Bench press","https://i0.wp.com/www.muscleandfitness.com/wp-content/uploads/2019/04/10-Exercises-Build-Muscle-Bench-Press.jpg?quality=86&strip=all"),
                new BuiltExercise("Back","Pull-ups","https://youfit.com/wp-content/uploads/2022/11/pull-ups-for-beginners.jpg"),
                new BuiltExercise("Legs", "Squats", "https://www.mensjournal.com/.image/t_share/MTk2MTM1ODU1MzI2NjM1MTUz/squat-barbell.jpg"),
                new BuiltExercise("Shoulders", "Shoulder press", "https://media.istockphoto.com/id/2094590241/photo/red-haired-athlete-arms-extended-performing-shoulder-press.jpg?s=612x612&w=0&k=20&c=dPhjbIE8WUvibh4ciqRcF74aAgwaOhKA7i5RohH2WNw="),
                new BuiltExercise("Biceps", "Bicep curls", "https://media.istockphoto.com/id/2025851985/photo/black-woman-in-the-gym-doing-biceps-curl-with-dumbbell.jpg?s=612x612&w=0&k=20&c=p5YY1-MLjLNZIEPb5cTKX_V7BWXHPqs1BRSHFx1PAMM="),
                new BuiltExercise("Abs", "Plank", "https://media.istockphoto.com/id/628092382/photo/its-great-for-the-abs.jpg?s=612x612&w=0&k=20&c=YOWaZRjuyh-OG6rv8k0quDNxRwqrxdMm8xgqe37Jmak="),
                new BuiltExercise("Legs", "Lunges", "https://media.istockphoto.com/id/1136449249/photo/young-man-practicing-walking-lunge-with-kettle-bell.jpg?s=612x612&w=0&k=20&c=G4Tjzf4UToRUQC4qiQKsRZJh4GzPjG8lMaK-Be5qY7k="),
                new BuiltExercise("Back", "Deadlift", "https://media.istockphoto.com/id/576578170/photo/full-length-of-confident-man-dead-lifting-barbell-in-gym.jpg?s=612x612&w=0&k=20&c=fPv5gq2Rr0dkHS4Tc1ujb4Al5yh37PjFm0uvZXaKBb4="),
                new BuiltExercise("Chest", "Incline bench press", "https://media.istockphoto.com/id/1324835296/photo/athletic-woman-doing-bench-press-with-dumbbells-in-gym.jpg?s=612x612&w=0&k=20&c=mld9_n8xOF7DJeIhKFdJvdxQQ39s0toNE3fdGHNY5HE="),
                new BuiltExercise("Triceps", "Dips", "https://media.istockphoto.com/id/1011350020/photo/one-young-man-20-years-old-side-view-gym-indoors-dip-exercise-dipping-stand-station.jpg?s=612x612&w=0&k=20&c=At7wE9EaUK0J4PcaPAeycmEx4-2M-JQ7cb3yDy1BWHo=")
        };

        for (BuiltExercise exercise : exercises) {
            DatabaseUtils.addExerciseToWarehouse(exercise);
        }

    }

    private void initView() {
        textViewUsername = findViewById(R.id.textViewWelcome);
        textViewPlanName = findViewById(R.id.textViewPlanName);
        textViewPlanName.setText(planName);
        addExerciseFromLibrary = findViewById(R.id.addExerciseFromLibrary);
        addCustomExercise = findViewById(R.id.addCustomExercise);
        btnReturn = findViewById(R.id.btnReturnFromPlanPage);
        buttonOpenSettings = findViewById(R.id.buttonOpenSettings);
    }


    private void initButtons() {
        //return button
        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyPlansActivity.class);
            startActivity(intent);
        });

        //add exercise from library button
        addExerciseFromLibrary.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddExerciseFromLibraryActivity.class);
            intent.putExtra("planId", planName);
            startActivity(intent);
        });

        //add custom exercise button
        addCustomExercise.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCustomExerciseActivity.class);
            intent.putExtra("planId", planName);
            startActivity(intent);
        });

        //settings button
        buttonOpenSettings.setOnClickListener(v->{
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
