package com.xht.android.managerhelp;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.managerhelp.mode.SortAdapter;
import com.xht.android.managerhelp.mode.SortModel;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.CharacterParser;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;
import com.xht.android.managerhelp.util.PinyinComparator;
import com.xht.android.managerhelp.view.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通讯录界面
 */
public class TxlFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TxlFragment";
    private MainActivity mActivity;
    private TextView mClientContacts;
    private TextView mInsideContacts;

    private static List<SortModel> mClientList=new ArrayList<>();
    private SortAdapter mSortAdapter;
    private ClearEditText mClientEdit;
    private int mFlagInt=1;
    private PinyinComparator pinyinComparator;
    private CharacterParser characterParser;

    private ListView mClientListView;
    private String mContactName;
    private String mContactsPhone;
    private String mId;
    private String style="1";

    /** 根据拼音来排列ListView里面的数据类
     **/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_txl, container, false);
        mClientContacts = (TextView) view.findViewById(R.id.mClientContacts);
        mInsideContacts = (TextView)view.findViewById(R.id.mInsideContacts);
        // mFragmentTXL = (FrameLayout) view.findViewById(R.id.mFragmentTXL);

        mClientEdit = (ClearEditText) view.findViewById(R.id.mClientEdit);
        mClientListView = (ListView) view.findViewById(R.id.mClientList);

        mClientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String name = ((SortModel) mSortAdapter.getItem(position)).getName();
               App.getInstance().showToast("name"+name);
                String[] split = name.split("_", 3);
                String mName = split[0];
                String mComName = split[1];
                String mPhone = split[2];
                Log.i(TAG,"----1--"+split[0]);
                Log.i(TAG,"----2--"+split[1]);
                Log.i(TAG,"----3--"+split[2]);
                App.getInstance().showToast("mName----:"+mName+"----mPhone:"+mPhone+"---mComName:"+mComName);
                Bundle bundle=new Bundle();
                bundle.putString("mContactName",mName);
                bundle.putString("mId",mId);
                bundle.putString("mComName",mComName);
                bundle.putString("mPhone",mPhone);
                LogHelper.i(TAG,"------"+position+";;;;"+mName+mComName+mPhone);
               IntentUtils.startActivityNumber(mActivity,bundle,TXLContactsDetial.class);
            }
        });
        mClientContacts.setOnClickListener(this);
        mInsideContacts.setOnClickListener(this);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        getTXLBarData();

        // mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        //根据输入框输入值的改变来过滤搜索
        mClientEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

                if (mClientList.size()>0) {
                    filterData(s.toString());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        mInsideContacts.setTextColor(Color.GRAY);
        mClientContacts.setTextColor(Color.WHITE);
        switch (v.getId()){

            case R.id.mClientContacts:
                selectText(1);
                LogHelper.i(TAG,"----ClientContacts");
                style = "1";
                break;
            case R.id.mInsideContacts:
                selectText(2);
                style ="2";
                LogHelper.i(TAG,"----InsideContacts");
                break;
        }
    }
    private void selectText(int i) {
        clearBack();

        switch (i){
            case 1:
                getTXLBarData();
                mClientContacts.setBackgroundResource(R.drawable.btn_background_circle);
                mClientContacts.setTextColor(Color.WHITE);
                mInsideContacts.setTextColor(Color.GRAY);
                break;
            case 2:
                getTXLInsideData();
                // mInsideContacts.setBackgroundColor(0xff33b5e5);
                mInsideContacts.setBackgroundResource(R.drawable.btn_background_circle);
                mClientContacts.setTextColor(Color.GRAY);
                mInsideContacts.setTextColor(Color.WHITE);
                break;
        }
    }
    private void clearBack() {
        mClientList.clear();

        mClientContacts.setBackgroundColor(Color.TRANSPARENT);
        mInsideContacts.setBackgroundColor(Color.TRANSPARENT);
    }

    //访问客户通讯录数据
    private void getTXLBarData() {
        clearBack();
        mClientContacts.setBackgroundResource(R.drawable.btn_background_circle);
        VolleyHelpApi.getInstance().getTXLData(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----所有信息--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){
                        ////{"ordContactId":1,"companyId":"12","companyName":"安测试公司一","telephone":"13531829360","contactName":"安"},
                        SortModel item=new SortModel();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        String id = JsonItem.optString("ordContactId");
                        String contactName = JsonItem.optString("contactName");
                        String telephone = JsonItem.optString("telephone");//起始步骤
                        String companyName = JsonItem.optString("companyName");//起始步骤
                        item.setName(contactName);
                        item.setPhoneNum(telephone);
                        item.setCompanyName(companyName);
                        item.setId(id);

                        mClientList.add(item);
                        LogHelper.i(TAG, "-----t通讯录--" +i+"--"+contactName+telephone);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //更新ListView
                udListView();
            }

            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
            }
        });
    }

    //访问内部通讯录数据
    private void getTXLInsideData() {
        VolleyHelpApi.getInstance().getTXLInsideData(new APIListener() {
            //"entity":[{"telephone":"13652365101","employeeName":"祝飞"},{"telephone":"13652365101","employeeName":"ZHUFEI"}
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----所有信息--" + result.toString());
                JSONArray JsonAy = null;
                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");
                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){//{"ordContactId":1,"companyId":"12","companyName":"安测试公司一","telephone":"13531829360","contactName":"安"},
                        // {"ordContactId":1,"companyId":"14","companyName":"测试公司二","telephone":"13531829360","contactName":"安"},{"ordContactId":1,"companyId":"16","companyName":"测试公司三","telephone":"13531829360","contactName":"安"},{"ordContactId":1,"companyId":"17","companyName":"古古怪怪","telephone":"13531829360","contactName":"安"},{"ordContactId":1,"companyId":"27","companyName":"测试公司四","telephone":"13531829360","contactName":"安"},{"ordContactId":1,"companyId":"28","companyName":"测试公司五","telephone":"13531829360","contactName":"安"},{"ordContactId":1,"companyId":"29","companyName":"测试公司八","telephone":"13531829360","contactName":"安"},{"ordContactId":2,"companyId":"13","companyName":"中山市小后台会计服务有限公司","telephone":"13421411253","contactName":"覃源恒"},
                        SortModel item=new SortModel();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        String id = JsonItem.optString("id");
                        String employeeName = JsonItem.optString("employeeName");
                        String telephone = JsonItem.optString("telephone");//起始步骤
                        String companyName = JsonItem.optString("companyName");//起始步骤
                        item.setName(employeeName);
                        item.setPhoneNum(telephone);
                        item.setCompanyName(companyName);
                        item.setId(id);
                        mClientList.add(item);

                        LogHelper.i(TAG, "-----内部通讯录--" +i+"--"+employeeName+telephone);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //更新ListView
                udListView();
            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
            }
        });
    }

    private void udListView() {
        //TODO
        int len = mClientList.size();
        String[] date = new String[len];
        Log.i(TAG, "----len-" + len);
        for (int i = 0; i < len; i++) {
            date[i] = mClientList.get(i).getName()+"_"+mClientList.get(i).getCompanyName()+"_"+mClientList.get(i).getPhoneNum();

            LogHelper.i(TAG,"--------"+i+"--"+mClientList.get(i).getId());
            LogHelper.i(TAG,"-------date-"+i+"--"+date[i].toString());
        }


        mClientList = filledData(date);
        // 根据a-z进行排序源数据
        Collections.sort(mClientList, pinyinComparator);
        LogHelper.i(TAG,"-----111"+mClientList.get(2).getPhoneNum());
        mSortAdapter = new SortAdapter(mActivity, mClientList);
        mClientListView.setAdapter(mSortAdapter);
        LogHelper.i(TAG,"-----222"+mClientList.get(2).getPhoneNum());

    }
   /*   * 为ListView填充数据
     * @param date
     * @return
     **/

    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            LogHelper.i(TAG,"---"+date.length);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
    /* * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     **/
    private void filterData(String filterStr){
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mClientList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mClientList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        mSortAdapter.updateListView(filterDateList);
    }

}