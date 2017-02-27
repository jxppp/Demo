package com.example.administrator.touchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyFrameLayout extends FrameLayout {

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context) {
        super(context);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("--MyFrameLayout-->dispatchTouchEvent-->start");
        boolean b = super.dispatchTouchEvent(ev);
        System.out.println("--MyFrameLayout-->dispatchTouchEvent-->end-->"
                + ev.getAction() + "-->" + b);
        return b;
    }
}
