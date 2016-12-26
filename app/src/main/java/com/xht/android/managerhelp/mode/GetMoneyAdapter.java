package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.util.LogHelper;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */

public class GetMoneyAdapter extends BaseAdapter {

    private static final String TAG = "GetMoneyAdapter";
    public List<NewDataBean> mListNewAdd;
    public Context mContext;
    private double mMoney;


    public GetMoneyAdapter(List<NewDataBean> mListDatas, Context mContexts){
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
            convertView=View.inflate(mContext, R.layout.item_getmoney,null);

            holder=new ViewHolder();
            holder. mGetMoneyComName = (TextView) convertView.findViewById(R.id.mGetMoneyComName);
            
            holder.mmGetMoneyAddress = (TextView) convertView.findViewById(R.id.mmGetMoneyAddress);
            holder.com = (LinearLayout) convertView.findViewById(R.id.com);
            holder.mGetMoneyContacts = (TextView)convertView. findViewById(R.id.mGetMoneyContacts);
            holder.contacts = (LinearLayout) convertView.findViewById(R.id.contacts);
            holder.mGetMoneyStyle = (TextView) convertView.findViewById(R.id.mGetMoneyStyle);
            holder.mNewAddCustomerTime = (TextView) convertView.findViewById(R.id.mGetMoneyTime);
            holder.mGetMoneyTotal = (TextView) convertView.findViewById(R.id.mGetMoneyTotal);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        NewDataBean newDataBean = mListNewAdd.get(position);


        String comName = newDataBean.getComName();
        String comAddress = newDataBean.getComAddress();

        LogHelper.i(TAG,"------"+comAddress+comName);

        holder. mGetMoneyComName.setText(newDataBean.getComName()+"");
        holder.mmGetMoneyAddress.setText(newDataBean.getComAddress()+"");
        holder.mGetMoneyContacts.setText(newDataBean.getContacts()+"");
        holder.mGetMoneyStyle.setText(newDataBean.getStyle()+"");

        String orderMoney = newDataBean.getOrderMoney();//getString(String.format(getResources().getString(R.string.yuan), billingSelf / 1.00f))


        LogHelper.i(TAG,"-----程安-"+orderMoney);
        if (orderMoney!=null) {
            mMoney = Double.parseDouble(orderMoney);
            holder.mGetMoneyTotal.setText(String.format(mContext.getResources().getString(R.string.heji_yuanjiaofen), mMoney / 100.00f));
        }




        return convertView;
    }

    class  ViewHolder{
         TextView mGetMoneyComName;
         TextView mmGetMoneyAddress;
         TextView mGetMoneyTotal;
         LinearLayout com;
         TextView mGetMoneyContacts;
         LinearLayout contacts;
         TextView mGetMoneyStyle;
        TextView mNewAddCustomerTime;
    }

    //数字三位一空格
    private static String getString(String str){
        DecimalFormat df = new DecimalFormat( "#,##0.00");
        return df.format(Double.parseDouble(str));
    }
}
