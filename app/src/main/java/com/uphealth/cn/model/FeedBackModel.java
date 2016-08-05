package com.uphealth.cn.model;

/**
 * 意见反馈列表
 * @description 
 * @data 2016年7月18日

 * @author jun.wang
 */
public class FeedBackModel {
	
	private String content ;
	
	private String id ;
	
	private String createtor ;
	
	private String createtorPicUrl ;
	
	private String images ;
	
	private String createDate ;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatetor() {
		return createtor;
	}

	public void setCreatetor(String createtor) {
		this.createtor = createtor;
	}

	public String getCreatetorPicUrl() {
		return createtorPicUrl;
	}

	public void setCreatetorPicUrl(String createtorPicUrl) {
		this.createtorPicUrl = createtorPicUrl;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "FeedBackModel [content=" + content + ", id=" + id
				+ ", createtor=" + createtor + ", createtorPicUrl="
				+ createtorPicUrl + ", images=" + images + ", createDate="
				+ createDate + "]";
	}

}
