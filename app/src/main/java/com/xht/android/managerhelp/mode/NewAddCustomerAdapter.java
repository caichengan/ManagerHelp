package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */

public class NewAddCustomerAdapter extends BaseAdapter {


    private List<NewDataBean> mListNewAdd;
    private Context mContext;


    public NewAddCustomerAdapter(List<NewDataBean> mListDatas, Context mContexts){
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
            convertView=View.inflate(mContext, R.layout.item_newaddcustomer,null);

            holder=new ViewHolder();
            holder. mNewAddCustomerComName = (TextView) convertView.findViewById(R.id.mNewAddCustomerComName);

            holder.mNewAddCustomerComAddress = (TextView) convertView.findViewById(R.id.mNewAddCustomerComAddress);
            holder.com = (LinearLayout) convertView.findViewById(R.id.com);
            holder.mNewAddCustomerContacts = (TextView)convertView. findViewById(R.id.mNewAddCustomerContacts);
            holder.contacts = (LinearLayout) convertView.findViewById(R.id.contacts);
            holder.mNewAddCustomerStyle = (TextView) convertView.findViewById(R.id.mNewAddCustomerStyle);
            holder.mNewAddCustomerTime = (TextView) convertView.findViewById(R.id.mNewAddCustomerTime);
            holder.mNewAddCustomerTime = (TextView) convertView.findViewById(R.id.mNewAddCustomerTime);
            holder.logincontainer = (LinearLayout) convertView.findViewById(R.id.login_container);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        NewDataBean newDataBean = mListNewAdd.get(position);
        holder. mNewAddCustomerComName.setText(newDataBean.getComName()+"");
        holder.mNewAddCustomerComAddress.setText(newDataBean.getComAddress()+"");
        holder.mNewAddCustomerContacts.setText(newDataBean.getContacts()+"");
        holder.mNewAddCustomerStyle.setText(newDataBean.getStyle()+"");


        return convertView;
    }


    class  ViewHolder{

         TextView mNewAddCustomerComName;
         TextView mNewAddCustomerComAddress;
         LinearLayout com;
         TextView mNewAddCustomerContacts;
         LinearLayout contacts;
         TextView mNewAddCustomerStyle;
        TextView mNewAddCustomerTime;
        LinearLayout logincontainer;

    }


}
