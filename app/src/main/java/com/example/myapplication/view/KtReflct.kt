package com.example.myapplication.view

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * kt反射
 */
class KtReflct {
    fun getMethod(){
        //获取字节码文件
        val kClass = KtReflctClass::class
        //获取类名
        val simpleName = kClass.simpleName
        //获取全类名：包名.类名
        val qualifiedName = kClass.qualifiedName
        //父类类型
        val supertypes = kClass.supertypes
        //是否可以继承
        val open = kClass.isOpen
        //是否时抽象类
        val abstract = kClass.isAbstract
        //是否是数据类
        val data = kClass.isData

        kClass.typeParameters.forEach{
            println(it)
        }
        val members = kClass.members
        //转化为java字节码对象
        val java = kClass.java
        val method = java.getMethod("aMethod")
        method.invoke("")
        //转化为kt字节码对象 kclass
        val kotlin = java.kotlin

        //报错并提示转化方法，点击可以转化
//        KtReflctClass().testRplace("zzz")

        MainScope().launch(Dispatchers.IO){
            withContext(Dispatchers.Main){

            }
        }
    }


}