package com.example.administrator.touchtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MainActivity extends Activity {

    View view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());

    }

    private View initView(){
        // 初始化三个控件
        MyFrameLayout mFrameLayout = new MyFrameLayout(this);

        MyRelativeLayout mRelativeLayout = new MyRelativeLayout(this);

        MyTextView myTextView = new MyTextView(this);

        // 分别设置LayoutParams
        FrameLayout.LayoutParams mFrameLayoutParams = new FrameLayout.LayoutParams(600, 600);

        android.widget.RelativeLayout.LayoutParams mRelativeLayoutParams = new RelativeLayout.LayoutParams(
                300, 300);

        // 将RelativeLayout添加到FrameLayout中
        mFrameLayout.addView(mRelativeLayout, mFrameLayoutParams);
        // 将TextView添加到RelativeLayout中
        mRelativeLayout.addView(myTextView, mRelativeLayoutParams);

        // 设置Gravity，居中
        mRelativeLayout.setGravity(Gravity.CENTER);

        mFrameLayoutParams.gravity = Gravity.CENTER;

        // 设置三个控件的颜色
        mFrameLayout.setBackgroundColor(Color.RED);

        mRelativeLayout.setBackgroundColor(Color.GREEN);

        myTextView.setBackgroundColor(Color.BLUE);

        //将FrameLayout返回，作为Activity显示的View
        return mFrameLayout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
