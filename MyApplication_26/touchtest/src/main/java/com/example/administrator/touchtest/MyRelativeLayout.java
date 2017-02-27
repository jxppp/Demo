package com.example.administrator.touchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyRelativeLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("--MyLinearLayout-->dispatchTouchEvent-->start");
        boolean b = super.dispatchTouchEvent(ev);
        System.out.println("--MyLinearLayout-->dispatchTouchEvent-->end-->"
                + ev.getAction() + "-->" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
