package com.download;

import com.download.events.DownloadEvent;
import com.download.utils.ZipUtils;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDeleteDownloadFileListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/7.
 * 下载任务
 */
class DownloadTask implements OnDeleteDownloadFileListener {
    private String downloadUrl;
    private String unzipForder;
    private ZipFileDownloadListener listener;

    DownloadTask(String downloadUrl, String unzipForder, ZipFileDownloadListener listener){
        this.downloadUrl = downloadUrl;
        this.unzipForder = unzipForder;
        this.listener = listener;
    }

    void download(){
        DownloadEvent event = new DownloadEvent(downloadUrl);
        event.setListener(new OnDownloadListener(){
            public void onDownloadStart(String downloadUrl, long downloadFileSize) {
                if (listener != null)
                    listener.onDownloadStart(downloadUrl,downloadFileSize );
            }
            public void onDownloadProgress(long currentSize, long totalSize, float downloadSpeed) {
                if (listener != null)
                    listener.onDownloadProgress(currentSize, totalSize, downloadSpeed);
            }

            public void onDownloadFinish(boolean isFinish, String downloadUrl, String savePath) {
                if (listener != null)
                    listener.onDownloadFinish(isFinish, downloadUrl, savePath);

                //如果下载成功，则进行解压
                if (isFinish){
                    if (listener != null)
                        listener.onUnzipStart(savePath, unzipForder);

                    File file = new File(savePath);
                    boolean isSuccess = false;

                    try {
                        if (file.exists()){
                            ZipUtils.upZipFileToFolder(file, unzipForder);
                            isSuccess = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (listener != null)
                            listener.onUnzipFinish(isSuccess, unzipForder);

                        //删除已经下载的文件包
                        FileDownloader.delete(downloadUrl, true, DownloadTask.this);
                    }
                }
            }

        });
        //进行下载
        FileDownloadManager.getEventBus().post(event);
    }
    @Override
    public void onDeleteDownloadFilePrepared(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onDeleteDownloadFileSuccess(DownloadFileInfo downloadFileInfo) {

    }

    @Override
    public void onDeleteDownloadFileFailed(DownloadFileInfo downloadFileInfo, DeleteDownloadFileFailReason deleteDownloadFileFailReason) {

    }
}
