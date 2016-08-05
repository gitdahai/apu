package com.uphealth.cn.model;

/**
 * @description 接口回调信息基类 
 * @data 2016年6月13日

 * @author jun.wang
 */
public class BaseModel {
	
	/**
	 * 提示信息
	 */
	private String reason ;
	
	/**
	 * 返回接口判断 1成功  0失败
	 */
	private String result ;
	
	/**
	 * 扩展码
	 */
	private String code ;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "BaseModel [reason=" + reason + ", result=" + result + ", code="
				+ code + "]";
	}

}
