package com.example.gymproject.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class FullScreenManager {

    private static Context context;
    private static volatile FullScreenManager instance;

    private FullScreenManager(Context context) {
        FullScreenManager.context = context.getApplicationContext();
    }


    public static void init(Context context) {
        if (instance == null) {
            instance = new FullScreenManager(context);
        }
    }

    public static FullScreenManager getInstance() {
        return instance;
    }

    public void fullScreen(Window window){
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}


