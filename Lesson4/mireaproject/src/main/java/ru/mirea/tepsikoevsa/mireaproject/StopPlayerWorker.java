package ru.mirea.tepsikoevsa.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class StopPlayerWorker extends Worker {
    private static final String TAG = "StopPlayerWorker";

    public StopPlayerWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Stopping audio playback...");
        PlayerService.stopPlayback();
        return Result.success();
    }
}
