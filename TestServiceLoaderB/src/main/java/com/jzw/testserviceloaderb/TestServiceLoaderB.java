package com.jzw.testserviceloaderb;

import com.jzw.testserviceloaderinterface.TestInterface;

/**
 * 模块B的实现
 */
public class TestServiceLoaderB implements TestInterface {
    @Override
    public String dispaly() {
        return "this is TestServiceLoaderB dispaly";
    }
}
