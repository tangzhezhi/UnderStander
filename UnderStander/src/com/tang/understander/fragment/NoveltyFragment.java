package com.tang.understander.fragment;

import java.util.ArrayList;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.tang.understander.R;
import com.tang.understander.adapter.NoveltyListAdapter;
import com.tang.understander.common.AppConstant;
import com.tang.understander.entity.Novelty;
import com.tang.understander.rest.MyStringRequest;
import com.tang.understander.rest.RequestController;
import com.tang.understander.rest.dto.QueryNoveltyReq;
import com.tang.understander.rest.dto.QueryNoveltyResp;
import com.tang.understander.utils.DateTimeUtil;
import com.tang.understander.view.DropDownListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class NoveltyFragment  extends Fragment {
	private static final String TAG = "NoveltyFragment";
	private View mView;
	private NoveltyListAdapter mAdapter;
	private DropDownListView lvNoveltyList;
	private ArrayList<Novelty> noveltyList = new ArrayList<Novelty>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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
		return mView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
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

	
	private void initData() {
		QueryNoveltyReq reqData = new QueryNoveltyReq();
		
		reqData.setCurrentDay(DateTimeUtil.getYmd());

		MyStringRequest req = new MyStringRequest(Method.GET, reqData.getAllUrl(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.v(TAG, "Response: " + response);
						checkResponse(response);
						lvNoveltyList.onDropDownComplete();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						lvNoveltyList.onDropDownComplete();
					}
				});

		RequestController.getInstance().addToRequestQueue(req, TAG);
	}
	
	
	private void checkResponse(String response) {
		try {
			QueryNoveltyResp respData = new QueryNoveltyResp(response);
			if (respData.getMsgFlag()==AppConstant.novelty_query_success) {
				doSuccess(respData);
			} 
			else{
				Toast.makeText(getActivity(), "解析错误", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "Failed to parser response data! \r\n" + e);
		}
	}
	
	private void doSuccess(QueryNoveltyResp respData) {
		if(noveltyList!=null && noveltyList.size() > 0){
			noveltyList.clear();
		}
		
		noveltyList.addAll(0, respData.getResponse());
//		NoveltyDBAdapter dbAdapter = new NoveltyDBAdapter();
		try {
//			dbAdapter.open();
//			dbAdapter.addServiceNovelty(respData.getResponse());

		} catch (Exception e) {
			Log.e(TAG, "Failed to operate database: " + e);
		} finally {
//			dbAdapter.close();
		}
	}

	
}
