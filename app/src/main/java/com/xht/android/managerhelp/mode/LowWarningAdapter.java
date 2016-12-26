package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.MainActivity;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/17.
 */

public class LowWarningAdapter extends BaseAdapter {


    private List<NewDataBean> mListNewAdd;
    private Context mContext;
    private   ViewHolder holder;

    private LinearLayout logincontainer;
    private PopupWindow popupwindow;
    private ListView listView;
    private static final String TAG = "LowWarningAdapter";
    private List<ImageView> levels;



    public LowWarningAdapter(ListView listView,List<NewDataBean> mListDatas, Context mContexts){
        this.mListNewAdd=mListDatas;
        this.mContext=mContexts;
        this.listView=listView;


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
    public View getView(final int position, View convertView, final ViewGroup parent) {


        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.item_lowwarning,null);

            holder=new ViewHolder();

            holder. mWarningComName = (TextView) convertView.findViewById(R.id.mWarningComName);

            holder.  com = (LinearLayout) convertView.findViewById(R.id.com);
            holder.mWarningName = (TextView) convertView.findViewById(R.id.mWarningName);
            holder.mWarningBanPeople = (TextView) convertView.findViewById(R.id.mWarningBanPeople);
            holder. contacts = (LinearLayout) convertView.findViewById(R.id.contacts);

            holder.mWarningStar1 = (ImageView) convertView.findViewById(R.id.mWarningStar1);
            holder.mWarningStar2 = (ImageView) convertView.findViewById(R.id.mWarningStar2);
            holder.mWarningStar3 = (ImageView) convertView.findViewById(R.id.mWarningStar3);
            holder. mWarningStar4 = (ImageView) convertView.findViewById(R.id.mWarningStar4);
            holder. mWarningStar5 = (ImageView) convertView.findViewById(R.id.mWarningStar5);

            holder.mWarningRarise = (Button) convertView.findViewById(R.id.mWarningRarise);
            holder.mWarningDone = (Button) convertView.findViewById(R.id.mWarningDone);

            levels = new ArrayList<>();
            levels.add(holder.mWarningStar1);
            levels.add(holder.mWarningStar2);
            levels.add(holder.mWarningStar3);
            levels.add(holder.mWarningStar4);
            levels.add(holder.mWarningStar5);

            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        final NewDataBean newDataBean = mListNewAdd.get(position);

        holder. mWarningComName.setText(newDataBean.getComName());
        holder. mWarningName.setText(newDataBean.getStepName());
        holder.mWarningBanPeople.setText(newDataBean.getBanZhengContact());

        String status = newDataBean.getStatus();
        String level = newDataBean.getLevel();

        //String []levels=new String[]{holder.mWarningStar1,holder.mWarningStar2,holder.mWarningStar3,holder.mWarningStar4};

        int lev=Integer.parseInt(level);
        if (lev>0) {

            for (int i = 0; i < lev; i++) {
                levels.get(i).setImageResource(R.drawable.list_icon_star_big_full);
            }
            for (int i = lev; i < 5; i++) {
                levels.get(i).setImageResource(R.drawable.list_icon_star_big_empty);
            }
        }else{
            for (int i = 0; i < 5; i++) {
                levels.get(i).setImageResource(R.drawable.list_icon_star_big_empty);
            }
        }

        LogHelper.i(TAG,"------"+status);
        if (status.equals("0")){
            holder.mWarningDone.setText("未处理");
            holder.mWarningDone.setClickable(true);
            holder.mWarningDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeSureDoneMethod(newDataBean);
                }
            });


        }else{
            holder.mWarningDone.setText("已处理");
            holder.mWarningDone.setClickable(false);
        }


        final View finalConvertView = convertView;
        holder.mWarningRarise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看评价 TODO 弹出浮窗
              dismissPopupwindow();
              getPopuWindow(finalConvertView,parent,position);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                dismissPopupwindow();
            }
        });

        return convertView;
    }

    //确认处理
    private void makeSureDoneMethod(NewDataBean newDataBean) {

        final JSONObject object=new JSONObject();
        String commentId = newDataBean.getCommentId();


        try {//TODO item.setCommentId(JsonItem.getString("commentId"));
            object.put("commentid",commentId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyHelpApi.getInstance().postSureMethod(object, new APIListener() {
            @Override
            public void onResult(Object result) {

                JSONObject object= (JSONObject) result;

                try {
                    String code = object.getString("code");
                    if (code.equals("1")){
                        App.getInstance().showToast("已经进行处理");

                        mContext.startActivity(new Intent(mContext, MainActivity.class));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Object e) {
            }
        });
    }
    public  void dismissPopupwindow() {
        if (popupwindow != null && popupwindow.isShowing()) {
            popupwindow.dismiss();
            popupwindow = null;
        }
    }

    private void getPopuWindow(View view,ViewGroup parent,int position) {

        View convertView = View.inflate(mContext,
                R.layout.poplayout, null);
        TextView mApprise= (TextView) convertView.findViewById(R.id.tvApprise);
        mListNewAdd.get(position).getComName();
        String comment = mListNewAdd.get(position).getComment();
        mApprise.setText(comment+"");
        popupwindow = new PopupWindow(convertView, -2, -2);
        popupwindow.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        int[] location = new int[2];
        view.getLocationInWindow(location);
        popupwindow.showAtLocation(parent, Gravity.TOP + Gravity.LEFT,
                50, location[1]);
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(1000);
        ScaleAnimation sa = new ScaleAnimation(0.2f, 1.0f, 0.2f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(1000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(sa);
        convertView.startAnimation(set);

    }
    class  ViewHolder{
         TextView mWarningComName;
         TextView mWarningBanPeople;
         TextView mStepAddress;
         LinearLayout com;
         TextView mWarningName;
         LinearLayout contacts;
         ImageView mWarningStar1;
         ImageView mWarningStar2;
        ImageView mWarningStar3;
         ImageView mWarningStar4;
        ImageView mWarningStar5;
         Button mWarningRarise;
        Button mWarningDone;

    }


}
