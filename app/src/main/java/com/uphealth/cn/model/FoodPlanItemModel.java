package com.uphealth.cn.model;

/**
 * @description 饮食方案item 
 * @data 2016年6月18日

 * @author jun.wang
 */
public class FoodPlanItemModel {
	
	// 饮食方案根据plans对接口数据做了拆分 取的是菜品信息Food
	
	private String id ;
	
	private String name ;
	
	private String icon ;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "FoodPlanItemModel [id=" + id + ", name=" + name + ", icon="
				+ icon + "]";
	}

}
