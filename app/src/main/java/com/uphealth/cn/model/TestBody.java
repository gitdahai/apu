package com.uphealth.cn.model;

import java.util.List;

/**
 * @description 体质测试 
 * @data 2016年6月23日

 * @author jun.wang
 */
public class TestBody {
	
	private String name ;
	
	private int type ;
	
	private List<TestBodyItem> items ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<TestBodyItem> getItems() {
		return items;
	}

	public void setItems(List<TestBodyItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "TestBody [name=" + name + ", type=" + type + ", items=" + items
				+ "]";
	}

}
