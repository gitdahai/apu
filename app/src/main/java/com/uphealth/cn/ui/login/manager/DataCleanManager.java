package com.uphealth.cn.ui.login.manager;

import java.io.File;
import java.math.BigDecimal;

import android.text.TextUtils;

/**
 * 缓存数据管理类
 * @description 
 * @data 2016年7月19日

 * @author jun.wang
 */
public class DataCleanManager {
	
	/** 
     * 删除指定目录下文件及目录 
     *  
     * @param deleteThisPath 
     * @param filepath 
     * @return 
     */  
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {  
        if (!TextUtils.isEmpty(filePath)) {  
            try {  
                File file = new File(filePath);  
                if (file.isDirectory()) {// 如果下面还有文件  
                    File files[] = file.listFiles();  
                    for (int i = 0; i < files.length; i++) {  
                        deleteFolderFile(files[i].getAbsolutePath(), true);  
                    }  
                }  
                if (deleteThisPath) {  
                    if (!file.isDirectory()) {// 如果是文件，删除  
                        file.delete();  
                    } else {// 目录  
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除  
                            file.delete();  
                        }  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  	
	
	
	// 获取文件  
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据  
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据  
    public static long getFolderSize(File file) throws Exception {  
        long size = 0;  
        try {  
            File[] fileList = file.listFiles();  
            for (int i = 0; i < fileList.length; i++) {  
                // 如果下面还有文件  
                if (fileList[i].isDirectory()) {  
                    size = size + getFolderSize(fileList[i]);  
                } else {  
                    size = size + fileList[i].length();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return size;  
    }  	
    
    public static String getCacheSize(File file) throws Exception {  
        return getFormatSize(getFolderSize(file));  
    }  
    
    /** 
     * 格式化单位 
     *  
     * @param size 
     * @return 
     */  
    public static String getFormatSize(double size) {  
        double kiloByte = size / 1024;  
        if (kiloByte < 1) {  
            return size + "B";  
        }  
  
        double megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "K";  
        }  
  
        double gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "M";  
        }  
  
        double teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "G";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "T";  
    }  

}
