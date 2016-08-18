package com.uphealth.cn.ui.login.fragment.four;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.model.FoodPlanItemModel;
import com.uphealth.cn.model.FoodPlanModel;
import com.uphealth.cn.model.MaterialNutrition;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.eat.UpFoodMenuActivity;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.CircularImage;
import com.uphealth.cn.widget.MyExpandablelistview;
import com.uphealth.cn.widget.MyListView;
import com.uphealth.cn.widget.ProgressBarView1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint({ "InflateParams", "HandlerLeak", "RtlHardcoded" })
public class HealthEatFragment extends Fragment implements OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private View view ;
	private MyListView eat_ListView ;
	HealthAdapter adapter ;
	
	private View buttom_view ;
	LayoutInflater inflater ;
	private TextView text_plan , up_plan ;
	
	private PullToRefreshView pullToRefreshView ;
	MyExpandablelistview expandablelistview ;
	private MyExpandableListAdapter expandAdapter;
	
	protected RequestQueue requestQueue = null;
	List<FoodPlanModel> lists = new ArrayList<FoodPlanModel>();
	ProgressBarView1 progressBarViewOne , progressBarViewTwo;
	ProgressBarView1 progressBarViewThree , progressBarViewFour;
	
	private String TAG = "HealthEatFragment" ;
	private List<ArticleModel> articleList = new ArrayList<>() ;
	MainListAdapter listAdapter ;
	
	
	MaterialNutrition materialNutrition = new MaterialNutrition() ;
	
    private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				System.out.println("eatlist222"+lists);
				if(lists.size() != 0){
					 expandAdapter = new MyExpandableListAdapter(getContext(),lists) ;
					 expandablelistview.setAdapter(expandAdapter);
				}
				break;
		
			// 更新四中营养素进度条	
			case 1002:
				setFourProgress() ;
				break ;
				
			case 1003:
				System.out.println(TAG+"1003");
				progressBarViewOne = (ProgressBarView1)view.findViewById(R.id.progressBarViewOne) ;
			    progressBarViewOne.setMax(100);
				progressBarViewOne.setProgress(GlobalData.accountModel.getCellulose()+1);
				new Thread(new Runnable() {
					@Override
					public void run() {
						progressBarViewOne.invalidate();
					}
				}).start();
				break ;
				
			case 1004:
				if(articleList.size() != 0){
					listAdapter = new MainListAdapter(getActivity() , articleList) ;
					eat_ListView.setAdapter(listAdapter);
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
		view = inflater.inflate(R.layout.fragment_health_eat, container, false);
		
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
	     
    }  	
	 
	private void init(){
		 requestQueue = Volley.newRequestQueue(getActivity());
		 inflater = LayoutInflater.from(getActivity()) ;
		 buttom_view = inflater.inflate(R.layout.view_buttom_eat, null) ;
		 buttom_view.findViewById(R.id.text_plan).setOnClickListener(this);
		 buttom_view.findViewById(R.id.up_plan).setOnClickListener(this);
		 expandablelistview = (MyExpandablelistview)view.findViewById(R.id.expandablelistviewId) ;
		 progressBarViewOne = (ProgressBarView1)view.findViewById(R.id.progressBarViewOne) ;
		 progressBarViewTwo = (ProgressBarView1)view.findViewById(R.id.progressBarViewTwo) ;
		 progressBarViewThree = (ProgressBarView1)view.findViewById(R.id.progressBarViewThree) ;
		 progressBarViewFour = (ProgressBarView1)view.findViewById(R.id.progressBarViewFour) ;
		 progressBarViewOne.setMax(100);
		 progressBarViewOne.setProgress(GlobalData.accountModel.getCellulose()+1);
		 progressBarViewTwo.setMax(100);
		 progressBarViewTwo.setProgress(GlobalData.accountModel.getVitamin()+1);
		 progressBarViewThree.setMax(100);
		 progressBarViewThree.setProgress(GlobalData.accountModel.getProtein()+1);
		 progressBarViewFour.setMax(100);
		 progressBarViewFour.setProgress(GlobalData.accountModel.getMinerals()+1);
		 
		 expandablelistview.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int groupPosition) {
				
				expandablelistview.setSelection(groupPosition);
				
				for (int i = 0, count = expandablelistview  
                        .getExpandableListAdapter().getGroupCount(); i < count; i++) {  
                    if (groupPosition != i) {// 关闭其他分组  
                    	expandablelistview.collapseGroup(i);  
                    }  
                }  
			}
		 }) ;
		 
		 LinearLayout layout = (LinearLayout)view.findViewById(R.id.layout);
		 layout.setFocusable(true);
		 layout.setFocusableInTouchMode(true);
		 layout.requestFocus();
		 
		 pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.main_pull_refresh_view) ;
		 pullToRefreshView.setOnHeaderRefreshListener(this);
	  	 pullToRefreshView.setOnFooterRefreshListener(this);
		 pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		 
		 expandablelistview.addFooterView(buttom_view);
		 expandablelistview.setGroupIndicator(null);
		 eat_ListView = (MyListView)view.findViewById(R.id.eat_ListView) ;
		 
		 requestData() ;
		 
		 requestArticle() ;
		 
//		 eat_ListView.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					
//					StringBuilder builder = new StringBuilder() ;
//					builder.append(HtmlUrl.H6_2) ;
//					builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
//					builder.append("&articleId=").append(articleList.get(position).getId()) ; 
//					
//					Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
//					intent.putExtra("titleStr", "资讯详情") ;
//					intent.putExtra("url", builder.toString()) ;
//					intent.putExtra("pitUrl", articleList.get(position).getIcon()) ;
//					intent.putExtra("shareUrl", HtmlUrl.H6_2+"?articleId="+articleList.get(position).getId()) ;
//					intent.putExtra("shareTitle",lists.get(position).getName()) ;
//					
//				}
//			});
		 
	 }
	
	 private void setFourProgress(){
		 progressBarViewOne.setProgress(materialNutrition.getCellulose()+1);
		 progressBarViewTwo.setProgress(materialNutrition.getVitamin()+1);
		 progressBarViewThree.setProgress(materialNutrition.getProtein()+1);
		 progressBarViewFour.setProgress(materialNutrition.getMinerals()+1);
	 }
	 
	 class HealthAdapter extends BaseAdapter{
		 
		private Context context ;
		LayoutInflater inflater ;
		
		@SuppressWarnings("static-access")
		public HealthAdapter(Context context){
			this.context = context ;
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		}

		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null ;
			if(null == convertView){
				convertView = inflater.inflate(R.layout.item_health, null) ;
				viewHolder = new ViewHolder() ;
				
				viewHolder.item_layout = (LinearLayout)convertView.findViewById(R.id.item_layout) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
				viewHolder.text_publish = (TextView)convertView.findViewById(R.id.text_publish) ;
				viewHolder.layout_detail = (LinearLayout)convertView.findViewById(R.id.layout_detail) ;
				viewHolder.layout_bottom = (LinearLayout)convertView.findViewById(R.id.layout_bottom) ;
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag() ;
			}
			
			viewHolder.item_layout.removeAllViews(); 
			final LinearLayout layout = viewHolder.layout ;
			for(int i = 0 ; i < 3 ; i ++){
				 View view = inflater.inflate(R.layout.item_common, null) ;
				 view.setPadding(30, 0, 30, 0);
				 viewHolder.item_layout.addView(view);
			}
			
			viewHolder.image.setOnClickListener(new OnClickListener() {
				
				boolean flag = true ;
				public void onClick(View v) {
					
					if(flag){
						 layout.setVisibility(View.VISIBLE);
						 flag = false ;              
					}else {
						 layout.setVisibility(View.GONE);
						 flag = true ;
					}
				}
			});
			
			// 发布早餐
			viewHolder.text_publish.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity() , PhotoThumbActivity.class) ;
					intent.putExtra("address", "") ;
					intent.putExtra("topic", "#早餐# ") ;
					intent.putExtra("topicId", lists.get(0).getTopicId()) ;
					startActivity(intent);
				}
			});
			
			// 查看详情
			viewHolder.layout_detail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
					intent.putExtra("titleStr", "食材详情") ;
					intent.putExtra("url", HtmlUrl.H3_4) ;
					startActivity(intent);
				}
			});
			
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout item_layout ;
			ImageView image ;
			LinearLayout layout ;
			TextView text_publish ;
			LinearLayout layout_detail ;
			LinearLayout layout_bottom ;
		}
		 
	 }

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.text_plan:
			 StringBuilder builder = new StringBuilder() ;
			 builder.append(HtmlUrl.H3_5).append("?accountId=").append(GlobalData.getUserId(getActivity()))  ;
			 intent = new Intent(getActivity() , HtmlActivity.class) ;
			 intent.putExtra("titleStr", "我的餐单") ;
			 intent.putExtra("url", builder.toString()) ;
			 startActivity(intent);
			 break;
			 
		case R.id.up_plan:
			 intent = new Intent(getActivity() , UpFoodMenuActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}
	
	private static int totalHeight=0;

	public static void setListViewHeight(ListView listView){

	/*得到适配器*/
	HealthAdapter adapter = (HealthAdapter)listView.getAdapter();

	/*遍历控件*/
	for (int i = 0; i < adapter.getCount(); i++) {

	View view = adapter .getView(i, null, listView);

	/*测量一下子控件的高度*/

	view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

	totalHeight+=view.getMeasuredHeight();

	}

	/*控件之间的间隙*/

	totalHeight+=listView.getDividerHeight()*(listView.getCount()-1);

	/*2、赋值给ListView的LayoutParams对象*/

	ViewGroup.LayoutParams params = listView.getLayoutParams();

	params.height = totalHeight;

	listView.setLayoutParams(params);

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
							
							requestaddMaterialNutrition(itemModel.getId()) ;
							
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
        	
        	if(expandablelistview.isGroupExpanded(groupPosition)){
				viewHolder.view.setVisibility(View.GONE);
			}else {
				viewHolder.view.setVisibility(View.VISIBLE);
			}
            
//        	final int index = groupPosition ;
//        	viewHolder.layout.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					viewHolder.view.setTag(index);
//					viewHolder.layout.setTag(view);
//					
//					if(expandablelistview.isGroupExpanded(index)){
//						expandablelistview.collapseGroup(index);  
//						viewHolder.view.setVisibility(View.VISIBLE);
//					}else {
//						viewHolder.view.setVisibility(View.GONE);
//						
//						for (int i = 0, count = expandablelistview  
//	                            .getExpandableListAdapter().getGroupCount(); i < count; i++) {  
//	                        if (index != i) {
//	                        	 expandablelistview.collapseGroup(i); 
//	                        }else {
//	                        	 expandablelistview.expandGroup(index) ;
//							}  
//	                        
//	                        // 同时判断之前的是否展开
//	                        if(expandablelistview.isGroupExpanded(i)){
//	                        	   
//	                        	   if(viewHolder.view.getTag().equals(i) ){
////	                        		    viewHolder.view.setVisibility(View.GONE);
//	                        		    ((View)(viewHolder.layout.getTag())).setVisibility(View.GONE);
//	                        		    
//	                        	   }else {
////	                        		    viewHolder.view.setVisibility(View.VISIBLE);
//	                        		    ((View)(viewHolder.layout.getTag())).setVisibility(View.VISIBLE);
//								   }
//	                        }
//	                        
//	                    }  
//						
//					}
//				}
//			});
        	
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

  
    private void requestData(){
    	StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getTakeIngPlan)
		.append("?accountId=").append(GlobalData.getUserId(getActivity())) 
		.append("&planType=4");
		
		System.out.println("eatarg0url" + builder.toString());
		
		lists.clear();
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
						    		    	   lists.add(foodModel) ;
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
						    		    	   lists.add(foodModel) ;
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
						    		    	   lists.add(foodModel) ;
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
						    		    	   lists.add(foodModel) ;
					    		    	   }
				    		    	   } 
				    		    	   
				    		     }
				    		     
				    	  }
				     
				     System.out.println("eatlist="+lists);	  
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
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);		
    }
    
    // 请求食材营养信息
    private void requestaddMaterialNutrition(String materialId){
    	StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getMaterial)
		.append("?accountId=").append(GlobalData.getUserId(getActivity()))
		.append("&materialId=").append(materialId) ;
		
		System.out.println("api" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	JSONObject json;
				try {
					   json = new JSONObject(arg0);
					   int result = json.getInt("result") ;
		               String data = json.getString("data") ;
		               String reason = json.getString("reason") ;
		            	
		               if(result == 1){
		            	   materialNutrition = new Gson().fromJson(data,
		       					new TypeToken<MaterialNutrition>() {
		       					}.getType());
		            	   handler.sendEmptyMessage(1002) ;
		            	   
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
     
   private void requestArticle(){
	   StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getArticleList)
		.append("?page=").append(1) 
		.append("&pageSize=").append(10)
		.append("&module=2") ;
		
		System.out.println("moduleapi=" + builder.toString());
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
//							Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show() ;
						}
					    
				} catch (JSONException e) {
					e.printStackTrace();
				}
           }
       }, new Response.ErrorListener() {

           @Override
           public void onErrorResponse(VolleyError arg0) {
           	
//               showToast(ErrorMsg.net_error) ;
           }
       });
       //为此get请求设置一个Tag属性
       stringRequest.setTag("GET_TAG");
       //将此get请求加入
       requestQueue.add(stringRequest);	 
	   
   } 
	 
}
