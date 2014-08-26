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
	private String updatetime;
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
	
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
