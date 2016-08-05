package com.uphealth.cn.ui.login.video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyVideoView;
import com.uphealth.cn.widget.RoundProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 定制后的视频模块界面
 * @description 
 * @data 2016年8月1日

 * @author jun.wang
 */
public class VideoNewThreeActivity extends Activity implements OnClickListener {
	private MyVideoView vv_player;
	MediaController mc ; 
	private int currentIndex = 0;  // 当前视频播放的位置
	private TextView title ;
	private TextView text_time , text_right ;   // 当前视频播放时间
	private ImageView image_pause , play_image , image_next , image_front;
	private RelativeLayout bottom_layout ;
	private LinearLayout allLayout ;
	
	private List<ProgressBar> viewLists = new ArrayList<>() ;
	private Handler mHandler = new Handler();
	private int index ;	
	private int heightPixels = 0 ;
	private LinearLayout layout ;
	private RoundProgressBar roundProgressBar ;	
	MediaPlayer voicePlayer  ;
	MediaPlayer first_actionPlayer ;
	
	private boolean isMuisc = true ;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1001:
				try {
					   viewLists.get(currentIndex).setMax(vv_player.getDuration()/1000);
				       viewLists.get(currentIndex).setProgress((int)(msg.obj)/1000);
				       text_time.setText(Utils.getTime((int)(msg.obj))+"");
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				break;
				
			case 1008:
				title.setVisibility(View.VISIBLE);
				text_right.setVisibility(View.VISIBLE);
				image_pause.setVisibility(View.VISIBLE);
				image_next.setVisibility(View.VISIBLE);
				image_front.setVisibility(View.VISIBLE);
				break ;
				
			case 1009:
				mc.setVisibility(View.GONE);
				mc.setFocusable(false);
	    		vv_player.setMediaController(mc);
				title.setVisibility(View.INVISIBLE);
				text_right.setVisibility(View.INVISIBLE);
				image_pause.setVisibility(View.INVISIBLE);
				image_front.setVisibility(View.INVISIBLE);
				image_next.setVisibility(View.INVISIBLE);
				break ;		
				
			case 1010:
			    play_image.setVisibility(View.VISIBLE);
				break ;
				
			case 1011:
				play_image.setVisibility(View.INVISIBLE);
				break ;

			default:
				break;
			}
			
		};
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏标题
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) ;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);	
		setContentView(R.layout.activity_video_new_three);
		
		init() ;
		
	}
	
	@SuppressWarnings("static-access")
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		title = (TextView)this.findViewById(R.id.title) ;
		title.setText("弹力坐姿测试");
		text_time = (TextView)this.findViewById(R.id.text_time) ;
		text_right = (TextView)this.findViewById(R.id.text_right) ;
		text_right.setOnClickListener(this);
		image_pause = (ImageView)this.findViewById(R.id.image_pause) ;
		image_pause.setOnClickListener(this);
		play_image = (ImageView)this.findViewById(R.id.play_image) ;
		play_image.setOnClickListener(this);
		image_next = (ImageView)this.findViewById(R.id.image_next) ;
		image_next.setOnClickListener(this);
		image_front = (ImageView)this.findViewById(R.id.image_front) ;
		image_front.setOnClickListener(this);
		
		bottom_layout = (RelativeLayout)this.findViewById(R.id.bottom_layout) ;
		allLayout = (LinearLayout)this.findViewById(R.id.allLayout) ;
		
		vv_player = (MyVideoView)findViewById(R.id.vv_player);
		voicePlayer = new MediaPlayer().create(this, R.raw.music_bg) ;
//		first_actionPlayer = new MediaPlayer().create(this, R.raw.music_first_action) ;
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				first_actionPlayer.start();
//			}
//		}, 1500) ;
		
		if(GlobalData.videoLists.size() == 1){
			heightPixels = GlobalData.heightPixels ; 
		}else {
			heightPixels = GlobalData.heightPixels - (40*2) ;
		}
		
		layout = (LinearLayout)this.findViewById(R.id.layout) ;
		System.out.println("GlobalData.videoLists" + GlobalData.videoLists);
		System.out.println("GlobalData.videoLists"+heightPixels/GlobalData.videoLists.size());
		viewLists.clear(); 
		layout.removeAllViews();
		for(int i = 0 ; i < GlobalData.videoLists.size() ; i++){
			System.out.println("utilsutils"+Utils.getProgreeLength(GlobalData.videoLists.get(i).getLength()));
			
			ProgressBar progressBar = new ProgressBar(this ,null,android.R.attr.progressBarStyleHorizontal) ;
			progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color_new));
			progressBar.setMax(100);
			progressBar.setProgress(0);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					Utils.getProgreeLength(GlobalData.videoLists.get(i).getLength()), 24) ;
			params.setMargins(0, 0, 10, 0);
			progressBar.setLayoutParams(params) ;
			viewLists.add(progressBar) ;
			layout.addView(progressBar);
		}
		
		roundProgressBar = (RoundProgressBar)this.findViewById(R.id.roundProgressBar1) ;
		roundProgressBar.setTextStr(GlobalData.videoLists.size()+"/1");
		roundProgressBar.setTextSize(100);
		roundProgressBar.setTextColor(getResources().getColor(R.color.text_main));
		roundProgressBar.setCricleColor(getResources().getColor(R.color.white));
		roundProgressBar.setCricleProgressColor(getResources().getColor(R.color.text_main)) ;
		roundProgressBar.setRoundWidth(12);
		roundProgressBar.setProgress(15);
		
//		mc = new MediaController(this , false);
//		mc.setVisibility(View.INVISIBLE);
//		vv_player.setMediaController(mc);
		
		loadView(currentIndex);
        addListener();
        
        // 视频点击事件
        allLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.sendEmptyMessage(1008) ;
				
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1009) ;
					}
				}, 5000) ;
			}
		});
        
//        mc.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				
//				System.out.println("handler1008");
//				handler.sendEmptyMessage(1008) ;
//				
//				return true;
//			}
//		});
        
	}
	
	//加载整个view视图
    public void loadView(int index){
    	
    	try {
    		  Uri uri = Uri.parse(GlobalData.videoLists.get(index).getVideoUrl());
    		  vv_player.setVideoURI(uri);
    		  vv_player.setMediaController(new MediaController(VideoNewThreeActivity.this));
    		  vv_player.requestFocus();
    		
    		  mc = new MediaController(this , false);
    		  mc.setVisibility(View.GONE);
    		  vv_player.setMediaController(mc);
    		  
    		  // 隐藏的代码
    		  vv_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
    		    	@Override
    		  	  public void onCompletion(MediaPlayer mp) {
    				  // 视频播放结束
//    				  next();
    			  }
    		  });
    		  
    		  vv_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {  
//    			  
    	            @Override  
    	            public void onPrepared(MediaPlayer mp) {  
    	                mp.start();  
    	                mp.setLooping(true);  
    	            }  
    	      });  
    		  
    		  
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace(); 
		}
    }
    
    //所有控件事件监听
    public void addListener(){
    	
		vv_player.start();
		
		if(GlobalData.videoLists.size() == currentIndex){
			System.out.println("currentIndex=="+currentIndex);
			
			// 更新方案进度接口
//	    	updatePlanProgress() ;
			
		}else {
			//通过handler启动线程
			index = 1001 ;
			mHandler.post(mRunnable);
		}	
    }
    
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

	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.image_pause:
			 vv_player.pause();
			 handler.sendEmptyMessage(1010) ;
			 break ;
			 
		case R.id.play_image:
			 vv_player.start();
			 handler.sendEmptyMessage(1011) ;
			 break ;
		
		// 播放背景音乐	 
		case R.id.text_right:
			 if(isMuisc){
				 voicePlayer.start();
				 voicePlayer.setLooping(true);
				 text_right.setBackgroundResource(R.drawable.music_close_bg);
				 isMuisc = false ;
			 }else {
				 voicePlayer.pause();;
				 text_right.setBackgroundResource(R.drawable.music_bg);
				 isMuisc = true ;
			 }
			 break ;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(voicePlayer.isPlaying()){
			voicePlayer.stop();
		}
		
//		if(first_actionPlayer.isPlaying()){
//			first_actionPlayer.stop();
//		}
		
	}

}
