package com.jzw.testserviceloadera;

import com.jzw.testserviceloaderinterface.TestInterface;

/**
 * 模块A的实现
 * 把这些接口实现类注册到配置文件中，spi的机制就是注册到META-INF/services中。首先在java的同级目录中new一个包
 * 目录resources，然后在resources新建一个目录META-INF/services，再新建一个file，file的名称就是接口的全限定名
 */
public class TestServiceLoaderA implements TestInterface {
    @Override
    public String dispaly() {
        return "this is TestServiceLoaderA dispaly";
    }
}
