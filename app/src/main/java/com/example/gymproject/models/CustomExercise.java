package com.example.gymproject.models;

public class CustomExercise {
    private String mainMuscle;
    private String name;
    private String imageUrl;
    private int sets;
    private int reps;
    private int weight;
    private int rest;
    private String other;

    // בונה ברירת מחדל דרוש לפעולה של Firebase
    public CustomExercise() {
    }

    public CustomExercise(String mainMuscle, String name, String imageUrl, int sets, int reps, int weight, int rest, String other) {
        this.mainMuscle=mainMuscle;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.rest = rest;
        this.other = other;
    }
    public CustomExercise(String mainMuscle, String name, String imageUrl) {
        this.mainMuscle=mainMuscle;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sets = 0;
        this.reps = 0;
        this.weight = 0;
        this.rest = 0;
        this.other = "";
    }

    // Getters and Setters
    public String getMainMuscle() {
        return mainMuscle;
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

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
