package com.apu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apu.data.GlobalData;
import com.apu.data.StringClass;
import com.apu.ui.login.home.MainActivity;
import com.apu.utils.Utils;
import com.uphealth.cn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 登录窗体界面 
 * @data 2016年5月13日

 * @author jun.wang
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText edit_phone , edit_password;
	private TextView text_phone_code ;
	private TextView text_error_phone , text_error_code ;
	private Button next ;
	private long exittime = 0 ;
	private static final int COUNT_SECONT_ID = 1000; // 计时递增的Id
	private static final int END_COUNT_SECONT_ID = 1001;// 停止计时的ID
	private static final int VERFICATE_PHONE_CODE = 1002;
	private int mCountSecondNum = 60; // 倒数计时起始数字
	
	/** 每一个分享平台的名字 */
	private List<String> platformNames = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("登录");
		findViewById(R.id.next).setOnClickListener(this);
		next = (Button)this.findViewById(R.id.next) ;
		findViewById(R.id.text_phone_code).setOnClickListener(this);
		edit_phone = (EditText)this.findViewById(R.id.edit_phone) ;
		text_phone_code = (TextView)this.findViewById(R.id.text_phone_code) ;
		edit_password = (EditText)this.findViewById(R.id.edit_password) ;
		text_error_phone = (TextView)this.findViewById(R.id.text_error_phone) ;
		text_error_code = (TextView)this.findViewById(R.id.text_error_code) ;
		
		findViewById(R.id.text_sina).setOnClickListener(this);
		findViewById(R.id.text_weixin).setOnClickListener(this);
		findViewById(R.id.text_qq).setOnClickListener(this);

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
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_sina:
//			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
//			 startActivity(intent);
			
			 //new AuthLogin().sinaAuth(this, authListener);
			 break ;
			 
		case R.id.text_weixin:
			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
			 startActivity(intent);
			 
//			 new AuthLogin().wechatAuth(this, authListener);
			 break ;
			 
		case R.id.text_qq:
//			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
//			 startActivity(intent);
			 
			 //new AuthLogin().qqAuth(this, authListener);
			 
			 break ;	 
			 
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
		
		// 判断是否登录过
		if(TextUtils.isEmpty(GlobalData.getPhone(this))){
			Intent intent = new Intent(LoginActivity.this , PersonActivity.class) ;
			startActivity(intent);
			GlobalData.commitPhone(this, edit_phone.getText().toString());
		}else {
			Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
        	  if((System.currentTimeMillis() - exittime) > 2000){
        		    Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show() ; 
        		    exittime = System.currentTimeMillis() ;
        	  }else {
        		   // 同时清除缓存数据
//        		   GlobalData.clear();
				   System.exit(0) ;
			  }
        	 return true ;
        }
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 授权登录监听器
	 */
	/*AuthListener authListener = new AuthListener() {

		@Override
		public void onAuthSucess(AuthUserInfo userInfo) {

			YtToast.showS(LoginActivity.this, "onAuthSucess");

			// 授权平台为QQ
			if (userInfo.isQqPlatform()) {
				// 授权完成后返回的QQ用户信息
				Log.w("QQ", userInfo.getQqUserInfoResponse());

				Log.w("QQ", userInfo.getQqOpenid());
				Log.w("QQ", userInfo.getQqGender());
				Log.w("QQ", userInfo.getQqImageUrl());
				Log.w("QQ", userInfo.getQqNickName());
			}

			// 授权平台为新浪
			else if (userInfo.isSinaPlatform()) {
				// 授权完成后返回的新浪用户信息
				Log.w("Sina", userInfo.getSinaUserInfoResponse());

				Log.w("Sina", userInfo.getSinaUid());
				Log.w("Sina", userInfo.getSinaGender());
				Log.w("Sina", userInfo.getSinaName());
				Log.w("Sina", userInfo.getSinaProfileImageUrl());
				Log.w("Sina", userInfo.getSinaScreenname());
				Log.w("Sina", userInfo.getSinaAccessToken());

			}

			// 授权平台为腾讯微博
			else if (userInfo.isTencentWbPlatform()) {
				// 授权完成后返回的腾讯微博用户信息
				Log.w("TencentWb", userInfo.getTencentUserInfoResponse());

				Log.w("TencentWb", userInfo.getTencentWbOpenid());
				Log.w("TencentWb", userInfo.getTencentWbName());
				Log.w("TencentWb", userInfo.getTencentWbNick());
				Log.w("TencentWb", userInfo.getTencentWbBirthday());
				Log.w("TencentWb", userInfo.getTencentWbHead());
				Log.w("TencentWb", userInfo.getTencentWbGender());
			}

			// 授权平台为微信
			else if (userInfo.isWechatPlatform()) {
				// 授权完成后返回的微信用户信息
				Log.w("Wechat", userInfo.getWeChatUserInfoResponse());

				Log.w("Wechat", userInfo.getWechatOpenId());
				Log.w("Wechat", userInfo.getWechatCountry());
				Log.w("Wechat", userInfo.getWechatImageUrl());
				Log.w("Wechat", userInfo.getWechatLanguage());
				Log.w("Wechat", userInfo.getWechatNickName());
				Log.w("Wechat", userInfo.getWechatProvince());
				Log.w("Wechat", userInfo.getWechatSex());
			}
		}

		@Override
		public void onAuthFail() {
			YtToast.showS(LoginActivity.this, "onAuthFail");
		}

		@Override
		public void onAuthCancel() {
			YtToast.showS(LoginActivity.this, "onAuthCancel");
		}
	};*/
	
	
}
