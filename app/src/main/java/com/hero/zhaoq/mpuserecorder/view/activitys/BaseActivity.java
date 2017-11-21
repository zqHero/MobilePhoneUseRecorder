package com.hero.zhaoq.mpuserecorder.view.activitys;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hero.zhaoq.mpuserecorder.MApplication;

import java.util.List;


/**
 * author: zhaoqiang
 * date:2017/11/18 / 11:14
 * zhaoqiang:zhaoq_hero@163.com
 */
public abstract class BaseActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initListener();
        initData();

        initMapLocation();

    }

    protected abstract int getLayoutRes();

    protected abstract void initView();

    protected abstract void initListener();

    protected void initData(){

    }

    /**
     * 位置  变化  处理
     * @param aMapLocation
     */
    protected void onMLocationChanged(AMapLocation aMapLocation) {

    }

    //-------------------------------------地图相关-----------------------------------
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initMapLocation() {
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
//                        //可在其中解析amapLocation获取相应内容。
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(aMapLocation.getTime());
//                        Log.i("info",
//                                aMapLocation.getLocationType() +//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                                        aMapLocation.getLatitude() + "=========" +//获取纬度
//                                        aMapLocation.getLongitude() + "=========" +//获取经度
//                                        aMapLocation.getAccuracy() + "=========" +//获取精度信息
//                                        aMapLocation.getAddress() + "=========" +//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                                        aMapLocation.getCountry() + "=========" +//国家信息
//                                        aMapLocation.getProvince() + "=========" +//省信息
//                                        aMapLocation.getCity() + "=========" +//城市信息
//                                        aMapLocation.getDistrict() + "=========" +//城区信息
//                                        aMapLocation.getStreet() + "=========" +//街道信息
//                                        aMapLocation.getStreetNum() + "=========" +//街道门牌号信息
//                                        aMapLocation.getCityCode() + "=========" +//城市编码
//                                        aMapLocation.getAdCode() + "=========" +//地区编码
//                                        aMapLocation.getAoiName() + "=========" +//获取当前定位点的AOI信息
//                                        aMapLocation.getBuildingId() + "=========" +//获取当前室内定位的建筑物Id
//                                        aMapLocation.getFloor() + "=========" +//获取当前室内定位的楼层
//                                        aMapLocation.getGpsAccuracyStatus() + "=========" +//获取GPS的当前状态
//                                        df.format(date)//获取定位时间
//                        );
                        onMLocationChanged(aMapLocation);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("info", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption 对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为  AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        //mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //  mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(MApplication.GET_LOCATION_INFO_INTERVAL);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(MApplication.REQUEST_LOCATION_TIMEOUT);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


}
