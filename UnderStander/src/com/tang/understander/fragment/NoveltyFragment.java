package com.tang.understander.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tang.understander.R;
import com.tang.understander.adapter.NoveltyListAdapter;
import com.tang.understander.common.AppConstant;
import com.tang.understander.entity.Novelty;
import com.tang.understander.rest.dto.NoveltyDTO;
import com.tang.understander.rest.dto.PageDTO;
import com.tang.understander.utils.DateTimeUtil;
import com.tang.understander.view.DropDownListView;
import com.tang.understander.view.DropDownListView.OnDropDownListener;


public class NoveltyFragment  extends Fragment {
	private static final String TAG = "NoveltyFragment";
	private View mView;
	private NoveltyListAdapter mAdapter;
	private DropDownListView lvNoveltyList;
	private ArrayList<Novelty> noveltyList = new ArrayList<Novelty>();
	RequestQueue requestQueue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //初始化
        requestQueue = Volley.newRequestQueue(getActivity());
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
		showMsgUi();
		postRequest();
	}
	
	private void postRequest() throws UnsupportedEncodingException{
		
		Map<String,String> m = new HashMap<String,String>();
		
		 Gson gson = new Gson();
		 NoveltyDTO dto = new NoveltyDTO();
         dto.setEntityType("query_novelty");
         dto.setTag("Novelty");
         dto.setUpdateTime(DateTimeUtil.getYmd());
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
						noveltyList = (ArrayList<Novelty>) json.fromJson(json.toJson(d.getData()),  
								new TypeToken<List<Novelty>>() {}.getType());
						
						mAdapter = new NoveltyListAdapter(mView.getContext(), noveltyList);
						lvNoveltyList.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
					}

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
		 
		 
//		GsonRequest<Novelty> gsonRequest = new GsonRequest<Novelty>(  
//				AppConstant.BASE_URL, Novelty.class, m,
//		         new Response.Listener<Novelty>() {  
//		            @Override  
//		            public void onResponse(Novelty novelty) {  
//		            	 Log.d("NoveltyFragment", "return msg::: ");  
////		                WeatherInfo weatherInfo = weather.getWeatherinfo();  
////		                Log.d("TAG", "city is " + weatherInfo.getCity());  
////		                Log.d("TAG", "temp is " + weatherInfo.getTemp());  
////		                Log.d("TAG", "time is " + weatherInfo.getTime());  
//		            }  
//		        }, new Response.ErrorListener() {  
//		            @Override  
//		            public void onErrorResponse(VolleyError error) {  
//		                Log.e("TAG", error.getMessage(), error);  
//		            }  
//		        });  
//		requestQueue.add(gsonRequest);  
	}
	
	
	private void showMsgUi(){
		lvNoveltyList = (DropDownListView) mView.findViewById(R.id.lv_list);
		mAdapter = new NoveltyListAdapter(mView.getContext(), noveltyList);
		lvNoveltyList.setAdapter(mAdapter);
		lvNoveltyList.setOnDropDownListener(new OnDropDownListener() {
			@Override
			public void onDropDown() {
				Log.d(TAG, "下拉点击");
			}});
		
		mAdapter.notifyDataSetChanged();
	}

	
}
