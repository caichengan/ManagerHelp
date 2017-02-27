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
 * Created by Administrator on 2017/2/20.
 */
public class TallyingAdapter extends BaseAdapter {
    private List<TallyMode> tallylistDatas;
    private Context mContext;
    public TallyingAdapter(Context mContext, List<TallyMode> tallylistDatas) {
        this.mContext=mContext;
        this.tallylistDatas=tallylistDatas;
    }
    @Override
    public int getCount() {
        if (tallylistDatas.size()>0) {
            return tallylistDatas.size();
        }
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return tallylistDatas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView= View.inflate(mContext, R.layout.item_tally, null);
            holder.tallyingmCompany = (TextView) convertView.findViewById(R.id.tallying_mCompany);
            holder.tallyingcom = (LinearLayout) convertView.findViewById(R.id.tallying_com);
            holder.tallyingmName = (TextView) convertView.findViewById(R.id.tallying_mName);
            holder.tallyingmAccount = (LinearLayout) convertView.findViewById(R.id.tallying_account);
            holder.tallyingmAddress = (TextView) convertView.findViewById(R.id.tallying_mAddress);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tallyingmCompany.setText(""+tallylistDatas.get(position).getmCompany());
        holder.tallyingmName.setText(""+ tallylistDatas.get(position).getmAccountName());
        holder.tallyingmAddress.setText(""+tallylistDatas.get(position).getmAddress());
        return convertView;
    }
    class ViewHolder{
         TextView tallyingmCompany;
         LinearLayout tallyingcom;
         TextView tallyingmName;
         LinearLayout tallyingmAccount;
         TextView tallyingmAddress;
    }
}
