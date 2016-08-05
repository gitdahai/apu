package com.apu.ui.login.eat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.apu.adapter.AddressAdapter;
import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 所在位置 
 * @data 2016年5月25日

 * @author jun.wang
 */
public class MyAddressActivity extends BaseActivity implements OnClickListener {
	AddressAdapter adapter ;
	private ListView listView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_address);
		
		init() ;
		
	}

	private void init(){
		((TextView)findViewById(R.id.title)).setText("所在位置");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView)this.findViewById(R.id.listView) ;
		adapter = new AddressAdapter(this) ;
		listView.setAdapter(adapter);
		
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
