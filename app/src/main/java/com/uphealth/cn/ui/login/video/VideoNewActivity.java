package com.uphealth.cn.ui.login.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.SportShareDialog;
import com.uphealth.cn.dialog.VideoBeforeDialog;
import com.uphealth.cn.widget.MyVideoView;
import com.uphealth.cn.widget.RoundProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description  视频播放定制界面
 * @data 2016年6月7日

 * @author jun.wang
 */
@SuppressLint("HandlerLeak")
public class VideoNewActivity extends Activity implements OnClickListener {
	private LinearLayout layout ;
	private ProgressBar progressBar , progressBar2;
	private ProgressBar progressBar3 , progressBar4 , progressBar5 , progressBar6 , progressBar7;
	
	private SeekBar sb_progress;
	private MyVideoView vv_player;
	private boolean flag = true;
	
	private ImageView image_next ;
	private int index ;
	
	private String planId = "" ;
	protected RequestQueue requestQueue = null;	
	private Handler mHandler = new Handler();
	private SportShareDialog.Builder Shareibuilder;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				try {
					   viewLists.get(currentIndex).setMax(vv_player.getDuration()/1000);
				       viewLists.get(currentIndex).setProgress((int)(msg.obj)/1000);
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				break;
				
			case 1002:
				progressBar2.setMax(vv_player.getDuration()/1000);
				progressBar2.setProgress((int)(msg.obj)/1000);
				System.out.println("数值11==" + (int)(msg.obj)/1000);
				break ;
				
			case 1003:
				System.out.println("初始化1003");
//				vv_player.start();
				break ; 
				
			case 1004:
				if(progressNum>=0){
					text_number.setText(progressNum+"");
					
//					if(progressNum == 0){
//						 // 隐藏倒计时，开始下一个视频
//						roundProgressBar.setVisibility(View.GONE);
//						text_number.setVisibility(View.GONE);
//						currentIndex ++ ;
//						loadView(currentIndex) ;
//						addListener();
//						
//					}
				}else {
						 // 隐藏倒计时，开始下一个视频
					    if(currentIndex < GlobalData.videoLists.size()){
					    	System.out.println("loadView0000");
					    	roundProgressBar.setVisibility(View.GONE);
							text_number.setVisibility(View.GONE);
							currentIndex ++ ;
							loadView(currentIndex) ;
							addListener();
					    }else {
							// 视频播放结束
					    	System.out.println("currentIndex=="+currentIndex);
					    	
						}
				}
				break ;
				
			case 1005:
				showShareDialog() ;
				break ;

			default:
				break;
			}
			
		};
	};
	
	private Runnable mRunnable = new Runnable(){

        @Override
        public void run() {
        	Message msg = new Message() ;
			msg.what = index ;
			msg.obj = vv_player.getCurrentPosition() ;
			handler.sendMessage(msg);
			//每两秒重启一下线程
            mHandler.postDelayed(mRunnable, 1000);		
        }
    };
    
	private VideoBeforeDialog.Builder Builder ; 
	Dialog dialog ;
	LayoutInflater inflater ;
	private boolean isCreate = true;
	private RoundProgressBar roundProgressBar ;
	private TextView text_number ;
	private int progress = 0;
	private int progressNum = 16 ;
	
	private int heightPixels = 0 ;
	
	private List<ProgressBar> viewLists = new ArrayList<>() ;
	private int currentIndex = 0;  // 当前视频播放的位置
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) ;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_new);
		
		planId = getIntent().getStringExtra("planId") ;
		
		init() ;
		
	}
	
	private void init(){
		requestQueue = Volley.newRequestQueue(this);
		findViewById(R.id.back).setOnClickListener(this);
		heightPixels = GlobalData.heightPixels - (40*2) ;
		layout = (LinearLayout)this.findViewById(R.id.layout) ;
		System.out.println("GlobalData.videoLists" + GlobalData.videoLists);
		System.out.println("GlobalData.videoLists"+heightPixels/GlobalData.videoLists.size());
		viewLists.clear(); 
		layout.removeAllViews();
		for(int i = 0 ; i < GlobalData.videoLists.size() ; i++){
			ProgressBar progressBar = new ProgressBar(this ,null,android.R.attr.progressBarStyleHorizontal) ;
			progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color_new));
			progressBar.setMax(100);
			progressBar.setProgress(0);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(heightPixels/GlobalData.videoLists.size(), 24) ;
			params.setMargins(0, 0, 40, 0);
			progressBar.setLayoutParams(params) ;
			viewLists.add(progressBar) ;
			layout.addView(progressBar);
		}
		
		roundProgressBar = (RoundProgressBar)this.findViewById(R.id.roundProgressBar1) ;
		roundProgressBar.setTextStr("15");
		roundProgressBar.setTextSize(100);
		roundProgressBar.setTextColor(getResources().getColor(R.color.text_main));
		roundProgressBar.setCricleColor(getResources().getColor(R.color.white));
		roundProgressBar.setCricleProgressColor(getResources().getColor(R.color.text_main)) ;
		roundProgressBar.setProgress(progress);
		text_number = (TextView)this.findViewById(R.id.text_number) ;
			  
		image_next = (ImageView)this.findViewById(R.id.image_next) ;
		image_next.setOnClickListener(this);
		
		vv_player = (MyVideoView)findViewById(R.id.vv_player);
		MediaController mc = new MediaController(this);
		mc.setVisibility(View.INVISIBLE);
		vv_player.setMediaController(mc);
		
		loadView(currentIndex);
        addListener();
	}

	//加载整个view视图
    public void loadView(int index){
    	
    	try {
    		  Uri uri = Uri.parse(GlobalData.videoLists.get(index).getVideoUrl());
    		  vv_player.setVideoURI(uri);
    		  vv_player.setMediaController(new MediaController(VideoNewActivity.this));
    		  vv_player.requestFocus();
    		
//    		  MediaController mc = new MediaController(this);
//    		  mc.setVisibility(View.INVISIBLE);
//    		  vv_player.setMediaController(mc);
    		
    		  
    		  // 隐藏的代码
    		  vv_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
    		    	@Override
    		  	  public void onCompletion(MediaPlayer mp) {
    				  // 视频播放结束
    				  next();
    			  }
    		  });
    		  
    		  
//    		  vv_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {  
//    			  
//    	            @Override  
//    	            public void onPrepared(MediaPlayer mp) {  
//    	                mp.start();  
//    	                mp.setLooping(true);  
//    	  
//    	            }  
//    	        });  
//    	  
//    		  vv_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
//    	  
//    	                    @Override  
//    	                    public void onCompletion(MediaPlayer mp) {  
//    	                    	vv_player.setVideoPath(GlobalData.videoLists.get(0).getVideoUrl());  
//    	                    	vv_player.start();  
//    	  
//    	                    }  
//    	        });  
    		  
    		  
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace(); 
		}
    	
    	
    }
    
    //所有控件事件监听
    public void addListener(){
    	
		vv_player.start();
		
		if(GlobalData.videoLists.size() == currentIndex){
			System.out.println("currentIndex=="+currentIndex);
			
//			Intent intent = new Intent(VideoNewActivity.this , SkinAfterActivity.class) ;
//			startActivity(intent);
//			finish();
			
			// 更新方案进度接口
	    	updatePlanProgress() ;
			
		}else {
			//通过handler启动线程
			index = 1001 ;
			mHandler.post(mRunnable);
		}
		
//		thread = new Thread(){public void run() {
//			
//			super.run();
//			while (flag) {
//				
//				Message msg = new Message() ;
//				msg.what = 1001 ;
//				msg.obj = vv_player.getCurrentPosition() ;
//				handler.sendMessage(msg);
//				System.out.println("线程000");
//				
//				try {
//					sleep(1000);
//				} catch (Exception e) {
//				}
//			}
//			
//		};} ;
    }
    
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.image_next:
//			 playNext() ;
			 
			 next() ;
			 break;
			 
		case R.id.back:
			 finish();
			 break ;

		default:
			break;
		}
	}	
	
	private void playNext(){
		mHandler.removeCallbacks(mRunnable);
		
		Uri uri = Uri.parse("http://data.vod.itc.cn/?prod=app&new=/170/20/i3F7MfczIfbrKd6AlfEYJ3.mp4");
		vv_player.setVideoURI(uri);
		vv_player.setMediaController(new MediaController(VideoNewActivity.this));
		//设置焦点
		vv_player.requestFocus();
		vv_player.start();
		
		MediaController mc = new MediaController(this);
		mc.setVisibility(View.INVISIBLE);
		vv_player.setMediaController(mc);
		
		//通过handler启动线程
		index = 1002 ;
		mHandler.post(mRunnable);
		
//		//创建一个线程用于同步seekbar进度
//		new Thread(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//				while (flag) {
//					Message msg = new Message() ;
//					msg.what = 1002 ;
//					msg.obj = vv_player.getCurrentPosition() ;
//					handler.sendMessage(msg);
//					System.out.println("线程1111");
//					try {
//						sleep(1000);
//					} catch (Exception e) {
//					}
//				}
//			}
//		}.start();
		
	}
    
	private void next(){
		roundProgressBar.setVisibility(View.VISIBLE);
		text_number.setVisibility(View.VISIBLE);
		progress = 0;
	    progressNum = 16 ;
		roundProgressBar.setTextStr("15");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(progress <= 16){
					progress += 1;
					progressNum -- ;
					handler.sendEmptyMessage(1004) ;
					roundProgressBar.setProgress(progress);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	// 更新方案完成进度
	private void updatePlanProgress(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.updatePlanProgress)
		.append("?accountId=").append(GlobalData.getUserId(VideoNewActivity.this))
		.append("&planId="+planId) ;
		
		
		System.out.println("planIdapi=" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	System.out.println("planIdapiarg=" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   
					   if(result == 1){
						   handler.sendEmptyMessage(1005) ;
					   }else {
						   handler.sendEmptyMessage(1005) ;
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
	
	private void showShareDialog(){
		inflater = LayoutInflater.from(this) ;
		Shareibuilder = new SportShareDialog.Builder(this);
		dialog = Shareibuilder.create() ;
		View view = inflater.inflate(R.layout.dialog_sport_finish, null);
		
		ImageView image_close = (ImageView)view.findViewById(R.id.image_close) ;
		image_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		TextView text_public = (TextView)view.findViewById(R.id.text_public) ;
		text_public.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VideoNewActivity.this , SkinAfterActivity.class) ;
				startActivity(intent);
				finish();
			}
		});
		
		TextView text_share = (TextView)view.findViewById(R.id.text_share) ;
		text_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		dialog.addContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();	
		
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.4);    //宽度设置为屏幕的0.9 
		p.height = (int) (d.getHeight() * 0.6);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
		dialog.getWindow().setGravity(Gravity.CENTER);	
	}
	
    
}
