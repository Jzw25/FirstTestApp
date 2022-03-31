package com.example.myapplication.works;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * 测试重复策略的work
 */
public class MyWorkTwo extends Worker {
    public MyWorkTwo(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(MyWorkOne.TAG, "doWork: this is worktwo s do work");
        //设置为可重复
        return Result.retry();
    }

    /**
     * 向后兼容性和前台服务
     * 为了保持加急作业的向后兼容性，WorkManager 可能会在 Android 12 之前版本的平台上运行前台服务。前台服务可以向用户显示通知。
     *
     * 在 Android 12 之前，工作器中的 getForegroundInfoAsync() 和 getForegroundInfo() 方法可让 WorkManager 在您调用 setExpedited() 时显示通知。
     *
     * 如果您想要请求任务作为加急作业运行，则所有的 ListenableWorker 都必须实现 getForegroundInfo 方法。
     *
     * 注意：如果未能实现对应的 getForegroundInfo 方法，那么在旧版平台上调用 setExpedited 时，可能会导致运行时崩溃。
     * 以 Android 12 或更高版本为目标平台时，前台服务仍然可通过对应的 setForeground 方法使用。
     *
     * 注意：setForeground() 可能会在 Android 12 上抛出运行时异常，并且在启动受到限制时可能会抛出异常。
     * 工作器
     * 工作器不知道自身所执行的工作是否已加急。不过，在某些版本的 Android 上，如果 WorkRequest 被加急，工作器可以显示通知。
     *
     * 为此，WorkManager 提供了 getForegroundInfoAsync() 方法，您必须实现该方法，让 WorkManager 在必要时显示通知，以便启动 ForegroundService。
     *
     * CoroutineWorker
     * 如果您使用 CoroutineWorker，则必须实现 getForegroundInfo()。然后，在 doWork() 内将其传递给 setForeground()。这样做会在 Android 12 之前的版本中创建通知。
     * @return
     */
    @NonNull
    @Override
    public ListenableFuture<ForegroundInfo> getForegroundInfoAsync() {
        return super.getForegroundInfoAsync();
    }
}
