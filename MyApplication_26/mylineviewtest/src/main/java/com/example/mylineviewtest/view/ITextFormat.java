package com.example.mylineviewtest.view;

/**
 * 格式化背景线上的数字
 * Created by JiangSong on 2015/7/13.
 */
public interface ITextFormat {
    /**
     * 格式化在线上显示的文字
     * @param value
     * @return
     */
    CharSequence getText(float value);

    /** 简单实现 */
    class Simple implements ITextFormat {
        private final String mFormat;

        public Simple(String format) {
            mFormat = format;
        }

        @Override
        public CharSequence getText(float value) {
            return String.format(mFormat, value);
        }
    }
}
