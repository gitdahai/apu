package com.uphealth.cn.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.SkinAfterModel;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * <Pre>
 * <p/>
 * </Pre>
 *
 * @author jun.wang
 * @version 1.0
 *          create by  2016-03-27 17:39
 */

@SuppressLint("SimpleDateFormat")
public class Utils {

    /******************************************

     *
     * @param context
     * @return progressDialog
     */
    public static ProgressDialog initRequestDialog(final Context context) {
        ProgressDialog progressDialog; progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.dialog_message));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String getVersionName(Context context) {
      try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;
			int versionCode = context.getPackageManager()
					.getPackageInfo(pkName, 0).versionCode;
			return versionCode+"";
		} catch (Exception e) {
		}
      
		return "";
	}	

    @SuppressWarnings("unused")
    public static File getFileFromServer(String path, ProgressDialog pd) throws IOException {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);

            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;

                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }
    
    /**
     * 获取屏幕大小
     *
     * @param activity
     * @return
     * @author Stone
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new int[]{width, height};
    }
    
    /** 
     * 验证手机格式 
     */  
    public static boolean isMobileNO(String mobiles) {  
		/* 
		        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 
		        联通：130、131、132、152、155、156、185、186 
		        电信：133、153、180、189、（1349卫通） 
		        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 
        */  
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        if (TextUtils.isEmpty(mobiles)) return false;  
        else return mobiles.matches(telRegex);  
    }  
    
    /**
     * @description 读取assets里面的文件 根据文件名
     * @return
     */
    public static String getAssets(Context context ,String fileName){
    	String result = "";
    	try {
		    	InputStream in = context.getResources().getAssets().open(fileName);
		    	//获取文件的字节数
		    	int lenght = in.available();
		    	//创建byte数组
		    	byte[]  buffer = new byte[lenght];
		    	//将文件中的数据读到byte数组中
		    	in.read(buffer);
		    	result = EncodingUtils.getString(buffer, "utf-8");
		    	} catch (Exception e) {
		    	e.printStackTrace();
		     }
    	return result;
    }
    
    /**
     * @description 读取assets里面的文件 根据文件名
     * @return
     */
    public static String getPrivacyPolicy(Context context ,String fileName){
    	String result = "";
    	try {
		    	InputStream in = context.getResources().getAssets().open(fileName);
		    	//获取文件的字节数
		    	int lenght = in.available();
		    	//创建byte数组
		    	byte[]  buffer = new byte[lenght];
		    	//将文件中的数据读到byte数组中
		    	in.read(buffer);
		    	result = EncodingUtils.getString(buffer, "gbk");
		    	System.out.println("result"+result);
		    	} catch (Exception e) {
		    	e.printStackTrace();
		     }
    	return result;
    }
    
    
    @SuppressWarnings("unused")
	public static String[] getTags(String tags){
    	if(TextUtils.isEmpty(tags)){
    		return null;
    	}
    	
    	String[] result = new String[]{} ;
    	
    	try {
    		    result = tags.split(",") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
     //是否连接WIFI
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
     
        return false ;
    } 
    
    public static String getCurrentDate(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日"); 
    	Date curDate = new Date(System.currentTimeMillis());//获取当前时间 
    	String str = formatter.format(curDate); 
    	
    	return str ;
    }
    
    public static String getCurrentTime(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("HH:mm"); 
    	Date curDate = new Date(System.currentTimeMillis());//获取当前时间 
    	String str = formatter.format(curDate); 
    	
    	return str ;
    }
    
    public static String getCurrentDateTwo(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss"); 
    	Date curDate = new Date(System.currentTimeMillis());//获取当前时间 
    	String str = formatter.format(curDate); 
    	
    	return str ;
    }
    
    public static String getSDPath(){
    	return Environment.getExternalStorageState() ;
    }
    
    public static List<String> getPaths(List<String> paths){
    	
    	for(int i = 0 ; i <paths.size() - 1 ; i++ )   { 
	    	    for  (int j = paths.size() - 1 ; j > i; j-- )   { 
	    	      if  (paths.get(j).equals(paths.get(i)))   { 
	    	    	  paths.remove(j); 
	    	      } 
	    	    } 
    	} 
    	
    	return paths ;
    }
    
    public static String getSkinImagePath(String path){
    	if(TextUtils.isEmpty(path)){
    		return "" ;
    	}
    	
    	String result = path.split(",")[0] ;
    	
    	return result ;
    }
    
    public static List<SkinAfterModel> getSkinList(List<SkinAfterModel> list){
    	
    	for(int i = 0 ; i < list.size() ; i ++){
    		   if(TextUtils.isEmpty(list.get(i).getImages())){
    			   list.remove(i) ;
    		   }
    	}
    	
    	return list ;
    }
    
    /**
     * 取得目标体重
     * @param height
     * @param weight
     * @return
     */
    public static int getBMIWeight(int height){
    	
    	int temp = (int) (height/3.4) ;
    	
    	return temp ;
    }
    
    public static String getSex(String str){
    	if(str.equals("1")){
    		return "女" ;
    	}else {
			return "男" ;
		}
    }
    
    /**
     * 得到 09:10 时间
     * @param time
     * @return
     */
    public static String getTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");  
        Date d1=new Date(time);  
        String t1=format.format(d1);  
        
        return t1 ;
    }
    
    /**
     * 得到视频的总长度
     * @return
     */
    public static int getVideoAllLength(){
    	if(null != GlobalData.videoLists && GlobalData.videoLists.size() != 0){
    		   
    		  int length = 0 ; 
    		  for(int i = 0 ; i < GlobalData.videoLists.size() ; i ++){
    			      length = length + GlobalData.videoLists.get(i).getLength() ;
    		  }
    		  
    		  return length ;
    	}
    	
		return 1;
    }
    
//    public static int getProgreeLength(int length){
//    	
//    	return length / getVideoAllLength() ; 
//    }
    
    public static int getProgreeLength(int length){
    	 return GlobalData.heightPixels * length / getVideoAllLength() ;
    }
    
    /**
     * 得到 09:10 时间  得到时间差
     * @param time
     * @return
     */
    public static int getTimeMin(){
    	Calendar cal = Calendar.getInstance();
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	int apm = cal.get(Calendar.AM_PM);
    	
    	if (hour >= 6 && hour < 9) {
    	     System.out.println("hour清晨");
    	     return 1 ;
    	} else if (hour >= 9 && hour < 12) {
     	     System.out.println("hour中餐");
     	     return 2 ;
    	} else if (hour >= 12 && hour < 18 || apm == 1) {
     	     System.out.println("hour晚餐");
     	     return 3 ;
    	}else {
    		 System.out.println("hour加餐");
    		 return 4 ;
    	}
    }
    
}
