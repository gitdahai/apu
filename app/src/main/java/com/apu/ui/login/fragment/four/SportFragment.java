package com.apu.ui.login.fragment.four;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.apu.adapter.SportAdapter;
import com.apu.ui.login.fragment.HtmlActivity;
import com.apu.widget.MyListView;
import com.uphealth.cn.R;

/**
 * @description 运动界面
 * @data 2016年5月26日

 * @author jun.wang
 */
public class SportFragment extends Fragment implements OnClickListener {
	private View view ;
	private View bottomView ;
	private MyListView listView ;
	SportAdapter adapter ; 
	LayoutInflater inflater ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_sport, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		
	
	private void init(){
		listView = (MyListView)view.findViewById(R.id.listView) ;
		adapter = new SportAdapter(getActivity()) ;
		listView.setAdapter(adapter);
		inflater = LayoutInflater.from(getActivity()) ;
		bottomView = inflater.inflate(R.layout.view_buttom_sport, null) ;
		listView.addFooterView(bottomView);
		bottomView.findViewById(R.id.up_plan).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.up_plan:
			 Intent intent = new Intent(getActivity() , HtmlActivity.class) ;
			 intent.putExtra("titleStr", "阿噗的运动-多次") ;
			 intent.putExtra("url", "https://www.baidu.com") ;
			 intent.putExtra("noRight", "noRight") ;
			 startActivity(intent);
			 break;

		default:
			break;
		}
		
	} 

}
