package com.uphealth.cn.model;

public class SportToolsModel {
	
	private String image ;
	
	private String name ;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SportToolsModel [image=" + image + ", name=" + name + "]";
	}
	

}
