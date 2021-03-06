package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
 * 我的客户页面
 */
public class MyCustomerActivity extends Activity{

    private int uid;

    private static final String TAG = "MyCustomerActivity";
    private ListView myCustomerList;
    private ClearEditText mClearCustomer;
    private PullRefreshLayout swipeRefreshLayout;
    private MyCustomerAdapter mMyCustomerAdapter;
    private PullRefreshLayout layout;
    private List<MyCustomerMode> companyList;
    private List<MyCustomerMode> searchCompanyList;

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
        companyList = new ArrayList<MyCustomerMode>();
        searchCompanyList=new ArrayList<>();
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
        mClearCustomer = (ClearEditText) findViewById(R.id.mClearCustomer);
        swipeRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        myCustomerList.setFastScrollEnabled(true);
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
                        getMyCuetomer();
                        App.getInstance().showToast("刷新完成");


                    }
                }, 3000);
            }
        });
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        mClearCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String mNewCustomString = mClearCustomer.getText().toString();

                if (!TextUtils.isEmpty(mNewCustomString)){

                    JSONObject object=new JSONObject();

                    try {
                        object.put("name",mNewCustomString);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    LogHelper.i(TAG,"----"+object);
                    for (int i = 0; i < companyList.size(); i++) {
                        boolean contains = companyList.get(i).getCompanyName().contains(mNewCustomString);
                        if (contains){
                            searchCompanyList.add(companyList.get(i));
                        }
                    }
                    mMyCustomerAdapter = new MyCustomerAdapter(MyCustomerActivity.this,searchCompanyList);
                    myCustomerList.setAdapter(mMyCustomerAdapter);
                }else{
                    getMyCuetomer();
                }
            }
        });

        myCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyCustomerMode myCustomerMode = companyList.get(position);
                String companyName = myCustomerMode.getCompanyName();
                String companyId = myCustomerMode.getCompanyId();
                String countyName = myCustomerMode.getCountyName();
                Bundle bundle=new Bundle();
                bundle.putString("companyName",companyName);
                bundle.putString("companyId",companyId);
                bundle.putString("countyName",countyName);
                IntentUtils.startActivityNumber(MyCustomerActivity.this,bundle,MyCustomerDetialActivity.class);

            }
        });

        getMyCuetomer();
    }



    /**
     * 获取所有的客户信息
     */
    private void getMyCuetomer() {

        companyList.clear();
        searchCompanyList.clear();
        VolleyHelpApi.getInstance().getDatasFromCustomer(new APIListener() {
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
                        item.setCountyName(jsonObject.optString("countyName"));
                        companyList.add(item);

                    }
                    mMyCustomerAdapter = new MyCustomerAdapter(MyCustomerActivity.this,companyList);
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
