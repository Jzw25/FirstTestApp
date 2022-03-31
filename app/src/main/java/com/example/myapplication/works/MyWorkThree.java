package com.example.myapplication.works;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.CoroutineWorker;
import androidx.work.ForegroundInfo;
import androidx.work.WorkerParameters;

import kotlin.coroutines.Continuation;

/**
 * CoroutineWorker
 * 如果您使用 CoroutineWorker，则必须实现 getForegroundInfo()。然后，在 doWork() 内将其传递给 setForeground()。这样做会在 Android 12 之前的版本中创建通知。
 */
public class MyWorkThree extends CoroutineWorker {
    public MyWorkThree(@NonNull Context appContext, @NonNull WorkerParameters params) {
        super(appContext, params);
    }

    @Nullable
    @Override
    public Object doWork(@NonNull Continuation<? super Result> continuation) {
//        setForeground(getForegroundInfo())
        return Result.retry();
    }

    @Nullable
    @Override
    public Object getForegroundInfo(@NonNull Continuation<? super ForegroundInfo> $completion) {
        return super.getForegroundInfo($completion);
    }
}
