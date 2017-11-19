package com.hero.zhaoq.mpuserecorder.view.widgets;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 12:22
 * zhaoqiang:zhaoq_hero@163.com
 */

public abstract class LVBase extends View {


    public ValueAnimator valueAnimator;

    public LVBase(Context context) {
        this(context, null);
    }

    public LVBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    protected abstract void init();

    protected abstract int getAnimDuration();

    protected abstract TimeInterpolator getInterpolator();

    protected abstract int getAnimRepeatCount();

    protected abstract int getAnimRepeatMode();

    protected abstract void onAnimUpdate(ValueAnimator valueAnimator);

    protected  boolean isAnimRunning(){

        return valueAnimator.isRunning();
    }

    private ValueAnimator startViewAnim() {

        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(getAnimDuration());
        valueAnimator.setInterpolator(getInterpolator());

        valueAnimator.setRepeatCount(getAnimRepeatCount());

        switch (getAnimRepeatMode()){

            case ValueAnimator.RESTART:

                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                break;

            case ValueAnimator.REVERSE:

                valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
                break;

            default:
                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                break;
        }


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                onAnimUpdate(valueAnimator);
            }
        });
        if (!valueAnimator.isRunning()) {

            valueAnimator.start();
        }

        return valueAnimator;
    }

    public void startAnim() {
        stopAnim();
        startViewAnim();
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();

    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }
}
