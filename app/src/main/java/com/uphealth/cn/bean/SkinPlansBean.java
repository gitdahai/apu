package com.uphealth.cn.bean;

/**
 * 美妆bean
 * @description 
 * @data 2016年7月21日

 * @author jun.wang
 */
public class SkinPlansBean {
	
	private String id ;
	
	private String name ;
	
	private String image ;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "SkinPlansBean [id=" + id + ", name=" + name + ", image="
				+ image + "]";
	}

}
