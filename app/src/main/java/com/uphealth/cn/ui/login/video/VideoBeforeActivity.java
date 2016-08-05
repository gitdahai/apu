package com.uphealth.cn.ui.login.video;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.VideoBeforeDialog;

/**
 * @description 视频播放准备界面 
 * @data 2016年6月8日

 * @author jun.wang
 */
public class VideoBeforeActivity extends Activity {
	private VideoBeforeDialog.Builder Builder ; 
	Dialog dialog ;
	LayoutInflater inflater ;
	private boolean isCreate = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) ;
		setContentView(R.layout.activity_video_before);
		
		if(GlobalData.isCreateDialog){
			initDialog() ;
			GlobalData.isCreateDialog = false;
		}
	}

	private void initDialog(){
		inflater = LayoutInflater.from(this) ;
		Builder = new VideoBeforeDialog.Builder(this);
		dialog = Builder.create() ;
		View view = inflater.inflate(R.layout.dialog_video_before, null);
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.layout) ;
		
		layout.removeAllViews();
		for(int i = 0 ; i < 2 ; i ++){
			  View view2 = inflater.inflate(R.layout.item_common_trans, null) ;
			  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
					  (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) ;
			  params.setMargins(0, 0, 40, 0);
			  view2.setLayoutParams(params);
			   
			  layout.addView(view2);
		}
		
		TextView text_next = (TextView)view.findViewById(R.id.text_next) ;
		text_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO
				Intent intent = new Intent(VideoBeforeActivity.this , VideoNewActivity.class) ;
				startActivity(intent);
			}
		});
		
		dialog.addContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();	
		
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.9 
		p.height = (int) (d.getHeight() * 0.6);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
		dialog.getWindow().setGravity(Gravity.CENTER);	
		
	}
}
