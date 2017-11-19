package com.hero.zhaoq.mpuserecorder.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 12:25
 * zhaoqiang:zhaoq_hero@163.com
 */

public class DisplayUtils {

    private static Resources getResource(Context context) {
        return context.getApplicationContext().getResources();
    }

    /**
     * dp转换px
     */
    public static int dp2px(int dip,Context context) {
        final float scale = getResource(context.getApplicationContext()).getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dp
     */

    public static int px2dip(int px,Context context) {
        final float scale = getResource(context.getApplicationContext()).getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue,Context context) {
        final float fontScale = getResource(context.getApplicationContext()).getDisplayMetrics()
                .scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue,Context context) {
        final float fontScale = getResource(context.getApplicationContext()).getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}

