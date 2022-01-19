package com.example.myapplication.shejimoshi;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 原型模式：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象
 * 1.浅克隆（创建一个新对象，新对象的属性和原来对象完全相同，对于非基本类型属性，仍指向原有属性所指向的对象的内存）
 * 2.深克隆（创建一个新对象，属性中引用的其他对象也会被克隆，不再指向原有对象地址。）
 * java中已经有clone浅克隆方法，实现Cloneable接口即可
 */
public class PrototypeTest implements Cloneable{

    public PrototypeTest() {

    }

    /**
     * 浅克隆方法
     * @return 浅克隆对象
     * @throws CloneNotSupportedException
     */
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 克隆修改,利用对象克隆，在使用内部set方法修改对象数据
     */
    public static class changeCloneTest implements Cloneable{
        public  static final String TAG = changeCloneTest.class.toString();
        private String name;
        private String age;
        private String classs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getClasss() {
            return classs;
        }

        public void setClasss(String classs) {
            this.classs = classs;
        }

        @Override
        public String toString() {
            return "changeCloneTest{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    ", classs='" + classs + '\'' +
                    '}';
        }

        public void display(){
            Log.d(TAG, "display: "+toString());
        }

        @NonNull
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
