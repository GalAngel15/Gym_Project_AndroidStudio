package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddExerciseFromLibraryActivity extends BaseActivity{
    private RecyclerView recyclerView;
    private BuiltExerciseAdapter adapter;
    private List<BuiltExercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_exercises);

        exerciseList = new ArrayList<>();
        initRecyclerView();
        initButtons();
        loadExercises();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.ourExercisesRecyclerView);
        adapter = new BuiltExerciseAdapter(this, exerciseList);
        adapter.setOnExerciseSaveListener(this::onSaveExercise);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initButtons() {
        findViewById(R.id.btnFinish).setOnClickListener(v->{
            Intent intent = new Intent(AddExerciseFromLibraryActivity.this, PlanPageActivity.class);
            intent.putExtra("planId", planName);
            startActivity(intent);
            finish();
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
                        exercise.setName(snapshot.getKey());
                        exerciseList.add(exercise);
                    } else {
                        Log.e("Exercise", "Exercise is null");
                    }
                }
                adapter.notifyItemRangeInserted(0, exerciseList.size());            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Exercise", "Database error: " + error.getMessage());
            }
        });
    }

    public void onSaveExercise(BuiltExercise exercise, int position, LinearLayout inputDetailsLayout) {
        // מציאת הפריט המתאים ב-RecyclerView על פי המיקום (position)
        View itemView = recyclerView.getLayoutManager().findViewByPosition(position);
        if (itemView != null) {
            String sets = getInputText(itemView, R.id.inputSets);
            String reps = getInputText(itemView, R.id.inputReps);
            String weight = getInputText(itemView, R.id.inputWeight);
            String rest = getInputText(itemView, R.id.inputRest);
            String notes = getInputText(itemView, R.id.inputNotes);

            if (!ExercisesUtiles.checkValuesForExercise(exercise.getMainMuscle(), exercise.getName(), sets, reps, weight, rest)) {
                Toast.makeText(this, "Invalid fields", Toast.LENGTH_SHORT).show();
                return;
            }

            PartialCustomExercise partialCustomExercise = new PartialCustomExercise( Integer.parseInt(sets), Integer.parseInt(reps), Double.parseDouble(weight), Integer.parseInt(rest), notes);
            DatabaseUtils.saveCustomUserExerciseFromLibrary(currentUser.getUid(), planName, partialCustomExercise,exercise.getName(), new OnExerciseSavedListener() {
                @Override
                public void onSuccess() {
                    inputDetailsLayout.setVisibility(View.GONE);
                    Toast.makeText(AddExerciseFromLibraryActivity.this, "Exercise saved successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(AddExerciseFromLibraryActivity.this, "Failed to save exercise", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getInputText(View parent, int resId) {
        EditText editText = parent.findViewById(resId);
        return editText != null ? editText.getText().toString() : "";
    }

}
