package com.example.myapplication.androidview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyTestBehavior extends CoordinatorLayout.Behavior {
   private float deltaY;

   public MyTestBehavior() {
   }

   public MyTestBehavior(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   //确定使用Behavior的View要依赖的View的类型
   @Override
   public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
      return dependency instanceof RecyclerView;
   }

   //当被依赖的View状态改变时回调
   @Override
   public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
      if (deltaY == 0) {
         deltaY = dependency.getY() - child.getHeight();
      }

      float dy = dependency.getY() - child.getHeight();
      dy = dy < 0 ? 0 : dy;
      float y = -(dy / deltaY) * child.getHeight();
      child.setTranslationY(y);

      return true;
   }

   //是否拦截触摸事件
   @Override
   public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
      return super.onInterceptTouchEvent(parent, child, ev);
   }

   //处理触摸事件
   @Override
   public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
      return super.onTouchEvent(parent, child, ev);
   }

   //当被依赖的View移除时回调
   @Override
   public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
      super.onDependentViewRemoved(parent, child, dependency);
   }

   //测量使用Behavior的View尺寸
   @Override
   public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
      return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
   }

   //确定使用Behavior的View位置
   @Override
   public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
      return super.onLayoutChild(parent, child, layoutDirection);
   }

   //嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
   @Override
   public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
      return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
   }

   //嵌套滑动结束（ACTION_UP或ACTION_CANCEL）
   @Override
   public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
      super.onStopNestedScroll(coordinatorLayout, child, target, type);
   }

   //嵌套滑动进行中，要监听的子 View的滑动事件已经被消费
   @Override
   public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
      super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
   }

   //嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
   @Override
   public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
      super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
   }

   //要监听的子 View在快速滑动中
   @Override
   public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
      return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
   }

   //要监听的子View即将快速滑动
   @Override
   public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
      return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
   }
}
