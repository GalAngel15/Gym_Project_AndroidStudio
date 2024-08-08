package com.example.gymproject.utilities;


public class ExercisesUtiles {
    public static boolean checkValuesForExercise(String selectedMuscle, String exerciseName, int setsString, int repsString, double weightString, int rest) {
            if (selectedMuscle==null ||exerciseName.isEmpty() || setsString<=0 || repsString<=0 || weightString<0 || rest<=0) {
                return false;
            }
            return true;
    }

    public static boolean checkValuesForExercise(String selectedMuscle, String exerciseName, String setsString, String repsString, String weightString, String rest) {
        if (selectedMuscle==null ||exerciseName.isEmpty() || setsString.isEmpty() || repsString.isEmpty() || weightString.isEmpty() || rest.isEmpty()) {
            return false;
        }else{
            if(!checkValuesForExercise(selectedMuscle, exerciseName, Integer.parseInt(setsString), Integer.parseInt(repsString), Double.parseDouble(weightString), Integer.parseInt(rest)))
                return false;
        }
        return true;
    }
}
