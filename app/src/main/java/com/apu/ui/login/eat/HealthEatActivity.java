package com.apu.ui.login.eat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apu.ui.login.BaseActivity;
import com.uphealth.cn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 健康吃 
 * @data 2016年5月22日

 * @author jun.wang
 */
public class HealthEatActivity extends BaseActivity implements OnClickListener {
	List<View> viewLists ;
	private TextView text_health , text_exercise , text_beauty , text_style ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_eat);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("阿噗");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.right).setBackgroundResource(R.drawable.icon_share);
		
		text_health = (TextView)this.findViewById(R.id.text_health) ;
		text_exercise = (TextView)this.findViewById(R.id.text_exercise) ;
		text_beauty = (TextView)this.findViewById(R.id.text_beauty) ;
		text_style = (TextView)this.findViewById(R.id.text_style) ;
		findViewById(R.id.text_health).setOnClickListener(this);
		findViewById(R.id.text_exercise).setOnClickListener(this);
		findViewById(R.id.text_beauty).setOnClickListener(this);
		findViewById(R.id.text_style).setOnClickListener(this);
		viewLists = new ArrayList<View>() ;
		viewLists.clear();
		viewLists.add(text_health) ;
		viewLists.add(text_exercise) ;
		viewLists.add(text_beauty) ;
		viewLists.add(text_style) ;
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_health:
			 uplateState(text_health) ;
			 break ;
			 
		case R.id.text_exercise:
			 uplateState(text_exercise) ;
			 break ;
			 
		case R.id.text_beauty:
			 uplateState(text_beauty) ;
			 break ;
			 
		case R.id.text_style:
			 uplateState(text_style) ;
			 break ;

		default:
			break;
		}
	}
	
	private void toward(final int index){
		
		switch (index) {
		case 1:
			uplateState(text_health) ;
			break;

		default:
			break;
		}
	}
	
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
