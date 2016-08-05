package com.uphealth.cn.ui.login.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.manager.ActivityManager;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.ui.login.LoginActivity;
import com.uphealth.cn.widget.DragGridViewOlder;
import com.uphealth.cn.widget.MyGallery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description 阿噗主界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private long exittime = 0 ;
	private Fragment[] mFragments;  
    private FragmentManager fragmentManager;  
    private FragmentTransaction fragmentTransaction;  
    ActivityManager activityManager ;
    
    private MyGallery gallery ;
    private DragGridViewOlder gridview ;
	private LinearLayout layout_two ;
	
	protected RequestQueue requestQueue = null;
	private RelativeLayout navi_item_person , navi_item_home;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// 判断是否登录过
		if(TextUtils.isEmpty(GlobalData.getFirst(MainActivity.this, ""))){
			 Intent intent = new Intent(MainActivity.this , LoginActivity.class) ;
			 startActivity(intent);
		}else {
			 setContentView(R.layout.activity_main);
			 init() ;
			 GlobalData.saveFirst(MainActivity.this, "1");
			 activityManager.pushActivity(MainActivity.this);
			 
//			AES gAes = new AES();  
//	        
//	        String sendString="123456";  
//	        byte[] sendBytes = null;  
//	        try {  
//	            sendBytes = sendString .getBytes("UTF8");  
//	        } catch (UnsupportedEncodingException e) {  
//	            e.printStackTrace();  
//	        }  
//	        String str = gAes.encrypt(sendBytes);  
//	        System.out.println("aes=加密"+str);  
//	        String result = gAes.decrypt(str);  
//	        System.out.println("aes=解密"+result);  
			 
		}
		
	}
	
	private void init(){
		activityManager = ActivityManager.getInstance() ;
		mFragments = new Fragment[3];  
        fragmentManager = getSupportFragmentManager();  
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_find); 
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_custom); 
        mFragments[2] = fragmentManager  
                .findFragmentById(R.id.fragement_center);  
        fragmentTransaction = fragmentManager.beginTransaction()  
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);  
        fragmentTransaction.show(mFragments[0]).commit();  
        
        findViewById(R.id.image).setOnClickListener(this);
        
        // 健康问阿噗
        findViewById(R.id.image).setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				
				Intent intent = new Intent(MainActivity.this , HealthAskActivity.class) ;
				startActivity(intent);
				return false;
			}
		});
        gallery = (MyGallery)this.findViewById(R.id.gallery) ;
        layout_two = (LinearLayout)this.findViewById(R.id.layout_two) ;
        gridview = (DragGridViewOlder)this.findViewById(R.id.gridView) ;
        
        navi_item_person = (RelativeLayout)this.findViewById(R.id.navi_item_person) ;
        navi_item_person.setOnClickListener(this);
        navi_item_home = (RelativeLayout)this.findViewById(R.id.navi_item_home) ;
        navi_item_home.setOnClickListener(this);
        
        requestQueue = Volley.newRequestQueue(this);
        requestPersonInfo() ;
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
        		   activityManager.popAllActivity(MainActivity.class);
				   System.exit(0) ;
			  }
        	 return true ;
        }
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		fragmentTransaction = fragmentManager.beginTransaction()  
	              .hide(mFragments[0]).hide(mFragments[1])  
	              .hide(mFragments[2]);  
		
		Intent intent = null ;
		switch (v.getId()) {
		case R.id.image:
			 fragmentTransaction.show(mFragments[0]).commit(); 
			 layout_two.setVisibility(View.GONE);
			 gallery.setVisibility(View.VISIBLE);
			 gridview.setVisibility(View.VISIBLE);
			 GlobalData.isStay = true ;
			 break;
			 
		case R.id.navi_item_home:
			 fragmentTransaction.show(mFragments[1]).commit(); 
			 break ;
			 
		// 个人主页	 
		case R.id.navi_item_person:
			 if(TextUtils.isEmpty(GlobalData.getUserId(this))){
				 intent = new Intent(MainActivity.this , LoginActivity.class) ;
				 startActivity(intent);
			 }else {
				 fragmentTransaction.show(mFragments[2]).commit(); 
			 }
			 break ;		 

		default:
			break;
		}
	}
	
	private void requestPersonInfo(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getPersonInfo)
		.append("?accountId=").append(GlobalData.getUserId(this)) ;
		
		System.out.println("api " + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("arg0" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					  
					   if(result == 1){
						   AccountModel model = new Gson().fromJson(data,
			    					new TypeToken<AccountModel>() {
			    					}.getType());
						   
//						   PersonInfo info = new Gson().fromJson(data,
//			    					new TypeToken<PersonInfo>() {
//			    					}.getType());
						   GlobalData.accountModel = model ;
//						   System.out.println("GlobalData.personInfo" + GlobalData.personInfo);
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

}
