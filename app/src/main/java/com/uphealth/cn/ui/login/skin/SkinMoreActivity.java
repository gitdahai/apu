package com.uphealth.cn.ui.login.skin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.SkinMoreAdapter;
import com.uphealth.cn.bean.CommonBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.model.SkinModel;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.widget.MyListView;
import com.uphealth.cn.widget.refresh.PullToRefreshView;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnFooterRefreshListener;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnHeaderRefreshListener;

/**
 * 更多阿噗的护肤
 * @description 
 * @data 2016年7月3日

 * @author jun.wang
 */
public class SkinMoreActivity extends BaseActivity implements OnHeaderRefreshListener, OnFooterRefreshListener, OnClickListener {
	private PullToRefreshView pullToRefreshView ;
	private MyListView listview ;
	SkinMoreAdapter adapter ;
	private List<SkinModel> lists = new ArrayList<SkinModel>() ;	
	
    private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				setSkinData() ;
				break;

			default:
				break;
			}
		};
	} ;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skin_more);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("阿噗的护肤");
		
		pullToRefreshView = (PullToRefreshView)this.findViewById(R.id.main_pull_refresh_view) ;
	    pullToRefreshView.setOnHeaderRefreshListener(this);
  	    pullToRefreshView.setOnFooterRefreshListener(this);
	    pullToRefreshView.setLastUpdated(new Date().toLocaleString());	
	    listview = (MyListView)this.findViewById(R.id.listView) ;
	    
	    requestSkinData() ;
	}
	
	private void setSkinData(){
		adapter = new SkinMoreAdapter(this,lists) ;
		listview.setAdapter(adapter);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshView.onHeaderRefreshComplete("更新于:"
						+ Calendar.getInstance().getTime().toLocaleString());
				pullToRefreshView.onHeaderRefreshComplete();
				Toast.makeText(SkinMoreActivity.this, "数据刷新完成!", 0).show();
			}

		}, 1000);
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				pullToRefreshView.onFooterRefreshComplete();
				Toast.makeText(SkinMoreActivity.this, "加载更多数据!", 0).show();
			}

		}, 1000);
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
	
	private void requestSkinData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getSkinPlanList)
		.append("?page=1")
		.append("&pageSize=10") ;
		
		System.out.println("builder" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	System.out.println("hufu" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String result = json.getString("result") ;
					   String data = json.getString("data") ;
					   JSONObject dataJson = new JSONObject(data) ;
					   String listStr = dataJson.getString("list") ;
					   
					   if(result.equals("1")){
//						    lists = new Gson().fromJson(list,
//			    					new TypeToken<List<SkinModel>>() {
//			    					}.getType());
//						    handler.sendEmptyMessage(1001) ;
						    
						   JSONArray listArrays = new JSONArray(listStr) ;
						   if(null != listArrays && listArrays.length() != 0){
						    	
							   for(int i = 0 ; i < listArrays.length() ; i ++){
				    		       JSONObject listJson = listArrays.getJSONObject(i) ;
				    		       SkinModel model = new SkinModel() ;
				    		       model.setName(listJson.getString("name"));
				    		       model.setId(listJson.getString("id"));
				    		       model.setLevel(listJson.getString("level"));
				    		       model.setTopicId(listJson.getString("topicId"));
				    		       model.setType(listJson.getString("type"));
				    		       model.setTags(listJson.getString("tags"));
				    		       model.setCardImage(listJson.getString("cardImage"));
				    		       lists.add(model) ;
							   }   
						    	
							   handler.sendEmptyMessage(1001) ; 
						    }
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
