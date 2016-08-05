package com.uphealth.cn.ui.login.eat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.AddressAdapter;
import com.uphealth.cn.bean.AddressBean;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.network.JsonParser;
import com.uphealth.cn.ui.login.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 所在位置 
 * @data 2016年5月25日

 * @author jun.wang
 */
public class MyAddressActivity extends BaseActivity implements OnClickListener {
	AddressAdapter adapter ;
	private ListView listView ;
	RequestQueue requestQueue = null;
	private List<AddressBean> list ;
	private EditText edit ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				System.out.println("返回列表" + list);
				adapter = new AddressAdapter(MyAddressActivity.this , list) ;
				listView.setAdapter(adapter);
				break;

			default:
				break;
			}
			
		};
		
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_address);
		
		init() ;
		
	}

	private void init(){
		((TextView)findViewById(R.id.title)).setText("所在位置");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView)this.findViewById(R.id.listView) ;
		list = new ArrayList<AddressBean>() ;
		edit = (EditText)this.findViewById(R.id.edit) ;
		findViewById(R.id.button_search).setOnClickListener(this);
		findViewById(R.id.text_no).setOnClickListener(this);
		
		 // 检查更新
        requestQueue = Volley.newRequestQueue(this);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GlobalData.dynamicAddress = list.get(position).getName() ;
				Intent intent = new Intent(MyAddressActivity.this , PhotoThumbActivity.class) ;
				startActivity(intent);
			}
		});
        
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.button_search:
			 searchCity() ;
			 break ;
			 
		case R.id.text_no:
			 Intent intent = new Intent(MyAddressActivity.this , PhotoThumbActivity.class) ;
			 intent.putExtra("address", "") ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}
	
	 // 检查更新
    private void searchCity(){
    	showDialog();
    	
    	try {
			   String query = URLEncoder.encode(edit.getText().toString(), "utf-8") ;
			   String region = URLEncoder.encode("131", "utf-8") ;
			   
			   StringBuilder builder = new StringBuilder() ;
			   builder.append("http://api.map.baidu.com/place/v2/suggestion?query=")
			   .append(query).append("&region=").append(region)
			   .append("&output=json&ak=t62tVP3G90TrCyFV79FdIt7zoHd9vhlr") ;
			   
		        StringRequest stringRequest = new StringRequest(Request.Method.GET,
		        		builder.toString(), new Response.Listener<String>() {
		             
		            @Override
		            public void onResponse(String arg0) {

		            	closeDialog() ;
		                list = JsonParser.getAddress(arg0) ;
		                handler.sendEmptyMessage(1001) ;
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
			   
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
	
}
