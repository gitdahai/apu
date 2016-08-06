package com.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.download.events.DownloadEvent;
import com.google.common.eventbus.Subscribe;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;

import java.io.File;

/**
 * Created by Administrator on 2016/8/6.
 * 下载服务
 */
public class DownloadService extends Service {
    public static boolean isStarted;

    @Override
    public void onCreate() {
        super.onCreate();
        initDownloadConfig();
        FileDownloadManager.getEventBus().register(this);
        isStarted = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileDownloadManager.onDownloadServiceStarted();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileDownloader.release();
        isStarted = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**=============================================================================================
     * 初始化下载器
     */
    private void initDownloadConfig(){
        // 1、创建Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "FileDownloader");
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(true);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒
        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);
    }

    /**
     * 接收一个事件，开始下载
     * @param event
     */
    @Subscribe
    public void onDownloadEvent(DownloadEvent event){
        if (event != null){
            DownloadProccess downloadProccess = new DownloadProccess(event.getDownloadUrl(), event.getSavePath(), event.getListener());
            downloadProccess.toDownloading();
        }
    }

    /**#############################################################################################
     * 文件下载进程
     */
    private class DownloadProccess implements OnFileDownloadStatusListener {
        private String downloadUrl;
        private String savePath;
        private OnDownloadListener downloadListener;

        /**
         *
         * @param downloadUrl
         * @param savePath
         * @param downloadListener
         */
        private DownloadProccess(String downloadUrl, String savePath, OnDownloadListener downloadListener){
            this.downloadUrl = downloadUrl;
            this.savePath = savePath;
            this.downloadListener = downloadListener;
        }

        /**
         * 进行下载操作
         */
        private void toDownloading(){
            FileDownloader.registerDownloadStatusListener(this);
            FileDownloader.start(downloadUrl);
        }

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
            if (downloadListener != null)
                downloadListener.onDownloadFinish(true, downloadUrl,downloadFileInfo.getFilePath());
        }

        // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
        public void onFileDownloadStatusFailed(String s, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason fileDownloadStatusFailReason) {
            FileDownloader.unregisterDownloadStatusListener(this);
            if (downloadListener != null)
                downloadListener.onDownloadFinish(false, downloadUrl,downloadFileInfo.getFilePath());
        }
    }
}
