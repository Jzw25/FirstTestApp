package com.example.myapplication.view

import java.io.IOException

class MyTestInterClass : MyTestInterface {
    override fun covertData(datas: List<String>) {
        TODO("Not yet implemented")
    }

    override fun getData(): List<String> {
        TODO("Not yet implemented")
    }

    override fun covertData1(datas: List<String>) {
        TODO("Not yet implemented")
    }

    override fun getData1(): List<String> {
        TODO("Not yet implemented")
    }

    /**
     * @JvmSynthetic 作用于函数、属性的setter,getter以及字段
    1、作用
    它在生成的类文件中将适当的元素标记为合成,并且编译器标记为合成的任何元素都将无法从Java语言中访问。

    2、什么是合成属性(Synthetic属性)?
    JVM字节码标识的ACC_SYNTHETIC属性用于标识该元素实际上不存在于原始源代码中，而是由编译器生成。

    3、合成属性能做什么?
    它一般用于支持代码生成，允许编译器生成不应向其他开发人员公开但需要支持实际公开接口所需的字段和方法。我们可以将其视为超越private或protected级别。
     */
    @JvmSynthetic
    val name : String = "zs"
    var age = 18
        @JvmSynthetic
    get() = field
        @JvmSynthetic
    set(value) {
        field = value
    }

    /**
     * @Throws 用于Kotlin中的函数,属性的setter或getter函数，构造器函数抛出异常
     * 作用于函数、属性的getter、setter函数、构造器函数
     */
    @Throws(IOException::class)
    fun show() = println("this is show")

    /**
     * @Transient
    该注解充当了Java中的transient关键字

    @Strictfp
    该注解充当了Java中的strictfp关键字

    @Synchronized
    该注解充当了Java中的synchronized关键字

    @Volatile
    该注解充当了Java中的volatile关键字
     */

}