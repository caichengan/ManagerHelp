package com.xht.android.managerhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.managerhelp.fragment.OrderMapAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/13.
 */

public class OrderActivity extends Activity {
    private static final String TAG = "OrderActivity";
    private String orderId;
    private String orderMoney;
    private String orderStyle;
    private String companyId;
    private String businezzType;
    private ArrayList<String> listData;
    private ArrayList<String> listArray;
    private ListView listView;
    private OrderMapAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        /*
        bundle.putString("orderId",orderId);
        bundle.putString("orderMoney",orderMoney);
        bundle.putString("orderStyle",orderStyle);
        bundle.putString("companyId",companyId);*/
        TextView mCustomView = new TextView(this);
        mCustomView.setGravity(Gravity.CENTER);
        mCustomView.setText("订单信息");
        mCustomView.setTextColor(Color.BLACK);
        mCustomView.setTextSize(18);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int change = ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        orderId = bundle.getString("orderId");
        businezzType = bundle.getString("businezzType");//订单类型
        orderMoney = bundle.getString("orderMoney");
        orderStyle = bundle.getString("orderStyle");
        companyId = bundle.getString("companyId");
        LogHelper.i(TAG,"-----onCreate--"+orderId+orderMoney+orderStyle+companyId);
        listView = (ListView) findViewById(R.id.listView);
      /*  Map<String,String> map=new HashMap<String,String>();
        map.put("1", "张三");
        map.put("2", "李四");
        map.put("3", "王五");*/
        listData = new ArrayList<>();
        listArray = new ArrayList<>();
       /* for (Map.Entry<String, String> m : map.entrySet()) {
            System.out.println("key:"+m.getKey()+" value"+m.getValue());
            listData.add(m.getKey()+": "+m.getValue());
        }
        */
        getOrderDatas();
    }
    private void getOrderDatas() {
        LogHelper.i(TAG,"------------"+orderId+"------"+businezzType);
        VolleyHelpApi.getInstance().getOrderDetials(orderId, businezzType,new APIListener() {
            @Override
            public void onResult(Object result) {////?orderId=10&orderType=10
                LogHelper.i(TAG,"------onResult---"+result.toString());


                if (businezzType.equals("10")) {

                  JSONObject jsonObject= (JSONObject) result;
                    String entity = jsonObject.optString("entity");

                    Map<String, Object> map = getMap(entity);

                    for(Map.Entry<String,Object> m:map.entrySet()){
                        LogHelper.i(TAG,"--------key------:" + m.getKey() + " value" + m.getValue());
                        listData.add(m.getKey() + "" + ":  " + m.getValue() + "");

                    }

                    adapter = new OrderMapAdapter(OrderActivity.this, listData);
                    listView.setAdapter(adapter);



                    //{"抢单编号":8,"抢单电话":"18938723410","备选名称":"","支付单号":3,"镇区编号":1,"下单电话":"13531829360","镇区名称":"石岐区","支付状态":"已付款","下单人员":"有限公司","下单编号":1,"记账单号":1,"服务状态":"服务中","抢单时间":"2016-12-15 15:30:31","公司名称":"破天","抢单人员":"覃源源",
                    // "是否加急":"否","订单金额":"￥0.01","地址托管":"否","含有记账":"是","下单时间":"2016-12-14 10:12:24","订单编号":3}
                    // JSONObject jsonObject= ((JSONObject) result).optJSONObject("entity");
                    //  LogHelper.i(TAG,"------onResult---"+jsonObject.toString());
                }
                if (businezzType.equals("12")){//社保服务
                    /**
                     *  /**
                     * {"person":[{"身份证号":"dfvxscgggexcffsdv","人员姓名":"某某","购买/撤销":"撤销"},{"身份证号":"451278966547887758","人员姓名":"购买","购买/撤销":"购买"},
                     * {"身份证号":"78548857455878","人员姓名":"撤销","购买/撤销":"撤销"}],
                     * "order":{"支付状态":"已付款","下单人员":"韦继胜","OptType":null,"抢单电话":null,"服务状态":"预约中","抢单时间":null,"抢单人员":null,"公司名称":"韦继胜测试公司3","订单金额":"￥1","下单电话":"13531833516","下单时间":"2017-01-16 11:05:13"}}
                     */



                JSONObject object= (JSONObject) result;
                JSONObject obj = object.optJSONObject("entity");

                    String code = object.optString("code");

                    if (code.equals("0")) {
                        App.getInstance().showToast("服务器繁忙...");
                        finish();
                        return;
                    }

                    //JSONArray jsonArrayPerson = obj.optJSONArray("person");
                    String person = obj.optString("person");
                    String entity = obj.optString("order");
                    Map<String, Object> map = getMap(entity);
                    for(Map.Entry<String,Object> m:map.entrySet()){
                        LogHelper.i(TAG,"--------key------:" + m.getKey() + " value" + m.getValue());
                        listData.add(m.getKey() + "" + ":  " + m.getValue() + "");

                    }
                    LogHelper.i(TAG,"--------person---"+person);
                    try {
                        List<Map<String, Object>> mapList = getList(person);
                        int size = mapList.size();

                        for (int i = 0; i < size; i++) {
                            Map<String, Object> map1ist = mapList.get(i);
                            listData.add("");
                            listData.add("-----社保人员-----");
                            for (Map.Entry<String, Object> m : map1ist.entrySet()) {
                                LogHelper.i(TAG,"---key:---list---" + m.getKey() + " value" + m.getValue());
                                listData.add(m.getKey() + "" + ":  " + m.getValue() + "");

                            }

                        }
                        LogHelper.i(TAG,"--------size---"+size);

                        adapter = new OrderMapAdapter(OrderActivity.this, listData);
                        listView.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogHelper.i(TAG, "------JSONArray---" );
                }
            }
            @Override
            public void onError(Object e) {
            }
        });
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
     * 把json 转换为ArrayList 形式
     * @return
     */
    public static List<Map<String, Object>> getList(String jsonString)
    {
        List<Map<String, Object>> list = null;
        try
        {
            JSONArray jsonArray = new JSONArray(jsonString);
            org.json.JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, Object> getMap(String jsonString)
    {
        org.json.JSONObject jsonObject;
        try
        {
            jsonObject = new org.json.JSONObject(jsonString); @SuppressWarnings("unchecked")
        Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext())
            {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
