package com.apu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uphealth.cn.R;

/**
 * @description 所在我位置数据适配器
 * @data 2016年5月25日

 * @author jun.wang
 */
public class AddressAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public AddressAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 8;
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
			convertView = inflater.inflate(R.layout.item_address, null) ;
		}
		
		
		return convertView;
	}

}
