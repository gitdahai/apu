package com.apu.ui.login.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 体重界面 
 * @data 2016年5月19日

 * @author jun.wang
 */
public class WeightActivity extends BaseActivity implements OnClickListener {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weight);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("体重");
		((TextView)findViewById(R.id.back)).setText("取消");
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.right)).setText("添加");
		findViewById(R.id.right).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.right:
			 Intent intent = new Intent(WeightActivity.this , ChatActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}

}
