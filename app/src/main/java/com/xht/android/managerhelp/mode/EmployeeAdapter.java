package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xht.android.managerhelp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class EmployeeAdapter extends BaseAdapter {
    private List<CompleteCompanyBean.EntityBean.PersonsBean> completeCompanyBeanList;
    private Context mContext;


    public EmployeeAdapter(Context mContext, List<CompleteCompanyBean.EntityBean.PersonsBean> completeCompanyBeanList) {
        this.mContext=mContext;
        this.completeCompanyBeanList=completeCompanyBeanList;
    }

    @Override
    public int getCount() {
        if (completeCompanyBeanList.size()>0){
            return completeCompanyBeanList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return completeCompanyBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.company_item_list,null);
            holder=new ViewHolder();
            holder.itemname = (TextView) convertView.findViewById(R.id.item_name);
            holder.itemrange = (TextView) convertView.findViewById(R.id.item_range);
            holder. itemnumber = (TextView) convertView.findViewById(R.id.item_number);
            holder.itemaddress = (TextView) convertView.findViewById(R.id.item_address);
            holder.itemworker = (TextView) convertView.findViewById(R.id.item_worker);

            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();

        }

        CompleteCompanyBean.EntityBean.PersonsBean personsBean = completeCompanyBeanList.get(position);

        holder.itemname.setText(personsBean.getPersonname());
        holder.itemrange.setText(personsBean.getShareRatio());
        holder.itemaddress.setText(personsBean.getIdcardaddress());
        holder.itemnumber.setText(personsBean.getIdcardcode());
        holder.itemworker.setText(personsBean.getPostname());


        return convertView;
    }

   class ViewHolder{
        TextView itemname;
        TextView itemrange;
        TextView itemnumber;
        TextView itemaddress;
        TextView itemworker;
   }
}
