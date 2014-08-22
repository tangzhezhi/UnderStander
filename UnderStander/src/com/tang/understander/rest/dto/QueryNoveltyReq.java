package com.tang.understander.rest.dto;

import java.util.HashMap;

import com.tang.understander.common.AppConstant;
import com.tang.understander.rest.BaseRequest;


public class QueryNoveltyReq extends BaseRequest {
	
	private String tag;
	private String currentDay;
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}

	@Override
	public String getPath() {
		return AppConstant.BASE_URL + "mobile/queryNovelty";
	}

	@Override
	public HashMap<String, String> toParamsMap() {
		HashMap<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("tag", this.tag);
		paramsHashMap.put("currentDay", this.currentDay);
		return paramsHashMap;
	}

}
