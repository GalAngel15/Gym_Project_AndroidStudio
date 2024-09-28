package com.example.gymproject.models;

import java.util.ArrayList;

public class User {
    private String userId;
    private String name;
    private String email;
    private String profilePictureUrl;
    private String joinDate;
    private float weight;
    private float height;
    private String gender;
    private int age;
    private String fitnessLevel;
    private String goals;
    private String bio;
    private String location;
    private String phoneNumber;
    private boolean notificationPreferences;
    private ArrayList<String> workoutImagesUrls;

    // Constructor
    public User(String userId, String name, String email, String profilePictureUrl, String joinDate,
                float weight, float height, String gender, int age, String fitnessLevel, String goals,
                String bio, String location, String phoneNumber, boolean notificationPreferences) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.joinDate = joinDate;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.fitnessLevel = fitnessLevel;
        this.goals = goals;
        this.bio = bio;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.notificationPreferences = notificationPreferences;
    }

    // Default constructor (for Firebase)
    public User() {}

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(boolean notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public ArrayList<String> getWorkoutImagesUrls() {
        return workoutImagesUrls;
    }

    public void setWorkoutImagesUrls(ArrayList<String> workoutImagesUrls) {
        this.workoutImagesUrls = workoutImagesUrls;
    }
}
