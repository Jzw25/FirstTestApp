package com.example.myapplication.bean;

import android.content.ClipData;
import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myapplication.BR;

/**
 * 一个POJO值改变并不能够自动更新UI，dataBing的强大之处在于值改变能够自动同步更新UI(单向绑定)，实现的方式有如下三种
 * 用一个类来实现Observable为了方便，Android 原生提供了已经封装好的一个类 - BaseObservable，并且实现了监听器的注册机制。
 * 我们可以直接继承BaseObservable自动跟新数据显示
 * BR 是编译阶段生成的一个类，功能与 R.java 类似，用 @Bindable 标记过 getter 方法会在 BR 中生成一个 entry，当我们
 *
 * 通过代码可以看出，当数据发生变化时还是需要手动发出通知。 通过调用notifyPropertyChanged(BR.firstName)来通知
 * 系统 BR.firstName 这个 entry 的数据已经发生变化，需要更新 UI。
 *
 */
public class DataBindingTestVo extends BaseObservable {
    private String name;
    private String id;
    private String age;

    /**
     * 构造代码块，调用构造函数时会被调用
     */
    {

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    public void clickOne(View view){
        Log.d("databingding", "clickOne: click");
    }

    public interface ItemEventHandler {
        void clickTitle(View view, ClipData.Item item);
    }
}
