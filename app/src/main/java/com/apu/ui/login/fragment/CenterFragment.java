package com.apu.ui.login.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uphealth.cn.R;

/**
 * @description 个人中心 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class CenterFragment extends Fragment {
    private View view ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_center, container, false);
		return view ;
	}

}
