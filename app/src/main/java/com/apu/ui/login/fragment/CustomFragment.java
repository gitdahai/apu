package com.apu.ui.login.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uphealth.cn.R;

/**
 * @description 定制 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class CustomFragment extends Fragment {
    private View view ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_custom, container, false);
		return view ;
	}

}
