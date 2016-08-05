package com.apu.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.apu.adapter.HealthLabelAdapter;
import com.apu.ui.login.home.MainActivity;
import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.HealthInfoTwoActivity;

/**
 * @description 健康信息设置
 * @data 2016年5月14日

 * @author jun.wang
 */
public class HealthInfoSetting extends BaseActivity implements OnClickListener {
	private GridView gridView ;
	HealthLabelAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthinfo_setting);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康信息设置");
		findViewById(R.id.back).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("下一步");
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.text_test).setOnClickListener(this);
		findViewById(R.id.text_look).setOnClickListener(this);
		gridView = (GridView)this.findViewById(R.id.gridView) ;
		adapter = new HealthLabelAdapter(this) ;
		gridView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 intent = new Intent(HealthInfoSetting.this , HealthInfoTwoActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_test:
			 intent = new Intent(HealthInfoSetting.this , TestBodyActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_look:
			 intent = new Intent(HealthInfoSetting.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}
	

}
