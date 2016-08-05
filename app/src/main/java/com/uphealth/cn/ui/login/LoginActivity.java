package com.uphealth.cn.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uphealth.cn.R;
import com.uphealth.cn.bean.ThirdBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.StringClass;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.center.ServiceActivity;
import com.uphealth.cn.ui.login.home.MainActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.CircularImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 登录窗体界面 
 * @data 2016年5月13日

 * @author jun.wang
 */
@SuppressLint("HandlerLeak")
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
	
	LoadImage loadImage ;
	private CircularImage headImage ;
	private CheckBox checkBox ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1001:
				String url = (String)msg.obj ;
				headImage.setTag(url);
				loadImage.addTask(url, headImage);
				loadImage.doTask();
				break;

			default:
				break;
			}
			
		};
		
	} ;
	
	
	private UMShareAPI mShareAPI = null;
	SHARE_MEDIA platform = null;
	
	private ImageView image_check ;
	private TextView text_treaty ;
	private boolean isCheck = true ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		init() ;
		
		setParamter() ;
		
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
		checkBox = (CheckBox)this.findViewById(R.id.checkBox) ;
		edit_phone.setText(GlobalData.getPhone(this));
		image_check = (ImageView)this.findViewById(R.id.image_check) ;
		image_check.setOnClickListener(this);
		text_treaty = (TextView)this.findViewById(R.id.text_treaty) ;
		text_treaty.setOnClickListener(this);
//		text_treaty.setText(Html.fromHtml("<u>"+"用户协议"+"</u>"));
		
		// 判断一下是否有手机号
		if(edit_phone.getText().toString().length() == 11){
			text_phone_code.setBackgroundResource(R.drawable.verification_cornor);
			text_phone_code.setEnabled(true);
		}
		
		setCheckBox() ;
		
		findViewById(R.id.text_sina).setOnClickListener(this);
		findViewById(R.id.text_weixin).setOnClickListener(this);
		findViewById(R.id.text_qq).setOnClickListener(this);
		
		headImage = (CircularImage)this.findViewById(R.id.headImage) ;
		loadImage = LoadImage.getInstance() ;
		
		// 友推分享组件初始化
//	    YtCore.init(this);
//		initPlatform();
		
		initEditListener() ;
		
		// 友盟分享
		mShareAPI = UMShareAPI.get( this );
	}
	
	/**
	 * 初始化分享平台的名字
	 */
	private void initPlatform() {
//		for (YtPlatform platform : YtPlatform.values())
//			// 截屏分享在自定义界面中不适用
//			if (platform != YtPlatform.PLATFORM_SCREENCAP)
//				platformNames.add(platform.getTitleName(this));
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
					
					// 请求用户头像
//					requestPersonInfo() ;
					
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
					    	
					    	// 键盘消失
					    	hide(edit_password) ;
					    	
					    }else {
					    	next.setBackgroundResource(R.drawable.login_cornor_default);
					    	next.setEnabled(false);
						}
				  }
			}
		});
	}
	
	private void setCheckBox(){
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					
				   // 手机号满足的情况下
				   if(edit_phone.getText().toString().length() == 11 && 
						   edit_password.getText().toString().length() == 6){
					   next.setBackgroundResource(R.drawable.login_cornor);
				       next.setEnabled(true);  
					}
				}else {
					next.setBackgroundResource(R.drawable.login_cornor_default);
			    	next.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		
		// 未登录状态进去
		case R.id.back:
			 intent = new Intent(LoginActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 break;
			 
		case R.id.text_sina:
//			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
//			 startActivity(intent);
			
//			 new AuthLogin().sinaAuth(this, authListener);
			 System.out.println("text_sina");
			 platform = SHARE_MEDIA.SINA;
			 mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
			 break ;
			 
		case R.id.text_weixin:
//			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
//			 startActivity(intent);
			 
//			 new AuthLogin().wechatAuth(this, authListener);
			
			 System.out.println("weixin");
			 platform = SHARE_MEDIA.WEIXIN;
			 mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
			 break ;
			 
		case R.id.text_qq:
//			 intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
//			 startActivity(intent);
			 
//			 new AuthLogin().qqAuth(this, authListener);
			
			 // 友盟
			
			 System.out.println("text_qq");
			 platform = SHARE_MEDIA.QQ;
			 mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
			 break ;	 
			 
		case R.id.next:
			 next() ;
			 
//			 GlobalData.isCreateDialog = true ;
//			 Intent test = new Intent(LoginActivity.this , VideoBeforeActivity.class) ;
//			 startActivity(test);
			 break ;
			 
		case R.id.text_phone_code:
			 getPhoneCode() ;
			 break ;
		
		// 用户协议	 
		case R.id.image_check:
			 if(isCheck){
				 next.setBackgroundResource(R.drawable.login_cornor_default);
			     next.setEnabled(false);
			     image_check.setBackgroundResource(R.drawable.check_bg_no);
			     isCheck = false ;
			 }else {
				 if(edit_phone.getText().toString().length() == 11 && 
						   edit_password.getText().toString().length() == 6){
					   next.setBackgroundResource(R.drawable.login_cornor);
				       next.setEnabled(true);  
				}
				image_check.setBackgroundResource(R.drawable.check_bg);
				isCheck = true ; 
			 }
			 break ;
		
		// 服务协议	 
		case R.id.text_treaty:
			 intent = new Intent(LoginActivity.this , ServiceActivity.class) ;
			 intent.putExtra("titleStr", "服务条款") ;
			 startActivity(intent);
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
		text_phone_code.setBackgroundResource((R.drawable.verification_cornor_default));
		
		requestCode() ;
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
				text_phone_code.setBackgroundResource((R.drawable.verification_cornor));
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
//		AES gAes = new AES();  
//        
//        String sendString="18795969061";  
//        byte[] sendBytes = null;  
//        try {  
//            sendBytes = sendString .getBytes("UTF8");  
//        } catch (UnsupportedEncodingException e) {  
//            e.printStackTrace();  
//        }  
//        String str = gAes.encrypt(sendBytes);  
//        System.out.println("aes=加密"+str);  
//        String result = gAes.decrypt(str);  
//        System.out.println("aes=解密"+result);  
		
		
		if(!Utils.isMobileNO(edit_phone.getText().toString())){
			 text_error_phone.setVisibility(View.VISIBLE);
			 return ;
		}
		
		text_error_code.setVisibility(View.GONE);
	    
		requestLogin() ;
		
//		// 判断是否登录过
//		if(TextUtils.isEmpty(GlobalData.getPhone(this))){
//			Intent intent = new Intent(LoginActivity.this , PersonActivity.class) ;
//			startActivity(intent);
//			GlobalData.savePhone(this, edit_phone.getText().toString());
//		}else {
//			Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
//			startActivity(intent);
//		}
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
	
//	/**
//	 * 授权登录监听器
//	 */
//	AuthListener authListener = new AuthListener() {
//
//		@Override
//		public void onAuthSucess(AuthUserInfo userInfo) {
//
//			// 授权平台为QQ
//			if (userInfo.isQqPlatform()) {
//				
//				// 授权完成后返回的QQ用户信息
//				Log.w("QQ", userInfo.getQqUserInfoResponse());
//
//				Log.w("QQ", userInfo.getQqOpenid());
//				Log.w("QQ", userInfo.getQqGender());
//				Log.w("QQ", userInfo.getQqImageUrl());
//				Log.w("QQ", userInfo.getQqNickName());
//				
//				ThirdBean thirdBean = new ThirdBean() ;
//				thirdBean.setOpenid(userInfo.getQqOpenid());
//				thirdBean.setGender(userInfo.getQqGender());
//				thirdBean.setPicUrl(userInfo.getQqImageUrl());
//				thirdBean.setName(userInfo.getQqNickName());
//				System.out.println("thirdBean" + thirdBean.toString());
//				GlobalData.thirdBean = thirdBean ;
//				
////				// 测试流程界面
////				Intent intent = new Intent(LoginActivity.this , TestBankActivity.class) ;
////				startActivity(intent);
//				
//				requestBand("4") ;
//			}
//
//			// 授权平台为新浪
//			else if (userInfo.isSinaPlatform()) {
//				// 授权完成后返回的新浪用户信息
//				Log.w("Sina", userInfo.getSinaUserInfoResponse());
//
//				Log.w("Sina", userInfo.getSinaUid());
//				Log.w("Sina", userInfo.getSinaGender());
//				Log.w("Sina", userInfo.getSinaName());
//				Log.w("Sina", userInfo.getSinaProfileImageUrl());
//				Log.w("Sina", userInfo.getSinaScreenname());
//				Log.w("Sina", userInfo.getSinaAccessToken());
//
//			}
//
//			// 授权平台为腾讯微博
//			else if (userInfo.isTencentWbPlatform()) {
//				// 授权完成后返回的腾讯微博用户信息
//				Log.w("TencentWb", userInfo.getTencentUserInfoResponse());
//
//				Log.w("TencentWb", userInfo.getTencentWbOpenid());
//				Log.w("TencentWb", userInfo.getTencentWbName());
//				Log.w("TencentWb", userInfo.getTencentWbNick());
//				Log.w("TencentWb", userInfo.getTencentWbBirthday());
//				Log.w("TencentWb", userInfo.getTencentWbHead());
//				Log.w("TencentWb", userInfo.getTencentWbGender());
//			}
//
//			// 授权平台为微信
//			else if (userInfo.isWechatPlatform()) {
//				// 授权完成后返回的微信用户信息
//				Log.w("Wechat", userInfo.getWeChatUserInfoResponse());
//
//				Log.w("Wechat", userInfo.getWechatOpenId());
//				Log.w("Wechat", userInfo.getWechatCountry());
//				Log.w("Wechat", userInfo.getWechatImageUrl());
//				Log.w("Wechat", userInfo.getWechatLanguage());
//				Log.w("Wechat", userInfo.getWechatNickName());
//				Log.w("Wechat", userInfo.getWechatProvince());
//				Log.w("Wechat", userInfo.getWechatSex());
//				
//				ThirdBean thirdBean = new ThirdBean() ;
//				thirdBean.setOpenid(userInfo.getWechatOpenId());
//				thirdBean.setGender(userInfo.getWechatSex());
//				thirdBean.setPicUrl(userInfo.getWechatImageUrl());
//				thirdBean.setName(userInfo.getWechatNickName());
//				System.out.println("thirdBean" + thirdBean.toString());
//				GlobalData.thirdBean = thirdBean ;
//				
//				requestBand("3") ;
//			}
//		}
//
//		@Override
//		public void onAuthFail() {
//			YtToast.showS(LoginActivity.this, "onAuthFail");
//		}
//
//		@Override
//		public void onAuthCancel() {
//			YtToast.showS(LoginActivity.this, "onAuthCancel");
//		}
//	};
	
	private void requestCode(){
        showDialog();
    	
        StringBuilder builder = new StringBuilder() ;
        builder.append(Contants.sendCode)
        .append("?phone=").append(edit_phone.getText().toString()) ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
        		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0="+arg0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            	closeDialog() ;
//                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
	}
	
	/*************************************************
	 * 获取屏幕的宽度和高度
	 */
	private void setParamter() {
        DisplayMetrics metrics = this.getApplicationContext().getResources().getDisplayMetrics() ;
		
		int heightPixels = metrics.heightPixels ;
		int widthPixels = metrics.widthPixels ;
		GlobalData.heightPixels = heightPixels ;
		GlobalData.widthPixels = widthPixels ;
		System.out.println("屏幕的宽度:" + heightPixels);
		System.out.println("屏幕的高度:" + widthPixels);
	}	
	
	private void requestLogin(){
		showDialog(); 
		
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.login)
		.append("?phone=").append(edit_phone.getText().toString())
		.append("&code=").append(edit_password.getText().toString().replaceAll(" ", "")) ;
		
		System.out.println("apilogin" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	
            	System.out.println("apilogin=="+arg0);
            	
            	try {
					    JSONObject json = new JSONObject(arg0) ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    String data = json.getString("data") ;
					    
					    if(result == 1){
					    	 
					    	  AccountModel model = new Gson().fromJson(data,
				    					new TypeToken<AccountModel>() {
				    					}.getType());
					    	  
					    	  // 是否返回了用户Id
					    	  if(TextUtils.isEmpty(model.getAccountId())){
					    		  GlobalData.isRequestCity = true ;
		                		  Intent intent = new Intent(LoginActivity.this , PersonActivity.class) ;
		            			  startActivity(intent);
		            			  GlobalData.savePhone(LoginActivity.this, edit_phone.getText().toString());
			            		  GlobalData.saveUserId(LoginActivity.this, model.getAccountId());
			            		  GlobalData.accountModel = model ;
					    	  }else {
					    		  GlobalData.saveFirst(LoginActivity.this, "1");
					    		  Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
		            			  startActivity(intent);
			            		  GlobalData.saveUserId(LoginActivity.this, model.getAccountId());
			            		  GlobalData.accountModel = model ;
			            		  GlobalData.savePhone(LoginActivity.this, edit_phone.getText().toString());
							  }
					    	  
//					    	// 判断是否已经设置过了用户信息
//		            		  if(GlobalData.getFirst(LoginActivity.this, "").equals("0")){
//		            		  	  Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
//		            			  startActivity(intent);
//			            		  GlobalData.saveUserId(LoginActivity.this, model.getAccountId());
//			            		  GlobalData.accountModel = model ;
//			            		  GlobalData.savePhone(LoginActivity.this, edit_phone.getText().toString());
//		            		  }else {
//		            			  GlobalData.isRequestCity = true ;
//		                		  Intent intent = new Intent(LoginActivity.this , PersonActivity.class) ;
//		            			  startActivity(intent);
//		            			  GlobalData.savePhone(LoginActivity.this, edit_phone.getText().toString());
//			            		  GlobalData.saveUserId(LoginActivity.this, model.getAccountId());
//			            		  GlobalData.accountModel = model ;
//						      }
					    	
					    }else {
							showToast(reason);
						}
					    
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
//            	
//            	
//            	if(result == 1){
//            		
//            		// 判断是否已经设置过了用户信息
//            		if(GlobalData.getFirst(LoginActivity.this, "").equals("0")){
//            			Intent intent = new Intent(LoginActivity.this , MainActivity.class) ;
//            			startActivity(intent);
////            			GlobalData.saveUserId(LoginActivity.this, model.getData());
//            		}else {
//            			GlobalData.isRequestCity = true ;
//                		Intent intent = new Intent(LoginActivity.this , PersonActivity.class) ;
//            			startActivity(intent);
//            			GlobalData.savePhone(LoginActivity.this, edit_phone.getText().toString());
////            			GlobalData.saveUserId(LoginActivity.this, model.getData());
//					}
//            		
//            		
//            		
//            	}else {
////            		showToast(model.getReason()+"");
//				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            	closeDialog() ;
                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
	}
	
	private void requestPersonInfo(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getPersonInfo)
		.append("?accountId=").append(GlobalData.getUserId(LoginActivity.this)) ;
		
		System.out.println("api=" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	System.out.println("personInfo" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   
					   if(result == 1){
						   JSONObject jsonItem = new JSONObject(data) ;
						   String pitUrl = jsonItem.getString("pitUrl") ;
						   Message msg = new Message() ;
						   msg.what = 1001 ;
						   msg.obj = pitUrl ;
						   handler.sendMessage(msg) ;
					   }
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
	}
	
	private void requestBand(String type){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.extLogin)
		.append("?type=").append(type) 
		.append("&number=").append(GlobalData.thirdBean.getOpenid()) 
		.append("&nickName=").append(GlobalData.thirdBean.getName())
		.append("&sex=").append(GlobalData.getSex(GlobalData.thirdBean.getGender()))
		.append("&pitUrl=").append(GlobalData.thirdBean.getPicUrl()) ;
		
		System.out.println("api=" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	System.out.println("personInfo" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   String reason = json.getString("reason") ;
					   
					   if(result == 1){
				    	   AccountModel model = new Gson().fromJson(data,
			    					new TypeToken<AccountModel>() {
			    					}.getType());
				    	   GlobalData.thirdAccountId = model.getAccountId() ;
						   
						   // 进入手机绑定界面
						   Intent intent = new Intent(LoginActivity.this , PhoneBandActivity.class) ;
						   startActivity(intent);
						   
					   }else {
//						    showToast(reason);
					   }
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);		
	}
	
	private void hide(EditText view){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘    
	}
	
	 /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            Log.d("user info","user info:"+data.toString());
            
//            if(platform == SHARE_MEDIA.QQ){
            	  mShareAPI.getPlatformInfo(LoginActivity.this, platform, umPersonInfoListener);
//            }
            
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
    
    private UMAuthListener umPersonInfoListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data!=null){
                System.out.println("dataStr=="+data.toString());
                
                if(platform == SHARE_MEDIA.QQ){
                	ThirdBean thirdBean = new ThirdBean() ;
     				thirdBean.setOpenid(data.get("openid"));
     				thirdBean.setName(data.get("screen_name"));
     				thirdBean.setPicUrl(data.get("profile_image_url"));
     				thirdBean.setGender(data.get("gender"));
     				GlobalData.thirdBean = thirdBean ;
    				requestBand("4") ;
                }else if (platform == SHARE_MEDIA.WEIXIN) {
                	ThirdBean thirdBean = new ThirdBean() ;
     				thirdBean.setOpenid(data.get("unionid"));
     				thirdBean.setName(data.get("nickname"));
     				thirdBean.setPicUrl(data.get("headimgurl"));
     				thirdBean.setGender(Utils.getSex(data.get("sex")));
     				GlobalData.thirdBean = thirdBean ;
    				requestBand("3") ;
				}
				
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }
    };
	
}
