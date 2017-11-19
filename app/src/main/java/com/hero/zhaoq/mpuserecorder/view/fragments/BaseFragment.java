package com.hero.zhaoq.mpuserecorder.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 15:58
 * zhaoqiang:zhaoq_hero@163.com
 */
public abstract class BaseFragment extends Fragment{

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(getResId(),null,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(rootView,savedInstanceState);
        initListener();
    }

    public void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //init data
    }

    protected abstract void initView(View rootView, Bundle savedInstanceState);

    protected abstract int getResId();


}
