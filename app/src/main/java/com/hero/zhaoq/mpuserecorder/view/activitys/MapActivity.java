package com.hero.zhaoq.mpuserecorder.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.view.fragments.BaseFragment;
import com.hero.zhaoq.mpuserecorder.view.fragments.LocationDetailFragment;
import com.hero.zhaoq.mpuserecorder.view.fragments.Normal3dMapFragment;


public class MapActivity extends BaseActivity implements View.OnClickListener {

    private TextView leftTv;
    private ImageView rightTv;
    private TextView title;
    private ImageView rightBackBg;
    private DrawerLayout drawLayout;

    private TextView locationDetail; //位置详情信息
    private TextView map_3d;    //3d  地图：   不使用2d  2d实在没法看  有需要的  自行查阅  高德官网：

    private FragmentManager fragmentManager;
    private FragmentTransaction tx;

    private SparseArray<BaseFragment> fragments;

    private LocationDetailFragment locationDetailFragment;
    private BaseFragment normal3dMapFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        leftTv = findViewById(R.id.title_left);
        rightTv = findViewById(R.id.title_right);
        rightBackBg = findViewById(R.id.right_back_bg);
        map_3d = findViewById(R.id.map_3d);  //3d  地图：

        title = findViewById(R.id.title_txt);
        leftTv.setText("more");
        rightTv.setVisibility(View.GONE);
        title.setText("Map Location");
        rightBackBg.setVisibility(View.VISIBLE);

        drawLayout = findViewById(R.id.draw_layout);
        locationDetail = findViewById(R.id.location_detail);
        drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,Gravity.LEFT);

        initFragment();
    }

    private void initFragment() {
        fragments = new SparseArray<>();

        //add  fragment:
        locationDetailFragment = LocationDetailFragment.newInstance();
        normal3dMapFragment = Normal3dMapFragment.newInstance();


        fragments.put(0, normal3dMapFragment);
        fragments.put(1, locationDetailFragment);

        fragmentManager = getSupportFragmentManager();
        tx = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.size(); i++) {
            tx.add(R.id.drawlayout_content, fragments.get(i), i + "fragment");
            tx.hide(fragments.get(i));
        }

        tx.show(normal3dMapFragment).commit();
    }


    @Override
    protected void initListener() {
        rightBackBg.setOnClickListener(this);
        leftTv.setOnClickListener(this);
        locationDetail.setOnClickListener(this);
        map_3d.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_back_bg:
                finish();
                break;
            case R.id.title_left:
                if (drawLayout.isDrawerOpen(Gravity.LEFT)){
                    drawLayout.closeDrawer(Gravity.LEFT);
                }else{
                    drawLayout.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.location_detail:
                //location detial：
                toFragment(locationDetail.getText().toString());
                break;

            case R.id.map_3d:
                //normal map：  3dmap
                toFragment(map_3d.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMLocationChanged(AMapLocation aMapLocation) {
        super.onMLocationChanged(aMapLocation);
        //更改   位置信息：
        if (locationDetailFragment!=null){
            locationDetailFragment.onMLocationChanged(aMapLocation);
        }
    }

    /**
     * TO  Fragment:
     */
    private void toFragment(String s) {
        drawLayout.closeDrawer(Gravity.LEFT);
        if (s.equals(getResources().getString(R.string.to_3d_map))) {
            //3d 地图：
            showFragment(normal3dMapFragment);
        } else if (s.equals(getResources().getString(R.string.my_location_detail))) {
            //to 位置详情:
            showFragment(locationDetailFragment);
        }
    }

    private void showFragment(BaseFragment showFrag) {
        tx = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragm = fragmentManager.findFragmentByTag(i + "fragment");
            if (fragm == showFrag) {
                tx.show(showFrag);
            } else {
                tx.hide(fragments.get(i));
            }
        }
        tx.commit();
    }

    public static void startAction(Context context) {
        context.startActivity(new Intent(context, MapActivity.class));
    }
}
