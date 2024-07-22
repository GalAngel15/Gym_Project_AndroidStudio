package com.example.gymproject.models;

public class WorkoutPlan {
    private String name;
    private String lastDate;
    private int timesDone;
    private String description;

    public WorkoutPlan(String name, String lastDate, int timesDone, String description) {
        this.name = name;
        this.lastDate = lastDate;
        this.timesDone = timesDone;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getLastDate() {
        return lastDate;
    }

    public int getTimesDone() {
        return timesDone;
    }

    public String getDescription() {
        return description;
    }
}
