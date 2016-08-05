package com.uphealth.cn.model;

/**
 * @description 资讯列表 
 * @data 2016年6月18日

 * @author jun.wang
 */
public class ArticleModel {
	
	private String tags ;
	
	private String id ;
	
	private String icon ;
	
	private String name ;

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ArticleModel [tags=" + tags + ", id=" + id + ", icon=" + icon
				+ ", name=" + name + "]";
	}

}
