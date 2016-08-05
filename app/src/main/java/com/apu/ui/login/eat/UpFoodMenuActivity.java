package com.apu.ui.login.eat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.apu.adapter.UpFoodMenuAdapter;
import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

/**
 * @description 阿噗的餐单 
 * @data 2016年5月26日

 * @author jun.wang
 */
public class UpFoodMenuActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	UpFoodMenuAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_food_menu);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("阿噗的餐单");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.right).setVisibility(View.GONE);
		listView = (ListView)this.findViewById(R.id.listView) ;
		adapter = new UpFoodMenuAdapter(this) ;
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent() ;
			}
		});
		
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
