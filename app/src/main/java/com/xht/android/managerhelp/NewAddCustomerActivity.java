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
import com.xht.android.managerhelp.mode.NewAddCustomerAdapter;
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
public class NewAddCustomerActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "NewAddCustomerActivity";
    private ListView mListViewNewAdd;
    private NewAddCustomerAdapter addCustomerAdapter;
    private List<NewDataBean> mListViewDatas;

    private Button btnDay;
    private Button btnWeekDay;
    private Button btnMonth;
    private int selectNumber;
    private TextView tvNewCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newcustomer);
        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("新增订单量");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        mListViewNewAdd = (ListView) findViewById(R.id.listviewNewAdd);

        View view = View.inflate(NewAddCustomerActivity.this, R.layout.item_text, null);
        mListViewNewAdd.addHeaderView(view);
        tvNewCustomer = (TextView) view.findViewById(R.id.tvLowNotDone);

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

        mListViewDatas=new ArrayList<>();

        btnDay.setTextColor(Color.RED);
        getNewAddCustomerDay();



        btnDay.setOnClickListener(this);
        btnWeekDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);

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
                getNewAddCustomerDay();
                break;
            case R.id.btnWeekDay:
                //切换到本周
                btnWeekDay.setTextColor(Color.RED);
                getNewAddCustomerWeek();
                break;

            case R.id.btnMonth:
                //切换到本月
                 btnMonth.setTextColor(Color.RED);

                getNewAddCustomerMonth();
                break;
        }



    }

    //访问本月数据
    private void getNewAddCustomerMonth() {
        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getNewAddCustomerMonth(new APIListener() {

            //{"countyName":"沙溪镇","businezzType":"10","placeOrderTime":"2016-12-15 19:22:26","starttime":"1481800946081","contactName":"彭翠玉","orderName":"注册公司","ordContactId":4,
            // "hasAccount":"Y","companyId":21,"orderid":"9","companyName":"中山市美芯环保科技有限公司","orderFee":"2"}

            @Override
            public void onResult(Object result) {
                App.getInstance().showToast("加载本月数据成功");
                LogHelper.i(TAG, "-----getNewAddCustomerMonth--" + result.toString());
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
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----客户通讯录--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                addCustomerAdapter.notifyDataSetChanged();


            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                addCustomerAdapter.notifyDataSetChanged();
            }
        });





    }


    //访问本周数据
    private void getNewAddCustomerWeek() {

        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getNewAddCustomerWeek(new APIListener() {
            //"entity":[{"telephone":"13652365101","employeeName":"祝飞"},{"telephone":"13652365101","employeeName":"ZHUFEI"}
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----getNewAddCustomerWeek--" + result.toString());
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
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-----内部通讯录--" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                addCustomerAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                addCustomerAdapter.notifyDataSetChanged();
            }
        });


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

    private void getNewAddCustomerDay() {
        mListViewDatas.clear();
        VolleyHelpApi.getInstance().getNewAddCustomerDay(new APIListener() {
            //"entity":[{"telephone":"13652365101","employeeName":"祝飞"},{"telephone":"13652365101","employeeName":"ZHUFEI"}
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
                        mListViewDatas.add(item);
                        LogHelper.i(TAG, "-------" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                addCustomerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Object e) {
                int size = mListViewDatas.size();
                LogHelper.i(TAG,"----size-"+size);
                tvNewCustomer.setText(""+mListViewDatas.size());
                App.getInstance().showToast(e.toString());
            }
        });
        addCustomerAdapter = new NewAddCustomerAdapter(mListViewDatas,NewAddCustomerActivity.this);
        mListViewNewAdd.setAdapter(addCustomerAdapter);

    }

}
