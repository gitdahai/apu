package com.uphealth.cn.ui.login.eat;

public class FakeMember {
	private int iconId;
	private boolean isOnline;
	
	public FakeMember(int iconId, boolean isOnline) {
		super();
		this.iconId = iconId;
		this.isOnline = isOnline;
	}

	@Override
	public String toString() {
		return "FakeMember [iconId=" + iconId + ", isOnline=" + isOnline + "]";
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
