package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * JvmOverloads 指导Kotlin编译器为带默认参数值的函数(包括构造函数)生成多个重载函数。
 * 该注解使用最多就是用于带默认值函数的重载，在Android中我们在自定义View的时候一般会重载多个构造器，需要加入该注
 * 解，如果不加默认只定义一个构造器，那么当你直接在XML使用这个自定义View的时候会抛出异常。
 */
class TestOverLoadKt @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    /**
     * @JvmPackageName 更改从使用该注解标注的文件生成的.class文件的JVM包的完全限定名称。 这不会影响Kotlin客户
     * 端在此文件中查看声明的方式，但Java客户端和其他JVM语言客户端将看到类文件，就好像它是在指定的包中声明的那样。
     * 如果使用此批注对文件进行批注，则它只能包含函数，属性和类型声明，但不能包含。
     */
}