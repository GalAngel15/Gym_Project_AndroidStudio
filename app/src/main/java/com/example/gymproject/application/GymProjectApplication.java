
package com.example.gymproject.application;

import android.app.Application;

import com.example.gymproject.utilities.ImageLoader;
import com.example.gymproject.utilities.ThemeUtil;

public class GymProjectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
        ThemeUtil.applyTheme(this);
    }
}