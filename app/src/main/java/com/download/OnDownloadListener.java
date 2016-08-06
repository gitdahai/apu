package com.download;

/**
 * Created by Administrator on 2016/8/6.
 * 下载事件监听器
 */
public interface OnDownloadListener {
    /**
     * 开始下载
     * @param downloadUrl       ：下载文件地址url
     * @param downloadFileSize  ；下载的文件大小
     */
    public void onDownloadStart(String downloadUrl, long downloadFileSize);

    /**
     * 文件下载进度
     * @param currentSize       ：当前已经下载大小
     * @param totalSize         ：总的文件大小
     * @param downloadSpeed     ：下载的数据（单位是：KB/秒）
     */
    public void onDownloadProgress(long currentSize, long totalSize, float downloadSpeed);

    /**
     * 下载完成
     * @param isFinish      ：如果正常完成，则为true,否则为false
     * @param downloadUrl   : 下载文件的地址
     * @param savePath      : 下载文件的保存地址
     */
    public void onDownloadFinish(boolean isFinish, String downloadUrl, String savePath);
}
