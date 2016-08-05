package com.uphealth.cn.ui.login.fragment.four;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.CommonAdapter;
import com.uphealth.cn.adapter.MainListAdapter;
import com.uphealth.cn.bean.CommonBean;
import com.uphealth.cn.bean.SkinModelItem;
import com.uphealth.cn.bean.SkinPlansBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.model.SkinIndex;
import com.uphealth.cn.model.SkinModel;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.ui.login.skin.SkinBeforeActivity;
import com.uphealth.cn.ui.login.skin.SkinMoreActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyGridView;
import com.uphealth.cn.widget.MyListView;
import com.uphealth.cn.widget.ProgressBarView1;
import com.uphealth.cn.widget.refresh.PullToRefreshView;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnFooterRefreshListener;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnHeaderRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @description 美妆护肤 
 * @data 2016年7月3日

 * @author jun.wang
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class BeautyAdornFragmentNew extends Fragment implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener{
	
	private View view ;
	private PullToRefreshView pullToRefreshView ;	
	protected RequestQueue requestQueue = null;
	LoadImage loadImage ;
	
	private TextView text_name_skin ;
	private List<SkinModel> lists = new ArrayList<SkinModel>() ;
	private List<SkinModelItem> itemLists = new ArrayList<SkinModelItem>() ;
	List<SkinPlansBean> plansLists = new ArrayList<>() ;
	
	private ImageView image_skin ;
	private LinearLayout tag_layout ;
	private MyGridView gridView , gridViewAdorn;
	private CommonAdapter commonAdapter , adornAdapter; 
	private LinearLayout layout_skin , layout_skin_item;
	private boolean isSkin = true ;
	
	private ProgressBarView1 skinAcne_progress , skinWhite_progress ;
	private ProgressBarView1 skinWater_progress , skinAntiAging_progress ;
	List<CommonBean> tempList = new ArrayList<>() ;  // 护肤步骤列表
	private String planId = "" ;
	SkinIndex skinIndex = new SkinIndex() ;
	
	private MyListView listView ;
	private List<ArticleModel> articleList = new ArrayList<>() ;
	MainListAdapter listAdapter ;	
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				setSkinData() ;
				break;
				
			case 1002:
				List<CommonBean> tempLists = (List<CommonBean>)msg.obj ;
				setAdornData(tempLists) ; 
				break ;
				
			case 1003:
				setSkinIndex() ;
				break ;
				
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
		view = inflater.inflate(R.layout.fragment_beauty_adorn_new, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		
	
	private void init(){
		skinAcne_progress = (ProgressBarView1)view.findViewById(R.id.skinAcne_progress) ;
		skinWhite_progress = (ProgressBarView1)view.findViewById(R.id.skinWhite_progress) ;
		skinWater_progress = (ProgressBarView1)view.findViewById(R.id.skinWater_progress) ;
		skinAntiAging_progress = (ProgressBarView1)view.findViewById(R.id.skinAntiAging_progress) ;
		
		AccountModel accountModel = GlobalData.accountModel ;
		skinAcne_progress.setProgress((float)accountModel.getSkinAcne()+1);
		skinWhite_progress.setProgress((float)accountModel.getSkinWhite()+1);
		skinWater_progress.setProgress((float)accountModel.getSkinWater()+1);
		skinAntiAging_progress.setProgress((float)accountModel.getSkinAntiAging()+1); 
		
		pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.main_pull_refresh_view) ;
	    pullToRefreshView.setOnHeaderRefreshListener(this);
  	    pullToRefreshView.setOnFooterRefreshListener(this);
	    pullToRefreshView.setLastUpdated(new Date().toLocaleString());
	    requestQueue = Volley.newRequestQueue(getActivity());	
	    loadImage = LoadImage.getInstance() ;
	    
	    text_name_skin = (TextView)view.findViewById(R.id.text_name_skin) ;
	    image_skin = (ImageView)view.findViewById(R.id.image_skin) ;
	    tag_layout = (LinearLayout)view.findViewById(R.id.tag_layout) ;
	    gridView = (MyGridView)view.findViewById(R.id.gridView) ;
	    
	    layout_skin = (LinearLayout)view.findViewById(R.id.layout_skin) ;
	    layout_skin.setOnClickListener(this);
	    layout_skin_item = (LinearLayout)view.findViewById(R.id.layout_skin_item) ;
	    layout_skin_item.setOnClickListener(this);
	    gridViewAdorn = (MyGridView)view.findViewById(R.id.gridViewAdorn) ;
	    view.findViewById(R.id.text_more_skin).setOnClickListener(this);
	    view.findViewById(R.id.layout_detail).setOnClickListener(this);
	    view.findViewById(R.id.text_publish).setOnClickListener(this);
	    listView = (MyListView)view.findViewById(R.id.listView) ;
	    
	    gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				addSkinIndex(tempList.get(position).getId()) ;
			}
		});
	    
		requestSkinData() ;
		
		requestAdornData() ;
		
		requestArticle() ;
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				
//				StringBuilder builder = new StringBuilder() ;
//				builder.append(HtmlUrl.H6_2) ;
//				builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
//				builder.append("&articleId=").append(articleList.get(position).getId()) ; 
//				
//				Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
//				intent.putExtra("titleStr", "资讯详情") ;
//				intent.putExtra("url", builder.toString()) ;
//				intent.putExtra("pitUrl", lists.get(position).getCardImage()) ;
//				intent.putExtra("shareUrl", HtmlUrl.H6_2+"?articleId="+lists.get(position).getId()) ;
//				intent.putExtra("shareTitle",lists.get(position).getName()) ;
//				
//			}
//	     });	
		
		
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
	 
	private void requestSkinData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getTakeIngPlan)
		.append("?accountId=").append(GlobalData.getUserId(getActivity())) 
		.append("&planType=3");
		
		System.out.println("builder" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	System.out.println("hufu" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   JSONObject dataJson = new JSONObject(data) ;
					   String listStr = dataJson.getString("list") ;
					   
					   if(result == 1){
						    
						    JSONArray listArrays = new JSONArray(listStr) ;
						    if(null != listArrays && listArrays.length() != 0){
						    	
						    	   for(int i = 0 ; i < listArrays.length() ; i ++){
						    		       JSONObject listJson = listArrays.getJSONObject(i) ;
						    		       SkinModel model = new SkinModel() ;
						    		       model.setName(listJson.getString("name"));
						    		       model.setId(listJson.getString("id"));
						    		       model.setLevel(listJson.getString("level"));
						    		       model.setTopicId(listJson.getString("topicId"));
						    		       model.setCardImage(listJson.getString("cardImage"));
						    		       model.setTags(listJson.getString("tags"));
						    		       
						    		       String plans = listJson.getString("plans") ;
						    		       System.out.println("beauty=="+plans);
						    		       JSONArray plansArray = new JSONArray(plans) ;
						    		       
						    		       if(null != plansArray && plansArray.length() != 0){
						    		    	   
						    		    	      for(int j = 0 ; j < plansArray.length() ; j ++){
						    		    	    	    SkinModelItem modelItem = new SkinModelItem() ;
						    		    	    	    
						    		    	    	    String itemArray = plansArray.getString(j) ;
						    		    	    	    
						    		    	    	    JSONArray itemItemArray = new JSONArray(itemArray) ;
						    		    	    	    
						    		    	    	    if(null != itemItemArray && itemItemArray.length() != 0){
						    		    	    	    	
						    		    	    	    	  for(int n = 0 ; n < itemItemArray.length() ; n ++ ){
						    		    	    	    		       JSONObject itemItemObj = itemItemArray.getJSONObject(n) ;
						    		    	    	    		       SkinPlansBean bean = new SkinPlansBean() ;
						    		    	    	    		       bean.setId(itemItemObj.getString("id"));
						    		    	    	    		       bean.setImage(itemItemObj.getString("image"));
						    		    	    	    		       bean.setName(itemItemObj.getString("name"));
						    		    	    	    		       plansLists.add(bean) ;
						    		    	    	    	  }
						    		    	    	    	
						    		    	    	    	  modelItem.setLists(plansLists);
						    		    	    	    }
						    		    	    	    
						    		    	    	    itemLists.add(modelItem) ;
						    		    	      }
						    		    	   
						    		    	      model.setPlans(itemLists);
						    		       }
						    		       
						    		      lists.add(model) ; 
						    		      System.out.println("model=lists" + lists); 
						    		      handler.sendEmptyMessage(1001) ;
						    	   }
						    	
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
	
	private void setSkinData(){
		if(null == lists || lists.size() == 0){
			 return ;
		}
		
		SkinModel model = lists.get(0) ;
		text_name_skin.setText(model.getName());
		image_skin.setTag(model.getCardImage());
		loadImage.addTask(model.getCardImage(), image_skin);
		loadImage.doTask();
		
		System.out.println("setSkinData=="+model);
		System.out.println("setSkinData=="+model.getTags());
		
		String[] result = Utils.getTags(model.getTags()) ;
		if(null != result && result.length != 0){
			tag_layout.removeAllViews();
    		for(int i = 0 ; i < result.length ; i ++){
    			   TextView text = new TextView(getActivity()) ;
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
    			   tag_layout.addView(text);
    		}
		}
		
		// 步骤数据
		tempList.clear();
		
		List<SkinPlansBean> tempListTwo = lists.get(0).getPlans().get(0).getLists() ;
		planId = lists.get(0).getId() ;
		
		if(null != tempListTwo && tempListTwo.size() != 0){
			   
			  for(int i = 0 ; i < tempListTwo.size() ; i ++){
				   SkinPlansBean beanItem = tempListTwo.get(i) ;
				    CommonBean bean = new CommonBean() ;
				    bean.setName(beanItem.getName());
				    bean.setUrl(beanItem.getImage());
				    bean.setId(beanItem.getId());
				    tempList.add(bean) ;
			  }
			  
			  
		   if(tempList.size() >= 4){
		    	gridView.setNumColumns(4);
		    }else {
		    	gridView.setNumColumns(tempList.size());
			}
			
		    commonAdapter = new CommonAdapter(getActivity(), tempList) ;
		    gridView.setAdapter(commonAdapter);
		}
	}
	
	private void setSkinIndex(){
		skinAcne_progress.setProgress((float)skinIndex.getSkinAcne()+1);
		skinWhite_progress.setProgress((float)skinIndex.getSkinWhite()+1);
		skinWater_progress.setProgress((float)skinIndex.getSkinWater()+1);
		skinAntiAging_progress.setProgress((float)skinIndex.getSkinAntiAging()+1); 
		
	}
	
	private void setAdornData(final List<CommonBean> lists){
		adornAdapter = new CommonAdapter(getContext(), lists) ;
		gridViewAdorn.setAdapter(adornAdapter);
		
		gridViewAdorn.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity() , SkinBeforeActivity.class) ;
				intent.putExtra("sceneId", lists.get(position).getId()) ;
				intent.putExtra("titleStr", "您需要准备的化妆品") ;
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.layout_skin:
			 if(isSkin){
				 layout_skin_item.setVisibility(View.VISIBLE);
				 isSkin = false ;
			 }else {
				 layout_skin_item.setVisibility(View.GONE);
				 isSkin = true ;
			 }
			 break;
		
		// 更多护肤	 
		case R.id.text_more_skin:
			 intent = new Intent(getActivity(),SkinMoreActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 查看详情	 
		case R.id.layout_detail:
			 StringBuilder builder = new StringBuilder() ;
			 builder.append(HtmlUrl.H5_3).append("?accounId=").append(GlobalData.getUserId(getActivity())) ;
			 builder.append("&planId=").append(lists.get(0).getId()) ;
			 
			 intent = new Intent(getActivity() , HtmlActivity.class) ;
		     intent.putExtra("titleStr", "我的护肤") ;
		     intent.putExtra("url", builder.toString()) ;
		     intent.putExtra("noRight", "noRight") ;
		     startActivity(intent);
			 break ;
		
		// 发布护肤	 
		case R.id.text_publish:
			 GlobalData.dynamicTopic = "#护肤#" ;
			 GlobalData.dynamicTopicId = lists.get(0).getTopicId() ;
			 GlobalData.dynamicAddress = "所在位置" ;
			 GlobalData.dynamicVideoUrl = "" ;
			 intent = new Intent(getActivity() , PhotoThumbActivity.class) ;
//			 intent.putExtra("address", "") ;
//			 intent.putExtra("topic", "#护肤#") ;
//			 intent.putExtra("topicId", lists.get(0).getTopicId()) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	} 
	 
	/**
	 * 美妆场景
	 */
	private void requestAdornData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getAdorn) ;
		
		System.out.println("getAdornmeizhuang" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	System.out.println("getAdornmeizhuang" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String result = json.getString("result") ;
					   String data = json.getString("data") ;
					   
					   if(result.equals("1")){
						   
						    JSONArray array = new JSONArray(data) ;
						    if(array.length() != 0){
						    	  List<CommonBean> tempLists = new ArrayList<>() ;  
						    	  for(int i = 0 ; i < array.length() ; i ++){
						    		      CommonBean bean = new CommonBean() ;
						    		      JSONObject obj = array.getJSONObject(i) ;
						    		      bean.setId(obj.getString("id"));
						    		      bean.setName(obj.getString("label"));
						    		      bean.setUrl(obj.getString("value"));
						    		      tempLists.add(bean) ;
						    	  }
						    	  
						    	  Message msg = new Message() ;
						    	  msg.what = 1002 ;
						    	  msg.obj = tempLists ;
						    	  handler.sendMessage(msg) ;
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
	
	private void addSkinIndex(String id){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.addSkinIndex) 
		.append("?accountId=").append(GlobalData.getUserId(getActivity()))
		.append("&planId=").append(planId) 
		.append("&skinStepId=").append(id) ;
		
		System.out.println("meizhuang" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	System.out.println("meizhuang" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   String reason = json.getString("reason") ;
					   
					   if(result == 1){
						   skinIndex = new Gson().fromJson(data,
			    					new TypeToken<SkinIndex>() {
			    					}.getType());
						   handler.sendEmptyMessage(1003) ;
					   }else {
						   Toast.makeText(getActivity(), reason+"", Toast.LENGTH_SHORT).show() ;
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
	
	private void requestArticle(){
	    StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getArticleList)
		.append("?page=").append(1) 
		.append("&pageSize=").append(10)
		.append("&module=3,4") ;
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
            
           @Override
           public void onResponse(String arg0) {
           	
           	System.out.println("arg0000000" + arg0);
           	
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
           	
           }
       });
       //为此get请求设置一个Tag属性
       stringRequest.setTag("GET_TAG");
       //将此get请求加入
       requestQueue.add(stringRequest);	 
   } 		
	

}
