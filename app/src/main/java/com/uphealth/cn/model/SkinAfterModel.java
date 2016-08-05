package com.uphealth.cn.model;

/**
 * 发布美妆
 * @description 
 * @data 2016年7月17日

 * @author jun.wang
 */
public class SkinAfterModel {
	
	private String topicId ;
	
	private String images ;

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "SkinAfterModel [topicId=" + topicId + ", images=" + images
				+ "]";
	}

}
