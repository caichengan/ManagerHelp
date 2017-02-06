package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.managerhelp.mode.OrderPictAdapter;
import com.xht.android.managerhelp.mode.OrderPictBean;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.AsyncImageLoader;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */

public class OrderPictureActivity extends Activity {
    private static final String TAG = "OrderPictureActivity";
    private String orderId;
    private ListView listviewPic;
    private AsyncImageLoader asyncImageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pictureshow);
        listviewPic = (ListView) findViewById(R.id.listViewPic);

        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("查看订单图片");
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        orderId = bundle.getString("orderId");
        LogHelper.i(TAG,"------orderId-----"+ orderId);

        if (!TextUtils.isEmpty(orderId)){
            getPictureShow();
        }
    }
    /**
     * 获取订单图片
     */
    private void getPictureShow() {

        VolleyHelpApi.getInstance().getPictureDatas(orderId, new APIListener() {
            @Override
            public void onResult(Object result) {
//{"process_file":[{"fileId":45,"checkStatus":"审核通过","upTime":"2016-1222 16:07:11",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482394031826_bzzbz_o12_s18_e2_f1_t1482394019881_g.jpg",
// "employeeName":"安仔"},{"fileId":47,"checkStatus":"审核通过","upTime":"2016-1222 16:36:22",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482395782772_bzzbz_o12_s18_e2_f1_t1482395758950_g.jpg",
// "employeeName":"安仔"},{"fileId":48,"checkStatus":"审核通过","upTime":"2016-1222 16:36:28",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482395788446_bzzbz_o12_s18_e2_f1_t1482395764263_g.jpg",
// "employeeName":"安仔"}],"flowName":"资料交接","result_file":[{"fileId":46,"checkStatus":"审核通过",
// "upTime":"2016-1222 16:17:56",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482394676733_bzzbz_o12_s18_e2_f1_t1482394613018_j.jpg",
// "employeeName":"安仔"},{"fileId":49,"checkStatus":"审核通过","upTime":"2016-1222 16:36:48",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482395808873_bzzbz_o12_s18_e2_f1_t1482395810731_j.jpg",
// "employeeName":"安仔"},{"fileId":50,"checkStatus":"审核通过","upTime":"2016-1222 16:36:56",
// "file":"http://www.xiaohoutai.com.cn:8888/XHT/servicefileController/downLoadServiceFile?fileName=1482395816223_bzzbz_o12_s18_e2_f1_t1482395813908_j.jpg",
// "employeeName":"安仔"}]}
                try {
                    analyize(result);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void onError(Object e) {
            }
        });
    }
    /**
     * 解析数据JSON
     * @param result
     */
    private void analyize(Object result) throws JSONException {

        JSONObject JSON= (JSONObject) result;
        JSONArray jsonArray = JSON.optJSONArray("entity");
        List<OrderPictBean> mListEntity=new ArrayList();
        String code = JSON.optString("code");
        if (code.equals("0")){
            App.getInstance().showToast("暂无数据");
            return;
        }
        LogHelper.i(TAG,"-----length--"+ jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            OrderPictBean orderBean=new OrderPictBean();
            List<OrderPictBean.ProcessFileBean> mListProcessFile=new ArrayList<>();
            List<OrderPictBean.ResultFileBean> mListResultFile=new ArrayList<>();
            JSONObject jsonObject= (JSONObject) jsonArray.get(i);
            JSONArray process_fileArray = jsonObject.optJSONArray("process_file");
            JSONArray result_fileArray = jsonObject.optJSONArray("result_file");
            if (process_fileArray.length()>0) {
                for (int j = 0; j < process_fileArray.length(); j++) {
                    JSONObject jsonProgress = (JSONObject) process_fileArray.get(j);
                    String fileId = jsonProgress.optString("fileId");
                    String checkStatus = jsonProgress.optString("checkStatus");
                    String upTime = jsonProgress.optString("upTime");
                    String file = jsonProgress.optString("file");
                    String employeeName = jsonProgress.optString("employeeName");

                    LogHelper.i(TAG, "----j--process_fileArray.length()----" + j + "-------" + process_fileArray.length());
                    LogHelper.i(TAG, "-----jsonProgress--" + file);
                    OrderPictBean.ProcessFileBean proBean = new OrderPictBean.ProcessFileBean();
                    proBean.setFileId(fileId);
                    proBean.setCheckStatus(checkStatus);
                    proBean.setUpTime(upTime);
                    proBean.setFile(file);
                    proBean.setEmployeeName(employeeName);
                    mListProcessFile.add(proBean);

                }
            }
            orderBean.setProcess_file(mListProcessFile);

            if (result_fileArray.length()>0) {
                for (int k = 0; k < result_fileArray.length(); k++) {
                    JSONObject jsonResult = (JSONObject) result_fileArray.get(k);
                    String fileId = jsonResult.optString("fileId");
                    String checkStatus = jsonResult.optString("checkStatus");
                    String upTime = jsonResult.optString("upTime");
                    String file = jsonResult.optString("file");
                    String employeeName = jsonResult.optString("employeeName");
                LogHelper.i(TAG,"----j--result_fileArray.length()----"+k+"-------"+result_fileArray.length());
                LogHelper.i(TAG,"-----jsonResult--"+ file);
                    OrderPictBean.ResultFileBean resBean = new OrderPictBean.ResultFileBean();
                    resBean.setFileId(fileId);
                    resBean.setCheckStatus(checkStatus);
                    resBean.setUpTime(upTime);
                    resBean.setFile(file);
                    resBean.setEmployeeName(employeeName);

                    mListResultFile.add(resBean);

                }
            }

            orderBean.setResult_file(mListResultFile);


            String flowName = jsonObject.optString("flowName");

            orderBean.setFlowName(flowName);

            mListEntity.add(orderBean);
        }
        //"entity":[{"process_file":[{"fileId":73,"checkStatus":"审核通过","upTime":"2017-0104 11:30:34","file":"http:\/\/www.xiaohoutai.com.cn:8888\/XHT\/servicefileController\/downLoadServiceFile?fileName=1483500634108_bzzbz_o3_s4_e8_f1_t1483500631936_g.jpg","employeeName":"覃源源"}],
        // "flowName":"资料交接",
        // "result_file":[{"fileId":74,"checkStatus":"审核通过","upTime":"2017-0104 11:30:50","file":"http:\/\/www.xiaohoutai.com.cn:8888\/XHT\/servicefileController\/downLoadServiceFile?fileName=1483500650236_bzzbz_o3_s4_e8_f1_t1483500647887_j.jpg","employeeName":"覃源源"}]}],"code":"1"}




        LogHelper.i(TAG,"-----------------------------");

        OrderPictAdapter adapter=new OrderPictAdapter(this,mListEntity,listviewPic);

        listviewPic.setAdapter(adapter);



        LogHelper.i(TAG,"----------22222222----------");

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
