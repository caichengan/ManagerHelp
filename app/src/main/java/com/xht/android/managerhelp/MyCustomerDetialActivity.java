package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;
import com.xht.android.managerhelp.fragment.CustomerContactsFragment;
import com.xht.android.managerhelp.fragment.DeclareTaxFragment;
import com.xht.android.managerhelp.fragment.DetailFragment;
import com.xht.android.managerhelp.fragment.FollowFragment;
import com.xht.android.managerhelp.fragment.OrderFragment;
import com.xht.android.managerhelp.fragment.PictureFragment;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.BitmapUtils;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的客户详细信息
 * @author czw 2016-10-13
 */
public class MyCustomerDetialActivity extends FragmentActivity {
    private static final String TAG = "MyCustomerDetialActivity";
    private android.support.v4.app.Fragment mFragment1, mFragment2, mFragment3,mFragment4,mFragment5,mFragment6;

    private static final String[] CONTENT = new String[] { "订单列表", "跟进记录", "详细信息", "图片", "报税记录", "联系人"};
    //private static final String[] CONTENT = new String[] {"跟进记录", "详细信息", "图片", "报税记录", "联系人"};
    private SharedPreferences mSHaredPreference;
    private String companyName;
    public String phone;
    private  ProgressDialog mProgDoal;
    private List<android.support.v4.app.Fragment> mListFragment;
    private String orderId;
    private String companyId;
    private String customerName;
    private LinearLayout mLLayout;

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial_customer);
        setTheme(com.viewpagerindicator.R.style.Theme_PageIndicatorDefaults);
        //setTheme(R.style.MyTextAppearance_TabPageIndicator);

       /* bundle.putString("companyName",companyName);
        bundle.putString("customerName",customerName);
        bundle.putString("orderId",orderId);*/

        Bundle bundle = getIntent().getBundleExtra("bundle");
        companyName = bundle.getString("companyName");
        companyId = bundle.getString("companyId");

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText(companyName+"");
        mCustomView.setTextColor(Color.BLACK);
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        mListFragment = new ArrayList<>();
        mFragment1= OrderFragment.newInstance(""+companyId,""+companyName);
        mFragment2= FollowFragment.newInstance(""+companyId,""+companyName);
        mFragment3= DetailFragment.newInstance(""+companyId,""+companyName);
        mFragment4= PictureFragment.newInstance(""+companyId,""+companyName);
        mFragment5= DeclareTaxFragment.newInstance(""+companyId,""+companyName);
        mFragment6= CustomerContactsFragment.newInstance(""+companyId,""+companyName);


        mListFragment.add(mFragment1);
        mListFragment.add(mFragment2);
        mListFragment.add(mFragment3);
        mListFragment.add(mFragment4);
        mListFragment.add(mFragment5);
        mListFragment.add(mFragment6);

        findView();

    }
    private void findView() {
        FragmentPagerAdapter adapter = new NewsAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.framelayout);
        pager.setAdapter(adapter);

        mLLayout = (LinearLayout) findViewById(R.id.containLLayout);

        final TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setBackgroundColor(R.drawable.btn_background_circle);
       // android:background="@drawable/btn_background_circle"
        indicator.setViewPager(pager);


        loadDataPs();//TODO 客户头像和姓名

     /*   final TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);*/

     /*   final float density = getResources().getDisplayMetrics().density;
        indicator.setBackgroundColor(0x18FF0000);
        indicator.setFooterColor(0xFFAA2222);
        indicator.setFooterLineHeight(1 * density); //1dp
        indicator.setFooterIndicatorHeight(3 * density); //3dp
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Underline);
        indicator.setTextColor(0x00000);
        indicator.setSelectedColor(0x000000);
        indicator.setSelectedBold(true);*/

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                indicator.setCurrentItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            return mListFragment.get(position);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }
        @Override
        public int getCount() {
            return mListFragment.size();
        }
    }

    /**
     * 办证中获取成员数据
     */
    private void loadDataPs() {
        VolleyHelpApi.getInstance().getDataBZChengYuan(orderId, new APIListener() {
            @Override
            public void onResult(Object result) {

                JSONArray jsonObj = ((JSONObject) result).optJSONArray("entity");
               //[{"employeeId":2,"headportrait":null,"employeeName":"安仔"}]
                LogHelper.i(TAG,"------result----"+result);
                for (int i=0;i<jsonObj.length();i++) {
                    try {
                       JSONObject obj= (JSONObject) jsonObj.get(i);
                        String headportrait = obj.optString("headportrait");
                        String employeeName = obj.optString("employeeName");
                        LogHelper.i(TAG,"-----headportrait-"+headportrait);

                        View view = View.inflate(MyCustomerDetialActivity.this,R.layout.item_bz_chengyuan,null);//TODO 添加头像
                        TextView textName = (TextView) view.findViewById(R.id.chengyuanName);
                        ImageView textImg = (ImageView) view.findViewById(R.id.touxiang);

                        textName.setText(employeeName);
                        BitmapUtils.loadImgageUrl(headportrait,textImg);

                        //TODO 加到滑动标签 中去
                        mLLayout.addView(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
            }
        });
    }

    public void createProgressDialog(String title) {
        if (mProgDoal == null) {
            mProgDoal = new ProgressDialog(this);
        }
        mProgDoal.setTitle(title);
        mProgDoal.setIndeterminate(true);
        mProgDoal.setCancelable(false);
        mProgDoal.show();
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

    /**
     * 隐藏mProgressDialog
     */
    public void dismissProgressDialog()
    {
        if(mProgDoal != null)
        {
            mProgDoal.dismiss();
            mProgDoal = null;
        }
    }


}
