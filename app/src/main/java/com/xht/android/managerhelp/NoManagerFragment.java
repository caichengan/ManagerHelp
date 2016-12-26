package com.xht.android.managerhelp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.mode.LowWarningAdapter;
import com.xht.android.managerhelp.mode.NewDataBean;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoManagerFragment extends Fragment {

    private ListView listviewLowWarning;
    private TextView tvLowNotDone;
    private List<NewDataBean> mListViewDatas;
    private int mDataNumber=0;

    private static final String TAG = "NoManagerFragment";
    private LowWarningAdapter lowWarningAdapter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

    /*    Bundle bundle = getIntent().getBundleExtra("bundle");
        int uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+uid);*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.nomanagerwarn, null);
        listviewLowWarning = (ListView) inflate. findViewById(R.id.listviewNo);

        View view = View.inflate(getActivity(), R.layout.item_text, null);
        listviewLowWarning.addHeaderView(view);
        tvLowNotDone= (TextView) view.findViewById(R.id.tvLowNotDone);

        final PullRefreshLayout layout = (PullRefreshLayout) inflate. findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // 刷新3秒完成
                        layout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        mListViewDatas = new ArrayList<>();
       // getTimeDatas(0);
        getLowDatasDay();
        return inflate;

	}





    //本周
    private void getLowDatasWeek() {

        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getLowDatasWeek(new APIListener() {
            //"entity":[{"telephone":"13652365101","employeeName":"祝飞"},{"telephone":"13652365101","employeeName":"ZHUFEI"}
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getLowDatasWeek--" + result.toString());
             JSONObject obj=   (JSONObject) result;
                String code = obj.optString("code");
                if (code.equals("0")){
                    LogHelper.i(TAG, "-----code--" + result.toString());
                    return;
                }
                JSONArray JsonAy = null;
                try {

                    //{"level":"1","commentTime":"1482395879410","commentContent":"一个星","companyName":"测试公司八",
                    // "flowName":"资料交接","orderId":"12","flowId":"1","commentId":1,"employeeName":"安仔"}
                    JsonAy = obj.getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();

                    LogHelper.i(TAG,"--------JsonArryLenth----"+JsonArryLenth);

                    for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);

                        item.setStatus(JsonItem.getString("dealStatus"));
                        item.setCommentId(JsonItem.getString("commentId"));
                        item.setComName(JsonItem.getString("companyName"));
                        item.setBanZhengContact(JsonItem.getString("employeeName"));
                        item.setComment(JsonItem.getString("commentContent"));
                        item.setLevel(JsonItem.getString("level"));
                        item.setStepName(JsonItem.getString("flowName"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "----item---" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                lowWarningAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                lowWarningAdapter.notifyDataSetChanged();
            }
        });

    }

    //本月
    private void getLowDatasMonth() {
        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getLowDatasMonth(new APIListener() {
            //"entity":[{"telephone":"13652365101","employeeName":"祝飞"},{"telephone":"13652365101","employeeName":"ZHUFEI"}
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getLowDatasMonth--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setCommentId(JsonItem.getString("commentId"));
                        item.setBanZhengContact(JsonItem.getString("employeeName"));
                        item.setComment(JsonItem.getString("commentContent"));
                        item.setLevel(JsonItem.getString("level"));
                        item.setStepName(JsonItem.getString("flowName"));
                        item.setStatus(JsonItem.getString("dealStatus"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-------" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lowWarningAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                lowWarningAdapter.notifyDataSetChanged();
            }
        });
    }
    //本日
    private void getLowDatasDay() {
            mListViewDatas.clear();
            VolleyHelpApi.getInstance().getLowDatasDay(new APIListener() {
                @Override
                public void onResult(Object result) {
                    LogHelper.i(TAG, "-----getLowDatasDay--" + result.toString());
                    JSONArray JsonAy = null;
                    try {
                        JsonAy = ((JSONObject) result).getJSONArray("entity");
                        int JsonArryLenth=JsonAy.length();
                        for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                            NewDataBean item=new NewDataBean();
                            JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                            item.setComName(JsonItem.getString("companyName"));

                            item.setBanZhengContact(JsonItem.getString("employeeName"));
                            item.setComment(JsonItem.getString("commentContent"));
                            item.setLevel(JsonItem.getString("level"));
                            item.setCommentId(JsonItem.getString("commentId"));
                            item.setStatus(JsonItem.getString("dealStatus"));
                            item.setStepName(JsonItem.getString("flowName"));
                            mListViewDatas.add(item);
                            LogHelper.i(TAG, "-------" );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tvLowNotDone.setText("33");
                    lowWarningAdapter.notifyDataSetChanged();
                }
                @Override
                public void onError(Object e) {
                    App.getInstance().showToast(e.toString());
                }
            });
        lowWarningAdapter = new LowWarningAdapter(listviewLowWarning,mListViewDatas,getActivity());
        listviewLowWarning.setAdapter(lowWarningAdapter);

        }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden){
            lowWarningAdapter.dismissPopupwindow();
        }

    }


    private void getTimeDatas(int mDataNumber) {
        mListViewDatas.clear();

        switch (mDataNumber){
            case 0:
                for (int i=0;i<10;i++){
                    NewDataBean item=new NewDataBean();
                    item.setComName("安0");

                    item.setStepName("工商注册");
                    item.setBanZhengContact("程安");

                    mListViewDatas.add(item);

                }
                break;
            case 1:
                for (int i=0;i<10;i++){
                    NewDataBean item=new NewDataBean();
                    item.setComName("安1");
                    item.setStepName("工商注册");
                    item.setBanZhengContact("程安");
                    mListViewDatas.add(item);

                }
                break;
            case 2:
                for (int i=0;i<10;i++){
                    NewDataBean item=new NewDataBean();
                    item.setComName("安2");
                    item.setStepName("工商注册");
                    item.setBanZhengContact("程安");
                    mListViewDatas.add(item);
                }
                break;
        }
         lowWarningAdapter = new LowWarningAdapter(listviewLowWarning,mListViewDatas,getActivity());
        listviewLowWarning.setAdapter(lowWarningAdapter);

    }
}
