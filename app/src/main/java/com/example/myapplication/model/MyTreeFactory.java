package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class MyTreeFactory extends DataSource.Factory<Integer,TreeBean> {

    private MutableLiveData<TreeBeanDataSource> liveData = new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource<Integer, TreeBean> create() {
        TreeBeanDataSource treeBeanDataSource = new TreeBeanDataSource();
        liveData.postValue(treeBeanDataSource);
        return treeBeanDataSource;
    }
}
