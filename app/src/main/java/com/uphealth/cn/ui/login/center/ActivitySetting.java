package com.uphealth.cn.ui.login.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 系统设置 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class ActivitySetting extends BaseActivity implements OnClickListener {
	private Button button_exit ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("系统设置");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.right).setVisibility(View.GONE);
		findViewById(R.id.text_info).setOnClickListener(this);
		findViewById(R.id.text_collect).setOnClickListener(this);
		findViewById(R.id.text_bind).setOnClickListener(this);
		findViewById(R.id.text_health).setOnClickListener(this);
		findViewById(R.id.text_msg_push).setOnClickListener(this);
		findViewById(R.id.text_clear_cache).setOnClickListener(this);
		findViewById(R.id.text_opinion).setOnClickListener(this);
		findViewById(R.id.text_about_us).setOnClickListener(this);
		findViewById(R.id.button_exit).setOnClickListener(this);
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
			 intent = new Intent(ActivitySetting.this , HelpAndOpinionActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_about_us:
			 intent = new Intent(ActivitySetting.this , AboutUsActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 退出登陆，清除数据	 
		case R.id.button_exit:
			 GlobalData.accountModel = null ;
//			 GlobalData.personInfo = null ;
			 GlobalData.saveUserId(ActivitySetting.this, "");
			 Toast.makeText(ActivitySetting.this, "退出成功！", Toast.LENGTH_SHORT).show() ;
			 break ;

		default:
			break;
		}
	}
	
	private void isLogin(){
		if(TextUtils.isEmpty(GlobalData.getUserId(this))){
			showToast("您还没有登陆！");
			return ;
		}
		
		
	}

}
