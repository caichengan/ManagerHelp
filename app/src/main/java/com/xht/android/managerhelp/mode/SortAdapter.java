package com.xht.android.managerhelp.mode;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xht.android.managerhelp.R;

import java.util.List;


public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private static final String TAG ="SortAdapter" ;
	private List<SortModel> list = null;
	private Context mContext;
	private TextView catalog;
	private ImageView headimgview;
	private TextView title;
	private TextView mPhone;
	private String group;
	private String phone;
	private String subname;

	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);


		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_sort_listview, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.company = (TextView) view.findViewById(R.id.company);
			view.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) view.getTag();
		}
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
			//viewHolder.tvLetter.setText(mContent.getName());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		String name = this.list.get(position).getName();

		String[] split = name.split("_", 3);

		String mName = split[0];
		String mComName = split[1];
		String mPhone = split[2];

		Log.i(TAG,"----1--"+split[0]);
		Log.i(TAG,"----2--"+split[1]);
		Log.i(TAG,"----3--"+split[2]);


		Log.i(TAG,"-----name--"+name+"------companyName--"+mComName+"----mPhone-"+mPhone);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现

		viewHolder.tvTitle.setText(mName);
		viewHolder.company.setText(mComName);
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView company;
	}
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}
	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 *
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}
	@Override
	public Object[] getSections() {
		return null;
	}
}