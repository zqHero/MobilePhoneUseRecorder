package com.hero.zhaoq.mpuserecorder.view.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.view.adapter.LocationListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 位置  详情信息：
 */
public class LocationDetailFragment extends BaseFragment {

    private ListView listView;
    private LocationListAdapter adapter;

    public LocationDetailFragment() {

    }

    public static LocationDetailFragment newInstance() {
        LocationDetailFragment fragment = new LocationDetailFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        //init View：
        listView = rootView.findViewById(R.id.location_list_view);
        adapter = new LocationListAdapter(getContext(), new HashMap<String, String>());
        listView.setAdapter(adapter);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_location_detail;
    }

    /**
     * location changed:
     *
     * @param aMapLocation
     */
    public void onMLocationChanged(AMapLocation aMapLocation) {
        HashMap<String, String> datas = new HashMap<>();

        datas.put("获取纬度:", aMapLocation.getLatitude() + "");
        datas.put("获取经度:", aMapLocation.getLongitude() + "");
        datas.put("获取精度信息:", aMapLocation.getAccuracy() + "米");

        datas.put("国家信息:", aMapLocation.getCountry() + "");
        datas.put("省信息:", aMapLocation.getProvince() + "");

        datas.put("城市信息:", aMapLocation.getCity() + "");
        datas.put("城区信息:", aMapLocation.getDistrict() + "");
        datas.put("街道信息:", aMapLocation.getStreet() + "");
        datas.put("街道门牌号信息:", aMapLocation.getStreetNum() + "");
        datas.put("城市编码:", aMapLocation.getCityCode() + "");
        datas.put("地区编码:", aMapLocation.getAdCode() + "");
        datas.put("详细地址:", aMapLocation.getAddress() + "");

        datas.put("定位结果来源:", aMapLocation.getLocationType() + "");
        datas.put("获取当前定位点的AOI信息:", aMapLocation.getAoiName() + "");
        datas.put("获取当前室内定位的建筑物Id:", aMapLocation.getBuildingId() + "");
        datas.put("获取当前室内定位的楼层:", aMapLocation.getFloor() + "");
        datas.put("获取GPS的当前状态:", aMapLocation.getGpsAccuracyStatus() + "");
        datas.put("获取定位时间:", new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(aMapLocation.getTime())) + "");

        adapter.setNewData(datas);
    }

}
