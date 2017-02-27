package com.example.administrator.myapplication_26;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/10/26.
 */
public class SwipeLinearLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;
    private View contentView;
    private View actionView;
    private int dragDistance;
    private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
    private int draggedX;

    public SwipeLinearLayout(Context context) {
        this(context, null);
    }

    public SwipeLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwipeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {
        contentView = getChildAt(0);
        actionView = getChildAt(1);
        actionView.setVisibility(GONE);
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragDistance = actionView.getMeasuredWidth();
    }

    private class DragHelperCallback extends ViewDragHelper.Callback{

        //确定滑动的view
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == contentView || child == actionView ;
        }

        //滑动的时候视图坐标的改变（此处的作用是为了当其中一个view被拖动时，另一个能跟着一起动）
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if(changedView == contentView){
                actionView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }

            if(actionView.getVisibility() == View.GONE){
                actionView.setVisibility(VISIBLE);
            }
            invalidate();
        }

        //实现滑动时(限制滑动的范围)
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child == contentView){
                final int leftBound = getPaddingLeft();
                Log.e("rd96", "leftBound" + leftBound + "left" + left);
                final int minLeftBound = leftBound - dragDistance;
                Log.e("rd96", "minLeftBound" + minLeftBound);
                final int newLeft = Math.min(Math.max(minLeftBound, left), leftBound);
                Log.e("rd96", "newLeft" + newLeft);
                return newLeft;
            }else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        //（返回水平滑动范围,返回0，则button或有点击时间的view无法获得处理，所有事件被拦截，返回一个值则不会）
        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        //拖动结束后视图的归位
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
       //     super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if(xvel > AUTO_OPEN_SPEED_LIMIT){
                settleToOpen = false;
            } else if(xvel < -AUTO_OPEN_SPEED_LIMIT){
                settleToOpen = true;
            } else if(draggedX <= -dragDistance / 2){
                settleToOpen = true;
            } else if(draggedX > -dragDistance / 2){
                settleToOpen = false;
            }

            final  int settleDestX = settleToOpen ? -dragDistance + getPaddingLeft() : getPaddingLeft();
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeLinearLayout.this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(viewDragHelper.shouldInterceptTouchEvent(ev)){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
