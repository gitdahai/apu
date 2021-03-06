package com.download;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 * 文件下载管理器
 */
public class FileDownloadManager {
    private static FileDownloadManager instance;
    //private EventBus mEventBus;

    private List<DownloadTask> downloadEventList = new ArrayList<>();
    private FileDownloadManager(Context context){
        //mEventBus = new EventBus("download");
        DownloadHelper.init(context);
    }
    /**
     * 返回EventBus对象
     * @return
     */
    /*public static EventBus getEventBus(){
        if (instance == null)
            instance = new FileDownloadManager();

        return instance.mEventBus;
    }*/

    /**
     * 当下载service启动后，需要通知管理器
     * 管理器检查是否有已经提交上来的下载任务
     * 如果有，就发送任务
     */
    static void onDownloadServiceStarted(){
        /*if (instance == null)
            instance = new FileDownloadManager();

        for (DownloadTask task : instance.downloadEventList)
            task.download();

        instance.downloadEventList.clear();*/
    }

    /**
     * 初始化服务
     * @param context
     */
    public static void init(Context context){
        if (instance == null)
            instance = new FileDownloadManager(context);
        /*Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);*/
    }

    /**
     * 停止服务，并且释放资源
     * @param context
     */
    public static void release(Context context){
        /*Intent intent = new Intent(context, DownloadService.class);
        context.stopService(intent);*/
    }

    /**
     * 根据url地址下载文件
     * @param downloadUrl       ：下载的url地址
     * @param unzipForder       ：文件解压后，保存的文件夹（完整文件夹路径）
     * @param listener          ：事件监听器
     */
    public static void download(String downloadUrl, String unzipForder, ZipFileDownloadListener listener){
        if (instance == null)
            throw new RuntimeException("FileDownloadManager 为进行初始化");

        DownloadTask task = new DownloadTask(downloadUrl, unzipForder, false, listener);
        task.download();

        /*if (DownloadService.isStarted)
            task.download();
        else
            instance.downloadEventList.add(task);*/
    }

    /**
     *
     * @param downloadUrl
     * @param unzipForder
     * @param isUpdate      ： 是否数据有更新（true=有更新， false=无更新）
     * @param listener
     */
    public static void download(String downloadUrl, String unzipForder, boolean isUpdate, ZipFileDownloadListener listener){
        if (instance == null)
            throw new RuntimeException("FileDownloadManager 为进行初始化");

        DownloadTask task = new DownloadTask(downloadUrl, unzipForder, isUpdate, listener);
        task.download();
    }

    /**
     * 根据url地址下载文件（该方法，默认把文件解压到应用的data地址处）
     * @param context
     * @param downloadUrl   : 下载的url地址
     * @param listener
     */
    public static void download(Context context, String downloadUrl, ZipFileDownloadListener listener){
        if (instance == null)
            throw new RuntimeException("FileDownloadManager 为进行初始化");

        String unzipForder = context.getFilesDir().getAbsolutePath();
        DownloadTask task = new DownloadTask(downloadUrl, unzipForder, false, listener);
        task.download();

        /*if (DownloadService.isStarted)
            task.download();
        else
            instance.downloadEventList.add(task);*/
    }

    /***********************************************************************************************
     * 下载文件
     * @param context
     * @param downloadUrl
     * @param isUpdate      ： 是否数据有更新（true=有更新， false=无更新）
     * @param listener
     */
    public static void download(Context context, String downloadUrl, boolean isUpdate, ZipFileDownloadListener listener){
        if (instance == null)
            throw new RuntimeException("FileDownloadManager 为进行初始化");

        String unzipForder = context.getFilesDir().getAbsolutePath();
        DownloadTask task = new DownloadTask(downloadUrl, unzipForder, isUpdate, listener);
        task.download();
    }
}
