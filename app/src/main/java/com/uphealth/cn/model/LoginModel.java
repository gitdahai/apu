package com.uphealth.cn.model;


public class LoginModel extends BaseModel{
	
	private String data ;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LoginModel [data=" + data + "]";
	}

}
