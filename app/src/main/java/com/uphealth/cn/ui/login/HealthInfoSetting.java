package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HealthLabelAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.SinglePicker;
import com.uphealth.cn.model.CommonModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.home.MainActivity;
import com.uphealth.cn.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @description 健康信息设置
 * @data 2016年5月14日

 * @author jun.wang
 */
public class HealthInfoSetting extends BaseActivity implements OnClickListener {
	private GridView gridView ;
	HealthLabelAdapter adapter ;
	private TextView edit_height ;
	private TextView edit_weight ;
	private TextView edit_goal_weight ;
	
	private String heightStr , weightStr = "" ;
	
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				List<CommonModel> lists = (List<CommonModel>)msg.obj ;
				adapter = new HealthLabelAdapter(HealthInfoSetting.this , lists , false) ;
				gridView.setAdapter(adapter);
				break;

			default:
				break;
			}
			
		};
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthinfo_setting);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康信息设置");
		findViewById(R.id.back).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("下一步");
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.text_test).setOnClickListener(this);
		findViewById(R.id.text_look).setOnClickListener(this);
		gridView = (GridView)this.findViewById(R.id.gridView) ;
		edit_height = (TextView)this.findViewById(R.id.edit_height) ;
		edit_height.setOnClickListener(this);
		edit_weight = (TextView)this.findViewById(R.id.edit_weight) ;
		edit_weight.setOnClickListener(this);
		edit_goal_weight = (TextView)this.findViewById(R.id.edit_goal_weight) ;
		edit_goal_weight.setOnClickListener(this);
		
//		adapter = new HealthLabelAdapter(this) ;
//		gridView.setAdapter(adapter);
		
		requestData() ;
		
		// 先清空缓存数据
		GlobalData.healthLebalList.clear();
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
//			 requestPersonInfo() ;
			 
			 next() ;
			 break ;
			 
		case R.id.text_test:
			 intent = new Intent(HealthInfoSetting.this , TestBodyActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.text_look:
			 intent = new Intent(HealthInfoSetting.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 请选择身高	 
		case R.id.edit_height:
			 SinglePicker singlePicker = new SinglePicker(this , GlobalData.getPersonHeight());
			 singlePicker.setOnClickOkListener(new SinglePicker.OnClickOkListener() {
		            @Override
		            public void onClickOk(String date) {
		            	if(date.equals("请选择身高")){
		            		edit_height.setText("");
		            	}else {
		            		edit_height.setText(date+"厘米");
		            		heightStr = date ;
						}
		            }
		     });
			 singlePicker.show(edit_height);
			 break ;
			 
//		// 目标体重	 
//		case R.id.edit_goal_weight:
//			 SinglePicker weightGoalPicker = new SinglePicker(this , GlobalData.getPersonGoalWeight());
//			 weightGoalPicker.setOnClickOkListener(new SinglePicker.OnClickOkListener() {
//		            @Override
//		            public void onClickOk(String date) {
//		            	if(date.equals("目标体重")){
//		            		edit_goal_weight.setText("");
//		            	}else {
//		            		edit_goal_weight.setText(date);
//		            		
//						}
//		            }
//		     });
//			 weightGoalPicker.show(edit_goal_weight);
//			 break ;
			 
			// 当前体重	 
			case R.id.edit_weight:
				 SinglePicker weightPicker = new SinglePicker(this , GlobalData.getPersonWeight());
				 weightPicker.setOnClickOkListener(new SinglePicker.OnClickOkListener() {
			            @Override
			            public void onClickOk(String date) {
			            	if(date.equals("当前体重")){
			            		edit_weight.setText("");
			            	}else {
			            		edit_weight.setText(date+"公斤");
			            		weightStr = date ;
			            		// 计算目标体重 BMI= 0.00173 为最佳体重
			            		edit_goal_weight.setText(Utils.getBMIWeight(Integer.parseInt(heightStr))+"公斤"); 
							}
			            }
			     });
				 weightPicker.show(edit_weight);
				 break ;		 

		default:
			break;
		}
	}
	
	private void requestData(){
        showDialog(); 
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				Contants.getDemandTagList, new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0" + arg0);
            	
            	try {
					     JSONObject json = new JSONObject(arg0) ;
					     
					     int result = json.getInt("result") ;
					     if(result == 1){
					    	  String data = json.getString("data") ;
					    	  
					    	  List<CommonModel> lists = new Gson().fromJson(data,
				    					new TypeToken<List<CommonModel>>() {
				    					}.getType());
					    	  Message msg = new Message() ;
					    	  msg.what = 1001 ;
					    	  msg.obj = lists ;
					    	  handler.sendMessage(msg) ;
					     }
					     
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
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
	
	private void next(){
		if(TextUtils.isEmpty(edit_height.getText())){
			showToast("身高不能为空!");
			return ;
		}
		
		if(TextUtils.isEmpty(edit_weight.getText())){
			showToast("当前体重不能为空!");
			return ;
		}
		
		if(TextUtils.isEmpty(edit_goal_weight.getText())){
			showToast("目标体重不能为空!");
			return ;
		}
		
		if(GlobalData.healthLebalList.size() == 0){
			showToast("您还没有选择健康标签！");
			return ;
		}
		
		System.out.println("healthLebalList==" + GlobalData.healthLebalList);
		requestPersonInfo() ;
	}
	
	
	private void requestPersonInfo(){
		showDialog(); 
		
		StringBuilder tagsBuilder = new StringBuilder() ;
		for(int i = 0 ; i < GlobalData.healthLebalList.size() ; i ++){
			  tagsBuilder.append(GlobalData.healthLebalList.get(i).getId()).append(",") ;
		}
		
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.updatePersonInfo) ;
		builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
		builder.append("&height=").append(heightStr) ;
		builder.append("&weight=").append(weightStr) ;
		builder.append("&healthTags=").append(tagsBuilder.toString()) ;
		
		System.out.println("api" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0" + arg0);
            	
            	try {
					     JSONObject json = new JSONObject(arg0) ;
					     
					     int result = json.getInt("result") ;
					     if(result == 1){
					    	 Intent intent = new Intent(HealthInfoSetting.this , HealthInfoTwoActivity.class) ;
							 startActivity(intent);
					    	 showToast("设置成功!");
					     }
					     
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
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
	

}
