package com.example.myapplication.javatest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型测试
 */
public class GenericityTest {

    public void tryTest(){
        FanXingShang shang = new FanXingShang();
        shang.setKey(new GenericityMethodTest.Man());
        shang.setKey(new GenericityMethodTest.Person());
    }

    /**
     * 泛型接口
     */
    public interface Generictor<T>{
        T next();
    }

    /**
     * 未指定类型泛型实现类
     * 此时需要将泛型的申明也加入到类中，否则会报错unknown class
     */
    public static class ConGenerictor<T> implements Generictor<T>{

        @Override
        public T next() {
            return null;
        }
    }

    /**
     * 指定类型的泛型实现类
     *
     * 可由此产生多种不同类型的类，实现接口时的方法自动转成传入类型
     */

    public static class ConstenConGenerictor implements Generictor<String>{

        @Override
        public String next() {
            return null;
        }
    }

    /**
     * 泛型上下边界
     * 在使用泛型的时候，我们还可以为传入的泛型类型实参进行上下边界的限制，如：类型实参只准传入某种类型的父类或某
     * 种类型的子类。
     */
    public static class FanXingShang <T extends GenericityMethodTest.Person>{
        //为泛型添加上边界，即传入的类型实参必须是指定类型的子类型
        private T key;

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        /**
         * 泛型方法增加上下边界必须在权限声明和返回值之间增加否则报错如
         * （public <T> T show(FanXingShang<T extends Person>)）
         * @param t
         * @param <T>
         * @return
         */
        public <T extends GenericityMethodTest.Person> T show(FanXingShang<T> t){
                return t.getKey();
        }
    }
    /**
     * 在java中是”不能创建一个确切的泛型类型的数组”的
     *
     * 这种情况下，由于JVM泛型的擦除机制，在运行时JVM是不知道泛型信息的，所以可以给oa[1]赋上一个ArrayList而不
     * 会出现异常，但是在取出数据的时候却要做一次类型转换，所以就会出现ClassCastException，如果可以进行泛型数
     * 组的声明，上面说的这种情况在编译期将不会出现任何的警告和错误，只有在运行时才会出错。
     *
     * 而对泛型数组的声明进行限制，对于这样的情况，可以在编译期提示代码有类型安全问题，比没有任何提示要强很多。
     * ————————————————
     * 下面采用通配符的方式是被允许的:数组的类型不可以是类型变量，除非是采用通配符的方式，因为对于通配符的方式，
     * 最后取出数据是要做显式的类型转换的。
     */

    /**
     * 泛型擦除机制
     * Java中的泛型基本上都是在编译器这个层次来实现的。在生成的Java字节码中是不包含泛型中的类型信息的。使用泛型
     * 的时候加上的类型参数，会在编译器在编译的时候去掉。这个过程就称为类型擦除。
     *
     * 如在代码中定义的List<object>和List<String>等类型，在编译后都会编程List。JVM看到的只是List，而由泛型附
     * 加的类型信息对JVM来说是不可见的。Java编译器会在编译时尽可能的发现可能出错的地方，但是仍然无法避免在运行时
     * 刻出现类型转换异常的情况。类型擦除也是Java的泛型实现方法与C++模版机制实现方式之间的重要区别。
     *
     * **可以通过反射越过泛型检查，即通过反射获取LIST<string>的对象可将int类型的参数添加到
     */
    public void showList(){
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();

        //会是true
        /**
         * 在这个例子中，我们定义了两个ArrayList数组，不过一个是ArrayList<String>泛型类型，只能存储字符串。
         * 一个是ArrayList<Integer>泛型类型，只能存储整形。最后，我们通过arrayList1对象和arrayList2对象的
         * getClass方法获取它们的类的信息，最后发现结果为true。说明泛型类型String和Integer都被擦除掉了，
         * 只剩下了原始类型。
         */
        boolean isSame = arrayList.getClass()==integerArrayList.getClass();

        /**
         * 在程序中定义了一个ArrayList泛型类型实例化为Integer的对象，如果直接调用add方法，那么只能存储整形的
         * 数据。不过当我们利用反射调用add方法的时候，却可以存储字符串。这说明了Integer泛型实例在编译之后被
         * 擦除了，只保留了原始类型。
         *
         * 如果是T无限类型变量，就会用object替换，其结果就是一个普通的类，如同泛型加入java变成语言之前已经实现
         * 的那样。在程序中可以包含不同类型的Pair，如Pair<String>或Pair<Integer>，但是，擦除类型后它们就成为
         * 原始的Pair类型了，原始类型都是Object。ArrayList<Integer>被擦除类型后，原始类型也变成了Object，
         * 所以通过反射我们就可以存储字符串了。
         *
         * 如果类型变量有限定，那么原始类型就用第一个边界的类型变量来替换。比如public class Pair<T extends Serializable&Comparable> {}
         * 那么原始类型就是Comparable
         * 而编译器在必要的时要向Comparable插入强制类型转换。为了提高效率，应该将标签（tagging）接口（即没有方法的接口）放在边界限定列表的末尾。
         */
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.add(23);
        try {
            arrayList1.getClass().getMethod("add",Object.class).invoke(arrayList1,"123");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /**
         * new ArrayList()只是在内存中开辟一个存储空间，可以存储任何的类型对象。而真正涉及类型检查的是它的引用，
         * 因为我们是使用它引用arrayList1 来调用它的方法，比如说调用add()方法。所以arrayList1引用能完成泛型类型的检查。
         *
         * 而引用arrayList2没有使用泛型，所以不行。
         */
        ArrayList arrayList2 = new ArrayList<String>();
        arrayList2.add(123);
        arrayList2.add("111");
        Object o = arrayList2.get(0);
    }

    //这是一个简单的泛型方法
    public static <T> T add(T x,T y){
        return y;
    }

    public void showAdd(){
        /**不指定泛型的时候*/
        int i= add(1, 2); //这两个参数都是Integer，所以T为Integer类型
        Number f= add(1, 1.2);//这两个参数一个是Integer，以风格是Float，所以取同一父类的最小级，为Number
        Object o= add(1, "asd");//这两个参数一个是Integer，以风格是Float，所以取同一父类的最小级，为Object

        /**指定泛型的时候*/
        int a=GenericityTest.<Integer>add(1, 2);//指定了Integer，所以只能为Integer类型或者其子类
//        int b=GenericityTest.<Integer>add(1, 2.2);//编译错误，指定了Integer，不能为Float
        Number c=GenericityTest.<Number>add(1, 2.2); //指定为Number，所以可以为Integer和Float
    }

    /**
     * 桥方法：编译器自己生成的桥方法，参数为object，内部调用我们自己的方法
     * 子类中的巧方法  Object   getValue()和Date getValue()是同 时存在的，可是如果是常规的两个方法，他们的
     * 方法签名是一样的，也就是说虚拟机根本不能分别这两个方法。如果是我们自己编写Java代码，这样的代码是无法通过
     * 编译器的检查的，但是虚拟机却是允许这样做的，因为虚拟机通过参数类型和返回类型来确定一个方法，所以编译器为
     * 了实现泛型的多态允许自己做这个看起来“不合法”的事情，然后交给虚拟器去区别。
     *
     * 编译时此不合法,虚拟机根据参数与返回值确定一个方法，所以虚拟机里是合法的
     * //    public String aaa(){
     * //        return "aaa";
     * //    }
     * //
     * //    public Object aaa(){
     * //        return "";
     * //    }
     */

    /**
     * 4、泛型类型变量不能是基本数据类型
     *
     * 不能用类型参数替换基本类型。就比如，没有ArrayList<double>，只有ArrayList<Double>。因为当类型擦除后，
     * ArrayList的原始类型变为Object，但是Object类型不能存储double值，只能引用Double的值。
     */


    /**
     * Rxjava中泛型利用：
     */

    public void showRxjava(){
        Observer<GenericityMethodTest.Man> implObservable = ImplObservable.create(new GenericityMethodTest.Man());
    }

    interface Observer<T>{
        <R> Observer<R> map(Func1<R,T> func1);
    }

    public static class ImplObservable<T> implements Observer{
        private T t;

        public ImplObservable(T t) {
            this.t = t;
        }

        public static <T> Observer create(T t){
            return new ImplObservable<T>(t);

        }

        @Override
        public Observer map(Func1 func1) {
            return null;
        }

//        @Override
//        public Observer map(Func1 func1) {
//            Observer<R> observer = ImplObservable.create(func1.call(T));
//            return observer;
//        }
    }

    interface Func1<R,T>{
        R call(T t);
    }

}
