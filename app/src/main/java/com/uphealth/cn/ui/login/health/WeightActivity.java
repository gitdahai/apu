package com.uphealth.cn.ui.login.health;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.SinglePicker;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description 体重界面 
 * @data 2016年5月19日

 * @author jun.wang
 */
public class WeightActivity extends BaseActivity implements OnClickListener {
	private TextView text_weight ;
	private TextView text_rate ;
	private String weightStr , rateStr = "" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weight);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("体重");
		((TextView)findViewById(R.id.text_back)).setText("取消");
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.right)).setText("添加");
		findViewById(R.id.right).setOnClickListener(this);
		
		((TextView)findViewById(R.id.text_date)).setText(Utils.getCurrentDate());
		((TextView)findViewById(R.id.text_time)).setText(Utils.getCurrentTime());
		
		text_weight = (TextView)this.findViewById(R.id.text_weight) ;
//		text_weight.setOnClickListener(this);
		text_rate = (TextView)this.findViewById(R.id.text_rate) ;
//		text_rate.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.right:
//			 Intent intent = new Intent(WeightActivity.this , ChatActivity.class) ;
//			 startActivity(intent);
			 requestData() ;
			 break ;
		
		// 输入体重	 
		case R.id.text_weight:
			 SinglePicker weightGoalPicker = new SinglePicker(this , GlobalData.getWeight());
			 weightGoalPicker.setOnClickOkListener(new SinglePicker.OnClickOkListener() {
		            @Override
		            public void onClickOk(String date) {
		            	if(date.equals("体重(公斤)")){
		            		text_weight.setHint("请选择体重");
		            		weightStr = "" ;
		            	}else {
		            		text_weight.setText(date+"公斤");
		            		weightStr = date ;
						}
		            }
		     });
			 weightGoalPicker.show(text_weight);
			 break ;
		
		// 体质率	 
		case R.id.text_rate:
			 SinglePicker rate = new SinglePicker(this , GlobalData.getWeightRate());
			 rate.setOnClickOkListener(new SinglePicker.OnClickOkListener() {
		            @Override
		            public void onClickOk(String date) {
		            	if(date.equals("体脂率(%)")){
		            		text_rate.setHint("请选择体脂率");
		            		rateStr = "" ;
		            	}else {
		            		text_rate.setText(date+"%");
		            		rateStr = date ;
						}
		            }
		     });
			 rate.show(text_rate);
			 break ;

		default:
			break;
		}
	}
	
	private void requestData(){
		if(TextUtils.isEmpty(text_weight.getText().toString()) && 
				TextUtils.isEmpty(text_rate.getText().toString())){
			showToast("体重或者体脂率不能为空!");
			return ;
		}
		
        showDialog(); 
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.addWeightHistory)
		.append("?accountId=").append(GlobalData.getUserId(this))
		.append("&weight=").append(text_weight.getText().toString()) 
		.append("&bodyFat=").append(text_rate.getText().toString()) ;
		
		System.out.println("api" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
                System.out.println("weight" + arg0);
                
                try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String reason = json.getString("reason") ;
					   
					   if(result == 1){
						   finish();
					   }
					   
					   showToast(reason);
					   
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
