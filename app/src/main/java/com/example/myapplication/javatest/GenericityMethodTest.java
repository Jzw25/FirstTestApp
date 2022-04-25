package com.example.myapplication.javatest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型方法
 * 原则：无论何时，如果你能做到，你就该尽量使用泛型方法。也就是说，如果使用泛型方法将整个类泛型化，那么就应该使用
 * 泛型方法。另外对于一个static的方法而已，无法访问泛型类型的参数。所以如果static方法要使用泛型能力，就必须使其
 * 成为泛型方法。
 */
public class GenericityMethodTest<T> {

    public static final String TAG = GenericityMethodTest.class.toString();

    public void tryTest(){
        GenericityMethodTest<Person> test = new GenericityMethodTest();
        test.show1(new Man());
        test.show1(new Person());
//        test.show1(new Brid());

        test.show2(new Brid());
        test.show2(new Man());

        test.show3(new Brid());
        test.show3(new Man());
    }

    public void tryPrint(){
        printMsg("111",222,"asdasd",55.55);
    }

    public static class Person{

        @Override
        public String toString() {
            return "Person{}";
        }
    }

    public static class Man extends Person{
        @Override
        public String toString() {
            return "Man{}";
        }
    }

    public static class SmallMan extends Man{
        @Override
        public String toString() {
            return "SmallMan{}";
        }
    }

    public static class Brid{
        @Override
        public String toString() {
            return "Brid{}";
        }
    }

    public void show1(T t){
        Log.d(TAG, "show1: "+t.toString());
    }

    /**
     * 再泛型类中声明泛型方法T，此T与泛型类的不同，是一种全新的类型，不与泛型类的T相同
     * @param t
     * @param <T>
     */
    public <T> void show2(T t){
        Log.d(TAG, "show2: " + t.toString());
    }

    public <E> void show3(E e){
        Log.d(TAG, "show3: " + e.toString());
    }

    /**
     * 可变参数泛型方法
     * @param agrs
     * @param <T>
     */
    public <T> void printMsg(T... agrs){
        for (T t : agrs){
            Log.d(TAG, "printMsg: t is : " +t);
        }
    }

    /**
     * 静态方法使用泛型必须声明为泛型方法，且不能使用泛型类定义的泛型
     * @param t
     * @param <T>
     */
    public static <T> void  mustNeedMethod(T t){

    }

    public <T extends Person> T isPerson(T t){
        return t;
    }

//    public <T super Man> T isMan(T t){
//        return t;
//    }

    public void ishsh(){
        /**
         * 协变
         * extends指出了泛型的上界为Fruit，<? extends T>称为子类通配符，意味着某个继承自Fruit的具体类型。
         *  使用通配符可以将ArrayList<Apple>向上转型了，也就实现了协变。
         *
         *  你无法将任何数据添加进数组，add方法失效
         *  List<? extends Fruit>也可以合法的指向一个List<Orange>，显然往里面放Apple、Fruit、Object都是非
         *  法的。编译器不知道List<? extends Fruit>所持有的具体类型是什么，所以一旦执行这种类型的向上转型，
         *  你就将丢失掉向其中传递任何对象的能力。
         *
         * 类比数组，尽管你可以把Apple[]向上转型成Fruit[]，然而往里面添加Fruit和Orange等对象都是非法的，会在
         * 运行时抛出ArrayStoreException异常。泛型把类型检查移到了编译期，协变过程丢掉了类型信息，编译器拒绝
         * 所有不安全的操作。
         *
         */
        List<? extends Man> list = new ArrayList<>();
        /**
         * 逆变
         * List<? extends Fruit>也可以合法的指向一个List<Orange>，显然往里面放Apple、Fruit、Object都是非法
         * 的。编译器不知道List<? extends Fruit>所持有的具体类型是什么，所以一旦执行这种类型的向上转型，你就将
         * 丢失掉向其中传递任何对象的能力。
         *
         * 类比数组，尽管你可以把Apple[]向上转型成Fruit[]，然而往里面添加Fruit和Orange等对象都是非法的，会在
         * 运行时抛出ArrayStoreException异常。泛型把类型检查移到了编译期，协变过程丢掉了类型信息，编译器拒绝
         * 所有不安全的操作。
         *
         *
         * 在testExtends方法中，因为泛型中用的是extends，在向list中存放元素的时候，我们并不能确定List中的元素的具体类型，
         * 即可能是Apple也可能是Banana。因此调用add方法时，不论传入new Apple()还是new Banana()，都会出现编译错误。
         *
         * 理解了extends之后，再看super就很容易理解了，即我们不能确定testSuper方法的参数中的泛型是Fruit的哪个父类，
         * 因此在调用get方法时只能返回Object类型。结合extends可见，在获取泛型元素时，使用extends获取到的是泛型中的
         * 上边界的类型(本例子中为Fruit),范围更小。
         */
        List<? super Man> listst = new ArrayList<>();

        //<? extends Apple>限制了get方法返回的类型必须是Apple及其父类型。
        Man man = list.get(0);
        Person person = list.get(0);

        //add的时候只能addsupre定义的类或者其子类，在初始化的时候添加就是原本的定义，只能是其类或者其父类
        listst.add(new Man());
        listst.add(new SmallMan());
        Object object = listst.get(0);

    }

    /**
     * 自限定:
     *新类Subtype接受的参数和返回的值具有Subtype类型而不仅仅是基类BasicHolder类型。所以自限定类型的本质就是：
     * 基类用子类代替其参数。这意味着泛型基类变成了一种其所有子类的公共功能模版，但是在所产生的类中将使用确切类型
     * 而不是基类型。因此，Subtype中，传递给set()的参数和从get() 返回的类型都确切是Subtype。
     * @param <T>
     */
    public class BasicHolder<T> {
        T element;
        void set(T arg) { element = arg; }
        T get() { return element; }
        void f() {
            System.out.println(element.getClass().getSimpleName());
        }
    }

    // CRGWithBasicHolder.java
    class Subtype extends BasicHolder<Subtype> {}

    public void CRGWithBasicHolder() {
        Subtype st1 = new Subtype(), st2 = new Subtype();
        st1.set(st2);
        Subtype st3 = st1.get();
        st1.f();

    }

    /**
     * 继承自定义类型基类的子类将产生确切的子类型作为其返回值
     * @param <T>
     */
    interface GenericsGetter<T extends GenericsGetter<T>> {
        T get();
    }

    interface Getter extends GenericsGetter<Getter> {}

    public void GenericsAndReturnTypes(Getter g) {
        Getter result = g.get();
        GenericsGetter genericsGetter = g.get();

    }

    /**
     * 协变参数类型
     * 在使用自限定类型时，在子类中只有一个方法，并且这个方法接受子类型而不是基类型为参数。
     * @param <T>
     */
    interface SelfBoundSetter<T extends SelfBoundSetter<T>> {
        void set(T args);
    }

    interface Setter extends SelfBoundSetter<Setter> {}

    public void SelfBoundAndCovariantArguments(Setter s1, Setter s2, SelfBoundSetter sbs) {
        s1.set(s2);
//        s1.set(sbs);  // 编译错误
    }

    /**
     * 捕获转换
     * <?>被称为无界通配符，无界通配符还有一个特殊的作用，如果向一个使用<?>的方法传递原生类型，那么对编译期来说，
     * 可能会推断出实际的参数类型，使得这个方法可以回转并调用另一个使用这个确切类型的方法。这种技术被称为捕获转换。
     */

}
