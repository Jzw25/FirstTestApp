package com.example.myapplication.javatest;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
    private AtomicInteger atomicInteger;

    public void tryAtmoicReference(){
        reference = new AtomicReference<>();
        reference.set(A);

    }

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
}
