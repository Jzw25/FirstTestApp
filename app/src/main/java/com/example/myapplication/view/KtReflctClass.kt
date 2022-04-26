package com.example.myapplication.view

class KtReflctClass {
    val name = "zs"

    fun aMethod(){
        println("the name is $name")
    }

    //函数组合,传入方法调用：：属性的调用也：：
    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
        return { x -> f(g(x)) }
    }
    /**
     * @Deprecated这个注解源码定义，以及它的使用。@Deprecated注解在原来的Java基础增强了一个ReplaceWith功能.
     * 可以直接在使用了老的API时，编译器可以根据ReplaceWith中的新API，自动替换成新的API。这一点在Java中是做
     * 不到的，你只能点击进入这个API查看源码来正确使用新的API。
     */
    @Deprecated("Use testRplace2(name) instead.", ReplaceWith("testReplcae2(name)"), level = DeprecationLevel.ERROR)//定义的级别是ERROR级别的，这样当你在调用remove方法，编译器会报错。
    fun testRplace(name:String){

    }

    fun testReplcae2(name: String){

    }

    //调用自定义注解
    @TestKtAnnotain("zs")
    fun testAnn(){

    }

    /**
     * JvmName注解改变默认方法名称，调用时就可以调用JvmName定义的名称了
     */
    @set:JvmName(name = "setAllAge")
    @get:JvmName(name = "getAllAge")
    var age:Int = 18

    @JvmName("getValue")
    fun showNameValue() = println("this age is $age")
}