package com.xht.android.managerhelp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.mode.ContactsAdapter;
import com.xht.android.managerhelp.mode.ContactsMode;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
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
public class CustomerContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String companyId;
    private String mParam2;
    private ListView mListView;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private View view;

    private List<ContactsMode> mListMyCustomer;
    private ContactsAdapter contactsAdapter;
    private PullRefreshLayout swipeRefreshLayout;

    public CustomerContactsFragment() {
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
    public static CustomerContactsFragment newInstance(String param1, String param2) {
        CustomerContactsFragment fragment = new CustomerContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "CustomerContactsFragmen";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        LogHelper.i(TAG,"--------"+companyId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_customer, container, false);
        mListView = (ListView) inflate.findViewById(R.id.mListCustomer);

        ImageView imgAdd= (ImageView) inflate.findViewById(R.id.addCustomer);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addContactsDialog();



            }
        });
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
        mListMyCustomer=new ArrayList<>();
        getOrderDatas();
        return inflate;
    }

    //添加成员对话框
    private void addContactsDialog() {

        builder=new AlertDialog.Builder(getActivity());
        view=View.inflate(getActivity(), R.layout.dialog_addcontacts, null);
        builder.setView(view);
        final EditText edContactName = (EditText) view.findViewById(R.id.edContactName);
        final EditText edContactPhone = (EditText) view.findViewById(R.id.edContactPhone);
        Button  btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ContactName = edContactName.getText().toString();
                String ContactPhone = edContactPhone.getText().toString();

                if (TextUtils.isEmpty(ContactName)){
                    App.getInstance().showToast("姓名不可为空");
                    return;
                }
                if (TextUtils.isEmpty(ContactPhone)){
                    App.getInstance().showToast("号码不可为空");
                    return;
                }

                addContactsMethod(ContactName,ContactPhone);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog=builder.show();


    }

    /**
     * 添加通讯录成员
     */
    private void addContactsMethod(String name,String phone) {

        JSONObject object=new JSONObject();
        try {
            object.put("telphone",""+phone);
            object.put("contactName",""+name);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        LogHelper.i(TAG,"--------"+object.toString());
        dialog.dismiss();
        VolleyHelpApi.getInstance().postAddcontacts(object, new APIListener() {
            @Override
            public void onResult(Object result) {



            }

            @Override
            public void onError(Object e) {

            }
        });

    }

    /**
     * 从网上获取通讯录
     */
    private void getOrderDatas() {

      //  mListMyCustomer.clear();

        LogHelper.i(TAG,"-----getContactsManager---");

        VolleyHelpApi.getInstance().getContactsManager(companyId, new APIListener() {
            @Override
            public void onResult(Object result) {
                //{"message":"没有订单","result":"error","entity":null,"code":"0"}
                JSONObject object = (JSONObject) result;

                String code = object.optString("code");
                if (code.equals("0")){
                    return;
                }

                LogHelper.i(TAG, "--------onResult--000-"+object.toString() );

//{"message":"联系人信息加载成功","result":"success","entity":[{"companyid":29,"ordContactId":1,"companyName":"
// 破天一","telephone":"13531829360","contactName":"有限公司"}],"code":"1"}
                JSONArray jsonArray = object.optJSONArray("entity");
                int length = jsonArray.length();
                try {
                for (int i = 0; i < length; i++) {

                        JSONObject json= (JSONObject) jsonArray.get(i);

                        ContactsMode item=new ContactsMode();
                        String companyid = json.optString("companyid");
                        String ordContactId = json.optString("ordContactId");
                        String companyName = json.optString("companyName");
                        String telephone = json.optString("telephone");
                        String contactName = json.optString("contactName");
                        item.setCompanyId( companyid);
                        item.setContantId(ordContactId);
                       item.setCompanyName(companyName);
                       item.setContactPhone(telephone);
                        item.setCustomerName(contactName);

                        LogHelper.i(TAG,"-----------"+companyid+"--"+ordContactId+"--"+companyName+"--"+telephone+"--"+contactName);
                        mListMyCustomer.add(item);


                }

                } catch (JSONException e) {
                    e.printStackTrace();


                }



                contactsAdapter = new ContactsAdapter(getActivity(),mListMyCustomer);
                mListView.setAdapter(contactsAdapter);


            }

            @Override
            public void onError(Object e) {
                LogHelper.i(TAG, "--------onError-" );
            }
        });
    }


}

