package com.example.myapplication.dagger;

import com.example.myapplication.activity.TestDaggerAndHiltActivity;

import dagger.Component;

/**
 * 负责完成依赖注入的过程，我们可以叫它依赖注入组件，大致的意思就是依赖需求方需要什么类型的对象，依赖注入组件就从
 * 依赖提供方中拿到对应类型的对象，然后进行赋值
 */
@Component
public interface MainComponent {
    //inject方法的参数是依赖需求方的类型，即例子中的 TestDaggerAndHiltActivity，注意不可是基类的类型
    void inject(TestDaggerAndHiltActivity activity);
    /**
     *  Dagger 2 采用了annotationProcessor技术，在项目编译时动态生成依赖注入需要的 Java 代码。
     * 此时我们编译项目，由于使用了@Component注解，框架会自动帮我们生成一个MainComponent接口的实现类DaggerMainComponent，
     * 这个类可以在app\build\generated\source\apt\debug\包名目录下找到。
     * 所以，DaggerMainComponent就是真正的依赖注入组件
     */
}
