package com.example.administrator.listviewandviewpage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.listviewandviewpage.R;
import com.example.administrator.listviewandviewpage.application.BaseApplication;

import java.util.List;


public class MyListViewAdapter extends BaseAdapter {
	private List<String> items;
	private int ListViewHeaderCounts;

	public MyListViewAdapter(List<String> items, int ListViewHeaderCounts) {
		this.items = items;
		this.ListViewHeaderCounts = ListViewHeaderCounts;
	}

	ListViewItemHolder holder;

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {

		// int headerViewsCount = mListView.getHeaderViewsCount();
		// return position - headerViewsCount;
		return position - ListViewHeaderCounts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (ListViewItemHolder) convertView.getTag();
		} else {
			convertView = View.inflate(BaseApplication.getmContext(),
					R.layout.list_item, null);
			holder = new ListViewItemHolder();

			convertView.setTag(holder);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
		}
		holder.tv.setText(items.get(position));
		return convertView;
	}

	class ListViewItemHolder {
		TextView tv;
	}
}
