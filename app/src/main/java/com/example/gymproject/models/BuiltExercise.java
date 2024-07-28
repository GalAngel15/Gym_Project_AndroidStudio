package com.example.gymproject.models;

public class BuiltExercise {
    private String mainMuscle;
    private String name;
    private String imageUrl;

    public BuiltExercise() {
    }

    public BuiltExercise(String mainMuscle, String name, String imageUrl) {
        this.mainMuscle = mainMuscle;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getMainMuscle() {
        return mainMuscle;
    }

    public void setMainMuscle(String mainMuscle) {
        this.mainMuscle = mainMuscle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
