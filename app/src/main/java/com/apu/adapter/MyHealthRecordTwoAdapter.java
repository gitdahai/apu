package com.apu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uphealth.cn.R;

public class MyHealthRecordTwoAdapter extends BaseAdapter {

	private Context context ;
	LayoutInflater inflater ;
	
	private String[] str = new String[]{
		"平和" , "气虚" ,"阴虚" ,"阳虚","痰湿","湿热","气郁","血淤","特禀"
	} ;
	
	public MyHealthRecordTwoAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return str.length;
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
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_my_health_record_two, null) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			viewHolder.layout = (FrameLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		if(position == 0 || position == 6){
			viewHolder.layout.setBackgroundResource(R.drawable.box_light_cornor);
		}else if (position == 1 || position == 4 || position == 7) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_middle_cornor);
		}else if (position == 2 || position == 3 || position == 5 || position == 8) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_deep_cornor);
		}
		
		viewHolder.text.setText(str[position]);
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text ;
		FrameLayout layout ;
	}

}
