package ru.mirea.tepsikoevsa.mireaproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PlayerWorker extends Worker {
    private static final String TAG = "PlayerWorker";
    private MediaPlayer mediaPlayer;

    public PlayerWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.d(TAG, "doWork: start");
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bird_song);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();

            // Ждем, пока музыкальный файл не будет воспроизведен полностью
            while (mediaPlayer.isPlaying()) {
                Thread.sleep(1000); // Проверяем каждую секунду
            }

            mediaPlayer.release(); // Освобождаем ресурсы MediaPlayer
            Log.d(TAG, "doWork: end");
            return Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Error during audio playback", e);
            return Result.failure();
        }
    }
}