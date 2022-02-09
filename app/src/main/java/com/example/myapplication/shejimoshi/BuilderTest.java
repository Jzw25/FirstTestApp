package com.example.myapplication.shejimoshi;

/**
 * 建造者模式
 * 1. 模式的结构
 * 建造者（Builder）模式的主要角色如下。
 * 产品角色（Product）：它是包含多个组成部件的复杂对象，由具体建造者来创建其各个零部件。
 * 抽象建造者（Builder）：它是一个包含创建产品各个子部件的抽象方法的接口，通常还包含一个返回复杂产品的方法 getResult()。
 * 具体建造者(Concrete Builder）：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。
 * 指挥者（Director）：它调用建造者对象中的部件构造与装配方法完成复杂对象的创建，在指挥者中不涉及具体产品的信息。
 */
public class BuilderTest {

    public BuilderTest(){
        Builder builder = new ProductBuilder();
        DirectorTest directorTest = new DirectorTest(builder);
        BuilderProduct builderProduct = directorTest.buildProduct();
        builderProduct.toString();
    }

    //具体产品
    public static class BuilderProduct{
        private String name;
        private String longer;
        private String color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLonger() {
            return longer;
        }

        public void setLonger(String longer) {
            this.longer = longer;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "BuilderProduct{" +
                    "name='" + name + '\'' +
                    ", longer='" + longer + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    //建造抽象类
    public static abstract class Builder{
        //维护一个产品，一个方法返回产品
        public BuilderProduct product = new BuilderProduct();

        abstract void setName();

        abstract void setLonger();

        abstract void setColor();

        public BuilderProduct getProduct(){
            return product;
        }
    }

    //具体实现的建造者

    public static class ProductBuilder extends Builder{

        @Override
        void setName() {
            product.setName("产品1");
        }

        @Override
        void setLonger() {
            product.setLonger("10m");
        }

        @Override
        void setColor() {
            product.setColor("蓝色");
        }
    }

    public static class DirectorTest{
       private Builder builder;

       public DirectorTest(Builder builder){
           this.builder = builder;
       }

       public BuilderProduct buildProduct(){
           builder.setColor();
           builder.setLonger();
           builder.setName();
           return builder.getProduct();
       }

    }
}
