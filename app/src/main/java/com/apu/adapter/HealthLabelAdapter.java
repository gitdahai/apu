package com.apu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;

public class HealthLabelAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	boolean isFlag = true ;
	
	public HealthLabelAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 3;
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
			convertView = inflater.inflate(R.layout.item_health_label, null) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final TextView label = viewHolder.text ;
		viewHolder.text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(isFlag){
					label.setBackgroundResource(R.drawable.label_no_round_cornor);
					label.setTextColor(context.getResources().getColor(R.color.text_main));
					isFlag = false ;
				}else {
					label.setBackgroundResource(R.drawable.label_round_cornor);
					label.setTextColor(context.getResources().getColor(R.color.white));
					isFlag = true ;
				}
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text ;
	}

}
