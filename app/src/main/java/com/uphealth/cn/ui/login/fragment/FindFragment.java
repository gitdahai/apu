package com.uphealth.cn.ui.login.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.CircleAdapter;
import com.uphealth.cn.adapter.CommonAdapter;
import com.uphealth.cn.adapter.MainListAdapter;
import com.uphealth.cn.adapter.MainMenuAdapter;
import com.uphealth.cn.adapter.SportAdapterTwo;
import com.uphealth.cn.bean.CommonBean;
import com.uphealth.cn.bean.FunctionBean;
import com.uphealth.cn.bean.SkinModelItem;
import com.uphealth.cn.bean.SkinPlansBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.interf.ScrollViewListener;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.model.FoodPlanItemModel;
import com.uphealth.cn.model.FoodPlanModel;
import com.uphealth.cn.model.LoginModel;
import com.uphealth.cn.model.SkinModel;
import com.uphealth.cn.model.SportPlanModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.HealthInfoSetting;
import com.uphealth.cn.ui.login.LoginActivity;
import com.uphealth.cn.ui.login.PersonActivity;
import com.uphealth.cn.ui.login.center.ActivitySetting;
import com.uphealth.cn.ui.login.eat.BreakfastRankingActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.fragment.four.HealthEatFragment.MyExpandableListAdapter;
import com.uphealth.cn.ui.login.health.CommentActivity;
import com.uphealth.cn.ui.login.health.WeightActivity;
import com.uphealth.cn.ui.login.home.MainActivity;
import com.uphealth.cn.ui.login.home.MainTwoActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.CircularImage;
import com.uphealth.cn.widget.DragGridViewOlder;
import com.uphealth.cn.widget.MyExpandablelistview;
import com.uphealth.cn.widget.MyGallery;
import com.uphealth.cn.widget.MyGridView;
import com.uphealth.cn.widget.MyListView;
import com.uphealth.cn.widget.ObservableScrollView;
import com.uphealth.cn.widget.refresh.PullToRefreshView;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnFooterRefreshListener;
import com.uphealth.cn.widget.refresh.PullToRefreshView.OnHeaderRefreshListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @description 发现主界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint({ "ClickableViewAccessibility", "ShowToast" })
public class FindFragment extends Fragment implements OnClickListener, ScrollViewListener, OnHeaderRefreshListener, OnFooterRefreshListener {
	private View view ;
	private MyGallery gallery ;
	CircleAdapter adapter ;
	
	private DragGridViewOlder gridview ;
	private ObservableScrollView scrollview ;
	MainMenuAdapter menuAdapter ;
	private List<FunctionBean> list;
	
	private MyListView listView ;
	MainListAdapter listAdapter ;
	private LinearLayout layout_two ;
	
	List<View> viewLists ;
	private TextView text_health , text_exercise , text_beauty , text_style ;
	private int index = 1;
	private TextView text_back ;
	
	private boolean flag = false ;
	
	private PullToRefreshView pullToRefreshView ;
	protected RequestQueue requestQueue = null;
	private List<ArticleModel> lists = new ArrayList<>() ;
	
	private MyExpandableListAdapter expandAdapter;
	LayoutInflater inflater ;
	SportAdapterTwo sportAdapter ; 
	private boolean isSkin = true ;
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				if(lists.size() != 0){
					listAdapter = new MainListAdapter(getActivity() , lists) ;
			    	listView.setAdapter(listAdapter);
				}
				break;
				
			case 1002:
				List<ArticleModel> list = (List<ArticleModel>)msg.obj ;
				if(null != list && list.size() != 0){
					System.out.println("list" + list);
					GlobalData.GALLERY_SIZE = list.size() ;
					adapter = new CircleAdapter(getContext() , list) ;
					gallery.setAdapter(adapter);
				}
				
				requestEatData() ;
				break ;
			
			// 饮食方案	
			case 1003:
				if(eatLists.size() != 0){
					 expandAdapter = new MyExpandableListAdapter(getContext(),eatLists) ;
					 eatExpandablelistview.setAdapter(expandAdapter);
				}
				
				requestSport();
				break ;
			
			// 运动方案	
			case 1004:
				if(SportLists.size() != 0){
					sportListView.setVisibility(View.VISIBLE);
					sportAdapter = new SportAdapterTwo(getActivity() , SportLists) ;
					sportListView.setAdapter(sportAdapter);
				}else {
					sportListView.setVisibility(View.GONE);
				}
				
				requestBeauty() ;
				break ;
		
			// 护肤方案	
			case 1005:
				if(skinLists.size() != 0){
					setSkinData() ;
				}
				break ;

			default:
				break;
			}
		};
	} ;
	
	private MyExpandablelistview eatExpandablelistview ;  // 饮食方案列表
	private MyListView sportListView ;   // 运动方案列表
	private MyListView beautyListView ;  // 美妆方案列表
	private MyListView skinListView ;    // 美妆方案
	
	List<FoodPlanModel> eatLists = new ArrayList<FoodPlanModel>();
	private List<SportPlanModel> SportLists = new ArrayList<>() ; 
	
	// 护肤
	private List<SkinModel> skinLists = new ArrayList<SkinModel>() ;
	private List<SkinModelItem> itemLists = new ArrayList<SkinModelItem>() ;
	List<SkinPlansBean> plansLists = new ArrayList<>() ;
	
	private TextView text_name_skin ;
	private ImageView image_skin ;
	LoadImage loadImage ;
	private LinearLayout skin_tag_layout ,layout_skin_item , layout_skin;
	private MyGridView skinGridView ;
	List<CommonBean> skinTempList = new ArrayList<>() ;  // 护肤步骤列表
	private String skinPlanId = "" ;
	private CommonAdapter commonAdapter ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_find, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        Utils.getTimeMin(); 
        
        init() ;
    }  	
	 
	private void init(){
		inflater = LayoutInflater.from(getActivity()) ;
		requestQueue = Volley.newRequestQueue(getActivity());
		loadImage = LoadImage.getInstance() ;
		gallery = (MyGallery)view.findViewById(R.id.gallery) ;
		text_back = (TextView)view.findViewById(R.id.text_back) ;
		text_back.setText("偏好");
		text_back.setOnClickListener(this);
		view.findViewById(R.id.back).setVisibility(View.VISIBLE);
		view.findViewById(R.id.right).setVisibility(View.VISIBLE);
		((TextView)view.findViewById(R.id.right)).setText("添加");
		view.findViewById(R.id.right).setOnClickListener(this);
		((TextView)view.findViewById(R.id.title)).setText("阿噗");
		layout_two = (LinearLayout)view.findViewById(R.id.layout_two) ;
		pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.main_pull_refresh_view) ;
		pullToRefreshView.setOnHeaderRefreshListener(this);
		pullToRefreshView.setOnFooterRefreshListener(this);
		pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		
		text_health = (TextView)view.findViewById(R.id.text_health) ;
		text_exercise = (TextView)view.findViewById(R.id.text_exercise) ;
		text_beauty = (TextView)view.findViewById(R.id.text_beauty) ;
		text_style = (TextView)view.findViewById(R.id.text_style) ;
		text_health.setOnClickListener(this);
		text_exercise.setOnClickListener(this);
		text_beauty.setOnClickListener(this);
		text_style.setOnClickListener(this);
		viewLists = new ArrayList<View>() ;
		viewLists.clear();
		viewLists.add(text_health) ;
		viewLists.add(text_exercise) ;
		viewLists.add(text_beauty) ;
		viewLists.add(text_style) ;
		
		list = new ArrayList<FunctionBean>() ;
		gridview = (DragGridViewOlder)view.findViewById(R.id.gridView) ;
    	scrollview = (ObservableScrollView)view.findViewById(R.id.scrollview) ;
    	scrollview.setScrollViewListener(this);
    	
    	// 四种方案数据
    	eatExpandablelistview = (MyExpandablelistview)view.findViewById(R.id.eatExpandablelistviewId) ;
    	eatExpandablelistview.setGroupIndicator(null);
    	sportListView = (MyListView)view.findViewById(R.id.sportListView) ;
    	beautyListView = (MyListView)view.findViewById(R.id.beautyListView) ;
    	skinListView = (MyListView)view.findViewById(R.id.skinListView) ;
    	
    	// 护肤
    	text_name_skin = (TextView)view.findViewById(R.id.text_name_skin) ;
  	    image_skin = (ImageView)view.findViewById(R.id.image_skin) ;
  	    skin_tag_layout = (LinearLayout)view.findViewById(R.id.skin_tag_layout) ;
  	    skinGridView = (MyGridView)view.findViewById(R.id.skin_gridView) ;
  	    layout_skin_item = (LinearLayout)view.findViewById(R.id.layout_skin_item) ;
  	    layout_skin = (LinearLayout)view.findViewById(R.id.layout_skin) ;
  	    layout_skin.setOnClickListener(this);
  	    view.findViewById(R.id.skin_layout_detail).setOnClickListener(this);
	    view.findViewById(R.id.skin_text_publish).setOnClickListener(this);
    	
    	requestBanner() ;
    	
//    	scrollview.setScrollViewListener(new ScrollViewListener() {
//			@Override
//			public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
//					int oldx, int oldy) {
//				
////				if(y > 380){
////					layout_two.setVisibility(View.VISIBLE);
////					gridview.setVisibility(View.GONE);
////					gallery.setVisibility(View.GONE);
////				}
//			}
//		});
    	
    	gridview.setFocusable(false);
    	gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = null ;
				
				switch (position) {
				case 0:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 100);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(1) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 1) ;
					startActivity(intent);
					break;
					
				case 1:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(2) ;
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 2) ;
					startActivity(intent);
					break ;
					
				case 2:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(3) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 3) ;
					startActivity(intent);
					break ; 
					
				case 3:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(4) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 4) ;
					startActivity(intent);
					break ; 			

				default:
					break;
				}
			}
		});
    	
    	listView = (MyListView)view.findViewById(R.id.listView) ;
//    	listAdapter = new MainListAdapter(getActivity()) ;
//    	listView.setAdapter(listAdapter);
    	listView.setFocusable(false);
    	
//    	listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				System.out.println("accountId" + GlobalData.getUserId(getActivity()));
//				
//				StringBuilder builder = new StringBuilder() ;
//				builder.append(HtmlUrl.H6_2) ;
//				builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
//				builder.append("&articleId=").append(lists.get(position).getId()) ; 
//				
//				Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
//				intent.putExtra("titleStr", "资讯详情") ;
//				intent.putExtra("url", builder.toString()) ;
//				intent.putExtra("pitUrl", lists.get(position).getIcon()) ;
//				intent.putExtra("shareUrl", HtmlUrl.H6_2+"?articleId="+lists.get(position).getId()) ;
//				intent.putExtra("shareTitle",lists.get(position).getName()) ;
//				startActivity(intent);
//				
//			}
//		});
    	
    	initGridData() ;
    	
//    	requestData() ;
	}

    private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getArticleList)
		.append("?page=").append(1) 
		.append("&pageSize=").append(10) ;
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("arg0" + arg0);
            	
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
	
	private void initGridData(){
		FunctionBean bean = new FunctionBean() ;
		list.add(bean) ;
		
		FunctionBean bean2 = new FunctionBean() ;
		list.add(bean2) ;
		
		FunctionBean bean3 = new FunctionBean() ;
		list.add(bean3) ;
		
		FunctionBean bean4 = new FunctionBean() ;
		list.add(bean4) ;
		
		gridview.setItems(list);
    	menuAdapter = new MainMenuAdapter(getActivity()) ;
    	gridview.setAdapter(menuAdapter);
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		
		// 体重设置
		case R.id.right:
			 intent = new Intent(getActivity() , WeightActivity.class) ;
			 startActivity(intent);
			 break;
			
		case R.id.text_health:
			 uplateState(text_health) ;
			 break ;
			 
		case R.id.text_exercise:
			 uplateState(text_exercise) ;
			 break ;
			 
		case R.id.text_beauty:
			 uplateState(text_beauty) ;
			 break ;
			 
		case R.id.text_style:
			 uplateState(text_style) ;
			 break ;		
	
		// 偏好	 
		case R.id.text_back:
			 intent = new Intent(getActivity() , HealthInfoSetting.class) ;
		     startActivity(intent);
			 break ;
			 
		case R.id.layout_skin:
			 if(isSkin){
				 layout_skin_item.setVisibility(View.VISIBLE);
				 isSkin = false ;
			 }else {
				 layout_skin_item.setVisibility(View.GONE);
				 isSkin = true ;
			 }
			 break;
			 
		// 查看详情	 
		case R.id.skin_layout_detail:
			 StringBuilder builder = new StringBuilder() ;
			 builder.append(HtmlUrl.H5_3).append("?accounId=").append(GlobalData.getUserId(getActivity())) ;
			 builder.append("&planId=").append(skinLists.get(0).getId()) ;
			 
			 intent = new Intent(getActivity() , HtmlActivity.class) ;
		     intent.putExtra("titleStr", "我的护肤") ;
		     intent.putExtra("url", builder.toString()) ;
		     intent.putExtra("noRight", "noRight") ;
		     startActivity(intent);
			 break ;
		
		// 发布护肤	 
		case R.id.skin_text_publish:
			 GlobalData.dynamicTopic = "#护肤#" ;
			 GlobalData.dynamicTopicId = skinLists.get(0).getTopicId() ;
			 GlobalData.dynamicAddress = "所在位置" ;
			 GlobalData.dynamicVideoUrl = "" ;
			 intent = new Intent(getActivity() , PhotoThumbActivity.class) ;
			 startActivity(intent);
			 break ;		 

		default:
			break;
		}
		
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		
//		if(!GlobalData.isStay && y > 20){
//			 gridview.setVisibility(View.VISIBLE);
//			 gallery.setVisibility(View.VISIBLE);
//			 handler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
////					layout_two.setVisibility(View.VISIBLE);
////					gridview.setVisibility(View.GONE);
////					gallery.setVisibility(View.GONE);
//					
//					Intent intent = new Intent(getActivity() , MainTwoActivity.class) ;
//					startActivity(intent);
//				}
//			}, 1000) ; 
//		}else {
//			
//			if(y > 380){
//				
////				gallery.setVisibility(View.GONE);
////				gridview.setVisibility(View.GONE);
////				layout_two.setVisibility(View.VISIBLE);
//			}
//			
//		}
		
	} 
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
    private void toward(final int index){
		
		switch (index) {
		case 1:
			uplateState(text_health) ;
			break;
			
		case 2:
			uplateState(text_exercise) ;
		    break ;	   
		    
		case 3:
			uplateState(text_beauty) ;
		    break ;
		    
		case 4:
			uplateState(text_style) ;
		    break ;		    

		default:
			break;
		}
	}
	
	private void uplateState(final TextView textView){
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				Drawable drawable= getResources().getDrawable(R.drawable.main_line);  
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
				
				for(int i = 0 ; i < viewLists.size() ; i ++){
					  
					   if(viewLists.get(i).equals(textView)){
						   textView.setCompoundDrawables(null,null,null,drawable);  
					   }else {
						   ((TextView)viewLists.get(i)).setCompoundDrawables(null,null,null,null);  
				       }
				}
				
			}
		}, 1000) ;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				pullToRefreshView.onFooterRefreshComplete();
//				gridViewData.add(R.drawable.pic1);
//				myAdapter.setGridViewData(gridViewData);
				Toast.makeText(getActivity(), "加载更多数据!", 0).show();
			}

		}, 3000);
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

		}, 3000);
	}		
	
	private void requestBanner(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getArticleList)
		.append("?page=").append(1) 
		.append("&pageSize=").append(4)
		.append("&banner=1") ;
		
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
					    	List<ArticleModel> lists = new Gson().fromJson(list,
			    					new TypeToken<List<ArticleModel>>() {
			    					}.getType());
					    	
					    	System.out.println("lists0000"+lists);
					    	Message msg = new Message() ;
					    	msg.what = 1002 ;
					    	msg.obj = lists ;
					    	handler.sendMessage(msg) ;
					    }else {
//							Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show() ;
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
	
	/**
	 * 饮食方案列表
	 */
	private void requestEatData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getTakeIngPlan)
		.append("?accountId=").append(GlobalData.getUserId(getActivity())) 
		.append("&planType=4");
		
		System.out.println("eatarg0url" + builder.toString());
		
		eatLists.clear();
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("eatarg0" + arg0);
            	 
            	try {
				    JSONObject json = new JSONObject(arg0) ;
				    int result = json.getInt("result") ;
				    String reason = json.getString("reason") ;
				    String data = json.getString("data") ;
				    JSONObject jsonItem = new JSONObject(data) ;
				    String list = jsonItem.getString("list") ;
				    System.out.println("eatlist00" + list);
				    if(result == 1){
				    	  JSONArray array = new JSONArray(list) ;
				    	  for(int i = 0 ; i < array.length() ; i ++){
				    		     JSONObject jsonObject = array.getJSONObject(i) ;
				    		     
				    		     String tags = jsonObject.getString("tags") ;
				    		     String id = jsonObject.getString("id") ;
				    		     String name = jsonObject.getString("name") ;
				    		     String cardImage = jsonObject.getString("cardImages") ;
				    		     String topicId = jsonObject.getString("topicId") ;
				    		     
				    		     String plans = jsonObject.getString("plans") ;
				    		     System.out.println("eatplans==" + plans);
				    		     JSONArray arrayItem = new JSONArray(plans) ;
				    		     
				    		     for(int j = 0 ; j < arrayItem.length() ; j ++){
				    		    	 
				    		    	   // 取早餐
				    		    	   JSONObject item = arrayItem.getJSONObject(j) ;
				    		    	   if(item.has("moring")){
				    		    		   String moring = item.getString("moring") ;
					    		    	   JSONArray morArray = new JSONArray(moring) ;
					    		    	   List<FoodPlanItemModel> itemLists = new ArrayList<FoodPlanItemModel>() ;
					    		    	   for(int m = 0 ; m < morArray.length() ; m ++ ){
					    		    		      JSONObject morJson = morArray.getJSONObject(m) ;
					    		    		      FoodPlanItemModel itemModel = new FoodPlanItemModel() ; 
					    		    		      String food = morJson.getString("material") ;
					    		    		      JSONObject foodObject = new JSONObject(food) ;
					    		    		      itemModel.setId(foodObject.getString("id")) ;
					    		    		      itemModel.setName(foodObject.getString("name"));
					    		    		      itemModel.setIcon(foodObject.getString("icon"));
					    		    		      // 判断是否有数据
					    		    		      if(!TextUtils.isEmpty(foodObject.getString("name"))){
					    		    		    	  itemLists.add(itemModel) ;
					    		    		      }
					    		    	   }
					    		    	   // 加入早餐标签
					    		    	   if(null != itemLists && itemLists.size() != 0){
					    		    		   FoodPlanModel foodModel = new FoodPlanModel() ;
					    		    		   foodModel.setCardImage(cardImage);
					    		    		   foodModel.setId(id);
					    		    		   foodModel.setItemName("早餐");
					    		    		   foodModel.setTags(tags);
					    		    		   foodModel.setTopicId(topicId);
					    		    		   foodModel.setPlans(itemLists);
					    		    		   
					    		    		   // 如果当前时间是早晨段
					    		    		   if(Utils.getTimeMin() == 1){
					    		    			   eatLists.add(foodModel) ;
					    		    		   }
					    		    		   
					    		    	   }
				    		    	   }
				    		    	   
				    		    	   // 取中餐
				    		    	   if(item.has("noon")){
				    		    		   String moring = item.getString("noon") ;
					    		    	   JSONArray morArray = new JSONArray(moring) ;
					    		    	   List<FoodPlanItemModel> itemLists = new ArrayList<FoodPlanItemModel>() ;
					    		    	   for(int m = 0 ; m < morArray.length() ; m ++ ){
					    		    		      JSONObject morJson = morArray.getJSONObject(m) ;
					    		    		      FoodPlanItemModel itemModel = new FoodPlanItemModel() ; 
					    		    		      String food = morJson.getString("material") ;
					    		    		      JSONObject foodObject = new JSONObject(food) ;
					    		    		      itemModel.setId(foodObject.getString("id")) ;
					    		    		      itemModel.setName(foodObject.getString("name"));
					    		    		      itemModel.setIcon(foodObject.getString("icon"));
					    		    		      // 判断是否有数据
					    		    		      if(!TextUtils.isEmpty(foodObject.getString("name"))){
					    		    		    	  itemLists.add(itemModel) ;
					    		    		      }
					    		    	   }
					    		    	   // 加入中餐餐标签
					    		    	   if(null != itemLists && itemLists.size() != 0){
					    		    		   FoodPlanModel foodModel = new FoodPlanModel() ;
					    		    		   foodModel.setCardImage(cardImage);
					    		    		   foodModel.setId(id);
					    		    		   foodModel.setItemName("中餐");
					    		    		   foodModel.setTags(tags);
					    		    		   foodModel.setTopicId(topicId);
					    		    		   foodModel.setPlans(itemLists);
					    		    		   
					    		    		   // 如果当前时间是中餐
					    		    		   if(Utils.getTimeMin() == 2){
					    		    			   eatLists.add(foodModel) ;
					    		    		   }
					    		    	   }
				    		    	   }
//				    		    	   
				    		    	   // 取晚餐
				    		    	   if(item.has("night")){
				    		    		   String moring = item.getString("night") ;
					    		    	   JSONArray morArray = new JSONArray(moring) ;
					    		    	   List<FoodPlanItemModel> itemLists = new ArrayList<FoodPlanItemModel>() ;
					    		    	   for(int m = 0 ; m < morArray.length() ; m ++ ){
					    		    		      JSONObject morJson = morArray.getJSONObject(m) ;
					    		    		      FoodPlanItemModel itemModel = new FoodPlanItemModel() ; 
					    		    		      String food = morJson.getString("material") ;
					    		    		      JSONObject foodObject = new JSONObject(food) ;
					    		    		      itemModel.setId(foodObject.getString("id")) ;
					    		    		      itemModel.setName(foodObject.getString("name"));
					    		    		      itemModel.setIcon(foodObject.getString("icon"));
					    		    		      // 判断是否有数据
					    		    		      if(!TextUtils.isEmpty(foodObject.getString("name"))){
					    		    		    	  itemLists.add(itemModel) ;
					    		    		      }
					    		    	   }
					    		    	   // 加入晚餐餐标签
					    		    	   if(null != itemLists && itemLists.size() != 0){
					    		    		   FoodPlanModel foodModel = new FoodPlanModel() ;
					    		    		   foodModel.setCardImage(cardImage);
					    		    		   foodModel.setId(id);
					    		    		   foodModel.setItemName("晚餐");
					    		    		   foodModel.setTags(tags);
					    		    		   foodModel.setTopicId(topicId);
					    		    		   foodModel.setPlans(itemLists);
					    		    		   
					    		    		   // 如果当前时间是晚餐段
					    		    		   if(Utils.getTimeMin() == 3){
					    		    			   eatLists.add(foodModel) ;
					    		    		   }
					    		    		   
					    		    	   }
				    		    	   } 
//				    		    	   
				    		    	   // 取加餐
				    		    	   if(item.has("other")){
				    		    		   String other = item.getString("other") ;
					    		    	   JSONArray morArray = new JSONArray(other) ;
					    		    	   List<FoodPlanItemModel> itemLists = new ArrayList<FoodPlanItemModel>() ;
					    		    	   for(int m = 0 ; m < morArray.length() ; m ++ ){
					    		    		      JSONObject morJson = morArray.getJSONObject(m) ;
					    		    		      FoodPlanItemModel itemModel = new FoodPlanItemModel() ; 
					    		    		      String food = morJson.getString("material") ;
					    		    		      JSONObject foodObject = new JSONObject(food) ;
					    		    		      itemModel.setId(foodObject.getString("id")) ;
					    		    		      itemModel.setName(foodObject.getString("name"));
					    		    		      itemModel.setIcon(foodObject.getString("icon"));
					    		    		      // 判断是否有数据
					    		    		      if(!TextUtils.isEmpty(foodObject.getString("name"))){
					    		    		    	  itemLists.add(itemModel) ;
					    		    		      }
					    		    	   }
					    		    	   // 加入晚餐餐标签
					    		    	   if(null != itemLists && itemLists.size() != 0){
					    		    		   FoodPlanModel foodModel = new FoodPlanModel() ;
					    		    		   foodModel.setCardImage(cardImage);
					    		    		   foodModel.setId(id);
					    		    		   foodModel.setItemName("加餐");
					    		    		   foodModel.setTags(tags);
					    		    		   foodModel.setTopicId(topicId);
					    		    		   foodModel.setPlans(itemLists);
					    		    		   
					    		    		   // 如果当前时间是加餐段
					    		    		   if(Utils.getTimeMin() == 4){
					    		    			   eatLists.add(foodModel) ;
					    		    		   }
					    		    	   }
				    		    	   } 
				    		    	   
				    		     }
				    		     
				    	  }
				     
				     System.out.println("eatlist="+lists);	  
				     handler.sendEmptyMessage(1003) ;
				    	
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
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);		
	}
	
	private void requestSport(){
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
						    SportLists = new Gson().fromJson(list,
			    					new TypeToken<List<SportPlanModel>>() {
			    					}.getType());
						    handler.sendEmptyMessage(1004) ;
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
	
	// 美妆护肤
	private void requestBeauty() {
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
						    		       
						    		      skinLists.add(model) ; 
						    		      System.out.println("model=lists" + lists); 
						    		      handler.sendEmptyMessage(1005) ;
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
	
	
	// 饮食数据适配器
	 public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		    private List<FoodPlanModel> lists = new ArrayList<FoodPlanModel>() ;
		    private Context context ;
	        
	        private View itemView ;
	        LoadImage loadImage ;
	        
	        private Integer[] defaultImgs = new Integer[]{
	        	 R.drawable.food_morning ,
	        	 R.drawable.food_lunch ,
	        	 R.drawable.food_dinner ,
	        	 R.drawable.food_add ,	
	        };
	        
	        public MyExpandableListAdapter(Context context , List<FoodPlanModel> lists){
	        	this.context = context ; 
	        	this.lists = lists ; 
	        	loadImage = LoadImage.getInstance() ;
	        }
	        
	        public Object getChild(int groupPosition, int childPosition) {
	        	 return lists.get(groupPosition).getPlans().get(childPosition);
	        }

	        public long getChildId(int groupPosition, int childPosition) {
	            return childPosition;
	        }

	        public int getChildrenCount(int groupPosition) {
	            return 1 ;
	        }    

	        public TextView getGenericView() {
	            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
	                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

	            TextView textView = new TextView(getActivity());
	            textView.setLayoutParams(lp);
	            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
	            textView.setPadding(36, 0, 0, 0);
	            return textView;
	        }
	        
	        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
	                View convertView, ViewGroup parent) {
	            
	            convertView = inflater.inflate(R.layout.item_health_child, null) ;
	            
	            LinearLayout item_layout = (LinearLayout)convertView.findViewById(R.id.item_layout); 
	            item_layout.removeAllViews() ;
	            
				for(int i = 0 ; i < lists.get(groupPosition).getPlans().size() ; i ++){
					 View view = inflater.inflate(R.layout.item_common_two, null) ;
					 TextView itemName = (TextView)view.findViewById(R.id.item_name) ;
					 itemName.setText(lists.get(groupPosition).getPlans().get(i).getName());
					 CircularImage image = (CircularImage)view.findViewById(R.id.headImage) ;
					 image.setTag(lists.get(groupPosition).getPlans().get(i).getIcon());
					 loadImage.addTask(lists.get(groupPosition).getPlans().get(i).getIcon(), image);
					 loadImage.doTask();
					 
					 final FoodPlanItemModel itemModel = lists.get(groupPosition).getPlans().get(i) ;
					 image.setTag(i);
					 
					 if(i == (Integer)image.getTag()){
						  image.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO
//								requestaddMaterialNutrition(itemModel.getId()) ;
							}
						});
					 }
					 
					 LinearLayout layout_scene = (LinearLayout)view.findViewById(R.id.layout_scene) ;
					 layout_scene.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							handler.sendEmptyMessage(1002) ;
						}
					});
					 
					 view.setPadding(30, 0, 30, 0);
					 item_layout.addView(view);
				}
	            
				TextView text_publish = (TextView)convertView.findViewById(R.id.text_publish) ;
				text_publish.setText("#"+lists.get(groupPosition).getItemName()+"#");
				text_publish.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						GlobalData.dynamicTopic = "#"+lists.get(groupPosition).getItemName()+"#";
						GlobalData.dynamicTopicId = lists.get(groupPosition).getTopicId() ;
						GlobalData.dynamicAddress = "所在位置" ;
						GlobalData.dynamicVideoUrl = "" ;
						GlobalData.apuAsk = "0" ;
						
						Intent intent = new Intent(getActivity() , PhotoThumbActivity.class) ;
						startActivity(intent);
					}
				});
				
				LinearLayout layout_detail = (LinearLayout)convertView.findViewById(R.id.layout_detail) ;
				layout_detail.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						StringBuilder builder = new StringBuilder() ;
						builder.append(HtmlUrl.H3_4).append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
						builder.append("&planId=").append(lists.get(groupPosition).getPlans().get(childPosition).getId()) ;
						
						System.out.println("H3_4" + builder.toString());
						Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
						intent.putExtra("titleStr", "食材详情") ;
						intent.putExtra("url", builder.toString()) ;
						startActivity(intent);
					}
				});
				
				
	            return convertView;
	        }

	        public Object getGroup(int groupPosition) {
	            return lists.get(groupPosition);
	        }

	        public int getGroupCount() {
	            return lists.size();
	        }

	        public long getGroupId(int groupPosition) {
	            return groupPosition;
	        }

	        boolean isShow = true ;
	        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	                ViewGroup parent) {
	            
	        	final ViewHolder viewHolder ;
	        	if(null == convertView){
	        		viewHolder = new ViewHolder() ;
	        		convertView = inflater.inflate(R.layout.item_health, null) ;
	        		
	        		viewHolder.view = (View)convertView.findViewById(R.id.itemView) ;
	        		viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layoutAll) ;
	        		viewHolder.tag_layout = (LinearLayout)convertView.findViewById(R.id.tag_layout) ;
	        		viewHolder.name = (TextView)convertView.findViewById(R.id.name) ;
	        		convertView.setTag(viewHolder);
	        	}else {
					viewHolder = (ViewHolder)convertView.getTag() ;
				}
	        	
	        	viewHolder.name.setText(lists.get(groupPosition).getItemName());
	        	viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image) ;
	        	
	        	if(TextUtils.isEmpty(lists.get(groupPosition).getCardImage())){
	        		
	        		try {
	        			    viewHolder.imageView.setBackgroundResource(defaultImgs[groupPosition]);
					} catch (IndexOutOfBoundsException e) {
                         e.printStackTrace();  
					}
	        		
	        	}else {
	        		viewHolder.imageView.setTag(lists.get(groupPosition).getCardImage()); 
	            	loadImage.addTask(lists.get(groupPosition).getCardImage(), viewHolder.imageView);
	            	loadImage.doTask();
				}
	        	
	        	String[] result = Utils.getTags(lists.get(groupPosition).getTags()) ;
	    		if(null != result && result.length != 0){
	    			viewHolder.tag_layout.removeAllViews();
	        		for(int i = 0 ; i < result.length ; i ++){
	        			   TextView text = new TextView(context) ;
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
	        			   viewHolder.tag_layout.addView(text);
	        		}
	    		}
	        	
	        	if(eatExpandablelistview.isGroupExpanded(groupPosition)){
					viewHolder.view.setVisibility(View.GONE);
				}else {
					viewHolder.view.setVisibility(View.VISIBLE);
				}
	        	
	            return convertView;
	        }
	        
	        public boolean isChildSelectable(int groupPosition, int childPosition) {
	            return true;
	        }

	        public boolean hasStableIds() {
	            return true;
	        }
	        
	        public View getItemViewNew(){
	           	return itemView ;
	        }

	    }
	     
	    class ViewHolder{
	    	TextView name ;
	    	ImageView imageView ;
	    	View view ;
	    	LinearLayout layout ;
	    	LinearLayout tag_layout ;
	    }
	
	    private void setSkinData(){
			if(null == skinLists || skinLists.size() == 0){
				 return ;
			}
			
			SkinModel model = skinLists.get(0) ;
			text_name_skin.setText(model.getName());
			image_skin.setTag(model.getCardImage());
			loadImage.addTask(model.getCardImage(), image_skin);
			loadImage.doTask();
			
			System.out.println("setSkinData=="+model);
			System.out.println("setSkinData=="+model.getTags());
			
			String[] result = Utils.getTags(model.getTags()) ;
			if(null != result && result.length != 0){
				skin_tag_layout.removeAllViews();
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
	    			   skin_tag_layout.addView(text);
	    		}
			}
			
			// 步骤数据
			skinTempList.clear();
			
			List<SkinPlansBean> tempListTwo = skinLists.get(0).getPlans().get(0).getLists() ;
			skinPlanId = skinLists.get(0).getId() ;
			
			if(null != tempListTwo && tempListTwo.size() != 0){
				   
				  for(int i = 0 ; i < tempListTwo.size() ; i ++){
					   SkinPlansBean beanItem = tempListTwo.get(i) ;
					    CommonBean bean = new CommonBean() ;
					    bean.setName(beanItem.getName());
					    bean.setUrl(beanItem.getImage());
					    bean.setId(beanItem.getId());
					    skinTempList.add(bean) ;
				  }
				  
				  
			   if(skinTempList.size() >= 4){
			    	skinGridView.setNumColumns(4);
			    }else {
			    	skinGridView.setNumColumns(skinTempList.size());
				}
				
			    commonAdapter = new CommonAdapter(getActivity(), skinTempList) ;
			    skinGridView.setAdapter(commonAdapter);
			}
     }	    
	    
}
