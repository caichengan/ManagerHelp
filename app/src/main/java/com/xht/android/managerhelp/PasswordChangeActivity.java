package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/19.
 */
public class PasswordChangeActivity extends Activity{

    private EditText editOldChange;
    private EditText editNewChange;
    private Button btnSure;
    private int uid;
    private String phoneNum;
    private String userName;
    private String newMiMa;
    private String oldMiMa;
    private ProgressDialog mProgDoal;

    private static final String TAG = "PasswordChangeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passord);
        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("修改密码");
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

        editOldChange = (EditText) findViewById(R.id.editOldChange);
        editNewChange = (EditText) findViewById(R.id.editNewChange);
        btnSure = (Button) findViewById(R.id.btnSure);
        btnSure.setOnClickListener(new View.OnClickListener() {
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

        oldMiMa = editOldChange.getText().toString();
        newMiMa = editNewChange.getText().toString();


        if (TextUtils.isEmpty(oldMiMa)){
            App.getInstance().showToast("请输入原密码");
            return;
        }

        if (TextUtils.isEmpty(newMiMa)){
            App.getInstance().showToast("请输入新密码");
            return;
        }

        try {
            obj.put("empId",uid);
            obj.put("oPw",oldMiMa);
            obj.put("nPw", newMiMa);
            obj.put("tel", phoneNum);


            LogHelper.i(TAG,"----obj---"+obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        createProgressDialog("正在修改密码");
        VolleyHelpApi.getInstance().postMiMaData(obj, new APIListener(){

            @Override
            public void onResult(Object result) {

                //用户姓名写入数据库
                dismissProgressDialog();

                LogHelper.i(TAG,"-------密码修改成功---");

                App.getInstance().showToast("密码修改成功");
                PasswordChangeActivity.this.finish();


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
