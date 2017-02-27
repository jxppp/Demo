package com.example.mylineviewtest.view;

import java.util.Arrays;

public class NiceScale implements INiceScale {
    public static void main(String... args) {
//        NiceScale niceScale = new NiceScale(5);
//        niceScale.setPositionalNotation(PositionalNotation.DECIMAL);
//        niceScale.setMinMaxPoints(0, 759);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(0, 1020);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(5, 111);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(5, 1111);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(0, 3600);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(0, 1800);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(0, 7560);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
//        niceScale.setMinMaxPoints(0, 7 * 3600);
//        p(">>", niceScale.getNiceMin(), niceScale.getTickSpacing(), niceScale.getNiceMax());
    }

    private float minPoint;
    private float maxPoint;

    /** 最大的间隔数 */
    private float maxTicks = 5;
    private float tickSpacing;
    private float range;
    private float niceMin;
    private float niceMax;

    public static void p(Object... objs) {
        System.out.println(Arrays.toString(objs));
//        Log.i("LineView", Arrays.toString(objs));
    }

    @Override
    public float getNiceMin() {
        return niceMin;
    }

    @Override
    public float getNiceMax() {
        return niceMax;
    }

    @Override
    public float getTickSpacing() {
        return tickSpacing;
    }

    public NiceScale(float maxTicks) {
        this.maxTicks = maxTicks;
    }

    public NiceScale() {
    }

    /**
     * Instantiates a new instance of the NiceScale class.
     * @param min the minimum data point on the axis
     * @param max the maximum data point on the axis
     */
    public NiceScale(float min, float max) {
        this.minPoint = min;
        this.maxPoint = max;
        calculate();
    }

    /**
     * Calculate and update values for tick spacing and nice
     * minimum and maximum data points on the axis.
     */
    private void calculate() {
        this.range = niceNum(maxPoint - minPoint, mPN.upRanges, mPN.values, mPN.baseNum, true);
        this.tickSpacing = niceNum(range / (maxTicks - 1), mPN.roundRanges, mPN.values, mPN.baseNum, false);
        this.niceMin = (float) (Math.floor(minPoint / tickSpacing) * tickSpacing);
        this.niceMax = (float) (Math.ceil(maxPoint / tickSpacing) * tickSpacing);
    }

    /**
     * Returns a "nice" number approximately equal to range Rounds
     * the number if round = true Takes the ceiling if round = false.
     * @param num       the data range
     * @param takeEqual whether to round the result
     * @return a "nice" number to be used for the data range
     */
    private float niceNum(float num, float[] ranges, float[] values, int baseNum, boolean takeEqual) {
        // 把一个数，依据它的baseNum(进制)，拆分为exponent(指数部分)和fraction(小数部分)
        // num = fraction * baseNum^fraction
        float exponent = (float) Math.floor(Math.log10(num) / Math.log10(baseNum));
        float fraction = (float) (num / Math.pow(baseNum, exponent));

        // 取一个nice的fraction
        int i = 0;
        float niceFraction = fraction;
        for (; i < ranges.length; i++) {
            if (fraction < ranges[i] || (takeEqual && fraction == ranges[i])) {// takeEqual控制是否要取等号~~
                niceFraction = values[i];
                break;
            }
        }
        if (i == ranges.length) {// 若fraction比所有的ranges值都要大，则fraction取values的最后一个值
            niceFraction = values[i];
        }

        // 由niceFraction算niceNum
        float niceNum = (float) (niceFraction * Math.pow(baseNum, exponent));
        p(num, takeEqual, exponent, fraction, niceFraction, niceNum);
        return niceNum;
    }

    private PositionalNotation mPN = PositionalNotation.DECIMAL;

    /**
     * Sets the minimum and maximum data points for the axis.
     * @param minPoint the minimum data point on the axis
     * @param maxPoint the maximum data point on the axis
     */
    @Override
    public void setMinMaxPoints(float minPoint, float maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        calculate();
    }

    public void setPositionalNotation(PositionalNotation pn) {
        this.mPN = pn;
    }

    /**
     * Sets maximum number of tick marks we're comfortable with
     * @param maxTicks the maximum number of tick marks for the axis
     */
    @Override
    public void setMaxTicks(float maxTicks) {
        this.maxTicks = maxTicks;
        calculate();
    }

    /** 进位制 */
    enum PositionalNotation {
        /** 十进制 */
        DECIMAL(10
                , new float[]{1.5f, 3, 7}
                , new float[]{1, 2, 5}
                , new float[]{1, 2, 5, 10}),
        /** 60进制 */
        SEXAGESIMAL(60
                , new float[]{8, 22, 45}
                , new float[]{1, 15, 30}
                , new float[]{1, 15, 30, 60});

        PositionalNotation(int baseNum, float[] roundRanges, float[] upRanges, float[] values) {
            this.baseNum = baseNum;
            this.roundRanges = roundRanges;
            this.upRanges = upRanges;
            this.values = values;
        }

        int baseNum;
        float[] roundRanges;
        float[] upRanges;
        float[] values;
    }
}