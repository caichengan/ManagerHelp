package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.util.LogHelper;


/**
 * Created by Administrator on 2016/12/16.
 */

public class LowWarningActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LowWarningActivity";
    private Button btnYesManage;
    private Button btnNoManage;
    private FrameLayout warning;


    private NoManagerFragment mNoFragment;
    private YesManagerFragment mYesFragment;
    public static int selectFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowwarning);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("低分预警");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        btnYesManage = (Button) findViewById(R.id.btnYesManage);
        btnNoManage = (Button) findViewById(R.id.btnNoManage);
        warning = (FrameLayout) findViewById(R.id.warning);
        btnYesManage.setOnClickListener(this);
        btnNoManage.setOnClickListener(this);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        int uid = bundle.getInt("uid", -1);
        LogHelper.i(TAG,"----uid----"+uid);
        setSelect(0);
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
     * 选择字体的颜色和显示选中的Fragment
     * @param i
     */
    private void setSelect(int i) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                if (mNoFragment==null){
                    mNoFragment=new NoManagerFragment();
                    transaction.add(R.id.warning,mNoFragment);
                }else {
                    transaction.show(mNoFragment);
                }
                btnNoManage.setTextColor(Color.RED);
                break;
            case 1:
                if (mYesFragment==null){
                    mYesFragment=new YesManagerFragment();
                    transaction.add(R.id.warning,mYesFragment);
                }else {
                    transaction.show(mYesFragment);
                }
                btnYesManage.setTextColor(Color.RED);
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mNoFragment!=null){
            transaction.hide(mNoFragment);
        }
        if (mYesFragment!=null){
            transaction.hide(mYesFragment);
        }
    }
    //重置TAB的字体颜色（也可以变化图片）
    private void resetImg() {
        btnNoManage.setTextColor(Color.WHITE);
        btnYesManage.setTextColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        resetImg();



        switch (v.getId()){
            case R.id.btnNoManage:
                selectFragment = 0;
                break;
            case R.id.btnYesManage:
                selectFragment = 1;
                break;
        }
        setSelect(selectFragment);
    }



}
