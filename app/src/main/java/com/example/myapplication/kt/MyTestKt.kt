package com.example.myapplication.kt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import com.bumptech.glide.Glide
import com.example.myapplication.activity.TabLayoutTestActivity
import java.io.File
import java.lang.Exception
import kotlin.math.roundToInt
//引入扩展文件
import com.example.myapplication.kt.randomValue as aa //as xx 这是重命名扩展，可以将很长的函数名修改成自定义的任何简化的名字，调用时名称就是as之后的名称
import com.example.myapplication.kt.randomValuePrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL

//编译时常量，在外部定义
const val main = ""
const val NAME = "jzw"
const val PWD = 123456

class MyTestKt {

    fun test() {
        //可读可写 名称 变量类型 变量值
        var name: String = "zhangsan"

        name = "里斯"

        //只读，不可修改的修饰符
        val age: Int = 18

        //类型推导，即可不声明变量类型，自动识别
        val time = 122//知道是int
        var fff = ""//知道是string

        /**
         * kt 数据类型看着像应用类型，但是在jvm中会自动转变成基本类型，性能无差别
         */

        /**
         * range 表达式，in 0..30 表示在0-30这个集合内，！in表示不在这个集合内
         */
        val aaa = if (age in 0..30) {

        } else if (age !in 0..30) {

        } else {

        }

        /**
         * when表达式 （类似java的switch），表达式和java中的语句不同，是由返回值的
         * else 可以是具体值也可以是{}，如果是具体值，返回值则为具体值的类型，如果时候{}则为any即java中的object
         */
        val week = 5
        val xxx = when (week) {
            1 -> "今天是周一"
            2 -> "今天是周二"
            3 -> "今天是周三"
            4 -> "今天是周四"
            5 -> "今天是周五"
            6 -> "今天是周六"
            else -> "啥也不是"
            //else -> {...}
        }

        /**
         * kt的string模板,能加入表达式
         */

        val place = "凌肯公园"
        val times = 5
        val isLogin = true
        println("今天去${place}玩了${time}个小时")

        println("金天使礼拜天，你进行了登录操作，恭喜你${if (isLogin) "登录成功了" else "登录失败了"}")

    }

    /**
     * kt 默认是public，不加就是public 私有的需加上修饰符，名称在前，类型在后
     * 参数可以有默认值，加了之后调用时没传入就使用默认值
     */
    private fun test01(age: Int, name: String = "lisi"): Int {
        return 200
    }

    private fun test02(age: Int, name: String, id: Int, firstname: String) {

    }

    /**
     * Unit如果不写返回类型时默认加上的，相当于java的void，转变为jvm字节码时和void等效
     * 无返回类型
     */
    private fun test03(): Unit {
        val age = 10;
        when (age) {
            //noting类型，会奔溃并返回todo中的内容
            -1 -> TODO("没有这种年龄")
            in 0..10 -> println("合理")
            in 11..20 -> println("也合理")
            else -> {
                println("不合理")
            }
        }
    }

    /**
     * ``在kt中第一种情况就是这么写函数名，可以当成时调用，支持这么写
     * 第二种是调用java的方法时，方法名与kt关键词同名时，jiashahng``可正常调用
     */
    private fun `你在干什么`(name: String, age: Int) {
        println(name + age)
        TabLayoutTestActivity.`in`()
    }

    private fun test04() {
        /**
         * 匿名函数
         */
        val name = "jajdaj"
        println(name.count())
        //此等价与计算内部含有j的个数，it为内部子字符，j，a，j。。。。
        val len = name.count {
            it == 'j'
        }
        println(len)
    }

    private fun test05() {
        //声明函数,输入输出
        val function: () -> String

        //实现函数
        function = {
            val num = 123
            "$num aaaa"
        }

        println(function)
    }

    private fun test06() {
        //声明函数,输入输出
        val function: (Int, String, Int) -> String

        //实现函数,输入参数名在前面定义并用->指向下面（a,b,c ->）
        function = { a, b, c ->
            val num = 123
            "$num aaaa a = $a , b=$b , c = $c"
            //匿名函数最后一行就是返回值，不用写return
        }
        //调用函数
        println(function(22, "vvv", 30))
    }

    /**
     * it关键词，当匿名函数只定义一个输入参数时，自带参数名就为it，此为kt定义的
     */
    private fun testIt() {

        val function: (Int) -> String = {
            "$it what is the it keyword"
        }
        //function.invoke(111)等价于function()都是调用函数
        println(function.invoke(111))
    }

    /**
     * 自动推倒返回值的写法，不用指定返回值，最后一行是什么类型返回值类型就是什么
     */
    private fun testDeng() {
        val function = { name: String, age: Int ->
            "name = $name , age = $age"
        }
        println(function("zs", 23))
    }

    /**
     * 匿名函数==lambda表达式
     * 此函数输入int 返回值为any
     */
    private fun testAny() {
        val funMethhod = { age: Int ->
            when (age) {
                1 -> "this is one"
                2 -> "this is 2"
                3 -> "this is 3"
                4 -> "this is 4"
                else -> 123
            }
        }

        println(funMethhod(2))
    }

    /**
     * 内联函数，在使用函数作为参数的时候使用inline关键词将其变为内联函数，在编译阶段会将函数不作为对象而直接使用，减少
     * 性能损耗
     */
    private inline fun testLogin(name: String, pwd: Int, result: (String, Int) -> Unit) {
        if (name == null || pwd == null) {
            println("name or pwd can not null")
            return
        }
        if (testLoginWeb(name, pwd)) {
            println("login success your name is $name your pwd is $pwd")
            result.invoke("login success", 200)
        } else {
            println("login fail")
            result("login fail", 404)
        }

    }

    private fun testLoginWeb(name: String, pwd: Int): Boolean {
        return name == NAME && pwd == PWD
    }


    /**
     * 具名函数
     */
    private fun testCanShuMethod(msg: String, code: Int) {
        println("the msg is $msg the code is $code")
    }

    /**
     * 返回一个匿名函数
     */
    private fun testReturnMethod(name: String): (name: String, age: Int) -> String {
        println("name is $name")
        return { name: String, age: Int ->
            "name is $name age is $age"
        }
    }

    /**
     * 使用replace实现简单加解密
     */

    private fun testReplace(){
        val pwd = "qqwertyuiopasdfghjklzxcvbnm"

        println("原密码为：$pwd")

        val jiami = pwd.replace(Regex("[abtgh]")) {
            when (it.value) {
                "a" -> "1"
                "b" -> "2"
                "t" -> "3"
                "g" -> "4"
                "h" -> "5"
                else -> it.value
            }
        }

        println("加密后的密码为：$jiami")

        val jiemi = jiami.replace(Regex("[12345]")) {
            when (it.value) {
                "1" -> "a"
                "2" -> "b"
                "3" -> "t"
                "4" -> "g"
                "5" -> "h"
                else -> it.value
            }
        }

        println("解密后的数据为：$jiemi")
    }

    /**
     * 利用let判空操作
     */
    private fun testLetNull(name: String?):String{
        return name?.let {
            "this is it is $it"
        }?:"this is null you alss"
    }

    /**
     * run方法，
     */
    private fun testRun(){
        val s = "Jzw is good man"
        /**
         * 匿名函数，this就是s，最后一行就是返回值，可链式调用
         */
        val run = s.run {
            println(this.length)
            1123
        }
        println(run)

        /**
         * 链式调用，下个run中的this就是上一个run中的最后一行，即返回值
         */
        s.run {
            "aaa"
        }.run {
            println(this)
        }

        /**
         * run中调用具名函数
         */
        s.run (::checkLeth).run (::showResultLeth).run(::println)

    }

    private fun checkLeth(str:String) = str.length>5
    private fun showResultLeth(isEng:Boolean) = if(isEng) "you length is en" else "you length is no en"

    private fun testWith(){
        val s = "what is name"
        /**
         * 具名with
         */
        val with = with(s, ::checkLeth)
        val with1 = with(with, ::showResultLeth)
        println(with1)
        /**
         * 匿名with
         */
        val with2 = with(with(s) {
            this.length > 5
        }) {
            if (this) "you length is en" else "you length is no en"
        }
        println(with2)
    }

    /**
     * also返回值为调用者本身，与allpy类似，不过内部是it不是this
     */
    private fun testAlso(){
        val s = "asdagfsaadsdad"
        val also = s.also {
            println(it.length)
        }.also {
            it.substring(it.indexOf("f"))
        }.also {
            11545
        }
        println(also)
    }

    /**
     * takeif内部返回true或者false，返回true结果返回调用者本身，返回false结果为空
     */
    private fun testTakIf(){
        val name = "zzz"
        val takeIf = name.takeIf {
            true
        }
        println(takeIf)//此结果为zzz

        val takeIf1 = name.takeIf { false }
        println(takeIf1)//此结果为null

        //结合合并操作符
        val s = name.takeIf {
            //逻辑判断。。。。
            false
        } ?: "jzw"
        println(s)
    }

    /**
     * takeUnless与takeif相反，true返回null，false返回值本身
     */
    private fun testTakUnless(){
        val name = "aaaa"
        //一般结合isNullOrBlank()使用，判断是否初始化值
        val s = name.takeUnless { name.isNullOrBlank() } ?: "you prmary is null"
        println(s)
    }

    /**
     * list取数
     */
    private fun testListNotNull(){
        val list = listOf(12,323,15,258)
        //正常取数，会越界
        val i = list[0]
        //此方法取数，越界会返回null
        val orNull = list.getOrNull(1)
        val value = list.getOrNull(55) ?: "你越界了"
    }

    /**
     * list可变不可变
     */
    private fun testListChange(){
        //可变集合,mutableListOf,可对集合进行操作
        val listChange = mutableListOf("jzw","zwj","wzj")
        listChange.add("sda")
        listChange.remove("wzj")
        //不可变集合，listOf，不可对其改变，无add，remove方法
        val listNotChange = listOf(1,2,3)

        /**
         * 可变集合互转
         */
        //可变至不可变
        val toList = listChange.toList()
        //不可变至可变
        val toMutableList = listNotChange.toMutableList()
        toMutableList.add(4)
        toMutableList.remove(1)
    }

    private fun testMutator(){
        val list = mutableListOf("jzw","jzw111","jzw2","jzw33")
        //-= += 相当于add remove操作
        list+="jzwa"
        list-="jzw2"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //按条件删除
            val removeIf = list.removeIf {
                it.contains("111")
            }
            //如果是true即自动遍历集合，全部删除
            list.removeIf { true }

        }

    }

    /**
     * 遍历集合
     */
    private fun testListBianLi(){
        val list = listOf(3,5,47,8,1,5,8)
        //第一种
        for (i in list){
            println(i)
        }
        //第二种
        list.forEach{
            println(it)
        }
        //第三种,带下标
        list.forEachIndexed { index, i ->
            println("下标是 : $index 值是 : $i")
        }
    }

    /**
     * 解构语法，过滤元素
     */
    private fun testJieGou(){
        val list = listOf("xiao","wang","ba","dan")
        //解构
        val (v1,v2,v3,v4) = list
        println("第一个元素是 $v1 , 第二个元素是 $v2 , 第三个元素是 $v3 ,第四个元素是 $v4")
        //_代表此元素不接收，编译期间不会对此读取并赋值，可以优化性能
        val (_,a,b,c) = list
    }

    private fun testSet(){
        //set为不重复集合，重复数据会过滤
        val of = setOf("aaa", "bbb", "ccc", "ddd", "ddd")
        //取数，但是越界会崩溃
        println(of.elementAt(1))
        //越界返回函数内
        println(of.elementAtOrElse(100){
            "越界了"
        })
        //越界返回空
        println(of.elementAtOrNull(100)?:"越界了")

        /**
         * 可变set集合
         */
        val mutableSetOf = mutableSetOf("aaa", "bbb", "ccc")
        mutableSetOf.add("ddd")
        mutableSetOf+="fff"

        /**
         * list set 互转
         */

        val toList = mutableSetOf.toList()

        val toMutableSet = toList.toMutableSet()
        val toSet = toList.toSet()

        //此函数就是去重，内部把list转为mutableSet，再转为list
        val distinct = toList.distinct()

        val aaa = mutableListOf<String>()
    }

    /**
     * 数组测试
     * intArrayOf()
     * charArrayOf()
     * doubleArrayOf()
     * shortArrayOf()
     * ....
     */
    private fun testArray(){
        //定义
        val intArrayOf = intArrayOf(2, 385, 748, 3)
        //取数,会数组越界
        println(intArrayOf[2])
        println(intArrayOf.elementAt(2))

        //越界返回-1
        println(intArrayOf.elementAtOrElse(2){ -1 })

        println(intArrayOf.elementAtOrNull(2)?:"越界了小老弟")

        //list转数组
        val listOf = listOf(21, 21, 21, 5, 22)
        val toIntArray = listOf.toIntArray()
        val arrayOf = arrayOf<File>(File("adasd"))
    }

    private fun testMap(){
        //创建map方式1  key-》"a" value-》 to 123 此为中缀表达式
        val mapOf = mapOf("a" to 123, "b" to 321)
        //创建map方式二
        val mapOf1 = mapOf(Pair("a", 1))
        //创建map方式三
        val mapOf2 = mapOf("a".to(123), "b".to(232))

        //map取数
        //这两个等价，找不到返回null
        println(mapOf["a"])
        println(mapOf.get("a"))
        //这个找不到会奔溃
        println(mapOf.getValue("a"))
        //此找不到返回1123，默认值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            println(mapOf.getOrDefault("a",1123))
        }
        //找不到返回labma内容
        println(mapOf.getOrElse("a"){
            1111
        })

        /**
         * 遍历map
         */
        for (entry in mapOf) {
            println("key is ${entry.key} the value is ${entry.value}")
        }

        mapOf.forEach {
            println("key is ${it.key} the value is ${it.value}")
        }

        mapOf.forEach{(k,v)->
            println("key is $k the value is $v")
        }

        /**
         * 可变map，添加方法
         */
        val mutableMapOf = mutableMapOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        mutableMapOf += "f" to(5)
        mutableMapOf -= "a"
        //这两个添加方法等价
        mutableMapOf["dd"] = 45
        mutableMapOf.put("asd",66)

        //此方法是当map里有这个key的值时直接返回对应的值，如果没有就先添加再返回值
        val orPut = mutableMapOf.getOrPut("aaa") { 303 }
        println(orPut)
    }


    private fun testFiled(){
        //调用的其实时TestFiledAnd().getname
        println(TestFiledAnd().name)
        //会返回随机数
        println(TestFiledAnd().age)

        val testSecondGouZao = TestSecondGouZao("zs")
        val testSecondGouZao1 = TestSecondGouZao("aaa", 15)
    }
    /**
     * Filed为转化自带参数，在定义一个参数时，去外部调用的时候，其实内部调用的时get set函数，filed为其值
     * field等价与name
     */
    class TestFiledAnd(){
        var name = "aaa"
            //我们可以修改默认函数，达到想要的效果
        set(value) {
            field = "[$value]"
        }
        get() = field.capitalize()

        //只读参数就没有set函数，只有get
        val age :Int
        //可以使用这样的方式赋值，此方法为1-100内随机数第一个
        get() = (1..100).shuffled().first()
    }

    /**
     * 主构造函数参数写在类后面的括号里，一般前面加_区分，不能直接用，时引用对象，需要在类中定义参数接收它
     */
    class TestZhuGouzao(_name:String,_age:Int){
        var name = _name
            //get不能私有，set可以,set私有就无法修改值，和val一样了
        get() = field
        private set(value) {
            field = value
        }
        val age = _age
        fun show(){
            println(age)
            println(name)
        }
    }

    /**
     * 主构造函数可以直接定义输入参数，达到不需要接收参数的条件
     */
    class TestZGouzao(var _name: String,val _age: Int){
        fun show(){
            println(_name)
            println(_age)
        }
    }

    /**
     * 次构造函数，定义时必须调用主构造函数，统一管理次构造函数，因为次构造函数可以无限定义
     */

    class TestSecondGouZao(_name: String){
        //: this(name)就是调用主构造函数了,在使用次构造函数的时候，会先调用主构造函数，再调用次构造函数里的逻辑
        constructor(name: String,age: Int) : this(name) {
          println("name is $name age is $age")
        }

        constructor(name: String,age: Int,sex:Char) : this(name){
            println("name is $name age is $age sex is $sex")
        }
    }

    /**
     * 设置构造函数默认值，如果都设置了默认值，调用此类时优先为主构造函数
     */
    class TestMoRenGouZao(_name: String = "jzw",_age: Int = 18){
        /**
         * 初始化代码块，调用构造函数时会被调用,在此方法种可以调用到主构造函数中的引用参数
         */
        init {
            println("name is $_name the age is $_age" )
        }
        constructor(name: String = "aaa",age: Int = 19,sex: Char = '男'): this(){

        }
    }

    /**
     * 延迟初始化，类似懒加载，可以先不赋值
     */
    lateinit var requestResult : String

    private fun loadRequest(){
        requestResult = "获取到值了哦"
    }

    private fun testLateninit(){
        //如果没有先调用loadRequest，会崩溃

        //判断是否初始化
        if(::requestResult.isInitialized){
            println("requestResult : $requestResult")
        }else{
            println("你没有初始化")
        }
    }

    /**
     * 惰性加载，即懒加载
     */
    class TestByLazyClass{
        //不使用惰性加载
        val date1 = getData()

        //使用惰性加载
        val date2 by lazy { getData() }

        private fun getData() : String {
            println("开始加载数据....")
            println("加载数据中....")
            println("加载数据中....")
            println("加载数据中....")
            println("加载数据中....")
            println("加载数据中....")
            println("加载数据中....")
            println("加载数据结束....")
            return "the date is load finish"
        }
    }

    /**
     * 调用惰性加载
     */
    private fun testByLazy(){
        val testByLazyClass = TestByLazyClass()
        Thread.sleep(5000)
        println(testByLazyClass.date1)
        println(testByLazyClass.date2)
    }

    /**
     * kt默认类都是final修饰的，所有不能被继承，需要用到open关键字，移除final修饰符
     */
    open class Father(_name: String){
        val name = "父类的名字是$_name"

        //方法也需要才能被重写
       open fun prltenName() = println(name)
    }
    //继承类
    class Son(_sname:String) : Father(_sname){
        val sname = "子类的名字是$_sname"

        override fun prltenName() {
            super.prltenName()
            println(sname)
        }
    }

    /**
     * is 判断是否是给定类型，as转换类型
     */
    private fun testIsAs(){
        val fa : Father = Son("zz")
        if(fa is Father){
            val father = fa as Son
            father.prltenName()
        }
        if(fa is Son){
            //智能类型转换，转过一次后，编译器记住了，以后调用都是转换过的类型
            val son = fa as Father
            son.prltenName()
        }
    }

    /**
     * object修饰的类，相当于变成了单例，编译会废除主构造函数，即空构造函数，init代码块会在static代码块中实现，并且初始化
     * 单例的该类对象
     */
    object TestObjectClass{
        init {
            println("this is init")
        }

        fun show(){
            println("this is show")
        }
    }

    /**
     * 对象表达式，可以用object关键字，实现接口，和匿名对象
     */
    private fun testBiaoDaShi(){
        //匿名对象实现
        val value = object : Father("jzw") {
            override fun prltenName() {
                super.prltenName()
                println("this is object name is $name")
            }
        }
        value.prltenName()

        //实现java的接口
        val runnable = object : Runnable {
            override fun run() {
                println("this is Runnable run...")
            }
        }
        runnable.run()
        //java接口还有简单方法
        val runnable1 = Runnable {
            println("runing")
        }
        runnable1.run()

        //kt接口就只能object实现
        object : KtRun{
            override fun run() {
                println("KtRun runing")
            }
        }.run()
    }

    //kt 接口
    interface KtRun{
        fun run()
    }

    /**
     * 伴生对象，companion object,kt中无Java的static，使用此定义的代码块内的对象和函数，使用上类似Java的static
     * 本质是在初始化对象的时候初始化了一次companion，静态代码块，调用的时候调用该静态对象
     */

    class TestCompanionClass{
        companion object{
            val name = "aaaa"
            fun show(){
                println("this is show")
            }
        }

        val age = 123

        fun show2(){
            println("this is show2")
        }
    }

    /**
     * inner,内部类关键词，使类成为内部类，就可以调用外部类的成员变量和函数，外部类也可以调用内部类的成员变量和函数
     */
    val testInner = "testInner"
    inner class TestInnerClass(){
        fun show(){
            //可以获取外部成员被变量
            println("i can get the testInner : $testInner")
        }
        //可以获取外部方法
        fun show2() = test03()
    }

    /**
     * 数据类，关键词data修饰，普通类默认继承any，调用时会调用到父类any的方法，any中的方法又是交由各个调用平台实现
     * 而data修饰的类会重写any中的方法，调用时就是子类的方法了
     */
    data class TestDataClass(val name: String,val age: Int,val sex: Char)

    /**
     * copy，克隆类
     */
    data class TestDataCopyClass(val name: String,val age: Int){
        lateinit var what :String

        init {
            println("主构造被调用")
        }
        constructor(name: String):this(name,19){
            what = "aaa"
            println("次构造被调用")
        }

        //如果不重写，打印只会打印出主构造中的两个参数，而不会打印出what
        override fun toString(): String {
            return "name : $name age : $age what : $what"
        }
    }

    /**
     * 解构类，date声明的数据类自带解构，普通类可以定义成为解构
     */
    class testJieGouClass(val name: String,val age: Int,val sex:String){
        //解构关键方法，注意顺序必须时123下来
        operator fun component1() = name
        operator fun component2() = age
        operator fun component3() = sex
    }

    /**
     * 运算符重载
     */
    data class TestYunSuan(val number1:Int,val number2:Int){
        //puls代表重载+运算符
        operator fun plus(p1:TestYunSuan):Int{
            return number1+p1.number1+number2+p1.number2
        }

        //operator fun TestYunSuan. 这种方式可以查看所有可重载的运算符
    }

    /**
     * 枚举类型，是一个类，枚举的值等于定义本身,;分号结束
     */
    enum class TestEnumClass{
        礼拜一,
        礼拜二,
        礼拜三,
        礼拜四,
        礼拜五,
        礼拜六,
        礼拜日;
    }

    data class UseToEnum(val name: String,val age: Int)
    /**
     * 使用其他类做参数定义枚举类,在调用枚举的时候，会执行主构造，传入参数，所以枚举类型的参数必须与主构造参数对应
     */
    enum class TestUseClassEnum(private val use:UseToEnum){
        zhangsan(UseToEnum("张三",18)),
        lisi(UseToEnum("李四",22)),
        wangwu(UseToEnum("王五",25)),
        sad(UseToEnum("萨德",27));

        fun show(){
            println("name is ${use.name} age is ${use.age}")
        }
    }

    /**
     * 代数数据类型
     */
    enum class ScorEnum{
        top,
        mid,
        low,
        tolow,
    }

    class TestDaiShuClss(private val scor:ScorEnum){
        //因为枚举类型已经确定所以不需要写else了
        fun show() = when(scor){
            ScorEnum.top-> "你的分数很好"
            ScorEnum.mid-> "你的分数中等"
            ScorEnum.low-> "你的分数不好"
            ScorEnum.tolow-> "你的分数太不好了"
        }
    }

    /**
     * 密封类sealed关键字修饰，密封类其实是一种特殊的抽象类，专门用于派生子类的。
     * 密封类的子类是固定的
     *密封类的直接子类必须和密封类在同一个文件中
     *密封类间接的子类可以在不同文件中
     *密封类所有的构造方法都是private的
     *如下程序定义了一个密封类和两个子类：
     */
    sealed class TestSealedClass{
        //必须继承自身
        object sealed1 : TestSealedClass()
        object sealed2 : TestSealedClass()
        object sealed3 : TestSealedClass()
        class imgood(val name: String) : TestSealedClass()
    }

    class TestSealed(val aa:TestSealedClass){
        fun show() = when(aa){
             is TestSealedClass.sealed1 -> "this is sealed1"
             is TestSealedClass.sealed2 -> "this is sealed2"
             is TestSealedClass.sealed3 -> "this is sealed3"
             is TestSealedClass.imgood -> "this is imgood ${this.aa.name}"
        }
    }

    /**
     * 定义接口，在接口中定义了参数，实现接口时也要实现，主构造传入或者重写参数的方式
     */
    interface TestInterface{
        var name:String
        var age:Int
        fun show() : String
    }
    //第一种实现接口方式，参数作为主构造参数
    class TestInterfaceClass(override var name: String, override var age: Int) : TestInterface{
        override fun show()  = "name is $name age is $age"
    }
    //第二种实现接口的方式，参数作为成员变量
    class TestInterfaceClass2 : TestInterface{
        override var name: String = "zs"
            get() {
                println("get the name vaule")
                return field
            }
            set(value) {
                field = value
                println("set the name vaule")
            }
        override var age: Int = 18
            get() {
                println("get the age vaule")
                return field
            }
            set(value) {
                println("set the age vaule")
                field = value
            }

        override fun show(): String = "name is $name age is $age"
    }

    /**
     * 接口参数可以定义默认值，即实现其getset方法，但是一般不这么做
     */
    interface TestMoRenInterFace{
        val name : String
        get() = "zs"
    }

    /**
     * 抽象类，和java相同
     */
    abstract class BaseActivity{
        fun onCreate(){
            initView()
            initAction()
            initDate()
        }
        abstract fun getLayout():Int
        abstract fun initView()
        abstract fun initDate()
        abstract fun initAction()
    }

    class MainAcyiviy : BaseActivity() {
        override fun getLayout(): Int {
            TODO("Not yet implemented")
        }

        override fun initView() {
            TODO("Not yet implemented")
        }

        override fun initDate() {
            TODO("Not yet implemented")
        }

        override fun initAction() {
            TODO("Not yet implemented")
        }

    }

    /**
     * 泛型类
     */
    class TestFanxinClass<T>(val obj:T){
        fun show() = "the vaule is $obj"
    }

    /**
     * 泛型类型转换
     */
    class TestFanZhuan<T> (val isMap:Boolean = true,val vaule: T){
        //写一个lambada，返回值最后一行即为结果，就可以转换类型了,注意，使用泛型方法需定义成内联函数
        inline fun<O> map(lambdaMethod:(T)->O) = lambdaMethod(vaule)
    }

    /**
     * 泛型类型转换方法
     */
    inline fun<T,O> fanmap(isMap: Boolean = true, value: T, method : (T) -> O) = method(value)

    data class Persondata(val name: String,val age: Int)
    data class Students(val name: String,val age: Int)

    /**
     * 泛型的限定,T : Persondata 想当于Java的 T extend Persondata 限定泛型类型为Persondata与其子类
     */
    fun<T : Persondata> fanxingxianding(){

    }

    /**
     *  vararg 动态参数关键词，和Java种得..一样，可以传入多个参数
     */
    class TestVarargClass<T> (vararg values:T){
        //接受临时参数,out是定义为只读得意思
        val arrays : Array<out T> = values

        //参数类型转换
        fun<O> changMap(index:Int,method : (T) -> O) = method(arrays[index])
        //输出参数
        fun show(index: Int) : T{
            return arrays[index]
        }

        //重载[]运算符
        operator fun get(index: Int) = arrays[index]


    }

    /**
     *kt的逆变与协变
     * 逆变：关键词in，相当于Java种的super
     * 协变：关键词out，相当于Java中的extend
     */
    open class Food{

    }
    open class Meat : Food(){

    }
    open class Fuit : Food() {

    }
    open class Apple : Fuit(){

    }
    open class Banna : Fuit(){

    }

    class ProductFood : TestOutClass<Food>(){

    }
    class ProductMeat : TestOutClass<Meat>(){

    }
    class ProductFuit : TestOutClass<Fuit>(){

    }
    class ProductApple : TestOutClass<Apple>(){

    }
    class ProductBanna : TestOutClass<Banna>(){

    }

    class ProductFood1 : TestInClass<Food>(){

    }
    class ProductMeat1 : TestInClass<Meat>(){

    }
    class ProductFuit1 : TestInClass<Fuit>(){

    }
    class ProductApple1 : TestInClass<Apple>(){

    }
    class ProductBanna1 : TestInClass<Banna>(){

    }

    /**
     * 协变类
     */
    open class TestOutClass<out T>{
    }

    /**
     * 逆变类
     */
    open class TestInClass<in T>{
        fun c(item:T){

        }
    }

    fun testInOut(){
        //协变，子类可以赋值给父类，Java中的？extend T
        val productFood : TestOutClass<Food> = ProductFood()
        val ProductMeat : TestOutClass<Food> = ProductMeat()
        val ProductFuit : TestOutClass<Food> = ProductFuit()
        val ProductApple : TestOutClass<Food> = ProductApple()
        val ProductBanna : TestOutClass<Food> = ProductBanna()
        //逆变，父类可以赋值给子类，逆着来，Java中的？super T
        val productFood1 : TestInClass<Apple> = ProductFood1()
        val productFood2 : TestInClass<Apple> = ProductFuit1()
    }

    /**
     * reified关键词，当需要用泛型类型is判断时，需加上此关键词
     */
    inline fun <reified T> testReified(noinline method:()->T) : T {
        val listOf = listOf(Persondata("jzw", 18), Students("zzz", 22), UseToEnum("aaa", 25))
        val first = listOf.shuffled().first()
        //判断是否为传入的类型
        return (first.takeIf { it is T }?:method) as T
    }

    /**
     * kt扩展函数，可以在所有类中添加扩展函数，此为kt亮点
     */
    //为food类扩展了show函数
    fun Food.show(){
        println("this is food class add show")
    }
    //还可以为jdk，或者开源库扩展函数
    fun Glide.show(){
        println("this is Glide class add show")
    }
    fun StringBuilder.show(){
        println("this is StringBuilder class add show")
    }

    /**
     * 给超类定义扩展函数，所有都可以用了
     */
    fun Any.chaoleishow() = "this is any s show"
    //扩展函数可链式调用,返回其本身即可
    fun Food.lianshi() : Food{
        return this
    }

    /**
     * 泛型类扩展函数，所有类型都是泛型，所以扩展函数所有类都可以用
     */

    fun<T> T.fanxinshow(){
        println("this is fanxing s $this show")
    }
    private fun testKouZan(){
        //调用扩展函数
        Food().show()
        java.lang.StringBuilder().show()
        //调用超类的扩展函数,所有类都可以调到了
        println(Food().chaoleishow())
        println(Persondata("jzw",22).chaoleishow())
        //调用扩展函数的链式调用
        Food().lianshi().lianshi().lianshi()
        "asda".chaoleishow()
        'a'.chaoleishow()
        //调用泛型扩展方法
        123.fanxinshow()
        "asd".fanxinshow()
        Food().fanxinshow()

    }

    /**
     * 模拟let
     */
    private fun<T,O> T.testLambada(lambada:(T)->O) = lambada(this)

    /**
     * 扩展属性,必须如下方式写出
     */
    val String.myInfo : String
    get() = "jzw"

    val String.myage : Int
    get() = 18

    /**
     * 对可空类型进行扩展
     */
    //如果为空返回默认值
    fun String?.showNullDefat(defat:String) = println(this?:defat)

    /**
     * 自定义中缀表达式+扩展函数,中缀表达式关键词 infix
     * 1.需要对第一个参数进行扩展c1
     * 2.需要传参第二个参数
     */
    infix fun <C1,C2> C1.zzkz(value:C2){
        println(value)
    }

    /**
     * 模拟apply,返回本身，内部持有this，this是调用者本身
     */
    fun <T> T.myApply(method : T.() -> Unit) : T{
        method()//省略this，可以写成 method(this)，但是lambada默认持有this了，因为T.()
        return this
    }

    /**
     * DSL 是领域专用语言的意思，就是你自己定义一种规范，别人去调用的时候需要遵循这种规范，比如json，gilde
     */
    class MyDSL(val name: String,val str: String){

        fun MyDSL.myapply(mehtod : MyDSL.(String)->Unit) : MyDSL{
            mehtod(name)
            return this
        }
    }

    fun MyDSL.twoAllpy(method : MyDSL.(String,String?) -> Unit) : MyDSL{
        method(name,str)
        return this
    }

    /**
     * kt 的map函数，和rxjava的一样，最后一行为返回值
     */
    fun testMapp(){
        val listOf = listOf("jzw", "aaa", "xxx")
        val map = listOf.map {
            "[$it]"
        }.map {
            "$it the length is ${it.length}"
        }.map {
            println(it)
            //即使没有返回值，内部也会构建一个空的list作为返回map
        }
    }

    /**
     * kt flatMap  遍历所有的元素 ，为每一个创建一个集合 ，最后把所有的集合放在一个集合中。
     * flatMap的返回值必须是Iterable，map 则没有这个要求
     */

    fun testFlatMap(){
        val listOf = listOf("jzw", "aaa", "xxx")
        val flatMap = listOf.flatMap {
            "<$it>".toList()
        }.map {
            println(it)
        }

        println(flatMap)

    }

    /**
     * filter 过滤关键词，使用时最后一行返回一个Boolean值，如果为true，则加入集合，flase不加入，可以用次判断是否入集合
     */
    fun testFilter(){
        val listOf = listOf(listOf("jzw", "zzz", "xxx"), listOf("aaa", "sss", "ddd"), listOf("qqq", "www", "eee"))
        val flatMap = listOf.flatMap { it ->
            it.filter {
                println(it)
                it.contains("z")
            }
        }
        println(flatMap)
        flatMap.map {
            println(it)
        }
    }

    /**
     * kt的合并函数zip关键词，可将两个集合合并成一个集合类型为list<pair<key,value>> ，第一个集合为key，第二个集合为value
     */

    @RequiresApi(Build.VERSION_CODES.N)
    fun testZip(){
        val namelist = listOf("jzw", "king", "mk")
        val agelist = listOf(18, 22, 25)
        val zip = namelist.zip(agelist)
        println(zip)
        //可将合并的集合转化为map，list，set等
        val toList = zip.toList()
        val toMutableList = zip.toMutableList()
        val toMap = zip.toMap()
        val toMutableStateMap = zip.toMutableStateMap()
        val toSet = zip.toSet()
        val toMutableSet = zip.toMutableSet()
        val toHashSet = zip.toHashSet()

        toList.forEach {
            println("name is ${it.first} age is ${it.second}")
        }

        toMap.forEach { (t, u) ->
            println("name is ${t} age is ${u}")
        }

        toMap.forEach {
            println("name is ${it.key} age is ${it.value}")
        }
    }

    /**
     * kt中的单例模式
     */
    //饿汉式,object
    object TestEHan
    //懒汉式
    class TestLanHan{
        //静态代码块
        companion object{
            private val  testLanHan :TestLanHan? = null
            get() = field ?: TestLanHan()

            fun getInstance() = testLanHan!!
        }

        fun show() = println("this is lanhan show")
    }

    //安全的懒汉单例，和Java中的volatile + synchronized
    class TestSafeLanHan private  constructor(){
        companion object{
            val instance : TestSafeLanHan by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                TestSafeLanHan()
            }
        }

        fun show() = println("this is safe show")
    }

    /**
     * lambada最后一行就为返回值
     */
    fun<out> whatsas(mehtod:()->out): out {
        return mehtod()
    }

    /**
     * 手写rxjava的操作符，create，observ，当你需要输入内部变化的最后一行为其返回值，并且不断流向下一个，你就可以定义
     * 一泛型类，传入泛型参数的lambda，作为返回值，增加扩展函数，返回值为其类本身，这样就可以链式调用了
     */
    class RxJavaTestClass<I>(val inputValue:I)

    //扩展该类函数，map
    fun<I,O> RxJavaTestClass<I>.rxMap(mapMthod : I.() -> O) = RxJavaTestClass(mapMthod)

    //create函数，开启
    fun<I> create(mehtod: () -> I) = RxJavaTestClass(mehtod)
    //写一个输出的observ

    fun <I> RxJavaTestClass<I>.observe(obMethod : (I)->Unit) = obMethod(inputValue)

    //声明与实现结合的lambada
    val aamehtod : () -> String = fun() = ""

    fun testLanmads(){
        //声明lambada
        val bbmethod : (Int) -> String
        //实现lambada
        bbmethod = fun(num) = num.toString()
        //调用lambada
        println(bbmethod.invoke(123))

        //声明+实现方法2，传入两个参数或以上需要自定义参数名name,age->
        val ccmethod : (String,Int)->String = {name,age-> "name is $name age is $age"}

        //单个参数传入默认为it
        val ddmethod : (String)->Unit = {
            println("your insert vaule is $it")
        }
        //单个参数也可以变为this传入，方法如下,变成了扩展函数，string类型都可以调到它,扩展函数的本质就是你对谁扩展，this就等于谁本身
        val thismethod : String.() -> Unit = {
            println("your insert vaule is $this")
        }
        "".thismethod()

        val ffmethod = {name:String->
            "what is $name"
        }

        println(ffmethod("jzw"))

    }

    fun asdasasd(mehtod: (String)->String) = mehtod("")

    fun<T> T.fanmethod(mm : T.()->Boolean) = mm()

    //泛型this，就等于本身
    fun<T> T.fanmethod2(mm : T.(T,T,T)->Boolean) = mm(this,this,this)

    fun testadasd() = {name:String->
        "what is $name"
    }

    fun diaoyong() {

        val invoke = testadasd().invoke("jzw")

        //调用手写的rxjava
        create {
            "jzw"
        }.rxMap {
            "[$this]"
        }.rxMap {
            "**$this**"
        }.observe {
            println(it)
        }
        val whatsas = whatsas {
            123
            "adasd"
        }
        //调用安全懒汉单例
        TestSafeLanHan.instance.show()
        //调用懒汉式单例
        TestLanHan.getInstance().show()
        ///调用DSL
        val myDSL = MyDSL("jzw", "what")
        val myApply1 = myDSL.myApply {
            println("the this vaule is $this")
            123
            true
        }.myApply {

        }
        val twoAllpy = myDSL.twoAllpy { name, str ->
            println("the name is $name the str is $str")
            println("the this is $this")
        }.twoAllpy { aaa, bbbb ->

        }
        //调用自定义的apply
        val myApply = "jzw".myApply {
            println(this)
        }
        println(myApply)
        //调用扩展文件
        val listOf = listOf("zzz", "xxx", "ccc")
        listOf.randomValuePrl()
        println(listOf.aa())
        //调用中缀表达式扩展函数
        "aaa".zzkz(123)//普通调用
        "aaa" zzkz 123//中缀调用
        //调用可控函数扩展
        val canbenull : String? = null
        canbenull.showNullDefat("your values is null o")
        //调用扩展属性
        println("aaa".myInfo.myage)
        //调用模拟let
        val testLambada = "jzw".testLambada {
            println("this vaule is $it")
            123
        }
        println("this return is $testLambada")
        //调用reified关键词测试函数,比如说我要studnets类型的
        val testReified = testReified<Students> {
            println("you do not get what i want")
            Students("someone", 27)
        }
        println(testReified.toString())
        //调用动态参数类
        val testVarargClass = TestVarargClass("aaa", 123, 'c', "ddd", null)
        println(testVarargClass.show(3))
        val changMap = testVarargClass.changMap(2) {
            "the vaule is $it"
        }
        println(changMap)
        println(testVarargClass[2])
        //调用泛型类型转换
        val map = TestFanZhuan(true, 123).map {
            it.toString()
        }
        //调用泛型转换方法
        fanmap(true,Persondata("zs",18)){
            Students(it.name,it.age)
        }
        println(map)
        //调用泛型类
        println(TestFanxinClass(123).show())
        println(TestFanxinClass("asdasdasd").show())
        println(TestFanxinClass(123.1545).show())
        println(TestFanxinClass(TestDataClass("jzw",18,'a')).show())
        //调用实现接口的类
        println(TestInterfaceClass("jzw",18).show())
        val testInterfaceClass2 = TestInterfaceClass2()
        println(testInterfaceClass2.show())
        testInterfaceClass2.age = 22
        println(testInterfaceClass2.age)

        //调用密封类
        println(TestSealed(TestSealedClass.imgood("jzw")))
        //调用代数函数
        println(TestDaiShuClss(ScorEnum.low).show())
        /**
         * 调用类做参数的枚举
         */
        println(TestUseClassEnum.sad.show())
        println(TestUseClassEnum.lisi.show())
        /**
         * 调用枚举
         */
        //输出值就是本身，礼拜一
        println(TestEnumClass.礼拜一)
        //为true，枚举等价于本身
        println(TestEnumClass.礼拜一 is TestEnumClass)
        /**
         * 运算符重载，重载了+方法，调用的时候就可以直接用+了
         */
        println(TestYunSuan(1,2)+TestYunSuan(3,4))
        /**
         * 普通类解构调用
         */
        val (a1,a2,a3) = testJieGouClass("jzw",12,"nan")
        println("name is $a1 age is $a2 sex is $a3")
        /**
         * 调用copy
         */
        val testDataCopyClass = TestDataCopyClass("zs")
        println(testDataCopyClass)
        val copy = testDataCopyClass.copy()
        //此时打印的也没有what的值，因为copy也会忽略次构造,只处理主构造
        println(copy)
        /**
         * 调用数据类时，打印的时候就是子类重写的方法，会打印数据
         */
        val testDataClass = TestDataClass("jzw", 18, 'N')
        println(testDataClass)
        //值比较，会返回ture，如果不是data修饰的会返回false
        println(TestDataClass("jzw", 18, 'N')==TestDataClass("jzw", 18, 'N'))

        //调用companion测试类
        println(TestCompanionClass.name)
        TestCompanionClass.show()

        //调用内部类
        val testInnerClass = TestInnerClass()
        testInnerClass.show()

        //调用objec类
        println(TestObjectClass.show())
        //单例对象，不管调用多少次都是同一个对象
        println(TestObjectClass)
        println(TestObjectClass)
        println(TestObjectClass)

        test01(18)
        test01(17, "lisi")
        //当参数过多时，kt可以在调用时指定传入的参数，可不按照参数顺序传入
        test02(id = 2, name = "zs", firstname = "z", age = 18)
        //调用``修饰的方法
        你在干什么("aaa", 11)

        testLogin("jzw", 123456) { msg, code ->
            println("the msg is $msg the code is $code")
        }

        testLogin("aa", 123, result = { msg, code ->
            println("the msg is $msg the code is $code")
        })

        val resMethod = { msg: String, code: Int ->
            println("the msg is $msg the code is $code")
        }

        testLogin("jzw", 123456, resMethod)

        /**
         * lambad属于函数类型对象。所以需要把函数变成函数类型对象即函数引用，加：：
         */
        val oobj = ::testCanShuMethod
        testLogin("jzw", 123456, oobj)
        testLogin("jzw", 123456, ::testCanShuMethod)

        /**
         * 返回值为函数，可以调用
         */
        val testReturnMethod = testReturnMethod("jzw")
        println(testReturnMethod.invoke("aaa",33))

        /**
         * 默认不能为null，但是加了?就可以赋值为null
         */
        var notNull : String ? = "aaa"
        notNull = null
        /**
         * 因为name是可空类型，所以在操作的时候要加上?或者判空处理才能调用方法，？的意思是，如果为空，？后面的方法都不执行
         */
//        notNull.capitalize()
        notNull?.capitalize()

        /**
         * let为内部函数，里面的参数就为notnull，可以在里面进行操作，返回等
         */
        val let = notNull?.let {
            if(it.isNotBlank()){
                it
            }else{
                "defalt"
            }
        }

        /**
         * 断言，即！！，不管为不为空都执行，即可能会空指针异常
         */
        notNull!!.capitalize()

        /**
         * 空合并操作符,?:,当此数据为空时会执行:后面的
         */
        println(notNull?:"you are null")
        //结合let
        println(notNull?.let { "[$it]" }?:"you are null")

        try {
            notNull?:throw nullExcpitions()
            println(notNull!!.capitalize())
        }catch (e:Exception){
            println("cathch $e")
        }

        /**
         * 先决条件函数，判断并抛异常
         */
        //判断是否为空，为空抛出异常
        checkNotNull(notNull)
        requireNotNull(notNull)
        var isSuccess:Boolean = false
        //如果为false抛出异常，true正常执行
        require(isSuccess)

        /**
         * substring
         */
        val ss = "jzw is good"
        //返回到i的字符长度
        var index = ss.indexOf('i')
        println(ss.substring(0,index))
        //0 until index就是0-xxx等价于上面的表达式
        println(ss.substring(0 until index))

        /**
         * split操作符
         */
        var ssss = "jzw,zwj,wzj,jjj"
        val split = ssss.split(",")
        println("list is $split")
        //解构操作
        val (v1,v2,v3,v4) = split
        println("v1 is $v1 v2 is $v2 v3 is $v3 v4 is $v4")

        /**
         * 值比较==
         * 引用比较===
         */
        val name1 = "Jzw"
        val name2 = "Jzw"
        val name3 = name1

        println(name1 == name2)
        println(name1===name3)

        val name4 = "jzw".capitalize()
        println(name1===name4)

        /**
         * string的foreach，遍历字符串
         */
        val fore = "asdnkahsjkdhkajhdwkjd"
        fore.forEach {
            println(it)
        }

        /**
         * 转换类型安全问题
         */

        val numbber = "666.6"
        //numbber.toInt(),会报错，因为时double
        //这个不会报错，会返回null
        numbber.toIntOrNull()

        /**
         * double与int类型转换
         */
        val toInt = 65.6515454.toInt()
        //此函数保证四舍五入效果
        val roundToInt = 65.6515454.roundToInt()
        //保留三位小数,stirng类型
        val format = "%.3f".format(65.6515454)

        /**
         * apply函数，返回的时调用函数的对象本身，可链式调用
         */
        val applyss = "Jzw is Great"
        println(applyss.length)
        println(applyss[applyss.length-1])
        println(applyss.toLowerCase())
        //用apply实现上述调用，apply函数内部，持有this，就是对象本身
        applyss.apply {
            println(this)//结果为"Jzw is Great"
            println(length)
            println(this[this.length-1])
            println(this.toLowerCase())
            println(toLowerCase())
        }
        //或者链式调用
        val apply = applyss.apply {
            println(length)
        }.apply {
            println(this[this.length - 1])
        }.apply {
            println(toLowerCase())
        }
        //返回对象就是"Jzw is Great"
        println(apply)

        /**
         * let函数，返回值为最后一行，内部it，对象为调用此函数的对象
         */
        val lista  = listOf(5,6,2,1,8)
        val first = lista.first()
        println(first+first)

        val let1 = lista.let {
            println(it.first() + it.first())
            "aaa"
        }
        println(let1)//结果为aaa

        testLetNull("jjjjzw")

    }

    fun testXieCheng(){
        //开启携程默认是子线程
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            //在此做子线程需要做的事儿或者跟新ui
            //如果开启子线程，会挂起线程，做完了在回来执行下面的代码
            /**
             * 这相当于在其内部实现了一个接口回调式的对象，在反编译为Java字节码的时候会加上一个叫continution的类，此类
             * 就是为了挂起后返回执行后续代码的类，在携程内部其实是维护了一个状态机，每一个挂起线程都有个编号，依次往下
             * 执行showCoroutine方法，该方法内部开启一个while true 死循环，执行第一个时将状态机置为下一个节点的值，当执行完第一个代码后，
             * 最后会调用invokesuspend函数，内部继续调用showCoroutine执行下一个状态机的代码，直到执行结束
             */

        }

    }



    /**
     * 自定义异常捕获
     */
    class nullExcpitions : IllegalArgumentException("你的代码有异常")

}