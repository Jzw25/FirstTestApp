package com.example.myapplication.androidview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义viewgroup
 * 流式布局
 */
public class FlowLayout extends ViewGroup {
   private int spaceHeight = 8;//高度间隔
   private int spaceWidth = 16;//宽度间隔
   private List<Integer> lineHeight;
   private List<Integer> hangCount;

   public FlowLayout(Context context) {
      super(context);
   }

   public FlowLayout(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   private void initList(){
      lineHeight = new ArrayList<>();
      hangCount = new ArrayList<>();
   }

   /**
    * 测绘view大小
    * viewgroup mearsure里会先测量自己，再测量子view，子view的mearsure测量完毕，layout确定位置完毕，再确定自己的大小
    * @param widthMeasureSpec 父布局给的宽
    * @param heightMeasureSpec 父布局给的高度
    */
   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      initList();
      //父布局padding
      int paddingBottom = getPaddingBottom();
      int paddingLeft = getPaddingLeft();
      int paddingRight = getPaddingRight();
      int paddingTop = getPaddingTop();
      int childCount = getChildCount();
      //获取父布局宽高
      int widthSize = MeasureSpec.getSize(widthMeasureSpec);
      int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      //获取父布局model
      int widthMode = MeasureSpec.getMode(widthMeasureSpec);
      int heightMode = MeasureSpec.getMode(heightMeasureSpec);
      int finalWidth = 0;
      int finalHeight = 0;
      int nowWidth = 0;
      int nowHeight = 0;
      //遍历子view测绘子view
      for (int i = 0 ; i<childCount;i++){
         View childView = getChildAt(i);
         //子view的属性
         LayoutParams layoutParams = childView.getLayoutParams();
         int widthChildMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, layoutParams.width);
         int heightChildMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, layoutParams.height);
         childView.measure(widthChildMeasureSpec,heightChildMeasureSpec);
         //测量玩子view，获取子view宽高
         int measuredWidth = childView.getMeasuredWidth();
         int measuredHeight = childView.getMeasuredHeight();
         if(nowWidth+measuredWidth>widthSize){
            finalWidth = Math.max(finalWidth,nowWidth+spaceWidth);
            finalHeight = finalHeight + nowHeight + spaceHeight;
            lineHeight.add(nowHeight + spaceHeight);
            hangCount.add(i);
            nowHeight = 0;
            nowWidth = 0;
         }
         nowHeight = Math.max(nowHeight,measuredHeight);
         nowWidth = nowWidth+measuredWidth+spaceWidth;

         if(i==childCount-1){
            finalWidth = Math.max(finalWidth,nowWidth+spaceWidth);
            finalHeight = finalHeight + nowHeight + spaceHeight;
            lineHeight.add(nowHeight + spaceHeight);
            hangCount.add(i);
         }
      }

      //确定自身大小
      int realHeight = (heightMode==MeasureSpec.EXACTLY)?heightSize:finalHeight;
      int realWidth = (widthMode==MeasureSpec.EXACTLY)?widthSize:finalWidth;
      //调用此函数后可获取getMeasuredHeight获取宽高
      setMeasuredDimension(realWidth,realWidth);
   }

   /**
    * 确当view在父布局的位置
    * @param changed
    * @param l
    * @param t
    * @param r
    * @param b
    */
   @Override
   protected void onLayout(boolean changed, int l, int t, int r, int b) {
      int childCount = getChildCount();
      int le = getPaddingLeft();
      int to = getPaddingTop();
      int index = 0;
      int count = hangCount.get(index);
      int hangHeight = lineHeight.get(index);
      for (int i = 0;i<childCount;i++){
         View view = getChildAt(i);
         if(i==count){
            index++;
            count = hangCount.get(index);
            hangHeight = lineHeight.get(index-1);
            le = getPaddingLeft();
            to = to + hangHeight + spaceHeight;
         }
         int left = le;
         int top = to;
         int right = le + view.getMeasuredWidth();
         int bottom = to + view.getMeasuredHeight();
         view.layout(left,top,right,bottom);
         le = right + spaceWidth;
      }
   }

   /**
    * 绘制view
    * @param canvas
    */
   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
   }

   /**
    * 自定义layoutparams
    */
   public static class FlowLayoutParams extends MarginLayoutParams{

      public FlowLayoutParams(Context c, AttributeSet attrs) {
         super(c, attrs);
         TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
         int anInt = typedArray.getInt(R.styleable.FlowLayout_position, 0);
         typedArray.recycle();
      }

      public FlowLayoutParams(int width, int height) {
         super(width, height);
      }

      public FlowLayoutParams(MarginLayoutParams source) {
         super(source);
      }

      public FlowLayoutParams(LayoutParams source) {
         super(source);
      }
   }

}
