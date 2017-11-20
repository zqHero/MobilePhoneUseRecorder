package com.hero.zhaoq.mpuserecorder.view.activitys;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.amap.api.location.AMapLocation;
import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.utils.AppInfoUtils;
import com.hero.zhaoq.mpuserecorder.view.adapter.MRecycleVAdapter;
import com.hero.zhaoq.mpuserecorder.view.inter.MOnRefreshListener;
import com.hero.zhaoq.mpuserecorder.view.widgets.RefreshHeadlerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
        leftTv = findViewById(R.id.title_left);
        title = findViewById(R.id.title_txt);
        leftTv.setText("locating");
        title.setText("MobilePhone Use Recorder");

        refreshLayout = findViewById(R.id.refresh_layout);
        recycleV = findViewById(R.id.recycle_view);

        refreshLayout.setEnablePullToRefresh(true);
        refreshLayout.setLoadMoreModel(LoadModel.NONE);
        refreshLayout.setPullResistance(40.0);
        refreshLayout.setRefreshHeadView(new RefreshHeadlerView(refreshLayout.getContext()));

        recycleV.setLayoutManager(new LinearLayoutManager(this));
        mRecycleVAdapter = new MRecycleVAdapter(this, new ArrayList<UsageStats>());
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        UsageStatsManager m = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        //获取 一个月内的使用数据
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long endt = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);//时间间隔为一个月
        long statt = calendar.getTimeInMillis();//开始时间

        List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, statt, endt);
//                Field field = list.get(i).getClass().getDeclaredField("mLaunchCount");

        Collections.sort(stats, new Comparator<UsageStats>() {
            @Override
            public int compare(UsageStats o1, UsageStats o2) {
                return o1.getLastTimeStamp() - o2.getLastTimeStamp() > 0 ? -1 : 1;
            }
        });

        //去掉  当前包名  和系统桌面包名：
        for (int i = 0; i < stats.size(); i++) {
            UsageStats stats1 = stats.get(i);
            Log.i("info",stats1.getPackageName()+"-------sd-------");
            if ( stats1.getPackageName().equals(AppInfoUtils.getHomes(this).get(0).toString())) {
                stats.remove(stats1);
            }
        }
        mRecycleVAdapter.setData(stats);
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
