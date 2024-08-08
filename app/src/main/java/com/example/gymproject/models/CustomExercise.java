package com.example.gymproject.models;

public class CustomExercise {
    private String mainMuscle;
    private String name;
    private String imageUrl;
    private int sets;
    private int reps;
    private double weight;
    private int rest;
    private String other;

    // בונה ברירת מחדל דרוש לפעולה של Firebase
    public CustomExercise() {
    }

    public CustomExercise(String mainMuscle, String name, String imageUrl, int sets, int reps, double weight, int rest, String other) {
        this.mainMuscle=mainMuscle;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.rest = rest;
        this.other = other;
    }

    // Getters and Setters
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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

    @Override
    public String toString() {
        return "CustomExercise{" +
                "mainMuscle='" + mainMuscle + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", sets=" + sets +
                ", reps=" + reps +
                ", weight=" + weight +
                ", rest=" + rest +
                ", other='" + other + '\'' +
                '}';
    }
}
