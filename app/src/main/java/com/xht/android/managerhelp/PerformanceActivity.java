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
import com.xht.android.managerhelp.mode.PerformanceAdapter;
import com.xht.android.managerhelp.mode.PerformanceBean;
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
public class PerformanceActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "PerformanceActivity";
    private Button btnDay;
    private Button btnWeekDay;
    private Button btnMonth;
    private List<PerformanceBean> mListViewDatas;
    private ListView listviewPerformance;
    private int selectNumber;
    private PerformanceAdapter performanceAdapter;
    private int uid;
    private TextView tvPerformance;
    private String number;
    private int stepNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_performance);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("办证业绩统计");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        listviewPerformance = (ListView) findViewById(R.id.listviewPerformance);
        View view = View.inflate(PerformanceActivity.this, R.layout.item_text, null);
        listviewPerformance.addHeaderView(view);
        tvPerformance = (TextView) view.findViewById(R.id.tvLowNotDone);


        btnDay = (Button) findViewById(R.id.btnDay);
        btnWeekDay = (Button) findViewById(R.id.btnWeekDay);
        btnMonth = (Button) findViewById(R.id.btnMonth);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+ uid);

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
        getPerformanceDay();
        btnDay.setOnClickListener(this);
        btnWeekDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+ uid);
        getPerformanceDay();
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
        stepNumber=0;
        switch (v.getId()){
            case R.id.btnDay:
                btnDay.setTextColor(Color.RED);
                //切换到本日
                selectNumber = 0;
                getPerformanceDay();
                break;
            case R.id.btnWeekDay:
                //切换到本周
                btnWeekDay.setTextColor(Color.RED);
                selectNumber = 1;
                getPerformanceWeek();
                break;
            case R.id.btnMonth:
                //切换到本月
                btnMonth.setTextColor(Color.RED);
                getPerformanceMonth();
                selectNumber = 2;
                break;
        }
    }
    //本日
    private void getPerformanceDay() {
            mListViewDatas.clear();
            VolleyHelpApi.getInstance().getPerformanceDay(new APIListener() {
                @Override
                public void onResult(Object result) {
                    LogHelper.i(TAG, "-----getPerformanceDay--" + result.toString());
                    JSONArray JsonAy = null;
                    try {
                        JsonAy = ((JSONObject) result).getJSONArray("entity");
                        int JsonArryLenth=JsonAy.length();
                        for (int i=0;i<JsonArryLenth;i++){
                            //TODO 数据还没有获取到
                            PerformanceBean item=new PerformanceBean();
                            JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                            item.setServericName(JsonItem.getString("employeeName"));
                            item.setStepNumber(JsonItem.getString("permitcount"));

                            mListViewDatas.add(item);
                            LogHelper.i(TAG, "-----3--" );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int size = mListViewDatas.size();
                    LogHelper.i(TAG,"----size-"+size);
                    for (PerformanceBean bean:mListViewDatas){
                        number = bean.getStepNumber();
                        stepNumber =stepNumber+ Integer.parseInt(number);

                    }
                    tvPerformance.setText(""+stepNumber);
                    performanceAdapter.notifyDataSetChanged();
                }
                @Override
                public void onError(Object e) {
                    App.getInstance().showToast(e.toString());

                    tvPerformance.setText(""+0);
                    performanceAdapter.notifyDataSetChanged();
                }
            });
        performanceAdapter = new PerformanceAdapter(mListViewDatas,PerformanceActivity.this);
        listviewPerformance.setAdapter(performanceAdapter);

        }

    //本周
    private void getPerformanceWeek() {
        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getPerformanceWeek(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getStepNumberDay--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){

                        //TODO 数据还没有获取到
                        PerformanceBean item=new PerformanceBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setServericName(JsonItem.getString("employeeName"));
                        item.setStepNumber(JsonItem.getString("permitcount"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----3--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                for (PerformanceBean bean:mListViewDatas){
                    number = bean.getStepNumber();
                    stepNumber =stepNumber+ Integer.parseInt(number);

                }
                tvPerformance.setText(""+stepNumber);
                performanceAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());

                tvPerformance.setText(""+0);
                performanceAdapter.notifyDataSetChanged();
            }
        });

    }

    //本月
    private void getPerformanceMonth() {
        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getPerformanceMonth(new APIListener() {
            @Override
            public void onResult(Object result) {//{"employeeId":2,"permitcount":8,"employeeName":"安仔"}
                LogHelper.i(TAG, "-----getPerformanceMonth--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){

                        //TODO 数据还没有获取到


                        PerformanceBean item=new PerformanceBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);

                        item.setServericName(JsonItem.getString("employeeName"));
                        item.setStepNumber(JsonItem.getString("permitcount"));

                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----3--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                for (PerformanceBean bean:mListViewDatas){
                    number = bean.getStepNumber();
                    stepNumber =stepNumber+ Integer.parseInt(number);

                }
                tvPerformance.setText(""+stepNumber);
                performanceAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());

                tvPerformance.setText(""+0);
                performanceAdapter.notifyDataSetChanged();
            }
        });

    }

    private void getNewAddCustomer(int selectNumber) {
        mListViewDatas.clear();
        switch (selectNumber) {
            case 0:
                for (int i = 0; i < 10; i++) {
                    PerformanceBean item=new PerformanceBean();
                   item.setServericName("程安0");
                    item.setStepNumber("55");


                    mListViewDatas.add(item);
                }
                break;
            case 1:
                for (int i = 0; i < 10; i++) {
                    PerformanceBean item=new PerformanceBean();

                    item.setServericName("程安1");
                    item.setStepNumber("55");
                    mListViewDatas.add(item);
                }
                break;
            case 2:
                for (int i = 0; i < 10; i++) {
                    PerformanceBean item=new PerformanceBean();

                    item.setServericName("程安2");
                    item.setStepNumber("55");
                    mListViewDatas.add(item);

                }
                break;
        }
        //TODO 网络获取数据


        performanceAdapter = new PerformanceAdapter(mListViewDatas,PerformanceActivity.this);

        listviewPerformance.setAdapter(performanceAdapter);

    }
}
