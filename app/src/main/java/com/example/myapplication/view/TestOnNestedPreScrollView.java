package com.example.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;

/**
 * onStartNestedScroll该方法返回true，代表当前ViewGroup能接受内部View的滑动参数（这个内部View不一定是直接子
 * View），一般情况下建议直接返回true，当然你可以根据nestedScrollAxes：判断垂直或水平方向才返回true。
 *
 * onNestedPreScroll该方法会传入内部View移动的dx与dy，当前ViewGroup可以消耗掉一定的dx与dy，然后通过最后一个
 * 参数consumed传回给子View。例如，当前ViewGroup消耗掉一半dx与dy
 * scrollBy(dx/2, dy/2);
 * consumed[0] = dx/2;
 * consumed[1] = dy/2;
 *
 * onNestedPreFling你可以捕获对内部View的fling事件，返回true表示拦截掉内部View的事件
 */
public class TestOnNestedPreScrollView extends LinearLayout implements NestedScrollingParent {
    private int maxScrollY = 100;
    private OverScroller mScroller;
    private int maxY = 100;

    public TestOnNestedPreScrollView(Context context) {
        super(context);
    }

    public TestOnNestedPreScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestOnNestedPreScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestOnNestedPreScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // dy > 0表示子View向上滑动;

        // 子View向上滑动且父View的偏移量<ImageView高度
        boolean hiddenTop = dy > 0 && getScrollY() < maxScrollY;

        // 子View向下滑动(说明此时父View已经往上偏移了)且父View还在屏幕外面, 另外内部View不能在垂直方向往下移动了
        /**
         * ViewCompat.canScrollVertically(view, int)
         * 负数: 顶部是否可以滚动(官方描述: 能否往上滚动, 不太准确吧~)
         * 正数: 底部是否可以滚动
         */
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {

        if (velocityY > 0 && getScrollY() < maxScrollY) // 向上滑动, 且当前View还没滑到顶
        {
            fling((int) velocityY, maxScrollY);
            return true;
        } else if (velocityY < 0 && getScrollY() > 0) // 向下滑动, 且当前View部分在屏幕外
        {
            fling((int) velocityY, 0);
            return true;
        }
        return false;
    }

    private void fling(int velocityY, int maxScrollY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, maxY);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) // 不允许向下滑动
        {
            y = 0;
        }
        if (y > maxScrollY) // 防止向上滑动距离大于最大滑动距离
        {
            y = maxScrollY;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}