package com.xht.android.managerhelp.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xht.android.managerhelp.OrderActivity;
import com.xht.android.managerhelp.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public class OrderMapAdapter extends BaseAdapter {

    ArrayList<String >  arrayList;
    private Context mContext;

    public OrderMapAdapter(OrderActivity orderActivity, ArrayList<String > listData) {
        this.mContext=orderActivity;
        this.arrayList=listData;
    }

    @Override
    public int getCount() {
        if (arrayList.size()>0){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_omap,null);
            holder.tvMap= (TextView) convertView.findViewById(R.id.tvMapList);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tvMap.setText(arrayList.get(position)+"");

        return convertView;
    }

    class ViewHolder {
        TextView tvMap;
    }

}
