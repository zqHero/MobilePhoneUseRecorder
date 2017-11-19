package com.hero.zhaoq.mpuserecorder.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hero.zhaoq.mpuserecorder.R;

import java.util.ArrayList;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 11:42
 * zhaoqiang:zhaoq_hero@163.com
 */
public class MRecycleVAdapter extends RecyclerView.Adapter {

    private ArrayList<String> datas;
    private final Context context;

    public MRecycleVAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelOffset(R.dimen.listView_item_height)));
        return new MViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder mholder = (MViewHolder) holder;
        mholder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setData(ArrayList<String> newData) {
        if (newData != null) {
            this.datas.clear();
            this.datas = newData;
            notifyDataSetChanged();
        }
    }

    private class MViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;

        public MViewHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
        }

        public void setData(int position) {
            txtContent.setText(datas.get(position));
        }
    }
}
