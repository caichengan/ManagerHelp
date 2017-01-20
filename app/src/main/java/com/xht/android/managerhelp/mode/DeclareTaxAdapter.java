package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */

public class DeclareTaxAdapter extends BaseAdapter {
    private List<DeclareTax> mListTax ;
    private Context mContext;
    private ViewHolder holder;


    public DeclareTaxAdapter(FragmentActivity activity, List<DeclareTax> mListTax) {
        this.mListTax=mListTax;
        this.mContext=activity;

    }

    @Override
    public int getCount() {
        if (mListTax.size()>0){
            return mListTax.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mListTax.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //[{"allowancesPrevious":0,"taxDataId":1,"eduSurchargeLocal":34524,"outputTaxAmountThisPeriod":0,"maintainBuildCityTax":134,
        // "employeeName":null,"billingTaxbureauReplace":564,"billingNot":342,"confirmTime":"2016-12-14 10:37:58","confirmStatus":"已确认",
        // "inputTaxAmountThisPeriod":0,"eduSurchargeCity":1323,"contactName":"有限公司","taxType":"小规模纳税人","taxTotal":1340,"billingSelf":434,"year":"2015",
        // "taxInputTime":"2016-12-14 10:37:58","companyId":"14","taxtime":"第2季度","incrementValueTax":231}],"code":"1"}



        if (convertView==null){
            holder = new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_tax,null);
            initialize(convertView);
            convertView.setTag(holder);

        }else{

            holder= (ViewHolder) convertView.getTag();

        }

       /** String taxType = mJsonObj.optString("taxType");//保税类型
        String companyId = mJsonObj.optString("companyId");
        String contactName = mJsonObj.optString("contactName");//报税人
        String employeeName = mJsonObj.optString("employeeName");
        String year = mJsonObj.optString("year");
        String taxInputTime = mJsonObj.optString("taxInputTime");//报税时间
        String confirmTime = mJsonObj.optString("confirmTime");//确认时间
        String confirmStatus = mJsonObj.optString("confirmStatus");//确认状态
        String taxtime = mJsonObj.optString("taxtime");//第2季度
        Double billingSelf = mJsonObj.optDouble("billingSelf");//自开票收入
        Double billingNot = mJsonObj.optDouble("billingNot");//无票收入1
        Double billingTaxbureauReplace = mJsonObj.optDouble("billingTaxbureauReplace");//税局代开票收入
        Double incrementValueTax = mJsonObj.optDouble("incrementValueTax");//应纳增值税
        Double eduSurchargeCity = mJsonObj.optDouble("eduSurchargeCity");//城市教育附加税
        Double eduSurchargeLocal = mJsonObj.optDouble("eduSurchargeLocal");//地方教育附加税
        Double maintainBuildCityTax = mJsonObj.optDouble("maintainBuildCityTax");//城市维护建设税

        Double taxTotal = mJsonObj.optDouble("taxTotal");//合计


        Double allowancesPrevious = mJsonObj.optDouble("allowancesPrevious");//上期留抵
        Double inputTaxAmountThisPeriod = mJsonObj.optDouble("inputTaxAmountThisPeriod");//本期进项税
        Double outputTaxAmountThisPeriod = mJsonObj.optDouble("outputTaxAmountThisPeriod");//本期销项税*/

        DeclareTax declareTax = mListTax.get(position);
        String contactName = declareTax.getContactName();
        Double taxTotal = declareTax.getTaxTotal();//合计
        Double incrementValueTax = declareTax.getIncrementValueTax();//应纳增值税
        Double companyTax = declareTax.getCompanyTax();                      //企业所得税  TODO
        Double billingSelf = declareTax.getBillingSelf();//自开票收入
        Double billingNot = declareTax.getBillingNot();//无票收入1
        Double eduSurchargeCity = declareTax.getEduSurchargeCity();//城市教育附加税
        Double eduSurchargeLocal = declareTax.getEduSurchargeLocal();//地方教育附加税
        Double maintainBuildCityTax = declareTax.getMaintainBuildCityTax();//城市维护建设税

        Double billingTaxbureauReplace = declareTax.getBillingTaxbureauReplace();//税局代开票收入------小规模

        Double allowancesPrevious = declareTax.getAllowancesPrevious();//上期留抵
        Double inputTaxAmountThisPeriod = declareTax.getInputTaxAmountThisPeriod();//本期进项税
        Double outputTaxAmountThisPeriod = declareTax.getOutputTaxAmountThisPeriod();//本期销项税


        String taxType = declareTax.getTaxType();
        if (taxType.equals("一般纳税人")){
            holder.usually.setVisibility(View.VISIBLE);
            holder.small.setVisibility(View.GONE);

            holder.mLastStay2.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), allowancesPrevious / 1.00f)));
            holder.mCurrentInTax2.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), inputTaxAmountThisPeriod / 1.00f)));
            holder.mCurrentOutTax2.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), outputTaxAmountThisPeriod / 1.00f)));

        }else{
            holder.usually.setVisibility(View.GONE);
            holder.small.setVisibility(View.VISIBLE);
            holder.mSJDKPtax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), billingTaxbureauReplace / 1.00f)));
        }

        holder. mQYSDtax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), companyTax / 1.00f)));
        holder.mAddTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), incrementValueTax / 1.00f)));
        holder.mZKPtax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), billingSelf / 1.00f)));
        holder. mWPSRtax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), billingNot / 1.00f)));

        holder.mTotaltax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), taxTotal / 1.00f)));//合计


        holder.mCityMaintainTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), maintainBuildCityTax / 1.00f)));
        holder.mCityTeachTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), eduSurchargeCity / 1.00f)));
        holder.mLocoalTeachTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), eduSurchargeLocal / 1.00f)));

        Double addTotalTax=allowancesPrevious+inputTaxAmountThisPeriod+outputTaxAmountThisPeriod+taxTotal;//总合计

        holder.mAllTotalTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), addTotalTax / 1.00f)));


        holder.mAdditionTax.setText(getString(String.format(mContext.getResources().getString(R.string.yuan), (addTotalTax-taxTotal) / 1.00f)));
      //  mAddTax2.setText(getString(String.format(getResources().getString(R.string.yuan), incrementValueTax / 1.00f)));

        holder. mTaxName.setText(contactName);

        return convertView;
    }

    //数字三位一空格
    private static String getString(String str){
        DecimalFormat df = new DecimalFormat( "#,##0.00");
        return df.format(Double.parseDouble(str));
    }
    private void initialize( View convertView) {
        holder.heji = (TextView) convertView.findViewById(R.id.heji);
        holder.mTaxTime = (TextView) convertView.findViewById(R.id.mTaxTime);
        holder.mAllTotalTax = (TextView) convertView.findViewById(R.id.mAllTotalTax);
        holder. mQYSDtax = (TextView) convertView.findViewById(R.id.mQYSDtax);
        holder.mAddTax = (TextView) convertView.findViewById(R.id.mAddTax);
        holder.mZKPtax = (TextView) convertView.findViewById(R.id.mZKPtax);
        holder.mSJDKPtax = (TextView) convertView.findViewById(R.id.mSJDKPtax);
        holder. mWPSRtax = (TextView) convertView.findViewById(R.id.mWPSRtax);
        holder.mTotaltax = (TextView) convertView.findViewById(R.id.mTotaltax);
        holder.mAdditionTax = (TextView) convertView.findViewById(R.id.mAdditionTax);

        holder.small=(LinearLayout)convertView.findViewById(R.id.small);//小规模
        holder.usually=(LinearLayout)convertView.findViewById(R.id.usually);//一般mCurrentOutTax2

        holder.mCurrentOutTax2 = (TextView) convertView.findViewById(R.id.mCurrentOutTax2);
        holder.mLastStay2 = (TextView) convertView.findViewById(R.id.mLastStay2);
        holder.mCurrentInTax2 = (TextView) convertView.findViewById(R.id.mCurrentInTax2);

        holder.mCityMaintainTax = (TextView) convertView.findViewById(R.id.mCityMaintainTax);
        holder. mLocoalTeachTax = (TextView) convertView.findViewById(R.id.mLocoalTeachTax);
        holder. mCityTeachTax = (TextView) convertView.findViewById(R.id.mCityTeachTax);
        holder.mSocialBiao = (LinearLayout) convertView.findViewById(R.id.mSocialBiao);
        holder.mTax = (TextView) convertView.findViewById(R.id.mTax);
        holder. mTaxName = (TextView) convertView.findViewById(R.id.mTaxName);
    }

    class ViewHolder{

         TextView heji;
         TextView mTaxTime;
         TextView mAllTotalTax;
         TextView mQYSDtax;
         TextView mAddTax;
         TextView mZKPtax;
         TextView mSJDKPtax;
         TextView mWPSRtax;
         TextView mTotaltax;
         TextView mAdditionTax;
         TextView mLastStay2;
         TextView mCurrentOutTax2;
         TextView mCurrentInTax2;


         TextView mCityMaintainTax;
         TextView mLocoalTeachTax;
         TextView mCityTeachTax;
         LinearLayout mSocialBiao;
         LinearLayout small;
         LinearLayout usually;

         TextView mTax;
         TextView mTaxName;

    }
}
