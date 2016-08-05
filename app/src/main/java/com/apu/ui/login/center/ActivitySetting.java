package com.apu.ui.login.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 系统设置 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class ActivitySetting extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("系统设置");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.text_info).setOnClickListener(this);
		findViewById(R.id.text_collect).setOnClickListener(this);
		findViewById(R.id.text_bind).setOnClickListener(this);
		findViewById(R.id.text_health).setOnClickListener(this);
		findViewById(R.id.text_msg_push).setOnClickListener(this);
		findViewById(R.id.text_clear_cache).setOnClickListener(this);
		findViewById(R.id.text_opinion).setOnClickListener(this);
		findViewById(R.id.text_about_us).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_info:
			 intent = new Intent(ActivitySetting.this , MyInfoActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_collect:
			 intent = new Intent(ActivitySetting.this , MyCollectActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_bind:
			 intent = new Intent(ActivitySetting.this , AccountBindActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_health:
			 intent = new Intent(ActivitySetting.this , HealthActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_msg_push:
			 intent = new Intent(ActivitySetting.this , MsgPushActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_clear_cache:
			 intent = new Intent(ActivitySetting.this , ClearChcheActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_opinion:
			 intent = new Intent(ActivitySetting.this , OpinionActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_about_us:
			 intent = new Intent(ActivitySetting.this , AboutUsActivity.class) ;
			 startActivity(intent);
			 break ;
			 

		default:
			break;
		}
		
	}

}
