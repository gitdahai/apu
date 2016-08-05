package com.uphealth.cn.model;

/**
 * 热门话题
 * @description 
 * @data 2016年7月24日

 * @author jun.wang
 */
public class HottestReplyModel {
	
	private String id ;
	
	private String name ;
	
	private String image ;
	
	private String readNum ;
	
	private String remarks ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "HottestReplyModel [id=" + id + ", name=" + name + ", image="
				+ image + ", readNum=" + readNum + ", remarks=" + remarks + "]";
	}

}
