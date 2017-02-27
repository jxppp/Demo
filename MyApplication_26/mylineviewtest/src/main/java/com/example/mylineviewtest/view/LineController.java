package com.example.mylineviewtest.view;

/** Created by JiangSong on 2015/6/23. */
public class LineController {
    /** 背景线View */
    private LineView mLineView;
    /** 把内部的最大值最小值转换成nice的数字 */
    private INiceScale mNiceScale;
    /** 用来转换内部的值和外部的值 */
    private ITransValue mTransValue;

    public void setTransValue(ITransValue transValue) {
        mTransValue = transValue;
    }

    public LineController(LineView lineView) {
        mLineView = lineView;
    }

    public LineController(LineView lineView, INiceScale niceScale, ITransValue transValue) {
        this(lineView);
        mNiceScale = niceScale;
        mTransValue = transValue;
    }

//    /**
//     * 使用该方法初始化{@link LineController}，要在使用LineController之前设置值转换器：{@link #setTransValue(ITransValue)}
//     * 不然会报null
//     * @param lineView
//     * @param niceScale
//     */
//    public LineController(LineView lineView, INiceScale niceScale) {
//        this(lineView, niceScale, null);
//    }

    public void setNiceScale(INiceScale niceScale) {
        if (niceScale != null) {
            mNiceScale = niceScale;
        }
    }

    private void refreshView(long duration) {
        mLineView.setLineArg(new LineView.LineArg(mNiceScale.getNiceMin(), mNiceScale.getTickSpacing(), mNiceScale.getNiceMax()), duration);
    }

    private float mMin;
    private float mMax;

    /**
     * 添加值，让LineController累计最大最小值
     * @param value
     * @return
     */
    public boolean addValue(float value) {
        value = mTransValue.value2internal(value);
        NiceScale.p("value=", value);
        boolean change = false;
        if (value < mMin) {
            mMin = value;
            change = true;
        }
        if (value > mMax) {
            mMax = value;
            change = true;
        }
        return change && setMinMaxValueInternal(mMin, mMax);
    }

    /**
     * 设置最大最小值，有动画
     * @param min
     * @param max
     * @return
     */
    public boolean setMinMaxValue(float min, float max) {
        min = mTransValue.value2internal(min);
        max = mTransValue.value2internal(max);
        mMin = min;
        mMax = max;
        return setMinMaxValueInternal(min, max);
    }

    /**
     * 重置最大最小值，没动画
     * @param min
     * @param max
     */
    public void reset(float min, float max) {
        if (mTransValue == null) {
            throw new RuntimeException("mTransValue == null，请在调用reset()前，设置TransValue");
        }
        min = mTransValue.value2internal(min);
        max = mTransValue.value2internal(max);
        mMin = min;
        mMax = max;
        mNiceScale.setMinMaxPoints(min, max);
        refreshView(0);
    }

    private boolean setMinMaxValueInternal(float min, float max) {
        float oldMin = mNiceScale.getNiceMin();
        float oldMax = mNiceScale.getNiceMax();
        mNiceScale.setMinMaxPoints(min, max);
        boolean rangeChange = oldMin != mNiceScale.getNiceMin() || oldMax != mNiceScale.getNiceMax();
        if (rangeChange) {
            refreshView(500);
        }
        return rangeChange;
    }

    /**
     * 获得百分比
     * @param value
     * @return
     */
    public float getPercent(float value) {
        value = mTransValue.value2internal(value);
        return (value - mNiceScale.getNiceMin()) / (mNiceScale.getNiceMax() - mNiceScale.getNiceMin());
    }

    /**
     * 获取最大值
     * @return
     */
    public float getNiceMax() {
        return mTransValue.internal2Value(mNiceScale.getNiceMax());
    }

    /**
     * 获取最小值
     * @return
     */
    public float getNiceMin() {
        return mTransValue.internal2Value(mNiceScale.getNiceMin());
    }

    /**
     * 由百分比，获得值
     * @param percent
     * @return
     */
    public float getValue(float percent) {
        float value = percent * (mNiceScale.getNiceMax() - mNiceScale.getNiceMin()) + mNiceScale.getNiceMin();
        return mTransValue.internal2Value(value);
    }

    /**
     * 设置x轴显示文字的格式
     * @param format
     */
    public void setSimpleTextFormat(final String format) {
        mLineView.setTextFormat(new ITextFormat.Simple(format));
    }

    public void setTextFormat(ITextFormat format) {
        mLineView.setTextFormat(format);
    }

}