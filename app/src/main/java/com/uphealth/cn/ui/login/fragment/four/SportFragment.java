package com.uphealth.cn.ui.login.fragment.four;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.uphealth.cn.adapter.MainListAdapter;
import com.uphealth.cn.adapter.SportAdapterTwo;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.model.SceneModel;
import com.uphealth.cn.model.SportPlanModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyListView;
import com.uphealth.cn.widget.ProgressBarView1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description 运动界面
 * @data 2016年5月26日

 * @author jun.wang
 */
public class SportFragment extends Fragment implements OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private View view ;
	private View bottomView ;
	private MyListView listView ;
	SportAdapterTwo adapter ; 
	LayoutInflater inflater ;
	private PullToRefreshView pullToRefreshView ;
	
	protected RequestQueue requestQueue = null;
	private List<SportPlanModel> lists = new ArrayList<>() ; 
	SportPlanModel firstModel = new SportPlanModel() ; 
	TextView first_name ;
	private ImageView first_image ;
	private LinearLayout first_tag_layout ;
	
	List<SceneModel> sceneLists = new ArrayList<>() ;
	LoadImage loadImage ;
	private List<ArticleModel> articleList = new ArrayList<>() ;
	MainListAdapter listAdapter ;
	
	private ProgressBarView1 progressBarViewOne ;
	
	private LinearLayout layout_scene ;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				if(lists.size() != 0){
					firstModel = lists.get(0) ;
//					lists.remove(0) ;
//					adapter = new SportAdapterTwo(getActivity() , lists) ;
//					listView.setAdapter(adapter);
					setFirstData() ;
				}
				break;
				
			case 1002:
				setScene() ;
				break;		
				
			case 1004:
				if(articleList.size() != 0){
					listAdapter = new MainListAdapter(getActivity() , articleList) ;
					listView.setAdapter(listAdapter);
				}
				break ;

			default:
				break;
			}
			
		};
	} ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_sport, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		
	 
	@Override
	public void onResume() {
		super.onResume();
		
	} 
	
	private void init(){
		requestQueue = Volley.newRequestQueue(getActivity());
		listView = (MyListView)view.findViewById(R.id.listView) ;
		listView.setFocusable(false);
		inflater = LayoutInflater.from(getActivity()) ;
		bottomView = inflater.inflate(R.layout.view_buttom_sport, null) ;
//		listView.addFooterView(bottomView);
//		listView.addHeaderView(bottomView);
		view.findViewById(R.id.up_plan).setOnClickListener(this);
		
	    pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.main_pull_refresh_view) ;
	    pullToRefreshView.setOnHeaderRefreshListener(this);
  	    pullToRefreshView.setOnFooterRefreshListener(this);
	    pullToRefreshView.setLastUpdated(new Date().toLocaleString());
	    
	    layout_scene = (LinearLayout)view.findViewById(R.id.layout_scene) ;
	    first_name = (TextView)view.findViewById(R.id.first_name) ;
	    loadImage = LoadImage.getInstance() ;
	    first_image = (ImageView)view.findViewById(R.id.first_image) ;
	    first_tag_layout = (LinearLayout)view.findViewById(R.id.first_tag_layout) ;
	    progressBarViewOne = (ProgressBarView1)view.findViewById(R.id.progressBarViewOne) ;
		
		requestSceneData() ;
		
		requestData() ;
		
		requestArticle() ;
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					
//					System.out.println("articleList"+articleList);
//					System.out.println("articleList"+position);
//					
//					try {   
//							StringBuilder builder = new StringBuilder() ;
//							builder.append(HtmlUrl.H6_2) ;
//							builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
//							builder.append("&articleId=").append(lists.get(position).getId()) ; 
//							
//							Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
//							intent.putExtra("titleStr", "资讯详情") ;
//							intent.putExtra("url", builder.toString()) ;
//							intent.putExtra("pitUrl", lists.get(position).getCardImage()) ;
//							intent.putExtra("shareUrl", HtmlUrl.H6_2+"?articleId="+lists.get(position).getId()) ;
//							intent.putExtra("shareTitle",lists.get(position).getName()) ;
//						
//					} catch (IndexOutOfBoundsException e) {
//						e.printStackTrace();
//					}
//				}
//		});	
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.up_plan:
			 Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
			 intent.putExtra("titleStr", "阿噗的运动-多次") ;
			 intent.putExtra("url", HtmlUrl.H4_11) ;
			 startActivity(intent);
			 break;

		default:
			break;
		}
	} 
	
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getTakeIngPlan)
		.append("?accountId=").append(GlobalData.getUserId(getActivity())) 
		.append("&planType=1");
		
		System.out.println("builder" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	System.out.println("yundong" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String result = json.getString("result") ;
					   String data = json.getString("data") ;
					   JSONObject dataJson = new JSONObject(data) ;
					   String list = dataJson.getString("list") ;
					   
					   if(result.equals("1")){
						    lists = new Gson().fromJson(list,
			    					new TypeToken<List<SportPlanModel>>() {
			    					}.getType());
						    handler.sendEmptyMessage(1001) ;
						    System.out.println("lists==" + lists);
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
	
	private void requestSceneData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getScene) ;
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("arg0" + arg0);
            	
            	try {
					    JSONObject json = new JSONObject(arg0) ;
					    String result = json.getString("result") ;
					    String data = json.getString("data") ;
					    
					    if(result.equals("1")){
					    	sceneLists = new Gson().fromJson(data,
			    					new TypeToken<List<SceneModel>>() {
			    					}.getType());
					    	System.out.println("model" + sceneLists);
					    	handler.sendEmptyMessage(1002) ;
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

	@SuppressLint("InflateParams")
	private void setScene(){
		layout_scene.removeAllViews(); 
		
		for(int i = 0 ; i < sceneLists.size() ; i ++){
			   View view = inflater.inflate(R.layout.item_common, null) ;
			   
			   SceneModel model = sceneLists.get(i) ;
			   view.setPadding(0, 0, 50, 0);
			   layout_scene.addView(view);
			   view.setTag(i);
			   
			   TextView name = (TextView)view.findViewById(R.id.item_name) ;
			   name.setText(model.getLabel());
			   ImageView image = (ImageView)view.findViewById(R.id.image) ;
			   image.setTag(model.getValue());
			   loadImage.addTask(model.getValue(), image);
			   loadImage.doTask();
			   
			   final int index = i ;
			   view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(index == (int)v.getTag()){
						 Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
						 intent.putExtra("titleStr", "阿噗的运动-单次") ;
						 intent.putExtra("url", HtmlUrl.H4_9) ;
						 intent.putExtra("noRight", "noRight") ;
						 getActivity().startActivity(intent);
					}
				}
			});
		}	
	}
	
	private void setFirstData(){
		first_name.setText(firstModel.getName());
		first_image.setTag(firstModel.getCardImage());
		loadImage.addTask(firstModel.getCardImage(), first_image);
		loadImage.doTask();
		
		String[] result = Utils.getTags(firstModel.getTags()) ;
		if(null != result && result.length != 0){
			first_tag_layout.removeAllViews();
			for(int i = 0 ; i < result.length ; i ++){
				   TextView text = new TextView(getContext()) ;
				   text.setText(result[i]);
				   text.setTextSize(14);
				   text.setBackgroundResource(R.drawable.white_transparent);
				   text.setTextColor(Color.WHITE);
				   text.setGravity(Gravity.CENTER);
				   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						   180, 80) ;
				   params.setMargins(0, 0, 0, 30);
				   params.gravity = Gravity.RIGHT ;
				   text.setLayoutParams(params);
				   first_tag_layout.addView(text);
			}
		}
		
		first_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder builder = new StringBuilder() ;
				builder.append(HtmlUrl.H4_2).append("?planId=").append(firstModel.getId())
				.append("&accountId=").append(GlobalData.getUserId(getActivity())) ;
				
				System.out.println("builder==" + builder.toString());
				
				Intent intent = new Intent(getContext() , HtmlActivity.class) ;
				intent.putExtra("titleStr", "我的训练") ;
				intent.putExtra("url", builder.toString()) ;
				getActivity().startActivity(intent);
			}
		});
		
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

	 private void requestArticle(){
		   StringBuilder builder = new StringBuilder() ;
			builder.append(Contants.getArticleList)
			.append("?page=").append(1) 
			.append("&pageSize=").append(10)
			.append("&module=1") ;
			
			StringRequest stringRequest = new StringRequest(Request.Method.GET,
					builder.toString(), new Response.Listener<String>() {
	            
	           @Override
	           public void onResponse(String arg0) {
	           	
	           	System.out.println("arg0000000运动" + arg0);
	           	
	           	try {
						    JSONObject json = new JSONObject(arg0) ;
						    int result = json.getInt("result") ;
						    String reason = json.getString("reason") ;
						    String data = json.getString("data") ;
						    JSONObject jsonItem = new JSONObject(data) ;
						    String list = jsonItem.getString("list") ;
						    System.out.println("list" + list);
						    if(result == 1){
						    	articleList = new Gson().fromJson(list,
				    					new TypeToken<List<ArticleModel>>() {
				    					}.getType());
						    	
						    	handler.sendEmptyMessage(1004) ;
						    }else {
//								Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show() ;
							}
						    
					} catch (JSONException e) {
						e.printStackTrace();
					}
	           }
	       }, new Response.ErrorListener() {

	           @Override
	           public void onErrorResponse(VolleyError arg0) {
	           	
//	               showToast(ErrorMsg.net_error) ;
	           }
	       });
	       //为此get请求设置一个Tag属性
	       stringRequest.setTag("GET_TAG");
	       //将此get请求加入
	       requestQueue.add(stringRequest);	 
	   } 	
	
	
}
