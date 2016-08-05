package com.uphealth.cn.ui.login.center.mod;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 修改昵称
 * @data 2016年6月27日

 * @author jun.wang
 */
public class NickNameActivity extends BaseActivity implements OnClickListener {
	private EditText edit ;
	private String name ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mod_nickname);
		
		name = getIntent().getStringExtra("name") ;
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this); 
		findViewById(R.id.delete).setOnClickListener(this); 
		((TextView)findViewById(R.id.title)).setText("名字");
		findViewById(R.id.right).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.text_right)).setText("保存");
		edit = (EditText)this.findViewById(R.id.edit) ;
		edit.setText(name);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;

		case R.id.delete:
			 break ;
			 
		default:
			break;
		}
		
	}
	

}
