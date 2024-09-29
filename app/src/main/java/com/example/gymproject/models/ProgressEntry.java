package com.example.gymproject.models;

import android.net.Uri;

import java.util.ArrayList;

public class ProgressEntry {
    private String yearMonth; // לדוגמה "2024/09"
    private ArrayList<Uri> imagesUri;

    public ProgressEntry(String yearMonth, ArrayList<Uri> imagesUri) {
        this.yearMonth = yearMonth;
        this.imagesUri = imagesUri;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public ArrayList<Uri> getImagesUri() {
        return imagesUri;
    }
}

