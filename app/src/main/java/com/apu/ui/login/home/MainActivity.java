package com.apu.ui.login.home;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apu.data.GlobalData;
import com.apu.manager.ActivityManager;
import com.apu.widget.DragGridViewOlder;
import com.apu.widget.MyGallery;
import com.uphealth.cn.R;

/**
 * @description 阿噗主界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private long exittime = 0 ;
	private Fragment[] mFragments;  
    private FragmentManager fragmentManager;  
    private FragmentTransaction fragmentTransaction;  
    ActivityManager activityManager ;
    
    private MyGallery gallery ;
    private DragGridViewOlder gridview ;
	private LinearLayout layout_two ;
	
	private RelativeLayout navi_item_person , navi_item_home;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		
		init() ;
	}
	
	private void init(){
		activityManager = ActivityManager.getInstance() ;
		mFragments = new Fragment[3];  
        fragmentManager = getSupportFragmentManager();  
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_find); 
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_custom); 
        mFragments[2] = fragmentManager  
                .findFragmentById(R.id.fragement_center);  
        fragmentTransaction = fragmentManager.beginTransaction()  
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);  
        fragmentTransaction.show(mFragments[0]).commit();  
        
        findViewById(R.id.image).setOnClickListener(this);
        gallery = (MyGallery)this.findViewById(R.id.gallery) ;
        layout_two = (LinearLayout)this.findViewById(R.id.layout_two) ;
        gridview = (DragGridViewOlder)this.findViewById(R.id.gridView) ;
        
        navi_item_person = (RelativeLayout)this.findViewById(R.id.navi_item_person) ;
        navi_item_person.setOnClickListener(this);
        navi_item_home = (RelativeLayout)this.findViewById(R.id.navi_item_home) ;
        navi_item_home.setOnClickListener(this);
        
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
        	  if((System.currentTimeMillis() - exittime) > 2000){
        		    Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show() ; 
        		    exittime = System.currentTimeMillis() ;
        	  }else {
        		   // 同时清除缓存数据
//        		   GlobalData.clear();
        		   activityManager.popAllActivity(MainActivity.class);
				   System.exit(0) ;
			  }
        	 return true ;
        }
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		fragmentTransaction = fragmentManager.beginTransaction()  
	              .hide(mFragments[0]).hide(mFragments[1])  
	              .hide(mFragments[2]);  
		
		Intent intent = null ;
		switch (v.getId()) {
		case R.id.image:
			 fragmentTransaction.show(mFragments[0]).commit(); 
			 layout_two.setVisibility(View.GONE);
			 gallery.setVisibility(View.VISIBLE);
			 gridview.setVisibility(View.VISIBLE);
			 GlobalData.isStay = true ;
			 break;
			 
		case R.id.navi_item_home:
			 intent = new Intent(MainActivity.this , HotActivity.class) ;
			 startActivity(intent);
			 break ;
			 
		// 个人主页	 
		case R.id.navi_item_person:
			 intent = new Intent(MainActivity.this , PersonalActivity.class) ;
			 startActivity(intent);
			 
//			 intent = new Intent(MainActivity.this , TestTActivity.class) ;
//			 startActivity(intent);
			 break ;		 

		default:
			break;
		}
		
	}

}
