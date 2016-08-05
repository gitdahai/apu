package com.apu.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.apu.manager.ActivityManager;
import com.apu.utils.Utils;

/**
 * @description 阿噗窗体基类 
 * @data 2016年5月13日

 * @author jun.wang
 */
public class BaseActivity extends Activity {
	
	protected ProgressDialog loadDialog;
    private Handler handler = new Handler();
    ActivityManager activityManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init() ;
    }

    private  void  init(){
        loadDialog = Utils.initRequestDialog(this);
        activityManager = ActivityManager.getInstance() ;
        activityManager.pushActivity(this);
    }

    protected void  showDialog(){
        if (null != loadDialog) {
            loadDialog.show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (loadDialog.isShowing()) {
                        Toast.makeText(BaseActivity.this, "数据请求异常，请稍后再试!", Toast.LENGTH_SHORT).show();
                        loadDialog.dismiss();
                    }
                }
            }, 15000);
        }
    }

    protected void closeDialog() {
        if (null != loadDialog) {
            loadDialog.dismiss();
        }
    }

    protected void showToast(String content){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
    }

	

}
