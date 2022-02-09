package com.example.myapplication.shejimoshi;

import android.util.Log;

/**
 * 状态模式
 * 状态模式把受环境改变的对象行为包装在不同的状态对象里，其意图是让一个对象在其内部状态改变的时候，其行为也随之改变。现在我们来分析其基本结构和实现方法。
 * 1. 模式的结构
 * 状态模式包含以下主要角色。
 * 环境类（Context）角色：也称为上下文，它定义了客户端需要的接口，内部维护一个当前状态，并负责具体状态的切换。
 * 抽象状态（State）角色：定义一个接口，用以封装环境对象中的特定状态所对应的行为，可以有一个或多个行为。
 * 具体状态（Concrete State）角色：实现抽象状态所对应的行为，并且在需要的情况下进行状态切换。
 */
public class StateTest {

    /**
     * 用“状态模式”设计一个多线程的状态转换程序。
     *
     * 分析：多线程存在 5 种状态，分别为新建状态、就绪状态、运行状态、阻塞状态和死亡状态现在先定义一个抽象状态类（TheadState），
     * 每个状态设计一个具体状态类，它们是新建状态（New）、就绪状态（Runnable ）、运行状态（Running）、阻塞状态（Blocked）
     * 和死亡状态（Dead），每个状态中有触发它们转变状态的方法，环境类（ThreadContext）中先生成一个初始状态（New），并提供相关触发方法
     */

    public static final String TAG = StateTest.class.toString();

    public void tryTest(){
        TheadContext context = new TheadContext();
        context.start();
        context.run();
        context.block();
        context.resume();
        context.run();
        context.dead();
    }

    public static class TheadState{
        private String TheadName;

        public String getTheadName() {
            return TheadName;
        }

        public void setTheadName(String theadName) {
            TheadName = theadName;
        }
    }

    public static class NewState extends TheadState{
        public NewState() {
            setTheadName("New");
            Log.d(TAG, "New: now is New state");
        }

        public void start(TheadContext context){
            if("New".equals(getTheadName())){
                context.setState(new RunnableState());
            }else {
                Log.d(TAG, "start: now state is not new,can not do start");
            }
        }

    }

    public static class RunnableState extends TheadState{

        public RunnableState() {
            setTheadName("Runnable");
            Log.d(TAG, "RunnableState: now is RunnableState");
        }

        public void run(TheadContext context){
            if("Runnable".equals(getTheadName())){
                context.setState(new RunningState());
            }else {
                Log.d(TAG, "Runnable: now state is not Runnable,can not do Runnable");
            }
        }
    }

    public static class RunningState extends TheadState{
        public RunningState() {
            setTheadName("Running");
            Log.d(TAG, "RunningState: now is running state");
        }

        public void block(TheadContext context){
            if("Running".equals(getTheadName())){
                context.setState(new BlockedState());
            }else {
                Log.d(TAG, "RunningState: now state is not Running,can not do RunningState");
            }
        }

        public void dead(TheadContext context){
            if("Running".equals(getTheadName())){
                context.setState(new DeadState());
            }else {
                Log.d(TAG, "RunningState: now state is not Running,can not do RunningState");
            }
        }
    }

    public static class BlockedState extends TheadState{
        public BlockedState() {
            setTheadName("Blocked");
            Log.d(TAG, "BlockedState: now is the block state");
        }

        public void resume(TheadContext context){
            if("Blocked".equals(getTheadName())){
                context.setState(new RunnableState());
            }else {
                Log.d(TAG, "resume: now state is not Blocked can not do resume");
            }
        }
    }

    public static class DeadState extends TheadState{
        public DeadState() {
            setTheadName("Dead");
            Log.d(TAG, "DeadState: now state is Dead");
        }
    }

    public static class TheadContext{
        private TheadState state;

        public TheadContext() {
            state = new NewState();
        }

        public TheadState getState() {
            return state;
        }

        public void setState(TheadState state) {
            this.state = state;
        }

        public void start(){
            ((NewState)state).start(this);
        }

        public void run(){
            ((RunnableState)state).run(this);
        }

        public void block(){
            ((RunningState)state).block(this);
        }

        public void dead(){
            ((RunningState)state).dead(this);
        }

        public void resume(){
            ((BlockedState)state).resume(this);
        }
    }

}
