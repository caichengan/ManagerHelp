package com.xht.android.managerhelp.mode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.managerhelp.OrderPictureActivity;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.SpaceImageDetailActivity;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;
import com.xht.android.managerhelp.view.NoScrollGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public class OrderPictAdapter extends BaseAdapter {

    private List<OrderPictBean> entity;
    private static final String TAG = "OrderPictAdapter";
    private Context mContext;
    private GridViewAdapter adapter;
    private GridViewResAdapter adapterRes;

    ListView listviewPic;


    public OrderPictAdapter(OrderPictureActivity orderPictureActivity, List<OrderPictBean> mListEntity, ListView listviewPic) {
        this.mContext=orderPictureActivity;
        this.entity=mListEntity;
        this.listviewPic=listviewPic;
    }

    @Override
    public int getCount() {
        if (entity.size()>0){
            return entity.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return entity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_pic_gridview,null);
            holder.styleName = (TextView)convertView. findViewById(R.id.styleName);
            holder.process = (TextView) convertView.findViewById(R.id.process);

            holder.gridviewProcess = (NoScrollGridView)convertView. findViewById(R.id.gridviewProcess);
            holder.gridviewResult = (NoScrollGridView) convertView.findViewById(R.id.gridviewResult);
            holder.result = (TextView)convertView. findViewById(R.id.result);
            convertView.setTag(holder);

        }else{
           holder = (ViewHolder) convertView.getTag();
        }


        final List<OrderPictBean.ProcessFileBean> process_file = entity.get(position).getProcess_file();
        final List<OrderPictBean.ResultFileBean> result_file = entity.get(position).getResult_file();

        LogHelper.i(TAG,"--------------3333---------");

        LogHelper.i(TAG,"-------process_file.size()----"+process_file.size());
        LogHelper.i(TAG,"-------result_file.size()----"+result_file.size());

        holder.styleName.setText(entity.get(position).getFlowName());
        //定义gridView的数据源
        adapter = new GridViewAdapter(mContext,entity.get(position).getProcess_file());
        holder.gridviewProcess.setAdapter(adapter);

        adapterRes = new GridViewResAdapter(mContext,entity.get(position).getResult_file());
        holder.gridviewResult.setAdapter(adapterRes);

        //将adapter定义在此，优化滑动效果(核心)
        holder.gridviewProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int Childposition, long id) {
                        //App.getInstance().showToast("这是第"+position+"个item");

                        Bundle bundle=new Bundle();
                        String file = process_file.get(Childposition-0).getFile();
                        bundle.putString("file",file);

                        LogHelper.i(TAG,"-------Process--"+Childposition);
                        LogHelper.i(TAG,"-------Process-file-"+file);
                        IntentUtils.startActivityNumber((Activity) mContext,bundle,SpaceImageDetailActivity.class);


                    }
                });

        holder.gridviewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //App.getInstance().showToast("这是第"+position+"个item");

                LogHelper.i(TAG,"-------Result--"+position);
                Bundle bundle=new Bundle();
                String file = result_file.get(position-0).getFile();
                bundle.putString("file",file);
                LogHelper.i(TAG,"-------Result-file-"+file);
                IntentUtils.startActivityNumber((Activity) mContext,bundle,SpaceImageDetailActivity.class);

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView styleName;
        TextView process;
        TextView result;
        NoScrollGridView gridviewProcess;
        NoScrollGridView gridviewResult;

    }

}
