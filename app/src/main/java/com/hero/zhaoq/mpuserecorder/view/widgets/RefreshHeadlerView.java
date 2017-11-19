package com.hero.zhaoq.mpuserecorder.view.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.IRefreshHeader;
import com.ajguan.library.State;
import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.view.inter.OnEasyRefreshScrollListener;
import com.hero.zhaoq.mpuserecorder.utils.DisplayUtils;
import com.hero.zhaoq.mpuserecorder.utils.RescourseUtils;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 12:17
 * zhaoqiang:zhaoq_hero@163.com
 */

public class RefreshHeadlerView extends FrameLayout implements IRefreshHeader {

    private TextView textView;
    private LVCircularRing ringView;
    private ImageView imageView;

    private OnEasyRefreshScrollListener listener;

    public RefreshHeadlerView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshHeadlerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.widget_refresh_header_view, this);

        textView = (TextView) findViewById(R.id.refresh_text);
        ringView = (LVCircularRing) findViewById(R.id.refresh_ring);
        imageView = (ImageView) findViewById(R.id.refresh_success);
        ringView.setRingColor(RescourseUtils.getColor(context,R.color.refresh_color));
        ringView.setRingWidth(DisplayUtils.dp2px(2,context));

    }


    public void setOnEasyRefreshScrollListener(OnEasyRefreshScrollListener listener) {

        this.listener = listener;
    }

    @Override
    public void reset() {

        textView.setText("下拉刷新");
        ringView.stopAnim();
        ringView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);

    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {

        textView.setText("正在刷新");
        ringView.startAnim();
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {

        if (listener != null) {
            listener.onScrollChange(currentPos, refreshPos);
        }

        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {

            if (isTouch && state == State.PULL) {
                textView.setText("下拉刷新");
            }
        }
        // 往下拉
        if (currentPos > refreshPos && lastPos <= refreshPos) {

            if (isTouch && state == State.PULL) {
                textView.setText("松开刷新");
            }
        }

        if (lastPos > refreshPos) {

            float angle = (lastPos - refreshPos) / (refreshPos * 1.85f - refreshPos) * 360;
            ringView.setRingAngle(angle);

        }

    }

    @Override
    public void complete() {

        textView.setText("刷新完成");
        ringView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }


}

