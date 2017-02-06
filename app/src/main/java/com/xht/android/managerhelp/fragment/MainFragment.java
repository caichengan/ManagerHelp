package com.xht.android.managerhelp.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.GetMoneyActivity;
import com.xht.android.managerhelp.LowWarningActivity;
import com.xht.android.managerhelp.MainActivity;
import com.xht.android.managerhelp.MyCustomerActivity;
import com.xht.android.managerhelp.NewAddCustomerActivity;
import com.xht.android.managerhelp.PerformanceActivity;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.StepBanZhengActivity;
import com.xht.android.managerhelp.mode.UserInfo;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.provider.MyDatabaseManager;
import com.xht.android.managerhelp.util.IntentUtils;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONObject;

public class MainFragment extends Fragment implements View.OnClickListener{
	private static final String TAG = "MainFragment";
	private MainActivity mActivity;
	private int uid;
	private String receivablesCountToday;
	private String orderCountToday;
	private String permitStepCountToday;
	private String lowLevelWarningNotDealCount;
	private String achievementPermitCountToday;
	private TextView orderNumber;
	private LinearLayout lLayout01;
	private TextView moneyNumber;
	private LinearLayout lLayout02;
	private TextView stepNumber;
	private LinearLayout lLayout03;
	private TextView lowNumber;
	private TextView permitNumber;
	private LinearLayout lLayout04;
	private LinearLayout lLayout05;
	private LinearLayout lLayout06;
	private View view;
	private int[] mCompIds;
	private UserInfo userInfo;
	//private PushAgent pushAgent;

	public  static String ALIAS_TYPE_XHT="com.xht.android.managerhelp";

	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		uid = MainActivity.getInstance().getUid();
		LogHelper.i(TAG,"-----  onAttach  --"+uid);
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity= (MainActivity) getActivity();
		userInfo = MainActivity.getInstance();
		uid=userInfo.getUid();
		getMainData();
		/*pushAgent = App.getPushAgent();
		//添加bu部分
			pushAgent.getTagManager().add(new TagManager.TCallBack() {
			@Override
			public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
				//isSuccess表示操作是否成功
				LogHelper.i(TAG,"----isSuccess---"+isSuccess+"---uid-"+uid);
			}
		}, uid+"");*/
		/*pushAgent.removeAlias(uid+"", ALIAS_TYPE_XHT, new UTrack.ICallBack() {
			@Override
			public void onMessage(boolean isSuccess, String message) {

				LogHelper.i(TAG,"----addAlias---"+isSuccess+"---message-"+message);
			}
		});*/

	}

	//访问数据显示数量
	private void getMainData() {

		if (!isUserLogin()){
			App.getInstance().showToast("请先登录再进行操作");
			return;
		}
		uid = userInfo.getUid();
		LogHelper.i(TAG,"-----  getMainData  --"+uid);

			VolleyHelpApi.getInstance().getMainData(uid, new APIListener() {

				@Override
				public void onResult(Object result) {

					LogHelper.i(TAG, "------11首页的所有信息--");
					LogHelper.i(TAG, "------11首页的所有信息--" + result.toString());
					//{"yw":0,"bz":0,"yj":3}
					//JSONObject JSONOB= (JSONObject) result;
					//{"message":"系统错误","result":"error","entity":null,"code":"0"}
					//{"receivablesCountToday":"0",收款量
					// "achievementPermitCountToday":"0",//业绩统计
					// "orderCountToday":"0",订单
					// "lowLevelWarningNotDealCount":"0",低分预警
					// "permitStepCountToday":"0"}办证步骤量

					String code = ((JSONObject) result).optString("code");
					if (code.equals("0")){
						LogHelper.i(TAG, "-----if-shouye--" + result.toString());
						return;
					}else {
						LogHelper.i(TAG, "----else--shouye--" + result.toString());
						JSONObject JSONOB = ((JSONObject) result).optJSONObject("entity");
						receivablesCountToday = JSONOB.optString("receivablesCountToday");
						orderCountToday = JSONOB.optString("orderCountToday");
						permitStepCountToday = JSONOB.optString("permitStepCountToday");
						lowLevelWarningNotDealCount = JSONOB.optString("lowLevelWarningNotDealCount");
						achievementPermitCountToday = JSONOB.optString("achievementPermitCountToday");


						LogHelper.i(TAG, "----所有信息--" + receivablesCountToday + orderCountToday + permitStepCountToday+lowLevelWarningNotDealCount+achievementPermitCountToday);
						App.getInstance().showToast("----所有信息--" + receivablesCountToday + orderCountToday + permitStepCountToday+lowLevelWarningNotDealCount+achievementPermitCountToday);
						uDpateDataMethod();
					}
				}
				@Override
				public void onError(Object e) {
					App.getInstance().showToast(e.toString());
				}
			});

	}

	@Override
	public void onResume() {
		super.onResume();

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		initialize(view);
		lLayout01.setOnClickListener(this);
		lLayout02.setOnClickListener(this);
		lLayout03.setOnClickListener(this);
		lLayout04.setOnClickListener(this);
		lLayout05.setOnClickListener(this);
		lLayout06.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		int uid = userInfo.getUid();
		Bundle bundle=new Bundle();
		bundle.putInt("uid",uid);// TODO 传用户id  2
		if (!isUserLogin()){
			App.getInstance().showToast("请先登录再进行操作");
			return;
		}

		switch (v.getId()){
			case R.id.lLayout01:


				IntentUtils.startActivityNumber(getActivity(),bundle,NewAddCustomerActivity.class);

				break;
			case R.id.lLayout02:

				IntentUtils.startActivityNumber(getActivity(),bundle,GetMoneyActivity.class);

				break;

			case R.id.lLayout03:


				IntentUtils.startActivityNumber(getActivity(),bundle,StepBanZhengActivity.class);
				break;
			case R.id.lLayout04:


				IntentUtils.startActivityNumber(getActivity(),bundle,LowWarningActivity.class);
				break;
			case R.id.lLayout05:


				IntentUtils.startActivityNumber(getActivity(),bundle,PerformanceActivity.class);
				break;
			case R.id.lLayout06:

				IntentUtils.startActivityNumber(getActivity(),bundle,MyCustomerActivity.class);
				break;
		}

	}

	private void initialize(View view) {
		orderNumber = (TextView) view.findViewById(R.id.orderNumber);
		lLayout01 = (LinearLayout)view.findViewById(R.id.lLayout01);
		moneyNumber = (TextView)view.findViewById(R.id.moneyNumber);
		lLayout02 = (LinearLayout)view.findViewById(R.id.lLayout02);
		stepNumber = (TextView) view.findViewById(R.id.stepNumber);
		lLayout03 = (LinearLayout) view.findViewById(R.id.lLayout03);
		lowNumber = (TextView) view.findViewById(R.id.lowNumber);
		permitNumber = (TextView) view.findViewById(R.id.permitNumber);
		lLayout04 = (LinearLayout) view.findViewById(R.id.lLayout04);
		lLayout05 = (LinearLayout) view.findViewById(R.id.lLayout05);
		lLayout06 = (LinearLayout) view.findViewById(R.id.lLayout06);
	}


	private void uDpateDataMethod() {
		orderNumber.setText(orderCountToday);
		moneyNumber.setText(receivablesCountToday);
		stepNumber.setText(permitStepCountToday);
		lowNumber.setText(lowLevelWarningNotDealCount);
		permitNumber.setText(achievementPermitCountToday);
	}
	boolean isUserLogin() {
		if (userInfo.getUid() == 0) {
			Cursor cursor = mActivity.getContentResolver().query(MyDatabaseManager.MyDbColumns.CONTENT_URI, null, null, null, null);
			if (cursor == null || cursor.getCount() == 0) {
				return false;
			}
			cursor.moveToFirst();
			int uidIndex = cursor.getColumnIndex(MyDatabaseManager.MyDbColumns.UID);
			int userNameIndex = cursor.getColumnIndex(MyDatabaseManager.MyDbColumns.NAME);
			int urlIndex = cursor.getColumnIndex(MyDatabaseManager.MyDbColumns.URL);
			int phoneIndex = cursor.getColumnIndex(MyDatabaseManager.MyDbColumns.PHONE);
			userInfo.setUid(cursor.getInt(uidIndex));
			userInfo.setUserName(cursor.getString(userNameIndex));
			userInfo.setmContactUrl(cursor.getString(urlIndex));
			userInfo.setPhoneNum(cursor.getLong(phoneIndex));
		}
		LogHelper.i(TAG, "----------mUserInfo.getUid() == " + userInfo.getUid() + "mUserInfo.getPhoneNum() == " + userInfo.getPhoneNum());

		return true;

	}

}
