package com.example.myapplication.works;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 创建自己的work类
 * Worker是一个抽象类，这个类用来指定具体需要执行的任务。使用时要继承这个类并且实现里面的doWork()方法，在其中写具体的业务逻辑。
 */
public class MyWorkOne extends Worker {
    public static final String TAG = "work";

    public MyWorkOne(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * work通过workmanager加入到队列，执行的就是dowork方法
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        // 执行业务逻辑

        Log.d(TAG, "doWork: this is work one test");

        // 根据执行情况进行返回，

        // 返回SUCCESS代表任务执行成功

        // 返回FAILURE代表任务执行失败，并且此时不会再重新执行任务

        // 返回RETRY，WorkManager会在之后再次尝试执行任务。
        return Result.success();
    }
}
