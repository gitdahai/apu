package com.uphealth.cn.ui.login.eat;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.UpFoodMenuAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.model.FoodPlanItemModel;
import com.uphealth.cn.model.FoodPlanModel;
import com.uphealth.cn.ui.login.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 阿噗的餐单 
 * @data 2016年5月26日

 * @author jun.wang
 */
public class UpFoodMenuActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	UpFoodMenuAdapter adapter ;
	List<FoodPlanModel> lists = new ArrayList<FoodPlanModel>();
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				if(lists.size() != 0){
					 adapter = new UpFoodMenuAdapter(UpFoodMenuActivity.this,lists) ;
					 listView.setAdapter(adapter);
				}
				break;

			default:
				break;
			}
			
		};
		
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_food_menu);
		
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("阿噗的餐单");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.right).setVisibility(View.GONE);
		listView = (ListView)this.findViewById(R.id.listView) ;
//		adapter = new UpFoodMenuAdapter(this) ;
//		listView.setAdapter(adapter);
		
		requestData() ;
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
	    	StringBuilder builder = new StringBuilder() ;
			builder.append(Contants.getFoodPlanList)
			.append("?page=").append(1) 
			.append("&pageSize=").append(10) ;
			
			System.out.println("url" + builder.toString());
			
			lists.clear();
			StringRequest stringRequest = new StringRequest(Request.Method.GET,
					builder.toString(), new Response.Listener<String>() {
	             
	            @Override
	            public void onResponse(String arg0) {
	            	
	            	System.out.println("eatarg0" + arg0);
	            	 
	            	try {
					    JSONObject json = new JSONObject(arg0) ;
					    String result = json.getString("result") ;
					    String reason = json.getString("reason") ;
					    String data = json.getString("data") ;
					    JSONObject jsonItem = new JSONObject(data) ;
					    String list = jsonItem.getString("list") ;
					    System.out.println("list" + list);
					    if(result.equals("1")){
					    	  JSONArray array = new JSONArray(list) ;
					    	  for(int i = 0 ; i < array.length() ; i ++){
					    		     JSONObject jsonObject = array.getJSONObject(i) ;
					    		     
					    		     String tags = jsonObject.getString("tags") ;
					    		     String id = jsonObject.getString("id") ;
					    		     String name = jsonObject.getString("name") ;
					    		     String cardImage = jsonObject.getString("cardImages") ;
					    		     String topicId = jsonObject.getString("topicId") ;
					    		     
					    		     String plans = jsonObject.getString("plans") ;
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
						    		    		      String food = morJson.getString("food") ;
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
						    		    		      String food = morJson.getString("food") ;
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
						    		    		      String food = morJson.getString("food") ;
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
						    		    		      String food = morJson.getString("food") ;
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
					     
					     handler.sendEmptyMessage(1001) ;
					    	
					    }else {
							Toast.makeText(UpFoodMenuActivity.this, reason, Toast.LENGTH_SHORT).show() ;
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
