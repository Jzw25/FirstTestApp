package com.example.myapplication.kt

/**
 * 扩展文件类，我们可以定义public的扩展函数再一个文件中，然后就可以再另外的文件中引入这个扩展文件，调用
 */
    //写一个list的父类的扩展函数
    fun <E> Iterable<E>.randomValue() = this.shuffled().first()
    //写一个可以打印的
    fun <E> Iterable<E>.randomValuePrl() = println(this.shuffled().first())