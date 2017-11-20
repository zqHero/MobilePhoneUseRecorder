package com.hero.zhaoq.mpuserecorder.moudle;

import android.graphics.drawable.Drawable;

/**
 * author: zhaoqiang
 * date:2017/11/20 / 16:30
 * zhaoqiang:zhaoq_hero@163.com
 */

public class AppInfo {

    String appName;
    String packageName;
    Drawable drawable;

    public AppInfo(){}

    public AppInfo(String appName){
        this.appName = appName;
    }

    public AppInfo(String appName, String packageName){
        this.appName = appName;
        this.packageName = packageName;
    }

    public AppInfo(String appName,String packageName, Drawable drawable){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
    }



    public String getAppName() {
        if(null == appName)
            return "";
        else
            return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        if(null == packageName)
            return "";
        else
            return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
