package com.example.administrator.myviewtest11_02;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtil {

    /**
     * 除数等于0，则返回0
     * @param a
     * @param b
     * @return
     */
    public static float divide(float a, float b) {
        if (b == 0) {
            return 0;
        } else {
            return a / b;
        }
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            return 0;
        } else {
            return a / b;
        }
    }

    public static Random random = new Random();

    public static int randomMax(int max) {
        return random.nextInt(max);
    }

    /**
     * [min, max]
     */
    public static int randomRange(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    /**
     * 四舍五入
     * @param f
     * @param scale
     * @return
     */
    public static float round(float f, int scale) {
        return new BigDecimal(f).setScale(scale, RoundingMode.HALF_EVEN).floatValue();
//        float pow = (float) Math.pow(10, scale);
//        return round(f * pow) / pow;
    }

    public static double round(double d, int scale) {
        return new BigDecimal(d).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
//        double pow = Math.pow(10, scale);
//        return round(d * pow) / pow;
    }

    public static long round(double d) {
        return (long) (d + (d > 0 ? 1 : -1) * 0.5);
    }

    public static int round(float f) {
        return (int) (f + (f > 0 ? 1 : -1) * 0.5);
    }

    /**
     * 四舍五入，返回String
     * @param d
     * @param scale
     * @return
     */
    public static String roundStr(double d, int scale) {
        return String.format("%." + scale + "f", d);
    }

    public static String roundStr(float f, int scale) {
        return String.format("%." + scale + "f", f);
    }

    /**
     * 直接舍弃末位
     * @param f
     * @param scale
     * @return
     */
    public static float roundDown(float f, int scale) {
        float pow = (float) Math.pow(10, scale);
        return (int) (f * pow) / pow;
    }

    public static double roundDown(double f, int scale) {
        double pow = Math.pow(10, scale);
        return (int) (f * pow) / pow;
    }

    public static int roundUp(float f) {
        int tt = 0;
        if ((int) f < f) {

            tt = (int) f + 1;

        } else {
            tt = (int) f;
        }

        return tt;

    }

    /**
     * 直接舍弃末位，返回String
     * @param d
     * @param scale
     * @return
     */
    public static String roundDownStr(double d, int scale) {
        return String.format("%." + scale + "f", roundDown(d, scale));
    }

    public static String roundDownStr(float f, int scale) {
        return String.format("%." + scale + "f", roundDown(f, scale));
    }

    /**
     * 把给定的数字转换成[1-9]+10的幂次+正负号
     * @param num
     * @return [个位数， 10的幂次， 正负号]
     */
    public static int[] toPow(float num) {
        int symbol = 1;
        if (num < 0) {// 为负数，转换成正数处理
            num = -num;
            symbol = -1;
        }
        int count = 0;
        while (num != 0) {// 不为0
            if (num >= 10) {
                num /= 10;
                count++;
            } else if (num < 1) {
                num *= 10;
                count--;
            } else {// [1, 10)
                break;
            }
        }
        return new int[]{(int) num, count, symbol};
    }
}
