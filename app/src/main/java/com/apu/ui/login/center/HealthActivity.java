package com.apu.ui.login.center;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 健康数据 
 * @data 2016年5月16日

 * @author jun.wang
 */
public class HealthActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康数据");
		findViewById(R.id.back).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;

		default:
			break;
		}
		
	}

}
