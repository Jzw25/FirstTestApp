package com.example.myapplication.observer;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    private CountDownTimer timer;
    private MutableLiveData<String> liveData;


    /**
     * 我们可以在onCleared()对定时器资源的释放，防止造成内存泄露
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if(timer!=null){
            timer.cancel();
        }
    }

    public MutableLiveData<String> showCount(){
        if(liveData==null){
            liveData = new MutableLiveData<>();
        }
        countDown();
        return liveData;
    }

    public void countDown() {
         timer = new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                // TODO：为了方便演示，我们直接在MainActivity中操作LiveData，实际项目中不要这样编写代码，应该把LiveData放到ViewModel中
                liveData.setValue("倒计时：" + l / 1000);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

}
