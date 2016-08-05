package com.uphealth.cn.ui.login.center;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.AppInstationAdapter;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * 站内信
 * @description 
 * @data 2016年7月25日

 * @author jun.wang
 */
public class AppInstationActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	AppInstationAdapter adapter ;
	private TextView text_back , right ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_instation);
		
		init() ;
	}
	
	private void init(){
		listView = (ListView)this.findViewById(R.id.listView) ;
		adapter = new AppInstationAdapter(this) ; 
		listView.setAdapter(adapter);
		
		text_back = (TextView)this.findViewById(R.id.text_back) ;
		text_back.setText("");
		text_back.setBackgroundResource(R.drawable.icon_info);
		((TextView)findViewById(R.id.title)).setText("个人中心");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.right).setVisibility(View.GONE);
		
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
