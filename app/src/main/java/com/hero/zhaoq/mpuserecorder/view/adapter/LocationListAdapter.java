package com.hero.zhaoq.mpuserecorder.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hero.zhaoq.mpuserecorder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 16:53
 * zhaoqiang:zhaoq_hero@163.com
 */

public class LocationListAdapter extends BaseAdapter {

    private final Context context;
    private HashMap<String, String> datas;

    public LocationListAdapter(Context context, HashMap<String, String> stringStringHashMap) {
        this.context = context;
        this.datas = stringStringHashMap;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Map.Entry getItem(int position) {
        ArrayList<Map.Entry> antrys = new ArrayList<>();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            antrys.add(entry);
        }
        return antrys.size() == 0 ? null : antrys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.locaiton_list_item_view, null, false);
        TextView title = itemView.findViewById(R.id.info_title);
        TextView content = itemView.findViewById(R.id.info_content);
        title.setText(position + ":  " + getItem(position).getKey() + "");
        content.setText(getItem(position).getValue() + "");

        return itemView;
    }

    public void setNewData(HashMap<String, String> datas) {
        if (datas != null) {
            this.datas = datas;
            notifyDataSetChanged();
        }
    }
}
