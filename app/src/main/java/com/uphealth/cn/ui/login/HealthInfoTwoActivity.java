package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.home.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description 健康信息设置第二个界面 
 * @data 2016年5月17日

 * @author jun.wang
 */
public class HealthInfoTwoActivity extends BaseActivity implements OnClickListener {
	private RadioGroup radioGroupFive , radioGroupOne , radioGroupTwo , radioGroupThree , radioGroupFour;
	
	private TextView text_feature_one , text_feature_two , text_feature_three ;
	
	private String oneStr , twoStr , threeStr , foruStr , fiveStr= "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_info_two);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康信息设置"); 
		findViewById(R.id.back).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("确定");
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.text_look).setOnClickListener(this);
		
		radioGroupOne = (RadioGroup)this.findViewById(R.id.radioGroupOne) ;
		radioGroupTwo = (RadioGroup)this.findViewById(R.id.radioGroupTwo) ;
		radioGroupThree = (RadioGroup)this.findViewById(R.id.radioGroupThree) ;
		radioGroupFour = (RadioGroup)this.findViewById(R.id.radioGroupFour) ;
		radioGroupFive = (RadioGroup)this.findViewById(R.id.radioGroupFive) ;
		
		text_feature_one = (TextView)this.findViewById(R.id.text_feature_one) ;
		text_feature_two = (TextView)this.findViewById(R.id.text_feature_two) ;
		text_feature_three = (TextView)this.findViewById(R.id.text_feature_three) ;
		
		radioGroupFive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)findViewById(radioGroupFive.getCheckedRadioButtonId());
		        String str=radioButton.getText().toString();
		        setTextColor(str) ;
		        System.out.println("checkedId" + fiveStr);
			}
		});
		
		// 选择运动程度
		radioGroupOne.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)findViewById(radioGroupOne.getCheckedRadioButtonId());
				oneStr = radioButton.getText().toString();
		        System.out.println("checkedId" + oneStr);
			}
		});
		
		// 选择瘦身需求
		radioGroupTwo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)findViewById(radioGroupTwo.getCheckedRadioButtonId());
				twoStr = radioButton.getText().toString();
		        System.out.println("checkedId" + twoStr);
			}
		});
		
		// 选择化妆风格
		radioGroupThree.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)findViewById(radioGroupThree.getCheckedRadioButtonId());
				threeStr = radioButton.getText().toString();
		        System.out.println("checkedId" + threeStr);
			}
		});	
		
		// 选择化妆风格
		radioGroupFour.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)findViewById(radioGroupFour.getCheckedRadioButtonId());
				foruStr = radioButton.getText().toString();
		        System.out.println("checkedId" + foruStr);
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
			 
		case R.id.next:
//			 intent = new Intent(HealthInfoTwoActivity.this , MyHealthRecordActivity.class) ;
//			 startActivity(intent);
			 next() ;
			 break ;
			 
		case R.id.text_look:
			 intent = new Intent(HealthInfoTwoActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}
	
	private void setTextColor(String params){
		if(params.equals("A")){
			text_feature_one.setTextColor(getResources().getColor(R.color.text_main));
			text_feature_two.setTextColor(getResources().getColor(R.color.black));
			text_feature_three.setTextColor(getResources().getColor(R.color.black));
			fiveStr = text_feature_one.getText().toString() ;
		}else if (params.equals("B")) {
			text_feature_one.setTextColor(getResources().getColor(R.color.black));
			text_feature_two.setTextColor(getResources().getColor(R.color.text_main));
			text_feature_three.setTextColor(getResources().getColor(R.color.black));
			fiveStr = text_feature_two.getText().toString() ;
		}else if (params.equals("C")) {
			text_feature_one.setTextColor(getResources().getColor(R.color.black));
			text_feature_two.setTextColor(getResources().getColor(R.color.black));
			text_feature_three.setTextColor(getResources().getColor(R.color.text_main));
			fiveStr = text_feature_three.getText().toString() ;
		}
	}

	private void next(){
		if(TextUtils.isEmpty(oneStr)){
			showToast("请选择运动程度!");
			return ;
		}
		
		if(TextUtils.isEmpty(twoStr)){
			showToast("请选择瘦身需求!");
			return ;
		}
		
		if(TextUtils.isEmpty(threeStr)){
			showToast("请选择化妆风格!");
			return ;
		}
		
		if(TextUtils.isEmpty(foruStr)){
			showToast("请选择肤质!");
			return ;
		}
		
		if(TextUtils.isEmpty(fiveStr)){
			showToast("请选择脸型!");
			return ;
		}
		
		requestData() ;
	}
	
	private void requestData(){
        showDialog(); 
        
        StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.updatePersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
    	builder.append("&sportLevel=").append(oneStr) ;
        builder.append("&thinbodyneed_type=").append(twoStr) ;
        builder.append("&makeup_style=").append(threeStr) ;
        builder.append("&skin_type=").append(foruStr) ;
        builder.append("&face_type=").append(fiveStr) ;
		
        System.out.println("url" + Contants.getDictList+builder.toString());
        
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
            
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String reason = json.getString("reason") ;
					   
					   if(result == 1){
						   showToast("设置成功！");
						   GlobalData.saveFirst(HealthInfoTwoActivity.this, "0");
						   Intent intent = new Intent(HealthInfoTwoActivity.this , MyHealthRecordActivity.class) ;
						   startActivity(intent);
					   }else {
						   showToast(reason);
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
