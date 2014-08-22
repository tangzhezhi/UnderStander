package com.tang.understander.entity;

import java.io.Serializable;

public class Novelty implements Serializable {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4738867043437884642L;
	private String id;
	private String title;
	private String content;
	private String updateTime;
	private String tag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
