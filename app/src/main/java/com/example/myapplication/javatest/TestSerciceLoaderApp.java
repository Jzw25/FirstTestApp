package com.example.myapplication.javatest;

import com.jzw.testserviceloaderinterface.TestInterface;

/**
 * app的实现
 */
public class TestSerciceLoaderApp implements TestInterface {
    @Override
    public String dispaly() {
        return "this is App dispaly";
    }
}
