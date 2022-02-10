package com.example.myapplication.shejimoshi;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  访问者模式
 *  <p>
 *  访问者（Visitor）模式的定义：将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构
 *  的前提下可以添加作用于这些元素的新的操作，为数据结构中的每个元素提供多种访问方式。它将对数据的操作与数据结构
 *  进行分离，是行为类模式中最复杂的一种模式。
 *  <p>
 * 访问者（Visitor）模式是一种对象行为型模式，其主要优点如下。
 * 扩展性好。能够在不修改对象结构中的元素的情况下，为对象结构中的元素添加新的功能。
 * 复用性好。可以通过访问者来定义整个对象结构通用的功能，从而提高系统的复用程度。
 * 灵活性好。访问者模式将数据结构与作用于结构上的操作解耦，使得操作集合可相对自由地演化而不影响系统的数据结构。
 * 符合单一职责原则。访问者模式把相关的行为封装在一起，构成一个访问者，使每一个访问者的功能都比较单一。
 *  <p>
 * 访问者（Visitor）模式的主要缺点如下。
 * 增加新的元素类很困难。在访问者模式中，每增加一个新的元素类，都要在每一个具体访问者类中增加相应的具体操作，
 * 这违背了“开闭原则”。
 * 破坏封装。访问者模式中具体元素对访问者公布细节，这破坏了对象的封装性。
 * 违反了依赖倒置原则。访问者模式依赖了具体类，而没有依赖抽象类。
 * <p>
 * 实现：
 * 访问者（Visitor）模式实现的关键是如何将作用于元素的操作分离出来封装成独立的类，其基本结构与实现方法如下。
 * 1. 模式的结构
 * 访问者模式包含以下主要角色。
 * 抽象访问者（Visitor）角色：定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作 visit() ，该操作中的
 * 参数类型标识了被访问的具体元素。
 * 具体访问者（ConcreteVisitor）角色：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
 * 抽象元素（Element）角色：声明一个包含接受操作 accept() 的接口，被接受的访问者对象作为 accept() 方法的参数。
 * 具体元素（ConcreteElement）角色：实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visit(this) ，
 * 另外具体元素中可能还包含本身业务逻辑的相关操作。
 * 对象结构（Object Structure）角色：是一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常
 * 由 List、Set、Map 等聚合类实现。
 */
public class VisitorTest {

    public static final String TAG = VisitorTest.class.toString();

    public void trytest(){
        ConcreteStructure structure = new ConcreteStructure();
        Visitor visitor = new ConcreteVisitorOne();
        Element concreteElementOne = new ConcreteElementOne();
        Element concreteElementTwo = new ConcreteElementTwo();
        structure.add(concreteElementOne);
        structure.add(concreteElementTwo);
        structure.accept(visitor);
        Log.d(TAG, "-----------------------------------------------------------");
        Visitor concreteVisitorTwo = new ConcreteVisitorTwo();
        structure.accept(concreteVisitorTwo);
    }

    private void tryCompanyTest(){
        SetMaterial setMaterial = new SetMaterial();
        Material paper = new Paper();
        Material cuprum = new Cuprum();
        setMaterial.add(paper);
        Company company = new ArtCompany();
        setMaterial.accept(company);
    }

    public interface Visitor{
        void visit(ConcreteElementOne concreteElementOne);
        void visit(ConcreteElementTwo concreteElementTwo);
    }

    public static class ConcreteVisitorOne implements Visitor{

        @Override
        public void visit(ConcreteElementOne concreteElementOne) {
            Log.d(TAG, "visit: this is ConcreteVisitorOne " + concreteElementOne.doSomeThing());
        }

        @Override
        public void visit(ConcreteElementTwo concreteElementTwo) {
            Log.d(TAG, "visit: this is ConcreteVisitorOne " + concreteElementTwo.doSomeThing());
        }
    }

    public static class ConcreteVisitorTwo implements Visitor{

        @Override
        public void visit(ConcreteElementOne concreteElementOne) {
            Log.d(TAG, "visit: this is ConcreteVisitorTwo " + concreteElementOne.doSomeThing());
        }

        @Override
        public void visit(ConcreteElementTwo concreteElementTwo) {
            Log.d(TAG, "visit: this is ConcreteVisitorTwo " + concreteElementTwo.doSomeThing());
        }
    }

    public interface Element{
        void accept(Visitor visitor);
    }

    public static class ConcreteElementOne implements Element{

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public String doSomeThing(){
            return "this is one do some things";
        }
    }

    public static class ConcreteElementTwo implements Element{

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public String doSomeThing(){
            return "this is two do some things";
        }
    }

    public static class ConcreteStructure{
        private List<Element> list;

        public ConcreteStructure() {
            list = new ArrayList<>();
        }

        public void add(Element e){
            list.add(e);
        }

        public void remove(Element e){
            list.remove(e);
        }

        public void accept(Visitor visitor){
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                ((Element)iterator.next()).accept(visitor);
            }
        }
    }

    /**
     * 例1.利用“访问者（Visitor）模式”模拟艺术公司与造币公司的功能。
     *
     * 分析：艺术公司利用“铜”可以设计出铜像，利用“纸”可以画出图画；造币公司利用“铜”可以印出铜币，利用“纸”可以印
     * 出纸币（点此下载运行该程序后所要显示的图片）。对“铜”和“纸”这两种元素，两个公司的处理方法不同，
     * 所以该实例用访问者模式来实现比较适合。
     *
     * 首先，定义一个公司（Company）接口，它是抽象访问者，提供了两个根据纸（Paper）或铜（Cuprum）这两种元素
     * 创建作品的方法；再定义艺术公司（ArtCompany）类和造币公司（Mint）类，它们是具体访问者，实现了父接口的方法。
     *
     * 然后，定义一个材料（Material）接口，它是抽象元素，提供了 accept（Company visitor）方法来接受访问者
     * （Company）对象访问；再定义纸（Paper）类和铜（Cuprum）类，它们是具体元素类，实现了父接口中的方法。
     *
     * 最后，定义一个材料集（SetMaterial）类，它是对象结构角色，拥有保存所有元素的容器 List，并提供让访问者对象
     * 遍历容器中的所有元素的 accept（Company visitor）方法；客户类设计成窗体程序，它提供材料集（SetMaterial）
     * 对象供访问者（Company）对象访问，实现了 ItemListener 接口，处理用户的事件请求。
     */

    public interface Company{
        void usePaper(Paper paper);
        void useCuprum(Cuprum cuprum);
    }

    public static class ArtCompany implements Company{

        @Override
        public void usePaper(Paper paper) {
            Log.d(TAG, "usePaper: this is ArtCompany use pager draw");
        }

        @Override
        public void useCuprum(Cuprum cuprum) {
            Log.d(TAG, "usePaper: this is ArtCompany use cuprum make cuprum");
        }
    }

    public static class Mint implements Company{

        @Override
        public void usePaper(Paper paper) {
            Log.d(TAG, "usePaper: this is Mint use paper make money");
        }

        @Override
        public void useCuprum(Cuprum cuprum) {
            Log.d(TAG, "usePaper: this is Mint use cuprum make money");
        }
    }

    public interface Material{
        void accept(Company company);
    }

    public static class Paper implements Material{

        @Override
        public void accept(Company company) {
            company.usePaper(this);
        }
    }

    public static class Cuprum implements Material{

        @Override
        public void accept(Company company) {
            company.useCuprum(this);
        }
    }

    public static class SetMaterial{
        private List<Material> list;

        public SetMaterial() {
            list = new ArrayList<>();
        }

        public void add(Material m){
            list.add(m);
        }

        public void accept(Company company){
            Iterator iterator = list.iterator();
            while (iterator.hasNext()){
                ((Material)iterator.next()).accept(company);
            }
        }
    }


}
