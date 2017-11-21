package com.hero.zhaoq.mpuserecorder;

import android.app.ActivityManager;
import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 13:48
 * zhaoqiang:zhaoq_hero@163.com
 */

public class MApplication extends Application {


    public static final long GET_LOCATION_INFO_INTERVAL = 1000; //每一秒   请求一次位置信息
    public static final long REQUEST_LOCATION_TIMEOUT = 20000; //请求   位置信息  超时时间

    @Override
    public void onCreate() {
        super.onCreate();

//        String sha1 = getSha1(this);
//        getSha1(this);

    }

    public void getRecentTask() {
        PackageManager pm = this.getPackageManager();
        ActivityManager mActivityManager = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> appList4 = mActivityManager
                .getRecentTasks(5, ActivityManager.RECENT_WITH_EXCLUDED);//参数，前一个是你要取的最大数，后一个是状态
        for (ActivityManager.RecentTaskInfo running : appList4) {
            Intent intent = running.baseIntent;
            ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                Log.i("info",resolveInfo.activityInfo.packageName + "n");//获取应用包名
                Log.i("info",resolveInfo.loadLabel(pm).toString() + "n");//获取应用名
            }
        }
    }

    public static String getSha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

//    {
//        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
//        long time = System.currentTimeMillis();
//        List<UsageStats> stats;
//        if(isFirst)
//
//        {
//            stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - TWENTYSECOND, time);
//        }else
//
//        {
//            stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - THIRTYSECOND, time);
//        }
//            // Sort the stats by the last time used
//        if(stats !=null)
//
//        {
//            TreeMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
//            start = System.currentTimeMillis();
//            for (UsageStats usageStats : stats) {
//                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
//            }
//            LogUtil.e(TAG, "isFirst=" + isFirst + ",mySortedMap cost:" + (System.currentTimeMillis() - start));
//            if (mySortedMap != null && !mySortedMap.isEmpty()) {
//
//                topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//
//                runningTopActivity = new ComponentName(topPackageName, "");
//                if (LogUtil.isDebug()) LogUtil.d(TAG, topPackageName);
//            }
//        }
//    }



}
