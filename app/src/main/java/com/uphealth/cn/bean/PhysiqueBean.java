package com.uphealth.cn.bean;

/**
 * @description 体质数据
 * @data 2016年7月2日

 * @author jun.wang
 */
public class PhysiqueBean {
	
	private String name ;
	
	private int rank ;

	public PhysiqueBean(String name , int rank){
		this.name = name ;
		this.rank = rank ;
	} ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "PhysiqueBean [name=" + name + ", rank=" + rank + "]";
	}

}
