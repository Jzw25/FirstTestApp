package com.example.myapplication.javatest;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多线程
 */
public class JavaMultithreadTest {
    public static final String TAG = JavaMultithreadTest.class.toString();
    private String A = "A";
    private String B = "B";
    private AtomicReference<String> reference;
    private AtomicStampedReference<String> atomicStampedReference;
    private AtomicInteger atomicInteger;

    public void tryAtmoicReference(){
        reference = new AtomicReference<>();
        reference.set(A);

    }
    /**
     * 线程实现：继承runabll，继承thread
     */

    public class MyTestThread extends Thread{
        @Override
        public void run() {
            super.run();
            //do someting
        }
    }

    public class MyTestRunable implements Runnable{

        @Override
        public void run() {
            //do someting
        }
    }

    /**
     * 调用线程
     */
    public void tryThread(){
        MyTestThread myTestThread = new MyTestThread();
        //设置为守护线程,start之前设置
        myTestThread.setDaemon(true);
        myTestThread.start();

        MyTestRunable myTestRunable = new MyTestRunable();
        Thread thread = new Thread(myTestRunable);
        thread.start();
    }

    /**
     * 中断线程是什么？(该解释来自java核心技术一书，我对其进行稍微简化)， 当线程的run()方法执行方法体中的最后一
     * 条语句后，并经由执行return语句返回时，或者出现在方法中没有捕获的异常时线程将终止。在java早期版本中有一个
     * stop方法，其他线程可以调用它终止线程，但是这个方法现在已经被弃用了，因为这个方法会造成一些线程不安全的问题
     * 。我们可以把中断理解为一个标识位的属性，它表示一个运行中的线程是否被其他线程进行了中断操作，而中断就好比其
     * 他线程对该线程打可个招呼，其他线程通过调用该线程的 interrupt方法对其进行中断操作，当一个线程调用interrupt
     * 方法时，线程的中断状态（标识位）将被置位（改变），这是每个线程都具有的boolean标志，每个线程都应该不时的检
     * 查这个标志，来判断线程是否被中断。而要判断线程是否被中断
     * Thread.currentThread().isInterrupted()
     * 关于中断线程，我们这里给出中断线程的一些主要方法：
     * void interrupt()：向线程发送中断请求，线程的中断状态将会被设置为true，如果当前线程被一个sleep调用阻塞，
     * 那么将会抛出interrupedException异常。
     * static boolean interrupted()：测试当前线程（当前正在执行命令的这个线程）是否被中断。注意这是个静态方法，
     * 调用这个方法会产生一个副作用那就是它会将当前线程的中断状态重置为false。
     * boolean isInterrupted()：判断线程是否被中断，这个方法的调用不会产生副作用即不改变线程的当前中断状态。
     * static Thread currentThread() : 返回代表当前执行线程的Thread对象。
     *
     * 守护线程:
     * 首先我们可以通过t.setDaemon(true)的方法将线程转化为守护线程。而守护线程的唯一作用就是为其他线程提供服务。
     * 计时线程就是一个典型的例子，它定时地发送“计时器滴答”信号告诉其他线程去执行某项任务。当只剩下守护线程时，
     * 虚拟机就退出了，因为如果只剩下守护线程，程序就没有必要执行了。另外JVM的垃圾回收、内存管理等线程都是守护
     * 线程。还有就是在做数据库应用时候，使用的数据库连接池，连接池本身也包含着很多后台线程，监控连接个数、超时
     * 时间、状态等等。最后还有一点需要特别注意的是在java虚拟机退出时Daemon线程中的finally代码块并不一定会执行
     * **其实守护线程和用户线程区别不大，可以理解为特殊的用户线程。特殊就特殊在如果程序中所有的用户线程都退出了，
     * 那么所有的守护线程就都会被杀死，很好理解，没有被守护的对象了，也不需要守护线程了
     * 还有一个启动守护线程的方法就是利用Timer和TimerTask。Timer是JDK提供的定时器工具，使用时会在主线程之外单
     * 独起一个线程执行指定的任务。Timer timer = new Timer()启动的是用户线程，而Timer timer =
     * new Timer(true)启动的就是守护线程。TimerTask是一个实现了Runnable接口的抽象类，配合Timer
     * 使用可以看做被Timer执行的任务，即启动的线程。
     *
     * 线程优先级:
     * 在现代操作系统中基本采用时分的形式调度运行的线程，操作系统会分出一个个时间片，线程会分配到若干时间片，
     * 当线程的时间片用完了就会发生线程调度，并等待着下一次分配。线程分配到的时间片多少也决定了线程使用处理
     * 器资源的多少，而线程优先级就是决定线程需要多或者少分配一些处理器资源的线程属性。在java线程中，通过
     * 一个整型的成员变量 Priority来控制线程优先级 ，每一个线程有一个优先级，默认情况下，一个线程继承它
     * 父类的优先级。可以用setPriority方法提高或降低任何一个线程优先级。可以将优先级设置在MIN_PRIORITY
     * （在Thread类定义为1）与MAX_PRIORITY（在Thread类定义为10）之间的任何值。线程的默认优先级为
     * NORM_PRIORITY（在Thread类定义为5）。 尽量不要依赖优先级，如果确实要用，应该避免初学者常犯的一个错误。
     * 如果有几个高优先级的线程没有进入非活动状态，低优先级线程可能永远也不能执行。每当调度器决定运行一个新线程时
     * ，首先会在具有高优先级的线程中进行选择，尽管这样会使低优先级的线程可能永远不会被执行到。因此我们在设置优
     * 先级时，针对频繁阻塞（休眠或者I/O操作）的线程需要设置较高的优先级，而偏重计算（需要较多CPU时间或者运算）
     * 的线程则设置较低的优先级，这样才能确保处理器不会被长久独占。当然还有要注意就是在不同的JVM以及操作系统上
     * 线程的规划存在差异，有些操作系统甚至会忽略对线程优先级的设定，如mac os系统或者Ubuntu系统........
     *
     * 线程的状态转化关系
     * （1）. 新建状态（New）：新创建了一个线程对象。
     * （2）. 就绪状态（Runnable）：线程对象创建后，其他线程调用了该对象的start()方法。该状态的线程位于可运行线程池中，变得可运行，等待获取CPU的使用权。
     * （3）. 运行状态（Running）：就绪状态的线程获取了CPU，执行程序代码。
     * （4）. 阻塞状态（Blocked）：阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：
     * - 等待阻塞（WAITING）：运行的线程执行wait()方法，JVM会把该线程放入等待池中。
     *
     * - 同步阻塞（Blocked）：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池中。
     *
     * - 超时阻塞（TIME_WAITING）：运行的线程执行sleep(long)或join(long)方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。
     *
     * （5）. 死亡状态（Dead）：线程执行完了或者因异常退出了run()方法，该线程结束生命周期。
     *  图示再hpid下：threadstate
     *  Thread.sleep()：在指定时间内让当前正在执行的线程暂停执行，但不会释放"锁标志"。不推荐使用。
     * Thread.sleep(long)：使当前线程进入阻塞状态，在指定时间内不会执行。
     * Object.wait()和 Object.wait(long) ：在其他线程调用对象的notify或notifyAll方法前，导致当前线程等待。
     * 线程会释放掉它所占有的"锁标志"，从而使别的线程有机会抢占该锁。  当前线程必须拥有当前对象锁。如果当前线程
     * 不是此锁的拥有者，会抛出IllegalMonitorStateException异常。 唤醒当前对象锁的等待线程使用notify或
     * notifyAll方法，也必须拥有相同的对象锁，否则也会抛出IllegalMonitorStateException异常， waite()和
     * notify()必须在synchronized函数或synchronized中进行调用。如果在non-synchronized函数或non-synchronized
     * 中进行调用,虽然能编译通过，但在运行时会发生IllegalMonitorStateException的异常。
     * Object.notifyAll()：则从对象等待池中唤醒所有等待等待线程
     * Object.notify()：则从对象等待池中唤醒其中一个线程。
     * Thread.yield()方法 暂停当前正在执行的线程对象，yield()只是使当前线程重新回到可执行状态，所以执行yield()
     * 的线程有可能在进入到可执行状态后马上又被执行，yield()只能使同优先级或更高优先级的线程有执行的机会。
     * Thread.Join()：把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。比如在线程B中调
     * 用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
     */



    /**
     * lock ：
     * synchronized是java中的一个关键字，也就是说是Java语言内置的特性。那么为什么会出现Lock呢？
     *
     * 　　在上面一篇文章中，我们了解到如果一个代码块被synchronized修饰了，当一个线程获取了对应的锁，并执行该代
     * 码块时，其他线程便只能一直等待，等待获取锁的线程释放锁，而这里获取锁的线程释放锁只会有两种情况：
     *
     * 　　1）获取锁的线程执行完了该代码块，然后线程释放对锁的占有；
     *
     * 　　2）线程执行发生异常，此时JVM会让线程自动释放锁。
     *
     * 　　那么如果这个获取锁的线程由于要等待IO或者其他原因（比如调用sleep方法）被阻塞了，但是又没有释放锁，
     * 其他线程便只能干巴巴地等待，试想一下，这多么影响程序执行效率。
     *
     * 　　因此就需要有一种机制可以不让等待的线程一直无期限地等待下去（比如只等待一定的时间或者能够响应中断），通过Lock就可以办到。
     *
     * 　　再举个例子：当有多个线程读写文件时，读操作和写操作会发生冲突现象，写操作和写操作会发生冲突现象，但是读操作和读操作不会发生冲突现象。
     *
     * 　　但是采用synchronized关键字来实现同步的话，就会导致一个问题：
     *
     * 　　如果多个线程都只是进行读操作，所以当一个线程在进行读操作时，其他线程只能等待无法进行读操作。
     *
     * 　　因此就需要一种机制来使得多个线程都只是进行读操作时，线程之间不会发生冲突，通过Lock就可以办到。
     *
     * 　　另外，通过Lock可以知道线程有没有成功获取到锁。这个是synchronized无法办到的。
     *
     * 　　总结一下，也就是说Lock提供了比synchronized更多的功能。但是要注意以下几点：
     *
     * 　　1）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
     *
     * 　　2）Lock和synchronized有一点非常大的不同，采用synchronized不需要用户去手动释放锁，当synchronized
     * 方法或者synchronized代码块执行完之后，系统会自动让线程释放对锁的占用；而Lock则必须要用户去手动释放锁，
     * 如果没有主动释放锁，就有可能导致出现死锁现象。
     */

    /**
     * 2.ReentrantLock
     * ReentrantLock，意思是“可重入锁”，关于可重入锁的概念在下一节讲述。ReentrantLock是唯一实现了Lock接口的类
     * ，并且ReentrantLock提供了更多的方法。下面通过一些实例看具体看一下如何使用ReentrantLock。
     */

    //参数不穿默认false，为非公平锁，传入true为公平锁
    private Lock lock = new ReentrantLock(false);
    private void insert(Thread thread){
        //获取锁
        lock.lock();
        /**
         * lock()方法是平常使用得最多的一个方法，就是用来获取锁。如果锁已被其他线程获取，则进行等待
         */
        try {
            Log.d(TAG, "insert: lock :  " + thread.getName() + "get the lock!");

        }catch (Exception e){

        }finally {
            //释放锁
            /**
             * 采用Lock，必须主动去释放锁，并且在发生异常时，不会自动释放锁。因此一般来说，使用Lock必须在try{}
             * catch{}块中进行，并且将释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。
             */
            lock.unlock();
            Log.d(TAG, "tryLockInsert: unlock!");
        }
    }
    
    private void tryLockInsert(Thread thread){
        /**
         * tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，如果获取失败（即锁已被其
         * 他线程获取），则返回false，也就说这个方法无论如何都会立即返回。在拿不到锁时不会一直在那等待。
         * tryLock(long time, TimeUnit unit)方法和tryLock()方法是类似的，只不过区别在于这个方法在拿不到锁时
         * 会等待一定的时间，在时间期限之内如果还拿不到锁，就返回false。如果如果一开始拿到锁或者在等待期间内拿到
         * 了锁，则返回true。
         */
        if(lock.tryLock()){
            try {
                Log.d(TAG, "tryLockInsert: get lock success! we get the lock!"+thread.getName());
            }catch (Exception e){

            }finally {
                lock.unlock();
                Log.d(TAG, "tryLockInsert: unlock!"+thread.getName());
            }
        }else {
            Log.d(TAG, "tryLockInsert: get lock fail"+thread.getName());
        }
    }

    /**
     * lockInterruptibly()方法比较特殊，当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中
     * 断，即中断线程的等待状态。也就使说，当两个线程同时通过lock.lockInterruptibly()想获取某个锁时，假若此时
     * 线程A获取到了锁，而线程B只有在等待，那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
     *由于lockInterruptibly()的声明中抛出了异常，所以lock.lockInterruptibly()必须放在try块中或者在调用
     * lockInterruptibly()的方法外声明抛出InterruptedException。
     * @param thread
     * @throws InterruptedException
     */
    private void interLockInsert(Thread thread) throws InterruptedException {
        //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        lock.lockInterruptibly();
        try {
            Log.d(TAG, "interLockInsert: get the lock : " + thread.getName());
        }catch (Exception e){

        }finally {
            lock.unlock();
            Log.d(TAG, "interLockInsert: unlock! : " + thread.getName());
        }
    }

    public void tryInterLockTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    interLockInsert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    interLockInsert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void tryLockTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        });
    }

    /**
     * 3.ReadWriteLock
     * ReadWriteLock也是一个接口，在它里面只定义了两个方法： Lock readLock(); Lock writeLock();
     * 一个用来获取读锁，一个用来获取写锁。也就是说将文件的读写操作分开，分成2个锁来分配给线程，从而使得多个线程
     * 可以同时进行读操作。下面的ReentrantReadWriteLock实现了ReadWriteLock接口。
     */

    /**
     * 4.ReentrantReadWriteLock
     * ReentrantReadWriteLock里面提供了很多丰富的方法，不过最主要的有两个方法：readLock()和writeLock()用来获取读锁和写锁。
     * 下面通过几个例子来看一下ReentrantReadWriteLock具体用法。
     */

    /**
     * 也有公平锁非公平锁之分，与上面的lock一样
     */
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);

    private void getWRLock(Thread thread){
        reentrantReadWriteLock.readLock().lock();
        try {
            long l = System.currentTimeMillis();
            while ((System.currentTimeMillis()-l)<=1){
                Log.d(TAG, "getWRLock: this is " +thread.getName() + "reading");
            }
            Log.d(TAG, "getWRLock: this is "+thread.getName() + "finish");
        }catch (Exception e){

        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    /**
     * 说明thread1和thread2在同时进行读操作。
     *这样就大大提升了读操作的效率。
     *不过要注意的是，如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
     *如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
     *关于ReentrantReadWriteLock类中的其他方法感兴趣的朋友可以自行查阅API文档。
     */
    public void tryWRLockTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getWRLock(Thread.currentThread());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getWRLock(Thread.currentThread());
            }
        }).start();
    }
    /**
     * 5.Lock和synchronized的选择
     *总结来说，Lock和synchronized有以下几点不同：
     *1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
     *2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果
     * 没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
     *3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，
     * 不能够响应中断；
     *4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
     *5）Lock可以提高多个线程进行读操作的效率。
     *在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），
     * 此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。
     */

    /**
     * 锁介绍：
     * 1.可重入锁
     * 如果锁具备可重入性，则称作为可重入锁。像synchronized和ReentrantLock都是可重入锁，可重入性在我看来实际
     * 上表明了锁的分配机制：基于线程的分配，而不是基于方法调用的分配。举个简单的例子，当一个线程执行到某个
     * synchronized方法时，比如说method1，而在method1中会调用另外一个synchronized方法method2，
     * 此时线程不必重新去申请锁，而是可以直接执行方法method2。
     * class MyClass {
     *     public synchronized void method1() {
     *         method2();
     *     }
     *
     *     public synchronized void method2() {
     *
     *     }
     * }
     * 上述代码中的两个方法method1和method2都用synchronized修饰了，假如某一时刻，线程A执行到了method1，此时
     * 线程A获取了这个对象的锁，而由于method2也是synchronized方法，假如synchronized不具备可重入性，此时线程A
     * 需要重新申请锁。但是这就会造成一个问题，因为线程A已经持有了该对象的锁，而又在申请获取该对象的锁，这样就会
     * 线程A一直等待永远不会获取到的锁。
     * 而由于synchronized和Lock都具备可重入性，所以不会发生上述现象。
     */

    /**
     * 2.可中断锁
     *可中断锁：顾名思义，就是可以相应中断的锁。
     *在Java中，synchronized就不是可中断锁，而Lock是可中断锁。
     *如果某一线程A正在执行锁中的代码，另一线程B正在等待获取该锁，可能由于等待时间过长，线程B不想等待了，想先处理
     * 其他事情，我们可以让它中断自己或者在别的线程中中断它，这种就是可中断锁。
     *在前面演示lockInterruptibly()的用法时已经体现了Lock的可中断性。
     */

    /**
     * 3.公平锁
     *公平锁即尽量以请求锁的顺序来获取锁。比如同是有多个线程在等待一个锁，当这个锁被释放时，等待时间最久的线程
     * （最先请求的线程）会获得该所，这种就是公平锁。
     *非公平锁即无法保证锁的获取是按照请求锁的顺序进行的。这样就可能导致某个或者一些线程永远获取不到锁。
     *在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。
     *而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。
     */

    /**
     * 4.读写锁
     *读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。
     *正因为有了读写锁，才使得多个线程之间的读操作不会发生冲突。
     *ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。
     *可以通过readLock()获取读锁，通过writeLock()获取写锁。
     * 而读写锁在同一时刻可以允许多个读线程访问，在写线程访问的时候其他的读线程和写线程都会被阻塞。读写锁维护一对
     * 锁(读锁和写锁)，通过锁的分离，使得并发性提高。
     *
     * b）关于读写锁的基本使用：在不使用读写锁的时候，一般情况下我们需要使用synchronized搭配等待通知机制完成并
     * 发控制（写操作开始的时候，所有晚于写操作的读操作都会进入等待状态），只有写操作完成并通知后才会将等待的线程唤醒继续执行。
     * 如果改用读写锁实现，只需要在读操作的时候获取读锁，写操作的时候获取写锁。当写锁被获取到的时候，后续操作
     * （读写）都会被阻塞，只有在写锁释放之后才会执行后续操作。并发包中对ReadWriteLock接口的实现类是ReentrantReadWriteLock，这个实现类具有下面三个特点
     *①具有与ReentrantLock类似的公平锁和非公平锁的实现：默认的支持非公平锁，对于二者而言，非公平锁的吞吐量由于公平锁；
     *②支持重入：读线程获取读锁之后能够再次获取读锁，写线程获取写锁之后能再次获取写锁，也可以获取读锁。
     *③锁能降级：遵循获取写锁、获取读锁在释放写锁的顺序，即写锁能够降级为读锁
     *
     *   **读写锁同步器机制见hdpi下的duxiesuotongbuqi
     */

    /**
     * volite 关键字保证操作可见性和循序性，但无法保证其原子性，需加锁。再变量用volite修饰后，字节码阶段会加上
     * lock字符，内存屏障，保证此段代码不会指令重排序，并且有写入操作时，会立即写入主存，并且将其他线程用到这个字段
     * 的本地内存不可见，只可去主存读取
     */

    /**
     * ThreadLocal:
     * 多线程访问同一个共享变量的时候容易出现并发问题，特别是多个线程对一个变量进行写入的时候，为了保证线程安全，
     * 一般使用者在访问共享变量的时候需要进行额外的同步措施才能保证线程安全性。ThreadLocal是除了加锁这种同步方
     * 式之外的一种保证一种规避多线程访问出现线程不安全的方法，当我们在创建一个变量后，如果每个线程对其进行访问
     * 的时候访问的都是线程自己的变量这样就不会存在线程不安全问题。
     * ThreadLocal是JDK包提供的，它提供线程本地变量，如果创建一乐ThreadLocal变量，那么访问这个变量的每个线程
     * 都会有这个变量的一个副本，在实际多线程操作的时候，操作的是自己本地内存中的变量，从而规避了线程安全问题
     *
     * get,set方法都会判断ThreadLocalMap是否存在，以当前线程为key值，存在就取值，不存在就创建
     * ThreadLocal不支持继承性，同一个ThreadLocal变量在父线程中被设置值后，在子线程中是获取不到的。
     * （threadLocals中为当前调用线程对应的本地变量，所以二者自然是不能共享的）但是InheritableThreadLocal类则可以做到这个功能，
     *
     * 带来的问题
     *
     * 资源消耗
     *
     * 　　由于在每个线程中都创建了副本，所以要考虑它对资源的消耗，比如内存的占用会比不使用ThreadLocal要大。
     *
     * 内存溢出
     *
     *实际上 ThreadLocalMap 中使用的 key 为 ThreadLocal 的弱引用，弱引用的特点是，如果这个对象只存在弱引用，
     * 那么在下一次垃圾回收的时候必然会被清理掉。所以如果 ThreadLocal 没有被外部强引用的情况下，在垃圾回收的时
     * 候会被清理掉的，这样一来 ThreadLocalMap 中使用这个 ThreadLocal 的 key 也会被清理掉。但是，value 是
     * 强引用，不会被清理，这样一来就会出现 key 为 null 的 value。
     *
     * ThreadLocalMap 实现中已经考虑了这种情况，在调用 set()、get()、remove() 方法的时候，会清理掉 key 为
     * null 的记录。如果说会出现内存泄漏，那只有在出现了 key 为 null 的记录后，没有手动调用 remove() 方法，
     * 并且之后也不再调用 get()、set()、remove() 方法的情况下。
     */
    private ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public void tryThreadLocalTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("threadlocalone");
                Log.d(TAG, "tryThreadLocalTest: the value is : " + threadLocal.get());
                threadLocal.remove();
                Log.d(TAG, "tryThreadLocalTest: the value is : " + threadLocal.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("threadlocaltwo");
                Log.d(TAG, "tryThreadLocalTest: the value is : " + threadLocal.get());
                threadLocal.remove();
                Log.d(TAG, "tryThreadLocalTest: the value is : " + threadLocal.get());
            }
        }).start();
    }

    /**
     * 1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
     * 2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，即，一般在synchronized
     * 同步代码块里使用 wait()、notify/notifyAll() 方法。
     * 3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
     * 当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
     * 只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执
     * 行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
     * 也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情
     * 况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程让其获得锁
     * 4、wait() 需要被try catch包围，以便发生异常中断也可以使wait等待的线程唤醒。
     * 5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
     * 6、notify 和 notifyAll的区别
     * notify方法只唤醒一个等待（对象的）线程并使该线程开始执行。所以如果有多个线程等待一个对象，这个方法只会唤醒
     * 其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。notifyAll 会唤醒所有等待(对象的)线程，尽管哪
     * 一个线程将会第一个处理取决于操作系统的实现。如果当前情况下有多个线程需要被唤醒，推荐使用notifyAll 方法。
     * 比如在生产者-消费者里面的使用，每次都需要唤醒所有的消费者或是生产者，以判断程序是否可以继续往下执行。
     */

    public void tryWait(int num,int need){
        //状态锁
        Object lock = null;
        synchronized (lock){
            //当不满足条件时一直等待，直到满足条件
            while (num<need){
                try {
                    lock.wait();
                }catch (Exception e){

                }
                Log.d(TAG, "tryWait: wake up!");
            }
            //做其他事情
            SystemClock.sleep(2000);
            num = 6;
            need = 5;
            lock.notifyAll();
        }
    }

    /**
     * join:
     * join()方法是Thread类中的一个方法，该方法的定义是等待该线程终止。其实就是join()方法将挂起调用线程的执行，
     * 直到被调用的对象完成它的执行。
     */
    public void tryJoinTest(){
        /**
         * 让123线程顺序执行
         */
        Thread thread1 = new Thread(() -> {
            Log.d(TAG, "tryJoinTest: thread1 running!");
        });
        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
                Log.d(TAG, "tryJoinTest: thread2 running!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
                Log.d(TAG, "tryJoinTest: thread3 running!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread3.start();
        thread2.start();
        thread1.start();
    }

    /**
     * CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。 如果内存位置的值与预期原值相匹配，那么
     * 处理器会自动将该位置值更新为新值 。否则，处理器不做任何操作。无论哪种情况，它都会在 CAS 指令之前返回该
     * 位置的值。（在 CAS 的一些特殊情况下将仅返回 CAS 是否成功，而不提取当前 值。）CAS 有效地说明了“我认为位
     * 置 V 应该包含值 A；如果包含该值，则将 B 放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可。”
     * (即内存上的值如果与A相同代表没改变，则去改变为新值，如果不相同则代表值已经改变，此时便不再去改变值)
     *
     * 通常将 CAS 用于同步的方式是从地址 V 读取值 A，执行多步计算来获得新 值 B，然后使用 CAS 将 V 的值从 A
     * 改为 B。如果 V 处的值尚未同时更改，则 CAS 操作成功。
     *
     * 类似于 CAS 的指令允许算法执行读-修改-写操作，而无需害怕其他线程同时 修改变量，因为如果其他线程修改变量，
     * 那么 CAS 会检测它（并失败），算法 可以对该操作重新计算。
     *
     * CAS存在的问题
     *
     * CAS虽然很高效的解决原子操作，但是CAS仍然存在三大问题。ABA问题，循环时间长开销大和只能保证一个共享变量的原子操作
     *
     * 1.  ABA问题。因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来
     * 是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解
     * 决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A。
     *
     * 从Java1.5开始JDK的atomic包里提供了一个类AtomicStampedReference来解决ABA问题。这个类的compareAndSet
     * 方法作用是首先检查当前引用是否等于预期引用，并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引
     * 用和该标志的值设置为给定的更新值。
     * 关于ABA问题参考文档: http://blog.hesey.net/2011/09/resolve-aba-by-atomicstampedreference.html
     *
     * 2. 循环时间长开销大。自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销。如果JVM能支持处理器提供的
     * pause指令那么效率会有一定的提升，pause指令有两个作用，第一它可以延迟流水线执行指令（de-pipeline）,
     * 使CPU不会消耗过多的执行资源，延迟的时间取决于具体实现的版本，在一些处理器上延迟时间是零。第二它可以
     * 避免在退出循环的时候因内存顺序冲突（memory order violation）而引起CPU流水线被清空
     * （CPU pipeline flush），从而提高CPU的执行效率。
     *
     * 3. 只能保证一个共享变量的原子操作。当对一个共享变量执行操作时，我们可以使用循环CAS的方式来保证原子操作，
     * 但是对多个共享变量操作时，循环CAS就无法保证操作的原子性，这个时候就可以用锁，或者有一个取巧的办法，就是
     * 把多个共享变量合并成一个共享变量来操作。比如有两个共享变量i＝2,j=a，合并一下ij=2a，然后用CAS来操作ij。
     * 从Java1.5开始JDK提供了AtomicReference类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来
     * 进行CAS操作。
     */

    private AtomicInteger atomicInt = new AtomicInteger(100);

    private AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100, 0);

    public void tryAtomicStampedReferenceTest(){
        Thread intT1 = new Thread(new Runnable() {

            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }

        });

        Thread intT2 = new Thread(new Runnable() {

            @Override

            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                //cas成功
                Log.d(TAG, "tryAtomicStampedReferenceTest: " + c3);
            }
        });

        intT1.start();
        intT2.start();
        try {
            intT1.join();
            intT2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread refT1 = new Thread(new Runnable() {

            @Override
            public void run(){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            }

        });

        Thread refT2 = new Thread(new Runnable() {

            @Override
            public void run() {

                int stamp = atomicStampedRef.getStamp();

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                //cas失败
                Log.d(TAG, "tryAtomicStampedReferenceTest: " + c3);
            }
        });

        refT1.start();
        refT2.start();
    }

    /**
     * Executor框架的结构
     * Executor框架的结构主要包括3个部分
     * 1.任务：包括被执行任务需要实现的接口：Runnable接口或Callable接口
     * 2.任务的执行：包括任务执行机制的核心接口Executor，以及继承自Executor的EexcutorService接口。Exrcutor
     * 有两个关键类实现了ExecutorService接口（ThreadPoolExecutor和ScheduledThreadPoolExecutor）。
     * 3.异步计算的结果：包括接口Future和实现Future接口的FutureTask类 图：hpid下executor
     *
     * Extecutor是一个接口，它是Executor框架的基础，它将任务的提交与任务的执行分离开来。
     * ThreadPoolExecutor是线程池的核心实现类，用来执行被提交的任务。
     * ScheduledThreadPoolExecutor是一个实现类，可以在给定的延迟后运行命令，或者定期执行命令。
     * ScheduledThreadPoolExecutor比Timer更灵活，功能更强大。
     * Future接口和实现Future接口的FutureTask类，代表异步计算的结果。
     * Runnable接口和Callable接口的实现类，都可以被ThreadPoolExecutor或者 ScheduledThreadPoolExecutor执行
     * 。区别就是Runnable无法返回执行结果，而Callable可以返回执行结果。 图：hpid下executorservice
     *
     * 主线程首先创建实现Runnable或Callable接口的任务对象，工具类Executors可以把一个Runnable对象封装为一个
     * Callable对象,使用如下两种方式：
     * Executors.callable(Runnable task)或者Executors.callable(Runnable task,Object resule)。
     * 然后可以把Runnable对象直接提交给ExecutorService执行，方法为ExecutorService.execute(Runnable command)；
     * 或者也可以把Runnable对象或者Callable对象提交给ExecutorService执行，方法为ExecutorService.
     * submit(Runnable task)或ExecutorService.submit(Callable<T> task)。这里需要注意的是如果执行
     * ExecutorService.submit(...), ExecutorService将返回一个实现Future接口的对象（其实就是FutureTask）。
     * 当然由于FutureTask实现了Runnable接口，我们也可以直接创建FutureTask，然后提交给ExecutorService执行
     */
    public void tryExecutorsTest(){
        Callable<Object> callable = Executors.callable(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    /**
     * ThreadPoolExecutor:
     * public ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               ThreadFactory threadFactory) {
     *         this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
     *              threadFactory, defaultHandler);
     *     }
     * corePoolSize：线程池的核心线程数，默认情况下，核心线程数会一直在线程池中存活，即使它们处理闲置状态。
     * 如果将ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，那么闲置的核心线程在等待新任务到来时
     * 会执行超时策略，这个时间间隔由keepAliveTime所指定，当等待时间超过keepAliveTime所指定的时长后，核心线程就会被终止。
     * maximumPoolSize：线程池所能容纳的最大线程数量，当活动线程数到达这个数值后，后续的新任务将会被阻塞。
     * keepAliveTime：非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收。当 ThreadPoolExecutor
     * 的allowCoreThreadTimeOut属性设置为true时，keepAliveTime同样会作用于核心线程。
     * unit：用于指定keepAliveTime参数的时间单位，这是一个枚举，常用的有TimeUnit.MILLISECONDS(毫秒)，
     * TimeUnit.SECONDS(秒)以及TimeUnit.MINUTES(分钟)等。
     * workQueue：线程池中的任务队列，通过线程池的execute方法提交Runnable对象会存储在这个队列中。
     * threadFactory：线程工厂，为线程池提供创建新线程的功能。ThreadFactory是一个接口，它只有一个方法：
     * Thread newThread（Runnable r）。
     * 除了上面的参数外还有个不常用的参数，RejectExecutionHandler，这个参数表示当 ThreadPoolExecutor已经关
     * 闭或者 ThreadPoolExecutor已经饱和时（达到了最大线程池大小而且工作队列已经满），execute方法将会调用
     * Handler的rejectExecution方法来通知调用者，默认情况 下是抛出一个RejectExecutionException异常。
     * 了解完相关构造函数的参数，我们再来看看 ThreadPoolExecutor执行任务时的大致规则：
     * （1）如果线程池的数量还未达到核心线程的数量，那么会直接启动一个核心线程来执行任务
     * （2）如果线程池中的线程数量已经达到或者超出核心线程的数量，那么任务会被插入到任务队列中排队等待执行。
     * （3）如果在步骤（2）中无法将任务插入到任务队列中，这往往是由于任务队列已满，这个时候如果线程数量未达到线程
     * 池规定的最大值，那么会立刻启动一个非核心线程来执行任务。
     * （4）如果在步骤（3）中线程数量已经达到线程池规定的最大值，那么就会拒绝执行此任务， ThreadPoolExecutor会
     * 调用 RejectExecutionHandler的 rejectExecution方法来通知调用者。
     * 到此 ThreadPoolExecutor的详细配置了解完了， ThreadPoolExecutor的执行规则也了解完了，那么接下来我们就
     * 来介绍3种常见的线程池，它们都直接或者间接地通过配置 ThreadPoolExecutor来实现自己的功能特性，这个3种线程
     * 池分别是FixedThreadPool，CachedThreadPool，ScheduledThreadPool以及SingleThreadExecutor。
     */


    public void tryThreadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20,
                100, TimeUnit.SECONDS, null, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //线程数量满，接收到通知
            }
        });
        threadPoolExecutor.execute(new MyTestRunable());

        /**
         * FixedThreadPool
         *  FixedThreadPool模式会使用一个优先固定数目的线程来处理若干数目的任务。规定数目的线程处理所有任务，
         *  一旦有线程处理完了任务就会被用来处理新的任务(如果有的话)。FixedThreadPool模式下最多的线程数目是一定的。
         *  public static ExecutorService newFixedThreadPool(int nThreads) {
         *         return new ThreadPoolExecutor(nThreads, nThreads,
         *                                       0L, TimeUnit.MILLISECONDS,
         *                                       new LinkedBlockingQueue<Runnable>());
         *     }
         *  FixedThreadPool的corePoolSize和 maximumPoolSize参数都被设置为nThreads。当线程池中的线程数量
         *  大于 corePoolSize时，keepAliveTime为非核心空闲线程等待新任务的最长时间，超过这个时间后非核心线
         *  程将被终止，这里 keepAliveTime设置为0L，就说明非核心线程会立即被终止。事实上这里也没有非核心线
         *  程创建，因为核心线程数和最大线程数都一样的。 图：fixedthreadpool
         *
         * （1）如果当前运行线程数少corePoolSize，则创建一个新的线程来执行任务。
         * （2）如果当前线程池的运行线程数等于corePoolSize，那么后面提交的任务将加入LinkedBlockingQueue。
         * （3）线程在执行完图中的1后，会在循环中反复从 LinkedBlockingQueue获取任务来执行。
         * 这里还有点要说明的是FixedThreadPool使用的是无界队列 LinkedBlockingQueue作为线程池的工作队列
         * （队列容量为Integer.MAX_VALUE）。使用该队列作为工作队列会对线程池产生如下影响
         * （1）当前线程池中的线程数量达到corePoolSize后，新的任务将在无界队列中等待。
         * （2）由于我们使用的是无界队列，所以参数 maximumPoolSize和keepAliveTime无效。
         * （3）由于使用无界队列，运行中的 FixedThreadPool不会拒绝任务（当然此时是未执行shutdown和shutdownNow方法）
         * ，所以不会去调用 RejectExecutionHandler的 rejectExecution方法抛出异常。
         */

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //三个线程来执行五个任务
        for (int i = 0 ; i<5;i++){
            executorService.execute(new MyTestRunable());
        }

        /**
         * CachedThreadPool:
         * CachedThreadPool首先会按照需要创建足够多的线程来执行任务(Task)。随着程序执行的过程，有的线程执行完
         * 了任务，可以被重新循环使用时，才不再创建新的线程来执行任务。创建方式：
         * public static ExecutorService newCachedThreadPool() {
         *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
         *                                       60L, TimeUnit.SECONDS,
         *                                       new SynchronousQueue<Runnable>());
         *     }
         * 从该静态方法，我们可以看到 CachedThreadPool的corePoolSize被设置为0，而 maximumPoolSize被设置
         * Integer.MAX_VALUE，即 maximumPoolSize是无界的，而keepAliveTime被设置为60L，单位为妙。也就是
         * 空闲线程等待时间最长为60秒，超过该时间将会被终止。而且在这里 CachedThreadPool使用的是没有容量的
         * SynchronousQueue作为线程池的工作队列，但其 maximumPoolSize是无界的，也就是意味着如果主线程提
         * 交任务的速度高于 maximumPoolSize中线程处理任务的速度时 CachedThreadPool将会不断的创建新的线程，
         * 在极端情况下， CachedThreadPool会因为创建过多线程而耗尽CPU和内存资源。 CachedThreadPool 的
         * execute()方法的运行流程：图：hpid下CachedThreadPool
         * （1）首先执行 SynchronousQueue.offer(Runnable task)，添加一个任务。如果当前 CachedThreadPool中
         * 有空闲线程正在执行 SynchronousQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS), 其中NANOSECONDS
         * 是毫微秒即十亿分之一秒（就是微秒/1000），那么主线程执行offer操作与空闲线程执行poll操作配对成功，主线
         * 程把任务交给空闲线程执行，execute()方法执行完成，否则进入第（2）步。
         * （2）当 CachedThreadPool初始线程数为空时，或者当前没有空闲线程，将没有线程去执行 SynchronousQueue
         * .poll(keepAliveTime,TimeUnit.NANOSECONDS)。这样的情况下，步骤（1）将会失败，此时 CachedThreadPool
         * 会创建一个新的线程来执行任务， execute()方法执行完成。
         * （3）在步骤（2）中创建的新线程将任务执行完成后，会执行 SynchronousQueue.poll(keepAliveTime,
         * TimeUnit.NANOSECONDS)，这个poll操作会让空闲线程最多在 SynchronousQueue中等待60秒，如果60秒内主
         * 线程提交了一个新任务，那么这个空闲线程将会执行主线程提交的新任务，否则，这个空闲线程将被终止。由于空
         * 闲60秒的空闲线程会被终止，因此长时间保持空闲的  CachedThreadPool是不会使用任何资源的。
         * 根据前面的分析我们知道 SynchronousQueue是一个没有容量的阻塞队列（其实个人认为是相对应时间而已的没有
         * 容量，因为时间到空闲线程就会被移除）。每个插入操作必须等到一个线程与之对应。 CachedThreadPool使用
         * SynchronousQueue，把主线程的任务传递给空闲线程执行。流程如下：图：CachedThreadPoolliucheng
         *
         */

        ExecutorService executorService1 = Executors.newCachedThreadPool();
        for (int j = 0;j<10;j++){
            executorService1.execute(new MyTestRunable());
        }

        /**
         * SingleThreadExecutor
         * SingleThreadExecutor模式只会创建一个线程。它和FixedThreadPool比较类似，不过线程数是一个。如果多
         * 个任务被提交给SingleThreadExecutor的话，那么这些任务会被保存在一个队列中，并且会按照任务提交的顺序
         * ，一个先执行完成再执行另外一个线程。SingleThreadExecutor模式可以保证只有一个任务会被执行。这种特点
         * 可以被用来处理共享资源的问题而不需要考虑同步的问题。
         * public static ExecutorService newSingleThreadExecutor() {
         *         return new FinalizableDelegatedExecutorService
         *             (new ThreadPoolExecutor(1, 1,
         *                                     0L, TimeUnit.MILLISECONDS,
         *                                     new LinkedBlockingQueue<Runnable>()));
         *     }
         * 从静态方法可以看出SingleThreadExecutor的corePoolSize和maximumPoolSize被设置为1，其他参数则与
         * FixedThreadPool相同。SingleThreadExecutor使用的工作队列也是无界队列LinkedBlockingQueue。由于
         * SingleThreadExecutor采用无界队列的对线程池的影响与FixedThreadPool一样，这里就不过多描述了。同样
         * 的我们先来看看其运行流程：图：hdpi下：singlethreadexecutor
         *  1）如果当前线程数少于corePoolSize即线程池中没有线程运行，则创建一个新的线程来执行任务。
         * （2）在线程池的线程数量等于 corePoolSize时，将任务加入到LinkedBlockingQueue。
         * （3）线程执行完成（1）中的任务后，会在一个无限循环中反复从 LinkedBlockingQueue获取任务来执行。
         */

        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        for (int k = 0 ; k<5 ; k++){
            executorService2.execute(new MyTestRunable());
        }

        /**
         * 各自的适用场景
         * FixedThreadPool：适用于为了满足资源管理需求，而需要限制当前线程的数量的应用场景，它适用于负载比较重的服务器。
         * SingleThreadExecutor：适用于需要保证执行顺序地执行各个任务；并且在任意时间点，不会有多个线程是活动的场景。
         * CachedThreadPool：大小无界的线程池，适用于执行很多的短期异步任务的小程序，或者负载较轻的服务器。
         */

        /**
         * ScheduledThreadPoolExecutor浅析
         * 3.1 ScheduledThreadPoolExecutor执行机制分析
         * ScheduledThreadPoolExecutor继承自ThreadPoolExecutor。它主要用来在给定的延迟之后执行任务，或者定
         * 期执行任务。 ScheduledThreadPoolExecutor的功能与Timer类似，但比Timer更强大，更灵活，Timer对应的
         * 是单个后台线程，而 ScheduledThreadPoolExecutor可以在构造函数中指定多个对应的后台线程数。接下来我们
         * 先来了解一下 ScheduledThreadPoolExecutor的运行机制：图：scheduledthreadpoolexecutor
         * DelayQueue是一个无界队列，所以ThreadPoolExecutor的maximumPoolSize在 ScheduledThreadPoolExecutor
         * 中无意义。 ScheduledThreadPoolExecutor的执行主要分为以下两个部分
         * （1）当调用 ScheduledThreadPoolExecutor的scheduleAtFixedRate()方法或者scheduleWithFixedDelay()
         * 方法时，会向 ScheduledThreadPoolExecutor的DelayQueue添加一个实现了RunnableScheduledFuture接口的
         * ScheduleFutureTask。
         * （2）线程池中的线程从DelayQueue中获取 ScheduleFutureTask，然后执行任务。
         *
         * 创建ScheduledThreadPoolExecutor:
         * 1）ScheduledThreadPoolExecutor：可以执行并行任务也就是多条线程同时执行。
         * （2）SingleThreadScheduledExecutor：可以执行单条线程。
         *
         * ScheduledThreadPoolExecutor和SingleThreadScheduledExecutor的适用场景
         * ScheduledThreadPoolExecutor：适用于多个后台线程执行周期性任务，同时为了满足资源管理的需求而需要限制后台线程数量的应用场景。
         * SingleThreadScheduledExecutor：适用于需要单个后台线程执行周期任务，同时需要保证任务顺序执行的应用场景。
         *
         * ScheduledThreadPoolExecutor使用案例
         * 我们创建一个Runnable的对象，然后使用 ScheduledThreadPoolExecutor的 Scheduled()来执行延迟任务，输出执行时间即可:
         * 我们先来介绍一下该类延迟执行的方法：public ScheduledFuture<?> schedule(Runnable command,long delay, TimeUnit unit);
         * 参数解析：
         * command：就是一个实现Runnable接口的类
         * delay：延迟多久后执行。
         * unit： 用于指定keepAliveTime参数的时间单位，这是一个枚举，常用的有TimeUnit.MILLISECONDS(毫秒)，
         * TimeUnit.SECONDS(秒)以及TimeUnit.MINUTES(分钟)等。
         * 这里要注意这个方法会返回ScheduledFuture实例，可以用于获取线程状态信息和延迟时间。
         */

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        for (int r = 0 ; r<5;r++){
            //延时十秒执行
            scheduledExecutorService.schedule(new MyTestRunable(),10,TimeUnit.SECONDS);
            // 设定可以循环执行的runnable,初始延迟为0，这里设置的任务的间隔为5秒
            /**
             * scheduleWithFixedDelay方法的作用是预定在初始的延迟结束后周期性地执行给定任务，在一次调用完成和
             * 下一次调用开始之间有长度为delay的延迟， 其中initialDelay为初始延迟（简单说是是等上一个任务结束后
             * ，在等固定的时间，然后执行。即：执行完上一个任务后再执行）。
             */
            scheduledExecutorService.scheduleAtFixedRate(new MyTestRunable(),0,5,TimeUnit.SECONDS);
        }
    }
}
