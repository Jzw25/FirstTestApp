package com.jzw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解类
 * **APT执行于javac编译之前，将其打成javac，而反射则执行与运行时，相比反射APT效率更高**
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface MyRouterTest {
    String value() default "default path";

}
