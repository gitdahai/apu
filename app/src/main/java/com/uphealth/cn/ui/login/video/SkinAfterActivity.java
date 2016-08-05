package com.uphealth.cn.ui.login.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.SkinAfterModel;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布美妆界面
 * @description 
 * @data 2016年7月17日

 * @author jun.wang
 */
public class SkinAfterActivity extends BaseActivity implements OnClickListener {
	private GridView gridview ;
	List<SkinAfterModel> lists ;
	LoadImage loadImage ;
	
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
		setContentView(R.layout.activity_skin_after);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("出门妆");
		((Button)findViewById(R.id.next)).setText("发布#出门妆#");
		findViewById(R.id.next).setOnClickListener(this);
		lists = new ArrayList<>() ;
		gridview = (GridView)this.findViewById(R.id.gridView) ;
		loadImage = LoadImage.getInstance() ;
		requestData() ;
		
	}
	
	private void setData(){
		if(null != lists && lists.size() != 0){
			
			 List<SkinAfterModel> tempLists = Utils.getSkinList(lists) ;
			 SkinAfterAdapter adapter = new SkinAfterAdapter(this , tempLists) ;
			 gridview.setAdapter(adapter);
		}
	}
	
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getMakeupHotReplyList)
		.append("?page=1")
		.append("&pageSize=9") ;
		
		System.out.println("api" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	 System.out.println("arg0"+arg0);
            	 
            	 try {
					      JSONObject json = new JSONObject(arg0) ;
					      int result = json.getInt("result") ;
					      
					      if(result == 1){
					    	    String data = json.getString("data") ;
					    	    JSONObject dataJson = new JSONObject(data) ;
					    	    String listStr = dataJson.getString("list") ;
					    	    
					    		lists = new Gson().fromJson(listStr,
				    					new TypeToken<List<SkinAfterModel>>() {
				    					}.getType());
					    	    System.out.println("lists=="+lists);
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
	
	private class SkinAfterAdapter extends BaseAdapter{
		private Context context ;
		LayoutInflater inflater ;
		List<SkinAfterModel> lists = new ArrayList<>() ;
		
		public SkinAfterAdapter(Context context , List<SkinAfterModel> lists){
			this.context = context ;
			this.lists = lists ;
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		}

		@Override
		public int getCount() {
			return lists.size();
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
				viewHolder = new ViewHolder() ;
				convertView = inflater.inflate(R.layout.item_skin_after, null) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag() ;
			}
			
			SkinAfterModel model = lists.get(position) ;
			viewHolder.image.setTag(Utils.getSkinImagePath(model.getImages()));
			loadImage.addTask(Utils.getSkinImagePath(model.getImages()),viewHolder.image);
			loadImage.doTask();
			
			return convertView;
		}
		
		class ViewHolder{
			ImageView image ;
		}
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 GlobalData.dynamicTopic = "#护肤#" ;
			 GlobalData.dynamicTopicId = lists.get(0).getTopicId() ;
			 GlobalData.dynamicAddress = "所在位置" ;
			 GlobalData.dynamicVideoUrl = "" ;
			 Intent intent = new Intent(SkinAfterActivity.this , PhotoThumbActivity.class) ;
			 startActivity(intent);
			 finish();
			 break ;

		default:
			break;
		}
		
	}

}
