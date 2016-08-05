package com.uphealth.cn.model;

/**
 * @description 食材信息 
 * @data 2016年6月21日

 * @author jun.wang
 */
public class MaterialNutrition {
	
	/**
	 * 纤维素
	 */
	private int cellulose ;
	
	/**
	 * 维生素
	 */
	private int vitamin ;
	
	/**
	 * 蛋白质
	 */
	private int protein ;
	
	/**
	 * 矿物质
	 */
	private int minerals ;

	public int getCellulose() {
		return cellulose;
	}

	public void setCellulose(int cellulose) {
		this.cellulose = cellulose;
	}

	public int getVitamin() {
		return vitamin;
	}

	public void setVitamin(int vitamin) {
		this.vitamin = vitamin;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	@Override
	public String toString() {
		return "MaterialNutrition [cellulose=" + cellulose + ", vitamin="
				+ vitamin + ", protein=" + protein + ", minerals=" + minerals
				+ "]";
	}

}
