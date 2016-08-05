package com.uphealth.cn.bean;

/**
 * 视频与音频资源
 * @description 
 * @data 2016年7月5日

 * @author jun.wang
 */
public class VideoBean {
	
	/**
	 * 视频Url
	 */
	private String videoUrl ;

	/**
	 * 音频Url
	 */
	private String voiceUrl ;
	
	/**
	 * 视频长度
	 */
	private int length ;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "VideoBean [videoUrl=" + videoUrl + ", voiceUrl=" + voiceUrl
				+ ", length=" + length + "]";
	}
	
	
}
