package com.example.myapplication.works;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 更新进度
 * 对于使用 ListenableWorker 或 Worker 的 Java 开发者，setProgressAsync() API 会返回 ListenableFuture<Void>；
 * 更新进度是异步过程，因为更新过程涉及将进度信息存储在数据库中。在 Kotlin 中，您可以使用 CoroutineWorker
 * 对象的 setProgress() 扩展函数来更新进度信息
 *
 * 此示例展示了一个简单的 ProgressWorker。Worker 在启动时将进度设置为 0，在完成后将进度值更新为 100。
 */
public class MyProgressWork extends Worker {
    private static final String NAME = "progress";
    private static final long SEELP = 1000L;
    @SuppressLint("RestrictedApi")
    public MyProgressWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setProgressAsync(new Data.Builder().put(NAME,0).build());
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(SEELP);
            setProgressAsync(new Data.Builder().put(NAME,100).build());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
