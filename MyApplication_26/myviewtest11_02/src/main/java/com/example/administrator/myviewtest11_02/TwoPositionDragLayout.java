package com.example.administrator.myviewtest11_02;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/2.
 */
public abstract class TwoPositionDragLayout extends ViewGroup implements OnPercentChangeObserver{

    private float mPercent = 0;
    protected Map<Object, StartEndPercent> mPercentMap = new HashMap<>();
    private ViewDragHelper mViewDragHelper;
    private GestureDetector gestureDetector;
    protected LayoutMethod defaultLayoutMethod = new VerticalLayoutMethod();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public TwoPositionDragLayout(Context context) {
        super(context);
        init();
    }

    public TwoPositionDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TwoPositionDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        registerObserver(this);
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return Math.abs(distanceY) > Math.abs(distanceX);
            }
        });
        mViewDragHelper = createViewDragHelper();
    }

    protected ViewDragHelper getViewDragHelper() {
        return mViewDragHelper;
    }

    protected ViewDragHelper createViewDragHelper() {
        return ViewDragHelper.create(this, 1, new Callback());
    }

    public static final int TAG_KEY_LAYOUT_METHOD = R.id.two_position_drag_layout_tag_key;

    /**
     * layout可移动的Children
     * @param curView
     * @param percent
     */
    public void layoutMoveableChildren(View curView, float percent){
        for(int i = 0; i < getChildCount(); i++){
            View child = getChildAt(i);
            if (mPercentMap.get(child) != null){// 包含在mPercentMap中的child，才处理
                float value = mPercentMap.get(child).getValue(percent);
                LayoutMethod layoutMethod =(LayoutMethod) child.getTag(TAG_KEY_LAYOUT_METHOD);
                if(layoutMethod == null){// 没设置layout方法，则使用默认的
                    layoutMethod = defaultLayoutMethod;
                }
                layoutMethod.layoutChild(curView, child, value);
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        try {
            intercept = mViewDragHelper.shouldInterceptTouchEvent(ev);
        }catch (Exception e){
            e.printStackTrace();
        }
        return intercept && gestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mViewDragHelper.processTouchEvent(event);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public int dp2px(float dp){
        return MathUtil.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

    public float getmPercent() {
        return mPercent;
    }

    public void setmPercent(float mPercent) {
        this.mPercent = mPercent;
    }

    @Override
    public abstract void onPercentChange(View movingView, float percent);

    class VerticalLayoutMethod implements LayoutMethod{
        @Override
        public void layoutChild(View movingView, View child, float value) {
            if(movingView != child){// 当前正在触摸移动的View不处理
                int top = MathUtil.round(value);
                child.layout(0, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }

    private List<OnPercentChangeObserver> mObservers = new ArrayList<>();

    public void registerObserver(OnPercentChangeObserver observer) {
        mObservers.add(observer);
    }

    public void unregisterObserver(OnPercentChangeObserver observer) {
        mObservers.remove(observer);
    }

    protected interface LayoutMethod {
        void layoutChild(View movingView, View child, float value);
    }

    private void dispatchPercentChange(View changedView, float percent) {
        mPercent = percent;
        for (OnPercentChangeObserver observer : mObservers) {
            observer.onPercentChange(changedView, percent);
        }
    }

    public class Callback extends ViewDragHelper.Callback{
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mPercentMap.get(child) != null;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            StartEndPercent startEndPercent = mPercentMap.get(child);
            if(startEndPercent == null){// tryCaptureView()返回false的View，也可能在ViewDragHelper拦截child的事件时，调用该方法~~
                return top;// 返回top，使这个child去tryCaptureView()~~
            }
            int[] value = new int[3];
            value[0] = MathUtil.round(startEndPercent.getValue(0f));
            value[1] = top;
            value[2] = MathUtil.round(startEndPercent.getValue(1f));
            Arrays.sort(value);
            return value[1];
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            float percent = mPercentMap.get(changedView).getPercent(top);
            dispatchPercentChange(changedView, percent);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            StartEndPercent startEndPercent = mPercentMap.get(releasedChild);
            float percent = startEndPercent.getPercent(releasedChild.getTop());
            boolean toEnd;
            if(yvel < -800){
                toEnd = true;
            } else if(yvel > 800){
                toEnd = false;
            } else {
                toEnd = percent > 0.5;
            }
            boolean needInvalidate;
            if(toEnd){
                needInvalidate = mViewDragHelper.settleCapturedViewAt(0, MathUtil.round(startEndPercent.getEnd()));
            } else {
                needInvalidate = mViewDragHelper.settleCapturedViewAt(0, MathUtil.round(startEndPercent.getStart()));
            }

            if(needInvalidate){
                ViewCompat.postInvalidateOnAnimation(TwoPositionDragLayout.this);
            }
        }
    }

}
