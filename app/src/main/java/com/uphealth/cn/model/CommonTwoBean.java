package com.uphealth.cn.model;

public class CommonTwoBean {
	
    private String id ;
	
	private String name ;
	
	private String icon ;
	
	private boolean isChoose ;

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
	
	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	@Override
	public String toString() {
		return "CommonTwoBean [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", isChoose=" + isChoose + "]";
	}
	
}
