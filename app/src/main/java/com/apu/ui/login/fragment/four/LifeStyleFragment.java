package com.apu.ui.login.fragment.four;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.apu.adapter.LifeStyleAdapter;
import com.uphealth.cn.R;

/**
 * @description 生活方式 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class LifeStyleFragment extends Fragment {
	private View view ;
	private ListView listView ;
	LifeStyleAdapter adapter ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_life_style, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		

	private void init(){
		listView = (ListView)view.findViewById(R.id.listView) ;
		adapter = new LifeStyleAdapter(getActivity()) ;
		listView.setAdapter(adapter);
		
	} 
	 
	 
}
