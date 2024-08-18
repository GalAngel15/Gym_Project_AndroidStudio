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
![image](https://github.com/user-attachments/assets/9c16324d-e8d8-4422-a70a-536bf3da75c3)


### 1. Getting Started
- **User Onboarding**: Introduce the users to the features and benefits of the app.
- **Sign-Up/Login**: Users can create an account or log in to track their progress over time.

![image](https://github.com/user-attachments/assets/59641a45-0683-42c9-a1f6-e2a0ddc43cbc)
<img src="![image](https://github.com/user-attachments/assets/00baad2a-6641-4db4-885b-124e7960b0ba)" alt="Log In Screen" style="height:700px;"/>



### 2. Creating and Managing Workout Plans
- **Create Workout Plan**: Users can create a new workout plan by adding exercises from a library or by creating custom exercises.
- **Manage Workout Plans**: Users can view and manage their existing workout plans.

    ![Create Plan Screen](path/to/create_plan_screen_image)
    ![Manage Plans Screen](path/to/manage_plans_screen_image)

### 3. Adding Exercises
- **Add Custom Exercise**: Users can add exercises that are not available in the library.
- **Add Exercise from Library**: Users can browse and select exercises from the pre-defined library.

    ![Add Custom Exercise Screen](path/to/add_custom_exercise_screen_image)
    ![Add Exercise from Library Screen](path/to/add_exercise_from_library_screen_image)

### 4. Tracking Workouts
- **Workout Timer**: Users can start a workout session and use the built-in timer to track their workout duration.
- **Exercise Execution**: Users can follow the exercises as per their workout plan.

    ![Workout Timer Screen](path/to/workout_timer_screen_image)
    ![Exercise Execution Screen](path/to/exercise_execution_screen_image)

### 5. Customization
- **User Profiles**: Users can set goals, and customize their profiles according to their fitness levels and preferences.

    ![User Profile Screen](path/to/user_profile_screen_image)
  
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

## Application Flow



