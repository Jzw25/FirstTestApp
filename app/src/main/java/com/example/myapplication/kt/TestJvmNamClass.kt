@file:JvmName("zhuan")

package com.example.myapplication.kt

/**
 * @file:JvmName("zhuan")可以在其他端如Java中调用时使用括号内定义的名称，但只能写在包名外的位置
 */
fun show() = println("this is jvmname show")
//没有加jvmfiled，默认是private私有的，会自动生成get函数
val list = listOf("kkk","aaa","aaaa")
//加jvmfiled，变成public可直接调用了
@JvmField
val jvmfileList = listOf("asd","fff","sdad")

/**
 * 默认其他平台如Java是不能使用kt默认参数值的，使用JvmOverloads注解后，就可以使得他们能和kt一样调用了
 * 其原理是编译阶段会为其生成一个重载函数，添加了默认值
 */
@JvmOverloads
fun toast(name:String,age:Int = 18,sex:String = "man"){
    println("name is $name age is $age sex is $sex")
}