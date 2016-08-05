package com.uphealth.cn.model;

import com.uphealth.cn.bean.SkinModelItem;

import java.util.List;

/**
 * 护肤方案列表
 * @description 
 * @data 2016年7月3日

 * @author jun.wang
 */
public class SkinModel {
	
	private String tags ;
	
	private String id ;
	
	private String cardImage ;
	
	private String level ;
	
	private String name ;
	
	private String topicId ;
	
	private String scene ;
	
	private String type ;
	
	// 过度数组
	private List<SkinModelItem> plans ; 

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

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SkinModelItem> getPlans() {
		return plans;
	}

	public void setPlans(List<SkinModelItem> plans) {
		this.plans = plans;
	}

	@Override
	public String toString() {
		return "SkinModel [tags=" + tags + ", id=" + id + ", cardImage="
				+ cardImage + ", level=" + level + ", name=" + name
				+ ", topicId=" + topicId + ", scene=" + scene + ", type="
				+ type + ", plans=" + plans + "]";
	}
	


}
