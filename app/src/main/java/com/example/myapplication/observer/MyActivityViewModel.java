package com.example.myapplication.observer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * 我们在使用ViewModel，需要注意的时，不要向ViewModel中传入任何类型的Context或带有Context引用的对象，可能会导
 * 致页面无法销毁，从而引发内存泄露。如果你希望在VIewModel中使用Context对象(例如，为了查找系统服务)，可以使用
 * AndroidViewModel类，它继承自ViewMoel,并接收Application作为Context.。因为Application会扩展Context
 */
public class MyActivityViewModel extends AndroidViewModel {
    public MyActivityViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * ViewModel与onSaveInstanceState()方法
     * onSaveInstanceState()方法
     * onSaveInstanceState()方法只能保存少量的、能支持序列化的数据，但是onSaveInstanceState()方法可以持久化页面的数据。
     *
     * ViewModel
     * ViewModel能支持页面中所有的数据，但是需要注意的是，ViewModel不支持数据的持久化，当页面被彻底销毁时，ViewModel及其持有的数据就不存在。
     *
     * 大家需要了解onSaveInstanceState()方法和ViewModel的区别，二者不可混淆。
     */
}
