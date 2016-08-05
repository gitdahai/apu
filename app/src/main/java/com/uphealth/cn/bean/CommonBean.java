package com.uphealth.cn.bean;

/**
 * 共同的数据适配器
 * @description 
 * @data 2016年7月3日

 * @author jun.wang
 */
public class CommonBean {
	
	private String id ;

	private String name ;
	
	private String url ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CommonBean [id=" + id + ", name=" + name + ", url=" + url + "]";
	}

}
