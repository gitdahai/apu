package com.uphealth.cn.ui.login.health;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 健康问阿噗 
 * @data 2016年5月19日

 * @author jun.wang
 */
public class HealthAskActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_ask);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康问阿噗");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView)this.findViewById(R.id.listView) ;
		
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
