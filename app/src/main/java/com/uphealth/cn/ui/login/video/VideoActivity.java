package com.uphealth.cn.ui.login.video;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;
import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 视频播放界面 
 * @data 2016年6月3日

 * @author jun.wang
 */
public class VideoActivity extends BaseActivity {
	private Button btn_load;
	private Button btn_play;
	private Button btn_pause;
	private SeekBar sb_progress;
	private VideoView vv_player;
	private boolean flag = true;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			sb_progress.setProgress(msg.getData().getInt("current", 0)/1000);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		loadView();
        addListener();
	}
	
	 //加载整个view视图
    public void loadView(){
    	//加载底部三个button控件
    	btn_load = (Button)findViewById(R.id.btn_load);
    	btn_play = (Button)findViewById(R.id.btn_play);
    	btn_pause = (Button)findViewById(R.id.btn_pause);
    	//加载进度条控件
    	sb_progress = (SeekBar)findViewById(R.id.sb_progress);
    	//加载videoview控件
    	vv_player = (VideoView)findViewById(R.id.vv_player);
    	
    	MediaController mc = new MediaController(this);
    	mc.setVisibility(View.INVISIBLE);
    	vv_player.setMediaController(mc);
    	
    }
    //所有控件事件监听
    public void addListener(){
    	//load控件事件监听方法
    	btn_load.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//设置服务器视频资源路径
				Uri uri = Uri.parse("http://data.vod.itc.cn/?prod=app&new=/170/20/i3F7MfczIfbrKd6AlfEYJ3.mp4");
				vv_player.setVideoURI(uri);
				//设置本地视频资源路径
//				vv_player.setVideoPath("/storage/emulated/0/redSun/video/2016_03_19_170642.mp4");
				vv_player.setMediaController(new MediaController(VideoActivity.this));
				//设置焦点
				vv_player.requestFocus();
			}
		});
    	//play控件事件监听方法
    	btn_play.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vv_player.start();
				sb_progress.setMax(vv_player.getDuration()/1000);
				//创建一个线程用于同步seekbar进度
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						while (flag) {
							Message msg = handler.obtainMessage();
							msg.getData().putInt("current", vv_player.getCurrentPosition());
							handler.sendMessage(msg);
							try {
								sleep(1000);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}.start();
			}
		});
    	//pause控件事件监听方法
		btn_pause.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vv_player.pause();
			}
		});
		
    }	
	
}
