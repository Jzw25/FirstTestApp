package com.example.myapplication.observer;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareFragmentViewModel extends ViewModel {
    private MutableLiveData<String> liveData;
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<String> getLiveData(){
        if(liveData==null){
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
}
