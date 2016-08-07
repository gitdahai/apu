package com.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.download.events.DownloadEvent;

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
        //FileDownloadManager.getEventBus().register(this);
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
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "apu");
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
    public void onDownloadEvent(DownloadEvent event){
        if (event != null){
            DownloadProccess downloadProccess = new DownloadProccess(event.getDownloadUrl(), event.getSavePath(), event.getListener());
            downloadProccess.toDownloading();
        }
    }

}
