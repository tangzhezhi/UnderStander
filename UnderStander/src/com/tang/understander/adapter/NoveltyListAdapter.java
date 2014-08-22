package com.tang.understander.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tang.understander.R;
import com.tang.understander.entity.Novelty;
import com.tang.understander.utils.DateTimeUtil;


public class NoveltyListAdapter  extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Novelty> mNoveltyList = new ArrayList<Novelty>();

	public NoveltyListAdapter(Context context, ArrayList<Novelty> noticeList) {
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
			holder.tvTime = (TextView) convertView.findViewById(R.id.tv_create_time);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Novelty novelty = mNoveltyList.get(pos);
		if (novelty != null) {
			holder.tvTime.setText(DateTimeUtil.toStandardTime(novelty.getUpdateTime()));
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
