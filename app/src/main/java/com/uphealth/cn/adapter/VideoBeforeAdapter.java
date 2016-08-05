package com.uphealth.cn.adapter;

import com.uphealth.cn.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class VideoBeforeAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public VideoBeforeAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 1;
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
			convertView = inflater.inflate(R.layout.item_common_trans, null) ;
		}
		
		return convertView;
	}
	
	

}
