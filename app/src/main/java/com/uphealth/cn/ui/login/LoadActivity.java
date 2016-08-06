package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.download.FileDownloadManager;
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

		FileDownloadManager.testDownload(this, "http://115.28.1.196/apu/interface/plan/getPlanFileZip?id=6f8e0779aa42492b95ae0bdc1d371507");
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
