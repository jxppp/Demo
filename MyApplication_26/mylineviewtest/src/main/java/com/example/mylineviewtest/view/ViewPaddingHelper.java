package com.example.mylineviewtest.view;

import android.graphics.Canvas;
import android.view.View;

/**
 * 方便处理padding的类
 * Created by JiangSong on 2015/10/21.
 */
public class ViewPaddingHelper {
    private View mView;

    public ViewPaddingHelper(View view) {
        this.mView = view;
    }

    /**
     * 平移canvas, 使原点变成padding的左上角
     * @param canvas
     */
    public void origin(Canvas canvas) {
        canvas.translate(mView.getPaddingLeft(), mView.getPaddingTop());
    }

    /**
     * padding的宽度
     * @return
     */
    public float getWidth() {
        return mView.getWidth() - mView.getPaddingLeft() - mView.getPaddingRight();
    }

    /**
     * padding的高度
     * @return
     */
    public float getHeight() {
        return mView.getHeight() - mView.getPaddingTop() - mView.getPaddingBottom();
    }

    /**
     * padding的左边线
     * @return
     */
    public float getLeft() {
        return mView.getPaddingLeft();
    }

    /**
     * padding的右边线
     * @return
     */
    public float getRight() {
        return mView.getWidth() - mView.getPaddingRight();
    }

    /**
     * padding的上边线
     * @return
     */
    public float getTop() {
        return mView.getPaddingTop();
    }

    /**
     * padding的下边线
     * @return
     */
    public float getBottom() {
        return mView.getHeight() - mView.getPaddingBottom();
    }
}
