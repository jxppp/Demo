package com.example.mylineviewtest.view;

/**
 * 值转换器
 * Created by JiangSong on 2015/7/13.
 */
public interface ITransValue {
    /**
     * 把值转换成LineController内部的值，用来应对单位转换等情况
     * @param value
     * @return
     */
    float value2internal(float value);

    /**
     * 把内部的值转换为外部的值
     * @param internal
     * @return
     */
    float internal2Value(float internal);

    /** 简单的实现 */
    class Simple implements ITransValue {

        @Override
        public float value2internal(float value) {
            return value;
        }

        @Override
        public float internal2Value(float internal) {
            return internal;
        }
    }
}
