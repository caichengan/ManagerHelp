package com.xht.android.managerhelp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.xht.android.managerhelp.App;
import com.xht.android.managerhelp.R;
import com.xht.android.managerhelp.mode.LowWarningAdapter;
import com.xht.android.managerhelp.mode.NewDataBean;
import com.xht.android.managerhelp.net.APIListener;
import com.xht.android.managerhelp.net.VolleyHelpApi;
import com.xht.android.managerhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YesManagerFragment extends Fragment {

	private ListView listviewYes;
	private PullRefreshLayout swipeRefreshLayout;
	private ListView listviewLowWarning;
	private List<NewDataBean> mListViewDatas;
	private LowWarningAdapter lowWarningAdapter;
	private TextView tvLowNotDone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private static final String TAG = "YesManagerFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.yesmanagerwarn, null);
		listviewLowWarning = (ListView) inflate. findViewById(R.id.listviewYes);
		View view = View.inflate(getActivity(), R.layout.item_text, null);
		listviewLowWarning.addHeaderView(view);
		tvLowNotDone= (TextView) view.findViewById(R.id.tvLowNotDone);
		final PullRefreshLayout layout = (PullRefreshLayout) inflate. findViewById(R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				layout.postDelayed(new Runnable() {
					@Override
					public void run() {

						// 刷新3秒完成
						layout.setRefreshing(false);
						App.getInstance().showToast("刷新完成");
						getDataDoneLowing();
					}
				}, 3000);
			}
		});

		mListViewDatas = new ArrayList<>();
		getDataDoneLowing();
        return inflate;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden){
			lowWarningAdapter.dismissPopupwindow();
		}
	}
	public void getDataDoneLowing(){
	mListViewDatas.clear();

	VolleyHelpApi.getInstance().getLowDoneDatas(new APIListener() {
		@Override
		public void onResult(Object result) {
			LogHelper.i(TAG, "-----getLowDoneDatas--" + result.toString());
			JSONArray JsonAy = null;
			try {
				JsonAy = ((JSONObject) result).getJSONArray("entity");
				int JsonArryLenth=JsonAy.length();
				for (int i=0;i<JsonArryLenth;i++){//[{"id":1,"telephone":"13531833516","contactName":"韦继胜"}
					NewDataBean item=new NewDataBean();
					JSONObject JsonItem = (JSONObject) JsonAy.get(i);
					item.setComName(JsonItem.getString("companyName"));
					item.setBanZhengContact(JsonItem.getString("employeeName"));
					item.setComment(JsonItem.getString("commentContent"));
					item.setLevel(JsonItem.getString("level"));
					item.setCommentId(JsonItem.getString("commentId"));
					item.setStatus(JsonItem.getString("dealStatus"));
					item.setStepName(JsonItem.getString("flowName"));
					mListViewDatas.add(item);
					LogHelper.i(TAG, "-------" );
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			tvLowNotDone.setText(mListViewDatas.size()+"");
			lowWarningAdapter.notifyDataSetChanged();
		}
		@Override
		public void onError(Object e) {
			App.getInstance().showToast(e.toString());
		}
	});
		lowWarningAdapter = new LowWarningAdapter(listviewLowWarning,mListViewDatas,getActivity());
		listviewLowWarning.setAdapter(lowWarningAdapter);

   }

}
