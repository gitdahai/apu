package com.uphealth.cn.ui.login.fragment.find;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HottestPeopleAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * 最热达人
 * @description 
 * @data 2016年7月24日

 * @author jun.wang
 */
public class HottestPeopleActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	HottestPeopleAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hottest_people);
		
		init();
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("最热达人");
		listView = (ListView)this.findViewById(R.id.listView) ;
		adapter = new HottestPeopleAdapter(this) ;
		listView.setAdapter(adapter);
		
		requestData() ;
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
		builder.append(Contants.getHotMasterList) 
		.append("?page=1").append("&pageSize=10") ;
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getHotTopicList="+arg0);
            	
//            	try {
//					   JSONObject json = new JSONObject(arg0) ;
//					   String data = json.getString("data") ;
//					   JSONObject dataObj = new JSONObject(data);
//					   String listStr = dataObj.getString("list") ;
//					   
//					   
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
            	
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
