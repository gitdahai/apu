package com.apu.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.uphealth.cn.R;

/**
 * @description 测试我的体质
 * @data 2016年5月14日

 * @author jun.wang
 */
public class TestBodyActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_body);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("测试我的体质");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("开始");
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 Intent intent = new Intent(TestBodyActivity.this , TestBankActivity.class);
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}

}
