package com.uphealth.cn.bean;

/**
 * @description 城市选择省份 
 * @data 2016年6月16日

 * @author jun.wang
 */
public class ProvinceBean {
	
	private String ProID ;
	
	private String name ;
	
	private String ProSort ;
	
	public String getProID() {
		return ProID;
	}

	public void setProID(String proID) {
		ProID = proID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProSort() {
		return ProSort;
	}

	public void setProSort(String proSort) {
		ProSort = proSort;
	}

	@Override
	public String toString() {
		return "ProvinceBean [ProID=" + ProID + ", name=" + name + ", ProSort="
				+ ProSort + "]";
	}
	

}
