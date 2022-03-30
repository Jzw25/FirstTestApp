package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.TreeBean;
import com.example.myapplication.respository.TestRespository;

/**
 * viewmodel层
 * ViewModel作为Activity/Fragment与其他组件的连接器。负责转换和聚合Model中返回的数据，使这些数据易于显示，并把这些数据改变及时的通知给Activity/Fragment。
 * ViewModel是具有生命周期意识的，当Activity/Fragment销毁时ViewModel的onClear方法会被回调，你可以在这里做一些清理工作。
 * LiveData是具有生命周期意识的一个可观察的的数据持有者，ViewModel中的数据由LiveData持有，并且只有当Activity/Fragment处于活动时才会通知UI数据的改变，避免无用的刷新UI；
 */
public class TestMvvmViewModel extends ViewModel {
    private TestRespository respository;
    private MutableLiveData<TreeBean> liveData;
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<TreeBean> getLiveData(){
        if(liveData==null){
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public void getData(){
        if(respository==null){
            respository = new TestRespository();
        }
        if(liveData==null){
            liveData = new MutableLiveData<>();
        }
        respository.getData(liveData);
    }
}
