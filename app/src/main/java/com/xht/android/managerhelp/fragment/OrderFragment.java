package com.xht.android.managerhelp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.OrderActivity;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.mode.CustomerOrderMode;
import com.xht.android.managerhelp.mode.OrderAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 * 我的客户中的订单信息
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "OrderFragment";
    // TODO: Rename and change types of parameters
    private String companyId;
    private String mParam2;
    private ListView orderListView;
    private List<CustomerOrderMode> mListOrderDatas;
    private OrderAdapter adapter;
    private PullRefreshLayout swipeRefreshLayout;

    public OrderFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenJinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        LogHelper.i(TAG,"------companyId--"+companyId);

        mListOrderDatas = new ArrayList<>();
    }

    /**
     * 从网上获取数据
     */
    private void getOrderDatas() {
        mListOrderDatas.clear();
        LogHelper.i(TAG,"-----getOrderDatas---");

        VolleyHelpApi.getInstance().getOrderListDatas(companyId, new APIListener() {
            @Override
            public void onResult(Object result) {
                //{"message":"没有订单","result":"error","entity":null,"code":"0"}
                JSONObject object = (JSONObject) result;

                String code = object.optString("code");
                if (code.equals("0")){
                    App.getInstance().showToast("暂无订单");
                    getActivity().finish();
                    return;
                }

                LogHelper.i(TAG, "--------onResult-" );

/**
 * [{"businezzType":"12","placeOrderTime":"2017-01-16 11:05:13","starttime":"1484535913039","orderName":"社保服务","hasAccount":"N","companyId":89,"orderid":"2","companyName":"韦继胜测试公司3","orderFee":"100"},
 * {"businezzType":"10","placeOrderTime":"2017-01-12 11:10:43","starttime":"1484190643773","orderName":"注册公司","hasAccount":"N","companyId":89,"orderid":"72","companyName":"韦继胜测试公司3","orderFee":"1"}]
 */
                JSONArray jsonArray = object.optJSONArray("entity");
                int length = jsonArray.length();
                try {
                    for (int i = 0; i < length; i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        CustomerOrderMode item = new CustomerOrderMode();
                        item.setOrderStyle(jsonObject.optString("orderName"));
                        item.setBusinezzType(jsonObject.optString("businezzType"));
                        item.setCompanyId(jsonObject.optString("companyId"));
                        item.setOrderId(jsonObject.optString("orderid"));
                        item.setOrderMoney(jsonObject.optString("orderFee"));
                        item.setOrderEndTime(jsonObject.optString("placeOrderTime"));
                        mListOrderDatas.add(item);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new OrderAdapter(mListOrderDatas, getActivity());
                orderListView.setAdapter(adapter);


            }

            @Override
            public void onError(Object e) {
                LogHelper.i(TAG, "--------onError-" );
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        orderListView = (ListView) view.findViewById(R.id.orderListView);
        swipeRefreshLayout = (PullRefreshLayout) view. findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        //getTaskBarData();
                        // 刷新3秒完成
                        App.getInstance().showToast("刷新完成");



                    }
                }, 3000);
            }
        });

        swipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerOrderMode customerOrderMode = mListOrderDatas.get(position);
                String orderMoney = customerOrderMode.getOrderMoney();
                String orderStyle = customerOrderMode.getOrderStyle();
                String orderId = customerOrderMode.getOrderId();
                String companyId = customerOrderMode.getCompanyId();
                String businezzType = customerOrderMode.getBusinezzType();

                Bundle bundle=new Bundle();
                bundle.putString("orderId",orderId);
                bundle.putString("businezzType",businezzType);
                bundle.putString("orderMoney",orderMoney);
                bundle.putString("orderStyle",orderStyle);
                bundle.putString("companyId",companyId);

                IntentUtils.startActivityNumber(getActivity(),bundle,OrderActivity.class);


            }
        });

        LogHelper.i(TAG,"------onCreateView--"+companyId);
        //获取数据 TODO
        getOrderDatas();

        return view;
    }





}

