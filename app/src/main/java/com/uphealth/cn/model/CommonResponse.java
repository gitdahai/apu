package com.uphealth.cn.model;

/**
 * @description 最基础的通用返回数据类型 
 * @data 2016年6月24日

 * @author jun.wang
 */
public class CommonResponse {
	
	private String data ;
	
	private String reason ;
	
	private int result ;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CommonResponse [data=" + data + ", reason=" + reason
				+ ", result=" + result + "]";
	}

}
