package com.example.myapplication.observer;

import com.example.myapplication.bean.LoginBean;
import com.example.myapplication.bean.SuccessBean;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class CustomObserver implements Observer<LoginBean> {

    public abstract void success(SuccessBean successBean);
    public abstract void fail(String messagee);

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull LoginBean loginBean) {
        if(loginBean.getCode()==200){
            success(loginBean.getBean());
        }else {
            fail(loginBean.getMessage());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        fail("报错了");
    }

    @Override
    public void onComplete() {

    }
}
