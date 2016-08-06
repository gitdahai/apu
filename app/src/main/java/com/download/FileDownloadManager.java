package com.download;

import android.content.Context;
import android.content.Intent;

import com.download.events.DownloadEvent;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 * 文件下载管理器
 */
public class FileDownloadManager {
    private static FileDownloadManager instance;
    private EventBus mEventBus;
    private FileDownloadManager(){
        mEventBus = new EventBus("download");
    }
    private List<DownloadEvent> downloadEventList = new ArrayList<>();

    /**
     * 返回EventBus对象
     * @return
     */
    public static EventBus getEventBus(){
        if (instance == null)
            instance = new FileDownloadManager();

        return instance.mEventBus;
    }

    /**
     * 当下载service启动后，需要通知管理器
     * 管理器检查是否有已经提交上来的下载任务
     * 如果有，就发送任务
     */
    static void onDownloadServiceStarted(){
        if (instance == null)
            instance = new FileDownloadManager();

        for (DownloadEvent event : instance.downloadEventList)
            instance.mEventBus.post(event);

        instance.downloadEventList.clear();
    }

    /**
     * 初始化服务
     * @param context
     */
    public static void init(Context context){
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
    }

    /**
     * 停止服务，并且释放资源
     * @param context
     */
    public static void release(Context context){
        Intent intent = new Intent(context, DownloadService.class);
        context.stopService(intent);
    }

    /**
     * 添加下载事件对象
     * @param event
     */
    public static void addDownloadEvent(DownloadEvent event){
        if (instance == null)
            instance = new FileDownloadManager();

        if (DownloadService.isStarted)
            instance.mEventBus.post(event);
        else
            instance.downloadEventList.add(event);
    }


    public static void testDownload(Context context, String url){
        FileDownloadManager.init(context);
        DownloadEvent downloadEvent = new DownloadEvent(url);
        downloadEvent.setListener(new OnDownloadListener(){
            public void onDownloadStart(String downloadUrl, long downloadFileSize) {
                System.out.println("====downloadUrl:" + downloadUrl);
                System.out.println("====downloadFileSize:" + downloadFileSize);
            }

            public void onDownloadProgress(long currentSize, long totalSize, float downloadSpeed) {
                System.out.println("====currentSize:" + currentSize);
                System.out.println("====totalSize:" + totalSize);
                System.out.println("====downloadSpeed:" + downloadSpeed);
            }

            public void onDownloadFinish(boolean isFinish, String downloadUrl, String savePath) {
                System.out.println("====isFinish:" + isFinish);
                System.out.println("====savePath:" + savePath);
            }
        });
        FileDownloadManager.addDownloadEvent(downloadEvent);
    }

}
