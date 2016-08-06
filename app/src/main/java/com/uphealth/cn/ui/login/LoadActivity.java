package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.download.FileDownloadManager;
import com.download.ZipFileDownloadListener;
import com.download.events.DownloadEvent;
import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.ui.login.home.MainActivity;

/**
 * 加载页
 * @description 
 * @data 2016年7月26日

 * @author jun.wang
 */
public class LoadActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		
		setParamter() ;
		
		// 判断是否登录过
		if(TextUtils.isEmpty(GlobalData.getFirst(LoadActivity.this, ""))){
			 Intent intent = new Intent(LoadActivity.this , LoginActivity.class) ;
			 startActivity(intent);
			 finish();
		}else {
			 GlobalData.saveFirst(LoadActivity.this, "1");
			 activityManager.pushActivity(LoadActivity.this);
			 
			 Intent intent = new Intent(LoadActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 finish();
		 }

		testDownloadFile();
	}

	private void testDownloadFile(){
		//这行代码应在应用启动的时候调用(最先执行 )
		FileDownloadManager.init(this);

		//下面的代码，一定要在上一行代码之后执行
		FileDownloadManager.download(this,"http://115.28.1.196:80//apu/app/temp/6f8e0779aa42492b95ae0bdc1d371507.zip", new ZipFileDownloadListener(){
			public void onDownloadStart(String downloadUrl, long downloadFileSize) {
				System.out.println("=====onDownloadStart-downloadFileSize:" + downloadFileSize);
			}

			public void onDownloadProgress(long currentSize, long totalSize, float downloadSpeed) {
				System.out.println("=====onDownloadProgress-currentSize-totalSize:" + currentSize + " " + totalSize);
			}

			public void onDownloadFinish(boolean isFinish, String downloadUrl, String savePath) {
				System.out.println("=====onDownloadFinish-isFinish:" + isFinish + " savePath=" + savePath);
			}

			public void onUnzipStart(String zipFilewName, String unzipFolder) {
				System.out.println("=====onUnzipStart-zipFilewName:" + zipFilewName + " unzipFolder=" + unzipFolder);
			}

			public void onUnzipFinish(boolean isSuccess, String unzipFolder) {
				System.out.println("=====onUnzipFinish-isSuccess:" + isSuccess + " unzipFolder=" + unzipFolder);
			}
		});
	}

	/*************************************************
	 * 获取屏幕的宽度和高度
	 */
	private void setParamter() {
        DisplayMetrics metrics = this.getApplicationContext().getResources().getDisplayMetrics() ;
		
		int heightPixels = metrics.heightPixels ;
		int widthPixels = metrics.widthPixels ;
		GlobalData.heightPixels = heightPixels ;
		GlobalData.widthPixels = widthPixels ;
		System.out.println("屏幕的宽度:" + heightPixels);
		System.out.println("屏幕的高度:" + widthPixels);
	}	

}
