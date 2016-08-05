package com.uphealth.cn.model;

/**
 * @description 测试题库的分数 
 * @data 2016年6月23日

 * @author jun.wang
 */
public class TestBodyItem {
	
	private int score ;
	
	private String label ;
	
	private boolean isChoose ;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "TestBodyItem [score=" + score + ", label=" + label
				+ ", isChoose=" + isChoose + "]";
	}

}
