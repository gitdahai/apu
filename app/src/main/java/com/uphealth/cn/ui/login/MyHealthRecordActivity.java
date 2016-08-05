package com.uphealth.cn.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HealthLabelAdapter;
import com.uphealth.cn.adapter.MyHealthRecordAdapter;
import com.uphealth.cn.adapter.MyHealthRecordTwoAdapter;
import com.uphealth.cn.bean.PhysiqueBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.model.CommonModel;
import com.uphealth.cn.ui.login.home.MainActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 我的健康档案
 * @data 2016年5月17日

 * @author jun.wang
 */
@SuppressLint("RtlHardcoded")
public class MyHealthRecordActivity extends BaseActivity implements OnClickListener {
	private MyGridView gridView , gridViewTwo , gridViewThree;
	MyHealthRecordAdapter adapter ;
	MyHealthRecordTwoAdapter adapterTwo ;
	HealthLabelAdapter adapterThree ;
	private TextView text_bmi_tip_show;
	private boolean isBMIshow , isPhysique , isHealth = true ;
	private TextView text_health_show ;
	private TextView text_physique_show ;
	private TextView text_bmi_tip ;
	private TextView textCalorie ;
	private TextView text_physique ;
	private LinearLayout layout_bmi ;
	
	private List<PhysiqueBean> lists = new ArrayList<>() ;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				setData() ;
				break;
				
			case 1002:
				String resultStr = (String)msg.obj ;
				textCalorie.setText(resultStr+"cal") ;
				break ; 

			default:
				break;
			}
		};
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_health_record);
		
		GlobalData.saveFirst(MyHealthRecordActivity.this, "1");
		init() ;
		requestPersonInfo() ;
		
	}

	private void init(){
		((TextView)findViewById(R.id.title)).setText("我的健康档案");
		findViewById(R.id.back).setVisibility(View.GONE);
		findViewById(R.id.right).setOnClickListener(this);
		findViewById(R.id.text_right).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.text_right)).setText("重新测试体质");
		((TextView)findViewById(R.id.text_right)).setTextColor(getResources().getColor(R.color.text_main));
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		gridViewTwo = (MyGridView)this.findViewById(R.id.gridViewTwo) ;
		lists = getLists() ;
		text_physique = (TextView)this.findViewById(R.id.text_physique) ;
		adapterTwo = new MyHealthRecordTwoAdapter(this,lists) ;
		gridViewTwo.setAdapter(adapterTwo);
		gridViewThree = (MyGridView)this.findViewById(R.id.gridViewThree) ;
		((Button)findViewById(R.id.next)).setText("我的健康方案");
		findViewById(R.id.next).setOnClickListener(this);
		text_bmi_tip = (TextView)this.findViewById(R.id.text_bmi_tip) ;
		text_bmi_tip.setOnClickListener(this);
		layout_bmi = (LinearLayout)this.findViewById(R.id.layout_bmi) ;
		layout_bmi.setOnClickListener(this);
		
		text_bmi_tip_show = (TextView)this.findViewById(R.id.text_bmi_tip_show) ;
		findViewById(R.id.text_health).setOnClickListener(this); ;
		text_health_show = (TextView)this.findViewById(R.id.text_health_show) ;
		findViewById(R.id.text_physique).setOnClickListener(this);
		text_physique_show = (TextView)this.findViewById(R.id.text_physique_show) ;
		textCalorie = (TextView)this.findViewById(R.id.textCalorie) ;
		
		getSuggestCalorie() ;
	}

	private void setData(){
		if(lists.get(0).getRank() == 0){
			gridViewTwo.setVisibility(View.GONE);
			text_physique.setVisibility(View.GONE);
		}else {
			gridViewTwo.setVisibility(View.VISIBLE);
			text_physique.setVisibility(View.VISIBLE);
		}
		
		adapterTwo.uplate(lists);
		
		adapter = new MyHealthRecordAdapter(this , GlobalData.accountModel.getBMI()) ;
		gridView.setAdapter(adapter);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
				LayoutParams.WRAP_CONTENT) ;
		
		// 根据不同区间显示不同的值
		if(GlobalData.accountModel.getBMI() <= 0.2){
			text_bmi_tip.setText("您的BMI值"+GlobalData.accountModel.getBMI()+"");
//			params.leftMargin = 10 ;
			params.gravity = Gravity.LEFT ;
		}else if (GlobalData.accountModel.getBMI() <= 0.4 && 
			GlobalData.accountModel.getBMI() > 0.2){
			params.rightMargin = 80 ;
			params.gravity = Gravity.CENTER ;
			System.out.println("accountModel==rightMargin");
		}else if (GlobalData.accountModel.getBMI() <= 0.6 && 
				GlobalData.accountModel.getBMI() > 0.4){
			params.leftMargin = 80 ;
			params.gravity = Gravity.CENTER ;
		}else if (GlobalData.accountModel.getBMI() <= 0.8 && 
				GlobalData.accountModel.getBMI() > 0.6){
//			params.leftMargin = 120 ;
			params.gravity = Gravity.RIGHT ;
		}
		
//		layout_bmi.setLayoutParams(params);
		text_bmi_tip.setText("您的BMI值"+GlobalData.accountModel.getBMI()+"");
		text_bmi_tip.setLayoutParams(params);
		
		// 健康标签
		List<CommonModel> lists = new ArrayList<>() ;
		
		if(!TextUtils.isEmpty(GlobalData.accountModel.getHealthTagNames())){
			   
			String[] temp = Utils.getTags(GlobalData.accountModel.getHealthTagNames()) ;
			
			for(int i = 0 ; i < temp.length ; i ++){
				   CommonModel bean = new CommonModel() ;
				   bean.setName(temp[i]);
				   lists.add(bean) ;
			}
			
			HealthLabelAdapter adapter = new HealthLabelAdapter(MyHealthRecordActivity.this , lists , true) ;
			gridViewThree.setAdapter(adapter);
		}
		
	}
	
	private void getSuggestCalorie(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getSuggestCalorie)
		.append("?accountId=").append(GlobalData.getUserId(this)) ;
		
		System.out.println("api" + builder.toString());
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
						   Message msg = new Message() ;
						   msg.what = 1002 ;
						   msg.obj = data ;
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
	
	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
//			 intent = new Intent(MyHealthRecordActivity.this , YourPhysiqueActivity.class) ;
			 intent = new Intent(MyHealthRecordActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		case R.id.right:
			 intent = new Intent(MyHealthRecordActivity.this , TestBodyActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// BMI	 
		case R.id.text_bmi_tip:
			 if(isBMIshow){
				 text_bmi_tip_show.setVisibility(View.VISIBLE);
				 isBMIshow = false ;
			 }else {
				 text_bmi_tip_show.setVisibility(View.GONE);
				 isBMIshow = true ;	
			 }
			 break; 
	
		// 健康标签	 
		case R.id.text_health:
			 if(isHealth){
				 text_health_show.setVisibility(View.VISIBLE);
				 isHealth = false ;
			 }else {
				 text_health_show.setVisibility(View.GONE);
				 isHealth = true ;
			 }
			 break ;
		
		// 体质	 
		case R.id.text_physique:
			 if(isPhysique){
				 text_physique_show.setVisibility(View.VISIBLE);
				 isPhysique = false ;
			 }else {
				 text_physique_show.setVisibility(View.GONE);
				 isPhysique = true ;
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
						   
						   GlobalData.accountModel = model ;
						   lists = getLists() ;
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
	
	
	private List<PhysiqueBean> getLists(){
		PhysiqueBean bean = null ;
		
		AccountModel info = GlobalData.accountModel ;
		lists.clear(); 
		bean = new PhysiqueBean("气虚" ,info.getTestBodyType3()) ;
		lists.add(bean) ;	
		
		bean = new PhysiqueBean("阴虚" ,info.getTestBodyType2()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("阳虚" ,info.getTestBodyType()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("痰湿" ,info.getTestBodyType4()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("平和" ,info.getTestBodyType9()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("湿热" ,info.getTestBodyType5()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("气郁" ,info.getTestBodyType8()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("血瘀" ,info.getTestBodyType6()) ;
		lists.add(bean) ;
		
		bean = new PhysiqueBean("特禀" ,info.getTestBodyType7()) ;
		lists.add(bean) ;
		
		return lists ;
	}
	
}
