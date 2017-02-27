package com.example.administrator.myview_test_01_13;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/1/13.
 */
public class CustomTitleView extends View {
    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTest, defStyleAttr, 0);

        mTitleText = a.getString(R.styleable.MyTest_android_text);
        mTitleTextColor = a.getColor(R.styleable.MyTest_myTextColor, Color.BLACK);
        mTitleTextSize = a.getDimensionPixelSize(R.styleable.MyTest_myTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        Log.d("rd96", "mbound.top" + mBound.top + " bottom: " + mBound.bottom + " left; " + mBound.left + " right " + mBound.right);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
//        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2 - mBound.left, getHeight() / 2 - mBound.height() / 2 - mBound.top, mPaint);//这里的起始坐标时以字符串的左下角为基准，即以相对我们正常的坐标系来给字符串的原点坐标（不同的坐标系）赋值

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        Log.d("rd96", "fontMetricsInt.top" + fontMetricsInt.top + " fontMetricsInt.bottom: " + fontMetricsInt.bottom + " fontMetricsInt.descent; "
                + fontMetricsInt.descent + " fontMetricsInt.leading " + fontMetricsInt.leading + " fontMetricsInt.ascent" + fontMetricsInt.ascent);

        int baseLine = (getHeight() - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mTitleText, getWidth() / 2, baseLine, mPaint);
    }
}
