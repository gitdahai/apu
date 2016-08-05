package com.uphealth.cn.model;

import java.util.List;

public class SportPlansModel {
	
	private List<SportVideoModel> videos ;

	public List<SportVideoModel> getVideos() {
		return videos;
	}

	public void setVideos(List<SportVideoModel> videos) {
		this.videos = videos;
	}

	@Override
	public String toString() {
		return "SportPlansModel [videos=" + videos + "]";
	}

}
