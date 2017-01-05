package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.mode.MyCustomerAdapter;
import com.xht.android.managerhelp.mode.MyCustomerMode;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */
public class MyCustomerActivity extends Activity{

    private int uid;

    private static final String TAG = "MyCustomerActivity";
    private ListView myCustomerList;
    private PullRefreshLayout swipeRefreshLayout;
    private MyCustomerAdapter mMyCustomerAdapter;
    private List<MyCustomerMode> mListMyCustomer;
    private PullRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mycustomer);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("我的客户");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+ uid);
        initialize();




    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+ uid);
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

    private void initialize() {

        myCustomerList = (ListView) findViewById(R.id.myCustomerList);
        swipeRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                        //getTaskBarData();
                        // 刷新3秒完成
                        App.getInstance().showToast("刷新完成");

                    }
                }, 3000);
            }
        });

        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        myCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mMyCustomerAdapter = new MyCustomerAdapter(this);
        myCustomerList.setAdapter(mMyCustomerAdapter);

        getMyCuetomer();
        mListMyCustomer=new ArrayList<>();
        mMyCustomerAdapter.addList(mListMyCustomer);

    }

    private void getMyCuetomer() {

        VolleyHelpApi.getInstance().getDatasFromCustomer(new APIListener() {
            @Override
            public void onResult(Object result) {
              JSONArray jsonArray= (JSONArray) result;
                LogHelper.i(TAG,"-------------"+jsonArray.toString());


            }

            @Override
            public void onError(Object e) {

            }
        });

    }
}
