package com.uphealth.cn.ui.login.center;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.dialog.CustomDialog;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 账号绑定 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class AccountBindActivity extends BaseActivity implements OnClickListener {
	private TextView text_weixin_bind , text_qq_bind , text_sina_bind ;
	private CustomDialog.Builder ibuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_bind);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("账号绑定");
		
		text_weixin_bind = (TextView)this.findViewById(R.id.text_weixin_bind) ;
		text_weixin_bind.setOnClickListener(this);
		text_qq_bind = (TextView)this.findViewById(R.id.text_qq_bind) ;
		text_qq_bind.setOnClickListener(this);
		text_sina_bind = (TextView)this.findViewById(R.id.text_sina_bind) ;
		text_sina_bind.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_weixin_bind:
			 show();
		     break ;
		     
		case R.id.text_qq_bind:
			 show();
		     break ;
		     
		case R.id.text_sina_bind:
			 show();
		     break ;		     

		default:
			break;
		}
	}
	
	private void show(){
		ibuilder = new CustomDialog.Builder(AccountBindActivity.this);    
		ibuilder.setTitle("");
		ibuilder.setMessage("是否解除绑定？");
		ibuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
			}
		});
		
		ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
			
		Dialog dialog = ibuilder.create() ;
		dialog.show();
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
	}	

}
