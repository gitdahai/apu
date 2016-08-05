package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.uphealth.cn.adapter.CityAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.ProvinceModel;
import com.uphealth.cn.network.ErrorMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @description 选择城市 
 * @data 2016年6月14日

 * @author jun.wang
 */
public class CityActivity extends BaseActivity implements OnClickListener {
	private String parentId ;
	private String name ;
	private CityAdapter cityAdapter ;
	private List<ProvinceModel> list ;
	private ListView listView ;
	
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
		setContentView(R.layout.activity_city);
		
		parentId = getIntent().getStringExtra("parentId") ;
		name = getIntent().getStringExtra("name") ;
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("选择省市");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView)this.findViewById(R.id.listView) ;
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				System.out.println("city" + list.get(position).getId());
				
				GlobalData.isRequestCity = false ;
				GlobalData.cityId = list.get(position).getId() ;
				
				Intent intent = new Intent(CityActivity.this , PersonActivity.class) ;
				Bundle bundle = new Bundle() ;
				bundle.putString("city", name+list.get(position).getName());
				intent.putExtras(bundle) ;
				startActivity(intent);
				finish();
			}
		});
		
		requestData() ;
	}
	
	private void setData(){
		if(null != list){
			cityAdapter = new CityAdapter(this , list) ;
			listView.setAdapter(cityAdapter);
		}
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
        showDialog(); 
    	
    	StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.getAreaList) ;
    	builder.append("?type=3") ;
    	builder.append("&parentId=").append(parentId);
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            
            	try {
            		
        		    JSONObject json = new JSONObject(arg0) ;
				    String data = json.getString("data") ;
				    String reason = json.getString("reason") ;
				    int result = json.getInt("result") ;
				    
				    if(result == 1){
				    	System.out.println("data" + data);
					    
				    	list = new Gson().fromJson(data,
		    					new TypeToken<List<ProvinceModel>>() {
		    					}.getType());
					    
					    handler.sendEmptyMessage(1001) ;
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
