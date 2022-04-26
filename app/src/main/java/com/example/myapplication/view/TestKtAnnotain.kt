package com.example.myapplication.view

/**
 * kt声明注解，和一般的声明很类似，只是在class前面加上了annotation修饰符
 */
/**
 * target属性：
 * CLASS, //表示作用对象有类、接口、object对象表达式、注解类
ANNOTATION_CLASS,//表示作用对象只有注解类
TYPE_PARAMETER,//表示作用对象是泛型类型参数(暂时还不支持)
PROPERTY,//表示作用对象是属性
FIELD,//表示作用对象是字段，包括属性的幕后字段
LOCAL_VARIABLE,//表示作用对象是局部变量
VALUE_PARAMETER,//表示作用对象是函数或构造函数的参数
CONSTRUCTOR,//表示作用对象是构造函数，主构造函数或次构造函数
FUNCTION,//表示作用对象是函数，不包括构造函数
PROPERTY_GETTER,//表示作用对象是属性的getter函数
PROPERTY_SETTER,//表示作用对象是属性的setter函数
TYPE,//表示作用对象是一个类型，比如类、接口、枚举
EXPRESSION,//表示作用对象是一个表达式
FILE,//表示作用对象是一个File
@SinceKotlin("1.1")
TYPEALIAS//表示作用对象是一个类型别名
 */
@Target(AnnotationTarget.FIELD,AnnotationTarget.FUNCTION)
/**
 * Retention元注解
 * SOURCE,//源代码时期(SOURCE): 注解不会存储在输出class字节码中
 * BINARY,//编译时期(BINARY): 注解会存储出class字节码中，但是对反射不可见
 * RUNTIME//运行时期(RUNTIME): 注解会存储出class字节码中，也会对反射可见, 默认是RUNTIME
 */
@Retention(AnnotationRetention.RUNTIME)
//该注解比较简单主要是为了标注一个注解类作为公共API的一部分，并且可以保证该注解在生成的API文档中存在。
@MustBeDocumented
//这个注解决定标注的注解在一个注解在一个代码元素上可以应用两次或两次以上。
@Repeatable
annotation class TestKtAnnotain(val name:String)
