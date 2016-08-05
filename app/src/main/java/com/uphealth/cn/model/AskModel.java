package com.uphealth.cn.model;

/**
 * 健康问阿噗
 * @description 
 * @data 2016年7月9日

 * @author jun.wang
 */
public class AskModel {
	
	private String id ;
	
	private String icon ;
	
	private String name ;
	
	private String topicId ;
	
	private String remarks ;
	
	private String createDate ;
	
	private String topicName ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public String toString() {
		return "AskModel [id=" + id + ", icon=" + icon + ", name=" + name
				+ ", topicId=" + topicId + ", remarks=" + remarks
				+ ", createDate=" + createDate + ", topicName=" + topicName
				+ "]";
	}

}
