package com.xht.android.managerhelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.mode.CustomerOrderMode;
import com.xht.android.managerhelp.mode.OrderAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

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
 *    我的客户中的订单信息
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "OrderFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView orderListView;
    private PullRefreshLayout swipeRefreshLayout;

    private List<CustomerOrderMode> mListOrderDatas=new ArrayList();

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        //获取数据
        getOrderDatas();


    }

    /**
     * 从网上获取数据
     */
    private void getOrderDatas() {

        int companyId=0;
        VolleyHelpApi.getInstance().getOrderListDatas(companyId, new APIListener() {
            @Override
            public void onResult(Object result) {

                JSONObject object= (JSONObject) result;
                LogHelper.i(TAG,"---------"+object.toString());
            }

            @Override
            public void onError(Object e) {


            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_order, container, false);
        orderListView = (ListView)view. findViewById(R.id.orderListView);
        swipeRefreshLayout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // 刷新3秒完成
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        OrderAdapter adapter=new OrderAdapter(mListOrderDatas,getActivity());

        orderListView.setAdapter(adapter);

        return view;
    }




}

