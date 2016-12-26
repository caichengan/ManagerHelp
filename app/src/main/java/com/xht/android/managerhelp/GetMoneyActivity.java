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
import com.xht.android.managerhelp.mode.GetMoneyAdapter;
import com.xht.android.managerhelp.mode.NewDataBean;
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
public class GetMoneyActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "GetMoneyActivity";
    private ListView mListViewNewAdd;

    private Button btnDay;
    private Button btnWeekDay;
    private Button btnMonth;
    private List<NewDataBean> mListViewDatas;
    private GetMoneyAdapter mGetMoneyAdapter;
    private int selectNumber;
    private TextView tvGetMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_getmoney);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("收款量");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        mListViewNewAdd = (ListView) findViewById(R.id.listviewGetMoney);

        View view = View.inflate(GetMoneyActivity.this, R.layout.item_text, null);
        mListViewNewAdd.addHeaderView(view);
        tvGetMoney = (TextView) view.findViewById(R.id.tvLowNotDone);

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
        btnDay.setOnClickListener(this);
        btnWeekDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);

        btnDay.setTextColor(Color.RED);
        getDataMoneyDay();

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

    @Override
    public void onClick(View v) {
        resetColor();
        switch (v.getId()){
            case R.id.btnDay:
                //切换到本日
                btnDay.setTextColor(Color.RED);
                getDataMoneyDay();

                break;
            case R.id.btnWeekDay:
                //切换到本周
                btnWeekDay.setTextColor(Color.RED);
                getDataMoneyWeek();
                break;
            case R.id.btnMonth:
                //切换到本月
                btnMonth.setTextColor(Color.RED);
                getDataMoneyMonth();
                break;
        }


    }

    //  //获取本月收款量
    private void getDataMoneyMonth() {
        mListViewDatas.clear();
        JSONObject mObjDatas = null;
        VolleyHelpApi.getInstance().getMoneyMonth( new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getMoneyMonth--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setContacts(JsonItem.getString("contactName"));
                        item.setComAddress(JsonItem.getString("countyName"));
                        item.setOrderId(JsonItem.getString("orderid"));
                        item.setTime(JsonItem.getString("placeOrderTime"));
                        item.setStyle(JsonItem.getString("orderName"));
                        item.setOrderMoney(JsonItem.getString("orderFee"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----内--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
                mGetMoneyAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
                mGetMoneyAdapter.notifyDataSetChanged();
            }
        });


    }

    //获取本周收款量
    private void getDataMoneyWeek() {
        mListViewDatas.clear();
        JSONObject mObjDatas = null;
        VolleyHelpApi.getInstance().getMoneyWeek(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getMoneyWeek--" + result.toString());
                JSONArray JsonAy = null;
                //{"countyName":"神湾镇","businezzType":"10","placeOrderTime":"2016-12-22 16:05:47","starttime":"1482393947062",
                // "contactName":"a你","orderName":"注册公司","ordContactId":1,"hasAccount":"N","companyId":29,"orderid":"12","companyName":"测试公司八","orderFee":"1"}{"countyName":"神湾镇","businezzType":"10","placeOrderTime":"2016-12-22 16:05:47","starttime":"1482393947062","contactName":"a你","orderName":"注册公司","ordContactId":1,
                // "hasAccount":"N","companyId":29,"orderid":"12","companyName":"测试公司八","orderFee":"1"}

                JSONObject object=(JSONObject) result;
                String code = object.optString("code");
                if (code.equals("0")){
                    LogHelper.i(TAG,"----code---"+code);
                    return;
                }

                try {
                    JsonAy = object.getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setContacts(JsonItem.getString("contactName"));
                        item.setComAddress(JsonItem.getString("countyName"));
                        item.setOrderId(JsonItem.getString("orderid"));
                        item.setTime(JsonItem.getString("placeOrderTime"));
                        item.setStyle(JsonItem.getString("orderName"));
                        item.setOrderMoney(JsonItem.getString("orderFee"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----内--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
                mGetMoneyAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
                mGetMoneyAdapter.notifyDataSetChanged();
            }
        });
    }
    private void resetColor() {
        btnDay.setTextColor(Color.WHITE);
        btnMonth.setTextColor(Color.WHITE);
        btnWeekDay.setTextColor(Color.WHITE);

    }

    //获取本天收款量
    private void getDataMoneyDay() {
        mListViewDatas.clear();
        JSONObject mObjDatas = null;
        VolleyHelpApi.getInstance().getMoneyDay( new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getNewAddCustomerDay--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
                        NewDataBean item=new NewDataBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        item.setComName(JsonItem.getString("companyName"));
                        item.setContacts(JsonItem.getString("contactName"));
                        item.setComAddress(JsonItem.getString("countyName"));
                        item.setOrderId(JsonItem.getString("orderid"));
                        item.setTime(JsonItem.getString("placeOrderTime"));
                        item.setStyle(JsonItem.getString("orderName"));
                        item.setOrderMoney(JsonItem.getString("orderFee"));
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-------" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
                mGetMoneyAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvGetMoney.setText(""+mListViewDatas.size());
            }
        });

        mGetMoneyAdapter = new GetMoneyAdapter(mListViewDatas,GetMoneyActivity.this);
        mListViewNewAdd.setAdapter(mGetMoneyAdapter);

    }
}
