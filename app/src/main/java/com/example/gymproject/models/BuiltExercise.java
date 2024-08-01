package com.example.gymproject.models;

public class BuiltExercise {
    private String id;
    private String mainMuscle;
    private String name;
    private String imageUrl;

    public BuiltExercise() {
    }

    public BuiltExercise(String id,String mainMuscle, String name, String imageUrl) {
        this.mainMuscle = mainMuscle;
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BuiltExercise{" +
                "id='" + id + '\'' +
                ", mainMuscle='" + mainMuscle + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
