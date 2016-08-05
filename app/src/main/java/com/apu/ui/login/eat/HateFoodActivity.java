package com.apu.ui.login.eat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.apu.adapter.HateFoodAdapter;
import com.apu.ui.login.BaseActivity;
import com.apu.widget.MyGridView;
import com.uphealth.cn.R;

/**
 * @description 你不爱吃的食物
 * @data 2016年5月23日

 * @author jun.wang
 */
public class HateFoodActivity extends BaseActivity implements OnClickListener {
	HateFoodAdapter adapter ;
	private MyGridView gridView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hate_food);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("你不爱吃的食物");
		findViewById(R.id.back).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("确定");
		((Button)findViewById(R.id.next)).setOnClickListener(this);
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		adapter = new HateFoodAdapter(this) ;
		gridView.setAdapter(adapter);
		
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
