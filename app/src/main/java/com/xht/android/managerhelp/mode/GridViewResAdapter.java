package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.util.LogHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class GridViewResAdapter extends BaseAdapter {

    private static final String TAG = "GridViewResAdapter";
    //数据源

    private Context mContext;
    private  ImageLoader imageLoader;
    //数据源
    private List<OrderPictBean.ResultFileBean> mListResult ;


    public GridViewResAdapter(Context mContext, List<OrderPictBean.ResultFileBean> mListResult) {
        super();
        this.mListResult=mListResult;
        this.mContext = mContext;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(config);
    }

    public void setmResultList(List<OrderPictBean.ResultFileBean> result_file) {

        this.mListResult=mListResult;
    }

    public List<OrderPictBean.ResultFileBean> getmListResult() {
        return mListResult;
    }


    @Override
    public int getCount() {
        return mListResult.size();
    }

    @Override
    public Object getItem(int position) {


        return position;


    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.gridViewItem);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置图片内容，本例木有图片
//        holder.iv.setImageResource(getItem(position));

     /*   BitmapUtils.loadImgageUrl(mListResult.get(position),holder.iv);*/

        String file = mListResult.get(position).getFile();
        LogHelper.i(TAG,"---111111111111-url-----"+file);

        ImageAware imageAware =new ImageViewAware(holder.iv,false);
        //首先得到要设置的GridView
        holder.iv.setTag(file);
        if(holder.iv.getTag()!=null&&holder.iv.getTag().equals(file)){

            imageLoader.displayImage(file,imageAware);
        }
        return convertView;
    }

    class ViewHolder {

        ImageView iv;


    }
}
