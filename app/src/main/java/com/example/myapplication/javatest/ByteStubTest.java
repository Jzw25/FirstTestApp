package com.example.myapplication.javatest;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 字节码插桩测试
 */
public class ByteStubTest {
   /**
    * hdpi下chengxujieshuqi描述了程序计数器的意义，即cpu在执行程序时是时间片轮转的，将一个周期分割成多个时间片，每个
    * 时间片时间固定，多个线程抢占cpu时间片，当一个时间片内线程内程序执行不完，下次抢占到后需要从之前位置开始执行程序计数器
    * 的意义就是记录执行位置（记录程序运行的地址）
    *
    * 栈帧：hdpi下zhanzheng，即程序执行方法时开辟的内存空间，用于存放执行方法
    * 递归oom也是因为栈帧压入方法过多导致栈溢出
    */

   /**
    * Android回crash（崩溃）的原因：Android的main函数中（RuntimeInit类的main（）方法），调用了commonInit（）
    * 方法，该方法调用了Thread.setDefaultUncaughtExceptionHandler(new KillApplicationHandler(loggingHandler));
    * 方法，KillApplicationHandler中，杀死线程，弹出崩溃弹窗，杀死app
    *
    * 所以：当异常KillApplicationHandler捕获到异常，进行完一系列处理（主要是打印crash日志，通知AMS展示crash弹窗等）
    * 后，最终会杀死进程，这样你的app就崩溃了。
    *
    * android异常捕获机制：
    * Thread.setCaughtExceptionPreHandler()覆盖所有线程，会在回调DefaultExceptionHandler之前调用；
    * Thread.setCaughtExceptionHandler()同样回覆盖所有线程，可以在应用层被重复调用，并且每一次调用后，
    * 都会覆盖上一次设置的DefaultUncaughtExceptionHandler；
    * Thread.currentThread.setUncaughtExceptionHandler()，只可以覆盖当前线程的异常。如果某个线程存在自定义的
    * UncaughtExceptionHandler，回调时会忽略全局的DefaultUncaughtHandler。
    *
    * 要想不crash，只能让线程不要抛出exception，唯此别无他法。如果我们能把一个线程的所有的操作都使用try-catch进行保护
    * ，理论上，就能做到app never crash。由于android基于Handler事件驱动的机制，可以在app启动时，向主线程中的
    * MessageQueue中提交一个死循环操作，在这个死循环中不断去poll事件，并且将这个死循环进行try-catch，这样所有主线程中
    * 的异常都会被catch住，从而app就再也不会发生crash。
    */

   public void neverCrash(){
       Handler handler = new Handler();
       handler.post(new Runnable() {
           @Override
           public void run() {
               while (true){
                   try {
                       Looper.loop();
                   }catch (Exception e){
                   }
               }
           }
       });
   }

    /**
     * Android布局嵌套多少回crash？
     * 所有的view都是通过反射造出来的，所有自定义view的时候不能省略两个构造函数的构造方法
     * 看层级，内部有递归，如果层级太多，递归造成栈溢出就oom了
     */

    /**
     * 字节码执行方法
     * 方法执行时，先将操作数放入操作数栈，等执行放入局部变量表语句时，将栈顶的操作数出栈，写入局部变量表，然后遇到操作数
     * 如+，贼弹出操作数栈内两个数进行操作，在压入操作数栈，直至遇到放入语句，放入语句带下标，即放入局部变量表的位置，最后
     * 将局部变量表的数据到方法出口输出，动态链接的意思是，对于jvm来说，当多态方法时，jvm是不知道执行哪个方法的，就由动态
     * 链接去确定该执行哪个方法（也多用于本地方法的确定）
     */


    /**
     * 栈帧(Stack Frame):  是用于支持虚拟机进行方法调用和方法执行的数据结构。栈帧存储了方法的局部变量表、操作数
     * 栈、动态连接和方法返回地址等信息。每一个方法从调用至执行完成的过程，都对应着一个栈帧在虚拟机栈里从入栈到出栈的过程。
     * 一个线程中方法的调用链可能会很长，很多方法都同时处于执行状态。对于JVM执行引擎来说，在在活动线程中，只有位
     * 于JVM虚拟机栈栈顶的元素才是有效的，即称为当前栈帧，与这个栈帧相关连的方法称为当前方法，定义这个方法的类叫做当前类。
     * 执行引擎运行的所有字节码指令都只针对当前栈帧进行操作。如果当前方法调用了其他方法，或者当前方法执行结束，那这个方法的栈帧就不再是当前栈帧了。
     * 调用新的方法时，新的栈帧也会随之创建。并且随着程序控制权转移到新方法，新的栈帧成为了当前栈帧。方法返回之际，
     * 原栈帧会返回方法的执行结果给之前的栈帧(返回给方法调用者)，随后虚拟机将会丢弃此栈帧。
     *
     * 栈帧是线程本地的私有数据，不可能在一个栈帧中引用另外一个线程的栈帧。
     * 栈帧结构在hpdi下zhanzhenjiegou
     * 关于「栈帧」，我们在看看《Java虚拟机规范》中的描述：
     *
     * 栈帧是用来存储数据和部分过程结果的数据结构，同时也用来处理动态连接、方法返回值和异常分派。
     * 栈帧随着方法调用而创建，随着方法结束而销毁——无论方法正常完成还是异常完成都算作方法结束。
     * 栈帧的存储空间由创建它的线程分配在Java虚拟机栈之中，每一个栈帧都有自己的本地变量表(局部变量表)、操作数栈和指向当前方法所属的类的运行时常量池的引用。
     *
     * 1.局部变量表
     * 局部变量表(Local Variable Table)是一组变量值存储空间，用于存放方法参数和方法内定义的局部变量。局部变量表
     * 的容量以变量槽(Variable Slot)为最小单位，Java虚拟机规范并没有定义一个槽所应该占用内存空间的大小，但是规定
     * 了一个槽应该可以存放一个32位以内的数据类型。
     * 在Java程序编译为Class文件时,就在方法的Code属性中的max_locals数据项中确定了该方法所需分配的局部变量表的最大容量。(最大Slot数量)
     * 一个局部变量可以保存一个类型为boolean、byte、char、short、int、float、reference和returnAddress类型的数据。
     * reference类型表示对一个对象实例的引用。returnAddress类型是为jsr、jsr_w和ret指令服务的，目前已经很少使用了。
     *虚拟机通过索引定位的方法查找相应的局部变量，索引的范围是从0~局部变量表最大容量。如果Slot是32位的，则遇到一
     * 个64位数据类型的变量(如long或double型)，则会连续使用两个连续的Slot来存储。
     *
     * 2.操作数栈
     * 操作数栈(Operand Stack)也常称为操作栈，它是一个后入先出栈(LIFO)。同局部变量表一样，操作数栈的最大深度也
     * 在编译的时候写入到方法的Code属性的max_stacks数据项中。
     * 操作数栈的每一个元素可以是任意Java数据类型，32位的数据类型占一个栈容量，64位的数据类型占2个栈容量,且在方
     * 法执行的任意时刻，操作数栈的深度都不会超过max_stacks中设置的最大值。
     * 当一个方法刚刚开始执行时，其操作数栈是空的，随着方法执行和字节码指令的执行，会从局部变量表或对象实例的字段
     * 中复制常量或变量写入到操作数栈，再随着计算的进行将栈中元素出栈到局部变量表或者返回给方法调用者，也就是出栈
     * /入栈操作。一个完整的方法执行期间往往包含多个这样出栈/入栈的过程。
     *
     * 3.动态连接
     * 在一个class文件中，一个方法要调用其他方法，需要将这些方法的符号引用转化为其在内存地址中的直接引用，而符号
     * 引用存在于方法区中的运行时常量池。
     * Java虚拟机栈中，每个栈帧都包含一个指向运行时常量池中该栈所属方法的符号引用，持有这个引用的目的是为了支持
     * 方法调用过程中的动态连接(Dynamic Linking)。
     * 这些符号引用一部分会在类加载阶段或者第一次使用时就直接转化为直接引用，这类转化称为静态解析。另一部分将在每
     * 次运行期间转化为直接引用，这类转化称为动态连接。
     *
     * 4.方法返回
     * 当一个方法开始执行时，可能有两种方式退出该方法：
     *
     * 正常完成出口
     * 异常完成出口
     * 正常完成出口是指方法正常完成并退出，没有抛出任何异常(包括Java虚拟机异常以及执行时通过throw语句显示抛出的异常)。
     * 如果当前方法正常完成，则根据当前方法返回的字节码指令，这时有可能会有返回值传递给方法调用者(调用它的方法)，
     * 或者无返回值。具体是否有返回值以及返回值的数据类型将根据该方法返回的字节码指令确定。
     *
     * 异常完成出口是指方法执行过程中遇到异常，并且这个异常在方法体内部没有得到处理，导致方法退出。
     *
     * 无论是Java虚拟机抛出的异常还是代码中使用athrow指令产生的异常，只要在本方法的异常表中没有搜索到相应的异常
     * 处理器，就会导致方法退出。无论方法采用何种方式退出，在方法退出后都需要返回到方法被调用的位置，程序才能继续
     * 执行，方法返回时可能需要在当前栈帧中保存一些信息，用来帮他恢复它的上层方法执行状态。
     * 方法退出过程实际上就等同于把当前栈帧出栈，因此退出可以执行的操作有：恢复上层方法的局部变量表和操作数栈，
     * 把返回值(如果有的话)压如调用者的操作数栈中，调整PC计数器的值以指向方法调用指令后的下一条指令。
     * 一般来说，方法正常退出时，调用者的PC计数值可以作为返回地址，栈帧中可能保存此计数值。而方法异常退出时，
     * 返回地址是通过异常处理器表确定的，栈帧中一般不会保存此部分信息。
     *
     * 5.附加信息
     * 虚拟机规范允许具体的虚拟机实现增加一些规范中没有描述的信息到栈帧之中，例如和调试相关的信息，这部分信息完全
     * 取决于不同的虚拟机实现。在实际开发中，一般会把动态连接，方法返回地址与其他附加信息一起归为一类，称为栈帧信息。
     */

    /**
     * 程序计数器（Program Counter Register）:
     * 程序计数器是一个记录着当前线程所执行的字节码的行号指示器。
     * JAVA代码编译后的字节码在未经过JIT（实时编译器）编译前，其执行方式是通过“字节码解释器”进行解释执行。简单的
     * 工作原理为解释器读取装载入内存的字节码，按照顺序读取字节码指令。读取一个指令后，将该指令“翻译”成固定的操作，
     * 并根据这些操作进行分支、循环、跳转等流程。
     * 从上面的描述中，可能会产生程序计数器是否是多余的疑问。因为沿着指令的顺序执行下去，即使是分支跳转这样的流程，
     * 跳转到指定的指令处按顺序继续执行是完全能够保证程序的执行顺序的。假设程序永远只有一个线程，这个疑问没有任何
     * 问题，也就是说并不需要程序计数器。但实际上程序是通过多个线程协同合作执行的。
     * 首先我们要搞清楚JVM的多线程实现方式。JVM的多线程是通过CPU时间片轮转（即线程轮流切换并分配处理器执行时间）
     * 算法来实现的。也就是说，某个线程在执行过程中可能会因为时间片耗尽而被挂起，而另一个线程获取到时间片开始执行。
     * 当被挂起的线程重新获取到时间片的时候，它要想从被挂起的地方继续执行，就必须知道它上次执行到哪个位置，在JVM中，
     * 通过程序计数器来记录某个线程的字节码执行位置。因此，程序计数器是具备线程隔离的特性，也就是说，每个线程工作
     * 时都有属于自己的独立计数器。
     *
     * 程序计数器的特点
     * 　　1.线程隔离性，每个线程工作时都有属于自己的独立计数器。
     * 　　2.执行java方法时，程序计数器是有值的，且记录的是正在执行的字节码指令的地址（参考上一小节的描述）。
     * 　　3.执行native本地方法时，程序计数器的值为空（Undefined）。因为native方法是java通过JNI直接调用本地
     *     C/C++库，可以近似的认为native方法相当于C/C++暴露给java的一个接口，java通过调用这个接口从而调用到
     *     C/C++方法。由于该方法是通过C/C++而不是java进行实现。那么自然无法产生相应的字节码，并且C/C++执行时
     *     的内存分配是由自己语言决定的，而不是由JVM决定的。
     *     4.程序计数器占用内存很小，在进行JVM内存计算时，可以忽略不计。
     * 　　5.程序计数器，是唯一一个在java虚拟机规范中没有规定任何OutOfMemoryError的区域。
     */

    /**
     * 本地方法栈（Native Method Stacks）:
     * 与 Java 虚拟机栈所发挥的作用是非常相似的，其区别不过是虚拟机栈为虚拟机执行 Java 方法（也就是字节码）服务，
     * 而本地方法栈则是为虚拟机使用到的 Native 方法服务。虚拟机规范中对本地方法栈中的方法使用的语言、使用方式与
     * 数据结构并没有强制规定，因此具体的虚拟机可以自由实现它。
     * Navtive 方法是 Java 通过 JNI 直接调用本地 C/C++ 库，可以认为是 Native 方法相当于 C/C++ 暴露给 Java
     * 的一个接口，Java 通过调用这个接口从而调用到 C/C++ 方法。当线程调用 Java 方法时，虚拟机会创建一个栈帧并
     * 压入 Java 虚拟机栈。然而当它调用的是 native 方法时，虚拟机会保持 Java 虚拟机栈不变，也不会向 Java 虚拟
     * 机栈中压入新的栈帧，虚拟机只是简单地动态连接并直接调用指定的 native 方法。
     * 图：hdpi下bendifangfazhan
     * 本地方法栈是一个后入先出（Last In First Out）栈。
     * 由于是线程私有的，生命周期随着线程，线程启动而产生，线程结束而消亡。
     * 本地方法栈会抛出 StackOverflowError 和 OutOfMemoryError 异常。
     */

    /**
     * 指针碰撞：
     * Java中提到指针碰撞主要是在GC中，GC的标记/整理（标记/压缩）算法在每次执行完之后会通过一个指针将堆内存分为
     * 两个区域，分别是已用区域和未用区域。指针的左边已用区域内存满了对象（其实就是经过上一次GC之后存活下来的对象）
     * ，指针的右边为可用区域。之后当需要new一个对象时，JVM会在当前指针所指位置为新对象分配内存，并将指针向后移动
     * 对象所对应size位。比如new的对象需要128字节的内存空间，那么JVM将会从当前指针所指位置开始，之后的128字节分
     * 配给该对象，同时指针后移128个字节。但由于堆内存本身是线程共享的，在多线程场景下，当一个线程需要创建对象，
     * 这时指针还没来得及修改（指针是在新对象占完位之后才能进行修改），如果另一个线程也需要分配空间，就会造成两个
     * 对象空间冲突，这就称之为指针碰撞。
     */

    /**
     * 空闲列表:
     * 适用于堆内存不完整的情况，已分配的内存和空闲内存相互交错，JVM通过维护一张内存列表记录可用的内存块信息，当
     * 分配内存时，从列表中找到一个足够大的内存块分配给对象实例，并更新列表上的记录，最常见的使用此方案的垃圾收集器就是CMS
     */

    /**
     * 内存分配并发问题线程
     *
     * 在建立对象的时候有一个很重要的问题，就是线程安全，由于在实际开发过程当中，建立对象是很频繁的事情，做为虚拟
     * 机来讲，必需要保证线程是安全的，一般来说，虚拟机采用两种方式来保证线程安全：指针
     *
     * CAS： CAS 是乐观锁的一种实现方式。所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操做，若是由于冲
     * 突失败就重试，直到成功为止。虚拟机采用 CAS 配上失败重试的方式保证更新操做的原子性。
     *
     * TLAB： 为每个线程预先分配一块内存，JVM在给线程中的对象分配内存时，首先在TLAB分配，当对象大于TLAB中的剩余
     * 内存或TLAB的内存已用尽时，再采用上述的CAS进行内存分配。
     *
     * TALB的原理:
     * TALB就是在堆内存上额外为每个线程分配一块线程私有区域，其大小一般比较小，默认占Eden区的1%。其本质就是通过
     * start、top、end三个指针实现，其中start和end分别指向这个TALB的开始和结尾位置，用于确定该TALB在堆上对应
     * 区域，避免其他线程再过来分配内存，top实时指向TALB区域内当前可分配的第一个位置，当一个TALB满了或剩余空间
     * 不足以存储新申请的对象时，线程话会向JVM再申请一块TALB。到这里为止，指针碰撞多线程问题似乎已经得到了解决，
     * 不过由于TALB空间本身较小（默认只占Eden区1%），所以就很容易出现TALB剩余区域不足以存储新对象的情况，这时
     * 线程会把新对象存到新申请的TALB中，这样原有的TALB中剩余区域就会被浪费，造成内存泄漏。那么如何解决内存泄漏呢？
     * 3 最大浪费空间
     * 由于TALB内存浪费现象较为严重，所以JVM开发人员提出了一个最大浪费空间对TALB进行约束。
     * 当TALB剩余空间存不下新对象时，会进行一个判断：
     * ① 如果当前TALB剩余空间小于最大浪费空间，则TALB所属线程会向JVM申请一个新的TALB区域存储新对象，如果依旧存储不下，则对象会放在Eden区创建。
     * ② 如果当前TALB剩余空间大于最大浪费空间，则对象直接去Eden区创建。
     * 4 TALB的局限性
     * 虽然TALB解决了指针碰撞在多线程场景下的问题，并且通过最大浪费空间可以减少内存泄漏，但其本身依旧有一些缺点：
     * ① GC更频繁： 由于每个TALB所占用的空间都要比线程实际需要的空间大小大一些（因为不可能每个TALB都刚好存满，
     * 也就是TALB空间浪费更严重），所以一批对象直接存储在Eden区会比存储在TALB区占用更多的空间，进而容易引发Minor GC。
     * ② TALB允许内存浪费，会导致Eden区内存不连续。
     */

    /**
     *   Java对象内存布局:
     *   1、对象在内存中存储的布局分为三块
     *
     * 对象头
     * 存储对象自身的运行时数据：Mark Word（在32bit和64bit虚拟机上长度分别为32bit和64bit），包含如下信息：
     * 对象hashCode
     * 对象GC分代年龄
     * 锁状态标志（轻量级锁、重量级锁）
     * 线程持有的锁（轻量级锁、重量级锁）
     * 偏向锁相关：偏向锁、自旋锁、轻量级锁以及其他的一些锁优化策略是JDK1.6加入的，这些优化使得Synchronized的性
     * 能与ReentrantLock的性能持平，在Synchronized可以满足要求的情况下，优先使用Synchronized，除非是使用一些
     * ReentrantLock独有的功能，例如指定时间等待等。
     * 类型指针：对象指向类元数据的指针（32bit-->32bit，64bit-->64bit(未开启压缩指针)，32bit(开启压缩指针)）
     * JVM通过这个指针来确定这个对象是哪个类的实例（根据对象确定其Class的指针）
     * 实例数据：对象真正存储的有效信息
     * 对齐填充
     * JVM要求对象的大小必须是8的整数倍，若不是，需要补位对齐
     * 2、注意
     *
     * Mark Word具有非固定的数据结构，以便在极小的空间内存储尽量多的信息
     * 如果对象是一个数组，对象头必须有一块儿用于记录数组长度的数据。JVM可以通过Java对象的元数据确定对象长度，但是对于数组不行。
     * 对于对象头长度而言
     * 32bit虚拟机一定是32bit+32bit，即8字节
     * 64bit虚拟机若没有开启了压缩指针，是64bit+64bit，即16字节，若开启了压缩指针，是64bit+32bit，即12字节（不是8bit的倍数）
     * -XX:+UseCompressedOops：开启压缩指针
     * 在《深入理解Java虚拟机（第二版）》中，说对象头是8字节或16字节，不知道是不是有误，自己的系统不是64bit，没有测试
     * 基本数据类型与对应包装类的选用
     */

    //直接内存
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10240);

    /**
     * 强引用
     *
     *  是指创建一个对象并把这个对象赋给一个引用变量。强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OutOfMemory错误也不会回收这种对象
     *  如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。
     *
     *   public void fun1() {
     *         Object object = new Object();
     *         Object[] objArr = new Object[1000];
     *  }
     *  当fun1运行完之后，object和objArr都已经不存在了，所以它们指向的对象都会被JVM回收。
     */
    String ss = new String("asd");

    String s = "asdad";
    /**
     * 软引用（SoftReference）
     *
     * 如果一个对象具有软引用，内存空间足够，垃圾回收器就不会回收它；
     *
     * 如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
     *
     * 软引用可用来实现内存敏感的高速缓存,比如网页缓存、图片缓存等。使用软引用能防止内存泄露，增强程序的健壮性。
     * SoftReference的特点是它的一个实例保存对一个Java对象的软引用， 该软引用的存在不妨碍垃圾收集线程对该Java对象的回收。
     *
     * 也就是说，一旦SoftReference保存了对一个Java对象的软引用后，在垃圾线程对 这个Java对象回收前，SoftReference类所提供的get()方法返回Java对象的强引用。
     *
     * 另外，一旦垃圾线程回收该Java对象之 后，get()方法将返回null。
     */
    SoftReference<String> softReference = new SoftReference<>(s);

    ReferenceQueue queue = new ReferenceQueue();

    /**
     * 作为一个Java对象，SoftReference对象除了具有保存软引用的特殊性之外，也具有Java对象的一般性。所以，当软可
     * 及对象被回收之后，虽然这个SoftReference对象的get()方法返回null,但这个SoftReference对象已经不再具有存
     * 在的价值，需要一个适当的清除机制，避免大量SoftReference对象带来的内存泄漏。在java.lang.ref包里还提供了
     * ReferenceQueue。如果在创建SoftReference对象的时候，使用了一个ReferenceQueue对象作为参数提供给SoftReference
     * 的构造方法.那么当这个SoftReference所软引用的aMyOhject被垃圾收集器回收的同时，ref所强引用的SoftReference
     * 对象被列入ReferenceQueue。也就是说，ReferenceQueue中保存的对象是Reference对象，而且是已经失去了它所软
     * 引用的对象的Reference对象。另外从ReferenceQueue这个名字也可以看出，它是一个队列，当我们调用它的poll()
     * 方法的时候，如果这个队列中不是空队列，那么将返回队列前面的那个Reference对象。在任何时候，我们都可以调用
     * ReferenceQueue的poll()方法来检查是否有它所关心的非强可及对象被回收。如果队列为空，将返回一个null,否则
     * 该方法返回队列中前面的一个Reference对象。利用这个方法，我们可以检查哪个SoftReference所软引用的对象已经
     * 被回收。于是我们可以把这些失去所软引用的对象的SoftReference对象清除掉
     */
    SoftReference<String> softReference1 = new SoftReference<>(s,queue);

    private void trySoftRer(){
        while ((softReference1 = (SoftReference<String>) queue.poll())!=null){

        }
    }

    /**
     * Minor GC
     * Minor GC指新生代GC，即发生在新生代（包括Eden区和Survivor区）的垃圾回收操作，当新生代无法为新生对象分配
     * 内存空间的时候，会触发Minor GC。因为新生代中大多数对象的生命周期都很短，所以发生Minor GC的频率很高，
     * 虽然它会触发stop-the-world，但是它的回收速度很快。
     *
     * Major GC
     * Major GC清理Tenured区，用于回收老年代，出现Major GC通常会出现至少一次Minor GC。
     *
     * Full GC
     * Full GC是针对整个新生代、老生代、元空间（metaspace，java8以上版本取代perm gen）的全局范围的GC。Full
     * GC不等于Major GC，也不等于Minor GC+Major GC，发生Full GC需要看使用了什么垃圾收集器组合，才能解释是
     * 什么样的垃圾回收。
     */

    public void tryHotFix(DexFile dexFile){
        if(dexFile!=null){
            Class aClass = dexFile.loadClass("", ClassLoader.getSystemClassLoader());


        }
    }

    public void tryAndFix(DexFile dexFile,File file , ClassLoader classLoader , List<String> classes){
        ClassLoader classLoader1 = new ClassLoader(classLoader) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                Class aClass = dexFile.loadClass(name, this);
                if(aClass!=null&&name.startsWith("com.jzw.andfix")){
                    return Class.forName(name);
                }
                if (aClass==null){
                    throw new ClassNotFoundException(name);
                }
                return aClass;
            }
        };
        Enumeration<String> entries = dexFile.entries();
        Class c = null;
        while (entries.hasMoreElements()){
            String s = entries.nextElement();
            if(classes!=null&&!classes.contains(s)){
                continue;
            }
            //找到了，加载
            c = dexFile.loadClass(s,classLoader1);
            if(c!=null){
                fixClass(c,classLoader);
            }
        }
    }

    private void fixClass(Class c, ClassLoader classLoader) {
        Method[] declaredMethods = c.getDeclaredMethods();
        // TODO: 2022/3/10 替换方法。。。。
        int spec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        int mode = View.MeasureSpec.getMode(spec);
        int size = View.MeasureSpec.getSize(spec);

    }
}
