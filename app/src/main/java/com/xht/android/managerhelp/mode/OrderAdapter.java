package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.util.Utils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */

public class OrderAdapter extends BaseAdapter {

    private List<CustomerOrderMode> orderList;

    private Context mContext;
    private TextView orderStyle;
    private TextView orderMoney;
    private TextView orderTime;
    private LinearLayout linOrder;

    public OrderAdapter(List<CustomerOrderMode> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (orderList.size()>0){
            return orderList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
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
            convertView=View.inflate(mContext, R.layout.item_order,null);

            holder.orderStyle = (TextView) convertView.findViewById(R.id.orderStyle);
            holder.orderMoney = (TextView) convertView.findViewById(R.id.orderMoney);
            holder.orderTime = (TextView) convertView.findViewById(R.id.orderTime);
            holder.linOrder = (LinearLayout) convertView.findViewById(R.id.linOrder);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        CustomerOrderMode OrderMode = orderList.get(position);



        String orderStyle = OrderMode.getOrderStyle();
        String orderMoney = OrderMode.getOrderMoney();
        String orderStartTime = OrderMode.getOrderStartTime();


        holder.orderStyle.setText(orderStyle);
        holder.orderMoney.setText(orderMoney);
        holder.orderTime.setText(Utils.getTimes(orderStartTime));

        return convertView;
    }

    class ViewHolder{
        TextView orderStyle;
        TextView orderMoney;
        TextView orderTime;
        LinearLayout linOrder;
    }

}
