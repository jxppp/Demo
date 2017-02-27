package com.example.administrator.myviewtest11_02;

/**
 * 处理初始值->结束值，变化百分比；的工具类
 * Created by JiangSong on 2015/1/7.
 */
public class StartEndPercent {
    private float start;
    private float end;

    public StartEndPercent() {
    }

    public StartEndPercent(float start, float end) {
        this.start = start;
        this.end = end;
    }

    public float getValue(float percent) {
        return start + (end - start) * percent;
    }

    public float getPercent(float value) {
        return (value - start) / (end - start);
    }

    public float getValueByValue(float start, float end, float value) {
        return getValue(new StartEndPercent(start, end).getPercent(value));
    }

    public void setStartEnd(float start, float end) {
        setStart(start);
        setEnd(end);
    }

    public void setStart(float start) {
        this.start = start;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }
}