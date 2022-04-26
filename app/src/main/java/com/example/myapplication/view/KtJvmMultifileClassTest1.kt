//存在于OneUtils文件中
@file:JvmName("OneUtils")
//该注解主要是为了生成多文件的类,将多个文件合并为一个文件，调用的时候使用OneUtils点出方法
@file:JvmMultifileClass
package com.example.myapplication.view

fun showOne() = println("this is show one")