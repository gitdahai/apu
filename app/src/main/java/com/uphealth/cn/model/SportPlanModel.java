package com.uphealth.cn.model;

import java.util.List;

/**
 * @description 运动方案列表 
 * @data 2016年6月18日

 * @author jun.wang
 */
public class SportPlanModel {
	
	private String tags ;
	
	private String id ;
	
	private String isInstrument ;
	
	private String cardImage ;
	
	private String level ;
	
	private String name ;
	
	private String topicId ;
	
	private String scene ;
	
	private String type ;
	
	private List<SportToolsModel> tools ;
	
	private List<SportPlansModel> plans ; 

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

	public String getIsInstrument() {
		return isInstrument;
	}

	public void setIsInstrument(String isInstrument) {
		this.isInstrument = isInstrument;
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
	
	public List<SportToolsModel> getTools() {
		return tools;
	}

	public void setTools(List<SportToolsModel> tools) {
		this.tools = tools;
	}

	public List<SportPlansModel> getPlans() {
		return plans;
	}

	public void setPlans(List<SportPlansModel> plans) {
		this.plans = plans;
	}

	@Override
	public String toString() {
		return "SportPlanModel [tags=" + tags + ", id=" + id
				+ ", isInstrument=" + isInstrument + ", cardImage=" + cardImage
				+ ", level=" + level + ", name=" + name + ", topicId="
				+ topicId + ", scene=" + scene + ", type=" + type + ", tools="
				+ tools + ", plans=" + plans + "]";
	}

}
