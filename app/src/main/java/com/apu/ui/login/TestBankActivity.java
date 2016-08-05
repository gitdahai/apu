package com.apu.ui.login;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.apu.adapter.TestBankAdapter;
import com.uphealth.cn.R;

/**
 * @description 题库窗体界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class TestBankActivity extends BaseActivity implements OnClickListener {
	private ListView listview ;
	TestBankAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_bank);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("测试我的体质");
		listview = (ListView)this.findViewById(R.id.listview) ;
		adapter = new TestBankAdapter(this) ;
		listview.setAdapter(adapter);
		
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
