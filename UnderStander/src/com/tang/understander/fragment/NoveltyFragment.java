package com.tang.understander.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tang.understander.R;
import com.tang.understander.adapter.NoveltyListAdapter;
import com.tang.understander.common.AppConstant;
import com.tang.understander.entity.Novelty;
import com.tang.understander.rest.GsonRequest;
import com.tang.understander.rest.dto.NoveltyDTO;
import com.tang.understander.utils.DateTimeUtil;
import com.tang.understander.view.DropDownListView;


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
		postRequest();
	}
	
	
	private void postRequest(){
		
		Map<String,String> m = new HashMap<String,String>();
		
		 Gson gson = new Gson();
		 NoveltyDTO dto = new NoveltyDTO();
         dto.setEntityType("Novelty");
         dto.setTag("Novelty");
         dto.setUpdateTime(DateTimeUtil.getYmd());
         String requeststr = gson.toJson(dto);
		 m.put("content", requeststr);
		
		
		GsonRequest<Novelty> gsonRequest = new GsonRequest<Novelty>(  
				AppConstant.BASE_URL, Novelty.class, m,
		         new Response.Listener<Novelty>() {  
		            @Override  
		            public void onResponse(Novelty novelty) {  
//		                WeatherInfo weatherInfo = weather.getWeatherinfo();  
//		                Log.d("TAG", "city is " + weatherInfo.getCity());  
//		                Log.d("TAG", "temp is " + weatherInfo.getTemp());  
//		                Log.d("TAG", "time is " + weatherInfo.getTime());  
		            }  
		        }, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                Log.e("TAG", error.getMessage(), error);  
		            }  
		        });  
		requestQueue.add(gsonRequest);  
	}

	
}
