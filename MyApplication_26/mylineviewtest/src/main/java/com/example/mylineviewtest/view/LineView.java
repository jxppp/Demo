package com.example.mylineviewtest.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;

import com.example.mylineviewtest.R;

/**
 * Created by JiangSong on 2015/6/18.
 */
public class LineView extends View {

    public static final int MAX_LINE_COUNT = 30;
    private Paint mCurPaint;
    private Paint mOldPaint;
    private ObjectAnimator mMaxAnimator;
    private ObjectAnimator mMinAnimator;
    private Path mLinePath;
    private Paint.FontMetrics mFontMetrics;
    private ViewPaddingHelper mPaddingHelper;
    private Paint mCurTextPaint;

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int color = 0xffbbbbbb;
        float size = sp2px(12);
        if (attrs != null) {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.LineView);
            color = typedArray.getColor(R.styleable.LineView_my_color, color);
            size = typedArray.getDimension(R.styleable.LineView_size, size);
            typedArray.recycle();
        }
        mCurPaint = new Paint();
        mCurPaint.setTextSize(size);
        mCurPaint.setColor(color);
        mCurPaint.setPathEffect(new DashPathEffect(new float[]{dp2px(3), dp2px(2)}, 0));
        mCurPaint.setAntiAlias(true);
        mCurPaint.setStyle(Paint.Style.STROKE);

        /*Oppo的一款手机上, 使用带DashPathEffect效果的Paint画出来的文字会不清楚, fuck*/
        mCurTextPaint = new Paint();
        mCurTextPaint.setTextSize(size);
        mCurTextPaint.setColor(color);
        mCurTextPaint.setAntiAlias(true);

        mOldPaint = new Paint();
        mOldPaint.setTextSize(size);
        mOldPaint.setColor(color);
        mOldPaint.setPathEffect(new DashPathEffect(new float[]{dp2px(3), dp2px(2)}, 0));
        mOldPaint.setAntiAlias(true);
        mOldPaint.setStyle(Paint.Style.STROKE);

        mLinePath = new Path();

        mFontMetrics = new Paint.FontMetrics();
        mPaddingHelper = new ViewPaddingHelper(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float percent = 1;
        // 算动画进行的百分比
        if (mMaxValue != mCurLineArg.max) {
            percent = (mMaxValue - mOldLineArg.max) / (mCurLineArg.max - mOldLineArg.max);
        } else if (mMinValue != mCurLineArg.min) {
            percent = (mMinValue - mOldLineArg.min) / (mCurLineArg.min - mOldLineArg.min);
        }

        int saveCount = canvas.save();
        mPaddingHelper.origin(canvas);
        draw(mCurLineArg, canvas, mCurTextPaint, mCurPaint, percent);
        draw(mOldLineArg, canvas, mOldPaint, mOldPaint, 1 - percent);
        canvas.restoreToCount(saveCount);
    }

    private void draw(LineArg arg, Canvas canvas, Paint textPaint, Paint linePaint, float percent) {
        if (percent == 0) {
            return;
        }
        int alpha = (int) (255 * percent);
        textPaint.setAlpha(alpha);
        linePaint.setAlpha(alpha);

        float heightPx = mPaddingHelper.getHeight();
        float gapHeightPx = heightPx / (mMaxValue - mMinValue) * arg.gap;
        float lineEndX = mPaddingHelper.getWidth();
        textPaint.getFontMetrics(mFontMetrics);
        float textOffsetY = -(mFontMetrics.bottom + mFontMetrics.top) / 2;

        CharSequence charSequence = getText(arg.max);
        float lineStartX = textPaint.measureText(charSequence, 0, charSequence.length()) + dp2px(5);

        mLinePath.reset();
        mLinePath.moveTo(lineStartX, 0);
        mLinePath.lineTo(lineEndX, 0);

        int minCount = (int) Math.floor((mMinValue - arg.min) / arg.gap);
        int maxCount = (int) Math.ceil((mMaxValue - arg.min) / arg.gap);
        int lineCount = maxCount - minCount + 1;
        int step = lineCount / MAX_LINE_COUNT + 1;// 设置步长，防止画的线太多(把一屏的line数控制在MAX_LINE_COUNT以内)
//        System.out.println(minCount + "," + maxCount);
        float value = arg.min + arg.gap * minCount;
        float y = value2Y(value);
        int saveCount = canvas.save();
        canvas.translate(0, y);// 移到minCount点
        for (int i = minCount; i <= maxCount; i += step) {
            if (percent >= 1 && i == minCount) {
                // 透明度为1时，不画最下面线
            } else {
                canvas.drawPath(mLinePath, linePaint);
                CharSequence text = getText(value);
                canvas.drawText(text, 0, text.length(), 0, textOffsetY, textPaint);
            }
            canvas.translate(0, -gapHeightPx * step);
            value += arg.gap * step;
        }
        canvas.restoreToCount(saveCount);
    }

    public ITextFormat getTextFormat() {
        return mTextFormat;
    }

    public void setTextFormat(ITextFormat textFormat) {
        mTextFormat = textFormat;
    }

    private ITextFormat mTextFormat;

    private CharSequence getText(float value) {
        return mTextFormat != null ? mTextFormat.getText(value) : String.format("%.0f", value);
    }

    private float y2Value(float px) {
        return px * (mMinValue - mMaxValue) / mPaddingHelper.getHeight() + mMaxValue;
    }

    private float value2Y(float value) {
        // mMinValue对应的位置在屏幕下方, mMaxValue对应的位置在屏幕上方, 故使用(mMinValue - mMaxValue)
        return mPaddingHelper.getHeight() / (mMinValue - mMaxValue) * (value - mMaxValue);
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

//    public String getTvFormat() {
//        return tvFormat;
//    }

//    public void setTvFormat(String tvFormat) {
//        this.tvFormat = tvFormat;
//    }

//    private String tvFormat = "%.0f";

    public void setLineArg(LineArg lineArg) {
        if (isShown()) {
            setLineArg(lineArg, 500);
        } else {
            setLineArg(lineArg, 0);
        }
    }

    public void setLineArg(LineArg lineArg, long duration) {
//        System.out.println("设置值：" + lineArg.min + "," + lineArg.max);
        mOldLineArg = mCurLineArg;
        mCurLineArg = lineArg;
        if (mMaxAnimator != null && mMaxAnimator.isStarted()) {
            mMaxAnimator.cancel();
        }
        if (mMinAnimator != null && mMinAnimator.isStarted()) {
            mMinAnimator.cancel();
        }
        mMaxAnimator = ObjectAnimator.ofFloat(this, new Property<LineView, Float>(Float.class, "max") {
            @Override
            public Float get(LineView object) {
                return mMaxValue;
            }

            @Override
            public void set(LineView object, Float value) {
                mMaxValue = value;
                ViewCompat.postInvalidateOnAnimation(LineView.this);
            }
        }, mCurLineArg.max);

        mMinAnimator = ObjectAnimator.ofFloat(this, new Property<LineView, Float>(Float.class, "min") {
            @Override
            public Float get(LineView object) {
                return mMinValue;
            }

            @Override
            public void set(LineView object, Float value) {
                mMinValue = value;
                ViewCompat.postInvalidateOnAnimation(LineView.this);
            }
        }, mCurLineArg.min);
        mMaxAnimator.setDuration(duration).start();
        mMinAnimator.setDuration(duration).start();
    }

    private LineArg mCurLineArg = new LineArg(0, 5f, 5);
    private LineArg mOldLineArg = mCurLineArg;
    private float mMaxValue = mCurLineArg.max;
    private float mMinValue = mCurLineArg.min;

    public static class LineArg {
        float min;
        float gap;
        int count;
        float max;

        public LineArg(float min, int count, float max) {
            this(min, (max - min) / count, count, max);
        }

        public LineArg(float min, float gap, float max) {
            this(min, gap, (int) Math.ceil((max - min) / gap));
        }

        public LineArg(float min, float gap, int count) {
            this(min, gap, count, min + gap * count);
        }

        private LineArg(float min, float gap, int count, float max) {
            this.min = min;
            this.gap = gap;
            this.max = max;
            this.count = count;
        }

    }
}
