package com.apu.ui.login.eat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.apu.widget.MyGridView;
import com.uphealth.cn.R;

/**
 * @description 发布新动态 
 * @data 2016年5月25日

 * @author jun.wang
 */
public class PublishDynamicActivity extends BaseActivity implements OnClickListener {
	private MyGridView gridView ;
	private TextView text_show ;
	private boolean isSee = true ;
	private TextView text_address ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_dynamic);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("发布新动态");
		((Button)findViewById(R.id.next)).setText("发布");
		findViewById(R.id.back).setOnClickListener(this);
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		text_show = (TextView)this.findViewById(R.id.text_show) ;
		text_show.setOnClickListener(this);
		text_address = (TextView)this.findViewById(R.id.text_address) ;
		text_address.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_show:
			 Drawable drawable= getResources().getDrawable(R.drawable.icon_see);  
			 drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
			 
			 Drawable drawableLight= getResources().getDrawable(R.drawable.icon_see_light);  
			 drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
			 
			 if(isSee){
				 text_show.setCompoundDrawables(drawable,null,null,null);  
				 text_show.setText("隐藏");
				 text_show.setTextColor(getResources().getColor(R.color.text_system_default));
				 isSee = false ;
			 }else {
				 text_show.setCompoundDrawables(drawableLight,null,null,null);  
				 text_show.setTextColor(getResources().getColor(R.color.text_main));
				 text_show.setText("公开");
				 isSee = true ;
			 }
			 break ;
		
		// 所在位置	 
		case R.id.text_address:
			 intent = new Intent(PublishDynamicActivity.this ,  MyAddressActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}

}
