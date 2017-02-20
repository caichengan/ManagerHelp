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
 * Created by Administrator on 2017/1/10.
 */

public class PictureAdapter extends BaseAdapter {
    private Context mContext;
    private List<CustomerOrderMode> mListCustomer;

    public PictureAdapter(Context mContext, List<CustomerOrderMode> mListCustomer) {
        this.mContext = mContext;
        this.mListCustomer = mListCustomer;
    }

    @Override
    public int getCount() {
        if (mListCustomer.size()>0){
            return mListCustomer.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mListCustomer.get(position);
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
            convertView=View.inflate(mContext, R.layout.item_picture,null);

            holder.picStyle = (TextView) convertView.findViewById(R.id.orderStyle);
            holder.picMoney = (TextView) convertView.findViewById(R.id.orderMoney);
            holder.picTime = (TextView) convertView.findViewById(R.id.orderTime);
            holder.linpic = (LinearLayout) convertView.findViewById(R.id.linOrder);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        CustomerOrderMode myCustomerMode = mListCustomer.get(position);
        String picStyle = myCustomerMode.getOrderStyle();
        String picTime = myCustomerMode.getOrderStartTime();
        String picMoney = myCustomerMode.getOrderMoney();

        holder.picStyle.setText(picStyle);
        holder.picMoney.setText(picMoney);
        holder.picTime.setText(Utils.getTimes(picTime));


        return convertView;
    }

    class ViewHolder{
        TextView picStyle;
        TextView picMoney;
        TextView picTime;
        LinearLayout linpic;
    }
}
