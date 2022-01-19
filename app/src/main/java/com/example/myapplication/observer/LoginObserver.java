package com.example.myapplication.observer;

import com.example.myapplication.bean.LoginBean;
import com.example.myapplication.bean.SuccessBean;

import io.reactivex.rxjava3.core.Observable;

public class LoginObserver {
    public static Observable<LoginBean> login(String name,String pwd){
        LoginBean bean = new LoginBean();
        if("jzw".equals(name)&&"123456".equals(pwd)){
            SuccessBean successBean = new SuccessBean();
            successBean.setId("775822");
            successBean.setName("Jzw");
            bean.setBean(successBean);
            bean.setCode(200);
            bean.setMessage("登陆成功");
        }else {
            bean.setBean(null);
            bean.setMessage("登录失败code为："+404);
            bean.setCode(404);
        }
        return Observable.just(bean);
    }
}
