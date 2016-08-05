package com.apu.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.apu.adapter.HealthLabelAdapter;
import com.apu.adapter.MyHealthRecordAdapter;
import com.apu.adapter.MyHealthRecordTwoAdapter;
import com.apu.ui.login.home.MainActivity;
import com.apu.widget.MyGridView;
import com.uphealth.cn.R;

/**
 * @description 我的健康档案
 * @data 2016年5月17日

 * @author jun.wang
 */
public class MyHealthRecordActivity extends BaseActivity implements OnClickListener {
	private MyGridView gridView , gridViewTwo , gridViewThree;
	MyHealthRecordAdapter adapter ;
	MyHealthRecordTwoAdapter adapterTwo ;
	HealthLabelAdapter adapterThree ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_health_record);
		
		init() ;
	}

	private void init(){
		((TextView)findViewById(R.id.title)).setText("我的健康档案");
		findViewById(R.id.back).setVisibility(View.GONE);
		((TextView)findViewById(R.id.right)).setText("重新测试体质");
		findViewById(R.id.right).setOnClickListener(this);
		((TextView)findViewById(R.id.right)).setTextColor(getResources().getColor(R.color.text_main));
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		adapter = new MyHealthRecordAdapter(this) ;
		gridView.setAdapter(adapter);
		gridViewTwo = (MyGridView)this.findViewById(R.id.gridViewTwo) ;
		adapterTwo = new MyHealthRecordTwoAdapter(this) ;
		gridViewTwo.setAdapter(adapterTwo);
		gridViewThree = (MyGridView)this.findViewById(R.id.gridViewThree) ;
		adapterThree = new HealthLabelAdapter(this) ;
		gridViewThree.setAdapter(adapterThree);
		((Button)findViewById(R.id.next)).setText("我的健康方案");
		findViewById(R.id.next).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
//			 intent = new Intent(MyHealthRecordActivity.this , YourPhysiqueActivity.class) ;
			 intent = new Intent(MyHealthRecordActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.right:
			 intent = new Intent(MyHealthRecordActivity.this , TestBodyActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}
	
}
