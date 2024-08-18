# Gym Project

## Overview

This is a Gym application built in Android Studio using Java. The application allows users to create, manage, and follow workout plans. Users can log in, register, and customize exercises according to their needs. The app includes various activities, adapters, models, and utilities to provide a seamless workout experience.

## Features

- User Authentication (Login and Registration)
- Create and Manage Workout Plans
- Add Custom Exercises
- Choose Exercises from a Library & Add
- Edit & delete exercise
- Timer for Workouts (which also counts how many sets were performed)
- Settings and Preferences Management
- Light & Night mode


## Application Flow

### 1. Getting Started
- **User Onboarding**
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/visitPage.jpg?raw=true" alt="Visit Page" style="height:700px;"/>

- **Sign-Up/Login**: Users can create an account or log in to track their plans over time.
<div class="row">
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/registerPage.jpg?raw=true" alt="Register Page" style="height:700px;"/>
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/loginPage.jpg?raw=true" alt="Login Page" style="height:700px;"/>
</div>

### 2. Creating and Managing Workout Plans
- **Create/delete Workout Plan**: Users can create a new workout plan, and can be deleted by clicking on the trash.
- Here you can see a small example of the day and night mode that the application offers. Of course, this affects the display of the entire application
<div class="row">
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/myPlansDark.jpg?raw=true" alt="My Plans Dark" style="height:700px;"/>
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/myPlans.jpg?raw=true" alt="My Plans" style="height:700px;"/>
</div>

### 3. Managing Workout Plan
- **Manage Workout Plan**: Users can build a dynamic training plan for themselves, add, remove, edit exercises.
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/plan.jpg?raw=true" alt="Plan" style="height:700px;"/>

### 4. Adding Exercises
- **Add Custom Exercise**: Users can add exercises that are not available in the library.
- **Add Exercise from Library**: Users can browse and select exercises from the pre-defined library.
<div class="row">
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/addExer.jpg?raw=true" alt="Add Exercise" style="height:700px;"/>
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/addLibraryexec.jpg?raw=true" alt="Add Library Exec" style="height:700px;"/>
</div>

### 5. Edit/ Remove Exercises
- **Edit Exercise**: Users can modify details of existing exercises to better suit their needs by clicking on the pencil.
- **Delete Exercise**: Users can remove exercises that they no longer wish to keep in their workout.
<div class="row">
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/editExer.jpg?raw=true" alt="Edit Exercise" style="height:700px;"/>
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/deleteEx.jpg?raw=true" alt="Edit Exercise" style="height:700px;"/>
</div>

### 6. Tracking rest times during sets
- **Training timer**: Users who finish a set can press the V and start a timer that counts the rest time the user entered and the amount of sets performed (and how many are left to perform).
- At the end of the timer, a sound is heard to warn that the next set should start
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/timer.jpg?raw=true" alt="Timer" style="height:700px;"/>

### 7. Settings
- **Mode button:**: User can select day/night mode.
<div class="row">
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/settingsDark.jpg?raw=true" alt="Settings Dark" style="height:700px;"/>
<img src="https://github.com/GalAngel15/GymProject_AndroidStudio/blob/gh-pages/images/settings.jpg?raw=true" alt="Settings" style="height:700px;"/>
</div>
  
## Project Structure

### Activities

- **AddCustomExerciseActivity.java**: Activity for adding custom exercises.
- **AddExerciseFromLibraryActivity.java**: Activity for adding exercises from the library.
- **BaseActivity.java**: Base activity that other activities inherit from.
- **LoginActivity.java**: Activity for user login.
- **MyPlansActivity.java**: Activity displaying user's workout plans.
- **PlanPageActivity.java**: Activity showing the details of a workout plan.
- **RegisterActivity.java**: Activity for user registration.
- **SettingsActivity.java**: Activity for managing user settings.
- **TimerActivity.java**: Activity with a timer for workouts.
- **VisitPageActivity.java**: Activity for visiting a specific page.

### Adapters

- **BuiltExerciseAdapter.java**: Adapter for built exercises.
- **CustomExerciseAdapter.java**: Adapter for custom exercises.
- **WorkoutPlanAdapter.java**: Adapter for workout plans.

### Application

- **GymProjectApplication.java**: Main application class.

### Interfaces

- **AddPlanCallback.java**: Callback interface for adding plans.
- **OnExerciseEditedListener.java**: Listener for exercise edits.
- **OnExerciseLoadedListener.java**: Listener for exercise loading.
- **OnExerciseSavedListener.java**: Listener for exercise saving.
- **OnExerciseSaveListener.java**: Another listener for exercise saving.
- **OnPlanClickListener.java**: Listener for plan clicks.

### Managers

- **SetsManager.java**: Manager for sets.
- **SharedPreferencesManager.java**: Manager for shared preferences.
- **WorkoutPlanManager.java**: Manager for workout plans.

### Models

- **BuiltExercise.java**: Model for built exercises.
- **CustomExercise.java**: Model for custom exercises.
- **PartialCustomExercise.java**: Model for partial custom exercises.
- **WorkoutPlan.java**: Model for workout plans.

### Utilities

- **DatabaseUtils.java**: Utility class for database operations.
- **DialogUtils.java**: Utility class for dialog operations.
- **ExercisesUtiles.java**: Utility class for exercises.
- **ImageLoader.java**: Utility class for loading images.
- **ThemeUtil.java**: Utility class for theme management.

## Libraries Used

- **Firebase Authentication**: Handles user authentication processes.
- **Gson**: Converts Java objects to JSON and vice versa.
- **Firebase Analytics**: Analyzes user behavior within the app.
- **Firebase Realtime Database**: Manages real-time data within the app.
- **Firebase UI Authentication**: Provides UI components for Firebase authentication.
- **Glide**: Manages and displays images efficiently.
