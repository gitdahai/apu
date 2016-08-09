package com.download;

import com.download.utils.FileUtils;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDeleteDownloadFileListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;

import java.io.File;

/**
 * Created by Administrator on 2016/8/7.
 */
public class DownloadProccess{
    private String downloadUrl;
    private String savePath;
    private OnDownloadListener downloadListener;

    /**
     *
     * @param downloadUrl
     * @param savePath
     * @param downloadListener
     */
     DownloadProccess(String downloadUrl, String savePath, OnDownloadListener downloadListener){
        this.downloadUrl = downloadUrl;
        this.savePath = savePath;
        this.downloadListener = downloadListener;
    }


    /**
     * 进行下载操作
     */
    void toDownloading(){

        //清除掉不存在的“下载文件”，以便与可以重新下载
        String downloadDir = FileDownloader.getDownloadDir();
        String shortName = FileUtils.getFileShortName(downloadUrl);
        File file = new File(downloadDir + "/" + shortName);

        if (!file.exists())
            FileDownloader.delete(downloadUrl, true, deleteListener);

        //开始下载
        FileDownloader.registerDownloadStatusListener(downloadStatusListener);
        FileDownloader.start(downloadUrl);
    }

    /*==============================================================================================
     * 下载文件被删除事件
     */
    private OnDeleteDownloadFileListener deleteListener = new OnDeleteDownloadFileListener(){
        public void onDeleteDownloadFilePrepared(DownloadFileInfo downloadFileInfo) {
            System.out.println("===onDeleteDownloadFilePrepared===  ");
        }


        public void onDeleteDownloadFileSuccess(DownloadFileInfo downloadFileInfo) {
            System.out.println("===onDeleteDownloadFileSuccess===  ");
        }

        public void onDeleteDownloadFileFailed(DownloadFileInfo downloadFileInfo, DeleteDownloadFileFailReason deleteDownloadFileFailReason) {
            System.out.println("===onDeleteDownloadFileSuccess===  ");
        }
    };


    /*==============================================================================================
     * 文件下载监听器
     */
    private OnFileDownloadStatusListener downloadStatusListener = new OnFileDownloadStatusListener(){
        //等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
        public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {

        }

        // 准备中（即，正在连接资源）
        public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {

        }

        // 已准备好（即，已经连接到了资源）
        public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
            if (downloadListener != null)
                downloadListener.onDownloadStart(downloadUrl,downloadFileInfo.getFileSizeLong() );
        }

        // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
        public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
            if (downloadListener != null)
                downloadListener.onDownloadProgress(downloadFileInfo.getDownloadedSizeLong(), downloadFileInfo.getFileSizeLong(), downloadSpeed);
        }

        // 下载已被暂停
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {

        }

        // 下载完成（整个文件已经全部下载完成）
        public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
            FileDownloader.unregisterDownloadStatusListener(this);

            if (downloadListener != null){
                String savePath = downloadFileInfo != null ? downloadFileInfo.getFilePath() : null;
                downloadListener.onDownloadFinish(true, downloadUrl, savePath);
            }
        }

        // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
        public void onFileDownloadStatusFailed(String s, DownloadFileInfo downloadFileInfo, OnFileDownloadStatusListener.FileDownloadStatusFailReason fileDownloadStatusFailReason) {
            FileDownloader.unregisterDownloadStatusListener(this);

            if (downloadListener != null){
                String savePath = downloadFileInfo != null ? downloadFileInfo.getFilePath() : null;
                downloadListener.onDownloadFinish(false, downloadUrl, savePath);
            }

        }
    };

}
