package com.download;

/**
 * Created by Administrator on 2016/8/7.
 */
public interface ZipFileDownloadListener extends OnDownloadListener {
    /**
     * 文件开始解压
     * @param zipFilewName  ： 压缩文件保存的路径和名字
     * @param unzipFolder   ：解压后保存的位置
     */
    public void onUnzipStart(String zipFilewName, String unzipFolder);
    /**
     * 解压完成事件
     * @param isSuccess     ：解压是否成功（tue=成功， false=失败）
     * @param unzipFolder   : 解压后保存的文件夹
     */
    public void onUnzipFinish(boolean isSuccess, String unzipFolder);
}
