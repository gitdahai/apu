package com.apu.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.uphealth.cn.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <Pre>
 * <p/>
 * </Pre>
 *
 * @author jun.wang
 * @version 1.0
 *          create by  2016-03-27 17:39
 */

public class Utils {


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
        //濡傛灉鐩哥瓑鐨勮瘽琛ㄧず褰撳墠鐨剆dcard鎸傝浇鍦ㄦ墜鏈轰笂骞朵笖鏄彲鐢ㄧ殑
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //鑾峰彇鍒版枃浠剁殑澶у皬
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
                //鑾峰彇褰撳墠涓嬭浇閲?
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
}
