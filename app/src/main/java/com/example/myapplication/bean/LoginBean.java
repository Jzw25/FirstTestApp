package com.example.myapplication.bean;

public class LoginBean {
    private SuccessBean bean;

    private int code;

    private String message;

    public SuccessBean getBean() {
        return bean;
    }

    public void setBean(SuccessBean bean) {
        this.bean = bean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "bean=" + bean +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
