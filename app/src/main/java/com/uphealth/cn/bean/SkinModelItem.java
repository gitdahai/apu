package com.uphealth.cn.bean;

import java.util.List;

public class SkinModelItem {
	
	private List<SkinPlansBean> lists ;

	public List<SkinPlansBean> getLists() {
		return lists;
	}

	public void setLists(List<SkinPlansBean> lists) {
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "SkinModelItem [lists=" + lists + "]";
	}
	

}
