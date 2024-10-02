package com.example.gymproject.interfaces;

import com.example.gymproject.models.ProgressEntry;

import java.util.ArrayList;

public interface OnProgressImagesLoadedListener {
    void onProgressImagesLoaded(ArrayList<ProgressEntry> progressEntries);
}
