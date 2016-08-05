package com.uphealth.cn.ui.login.eat;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HateFoodAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.CommonTwoBean;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.widget.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 你不爱吃的食物
 * @data 2016年5月23日

 * @author jun.wang
 */
public class HateFoodActivity extends BaseActivity implements OnClickListener {
	HateFoodAdapter adapter ;
	private MyGridView gridView ;
	List<CommonTwoBean> list = new ArrayList<>() ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				setData() ;
				break;

			default:
				break;
			}
		};
		
	} ;
	
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
		
//		requestData() ;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		requestData() ;
	}
	
	private void setData(){
		adapter = new HateFoodAdapter(this , list) ;
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
	
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getAvoidFoodList) 
		.append("?accountId=").append(GlobalData.getUserId(this)) ;
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getAvoidFoodList="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String data = json.getString("data") ;
					   
					   if(!TextUtils.isEmpty(data)){
						   list = new Gson().fromJson(data,
			    					new TypeToken<List<CommonTwoBean>>() {
			    					}.getType());
						   handler.sendEmptyMessage(1001) ;
					   }
					   
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            	closeDialog() ;
//	                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
		
	}
	
	
	
	
	

}
