package com.uphealth.cn.bean;

/**
 * @description 第三方登录信息 
 * @data 2016年6月21日

 * @author jun.wang
 */
public class ThirdBean {
	
	private String name ;
	
	private String picUrl ;
	
	private String openid ;
	
	private String gender ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "ThirdBean [name=" + name + ", picUrl=" + picUrl + ", openid="
				+ openid + ", gender=" + gender + "]";
	}

}
