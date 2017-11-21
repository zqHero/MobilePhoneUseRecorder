package com.hero.zhaoq.mpuserecorder.view.activitys;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.moudle.AppInfo;
import com.hero.zhaoq.mpuserecorder.utils.AppInfoUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppDitalInfoActivity extends BaseActivity implements View.OnClickListener {

    private List<AppInfo> packageInfos;
    private List<String> stringPackagesNames;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_app_dital_info;
    }

    private TextView leftTv;
    private TextView title;
    private View titleRight;
    private TextView txtContent;

    @Override
    protected void initView() {
        leftTv = findViewById(R.id.title_left);
        titleRight = findViewById(R.id.title_right);
        txtContent = findViewById(R.id.txt_info);
        title = findViewById(R.id.title_txt);
        title.setText("App detail Info");
        titleRight.setVisibility(View.GONE);
        leftTv.setText(" < ");
    }

    @Override
    protected void initListener() {
        leftTv.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        super.initData();
        stringPackagesNames = new ArrayList<>();
        packageInfos = AppInfoUtils.getAppInfos(this);
        for (AppInfo info : packageInfos) {
            stringPackagesNames.add(info.getPackageName());
        }

        UsageStats usageStats = getIntent().getBundleExtra("bundle").getParcelable("data");
        txtContent.append("原始对象 : " + usageStats.toString());

        //包含 该对象：取出  该app info
        AppInfo info = null;
        if (stringPackagesNames.contains(usageStats.getPackageName())) {
            info = packageInfos.get(stringPackagesNames.indexOf(usageStats.getPackageName()));
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Field field = usageStats.getClass().getDeclaredField("mLaunchCount");
                txtContent.append("\n\n解析数据:" + "\n名称:" + info.getAppName() + "\n" +
                        "LastTimeUsed：" + usageStats.getLastTimeUsed() + "\n" +
                        "FirstTimeStamp：" + usageStats.getFirstTimeStamp() + "\n" +
                        "LastTimeStamp：" + usageStats.getLastTimeStamp() + "\n" +
                        "TotalTimeInForeground：" + usageStats.getTotalTimeInForeground() + "\n" +
                        "mLaunchCount：" + field.getInt(usageStats) + "\n"
                );
                txtContent.append("\n\n格式化:" + "\n名称:" + info.getAppName() + "\n" +
                        "LastTimeUsed：" + simpleDateFormat.format(new Date(usageStats.getLastTimeUsed())) + "\n" +
                        "FirstTimeStamp：" + simpleDateFormat.format(new Date(usageStats.getFirstTimeStamp())) + "\n" +
                        "LastTimeStamp：" + simpleDateFormat.format(new Date(usageStats.getLastTimeStamp())) + "\n" +
                        "TotalTimeInForeground：" + getFormatData(usageStats.getTotalTimeInForeground()) + "\n" +
                        "mLaunchCount：" + field.getInt(usageStats) + "\n"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("info","异常");
        }
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private String getFormatData(long totalTimeInForeground) {
        int h = (int) (totalTimeInForeground / (1000 * 60 * 60));
        int m = (int) (totalTimeInForeground - (h * (1000 * 60 * 60))) / (1000 * 60);
        int s = (int) (totalTimeInForeground - (h * (1000 * 60 * 60)) - (m * 1000 * 60)) / 1000;
        return h + ":" + m + ":" + s;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
