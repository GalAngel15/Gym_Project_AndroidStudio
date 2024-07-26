package com.example.gymproject.managers;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
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

    public WorkoutPlanManager(Context context, FirebaseUser currentUser, RecyclerView recyclerView) {
        this.currentUser = currentUser;
        this.recyclerView = recyclerView;
        this.context = context;
        exerciseList = new ArrayList<>();
    }

    public void init(){
        this.recyclerView = ((LoginActivity) context).findViewById(R.id.recyclerView);
    }

}
