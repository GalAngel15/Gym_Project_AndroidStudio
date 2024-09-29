package com.example.gymproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.models.ProgressEntry;

import java.util.ArrayList;

public class ProgressEntryAdapter extends RecyclerView.Adapter<ProgressEntryAdapter.EntryViewHolder> {

    private final ArrayList<ProgressEntry> progressEntries;
    private Context context;
    private String userId;

    public ProgressEntryAdapter(ArrayList<ProgressEntry> progressEntries, Context context, String userId) {
        this.progressEntries = progressEntries;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_entry, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        ProgressEntry entry = progressEntries.get(position);

        // הצגת התאריך
        holder.textViewDate.setText(entry.getYearMonth());

        // הצגת התמונות של אותו תאריך
        ProgressImageAdapter imageAdapter = new ProgressImageAdapter(entry.getImagesUri(), context, userId);
        holder.recyclerViewImages.setAdapter(imageAdapter);
    }

    @Override
    public int getItemCount() {
        return progressEntries.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        RecyclerView recyclerViewImages;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate); // תאריך
            recyclerViewImages = itemView.findViewById(R.id.recyclerViewImages); // RecyclerView פנימי לתמונות
            recyclerViewImages.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}

