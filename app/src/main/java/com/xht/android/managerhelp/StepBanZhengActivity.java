package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.mode.NewDataBean;
import com.xht.android.managerhelp.mode.StepBanZhengAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */
public class StepBanZhengActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "StepBanZhengActivity";
    private ListView listviewStep;
    Button btnDay;
    Button btnWeekDay;
    Button btnMonth;
    private List<NewDataBean> mListViewDatas;
    private StepBanZhengAdapter stepBanZhengAdapter;
    private int selectNumber;
    private TextView tvStepMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stepbanzheng);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("办证步骤量");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        listviewStep = (ListView) findViewById(R.id.listviewStep);

        View view = View.inflate(StepBanZhengActivity.this, R.layout.item_text, null);
        listviewStep.addHeaderView(view);
        tvStepMoney = (TextView) view.findViewById(R.id.tvLowNotDone);

        btnDay = (Button) findViewById(R.id.btnDay);
        btnWeekDay = (Button) findViewById(R.id.btnWeekDay);
        btnMonth = (Button) findViewById(R.id.btnMonth);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        int uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+uid);

        final PullRefreshLayout layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
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
        btnDay.setTextColor(Color.RED);

        getStepDay();

        btnDay.setOnClickListener(this);
        btnWeekDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void resetColor() {
        btnDay.setTextColor(Color.WHITE);
        btnMonth.setTextColor(Color.WHITE);
        btnWeekDay.setTextColor(Color.WHITE);

    }

    @Override
    public void onClick(View v) {
        resetColor();
        switch (v.getId()){
            case R.id.btnDay:
                btnDay.setTextColor(Color.RED);
                //切换到本日
                getStepDay();
                break;
            case R.id.btnWeekDay:
                //切换到本周
                btnWeekDay.setTextColor(Color.RED);
                getStepWeek();
                break;
            case R.id.btnMonth:
                //切换到本月
                btnMonth.setTextColor(Color.RED);
                getStepMonth();
                break;
        }



    }

    //本月
    private void getStepMonth() {

        mListViewDatas.clear();
       /* item.setComName("安1");
        item.setComAddress("广药宿舍");
        item.setStepName("发朋友圈");
        item.setBanZhengContact("程安");
        item.setTime("2017-11-14");*/
        VolleyHelpApi.getInstance().getStepNumberMonth(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getStepNumberDay--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){

                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setBanZhengContact(JsonItem.getString("employeeName"));
                        item.setComAddress(JsonItem.getString("countyName"));
                        item.setTime(JsonItem.getString("timeformat"));
                        item.setStepName(JsonItem.getString("flowName"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----3--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvStepMoney.setText(""+mListViewDatas.size());
                stepBanZhengAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvStepMoney.setText(""+mListViewDatas.size());
                stepBanZhengAdapter.notifyDataSetChanged();
            }
        });

    }

    //本周
    private void getStepWeek() {
        mListViewDatas.clear();
       /* item.setComName("安1");
        item.setComAddress("广药宿舍");
        item.setStepName("发朋友圈");
        item.setBanZhengContact("程安");
        item.setTime("2017-11-14");*/
            VolleyHelpApi.getInstance().getStepNumberWeek(new APIListener() {
                @Override
                public void onResult(Object result) {
                    LogHelper.i(TAG, "-----getStepNumberDay--" + result.toString());
                    JSONArray JsonAy = null;
                    try {
                        JsonAy = ((JSONObject) result).getJSONArray("entity");
                        int JsonArryLenth=JsonAy.length();
                        for (int i=0;i<JsonArryLenth;i++){

                            //TODO 数据还没有获取到


                            NewDataBean item=new NewDataBean();
                            JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                            item.setComName(JsonItem.getString("companyName"));
                            item.setBanZhengContact(JsonItem.getString("employeeName"));
                            item.setComAddress(JsonItem.getString("countyName"));
                            item.setTime(JsonItem.getString("timeformat"));
                            item.setStepName(JsonItem.getString("flowName"));
                            mListViewDatas.add(item);
                            LogHelper.i(TAG, "-----3--" );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int size = mListViewDatas.size();
                    LogHelper.i(TAG,"----size-"+size);
                    tvStepMoney.setText(""+mListViewDatas.size());
                    stepBanZhengAdapter.notifyDataSetChanged();

                }
                @Override
                public void onError(Object e) {
                    App.getInstance().showToast(e.toString());
                    int size = mListViewDatas.size();
                    LogHelper.i(TAG,"----size-"+size);
                    tvStepMoney.setText(""+mListViewDatas.size());
                    stepBanZhengAdapter.notifyDataSetChanged();
                }
            });

    }

    //本日
    private void getStepDay() {
        mListViewDatas.clear();

       /* item.setComName("安1");
        item.setComAddress("广药宿舍");
        item.setStepName("发朋友圈");
        item.setBanZhengContact("程安");
        item.setTime("2017-11-14");*/

        VolleyHelpApi.getInstance().getStepNumberDay(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getStepNumberDay--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){

                        //TODO 数据还没有获取到


                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setBanZhengContact(JsonItem.getString("employeeName"));
                        item.setComAddress(JsonItem.getString("countyName"));
                        item.setTime(JsonItem.getString("timeformat"));
                        item.setStepName(JsonItem.getString("flowName"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----3--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvStepMoney.setText(""+mListViewDatas.size());
                stepBanZhengAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvStepMoney.setText(""+mListViewDatas.size());
                stepBanZhengAdapter.notifyDataSetChanged();
            }
        });



        stepBanZhengAdapter = new StepBanZhengAdapter(mListViewDatas,StepBanZhengActivity.this);
        listviewStep.setAdapter(stepBanZhengAdapter);
    }


    private void getNewAddCustomer(int selectNumber) {
        mListViewDatas.clear();
        switch (selectNumber) {
            case 0:
                for (int i = 0; i < 10; i++) {
                    NewDataBean item=new NewDataBean();
                    item.setComName("安0");
                    item.setComAddress("广药宿舍");
                    item.setStepName("发朋友圈");
                    item.setBanZhengContact("程安");
                    item.setTime("2017-11-14");

                    mListViewDatas.add(item);
                }
                break;
            case 1:
                for (int i = 0; i < 10; i++) {
                    NewDataBean item=new NewDataBean();
                    item.setComName("安1");
                    item.setComAddress("广药宿舍");
                    item.setStepName("发朋友圈");
                    item.setBanZhengContact("程安");
                    item.setTime("2017-11-14");

                    mListViewDatas.add(item);
                }
                break;
            case 2:
                for (int i = 0; i < 10; i++) {
                    NewDataBean item=new NewDataBean();
                    item.setComName("安2");
                    item.setComAddress("广药宿舍");
                    item.setStepName("发朋友圈");
                    item.setBanZhengContact("程安");
                    item.setTime("2017-11-14");

                    mListViewDatas.add(item);

                }
                break;
        }
        //TODO 网络获取数据


        stepBanZhengAdapter = new StepBanZhengAdapter(mListViewDatas,StepBanZhengActivity.this);
        listviewStep.setAdapter(stepBanZhengAdapter);

    }

}
