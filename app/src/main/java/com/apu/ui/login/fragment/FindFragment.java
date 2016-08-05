package com.apu.ui.login.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apu.adapter.CircleAdapter;
import com.apu.adapter.MainListAdapter;
import com.apu.adapter.MainMenuAdapter;
import com.apu.bean.FunctionBean;
import com.apu.interf.ScrollViewListener;
import com.apu.ui.login.HealthInfoSetting;
import com.apu.ui.login.home.MainTwoActivity;
import com.apu.widget.DragGridViewOlder;
import com.apu.widget.MyGallery;
import com.apu.widget.MyListView;
import com.apu.widget.ObservableScrollView;
import com.uphealth.cn.R;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 发现主界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint("ClickableViewAccessibility")
public class FindFragment extends Fragment implements OnClickListener, ScrollViewListener {
	private View view ;
	private MyGallery gallery ;
	CircleAdapter adapter ;
	
	private DragGridViewOlder gridview ;
	private ObservableScrollView scrollview ;
	MainMenuAdapter menuAdapter ;
	private List<FunctionBean> list;
	
	private MyListView listView ;
	MainListAdapter listAdapter ;
	private LinearLayout layout_two ;
	
	List<View> viewLists ;
	private TextView text_health , text_exercise , text_beauty , text_style ;
	private int index = 1;
	
	private boolean flag = false ;
	private Handler handler = new Handler() ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_find, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  	
	 
	private void init(){
		gallery = (MyGallery)view.findViewById(R.id.gallery) ;
		adapter = new CircleAdapter(getActivity()) ;
		gallery.setAdapter(adapter);
		view.findViewById(R.id.back).setVisibility(View.GONE);
		view.findViewById(R.id.right).setVisibility(View.VISIBLE);
		((TextView)view.findViewById(R.id.right)).setText("偏好");
		view.findViewById(R.id.right).setOnClickListener(this);
		((TextView)view.findViewById(R.id.title)).setText("阿噗");
		layout_two = (LinearLayout)view.findViewById(R.id.layout_two) ;
		
		text_health = (TextView)view.findViewById(R.id.text_health) ;
		text_exercise = (TextView)view.findViewById(R.id.text_exercise) ;
		text_beauty = (TextView)view.findViewById(R.id.text_beauty) ;
		text_style = (TextView)view.findViewById(R.id.text_style) ;
		text_health.setOnClickListener(this);
		text_exercise.setOnClickListener(this);
		text_beauty.setOnClickListener(this);
		text_style.setOnClickListener(this);
		viewLists = new ArrayList<View>() ;
		viewLists.clear();
		viewLists.add(text_health) ;
		viewLists.add(text_exercise) ;
		viewLists.add(text_beauty) ;
		viewLists.add(text_style) ;
		
		list = new ArrayList<FunctionBean>() ;
		gridview = (DragGridViewOlder)view.findViewById(R.id.gridView) ;
    	scrollview = (ObservableScrollView)view.findViewById(R.id.scrollview) ;
    	scrollview.setScrollViewListener(this);
//    	scrollview.setScrollViewListener(new ScrollViewListener() {
//			@Override
//			public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
//					int oldx, int oldy) {
//				
////				if(y > 380){
////					layout_two.setVisibility(View.VISIBLE);
////					gridview.setVisibility(View.GONE);
////					gallery.setVisibility(View.GONE);
////				}
//			}
//		});
    	
    	gridview.setFocusable(false);
    	gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = null ;
				
				switch (position) {
				case 0:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 100);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(1) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 1) ;
					startActivity(intent);
					break;
					
				case 1:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(2) ;
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 2) ;
					startActivity(intent);
					break ;
					
				case 2:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(3) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 3) ;
					startActivity(intent);
					break ; 
					
				case 3:
//					GlobalData.isStay = false ;
//					scrollview.scrollTo(0, 0);
//					scrollview.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in));  
//					toward(4) ;
					
					intent = new Intent(getActivity() , MainTwoActivity.class) ;
					intent.putExtra("index", 4) ;
					startActivity(intent);
					break ; 			

				default:
					break;
				}
			}
		});
    	
    	listView = (MyListView)view.findViewById(R.id.listView) ;
    	listAdapter = new MainListAdapter(getActivity()) ;
    	listView.setAdapter(listAdapter);
    	listView.setFocusable(false);
    	
    	initGridData() ;
    	
	}

	private void initGridData(){
		FunctionBean bean = new FunctionBean() ;
		list.add(bean) ;
		
		FunctionBean bean2 = new FunctionBean() ;
		list.add(bean2) ;
		
		FunctionBean bean3 = new FunctionBean() ;
		list.add(bean3) ;
		
		FunctionBean bean4 = new FunctionBean() ;
		list.add(bean4) ;
		
		gridview.setItems(list);
    	menuAdapter = new MainMenuAdapter(getActivity()) ;
    	gridview.setAdapter(menuAdapter);
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.right:
			intent = new Intent(getActivity() , HealthInfoSetting.class) ;
			startActivity(intent);
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

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		
//		if(!GlobalData.isStay && y > 20){
//			 gridview.setVisibility(View.VISIBLE);
//			 gallery.setVisibility(View.VISIBLE);
//			 handler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
////					layout_two.setVisibility(View.VISIBLE);
////					gridview.setVisibility(View.GONE);
////					gallery.setVisibility(View.GONE);
//					
//					Intent intent = new Intent(getActivity() , MainTwoActivity.class) ;
//					startActivity(intent);
//				}
//			}, 1000) ; 
//		}else {
//			
//			if(y > 380){
//				
////				gallery.setVisibility(View.GONE);
////				gridview.setVisibility(View.GONE);
////				layout_two.setVisibility(View.VISIBLE);
//			}
//			
//		}
		
	} 
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
    private void toward(final int index){
		
		switch (index) {
		case 1:
			uplateState(text_health) ;
			break;
			
		case 2:
			uplateState(text_exercise) ;
		    break ;	   
		    
		case 3:
			uplateState(text_beauty) ;
		    break ;
		    
		case 4:
			uplateState(text_style) ;
		    break ;		    

		default:
			break;
		}
	}
	
	private void uplateState(final TextView textView){
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
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
		}, 1000) ;
	}		
	
}
