package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xht.android.managerhelp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */
public class PerformanceAdapter extends BaseAdapter {
    private List<PerformanceBean> mListNewAdd;
    private Context mContext;

    public PerformanceAdapter(List<PerformanceBean> mListDatas, Context mContexts){
        this.mListNewAdd=mListDatas;
        this.mContext=mContexts;
    }
    @Override
    public int getCount() {
        if (mListNewAdd.size()>0){
            return mListNewAdd.size();
        }
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return mListNewAdd.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.item_performancecount,null);
            holder=new ViewHolder();
            holder.mPerCountName = (TextView)convertView. findViewById(R.id.mPerCountName);
            holder.mPerCountNumber = (TextView) convertView.findViewById(R.id.mPerCountNumber);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        PerformanceBean performanceBean = mListNewAdd.get(position);
        holder.mPerCountName.setText(performanceBean.getServericName());
        holder.mPerCountNumber.setText(performanceBean.getStepNumber());
        performanceBean.getStepNumber();
        return convertView;
    }
    class  ViewHolder{
         TextView mPerCountName;
         TextView mPerCountNumber;
    }

}
