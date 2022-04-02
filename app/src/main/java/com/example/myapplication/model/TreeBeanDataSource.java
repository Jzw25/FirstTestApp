package com.example.myapplication.model;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

public class TreeBeanDataSource extends PositionalDataSource<TreeBean> {

    /**
     * 加载初始化数据，可以这么来理解，加载的是第一页的数据。形象的说，当我们第一次打开页面，需要回调此方法来获取数据。
     * @param loadInitialParams
     * @param loadInitialCallback
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams loadInitialParams, @NonNull LoadInitialCallback<TreeBean> loadInitialCallback) {
        loadInitialCallback.onResult(fetchItems(0,20),0,200);
    }

    /**
     * 当有了初始化数据之后，滑动的时候如果需要加载数据的话，会调用此方法。
     * @param loadRangeParams
     * @param loadRangeCallback
     */
    @Override
    public void loadRange(@NonNull LoadRangeParams loadRangeParams, @NonNull LoadRangeCallback<TreeBean> loadRangeCallback) {
        loadRangeCallback.onResult(fetchItems(loadRangeParams.startPosition,loadRangeParams.loadSize));
    }

    private List<TreeBean> fetchItems(int startPosition, int pageSize) {
        List<TreeBean> list = new ArrayList<>();
        for (int i = startPosition; i < startPosition + pageSize; i++) {
            TreeBean concert = new TreeBean();
            concert.setId(i+"");
            concert.setName("zs"+i);
            list.add(concert);
        }
        return list;
    }
}
