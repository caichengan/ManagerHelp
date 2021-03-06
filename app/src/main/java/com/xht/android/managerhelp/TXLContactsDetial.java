package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.util.LogHelper;

/**
 * Created by Administrator on 2016-11-1.
 */
public class TXLContactsDetial extends Activity implements View.OnClickListener {

    private String name;
    private String phoneNum;
    private static final String TAG = "TXLContactsDetial";
    private ImageView head;
    private TextView mCallName;
    private ImageView message;
    private TextView mCallPhone;
    private ImageView call;
    private RelativeLayout relayoutcall;
    private String mId;
    private String mComName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactsdetial);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        name = bundle.getString("mContactName");
        phoneNum = bundle.getString("mPhone");//  bundle.putString("mId",mId);
        mId = bundle.getString("mId");
        mComName = bundle.getString("mComName");
        LogHelper.i(TAG,"----"+name+phoneNum+mId);
        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("返回");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        initialize();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {
        //mDataList = new ArrayList<ContactsBean>();
        if (name==null||name==""||name.equals("null")){
         mCallName.setText("");
        }else {
            mCallName.setText(name);
        }
        mCallPhone.setText(phoneNum);
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
        switch(v.getId()){
            case R.id.message:
                //发送信息
                sendMessage();
                break;
            case R.id.call:

            case R.id.relayout_call:
                //拨打电话
                callPhone();
                break;
        }
    }
    //拨打电话
    private void callPhone() {
        LogHelper.i(TAG,"----打电话");
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    //发送消息
    private void sendMessage() {
        LogHelper.i(TAG,"----发消息");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("address", phoneNum);
            intent.putExtra("sms_body", "");
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
    }
    private void initialize() {
        head = (ImageView) findViewById(R.id.head);
        mCallName = (TextView) findViewById(R.id.mCallName);//姓名
        message = (ImageView) findViewById(R.id.message);//发送消息
        mCallPhone = (TextView) findViewById(R.id.mCallPhone);//电话号码
        call = (ImageView) findViewById(R.id.call);//点击打电话
        relayoutcall = (RelativeLayout) findViewById(R.id.relayout_call);//点击打电话

        relayoutcall.setOnClickListener(this);
        call.setOnClickListener(this);
        message.setOnClickListener(this);

        initData();

    }


}
