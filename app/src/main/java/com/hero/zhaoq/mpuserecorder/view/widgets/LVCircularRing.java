package com.hero.zhaoq.mpuserecorder.view.widgets;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 12:22
 * zhaoqiang:zhaoq_hero@163.com
 */

public class LVCircularRing extends LVBase {

    private Paint mPaint;
    private Paint mPaintPro;


    private float mWidth;
    private float mRingWidth = 3f;  //环形宽度

    //运动弧位置
    private final float startAngle = 270f;
    private float currentAngle = startAngle;

    //运动弧长
    private final float maxRectAngle = 326f;  //最大弧长增量
    private final float minRectAngle = 4f; //最小弧长
    private float currentRectAngle = minRectAngle;
    private RectF rectF;


    public LVCircularRing(Context context) {
        super(context);
    }

    public LVCircularRing(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LVCircularRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(mRingWidth);

        mPaintPro = new Paint();
        mPaintPro.setAntiAlias(true);
        mPaintPro.setStyle(Paint.Style.STROKE);
        mPaintPro.setColor(Color.argb(255, 230, 230, 230));
        mPaintPro.setStrokeWidth(mRingWidth);
    }

    @Override
    protected int getAnimDuration() {
        return 650;
    }

    @Override
    protected TimeInterpolator getInterpolator() {

        return new AccelerateDecelerateInterpolator();
    }

    @Override
    protected int getAnimRepeatCount() {

        return ValueAnimator.INFINITE;
    }

    @Override
    protected int getAnimRepeatMode() {

        return ValueAnimator.RESTART;
    }

    @Override
    protected void onAnimUpdate(ValueAnimator valueAnimator) {

        float value = (float) valueAnimator.getAnimatedValue();
        currentAngle = 360 * value + startAngle;
        if (value <= 0.5) {

            currentRectAngle = minRectAngle + maxRectAngle * value;
        } else {

            float d = (float) (maxRectAngle * 0.5);
            currentRectAngle = (float) (minRectAngle + d - (value - 0.5) / 0.5 * d);
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (getMeasuredWidth() > getHeight()) {

            mWidth = getMeasuredHeight();

        } else {

            mWidth = getMeasuredWidth();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mRingWidth / 2, mPaintPro);
        rectF = new RectF(mRingWidth / 2, mRingWidth / 2, mWidth - mRingWidth / 2, mWidth - mRingWidth / 2);
        canvas.drawArc(rectF, currentAngle, currentRectAngle, false, mPaint);//第四个参数是否显示半径
    }

    @Override
    public void startAnim() {

        currentRectAngle = minRectAngle;
        super.startAnim();

    }

    public void setShadeRingColor(int color) {

        mPaintPro.setColor(color);
        postInvalidate();
    }

    public void setRingColor(int color) {
        mPaint.setColor(color);
        postInvalidate();
    }

    public void setRingWidth(float width) {

        mRingWidth = width;
        mPaint.setStrokeWidth(mRingWidth);
        mPaintPro.setStrokeWidth(mRingWidth);
        postInvalidate();
    }

    public void setRingAngle(@FloatRange(from = 1f, to = 360f) float angle) {

        currentRectAngle = angle;
        postInvalidate();
    }
}
