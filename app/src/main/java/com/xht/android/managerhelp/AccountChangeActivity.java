package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xht.android.managerhelp.fragment.MyFragment;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.provider.MyDatabaseManager;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/19.
 */
public class AccountChangeActivity extends Activity{

    private EditText editNewName;
    private EditText editNewPhone;
    private EditText editMiMa;
    private Button ntnSure;
    private int uid;
    private String phoneNum;
    private String userName;

    private static final String TAG = "AccountChangeActivity";
    private String newName;
    private String newTel;
    private ProgressDialog  mProgDoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("修改账户");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        uid = bundle.getInt("uid", -1);
        phoneNum = bundle.getString("phoneNum");
        userName = bundle.getString("userName");
        initialize();
    }

    private void initialize() {

        editNewName = (EditText) findViewById(R.id.editNewName);
        editNewPhone = (EditText) findViewById(R.id.editNewPhone);
        editMiMa = (EditText) findViewById(R.id.editMiMa);
        ntnSure = (Button) findViewById(R.id.ntnSure);

        editNewName.setText(userName);
        editNewPhone.setText(phoneNum);

        ntnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeSureMethod();


            }
        });
    }

    private void MakeSureMethod() {
        //提交确认
        JSONObject obj=new JSONObject();
        if (uid==-1){
            App.getInstance().showToast("服务器放繁忙，请稍后再试...");
            return;
        }

        String mima = editMiMa.getText().toString();
        newName = editNewName.getText().toString();
        newTel = editNewPhone.getText().toString();


        if (TextUtils.isEmpty(mima)){
            App.getInstance().showToast("请输入密码");
            return;
        }

        if (TextUtils.isEmpty(newName)){
            App.getInstance().showToast("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(newTel)){
            App.getInstance().showToast("电话号码不能为空");
            return;
        }
        try {
            obj.put("empId",uid);
            obj.put("pw",mima);
            obj.put("name", newName);
            obj.put("tel", newTel);


            LogHelper.i(TAG,"----obj---"+obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        createProgressDialog("正在修改账户");
        VolleyHelpApi.getInstance().postManagerData(obj, new APIListener(){

            @Override
            public void onResult(Object result) {

                //用户姓名写入数据库
                dismissProgressDialog();

                LogHelper.i(TAG,"-------修改账户---");
                //用户姓名写入数据库
                ContentValues cv = new ContentValues();
                if (newName!=null) {
                    cv.put(MyDatabaseManager.MyDbColumns.NAME, newName);
                }
                if (newTel!=null) {
                    cv.put(MyDatabaseManager.MyDbColumns.PHONE, newTel);
                }

                LogHelper.i(TAG,"-------MyDatabaseManager---");
                String where = MyDatabaseManager.MyDbColumns.UID + " = ?";
                String[] selectionArgs = {String.valueOf(uid)};
                AccountChangeActivity.this.getContentResolver().update(MyDatabaseManager.MyDbColumns.CONTENT_URI, cv, where, selectionArgs);
                //数据有更新，更新一下内存的用户变量
                Intent intent = new Intent(MyFragment.BRO_ACT_S);
                intent.putExtra(MyFragment.UID_KEY, uid);
                if (newTel!=null) {
                    intent.putExtra(MyFragment.PHONENUM_KEY, Long.parseLong(newTel));
                }
                if (newName!=null) {
                    intent.putExtra(MyFragment.UNAME_KEY, newName);
                }
                sendBroadcast(intent);
                App.getInstance().showToast("账户修改成功");
                AccountChangeActivity.this.finish();


            }

            @Override
            public void onError(Object e) {
                dismissProgressDialog();
                App.getInstance().showToast(e.toString());

            }
        });
    }

    /**
     * 创建对话框
     *
     * @param title
     */
    private void createProgressDialog(String title) {
        if (mProgDoal == null) {
            mProgDoal = new ProgressDialog(this);
        }
        mProgDoal.setTitle(title);
        mProgDoal.setIndeterminate(true);
        mProgDoal.setCancelable(false);
        mProgDoal.show();
    }
    /**
     * 隐藏对话框
     */
    private void dismissProgressDialog() {
        if (mProgDoal != null) {
            mProgDoal.dismiss();
            mProgDoal = null;
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
