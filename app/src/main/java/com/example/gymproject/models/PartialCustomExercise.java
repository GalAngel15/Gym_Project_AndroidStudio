package com.example.gymproject.models;

public class PartialCustomExercise {
    private int sets;
    private int reps;
    private double weight;
    private int rest;
    private String other;
    private int numExercise;

    public PartialCustomExercise(int sets, int reps, double weight, int rest, String other) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.rest = rest;
        this.other = other;
    }

    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public double getWeight() { return weight; }
    public int getRest() { return rest; }
    public String getOther() { return other; }

    public void setSets(int sets) {
        this.sets = sets;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setRest(int rest) {
        this.rest = rest;
    }
    public void setOther(String other) {
        this.other = other;
    }
    public int getNum() {
        return numExercise;
    }
    public void setNum(int num) {
        this.numExercise = num;
    }
}
