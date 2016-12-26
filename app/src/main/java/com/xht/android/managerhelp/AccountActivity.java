package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.mode.UserInfo;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;

/**
 * Created by Administrator on 2016/12/19.
 */
public class AccountActivity extends Activity implements View.OnClickListener {

    private LinearLayout tvAccountChange;
    private LinearLayout tvMiMaChange;
    private MainActivity mMainActivity;
    private UserInfo mUseInfo;

    private static final String TAG = "AccountActivity";
    private int uid;
    private long phoneNum;
    private String userName;
    private ProgressDialog mProgDoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountmanager);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("修改账户");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        initialize();
        mUseInfo = MainActivity.getInstance();
        uid = mUseInfo.getUid();
        phoneNum = mUseInfo.getPhoneNum();
        userName = mUseInfo.getUserName();
        LogHelper.i(TAG,"-----onCreate---"+ uid + phoneNum + userName);
    }
    @Override
    protected void onResume() {
        super.onResume();
        phoneNum = mUseInfo.getPhoneNum();
        userName = mUseInfo.getUserName();

    }
    private void initialize() {
        tvAccountChange = (LinearLayout) findViewById(R.id.tvAccountChange);
        tvMiMaChange = (LinearLayout) findViewById(R.id.tvMiMaChange);
        tvAccountChange.setOnClickListener(this);
        tvMiMaChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putInt("uid",uid);
        bundle.putString("phoneNum",phoneNum+"");
        bundle.putString("userName",userName+"");
        switch (v.getId()){
            case R.id.tvAccountChange:


                IntentUtils.startActivityNumber(this,bundle,AccountChangeActivity.class);
                break;

            case R.id.tvMiMaChange:
                IntentUtils.startActivityNumber(this,bundle,PasswordChangeActivity.class);
                break;

        }
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
