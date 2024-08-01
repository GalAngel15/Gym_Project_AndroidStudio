package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.BuiltExerciseAdapter;
import com.example.gymproject.interfaces.OnExerciseSavedListener;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.models.PartialCustomExercise;
import com.example.gymproject.utilities.DatabaseUtils;
import com.example.gymproject.utilities.ExercisesUtiles;
import com.example.gymproject.utilities.ImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddExerciseFromLibraryActivity extends BaseActivity{
    private Button btnFinish;
    private String selectedMuscle, exerciseName, other;
    private TextView textViewExerciseName;
    private ImageView imageViewExercise;
    private RecyclerView recyclerView;
    private BuiltExerciseAdapter adapter;
    private List<BuiltExercise> exerciseList;
    private EditText inputSets, inputReps, inputWeight, inputRest, inputNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_exercises);
        exerciseList = new ArrayList<>();
        initViews();
        initButtons();
        ImageLoader.init(this);
        loadExercises();
    }

    private void initViews() {
        inputSets = findViewById(R.id.inputSets);
        inputReps = findViewById(R.id.inputReps);
        inputWeight = findViewById(R.id.inputWeight);
        inputRest = findViewById(R.id.inputRest);
        inputNotes = findViewById(R.id.inputNotes);
        btnFinish= findViewById(R.id.btnFinish);

        textViewExerciseName= findViewById(R.id.textViewExerciseName);
        imageViewExercise= findViewById(R.id.imageViewExercise);
        exerciseList = new ArrayList<>();
        recyclerView=findViewById(R.id.ourExercisesRecyclerView);
        adapter = new BuiltExerciseAdapter(this, exerciseList);
        adapter.setOnExerciseSaveListener(this::onSaveExercise); //init callback
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initButtons() {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExerciseFromLibraryActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadExercises() {
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
                        exercise.setId(snapshot.getKey());
                        Log.e("ExerciseKey", "key" + snapshot.getKey());
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

    public void onSaveExercise(BuiltExercise exercise, int position) {
        // מציאת הפריט המתאים ב-RecyclerView על פי המיקום (position)
        View itemView = recyclerView.getLayoutManager().findViewByPosition(position);
        if (itemView != null) {
            inputSets = itemView.findViewById(R.id.inputSets);
            inputReps = itemView.findViewById(R.id.inputReps);
            inputWeight = itemView.findViewById(R.id.inputWeight);
            inputRest = itemView.findViewById(R.id.inputRest);
            inputNotes = itemView.findViewById(R.id.inputNotes);

            String sets = inputSets.getText().toString();
            String reps = inputReps.getText().toString();
            String weight = inputWeight.getText().toString();
            String rest = inputRest.getText().toString();
            String notes = inputNotes.getText().toString();

            if (!ExercisesUtiles.checkValuesForExercise(exercise.getMainMuscle(), exercise.getName(), sets, reps, weight, rest)) {
                Toast.makeText(AddExerciseFromLibraryActivity.this, "Invalid fields", Toast.LENGTH_SHORT).show();
                return;
            }

            PartialCustomExercise partialCustomExercise = new PartialCustomExercise( Integer.parseInt(sets), Integer.parseInt(reps), Integer.parseInt(weight), Integer.parseInt(rest), notes);
            DatabaseUtils.saveCustomUserExerciseFromLibrary(currentUser.getUid(), "1", partialCustomExercise,exercise.getId(), new OnExerciseSavedListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AddExerciseFromLibraryActivity.this, "Exercise saved successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(AddExerciseFromLibraryActivity.this, "Failed to save exercise", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
