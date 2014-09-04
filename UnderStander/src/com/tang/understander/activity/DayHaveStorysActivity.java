package com.tang.understander.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.tang.understander.R;
import com.tang.understander.adapter.TabsAdapter;
import com.tang.understander.base.BaseActionBarActivity;
import com.tang.understander.fragment.BadIdeaFragment;
import com.tang.understander.fragment.HumorFragment;
import com.tang.understander.fragment.NoveltyFragment;
import com.tang.understander.service.UpdateService;

/**
 * 每一天都有故事
 * @author Administrator
 *
 */
public class DayHaveStorysActivity extends BaseActionBarActivity {
	
	private static final String TAG = "DayHaveStorysActivity";
	TabsAdapter mTabsAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(11);
		setContentView(mViewPager);
		
		ActionBar bar = getSupportActionBar();
		bar.setTitle(getString(R.string.title));
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//显示在顶部
		bar.setDisplayHomeAsUpEnabled(true);
		
		Log.d(TAG, "时间：："+getIntent().getSerializableExtra("DayHaveStorysActivity.selectDate"));
		
		
		//在导航上添加上公告页面--ViewPager
		mTabsAdapter = new TabsAdapter(this, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.novelty)), NoveltyFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.humor)), HumorFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.bad_idea)), BadIdeaFragment.class, null);
	}
	
}
