package com.hero.zhaoq.mpuserecorder.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 12:23
 * zhaoqiang:zhaoq_hero@163.com
 */

public class RescourseUtils {

    public static Drawable getDrawble(Context context, @DrawableRes int id){
        return ContextCompat.getDrawable(context.getApplicationContext(),id);
    }

    public static int getColor(Context context,@ColorRes int id){
        return  ContextCompat.getColor(context.getApplicationContext(),id);
    }

    public static String getString(Context context,@StringRes int id){
        return  context.getApplicationContext().getResources().getString(id);
    }

    public static float getDimens(Context context,@DimenRes int id){
        return context.getApplicationContext().getResources().getDimension(id);
    }
}

