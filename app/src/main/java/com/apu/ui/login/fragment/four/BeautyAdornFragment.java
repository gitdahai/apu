package com.apu.ui.login.fragment.four;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apu.adapter.BeautyAdornAdapter;
import com.apu.widget.MyListView;
import com.uphealth.cn.R;

/**
 * @description 美妆护理 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class BeautyAdornFragment extends Fragment {
	private View view ;
	private MyListView listView ;
	BeautyAdornAdapter adapter ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_beauty_adorn, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		
	 
	private void init(){
		listView = (MyListView)view.findViewById(R.id.listView) ;
		adapter = new BeautyAdornAdapter(getActivity()) ;
		listView.setAdapter(adapter);
		
	} 
	
}
