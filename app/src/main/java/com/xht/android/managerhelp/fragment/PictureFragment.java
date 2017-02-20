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
import com.xht.android.managerhelp.OrderPictureActivity;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.mode.CustomerOrderMode;
import com.xht.android.managerhelp.mode.PictureAdapter;
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
 * Use the {@link PictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 *    我的客户中的图片
 */
public class PictureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String companyId;
    private String mParam2;
    private ListView mListPicture;
    private List<CustomerOrderMode> mListMyCustomer;
    private PictureAdapter pictureAdapter;
    private PullRefreshLayout swipeRefreshLayout;
    public PictureFragment() {
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
    public static PictureFragment newInstance(String param1, String param2) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private static final String TAG = "PictureFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mListMyCustomer=new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_picture, container, false);
        mListPicture = (ListView) inflate.findViewById(R.id.mListPicture);

        swipeRefreshLayout = (PullRefreshLayout) inflate. findViewById(R.id.swipeRefreshLayout);
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
        getOrderDatas();


        mListPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomerOrderMode item = mListMyCustomer.get(position);
                String orderId = item.getOrderId();

                Bundle bundle=new Bundle();
                bundle.putString("orderId",orderId);//?orderId=12
                IntentUtils.startActivityNumber(getActivity(),bundle,OrderPictureActivity.class);


            }
        });


        return inflate;
    }

    /**
     * 从网上获取数据
     */
    private void getOrderDatas() {

        mListMyCustomer.clear();

        LogHelper.i(TAG,"-----getOrderDatas---");

        VolleyHelpApi.getInstance().getOrderListDatas(companyId, new APIListener() {
            @Override
            public void onResult(Object result) {
                //{"message":"没有订单","result":"error","entity":null,"code":"0"}
                JSONObject object = (JSONObject) result;
                LogHelper.i(TAG, "-----pic---onResult-" +object.toString());

                String code = object.optString("code");
                if (code.equals("0")){
                    App.getInstance().showToast("暂无订单");
                    getActivity().finish();
                    return;
                }



//{"businezzType":"10","placeOrderTime":"2017-01-11 14:19:46","starttime":"1484115586316",
// "orderName":"注册公司","hasAccount":"N","companyId":84,"orderid":"67","companyName":"测试公司了","orderFee":"1"}
//[{"businezzType":"10","placeOrderTime":"2017-01-04 15:49:52","starttime":"1483516192977","orderName":"注册公司",
// "hasAccount":"Y","companyId":30,"orderid":"13","companyName":"滚滚红","orderFee":"1"}
                JSONArray jsonArray = object.optJSONArray("entity");
                int length = jsonArray.length();
                try {
                    for (int i = 0; i < length; i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        CustomerOrderMode item = new CustomerOrderMode();
                        item.setOrderStyle(jsonObject.optString("orderName"));
                        String businezzType = jsonObject.optString("businezzType");
                        item.setBusinezzType(businezzType);
                        item.setOrderMoney(jsonObject.optString("orderFee"));
                        item.setOrderId((jsonObject.optString("orderid")));
                        item.setOrderStartTime(jsonObject.optString("placeOrderTime"));
                        if (businezzType.equals("10")){//现在只加注册公司
                            mListMyCustomer.add(item);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pictureAdapter = new PictureAdapter(getActivity(),mListMyCustomer);
                mListPicture.setAdapter(pictureAdapter);


            }

            @Override
            public void onError(Object e) {
                LogHelper.i(TAG, "--------onError-" );
            }
        });
    }


}

