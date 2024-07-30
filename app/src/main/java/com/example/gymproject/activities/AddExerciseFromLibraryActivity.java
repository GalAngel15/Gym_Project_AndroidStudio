package com.example.gymproject.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.BuiltExerciseAdapter;
import com.example.gymproject.adapters.CustomExerciseAdapter;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.CustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.ImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddExerciseFromLibraryActivity extends BaseActivity {
    private Button buttonAddExercise;
    private String selectedMuscle, exerciseName, other;
    private TextView textViewExerciseName;
    private ImageView imageViewExercise;
    private RecyclerView recyclerView;
    private BuiltExerciseAdapter adapter;
    private List<BuiltExercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_exercises);
        exerciseList = new ArrayList<>();
        initViews();
        //initButtons();
        ImageLoader.init(this);
    }

    private void initViews() {
        textViewExerciseName= findViewById(R.id.textViewExerciseName);
        imageViewExercise= findViewById(R.id.imageViewExercise);
        buttonAddExercise = findViewById(R.id.btnAddBuiltExercise);

        recyclerView=findViewById(R.id.ourExercisesRecyclerView);
        adapter = new BuiltExerciseAdapter(this, exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DatabaseUtils.loadAllWarehouseExercises(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exerciseList.clear();
                if (!dataSnapshot.exists()) {
                    Log.e("Exercise", "snapshot don't exists");
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BuiltExercise exercise = snapshot.getValue(BuiltExercise.class);
                    if (exercise != null) {
                        Log.e("Exercise", "name" + exercise.getName() +", imageUrl: " + exercise.getImageUrl() + ", mainMuscle: " + exercise.getMainMuscle() + "\n");
                        exerciseList.add(exercise);
                    } else {
                        Log.e("Exercise", "Exercise is null");
                    }
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Exercise", "Database error: " + error.getMessage());
            }
        });
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
