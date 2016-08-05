package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;

import java.util.ArrayList;
import java.util.List;

public class HealthLabelTwoAdapter extends BaseAdapter {

	private Context context ;
	LayoutInflater inflater ;
	private List<String> list = new ArrayList<String>() ;
	boolean isFlag = true ;
	
	public HealthLabelTwoAdapter(Context context , List<String> list){
		this.context = context ;
		this.list = list ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return list.size();
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
		
		viewHolder.text.setText(list.get(position));
		
		final TextView label = viewHolder.text ;
		viewHolder.text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(isFlag){
					label.setBackgroundResource(R.drawable.label_no_round_cornor_new);
					label.setTextColor(context.getResources().getColor(R.color.text_main));
					isFlag = false ;
				}else {
					label.setBackgroundResource(R.drawable.label_round_new);
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
