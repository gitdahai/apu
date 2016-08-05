package com.apu.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apu.data.StringClass;
import com.apu.utils.Utils;
import com.uphealth.cn.R;

/**
 * @description 登录 
 * @data 2016年5月13日

 * @author jun.wang
 */
public class PhoneBandActivity extends BaseActivity implements OnClickListener {
	private TextView text_error_phone , text_error_code , text_phone_code;
	private EditText edit_phone , edit_password;
	
	private static final int COUNT_SECONT_ID = 1000; // 计时递增的Id
	private static final int END_COUNT_SECONT_ID = 1001;// 停止计时的ID
	private static final int VERFICATE_PHONE_CODE = 1002;
	private int mCountSecondNum = 60; // 倒数计时起始数字
	
	private Button next ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_band);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("绑定手机");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		next = (Button)this.findViewById(R.id.next) ;
		((Button)findViewById(R.id.next)).setText("下一步");
		findViewById(R.id.text_phone_code).setOnClickListener(this);
		edit_phone = (EditText)this.findViewById(R.id.edit_phone) ;
		edit_password = (EditText)this.findViewById(R.id.edit_password) ;
		text_phone_code = (TextView)this.findViewById(R.id.text_phone_code) ;
		text_error_phone = (TextView)this.findViewById(R.id.text_error_phone) ;
		text_error_code = (TextView)this.findViewById(R.id.text_error_code) ;
		
		initEditListener() ;
	}
	
	private void initEditListener(){
		edit_phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() == 11){
					text_phone_code.setBackgroundResource(R.drawable.verification_cornor);
					text_phone_code.setEnabled(true);
					if(edit_password.getText().toString().length() == 6){
						next.setBackgroundResource(R.drawable.login_cornor);
						next.setEnabled(true);
					}
					
				}else {
					text_phone_code.setBackgroundResource(R.drawable.verification_cornor_default);
					text_phone_code.setEnabled(false);
					next.setBackgroundResource(R.drawable.login_cornor_default);
					next.setEnabled(false);
				}
			}
		});
		
		edit_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				  // 手机号满足的情况下
				  if(edit_phone.getText().toString().length() == 11){
					     
					    if(s.toString().length() == 6){
					    	next.setBackgroundResource(R.drawable.login_cornor);
					    	next.setEnabled(true);
					    }else {
					    	next.setBackgroundResource(R.drawable.login_cornor_default);
					    	next.setEnabled(false);
						}
				  }
			}
		});
	}	

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 next() ;
			 break ;
			 
		case R.id.text_phone_code:
			 getPhoneCode() ;
			 break ;

		default:
			break;
		}
	}
	
	// 获取短信验证码
	private void getPhoneCode(){
		if(!Utils.isMobileNO(edit_phone.getText().toString())){
			 text_error_phone.setVisibility(View.VISIBLE);
			 return ;
		}
		
		text_error_phone.setVisibility(View.GONE);
		text_phone_code.setClickable(false);
		text_phone_code.setTextColor(getResources().getColor(R.color.white));
		mCountHandler.post(mCountRunnable);
	}

	private Handler mCountHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case COUNT_SECONT_ID:
				// 计时中，刷新计时数据
				text_phone_code.setText(msg.arg1 + "");
				mCountHandler.postDelayed(mCountRunnable, 1000);
				break;

			// 结束计时
			case END_COUNT_SECONT_ID:
				text_phone_code.setClickable(true);
				text_phone_code.setTextColor(getResources().getColor(R.color.white));

				// 恢复倒数计时数据
				mCountSecondNum = 60;
				mCountHandler.removeCallbacks(mCountRunnable);
				text_phone_code
						.setText(StringClass.FSIRT_PAY_DETAIL_REQUEST_MSG_REGET_CODE);
				break;

			default:
				break;
			}
		};
	};
	
	private Runnable mCountRunnable = new Runnable() {

		public void run() {

			Message msg = new Message();
			msg.what = COUNT_SECONT_ID;
			msg.arg1 = mCountSecondNum--;

			if (mCountSecondNum >= 0) {
				mCountHandler.sendMessage(msg); // 倒数计时，刷新界面显示（一秒鐘刷新一次）
			} else {

				// 当倒数至零的时候，停止计时
				mCountHandler.removeCallbacks(mCountRunnable);
				mCountHandler.sendEmptyMessage(END_COUNT_SECONT_ID);
			}
		}
	};		

	private void next(){
		if(!Utils.isMobileNO(edit_phone.getText().toString())){
			 text_error_phone.setVisibility(View.VISIBLE);
			 return ;
		}
		
		text_error_code.setVisibility(View.GONE);
		Intent intent = new Intent(PhoneBandActivity.this , PersonActivity.class) ;
		startActivity(intent);
	}
}
