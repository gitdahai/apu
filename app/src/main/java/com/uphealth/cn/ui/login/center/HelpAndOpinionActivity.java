package com.uphealth.cn.ui.login.center;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HelpAndOpinionAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.model.FeedBackModel;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.widget.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 帮助与反馈 
 * @data 2016年6月3日

 * @author jun.wang
 */
public class HelpAndOpinionActivity extends BaseActivity implements OnClickListener {
	@SuppressWarnings("unused")
	private MyListView listView ;
	HelpAndOpinionAdapter adapter ;
	
	private List<FeedBackModel> list = new ArrayList<>() ;
	
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
		setContentView(R.layout.activity_help_and_opinion);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.text).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("帮助与反馈");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (MyListView)this.findViewById(R.id.listView) ;
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		requestData() ;
	}
	
	private void setData(){
		adapter = new HelpAndOpinionAdapter(this , list) ;
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.text:
			 Bimp.tempSelectBitmap.clear();
			 Intent intent = new Intent(HelpAndOpinionActivity.this , OpinionActivity.class) ;
			 startActivity(intent);
			 break;
			 
		case R.id.back:
			 finish();
			 break ;

		default:
			break;
		}
	}
	
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getFeedbackList) 
		.append("?page=1").append("&pageSize=50") ;
		
		System.out.println("builder=" + builder.toString());
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getFeedbackList="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String data = json.getString("data") ;
					   JSONObject dataObj = new JSONObject(data) ;
					   String listStr = dataObj.getString("list") ;
					   
					   if(!TextUtils.isEmpty(listStr)){
						   list = new Gson().fromJson(listStr,
			    					new TypeToken<List<FeedBackModel>>() {
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
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
		
	}	
	
}
