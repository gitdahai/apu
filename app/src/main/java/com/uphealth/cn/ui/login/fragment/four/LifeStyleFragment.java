package com.uphealth.cn.ui.login.fragment.four;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.customview.PullToRefreshView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.LifeStyleAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description 生活方式 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class LifeStyleFragment extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private View view ;
	private ListView listView ;
	LifeStyleAdapter adapter ;
	private PullToRefreshView pullToRefreshView ;
	protected RequestQueue requestQueue = null;
	private List<ArticleModel> lists = new ArrayList<>() ;
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				if(lists.size() != 0){
					adapter = new LifeStyleAdapter(getActivity() , lists) ;
			    	listView.setAdapter(adapter);
				}
				break;
				
			default:
				break;
			}
		};
	} ;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_life_style, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		

	private void init(){
		listView = (ListView)view.findViewById(R.id.listView) ;
		pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.main_pull_refresh_view) ;
		pullToRefreshView.setOnHeaderRefreshListener(this);
	  	pullToRefreshView.setOnFooterRefreshListener(this);
		pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		
		requestQueue = Volley.newRequestQueue(getActivity());
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				StringBuilder builder = new StringBuilder() ;
				builder.append(HtmlUrl.H6_2) ;
				builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
				builder.append("&articleId=").append(lists.get(position).getId()) ; 
				
				System.out.println("builder==" + builder.toString());
				
				Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
				intent.putExtra("titleStr", lists.get(position).getName()) ;
				intent.putExtra("url", builder.toString()) ;
				intent.putExtra("pitUrl", lists.get(position).getIcon()) ;
				startActivity(intent);
			}
		});
		
		requestData() ;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				pullToRefreshView.onFooterRefreshComplete();
				Toast.makeText(getActivity(), "加载更多数据!", 0).show();
			}

		}, 1000);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				pullToRefreshView.onHeaderRefreshComplete("更新于:"
						+ Calendar.getInstance().getTime().toLocaleString());
				pullToRefreshView.onHeaderRefreshComplete();
				Toast.makeText(getActivity(), "数据刷新完成!", 0).show();
			}

		}, 1000);
	} 
	 
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getArticleList)
		.append("?page=").append(1) 
		.append("&pageSize=").append(10) 
		.append("&module=5,6,7,8") ;
		
		System.out.println("getArticleList+" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getArticleList=" + arg0);
            	
            	try {
					    JSONObject json = new JSONObject(arg0) ;
					    String result = json.getString("result") ;
					    String reason = json.getString("reason") ;
					    String data = json.getString("data") ;
					    JSONObject jsonItem = new JSONObject(data) ;
					    String list = jsonItem.getString("list") ;
					    System.out.println("list" + list);
					    if(result.equals("1")){
					    	  
					    	lists = new Gson().fromJson(list,
			    					new TypeToken<List<ArticleModel>>() {
			    					}.getType());
					    	
					    	System.out.println("lists"+lists);
					    	
					    	handler.sendEmptyMessage(1001) ;
					    	
					    }else {
							Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show() ;
						}
					    
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
//                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
		
	}
	 
}
