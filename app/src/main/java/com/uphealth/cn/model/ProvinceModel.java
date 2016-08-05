package com.uphealth.cn.model;

/**
 * @description 二级省市 
 * @data 2016年6月14日

 * @author jun.wang
 */
public class ProvinceModel {
	
	private String id ;
	
	private String name ;

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

	@Override
	public String toString() {
		return "ProvinceModel [id=" + id + ", name=" + name + "]";
	}

}
