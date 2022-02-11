package com.example.myapplication.javatest;

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
}
