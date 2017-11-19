package com.hero.zhaoq.mpuserecorder;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        getSha1(this);
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




}