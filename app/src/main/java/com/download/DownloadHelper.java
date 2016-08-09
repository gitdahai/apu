package com.download;

import android.content.Context;
import android.os.Environment;

import com.download.events.DownloadEvent;

import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;

import java.io.File;

/**
 * Created by Administrator on 2016/8/7.
 */
public class DownloadHelper {
    private static DownloadHelper instance;

    private DownloadHelper(Context context){
        // 1、创建Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(context.getApplicationContext());
        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "apu");
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        //builder.configDebugMode(true);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒
        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);
    }

    public static void init(Context context){
        if (instance == null)
            instance = new DownloadHelper(context);
    }

    /**
     *
     * @param event
     */
    public static void onDownloadEvent(DownloadEvent event){
        if (instance == null)
            throw new RuntimeException("FileDownloadManager 没有进行初始化");

        if (event != null){
            DownloadProccess downloadProccess = new DownloadProccess(event.getDownloadUrl(), event.getSavePath(), event.isUpdate() ,event.getListener());
            downloadProccess.toDownloading();
        }
    }
}
