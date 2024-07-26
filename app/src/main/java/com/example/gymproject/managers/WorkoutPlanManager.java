package com.example.gymproject.managers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.activities.HomePageActivity;
import com.example.gymproject.activities.LoginActivity;
import com.example.gymproject.adapters.ExerciseAdapter;
import com.example.gymproject.models.Exercise;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanManager {
    FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private Context context;

    public WorkoutPlanManager(Context context, FirebaseUser currentUser) {
        this.currentUser = currentUser;
        this.context = context;
        exerciseList = new ArrayList<>();
        init();
    }

    public void init(){
        this.recyclerView = ((HomePageActivity) context).findViewById(R.id.recyclerView);
        initExerciseList();
        adapter = new ExerciseAdapter(context, exerciseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    private void initExerciseList() {
        exerciseList.add(new Exercise("תרגיל 1", "url_to_image", 3, 12, 20, 60,""));
        exerciseList.add(new Exercise("תרגיל 2", "url_to_image", 4, 10, 25, 90,""));

    }

}
