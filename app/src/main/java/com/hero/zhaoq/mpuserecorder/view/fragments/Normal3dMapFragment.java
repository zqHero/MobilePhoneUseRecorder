package com.hero.zhaoq.mpuserecorder.view.fragments;


import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hero.zhaoq.mpuserecorder.MApplication;
import com.hero.zhaoq.mpuserecorder.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * normal map:
 */
public class Normal3dMapFragment extends BaseFragment implements View.OnClickListener {

    private MapView mapView;

    private UiSettings mUiSettings;  //设置 ui
    private AMap aMap;
    private FloatingActionButton bigBtn, smallBtn, modleStar, modleNight, modleNavigation, modleNormal;

    public static Normal3dMapFragment newInstance() {
        Normal3dMapFragment fragment = new Normal3dMapFragment();
        return fragment;
    }

    public Normal3dMapFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_normal3d_map;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        FloatingActionMenu fab = rootView.findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(false);
        bigBtn = rootView.findViewById(R.id.float_big);
        smallBtn = rootView.findViewById(R.id.float_small);

        modleStar = rootView.findViewById(R.id.modle_star);
        modleNight = rootView.findViewById(R.id.modle_night);
        modleNavigation = rootView.findViewById(R.id.modle_navigation);
        modleNormal = rootView.findViewById(R.id.modle_normal);

        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
        //  Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }

        mUiSettings.setScaleControlsEnabled(true); //默认   显示  比例尺
        mUiSettings.setZoomControlsEnabled(false); //放大和缩小   标签  默认不显示
        mUiSettings.setLogoLeftMargin(getContext().getResources().getDimensionPixelOffset(R.dimen.logo_margin));
        mUiSettings.setLogoBottomMargin(getContext().getResources().getDimensionPixelOffset(R.dimen.logo_margin));


        initMyLocationStyle();
    }

    /**
     * 实现 定位蓝点:
     */
    private void initMyLocationStyle() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(MApplication.GET_LOCATION_INFO_INTERVAL); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。

//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        //方法自5.1.0版本后支持
        myLocationStyle.showMyLocation(true);
    }

    @Override
    public void initListener() {
        super.initListener();
        bigBtn.setOnClickListener(this);
        smallBtn.setOnClickListener(this);

        modleStar.setOnClickListener(this);
        modleNight.setOnClickListener(this);
        modleNavigation.setOnClickListener(this);
        modleNormal.setOnClickListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_big:
                //
                float zoom = aMap.getMaxZoomLevel(); //19
                float zoomLevel = aMap.getMinZoomLevel();//3


                Log.i("info", zoom + "=====" + zoomLevel);


                break;
            case R.id.float_small:
                //

                break;
            case R.id.modle_star:
                //
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                break;
            case R.id.modle_night:
                //
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
                break;
            case R.id.modle_navigation:
                //
                aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图模式
                break;
            case R.id.modle_normal:
                //
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                break;
            default:
                break;
        }
    }
}
