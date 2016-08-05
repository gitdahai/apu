package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uphealth.cn.R;

/**
 * 站内信功能
 * @description 
 * @data 2016年7月25日

 * @author jun.wang
 */
public class AppInstationAdapter extends BaseAdapter {
	
	public Context context ;
	LayoutInflater inflater ;
	
    public AppInstationAdapter(Context context){
    	this.context = context ;
    	inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
    } 
	
	@Override
	public int getCount() {
		return 6;
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
			convertView = inflater.inflate(R.layout.item_app_instation, null) ;
		}
		
		return convertView;
	}
	
	

}
