package com.example.gymproject.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.interfaces.OnExerciseSaveListener;
import com.example.gymproject.models.BuiltExercise;
import com.example.gymproject.utilities.ImageLoader;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BuiltExerciseAdapter extends RecyclerView.Adapter<BuiltExerciseAdapter.BuiltExerciseViewHolder> {

    private Context context;
    private List<BuiltExercise> exerciseList;
    private OnExerciseSaveListener onExerciseSaveListener;

    public BuiltExerciseAdapter(Context context, List<BuiltExercise> exerciseList, OnExerciseSaveListener onExerciseSaveListener) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.onExerciseSaveListener = onExerciseSaveListener;
    }

    @NonNull
    @Override
    public BuiltExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_built_exercise, parent, false);
        return new BuiltExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuiltExerciseViewHolder holder, int position) {
        BuiltExercise exercise = exerciseList.get(position);
        String imageUrl = exercise.getImageUrl();
        holder.textViewExerciseName.setText(exercise.getName());

        ImageLoader.getInstance().load(imageUrl, holder.imageViewExercise);

        // הגדרת הפעולה בלחיצה על הכפתור
        if (holder.btnShowInputFields == null) {
            Log.e("BuiltExerciseAdapter", "btnAdd is null at position: " + position);
        } else {
            holder.btnShowInputFields.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.inputDetailsLayout.getVisibility() == View.VISIBLE){
                        holder.inputDetailsLayout.setVisibility(View.GONE);
                    }else{
                        holder.inputDetailsLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        holder.btnAddBuiltExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onExerciseSaveListener.onSaveExercise(exerciseList.get(adapterPosition), adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class BuiltExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewExercise;
        TextView textViewExerciseName;
        ImageButton btnShowInputFields;
        MaterialButton btnAddBuiltExercise;
        LinearLayout inputDetailsLayout;


        public BuiltExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExercise = itemView.findViewById(R.id.imageViewExercise);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            btnShowInputFields = itemView.findViewById(R.id.btnShowInputFields);
            inputDetailsLayout = itemView.findViewById(R.id.inputDetailsLayout);
            btnAddBuiltExercise = itemView.findViewById(R.id.btnAddBuiltExercise);
        }
    }
}
