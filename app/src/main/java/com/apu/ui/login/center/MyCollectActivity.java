package com.apu.ui.login.center;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 我的收藏 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class MyCollectActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collect);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("我的收藏");
		
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
