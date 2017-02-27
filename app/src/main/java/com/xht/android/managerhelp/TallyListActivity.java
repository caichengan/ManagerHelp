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
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.mode.TallyMode;
import com.xht.android.managerhelp.mode.TallyingAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;
import com.xht.android.managerhelp.view.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
public class TallyListActivity extends Activity{

    private ClearEditText mClientEdit;
    private ListView listtally;
    private PullRefreshLayout swipeRefreshLayout;
    private static final String TAG = "TallyListActivity";
    private int mUId;
    private List<TallyMode> tallylistDatas;
    private TallyingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tallying);
        initialize();


        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUId = bundle.getInt("uid", -1);
        LogHelper.i(TAG, "------CertificateListActivity-" + mUId);


        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("记账列表");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);


        getTallyDatas();


    }

    /**
     * 获取记账中的公司
     */
    private void getTallyDatas() {

        VolleyHelpApi.getInstance().getTallyListCompany(mUId+"", new APIListener() {
            @Override
            public void onResult(Object result) {

//{"companyName":"破天","countyName":"东区","employeeName":"韦继胜客服帐号","companyId":14}

             JSONObject object= (JSONObject) result;
                JSONArray jsonArray = object.optJSONArray("entity");


                LogHelper.i(TAG,"-------"+jsonArray.toString());

                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject iJSON= (JSONObject) jsonArray.get(i);
                        TallyMode item=new TallyMode();
                        item.setmCompany(iJSON.optString("companyName"));
                        item.setmCompanyId(iJSON.optString("companyId"));
                        item.setmAddress(iJSON.optString("countyName"));
                        item.setmAccountName(iJSON.optString("employeeName"));

                        tallylistDatas.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                adapter = new TallyingAdapter(TallyListActivity.this,tallylistDatas);
                listtally.setAdapter(adapter);
            }

            @Override
            public void onError(Object e) {

            }
        });

    }

    private void initialize() {
        tallylistDatas = new ArrayList<>();

        mClientEdit = (ClearEditText) findViewById(R.id.mClientEdit);
        listtally = (ListView) findViewById(R.id.list_tally);
        swipeRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TallyListActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });

        listtally.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String company = tallylistDatas.get(position).getmCompany();
                String companyId = tallylistDatas.get(position).getmCompanyId();
                String orderId = tallylistDatas.get(position).getmOrderId();
                String address = tallylistDatas.get(position).getmAddress();

                Bundle bundle=new Bundle();
                bundle.putString("companyName",company);
                bundle.putString("companyId",companyId);
                bundle.putString("orderId",orderId);
                bundle.putString("countyName",address);
                IntentUtils.startActivityNumber(TallyListActivity.this,bundle,MyCustomerDetialActivity.class);
            }
        });
    }
    /**
     * 转换时间
     *
     * @param time
     * @return
     */
    public String getDateTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
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
}