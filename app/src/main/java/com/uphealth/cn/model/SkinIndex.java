package com.uphealth.cn.model;

/**
 * 美妆四组指数
 * @description 
 * @data 2016年7月22日

 * @author jun.wang
 */
public class SkinIndex {

	private int skinAcne ;
	
	private int skinWater ;
	
	private int skinAntiAging ;
	
	private int skinWhite ;

	public int getSkinAcne() {
		return skinAcne;
	}

	public void setSkinAcne(int skinAcne) {
		this.skinAcne = skinAcne;
	}

	public int getSkinWater() {
		return skinWater;
	}

	public void setSkinWater(int skinWater) {
		this.skinWater = skinWater;
	}

	public int getSkinAntiAging() {
		return skinAntiAging;
	}

	public void setSkinAntiAging(int skinAntiAging) {
		this.skinAntiAging = skinAntiAging;
	}

	public int getSkinWhite() {
		return skinWhite;
	}

	public void setSkinWhite(int skinWhite) {
		this.skinWhite = skinWhite;
	}

	@Override
	public String toString() {
		return "SkinIndex [skinAcne=" + skinAcne + ", skinWater=" + skinWater
				+ ", skinAntiAging=" + skinAntiAging + ", skinWhite="
				+ skinWhite + "]";
	}
	
}
