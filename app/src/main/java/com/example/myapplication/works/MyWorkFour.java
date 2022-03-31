package com.example.myapplication.works;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkFour extends Worker {
    public MyWorkFour(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(MyWorkOne.TAG, "doWork: this is workfour doing");
        return Result.retry();
    }
}
