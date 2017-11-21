package com.hero.zhaoq.mpuserecorder.view.adapter;

import android.app.usage.UsageStats;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hero.zhaoq.mpuserecorder.R;
import com.hero.zhaoq.mpuserecorder.moudle.AppInfo;
import com.hero.zhaoq.mpuserecorder.utils.AppInfoUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: zhaoqiang
 * date:2017/11/18 / 11:42
 * zhaoqiang:zhaoq_hero@163.com
 */
public class MRecycleVAdapter extends RecyclerView.Adapter {

    private List<UsageStats> datas;
    private final Context context;
    private LinearLayout.LayoutParams params;

    private List<AppInfo> packageInfos;
    private List<String> stringPackagesNames;

    public MRecycleVAdapter(Context context, ArrayList<UsageStats> datas) {
        this.context = context;
        this.datas = datas;
        //初始化  包名信息
        packageInfos = AppInfoUtils.getAppInfos(context);

        stringPackagesNames = new ArrayList<>();
        for (AppInfo info : packageInfos) {
            stringPackagesNames.add(info.getPackageName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_view, null, false);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelOffset(R.dimen.listView_item_height));
        rootView.setLayoutParams(params);
        return new MViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder mholder = (MViewHolder) holder;
        mholder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setData(List<UsageStats> newData) {
        if (newData != null) {
            this.datas.clear();
            this.datas = newData;
            notifyDataSetChanged();
        }
    }

    private class MViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;
        ImageView appImg;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        public MViewHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
            appImg = itemView.findViewById(R.id.app_img);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void setData(int position) {
            //包含 该对象：取出  该app info
            AppInfo info = null;
            if (stringPackagesNames.contains(datas.get(position).getPackageName())) {
                info = packageInfos.get(stringPackagesNames.indexOf(datas.get(position).getPackageName()));
            }
            if (info != null) {
                txtContent.setText(
                        "名称：" + info.getAppName() + "(" + info.getPackageName() + ")\n" +
                                "最近使用：" + simpleDateFormat.format(new Date(datas.get(position).getLastTimeUsed())) + "\n" +
                                "用时：" + getFormatData(datas.get(position).getTotalTimeInForeground()) + "");
                appImg.setBackgroundDrawable(info.getDrawable());

//                Log.i("info", "getAppName：" + info.getAppName() + "(" + info.getPackageName() + ")\n" +
//                        "getLastTimeUsed：" + simpleDateFormat.format(new Date(datas.get(position).getLastTimeUsed())) + "\n" +
//                        "getFirstTimeStamp：" + simpleDateFormat.format(new Date(datas.get(position).getFirstTimeStamp())) + "\n" +
//                        "getLastTimeStamp：" + simpleDateFormat.format(new Date(datas.get(position).getLastTimeStamp())) + "\n" +
//                        "getTotalTimeInForeground：" + simpleDateFormat.format(new Date(datas.get(position).getTotalTimeInForeground())) + "\n"
//                );
            }

            itemView.setTag(datas.get(position));

            if (onItemClickListener!=null){
                itemView.setOnClickListener(onItemClickListener);
            }
        }
    }

    public View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private String getFormatData(long totalTimeInForeground) {
        int h = (int) (totalTimeInForeground / (1000 * 60 * 60));
        int m = (int) (totalTimeInForeground - (h * (1000 * 60 * 60))) / (1000 * 60);
        int s = (int) (totalTimeInForeground - (h * (1000 * 60 * 60)) - (m * 1000 * 60)) / 1000;
        return h + ":" + m + ":" + s;
    }



}
