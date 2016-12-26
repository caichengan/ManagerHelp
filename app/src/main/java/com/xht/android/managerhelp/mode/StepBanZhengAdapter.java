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

public class StepBanZhengAdapter extends BaseAdapter {


    private List<NewDataBean> mListNewAdd;
    private Context mContext;


    public StepBanZhengAdapter(List<NewDataBean> mListDatas, Context mContexts){
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
            convertView=View.inflate(mContext, R.layout.item_banzhengstep,null);

            holder=new ViewHolder();
            holder.mStepComName = (TextView)convertView. findViewById(R.id.mStepComName);
            holder. mStepAddress = (TextView) convertView.findViewById(R.id.mStepAddress);
            holder.mStepName = (TextView) convertView.findViewById(R.id.mStepName);
            holder. mStepContact = (TextView) convertView.findViewById(R.id.mStepContact);
            holder. mStepTime = (TextView) convertView.findViewById(R.id.mStepTime);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        NewDataBean newDataBean = mListNewAdd.get(position);
        holder. mStepComName.setText(newDataBean.getComName()+"");
        holder.mStepAddress.setText(newDataBean.getComAddress()+"");
        holder.mStepName.setText(newDataBean.getStepName()+"");
        holder.mStepContact.setText(newDataBean.getBanZhengContact()+"");
        holder.mStepTime.setText(newDataBean.getTime()+"");



        return convertView;
    }




    class  ViewHolder{

         TextView mStepComName;
         TextView mStepAddress;
         TextView mStepName;
         TextView mStepContact;
        TextView mStepTime;

    }


}
