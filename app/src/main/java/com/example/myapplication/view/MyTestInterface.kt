package com.example.myapplication.view

/**
 * @JvmSuppressWildcards和@JvmWildcard 用于指示编译器生成或省略类型参数的通配符，JvmSuppressWildcards用于
 * 参数的泛型是否生成或省略通配符，而JvmWildcard用于返回值的类型是否生成或省略通配符
 */
interface MyTestInterface {
    fun covertData(datas: List<String>)

    fun getData(): List<String>

    fun covertData1(datas: List<@JvmSuppressWildcards(suppress = false) String>)//@JvmSuppressWildcardsd用于参数类型

    fun getData1(): List<@JvmWildcard String>//@JvmWildcard用于返回值类型
}