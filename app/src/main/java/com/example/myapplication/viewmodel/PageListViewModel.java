package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagingSource;

import com.example.myapplication.model.MyTreeFactory;
import com.example.myapplication.model.TreeBean;

import kotlin.jvm.functions.Function0;

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
            liveData = new LivePagedListBuilder<Integer,TreeBean>((Function0<? extends PagingSource<Integer, TreeBean>>) dataSource,myPagingConfig).build();
        }
        return liveData;
    }

    public void initDataSource(){
        dataSource.invalidate();
    }
}
