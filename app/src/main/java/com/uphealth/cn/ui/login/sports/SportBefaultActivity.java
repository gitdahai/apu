package com.uphealth.cn.ui.login.sports;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.CommonAdapterTwo;
import com.uphealth.cn.bean.CommonBean;
import com.uphealth.cn.bean.VideoBean;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.WifiDialog;
import com.uphealth.cn.ui.login.video.VideoNewActivity;
import com.uphealth.cn.ui.login.video.VideoNewThreeActivity;
import com.uphealth.cn.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动播放前
 * @description 
 * @data 2016年7月17日

 * @author jun.wang
 */
public class SportBefaultActivity extends Activity implements OnClickListener {
	
	private GridView gridView ;
	CommonAdapterTwo adapter ;
	List<CommonBean> lists ;
	private WifiDialog.Builder ibuilder;
	private String id = "";
	private String titleStr = "" ;
	private TextView text_title ;
	
	protected RequestQueue requestQueue = null;
	private List<VideoBean> videoLists = new ArrayList<>() ;
	
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) ;	
		setContentView(R.layout.activity_sport_befault);
		
		id = getIntent().getStringExtra("id") ;
		init() ;
	}
	
	private void init(){
		requestQueue = Volley.newRequestQueue(this);
		findViewById(R.id.back).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("准备好了");
		text_title = (TextView)this.findViewById(R.id.text_title) ;
		text_title.setText(titleStr);
		
		findViewById(R.id.next).setOnClickListener(this);
		gridView = (GridView)this.findViewById(R.id.gridView) ;
		lists = new ArrayList<CommonBean>() ;
		
		requestData() ;
	}
	
	private void setData(){
		if(lists.size() >= 5){
			gridView.setNumColumns(5);
		}else {
			gridView.setNumColumns(lists.size()) ;
		}
		
		adapter = new CommonAdapterTwo(this, lists) ;
		gridView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.next:
			 if(Utils.isWifiConnected(this)){
				 
				 if(GlobalData.videoLists.size() == 0){
					 Toast.makeText(SportBefaultActivity.this, "没有相关视频", Toast.LENGTH_SHORT).show() ;
					 return ;
				 }else {
//					 Intent intent = new Intent(SportBefaultActivity.this , VideoNewActivity.class) ;
//					 intent.putExtra("planId", id) ;
//					 startActivity(intent);
					 
					 Intent intent = new Intent(SportBefaultActivity.this , VideoNewThreeActivity.class) ;
					 startActivity(intent);
				 }
			
			 }else {
				 show();
			 }
			 break ;
		}	 
	}
	
	private void show(){
		ibuilder = new WifiDialog.Builder(SportBefaultActivity.this);    
		ibuilder.setMessage("是否在非wifi环境看视频?");
		ibuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
				finish();
			}
		});
		
		ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				Intent intent = new Intent(SportBefaultActivity.this , VideoNewActivity.class) ;
				startActivity(intent);
			}
		});
			
		Dialog dialog = ibuilder.create() ;
		dialog.show();
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.4);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
	}	
	
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getSportPlanDetail)
		.append("?accountId=").append(GlobalData.getUserId(this))
		.append("&id=").append(id) ;
		
		System.out.println("sportBuilder=" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	try {
					     System.out.println("arg0"+arg0);
					     JSONObject json = new JSONObject(arg0) ;
					     String data = json.getString("data") ;
					     JSONObject dataObj = new JSONObject(data) ;
					     String tools = dataObj.getString("tools") ;   // 工具图
					     String plans = dataObj.getString("plans") ;   // 视频吞
					     
					     System.out.println("tools=" + tools);
					     System.out.println("plans=" + plans);
					     
					     videoLists.clear();
					     GlobalData.videoLists.clear();
					     JSONArray arrayPlans = new JSONArray(plans) ;
					     if(null != arrayPlans && arrayPlans.length() != 0){
					    	   for(int i = 0 ; i < arrayPlans.length() ; i ++){
					    		      
					    		     JSONObject obj = arrayPlans.getJSONObject(i) ;
					    		     
					    		     String videoStr = obj.getString("videos") ;
					    		     
					    		     JSONArray arrayVideo = new JSONArray(videoStr) ;
					    		     if(null != arrayVideo && arrayVideo.length() != 0){
					    		    	  
					    		    	  for(int j = 0 ; j < arrayVideo.length() ; j ++){
					    		    		    JSONObject objVideo = arrayVideo.getJSONObject(j) ;
					    		    		    VideoBean bean = new VideoBean() ;
								    		    bean.setVideoUrl(objVideo.getString("videoUrl"));
								    		    bean.setVoiceUrl("");
								    		    bean.setLength(j+1);
								    		    videoLists.add(bean) ;
					    		    	  }
					    		     }
					    		   
					    	   }
					    	   
					    	   GlobalData.videoLists.addAll(videoLists) ; 
					     }
					     
					     System.out.println("GlobalData==" + GlobalData.videoLists);
					     
					     lists.clear();
					     JSONArray array = new JSONArray(tools) ;
					     System.out.println("array==" + array.length());
					     if(null != array && array.length() != 0){
					    	   for(int i = 0 ; i < array.length() ; i ++){
					    		      
					    		     JSONObject obj = array.getJSONObject(i) ;
					    		     CommonBean bean = new CommonBean() ;
					    		     bean.setName(obj.getString("name"));
					    		     bean.setUrl(obj.getString("image"));
					    		     lists.add(bean) ;
					    	   }
					    	   
					    	   System.out.println("GlobalData=" + lists);
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

}
