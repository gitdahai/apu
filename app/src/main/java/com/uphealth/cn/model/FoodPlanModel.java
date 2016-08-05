package com.uphealth.cn.model;

import java.util.List;

/**
 * @description 饮食方案 
 * @data 2016年6月18日

 * @author jun.wang
 */
public class FoodPlanModel {
	
	private String id ;
	
	private String name ;
	
	private String cardImage ;
	
	private String topicId ;
	
	private String tags ;
	
	private String itemName ;  // 接口拆分的标签
	
	private List<FoodPlanItemModel> plans ;

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

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<FoodPlanItemModel> getPlans() {
		return plans;
	}

	public void setPlans(List<FoodPlanItemModel> plans) {
		this.plans = plans;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "FoodPlanModel [id=" + id + ", name=" + name + ", cardImage="
				+ cardImage + ", topicId=" + topicId + ", tags=" + tags
				+ ", itemName=" + itemName + ", plans=" + plans + "]";
	}

}
