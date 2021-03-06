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
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;
import com.xht.android.managerhelp.view.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 *
 * 我的过期客户页面
 */
public class OutCustomerActivity extends Activity{

    private int uid;

    private static final String TAG = "OutCustomerActivity";
    private ListView myCustomerList;
    private PullRefreshLayout swipeRefreshLayout;
    private MyCustomerAdapter mMyCustomerAdapter;
    private PullRefreshLayout layout;
    private List<MyCustomerMode> companyList;
    private ClearEditText clearCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_outcustomer);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("到期合同");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+ uid);
        companyList = new ArrayList<MyCustomerMode>();

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
        clearCustomer = (ClearEditText) findViewById(R.id.mClearCustomer);

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

        myCustomerList.setFastScrollEnabled(true);

        myCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyCustomerMode myCustomerMode = companyList.get(position);
                String companyName = myCustomerMode.getCompanyName();
                String companyId = myCustomerMode.getCompanyId();
                Bundle bundle=new Bundle();
                bundle.putString("companyName",companyName);
                bundle.putString("companyId",companyId);
                IntentUtils.startActivityNumber(OutCustomerActivity.this,bundle,MyCustomerDetialActivity.class);

            }
        });

        getMyOutCuetomer();
    }

    /**
     * 获取所有的过期客户信息
     */
    private void getMyOutCuetomer() {

        VolleyHelpApi.getInstance().getOutFromCustomer(new APIListener() {
            @Override
            public void onResult(Object result) {
                JSONArray jsonArray= null;


                try {
                    jsonArray = ((JSONObject) result).getJSONArray("entity");
        //{"companyName":"安测试公司一","companyId":12},{"companyName":"中山市小后台会计服务有限公司","companyId":13}
                    LogHelper.i(TAG,"--------jsonArray-----"+jsonArray.toString());
                    int length = jsonArray.length();

                    for (int i=0;i<length;i++){
                        MyCustomerMode item=new MyCustomerMode();
                        JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                        item.setCompanyName(jsonObject.optString("companyName"));
                        item.setCompanyId(jsonObject.optString("companyId"));
                        item.setDataOut(jsonObject.optString("dataOut"));
                        companyList.add(item);

                    }
                    mMyCustomerAdapter = new MyCustomerAdapter(OutCustomerActivity.this,companyList);
                    myCustomerList.setAdapter(mMyCustomerAdapter);
                    LogHelper.i(TAG,"--------jsonArray-----"+jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Object e) {

            }
        });

    }
}
