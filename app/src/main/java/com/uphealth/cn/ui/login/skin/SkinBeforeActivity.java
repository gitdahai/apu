package com.uphealth.cn.ui.login.skin;

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
import com.uphealth.cn.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 护肤前的准备工作
 * @description 
 * @data 2016年7月5日

 * @author jun.wang
 */
public class SkinBeforeActivity extends Activity implements OnClickListener {
	private GridView gridView ;
	CommonAdapterTwo adapter ;
	List<CommonBean> lists ;
	private WifiDialog.Builder ibuilder;
	private String sceneId = "";
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
		setContentView(R.layout.activity_skin_befault);
		
		titleStr = getIntent().getStringExtra("titleStr") ;
		sceneId = getIntent().getStringExtra("sceneId") ;
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
		if(lists.size() >= 6){
			gridView.setNumColumns(6);
		}else {
			gridView.setNumColumns(lists.size());
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
//				 GlobalData.videoLists.clear();
//				 VideoBean videoBean = new VideoBean() ;
//				 videoBean.setVideoUrl("http://data.vod.itc.cn/?prod=app&new=/170/20/i3F7MfczIfbrKd6AlfEYJ3.mp4");
//				 videoBean.setVoiceUrl("");
//				 GlobalData.videoLists.add(videoBean) ;
//				
//				 VideoBean videoBean2 = new VideoBean() ;
//				 videoBean2.setVideoUrl("http://115.28.1.196:80/apu/userfiles/1/videos/2016/07/20160717151954.mp4");
//				 videoBean2.setVoiceUrl("");
//				 GlobalData.videoLists.add(videoBean2) ;
				 
				 if(GlobalData.videoLists.size() == 0){
					 Toast.makeText(SkinBeforeActivity.this, "没有相关视频", Toast.LENGTH_SHORT).show() ;
					 return ;
				 }else {
					 Intent intent = new Intent(SkinBeforeActivity.this , VideoNewActivity.class) ;
					 startActivity(intent);
				 }
			
			 }else {
				 show();
			 }
			 break ;

		default:
			break;
		}
	}
	
	private void show(){
		ibuilder = new WifiDialog.Builder(SkinBeforeActivity.this);    
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
				
//				GlobalData.videoLists.clear();
//				VideoBean videoBean = new VideoBean() ;
//				videoBean.setVideoUrl("http://data.vod.itc.cn/?prod=app&new=/170/20/i3F7MfczIfbrKd6AlfEYJ3.mp4");
//				videoBean.setVoiceUrl("");
//				GlobalData.videoLists.add(videoBean) ;
//				
//				VideoBean videoBean2 = new VideoBean() ;
//				videoBean2.setVideoUrl("http://14.29.86.12/music.qqvideo.tc.qq.com/b0020op7h63.p301.1.mp4?vkey=7FA41F88494189D4886481CB174E5B41638E455D3B5C6675A9EE9ACEEB4C5B4639DE811CB11CCDBFC37062FCD7921F6F0F0698E914C7F98A40E3F1E40F2F78320D94EDABE384B1E9BF8B27467EAF6266D0ED1EDD75785DE6&locid=7703bb82-a704-4f37-b177-020d7c456e51&size=31705757&ocid=300556204");
//				videoBean2.setVoiceUrl("");
//				GlobalData.videoLists.add(videoBean2) ;
				
//				VideoBean videoBean3 = new VideoBean() ;
//				videoBean3.setVideoUrl("http://14.29.86.15/music.qqvideo.tc.qq.com/f0020x1q44r.p301.1.mp4?vkey=8C05B398A42E585CC605866E50996D92FD8E2F8C4F40114F812F3A7C8CF561F026A2CBCCC439CE6E0807C320D9A9D57631C3BCBFE63997952205F32F81F3E199B6C967014E51F53522B32BE165C9652A7F92C558B624B559&locid=9e4be674-4967-4728-8cd7-ba27f95fa806&size=37929243&ocid=283778988");
//				videoBean3.setVoiceUrl("");
//				GlobalData.videoLists.add(videoBean3) ;
				
				Intent intent = new Intent(SkinBeforeActivity.this , VideoNewActivity.class) ;
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
		builder.append(Contants.getMakeupPlanDetailByScene)
		.append("?accountId=").append(GlobalData.getUserId(this))
		.append("&sceneId=").append(sceneId) ;
		
		System.out.println("accountId===" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	 System.out.println("arg0"+arg0);
            	
            	 try {
            		     System.out.println("arg0"+arg0);
					     JSONObject json = new JSONObject(arg0) ;
					     String data = json.getString("data") ;
					     JSONObject dataObj = new JSONObject(data) ;
					     String tools = dataObj.getString("tools") ;   // 工具图
					     String plans = dataObj.getString("plans") ;   // 视频吞
					     
					     videoLists.clear();
					     GlobalData.videoLists.clear();
					     JSONArray arrayPlans = new JSONArray(plans) ;
					     if(null != arrayPlans && arrayPlans.length() != 0){
					    	   for(int i = 0 ; i < arrayPlans.length() ; i ++){
					    		      
					    		     JSONObject obj = arrayPlans.getJSONObject(i) ;
					    		     VideoBean bean = new VideoBean() ;
					    		     bean.setVideoUrl(obj.getString("url"));
					    		     bean.setVoiceUrl("");
					    		     videoLists.add(bean) ;
					    	   }
					    	   
//					    	   VideoBean videoBean2 = new VideoBean() ;
//							   videoBean2.setVideoUrl("http://115.28.1.196:80/apu/userfiles/1/videos/2016/07/20160717151954.mp4");
//							   videoBean2.setVoiceUrl("");
//							   GlobalData.videoLists.add(videoBean2) ;
					    	   
					    	   GlobalData.videoLists.addAll(videoLists) ; 
					     }
					     
					     lists.clear();
					     JSONArray array = new JSONArray(tools) ;
					     if(null != array && array.length() != 0){
					    	   for(int i = 0 ; i < array.length() ; i ++){
					    		      
					    		     JSONObject obj = array.getJSONObject(i) ;
					    		     CommonBean bean = new CommonBean() ;
					    		     bean.setName(obj.getString("name"));
					    		     bean.setUrl(obj.getString("image"));
					    		     lists.add(bean) ;
					    	   }
					    	   
					    	   System.out.println("GlobalData.videoLists" + GlobalData.videoLists);
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
