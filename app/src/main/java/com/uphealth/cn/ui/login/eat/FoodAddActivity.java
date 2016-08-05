package com.uphealth.cn.ui.login.eat;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.FoodAddAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.CommonTwoBean;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.widget.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加不爱吃的食物列表
 * @description 
 * @data 2016年7月18日

 * @author jun.wang
 */
public class FoodAddActivity extends BaseActivity implements OnClickListener {
	private FoodAddAdapter adapter ;
	MyListView listView ;
	List<CommonTwoBean> list = new ArrayList<>() ;
	private EditText edit ;
	private Button search ;
	
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
		setContentView(R.layout.activity_food_add);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("你不爱吃的食物");
		((Button)findViewById(R.id.next)).setText("确认");
		findViewById(R.id.next).setOnClickListener(this);
		edit = (EditText)this.findViewById(R.id.edit) ;
		search = (Button)this.findViewById(R.id.search) ;
		search.setOnClickListener(this);
		
		listView = (MyListView)this.findViewById(R.id.listView) ;
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(!list.get(position).isChoose()){
					list.set(position, list.get(position)).setChoose(true);
				}else {
					list.set(position, list.get(position)).setChoose(false);
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		requestData() ;
	}
	
	private void setData(){
		System.out.println("list=="+list);
		
		adapter = new FoodAddAdapter(this , list) ;
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 System.out.println("list==" + list);
			 requestAddData() ;
			 break ;
			 
		case R.id.search:
			 requestData();
			 break ;

		default:
			break;
		}
	}
	
	private void requestData(){
		showDialog();
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getFoodMaterialList) 
		.append("?page=1").append("&pageSize=10") 
		.append("&keywords=").append(edit.getText().toString().replaceAll(" ", ""));
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog();
            	System.out.println("getFoodMaterialList="+arg0);
            	
            	try {
					    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    JSONObject dataObj = new JSONObject(data) ;
					    String listStr = dataObj.getString("list") ;
					    
					    if(!TextUtils.isEmpty(listStr)){
					    	 list = new Gson().fromJson(listStr,
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
            	closeDialog();
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
	}	
	
	private void requestAddData(){
		showDialog();
		
		StringBuilder foodIdBuilder = new StringBuilder() ;
		
		if(null != list && list.size() != 0){
			  for(int i = 0 ; i < list.size() ; i ++){
				  
				     if(list.get(i).isChoose()){
				    	 if(i != (list.size() - 1)){
				    		 foodIdBuilder.append(list.get(i).getId()).append(",") ;
					     }
		    	      }
				     
			  }  
		}
		
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.addAvoidFood) 
		.append("?accountId=").append(GlobalData.getUserId(this)) 
		.append("&foodMaterialIds=").append(foodIdBuilder.toString()); 
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog();
            	System.out.println("builder00"+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String reason = json.getString("reason") ;
					   showToast(reason);
					   
					   if(result == 1){
						   finish();
					   }
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	closeDialog();
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
	}	
	

}
