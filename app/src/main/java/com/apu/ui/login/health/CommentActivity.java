package com.apu.ui.login.health;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.adapter.PhotoWallAdapter;
import com.apu.ui.login.BaseActivity;
import com.apu.widget.MyGridView;
import com.uphealth.cn.R;

/**
 * @description 评论窗体界面 
 * @data 2016年5月19日

 * @author jun.wang
 */
public class CommentActivity extends BaseActivity implements OnClickListener {
	private MyGridView gridView ;
	PhotoWallAdapter wallAdapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("评论");
		findViewById(R.id.back).setOnClickListener(this);
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		wallAdapter = new PhotoWallAdapter(this) ;
		gridView.setAdapter(wallAdapter);
		
		
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
