package com.uphealth.cn.bean;

/**
 * @description 地址位置匹配bean 
 * @data 2016年6月2日

 * @author jun.wang
 */
public class AddressBean {
	
	private String name ;
	
	private String city ;
	
	private String district ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "AddressBean [name=" + name + ", city=" + city + ", district="
				+ district + "]";
	}

}
