package com.example.gymproject.utilities;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerUtil {

    private static MediaPlayer mediaPlayer;

    // ניגון צליל מסוים
    public static void playSound(Context context, int soundResId) {
        // בדיקת ניגון קיים ושחרור משאבים
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // יצירת ה-MediaPlayer
        mediaPlayer = MediaPlayer.create(context, soundResId);
        mediaPlayer.start();

        // שחרור ה-MediaPlayer בסיום הניגון
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    // עצירת הניגון ושחרור ה-MediaPlayer
    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
