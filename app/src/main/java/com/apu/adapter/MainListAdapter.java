package com.apu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uphealth.cn.R;

/**
 * @description 首页列表适配器 
 * @data 2016年5月15日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class MainListAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public MainListAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}
	
	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(null == convertView){
			convertView = inflater.inflate(R.layout.item_main_list, null) ;
		}
		
		return convertView;
	}

}
