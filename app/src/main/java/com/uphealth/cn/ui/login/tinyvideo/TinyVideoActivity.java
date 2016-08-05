package com.uphealth.cn.ui.login.tinyvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.manager.ActivityManager;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.tinyvideo.MovieRecorderView.OnRecordFinishListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description 微视频窗体界面 
 * @data 2016年1月20日

 * @author jun.wang
 */
public class TinyVideoActivity extends BaseActivity {
	private MovieRecorderView mRecorderView;//视频录制控件
	private Button mShootBtn;//视频开始录制按钮
	private boolean isFinish = true;
	private boolean success = false;//防止录制完成后出现多次跳转事件
	ActivityManager activityManager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tiny_video);
		
		activityManager = ActivityManager.getInstance() ;
		activityManager.pushActivity(this);
		mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
		mShootBtn = (Button) findViewById(R.id.shoot_button);

		//用户长按事件监听
		mShootBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {//用户按下拍摄按钮
					mShootBtn.setBackgroundResource(R.drawable.bg_movie_add_shoot_select);
					mRecorderView.record(new OnRecordFinishListener() {

						@Override
						public void onRecordFinish() {
							if(!success&&mRecorderView.getTimeCount()>6){//判断用户按下时间是否大于10秒
								success = true;
								handler.sendEmptyMessage(1);
							}
						}
					});
				} else if (event.getAction() == MotionEvent.ACTION_UP) {//用户抬起拍摄按钮
					mShootBtn.setBackgroundResource(R.drawable.bg_movie_add_shoot);
					if (mRecorderView.getTimeCount() > 0){//判断用户按下时间是否大于3秒
						if(!success){
							success = true;
							handler.sendEmptyMessage(1);
						}
					} else {
						success = false;
						if (mRecorderView.getmVecordFile() != null)
							mRecorderView.getmVecordFile().delete();//删除录制的过短视频
						mRecorderView.stop();//停止录制
						Toast.makeText(TinyVideoActivity.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			}
		});	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		isFinish = true;
		if (mRecorderView.getmVecordFile() != null)
			mRecorderView.getmVecordFile().delete();//视频使用后删除
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		isFinish = false;
		success = false;
		mRecorderView.stop();//停止录制
	}

	@Override
	public void onPause() {
		super.onPause();
		
		mRecorderView.stop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mRecorderView.stop();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mRecorderView.stop();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(success){
				finishActivity();
			}
		}
	};

	//视频录制结束后，跳转的函数
	private void finishActivity() {
		if (isFinish) {
			
			System.out.println("video_url" + mRecorderView.getmVecordFile().toString());
			GlobalData.dynamicVideoUrl = mRecorderView.getmVecordFile().toString() ;
			
			Bimp.tempSelectBitmap.clear();
//			if(!TextUtils.isEmpty(GlobalData.dynamicVideoUrl)){
//				ImageItem imageItem = new ImageItem() ;
//				
//				String fileName = String.valueOf(System.currentTimeMillis());
//				Bitmap bm = createVideoThumbnail(GlobalData.dynamicVideoUrl);
//				String imagePath = FileUtils.saveBitmap(bm, fileName);
//				
//				imageItem.setBitmap(createVideoThumbnail(GlobalData.dynamicVideoUrl));
//				imageItem.setImagePath(imagePath);
//				Bimp.tempSelectBitmap.add(imageItem) ;
//				System.out.println("Bimp==" + Bimp.tempSelectBitmap.size());
//			}
			
			GlobalData.maxTime = false ;
			mRecorderView.stop();
			Intent intent = new Intent(this, PhotoThumbActivity.class);
			startActivity(intent);
			finish();
		}
		success = false;
	}

	/**
	 * 录制完成回调
	 */
	 public interface OnShootCompletionListener {
		 public void OnShootSuccess(String path, int second);
		 public void OnShootFailure();
	 }
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
        	 activityManager.popAllActivity(TinyVideoActivity.class);
        	 finish();
        	 return true ;
        }
		
		return super.onKeyDown(keyCode, event);
	}

	  //获取视频第一帧的缩略图并显示
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (RuntimeException | InstantiationException | IllegalAccessException
                | NoSuchMethodException | ClassNotFoundException | InvocationTargetException ex) {
            Log.e("createVideoThumbnail", "createVideoThumbnail") ;
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
