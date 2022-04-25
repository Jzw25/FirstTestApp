package com.example.myapplication.kt

class TestJvmStatic {
    companion object{
        @JvmField
        val namm = "jzw"
        @JvmStatic
        fun show(name:String) = println("$name go to lick $namm")
    }

}

fun main(){
    //kt中可以直接调用,但是Java中不行，得加上jvmfiled
    println(TestJvmStatic.namm)
    //这个得加上jvmstatic
    TestJvmStatic.show("yt")
}