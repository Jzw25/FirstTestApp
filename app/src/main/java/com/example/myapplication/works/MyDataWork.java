package com.example.myapplication.works;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyDataWork extends Worker {

    public MyDataWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String s = getInputData().getString("data");
        if(TextUtils.isEmpty(s)){
            Log.d(MyWorkOne.TAG, "doWork: this is datawork get the data is : xxx");
            return Result.failure();
        }else {
            Log.d(MyWorkOne.TAG, "doWork: this is datawork get the data is : " + s);
            return Result.success();
        }

    }
}
