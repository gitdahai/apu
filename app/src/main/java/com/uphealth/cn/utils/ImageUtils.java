package com.uphealth.cn.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description 图片处理工具类 
 * @data 2016年6月5日

 * @author jun.wang
 */
public class ImageUtils {
	
	 public static void readByte(Context c, String name) { 
	        byte[] b = null; 
	        try { 
	            AssetManager am = null; 
	            am = c.getAssets(); 
	            InputStream is = am.open(name); 
                // 读取数据  
                is.read(b); 
                saveMyBitmap(Bytes2Bimap(b), Contants.filePath+"jiahao"+".png"); 
	            is.close(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	 }
	 
   public static Bitmap Bytes2Bimap(byte[] b) { 
	        if (b.length != 0) { 
	            return BitmapFactory.decodeByteArray(b, 0, b.length); 
	        } else { 
	            return null; 
	        } 
	} 	
   
   public static boolean saveMyBitmap(Bitmap bmp, String path) { 
       File f = new File(path); 
       try { 
           f.createNewFile(); 
           FileOutputStream fOut = new FileOutputStream(f); 
           bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut); 
           fOut.flush(); 
           fOut.close(); 
           return true; 
       } catch (Exception e) { 
           e.printStackTrace(); 
       } 
       return false; 
   }  
   
   public static boolean saveMyBitmap(Context context , String path) { 
	   System.out.println("path" + path);
       File f = new File(path); 
       try { 
           f.createNewFile(); 
           FileOutputStream fOut = new FileOutputStream(f); 
           
           Resources res = context.getResources();  
           Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon_jia);  
           bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut); 
           fOut.flush(); 
           fOut.close(); 
           return true; 
       } catch (Exception e) { 
           e.printStackTrace(); 
       } 
       return false; 
   }  

}
