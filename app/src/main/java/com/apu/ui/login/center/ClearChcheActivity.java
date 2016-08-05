package com.apu.ui.login.center;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.apu.dialog.CustomDialog;
import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;


/**
 * @description 清除缓存 
 * @data 2016年5月16日

 * @author jun.wang
 */
public class ClearChcheActivity extends BaseActivity implements OnClickListener {
	private CustomDialog.Builder ibuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_chche);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("缓存管理");
		findViewById(R.id.text_clear).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.text_clear:
			 show() ;
			 break;
			 
		case R.id.back:
			 finish();
			 break;

		default:
			break;
		}
		
	}
	
	private void show(){
		ibuilder = new CustomDialog.Builder(ClearChcheActivity.this);    
		ibuilder.setTitle("清除缓存");
		ibuilder.setMessage("清除缓存会导致下载的内容删除，是否确定?");
		ibuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
				finish();
			}
		});
		
		ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
			
		Dialog dialog = ibuilder.create() ;
		dialog.show();
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
	}

}
