package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.myapplication.model.MyTreeFactory;
import com.example.myapplication.model.TreeBean;

public class PageListViewModel extends ViewModel {
    private LiveData<PagedList<TreeBean>> liveData;
    private DataSource<Integer,TreeBean> dataSource;

    private LiveData<PagedList<TreeBean>> getLiveData(){
        if(liveData==null){
            MyTreeFactory myTreeFactory = new MyTreeFactory();
            dataSource = myTreeFactory.create();
            PagedList.Config myPagingConfig = new PagedList.Config.Builder()
                    .setPageSize(20)
                    .setPrefetchDistance(200)
                    .setEnablePlaceholders(true)
                    .build();
            liveData = new LivePagedListBuilder<>(dataSource,myPagingConfig).build();
        }
        return liveData;
    }

    public void initDataSource(){
        dataSource.invalidate();
    }
}
