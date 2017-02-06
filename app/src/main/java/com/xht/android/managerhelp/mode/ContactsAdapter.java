package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.util.LogHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ContactsAdapter extends BaseAdapter {

    private List<ContactsMode> contactsList;

    private static final String TAG = "ContactsAdapter";
    private Context mContext;



    public ContactsAdapter(Context mContext, List<ContactsMode> mListContacts) {

        this.contactsList=mListContacts;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        if (contactsList.size()>0){
            return contactsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return contactsList.get(position);
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
            convertView=View.inflate(mContext, R.layout.item_customer_contacts,null);

            holder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            holder.contactPhone = (TextView) convertView.findViewById(R.id.contactPhone);
            holder.linOrder = (RelativeLayout) convertView. findViewById(R.id.linOrder);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }


        ContactsMode contactsMode = contactsList.get(position);
        String customerName = contactsMode.getCustomerName();
        String companyWork = contactsMode.getCompanyWork();
        String contactPhone = contactsMode.getContactPhone();
        String companyName = contactsMode.getCompanyName();

        LogHelper.i(TAG,"------11-----"+customerName+companyWork+contactPhone+companyName);

        holder.contactName.setText(customerName);
        holder.contactPhone.setText(contactPhone);

        return convertView;
    }



    class ViewHolder{
        TextView contactName;
        TextView contactPhone;
        RelativeLayout linOrder;
    }

}
