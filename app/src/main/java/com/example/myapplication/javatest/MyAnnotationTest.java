package com.example.myapplication.javatest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * RetentionPolicy.RUNTIME 由JVM 加载，包含在类文件中，在运行时可以被获取到
 * ElementType.TYPE 应用于类、接口（包括注解类型）、枚举
 * ElementType.FIELD 应用于属性（包括枚举中的常量）
 * Documented  表明该注解标记的元素可以被Javadoc 或类似的工具文档化
 * Inherited 表明使用了@Inherited注解的注解，所标记的类的子类也会拥有这个注解
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnnotationTest {
    boolean isChange() default false;
    String value() default "jzw";
    boolean isCheck();

    /**
     * 注解元素的默认值：
     *注解元素必须有确定的值，要么在定义注解的默认值中指定，要么在使用注解时指定，非基本类型的注解元素的值不可为
     * null。因此, 使用空字符串或0作为默认值是一种常用的做法。这个约束使得处理器很难表现一个元素的存在或缺失的
     * 状态，因为每个注解的声明中，所有元素都存在，并且都具有相应的值，为了绕开这个约束，我们只能定义一些特殊的
     * 值，例如空字符串或者负数，一次表示某个元素不存在，在定义注解时，这已经成为一个习惯用法。
     */
    @Target({ElementType.FIELD,ElementType.TYPE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AnDefault{
        String value() default "";
        int id() default 0;
    }

    @Target(ElementType.TYPE_PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test{}

    class PrarTest<@Test T>{ }

    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UseTest{ }

    class A{}
    class B extends @UseTest A{
        public @UseTest int add(@UseTest int a){
            @UseTest int result = 0;
            return result;
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MethodTest{
        String value() default "no vaule";
    }
}
/**
 * Target类型	描述
 * ElementType.TYPE	应用于类、接口（包括注解类型）、枚举
 * ElementType.FIELD	应用于属性（包括枚举中的常量）
 * ElementType.METHOD	应用于方法
 * ElementType.PARAMETER	应用于方法的形参
 * ElementType.CONSTRUCTOR	应用于构造函数
 * ElementType.LOCAL_VARIABLE	应用于局部变量
 * ElementType.ANNOTATION_TYPE	应用于注解类型
 * ElementType.PACKAGE	应用于包
 * ElementType.TYPE_PARAMETER	1.8版本新增，应用于类型变量）
 * ElementType.TYPE_USE	1.8版本新增，应用于任何使用类型的语句中（例如声明语句、泛型和强制转换语句中的类型）
 *
 * 不写的话，好像默认各个位置都是可以的
 *
 * ————————————————
 * 生命周期类型	描述
 * RetentionPolicy.SOURCE	编译时被丢弃，不包含在类文件中
 * RetentionPolicy.CLASS	JVM加载时被丢弃，包含在类文件中，默认值
 * RetentionPolicy.RUNTIME	由JVM 加载，包含在类文件中，在运行时可以被获取到(可以通过反射获取到该注解。)
 *
 * @Override，限定重写父类方法 这个annotaton常常在我们试图覆盖父类方法而确又写错了方法名时发挥威力。
 * @Deprecated，标记已过时：当一个类型或者类型成员使用@Deprecated修饰的话，编译器将不鼓励使用这个被标注的程序
 * 元素。而且这种修饰具有一定的 “延续性”：如果我们在代码中通过继承或者覆盖的方式使用了这个过时的类型或者成员，
 * 虽然继承或者覆盖后的类型或者成员并不是被声明为 @Deprecated，但编译器仍然要报警。
 *SuppressWarnnings，抑制编译器警告：SuppressWarning不是一个标记注解。它有一个类型为String[]的成员，这个成员
 * 的值为被禁止的警告名。对于javac编译器来讲，被-Xlint选项有效的警告 名也同样对@SuppressWarings有效，同时编译
 * 器忽略掉无法识别的警告名。
 *  SuppressWarnings注解的常见参数值的简单说明：
 *
 * 　　　　1.deprecation：使用了不赞成使用的类或方法时的警告；
 * 　　　　2.unchecked：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型;
 * 　　　　3.fallthrough：当 Switch 程序块直接通往下一种情况而没有 Break 时的警告;
 * 　　　　4.path：在类路径、源文件路径等中有不存在的路径时的警告;
 * 　　　　5.serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告;
 * 　　　　6.finally：任何 finally 子句不能正常完成时的警告;
 * 　　　　7.all：关于以上所有情况的警告。
 */
