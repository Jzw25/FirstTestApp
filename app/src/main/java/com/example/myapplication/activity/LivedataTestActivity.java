package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLivedataTestBinding;
import com.example.myapplication.observer.NetWorkLiveData;
import com.example.myapplication.observer.TimerViewModel;

public class LivedataTestActivity extends AppCompatActivity {
    private static final String TAG = LivedataTestActivity.class.toString();

    private ActivityLivedataTestBinding binding;
    private MutableLiveData<Long> liveData;
    private MutableLiveData<String> mutableLiveData1;
    private MutableLiveData<String> mutableLiveData2;
    private MediatorLiveData<String> mediatorLiveData;

    private TimerViewModel model;

    private NetWorkLiveData netWorkLiveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivedataTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        netWorkLiveData = NetWorkLiveData.getInstance(this);
        //网咯监听
        netWorkLiveData.observe(this, new Observer<NetworkInfo>() {
            @Override
            public void onChanged(NetworkInfo networkInfo) {
                Log.d(TAG, "onChanged: networkInfo : " + networkInfo);
            }
        });
    }

    private void testLiveDate(){
        liveData = new MutableLiveData<>();

        liveData.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                /**
                 * 简单做一个倒计时
                 */
                binding.tv.setText(String.valueOf(aLong/1000));
            }
        });
        countDown();
    }

    public void countDown() {
        new CountDownTimer(1 * 60 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {
                // TODO：为了方便演示，我们直接在MainActivity中操作LiveData，实际项目中不要这样编写代码，应该把LiveData放到ViewModel中
                liveData.setValue(l);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void testMediatorLiveData(){
        mediatorLiveData = new MediatorLiveData<>();
        mutableLiveData1 = new MutableLiveData<>();
        mutableLiveData2 = new MutableLiveData<>();
        mediatorLiveData.addSource(mutableLiveData1, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mediatorLiveData.setValue(s);
            }
        });
        mediatorLiveData.addSource(mutableLiveData2, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mediatorLiveData.setValue(s);
            }
        });
        mediatorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tv.setText(s);
            }
        });
        showMergeTest();
    }

    private void showMergeTest(){
        new CountDownTimer(60*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                mutableLiveData1.setValue("网咯数据更新" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        new CountDownTimer(60*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                /**
                 * 您必须调用setValue(T)方法以LiveData从主线程更新对象。如果代码在工作线程中行，则可以使用该postValue(T)方法来更新LiveData对象。
                 */
                mutableLiveData2.setValue("本地数据更新" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * viewmodle+livedata
     */
    private void testViewModel(){
        model = new ViewModelProvider(this).get(TimerViewModel.class);
        MutableLiveData<String> mutableLiveData = model.showCount();
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tv.setText(s);
            }
        });
    }
}