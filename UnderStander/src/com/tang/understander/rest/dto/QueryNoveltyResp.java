package com.tang.understander.rest.dto;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tang.understander.common.AppConstant;
import com.tang.understander.entity.Novelty;

public class QueryNoveltyResp  {
	private int msgFlag;
	public int getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(int msgFlag) {
		this.msgFlag = msgFlag;
	}

	private ArrayList<Novelty> response;
	
	public ArrayList<Novelty> getResponse() {
		return response;
	}

	public void setResponse(ArrayList<Novelty> response) {
		this.response = response;
	}

	public QueryNoveltyResp(String jsonStr) throws JSONException {
		parseResponseData(jsonStr);
	}
	
	@SuppressWarnings("unchecked")
	private void parseResponseData(String jsonStr) throws JSONException {
		Gson json = new Gson();
		QueryNoveltyResp d =  json.fromJson(jsonStr, QueryNoveltyResp.class);

		if(d!=null && d.getMsgFlag()==AppConstant.novelty_query_success){
			this.msgFlag = d.getMsgFlag();
			this.response = (ArrayList<Novelty>) json.fromJson(json.toJson(d.getResponse()),  
					new TypeToken<List<Novelty>>() {}.getType());
		}

	}
	

}
