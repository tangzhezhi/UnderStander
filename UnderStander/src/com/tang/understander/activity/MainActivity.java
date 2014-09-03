package com.tang.understander.activity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tang.understander.R;
import com.tang.understander.adapter.CalendarAdapter;
import com.tang.understander.base.BaseActionBarActivity;
import com.tang.understander.entity.TaskCurrentDay;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.cache.CacheManager;

public class MainActivity  extends BaseActionBarActivity  implements OnGestureListener{
	private GestureDetectorCompat gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private String ruzhuTime;
	private String lidianTime;
	private String selectDate;
	private ListView lvTaskCurrentDayList;
	private ArrayList<TaskCurrentDay> taskCurrentDayList = new ArrayList<TaskCurrentDay>();
	private LinearLayout layout_left;
	private LinearLayout layout_right;
	Message m = null;
	
	public MainActivity() {
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    	currentDate = sdf.format(date);  //当期日期
    	year_c = Integer.parseInt(currentDate.split("-")[0]);
    	month_c = Integer.parseInt(currentDate.split("-")[1]);
    	day_c = Integer.parseInt(currentDate.split("-")[2]);
	}
	
	
	private void registerPush(){
		// 1.获取设备Token
				Handler handler = new HandlerExtension(MainActivity.this);
				m = handler.obtainMessage();
				//注册接口
				XGPushManager.registerPush(getApplicationContext(),
						new XGIOperateCallback() {
							@Override
							public void onSuccess(Object data, int flag) {
								Log.w(Constants.LogTag,
										"+++ register push sucess. token:" + data);
								m.obj = "+++ register push sucess. token:" + data;
								m.sendToTarget();
								CacheManager.getRegisterInfo();
							}

							@Override
							public void onFail(Object data, int errCode, String msg) {
								Log.w(Constants.LogTag,
										"+++ register push fail. token:" + data
												+ ", errCode:" + errCode + ",msg:"
												+ msg);

								m.obj = "+++ register push fail. token:" + data
										+ ", errCode:" + errCode + ",msg:" + msg;
								m.sendToTarget();
							}
						});
	}
	
	
	private static class HandlerExtension extends Handler {
		WeakReference<MainActivity> mActivity;

		HandlerExtension(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			MainActivity theActivity = mActivity.get();
			if (msg != null) {
				Log.w(Constants.LogTag, msg.obj.toString()+XGPushConfig.getToken(theActivity));
			}
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		registerPush();
		
		setContentView(R.layout.calendar);
		
		ActionBar bar = getSupportActionBar();
		bar.setTitle(getString(R.string.title));
		//显示在顶部
		bar.setDisplayHomeAsUpEnabled(true);
		
		gestureDetector = new GestureDetectorCompat(this,this);
		
        calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
        addGridView();
        gridView.setAdapter(calV);
        
		topText = (TextView) findViewById(R.id.tv_month);
		
		layout_left = (LinearLayout) findViewById(R.id.btn_prev_month );
		layout_right = (LinearLayout) findViewById(R.id.btn_next_month);
		
		addTextToTopTextView(topText);
		
//		initDayTask();
		
		layout_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				taskCurrentDayList.clear();
//				mAdapter2.notifyDataSetChanged();
	            //向右滑动
				addGridView();   //添加一个gridView
				jumpMonth--;     //上一个月
				calV = new CalendarAdapter(MainActivity.this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		        gridView.setAdapter(calV);
		        addTextToTopTextView(topText);
			}
			
		});
		
		layout_right.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				taskCurrentDayList.clear();
//				mAdapter2.notifyDataSetChanged();
	            //向右滑动
				addGridView();   //添加一个gridView
				jumpMonth++;     //上一个月
				calV = new CalendarAdapter(MainActivity.this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
		        gridView.setAdapter(calV);
		        addTextToTopTextView(topText);
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//添加头部的年份 闰哪月等信息
	public void addTextToTopTextView(TextView view){
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("年").append(
				calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	//添加gridview
		private void addGridView() {
			
			gridView =(GridView)findViewById(R.id.gridview);

			gridView.setOnTouchListener(new OnTouchListener() {
	            //将gridview中的触摸事件回传给gestureDetector
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return MainActivity.this.gestureDetector.onTouchEvent(event);
				}
			});           

			
			gridView.setOnItemClickListener(new OnItemClickListener() {
	            //gridView中的每一个item的点击事件
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					
					  //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
					  int startPosition = calV.getStartPositon();
					  int endPosition = calV.getEndPosition();
					  if(startPosition <= position+7  && position <= endPosition-7){
						  String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历
						  //String scheduleLunarDay = calV.getDateByClickItem(position).split("\\.")[1];  //这一天的阴历
		                  String scheduleYear = calV.getShowYear();
		                  String scheduleMonth = (Integer.valueOf(calV.getShowMonth())>10)?calV.getShowMonth():("0"+calV.getShowMonth());
		                  scheduleDay = (Integer.valueOf(scheduleDay)>10)?scheduleDay:("0"+scheduleDay);
		                  
			              ruzhuTime=scheduleMonth+"月"+scheduleDay+"日";	                  
		                  lidianTime=scheduleMonth+"月"+scheduleDay+"日";       
		                  
		                  selectDate = scheduleYear+"-"+scheduleMonth+"-"+scheduleDay;
		                  
		                  DateTime dateTime = new DateTime(selectDate);
		                  
			                Intent intent=new Intent();
			                intent.addCategory("org.tang.exam.activity.DayHaveStorysActivity");
			                intent.setClass(MainActivity.this, DayHaveStorysActivity.class);
			                intent.putExtra("DayHaveStorysActivity.selectDate", dateTime);
			                startActivity(intent);
		                }
					
				}
				
			});
		}
	
	
	
	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	float velocityY) {
		int gvFlag = 0;         //每次添加gridview到viewflipper中时给的标记
		if (e1.getX() - e2.getX() > 120) {
            //像左滑动
			addGridView();   //添加一个gridView
			jumpMonth++;     //下一个月
			calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
	        gridView.setAdapter(calV);
	        addTextToTopTextView(topText);
	        gvFlag++;
	        
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
            //向右滑动
			addGridView();   //添加一个gridView
			jumpMonth--;     //上一个月
			calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c);
	        gridView.setAdapter(calV);
	        gvFlag++;
	        addTextToTopTextView(topText);

			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

}
