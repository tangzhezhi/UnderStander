package com.tang.understander.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tang.understander.R;
import com.tang.understander.entity.Novelty;
import com.tang.understander.utils.DateTimeUtil;


public class NoveltyListAdapter  extends BaseAdapter {
	private static final String TAG = "NoveltyListAdapter";
	
	private LayoutInflater mInflater;
	private LinkedList<Novelty> mNoveltyList = new LinkedList<Novelty>();

	public NoveltyListAdapter(Context context, LinkedList<Novelty> noticeList) {
		super();
		if(noticeList!=null && noticeList.size() > 0){
			mNoveltyList = noticeList;
		}
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mNoveltyList.size();
	}

	@Override
	public Object getItem(int pos) {
		return mNoveltyList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_novelty, null);

			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Novelty novelty = mNoveltyList.get(pos);
		if (novelty != null) {
			holder.tvTitle.setText(novelty.getTitle());
			holder.tvContent.setText(novelty.getContent());
		}
		
		return convertView;
	}

	private final class ViewHolder {
		public TextView tvTime;
		public TextView tvTitle;
		public TextView tvContent;
	}

}
