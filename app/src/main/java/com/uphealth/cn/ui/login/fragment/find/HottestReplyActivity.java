package com.uphealth.cn.ui.login.fragment.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HottestReplyAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.model.HottestReplyModel;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.fragment.HtmlNoTitleActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 最热话题
 * @description 
 * @data 2016年7月24日

 * @author jun.wang
 */
public class HottestReplyActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	HottestReplyAdapter adapter ;
	private List<HottestReplyModel> list = new ArrayList<>() ;
	
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
		setContentView(R.layout.activity_hottestreply);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("最热话题");
		listView = (ListView)this.findViewById(R.id.listView) ;
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Intent intent = new Intent(HottestReplyActivity.this , HtmlNoTitleActivity.class) ;
        	     intent.putExtra("url", HtmlUrl.H3_10) ;
        	     intent.putExtra("titleStr", list.get(position).getName()) ;
        	     startActivity(intent);
			}
		});
		
		requestData() ;
	}
	
	private void setData(){
		adapter = new HottestReplyAdapter(this , list) ;
		listView.setAdapter(adapter);
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
		builder.append(Contants.getHotTopicList) 
		.append("?page=1").append("&pageSize=10") ;
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getHotTopicList="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String data = json.getString("data") ;
					   JSONObject dataObj = new JSONObject(data);
					   String listStr = dataObj.getString("list") ;
					   
					   if(!TextUtils.isEmpty(data)){
						   list = new Gson().fromJson(listStr,
			    					new TypeToken<List<HottestReplyModel>>() {
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
