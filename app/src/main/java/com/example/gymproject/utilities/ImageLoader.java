package com.example.gymproject.utilities;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gymproject.R;

public class ImageLoader {
    private static Context context;
    private static volatile ImageLoader instance;

    private ImageLoader(Context context) {
        ImageLoader.context = context.getApplicationContext();    }

    public static ImageLoader getInstance() {
        return instance;
    }

    public static ImageLoader init(Context context){
        if (instance == null){
            synchronized (ImageLoader.class){
                if (instance == null){
                    instance = new ImageLoader(context);
                }
            }
        }
        return getInstance();
    }

    public void load (String source, ImageView imageView){
        Glide
                .with(context)
                .load(source)
                .placeholder(R.drawable.unavailable_photo)
                .centerInside()
                .into(imageView);
    }

    public void load (Uri source, ImageView imageView){
        Glide
                .with(context)
                .load(source)
                .placeholder(R.drawable.unavailable_photo)
                .centerInside()
                .into(imageView);
    }
}
