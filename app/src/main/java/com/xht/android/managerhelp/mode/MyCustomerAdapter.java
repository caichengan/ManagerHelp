package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.util.LogHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class MyCustomerAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyCustomerMode> myCustomerLists;
    private static final String TAG = "MyCustomerAdapter";

    public MyCustomerAdapter(Context mContext, List<MyCustomerMode> myCustomerLists) {
        this.mContext = mContext;
        this.myCustomerLists = myCustomerLists;
    }
    @Override
    public int getCount() {
        return myCustomerLists.size();
    }
    @Override
    public Object getItem(int position) {
        return myCustomerLists.get(position);
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
            convertView=View.inflate(mContext, R.layout.item_customer,null);
            holder.myTextCustomer = (TextView) convertView.findViewById(R.id.myTextCustomer);
            holder.myTextData = (TextView) convertView.findViewById(R.id.myTextData);
            holder.mLinearCustomer = (LinearLayout) convertView.findViewById(R.id.mLinearCustomer);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        String companyName = myCustomerLists.get(position).getCompanyName();
        String dataOut = myCustomerLists.get(position).getDataOut();

        LogHelper.i(TAG,"----dataOut--"+dataOut);
        if (dataOut!=null){

            holder.myTextData.setText(dataOut+"天");
        }else{
            holder.myTextData.setText("");
        }

        holder.myTextCustomer.setText(companyName);
        return convertView;
    }
    class ViewHolder{
        LinearLayout mLinearCustomer;
        TextView myTextCustomer;
        TextView myTextData;
    }
}
