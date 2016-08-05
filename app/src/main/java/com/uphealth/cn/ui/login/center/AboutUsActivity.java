package com.uphealth.cn.ui.login.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 关于窗体界面 
 * @data 2016年5月16日

 * @author jun.wang
 */
public class AboutUsActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("关于我们");
		findViewById(R.id.back).setOnClickListener(this);
		
		findViewById(R.id.layout_service).setOnClickListener(this);
		findViewById(R.id.layout_privacy).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
		
		// 服务条款	 
		case R.id.layout_service:
			 intent = new Intent(AboutUsActivity.this , ServiceActivity.class) ;
			 intent.putExtra("titleStr", "服务条款") ;
			 startActivity(intent);
			 break ;
			 
		// 隐私条款	 
		case R.id.layout_privacy:
			 intent = new Intent(AboutUsActivity.this , ServiceActivity.class) ;
			 intent.putExtra("titleStr", "隐私条款") ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}
	

}
