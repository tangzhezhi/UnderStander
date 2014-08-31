package com.tang.understander.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tang.understander.R;
import com.tang.understander.adapter.NoveltyListAdapter;
import com.tang.understander.common.AppConstant;
import com.tang.understander.common.AppConstant.PullType;
import com.tang.understander.entity.Novelty;
import com.tang.understander.rest.dto.NoveltyDTO;
import com.tang.understander.rest.dto.PageDTO;
import com.tang.understander.view.DropDownListView;
import com.tang.understander.view.DropDownListView.OnDropDownListener;


public class NoveltyFragment  extends Fragment {
	private static final String TAG = "NoveltyFragment";
	private View mView;
	private NoveltyListAdapter mAdapter;
	private DropDownListView lvNoveltyList;
	private LinkedList<Novelty> noveltyList = new LinkedList<Novelty>();
	RequestQueue requestQueue;
	private DateTime currentDay = new DateTime();
	private	ArrayList<Novelty> nlist = new ArrayList<Novelty>();
	private int scrolledX;
	private int scrolledY;
	private ViewGroup viewGroup;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //初始化
        requestQueue = Volley.newRequestQueue(getActivity());
		setHasOptionsMenu(true);
		
		if(getActivity().getIntent()!=null){
			Intent intent = getActivity().getIntent();
			DateTime paramDay = (DateTime) intent.getSerializableExtra("DayHaveStorysActivity.selectDate");
			
			if(paramDay!=null && !paramDay.equals("")){
				currentDay = paramDay;
			}
		}
		
	}
	
	 @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
     }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case android.R.id.home:
 			getActivity().finish();
 			break;
		}
		return true;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list, container, false);
		this.viewGroup = container;
		return mView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			initData();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
    @Override
	public void onStop() {
        super.onStop();
    }
		
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public static NoveltyFragment newInstance() {
		NoveltyFragment newFragment = new NoveltyFragment();
		return newFragment;
	}

	
	private void initData() throws UnsupportedEncodingException {
		noveltyList.clear();
		showMsgUi();
		postRequest(PullType.No);
	}
	
	private void postRequest(final int pullType) throws UnsupportedEncodingException{
		
		Map<String,String> m = new HashMap<String,String>();
		
		 Gson gson = new Gson();
		 NoveltyDTO dto = new NoveltyDTO();
         dto.setEntityType("query_novelty");
         dto.setTag("Novelty");
         
         String upatetime = currentDay.toString("yyyyMMdd");
         
         if(upatetime.length() > 8){
        	 upatetime = upatetime.substring(0, 8);
         }
         
         dto.setUpdateTime(upatetime);
       
         String requeststr = gson.toJson(dto);
		 m.put("content", requeststr);
		
		 RequestParams params = new RequestParams();
		 params.setContentEncoding("utf-8");
		 
		 params.put("content", requeststr);
		 
		 AsyncHttpClient client = new AsyncHttpClient();
		 StringEntity stringEntity = new StringEntity(requeststr);  
		 client.post(getActivity(),AppConstant.BASE_URL, stringEntity, "application/json", new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				super.onSuccess(statusCode, headers, response);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if(response!=null){
					Gson json = new Gson();
					PageDTO d =  json.fromJson(response.toString(), PageDTO.class);
					if(d!=null){
						
						nlist = (ArrayList<Novelty>) json.fromJson(json.toJson(d.getData()),  
								new TypeToken<List<Novelty>>() {}.getType());
						
						
						int currentPosition = lvNoveltyList.getFirstVisiblePosition();
						
						if(nlist.size() > 0){
							if(pullType == PullType.Down){
								noveltyList.addLast(nlist.get(0));
							}
							else if(pullType == PullType.Up){
								noveltyList.addFirst(nlist.get(0));
							}
							else{
								noveltyList.addLast(nlist.get(0));
							}
						}
						
						mAdapter = new NoveltyListAdapter(mView.getContext(), noveltyList);
						lvNoveltyList.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
					}

				}
				
				
				if(pullType == PullType.Down){
					lvNoveltyList.onDropDownComplete();
				}
				else if(pullType == PullType.Up){
					lvNoveltyList.onBottomComplete();
					
				}
				else{
					lvNoveltyList.onBottomComplete();
					lvNoveltyList.onDropDownComplete();
				}
				
				

				
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				super.onSuccess(statusCode, headers, responseString);

			}

			@Override
			protected Object parseResponse(byte[] responseBody)
					throws JSONException {
				return super.parseResponse(responseBody);
			}
			 
		});
		 
		 
	}
	
	
	private void showMsgUi(){
		lvNoveltyList = (DropDownListView) mView.findViewById(R.id.lv_list);
		mAdapter = new NoveltyListAdapter(mView.getContext(), noveltyList);
		lvNoveltyList.setAdapter(mAdapter);
		lvNoveltyList.setSelected(true);
		lvNoveltyList.setOnDropDownListener(new OnDropDownListener() {
			@Override
			public void onDropDown() {
				Log.d(TAG, "下拉点击");
				try {
					currentDay = currentDay.minusDays(1);
					postRequest(PullType.Down);
					
					if(nlist.size()==0){
						lvNoveltyList.setHeaderDefaultText(getString(R.string.click_down_list_header_no_more_text));
						lvNoveltyList.onDropDownComplete();
						return;
					}
					lvNoveltyList.setSelection(lvNoveltyList.getFirstVisiblePosition());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}});
		
	   
		lvNoveltyList.setOnBottomListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					currentDay = currentDay.plusDays(1);
					postRequest(PullType.Up);
					if(nlist.size()==0){
						lvNoveltyList.setFooterDefaultText(getString(R.string.drop_down_list_footer_no_more_text));
						lvNoveltyList.onBottomComplete();
						return;
					}
					
					Log.d(TAG, "lvNoveltyList.getCount:::"+lvNoveltyList.getCount());
					
					mAdapter.notifyDataSetChanged();
					 lvNoveltyList.setSelection(lvNoveltyList.getCount()-1);
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		
		
//		lvNoveltyList.setOnScrollListener(new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView view, int  scrollState) {
//				// 不滚动时保存当前滚动到的位置
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					if (noveltyList != null) {
//						scrolledX = view.getScrollX();
//						scrolledY = lvNoveltyList.getChildAt(0).getTop();
//						Log.d(TAG, "getScrollY:::"+lvNoveltyList.getChildAt(0).getTop());
//					}
//				}
//			}
//			
//			@Override
//			public void onScroll(AbsListView view,  int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				
//			}
//		});

	}

	
}
