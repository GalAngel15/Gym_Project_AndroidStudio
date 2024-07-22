package com.example.gymproject.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ExerciseAdapter;
import com.example.gymproject.models.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private Button btnOtherFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize exercise list and adapter
        exerciseList = new ArrayList<>();

        exerciseList.add(new Exercise("תרגיל 1", "url_to_image", 3, 12, 20, 60,""));
        exerciseList.add(new Exercise("תרגיל 2", "url_to_image", 4, 10, 25, 90,""));
        TextView textViewUsername = findViewById(R.id.textViewWelcome);

        adapter = new ExerciseAdapter(this, exerciseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userName = currentUser.getDisplayName();
            // הצגת שם המשתמש שהתקבל מ-FirebaseAuth
            textViewUsername.setText("Welcome, " + userName + "!");
        }
    }
}
