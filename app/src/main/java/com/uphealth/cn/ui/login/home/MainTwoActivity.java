package com.uphealth.cn.ui.login.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.ui.login.eat.HateFoodActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 阿噗主页面的另一种展示 
 * @data 2016年5月22日

 * @author jun.wang
 */
public class MainTwoActivity extends FragmentActivity implements OnClickListener {
	List<View> viewLists ;
	private TextView text_health , text_exercise , text_beauty , text_style ;
	private int index = 1;
	
	private Fragment[] mFragments;  
    private FragmentManager fragmentManager;  
    private FragmentTransaction fragmentTransaction;  
	private ImageView menu_image ;
	private LinearLayout layout_tap , layout_title;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setContentView(R.layout.activity_main_two);
	    
	    index = getIntent().getIntExtra("index", 0) ;
	    init() ;
	}
	
	private void init(){
		mFragments = new Fragment[6];  
        fragmentManager = getSupportFragmentManager();  
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_health_eat); 
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_sport); 
        mFragments[2] = fragmentManager  
                .findFragmentById(R.id.fragement_beauty_adorn);  
        mFragments[3] = fragmentManager  
                .findFragmentById(R.id.fragement_life_style);  
        mFragments[4] = fragmentManager  
                .findFragmentById(R.id.fragement_life_find);  
        mFragments[5] = fragmentManager  
                .findFragmentById(R.id.fragement_life_center);  
		
		((TextView)findViewById(R.id.title)).setText("阿噗");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.back).setVisibility(View.GONE);
		findViewById(R.id.text_right).setBackgroundResource(R.drawable.icon_share);
		findViewById(R.id.right).setOnClickListener(this);
		findViewById(R.id.navi_item_home).setOnClickListener(this); 
		findViewById(R.id.navi_item_personal).setOnClickListener(this);
		layout_tap = (LinearLayout)this.findViewById(R.id.layout_tap) ;
		layout_title = (LinearLayout)this.findViewById(R.id.layout_title) ;
		findViewById(R.id.title_line).setVisibility(View.GONE);
		
		text_health = (TextView)this.findViewById(R.id.text_health) ;
		text_exercise = (TextView)this.findViewById(R.id.text_exercise) ;
		text_beauty = (TextView)this.findViewById(R.id.text_beauty) ;
		text_style = (TextView)this.findViewById(R.id.text_style) ;
		findViewById(R.id.text_health).setOnClickListener(this);
		findViewById(R.id.text_exercise).setOnClickListener(this);
		findViewById(R.id.text_beauty).setOnClickListener(this);
		findViewById(R.id.text_style).setOnClickListener(this);
		findViewById(R.id.menu_image).setOnClickListener(this);
		viewLists = new ArrayList<View>() ;
		viewLists.clear();
		viewLists.add(text_health) ;
		viewLists.add(text_exercise) ;
		viewLists.add(text_beauty) ;
		viewLists.add(text_style) ;
		toward(index) ;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null ; 
		fragmentTransaction = fragmentManager.beginTransaction()  
	              .hide(mFragments[0]).hide(mFragments[1])  
	              .hide(mFragments[2]).hide(mFragments[3])
	              .hide(mFragments[4])
	              .hide(mFragments[5]) ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_health:
			 layout_tap.setVisibility(View.VISIBLE);	
			 layout_title.setVisibility(View.VISIBLE);	
			 fragmentTransaction.show(mFragments[0]).commit(); 
			 uplateState(text_health) ;
			 break ;
			 
		case R.id.text_exercise:
			 layout_tap.setVisibility(View.VISIBLE);	
			 layout_title.setVisibility(View.VISIBLE);	
			 fragmentTransaction.show(mFragments[1]).commit(); 
			 uplateState(text_exercise) ;
			 break ;
			 
		case R.id.text_beauty:
			 layout_tap.setVisibility(View.VISIBLE);	
			 layout_title.setVisibility(View.VISIBLE);	
			 fragmentTransaction.show(mFragments[2]).commit(); 
			 uplateState(text_beauty) ;
			 break ;
			 
		case R.id.text_style:
			 layout_tap.setVisibility(View.VISIBLE);
			 layout_title.setVisibility(View.VISIBLE);	
			 fragmentTransaction.show(mFragments[3]).commit(); 
			 uplateState(text_style) ;
			 break ;
			 
		case R.id.right:
			 intent = new Intent(MainTwoActivity.this , HateFoodActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 回到主页	 
		case R.id.menu_image:
			 intent = new Intent(MainTwoActivity.this , MainActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 热门主页	 
		case R.id.navi_item_home:
//			 intent = new Intent(MainTwoActivity.this , HotActivity.class) ;
//			 startActivity(intent);
			 
			 layout_title.setVisibility(View.GONE);	
			 layout_tap.setVisibility(View.GONE);
			 fragmentTransaction.show(mFragments[4]).commit(); 
			 
			 break ;
		
		// 个人头像	 
		case R.id.navi_item_personal:
		     layout_title.setVisibility(View.GONE);	
		     layout_tap.setVisibility(View.GONE);
			 fragmentTransaction.show(mFragments[5]).commit(); 
			
//			 intent = new Intent(MainTwoActivity.this , PersonalActivity.class) ;
//			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}
	
	private void toward(final int index){
		fragmentTransaction = fragmentManager.beginTransaction()  
	              .hide(mFragments[0]).hide(mFragments[1])  
	              .hide(mFragments[2]).hide(mFragments[3])
	              .hide(mFragments[4])
	              .hide(mFragments[5]) ;
		
		switch (index) {
		case 1:
			fragmentTransaction.show(mFragments[0]).commit(); 
			mFragments[0].onStart();
			uplateState(text_health) ;
			break;
			
		case 2:
			fragmentTransaction.show(mFragments[1]).commit(); 
			uplateState(text_exercise) ;
		    break ;	   
		    
		case 3:
			fragmentTransaction.show(mFragments[2]).commit(); 
			uplateState(text_beauty) ;
		    break ;
		    
		case 4:
			fragmentTransaction.show(mFragments[3]).commit(); 
			uplateState(text_style) ;
		    break ;		
		    
		case 5:
			fragmentTransaction.show(mFragments[4]).commit(); 
			break ;
			
		case 6:
			fragmentTransaction.show(mFragments[5]).commit(); 
			break ;

		default:
			break;
		}
	}
	
//	@Override
//	protected void onStart() {
//		super.onStart();
//		
//		System.out.println("onStartonStartonStartonStart");
//		
//		toward(index) ;
//		
//	}
	
	private void uplateState(TextView textView){
		Drawable drawable= getResources().getDrawable(R.drawable.main_line);  
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
		
		for(int i = 0 ; i < viewLists.size() ; i ++){
			  
			   if(viewLists.get(i).equals(textView)){
				   textView.setCompoundDrawables(null,null,null,drawable);  
			   }else {
				   ((TextView)viewLists.get(i)).setCompoundDrawables(null,null,null,null);  
		       }
		}
		
	}	

	

}
