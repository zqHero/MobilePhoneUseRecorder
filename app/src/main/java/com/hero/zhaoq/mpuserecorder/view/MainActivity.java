package com.hero.zhaoq.mpuserecorder.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.amap.api.location.AMapLocation;
import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.VirtualData;
import com.hero.zhaoq.mpuserecorder.view.activitys.MapActivity;
import com.hero.zhaoq.mpuserecorder.view.adapter.MRecycleVAdapter;
import com.hero.zhaoq.mpuserecorder.view.activitys.BaseActivity;
import com.hero.zhaoq.mpuserecorder.view.inter.MOnRefreshListener;
import com.hero.zhaoq.mpuserecorder.view.widgets.RefreshHeadlerView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recycleV;
    private EasyRefreshLayout refreshLayout;
    private TextView leftTv;
    private MRecycleVAdapter mRecycleVAdapter;
    private TextView title;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        leftTv =  findViewById(R.id.title_left);
        title =  findViewById(R.id.title_txt);
        leftTv.setText("locating");
        title.setText("MobilePhone Use Recorder");

        refreshLayout = findViewById(R.id.refresh_layout);
        recycleV =  findViewById(R.id.recycle_view);

        refreshLayout.setEnablePullToRefresh(true);
        refreshLayout.setLoadMoreModel(LoadModel.NONE);
        refreshLayout.setPullResistance(40.0);
        refreshLayout.setRefreshHeadView(new RefreshHeadlerView(refreshLayout.getContext()));

        recycleV.setLayoutManager(new LinearLayoutManager(this));
        mRecycleVAdapter = new MRecycleVAdapter(this,new ArrayList<String>());
        recycleV.setAdapter(mRecycleVAdapter);
    }

    @Override
    protected void initListener() {
        refreshLayout.addEasyEvent(new MOnRefreshListener() {
            @Override
            public void onRefreshing() {
                //刷新   了界面
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                }, 3000);
            }
        });
        leftTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mRecycleVAdapter.setData(VirtualData.getDatas());
    }

    @Override
    protected void onMLocationChanged(AMapLocation aMapLocation) {
        leftTv.setText(aMapLocation.getCity() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                MapActivity.startAction(this);
                break;
            default:
                break;
        }
    }
}
