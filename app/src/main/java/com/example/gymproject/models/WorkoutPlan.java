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

    public void setName(String name) {
        this.name = name;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getTimesDone() {
        return timesDone;
    }

    public void setTimesDone(int timesDone) {
        this.timesDone = timesDone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
