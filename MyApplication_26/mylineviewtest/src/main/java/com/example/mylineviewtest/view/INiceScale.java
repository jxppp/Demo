package com.example.mylineviewtest.view;

/**
 * Created by JiangSong on 2015/6/23.
 */
public interface INiceScale {
    /**
     * 获取最佳最小值
     * @return
     */
    float getNiceMin();

    /**
     * 获取最佳最大值
     * @return
     */
    float getNiceMax();

    /**
     * 获得间隔
     * @return
     */
    float getTickSpacing();

    /**
     * 设置最小、最大值给INiceScale计算
     * @param minPoint
     * @param maxPoint
     */
    void setMinMaxPoints(float minPoint, float maxPoint);

    /**
     * 设置最大间隔数
     * @param maxTicks
     */
    void setMaxTicks(float maxTicks);
}
