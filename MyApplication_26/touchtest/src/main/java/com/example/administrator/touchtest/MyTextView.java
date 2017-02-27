package com.example.administrator.touchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("--MyTextView-->dispatchTouchEvent-->start");
        boolean b = super.dispatchTouchEvent(ev);
        System.out.println("--MyTextView-->dispatchTouchEvent-->end-->"
                + ev.getAction() + "-->" + b);
        return b;
    }
}
