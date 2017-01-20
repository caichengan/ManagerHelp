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

public class GridViewAdapter extends BaseAdapter {
    private static final String TAG = "GridViewAdapter";

    //数据源
    private List<OrderPictBean.ProcessFileBean> mListProcess ;

    private Context mContext;
    private  ImageLoader imageLoader;

    public GridViewAdapter(Context mContext, List<OrderPictBean.ProcessFileBean> process_file) {
        super();
        this.mContext = mContext;
        this.mListProcess=process_file;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(config);
    }

    public List<OrderPictBean.ProcessFileBean> getmListProcess() {
        return mListProcess;
    }

    public void setmList(List<OrderPictBean.ProcessFileBean> process_file) {
        this.mListProcess=process_file;

    }



    @Override
    public int getCount() {
        return mListProcess.size();
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            holder.iv = (ImageView) convertView.findViewById(R.id.gridViewItem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


/*        BitmapUtils.loadImgageUrl(mListProcess.get(position),holder.iv);*/
        int size = mListProcess.size();

        String file = mListProcess.get(position).getFile();
        LogHelper.i(TAG,"---111111111111-url-----"+file);

        ImageAware imageAware =new ImageViewAware(holder.iv,false);
        //首先得到要设置的GridView
        holder.iv.setTag(file);
        if(holder.iv.getTag()!=null&&holder.iv.getTag().equals(file)){
            imageLoader.displayImage(file,imageAware);

        }
        LogHelper.i(TAG,"---1111111111111111111url-----"+file);
        //设置图片内容，本例木有图片
//       holder.iv.setImageResource();

        LogHelper.i(TAG,"----GridViewAdapter---"+position);
        return convertView;
    }

    class ViewHolder {

        ImageView iv;
    }
}
