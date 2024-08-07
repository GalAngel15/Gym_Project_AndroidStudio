package com.example.gymproject.models;

public class WorkoutPlan {
    private String id;
    private String name;
    private String lastDate;
    private int timesDone;
    private String description;

    public WorkoutPlan(String id, String name, String lastDate, int timesDone, String description) {
        this.id=id;
        this.name = name;
        this.lastDate = lastDate;
        this.timesDone = timesDone;
        this.description = description;
    }

    public String getId(){ return id;}

    public void setId(String id){ this.id = id;}

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
