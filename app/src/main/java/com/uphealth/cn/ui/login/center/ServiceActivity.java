package com.uphealth.cn.ui.login.center;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.utils.Utils;

/**
 * 服务条款窗体
 * @description 
 * @data 2016年7月13日

 * @author jun.wang
 */
public class ServiceActivity extends BaseActivity implements OnClickListener {
	private TextView text ;
	private String titleStr = "" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_app);
		
		titleStr = getIntent().getStringExtra("titleStr") ;
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		text = (TextView)this.findViewById(R.id.text) ;
		((TextView)findViewById(R.id.title)).setText(titleStr);
		
		if(titleStr.equals("服务条款")){
			text.setText(Utils.getPrivacyPolicy(this, "serviceterms.txt"));
		}else {
			text.setText(Utils.getPrivacyPolicy(this, "privacypolicy.txt"));
		}
		
		
		
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
