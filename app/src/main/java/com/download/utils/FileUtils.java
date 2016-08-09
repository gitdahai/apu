package com.download.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by Administrator on 2016/8/7.
 */
public class FileUtils {
    /**
     * 根据文件的下载地址，获取保存在应用的文件区中的文件
     * @param context
     * @param url   : 文件的下载地址
     * @return
     */
    public static File getFileFromContextFiles(Context context, String url){
        String filePath = context.getFilesDir().getAbsolutePath() + "/" + getFileShortName(url);
        File file = new File(filePath) ;
        return file;
    }

    /**
     * 从指定的路径中，读取文件
     * @param patch
     * @param url
     * @return
     */
    public static File getFileFromPath(String patch, String url){
        String filePath = patch + "/" + getFileShortName(url);
        File file = new File(filePath) ;
        return file;
    }

    /**
     * 获取文件的最短文件名
     * @param fileName
     * @return
     */
    public static String getFileShortName(String fileName){
        String result = null;
        int index = fileName.lastIndexOf("/");

        if (index == -1)
            result = fileName;
        else
            result = fileName.substring(index + 1);

        return result;
    }
}
