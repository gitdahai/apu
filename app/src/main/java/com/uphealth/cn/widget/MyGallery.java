package com.uphealth.cn.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import com.uphealth.cn.data.GlobalData;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自动轮播图控件
 * @author jun.wang
 * @category 替换方案viewPager ViewFlipper
 */
@SuppressWarnings("deprecation")
public class MyGallery extends Gallery {
	
	private int index = 0;

    /**
     * 定时器，实现自动播放
     */
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 2;
            index = getSelectedItemPosition();
            index++;
            handler.sendMessage(message);
        }
    };
 
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 2:
                setSelection(index);
                break;
            default:
                break;
            }
        }
    };

	
	private int position ;
	private Timer timer = new Timer() ;
	@SuppressWarnings("deprecation")
	public MyGallery(Context context , AttributeSet attrs){
		super(context, attrs) ;
		timer.schedule(task, 3000, 3000) ;
	}
	
	@SuppressWarnings("deprecation")
	private void start(){
	    position = getSelectedItemPosition() ;
		if(position >= getCount() - 1)
			onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null) ;
		else
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null) ;
	}
	
	  @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
 
        int kEvent;
        if (isScrollingLeft(e1, e2)) {
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null);
 
        if (this.getSelectedItemPosition() == 0) {// 实现后退功能
            this.setSelection(GlobalData.GALLERY_SIZE);
        }
        
       System.out.println("eeeeeeeee" + e1+e2); 
       return false;
    }    
	  
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
         return e2.getX() > e1.getX();
    }
    
    @Override 
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
    	
    	System.out.println("eeeeeeeee00" + e1+e2); 
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

	
	@SuppressWarnings("unused")
	private boolean isScrollLeft(MotionEvent paEvent , MotionEvent paEvent2){
		float f1 = paEvent.getX() ;
		float f2 = paEvent2.getX() ;
		
		if(f2 > f1){
			return true ;
		}
		
		return false ;
	}


}
