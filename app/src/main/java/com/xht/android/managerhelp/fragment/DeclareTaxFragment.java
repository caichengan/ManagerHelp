package com.xht.android.managerhelp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.mode.DeclareTax;
import com.xht.android.managerhelp.mode.DeclareTaxAdapter;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link DeclareTaxFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 *     我的客户中的报税
 */
public class DeclareTaxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String companyId;
    private static final String TAG = "DeclareTaxFragment";
    private String year;

    private String[] mYears;
    private int mSelectedYear=-1;//选中的公司id


    private ProgressDialog mProgDoal;

    private Spinner companyTax;
    private TextView mAllTotalTax;
    private TextView mAllTotalTax2;

    private TextView mAddTax;////应纳增值税
    private TextView mAddTax2;

    private TextView mZKPtax;//自开票
    private TextView mZKPtax2;

    private TextView mSJDKPtax;//税局代开

    private TextView mWPSRtax;//无票收入
    private TextView mWPSRtax2;

    private TextView mQYSDtax;//企业所得税
    private TextView mQYSDtax2;

    private TextView mTotaltax;//小合计
    private TextView mTotaltax2;

    private TextView mAdditionTax;//附加税总额
    private TextView mAdditionTax2;

    private TextView mCityMaintainTax;//城市维护税
    private TextView mCityMaintainTax2;

    private TextView mLocoalTeachTax;//地方教育税
    private TextView mLocoalTeachTax2;

    private TextView mCityTeachTax;//城市教育税
    private TextView mCityTeachTax2;

    private TextView mLastStay2;//上期留抵
    private TextView mCurrentOutTax2;//本期销项税额度
    private TextView mCurrentInTax2;//本期进项税额度

    private LinearLayout mSalaryBiao;
    private LinearLayout mSalaryBiao2;

    private LinearLayout mSocialBiao;
    private LinearLayout mSocialBiao2;

    private Button mMakeSureTax;
    private ScrollView mScrollView;//滑动模块（小规模还是一般纳税）
    private ScrollView mScrollView2;
    private TextView mmTaxTime; //报税时间
    private TextView mmTaxTime2;
    private TextView mName;
    private TextView mName2;
    private String taxType;
    private String taxDataId;
    private String confirmStatus;
    private ListView listviewTax;
    private Spinner spinner;
    private String[] years;
    private List<DeclareTax> mListTax;


    public DeclareTaxFragment() {
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
    public static DeclareTaxFragment newInstance(String param1, String param2) {
        DeclareTaxFragment fragment = new DeclareTaxFragment();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tax, container, false);

        listviewTax = (ListView) view.findViewById(R.id.listViewTax);
        spinner = (Spinner) view.findViewById(R.id.spinner);


        Calendar a=Calendar.getInstance();
        int years = a.get(Calendar.YEAR);
        mYears = new String[]{years+"",(years-1)+"",(years-2)+"",(years-3)+""};
        LogHelper.i(TAG,"----year--"+a.get(Calendar.YEAR));
        year=a.get(Calendar.YEAR)+"";
        System.out.println(a.get(Calendar.YEAR));//得到年
        getDatasTaxDeclare();

        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, mYears);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                LogHelper.i("----------------spinner1---------", spinner.getSelectedItem().toString());
                LogHelper.i(TAG,"------------"+spinner.getSelectedItem().toString());
                 year= (String) spinner.getSelectedItem();
                getDatasTaxDeclare();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        initialize(view);
        ////?companyId=16&year=2016





        return view;

    }

    private void getDatasTaxDeclare() {

        LogHelper.i(TAG,"-----year----"+year+"---companyId---"+companyId);
        VolleyHelpApi.getInstance().getDatasTax(companyId, year, new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG,"-----------"+result.toString());

                JSONObject jsonObject= (JSONObject) result;
                String code = jsonObject.optString("code");
                if (code.equals("0")){
                    App.getInstance().showToast("暂无数据");
                    return;
                }

                JSONArray jsonArray = jsonObject.optJSONArray("entity");
                int length = jsonArray.length();

                mListTax = new ArrayList();
                try {
                for (int i=0;i<length;i++){
                    JSONObject object= (JSONObject) jsonArray.get(i);
                    reFreshUIText(object);
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                DeclareTaxAdapter adapter=new DeclareTaxAdapter(getActivity(),mListTax);
                listviewTax.setAdapter(adapter);




            }
            @Override
            public void onError(Object e) {

            }
        });

    }






    //小规模与一般纳税人
    private void reFreshUIText(JSONObject mJsonObj) {

        //,"confirmTime":"2016-12-14 10:37:58"
        //{"message":"报税数据加载成功","result":"success","billingNot":342,"year":"2015","taxInputTime":"2016-12-14 10:37:58","taxtime":"第2季度""billingTaxbureauReplace":564,
        // "entity":[{,"taxDataId":1,":0,"":134,

        // "":0,"taxType":"小规模纳税人","":1340,
        // ],
        // "code":"1"}
        DeclareTax item=new DeclareTax();
        String taxType = mJsonObj.optString("taxType");//保税类型
        String companyId = mJsonObj.optString("companyId");
        String contactName = mJsonObj.optString("contactName");//报税人
        String employeeName = mJsonObj.optString("employeeName");
        String year = mJsonObj.optString("year");
        String taxInputTime = mJsonObj.optString("taxInputTime");//报税时间
        String confirmTime = mJsonObj.optString("confirmTime");//确认时间
        String confirmStatus = mJsonObj.optString("confirmStatus");//确认状态
        String taxtime = mJsonObj.optString("taxtime");//第2季度
        Double billingSelf = mJsonObj.optDouble("billingSelf");//自开票收入

        Double companyTax = mJsonObj.optDouble("incrementValueTax");//企业所得税

        Double billingNot = mJsonObj.optDouble("billingNot");//无票收入1
        Double billingTaxbureauReplace = mJsonObj.optDouble("billingTaxbureauReplace");//税局代开票收入
        Double incrementValueTax = mJsonObj.optDouble("incrementValueTax");//应纳增值税
        Double eduSurchargeCity = mJsonObj.optDouble("eduSurchargeCity");//城市教育附加税
        Double eduSurchargeLocal = mJsonObj.optDouble("eduSurchargeLocal");//地方教育附加税
        Double maintainBuildCityTax = mJsonObj.optDouble("maintainBuildCityTax");//城市维护建设税

        Double taxTotal = mJsonObj.optDouble("taxTotal");//合计


        Double allowancesPrevious = mJsonObj.optDouble("allowancesPrevious");//上期留抵
        Double inputTaxAmountThisPeriod = mJsonObj.optDouble("inputTaxAmountThisPeriod");//本期进项税
        Double outputTaxAmountThisPeriod = mJsonObj.optDouble("outputTaxAmountThisPeriod");//本期销项税


        item.setCompanyTax(companyTax);
        item.setTaxType(taxType+"");
        item.setCompanyId(companyId+"");
        item.setContactName(contactName+"");
        item.setEmployeeName(employeeName+"");
        item.setYear(year+"");
        item.setTaxInputTime(taxInputTime+"");
        item.setConfirmTime(confirmTime+"");
        item.setConfirmStatus(confirmStatus+"");
        item.setTaxtime(taxtime+"");
        item.setIncrementValueTax(incrementValueTax);
        item.setBillingSelf(billingSelf);
        item.setBillingNot(billingNot);
        item.setBillingTaxbureauReplace(billingTaxbureauReplace);
        item.setEduSurchargeCity(eduSurchargeCity);
        item.setEduSurchargeLocal(eduSurchargeLocal);
        item.setMaintainBuildCityTax(maintainBuildCityTax);
        item.setTaxTotal(taxTotal);
        item.setAllowancesPrevious(allowancesPrevious);
        item.setInputTaxAmountThisPeriod(inputTaxAmountThisPeriod);
        item.setOutputTaxAmountThisPeriod(outputTaxAmountThisPeriod);



        mListTax.add(item);
       // mAddTax.setText(getString(String.format(getResources().getString(R.string.yuan), incrementValueTax / 1.00f)));

    }

    private void reFreshUIAndTax(){

        mAddTax.setText("");
        mZKPtax.setText("");
        mSJDKPtax.setText("");
        mWPSRtax.setText("");
        mTotaltax.setText("");
        mAdditionTax.setText("");
        mCityMaintainTax.setText("");
        mLocoalTeachTax.setText("");
        mCityTeachTax.setText("");
        mmTaxTime.setText("");
        mName.setText("");
        mAllTotalTax.setText("");


        mAddTax2.setText("");
        mZKPtax2.setText("");
        mWPSRtax2.setText("");

        mQYSDtax2.setText("");

        mTotaltax2.setText("");
        mAdditionTax2.setText("");
        mCityMaintainTax2.setText("");
        mLocoalTeachTax2.setText("");
        mCityTeachTax2.setText("");
        mmTaxTime2.setText("");
        mName2.setText("");
        mLastStay2.setText("");
        mCurrentOutTax2.setText("");
        mCurrentInTax2.setText("");
        mAllTotalTax2.setText("");



    }

    //一般纳税人
    private void reFreshNornalText(JSONObject mJsonObj) {

        String employeeName = mJsonObj.optString("employeeName");
        String year = mJsonObj.optString("year");
        String quarterOrMonth = mJsonObj.optString("quarterOrMonth");
        Double billingSelf = mJsonObj.optDouble("billingSelf");//自开票收入
        Double billingNot = mJsonObj.optDouble("billingNot");//无票收入
        Double incrementValueTax = mJsonObj.optDouble("incrementValueTax");//应纳增值税
        Double companyTax = mJsonObj.optDouble("incrementValueTax");//企业所得税
        Double taxTotal = mJsonObj.optDouble("taxTotal");//合计
        Double maintainBuildCityTax = mJsonObj.optDouble("maintainBuildCityTax");//城市维护建设税
        Double eduSurchargeCity = mJsonObj.optDouble("eduSurchargeCity");//城市教育附加税
        Double eduSurchargeLocal = mJsonObj.optDouble("eduSurchargeLocal");//地方教育附加税
        Double allowancesPrevious = mJsonObj.optDouble("allowancesPrevious");//上期留抵
        Double inputTaxAmountThisPeriod = mJsonObj.optDouble("inputTaxAmountThisPeriod");//本期进项税
        Double outputTaxAmountThisPeriod = mJsonObj.optDouble("outputTaxAmountThisPeriod");//本期销项税

        LogHelper.i(TAG,"----maintainBuildCityTax--"+maintainBuildCityTax);

        mAddTax2.setText(getString(String.format(getResources().getString(R.string.yuan), incrementValueTax / 1.00f)));
        mZKPtax2.setText(getString(String.format(getResources().getString(R.string.yuan), billingSelf / 1.00f)));
        mWPSRtax2.setText(getString((String.format(getResources().getString(R.string.yuan), billingNot / 1.00f))));
        mTotaltax2.setText(getString(String.format(getResources().getString(R.string.yuan), taxTotal / 1.00f)));

        mQYSDtax2.setText(getString(String.format(getResources().getString(R.string.yuan), taxTotal / 1.00f)));

        mAdditionTax2.setText(getString(String.format(getResources().getString(R.string.yuan), (maintainBuildCityTax+eduSurchargeCity+eduSurchargeLocal) / 1.00f)));//总附加税
        mCityMaintainTax2.setText(getString(String.format(getResources().getString(R.string.yuan),maintainBuildCityTax / 1.00f)));
        mLocoalTeachTax2.setText(getString(String.format(getResources().getString(R.string.yuan),eduSurchargeLocal / 1.00f)));
        mCityTeachTax2.setText(getString(String.format(getResources().getString(R.string.yuan),eduSurchargeCity / 1.00f)));
        mmTaxTime2.setText("第"+quarterOrMonth+"季度");
        mName2.setText(employeeName);

        mLastStay2.setText(getString(String.format(getResources().getString(R.string.yuan),allowancesPrevious / 1.00f)));
        mCurrentOutTax2.setText(getString(String.format(getResources().getString(R.string.yuan),inputTaxAmountThisPeriod / 1.00f)));
        mCurrentInTax2.setText(getString(String.format(getResources().getString(R.string.yuan),outputTaxAmountThisPeriod / 1.00f)));

        mAllTotalTax2.setText(getString(String.format(getResources().getString(R.string.yuan), (maintainBuildCityTax+eduSurchargeCity+eduSurchargeLocal+incrementValueTax+incrementValueTax )/ 1.00f)));

    }

    //数字三位一空格
    private static String getString(String str){
        DecimalFormat df = new DecimalFormat( "#,##0.00");
        return df.format(Double.parseDouble(str));
    }
    private void initialize(View view) {

        /*mScrollView = (ScrollView)view.findViewById(R.id.mScrollView);
        mScrollView2 = (ScrollView)view.findViewById(R.id.mScrollView2);
        companyTax = (Spinner) view.findViewById(R.id.companyTax);
        mAllTotalTax = (TextView) view.findViewById(R.id.mAllTotalTax);
        mAllTotalTax2 = (TextView) view.findViewById(R.id.mAllTotalTax2);
        mmTaxTime = (TextView) view.findViewById(R.id.mTaxTime);
        mmTaxTime2 = (TextView) view.findViewById(R.id.mTaxTime2);

        mAddTax = (TextView) view.findViewById(R.id.mAddTax);
        mAddTax2 = (TextView) view.findViewById(R.id.mAddTax2);

        mZKPtax = (TextView) view.findViewById(R.id.mZKPtax);
        mZKPtax2 = (TextView) view.findViewById(R.id.mZKPtax2);
        mSJDKPtax = (TextView) view.findViewById(R.id.mSJDKPtax);

        mWPSRtax = (TextView) view.findViewById(R.id.mWPSRtax);
        mWPSRtax2 = (TextView) view.findViewById(R.id.mWPSRtax2);

        mQYSDtax2 = (TextView) view.findViewById(R.id.mQYSDtax2);//企业所得税
        mQYSDtax = (TextView) view.findViewById(R.id.mQYSDtax);//企业所得税

        mTotaltax = (TextView) view.findViewById(R.id.mTotaltax);
        mTotaltax2 = (TextView) view.findViewById(R.id.mTotaltax2);

        mAdditionTax = (TextView) view.findViewById(R.id.mAdditionTax);
        mAdditionTax2 = (TextView) view.findViewById(R.id.mAdditionTax2);

        mCityMaintainTax = (TextView) view.findViewById(R.id.mCityMaintainTax);
        mCityMaintainTax2 = (TextView) view.findViewById(R.id.mCityMaintainTax2);

        mLocoalTeachTax = (TextView) view.findViewById(R.id.mLocoalTeachTax);
        mLocoalTeachTax2 = (TextView) view.findViewById(R.id.mLocoalTeachTax2);

        mCityTeachTax = (TextView) view.findViewById(R.id.mCityTeachTax);
        mCityTeachTax2 = (TextView) view.findViewById(R.id.mCityTeachTax2);

        mSalaryBiao = (LinearLayout) view.findViewById(R.id.mSalaryBiao);
        mSalaryBiao2 = (LinearLayout) view.findViewById(R.id.mSalaryBiao2);
        mSocialBiao = (LinearLayout) view.findViewById(R.id.mSocialBiao);
        mSocialBiao2 = (LinearLayout) view.findViewById(R.id.mSocialBiao2);

        mLastStay2 = (TextView) view.findViewById(R.id.mLastStay2);
        mCurrentOutTax2 = (TextView) view.findViewById(R.id.mCurrentOutTax2);
        mCurrentInTax2 = (TextView) view.findViewById(R.id.mCurrentInTax2);

        mName = (TextView) view.findViewById(R.id.mTaxName);
        mName2 = (TextView) view.findViewById(R.id.mTaxName2);*/



    }


}

