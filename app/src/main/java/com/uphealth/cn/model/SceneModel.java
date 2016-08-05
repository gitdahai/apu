package com.uphealth.cn.model;

/**
 * @description 运动场景 
 * @data 2016年6月18日

 * @author jun.wang
 */
public class SceneModel {
	
	private String id ;
	
	private String value ;
	
	private String label ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "SceneModel [id=" + id + ", value=" + value + ", label=" + label
				+ "]";
	}

}
