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
	
	UpdateService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {            
            Log.i(TAG, "--Service Disconnected--");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "--Service Connected--");
            // 取得Service对象中的Binder对象
            binder = (UpdateService.MyBinder) service;
        }
    };
	
	
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
		
        final Intent intent = new Intent();
        // 指定开启服务的action
        intent.setAction("com.tang.understander.activity.DayHaveStorysActivity");
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		
		//在导航上添加上公告页面--ViewPager
		mTabsAdapter = new TabsAdapter(this, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.novelty)), NoveltyFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.humor)), HumorFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(getString(R.string.bad_idea)), BadIdeaFragment.class, null);
	}
	
}
