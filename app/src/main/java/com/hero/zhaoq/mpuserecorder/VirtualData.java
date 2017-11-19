package com.hero.zhaoq.mpuserecorder;

import java.util.ArrayList;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 11:43
 * zhaoqiang:zhaoq_hero@163.com
 */

public class VirtualData {


    /**
     * 获取虚拟数据
     * @return
     */
    public static ArrayList<String> getDatas() {
        ArrayList<String> strss = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            strss.add("数据" + i);
        }

        return strss;
    }


}
