package com.download.events;

import android.text.TextUtils;

import com.download.OnDownloadListener;

/**
 * Created by Administrator on 2016/8/6.
 */
public class DownloadEvent {
    private String downloadUrl;             //下载的地址
    private String savePath;                //默认保存在应用的/data目录下
    private OnDownloadListener listener;    //下载事件监听器

    /**
     * 下载事件对象
     * @param downloadUrl   ： 不能为空
     */
    public DownloadEvent(String downloadUrl){
        if (TextUtils.isEmpty(downloadUrl))
            throw new RuntimeException("download url is null");

        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public OnDownloadListener getListener() {
        return listener;
    }

    public void setListener(OnDownloadListener listener) {
        this.listener = listener;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
