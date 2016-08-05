package com.uphealth.cn.model;

/**
 * 收藏列表
 * @description 
 * @data 2016年7月10日

 * @author jun.wang
 */
public class CollectModel {
	
	private String id ;
	
	private String topicId ;
	
	private String image ;
	
	private String remarks ;
	
	private String type ;
	
	private String topicName ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public String toString() {
		return "CollectModel [id=" + id + ", topicId=" + topicId + ", image="
				+ image + ", remarks=" + remarks + ", type=" + type
				+ ", topicName=" + topicName + "]";
	}
	
	

}
