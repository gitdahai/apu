package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.uphealth.cn.R;

/**
 * @description 我的健康体质 
 * @data 2016年5月17日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class MyHealthRecordAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	private float BMI = 0 ;
	
	private String[] str = new String[]{
		"过低BMI" , "较低BMI" ,"正常BMI" ,"较高BMI"	
	} ;
	
	public MyHealthRecordAdapter(Context context , float bmi){
		this.context = context ;
		this.BMI = bmi ;
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
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_my_health_record, null) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			viewHolder.layout = (FrameLayout)convertView.findViewById(R.id.layout) ;
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		if(position == 0){
			viewHolder.layout.setBackgroundResource(R.drawable.box_one_cornor);
			viewHolder.text.setText(str[position]);
			
			if(BMI <= 0.2){
				viewHolder.imageView.setVisibility(View.VISIBLE);
			}else {
				viewHolder.imageView.setVisibility(View.GONE);
			}
			
		}else if (position == 1) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_two_cornor);
			viewHolder.text.setText(str[position]);
			
			if(BMI <= 0.4 && BMI > 0.2){
				viewHolder.imageView.setVisibility(View.VISIBLE);
			}else {
				viewHolder.imageView.setVisibility(View.GONE);
			}
			
		}else if (position == 2) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_three_cornor);
			viewHolder.text.setText(str[position]);
			viewHolder.imageView.setVisibility(View.VISIBLE);
			
			if(BMI <= 0.6 && BMI > 0.4){
				viewHolder.imageView.setVisibility(View.VISIBLE);
			}else {
				viewHolder.imageView.setVisibility(View.GONE);
			}
			
		}else if (position == 3) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_four_cornor);
			viewHolder.text.setText(str[position]);
			
			if(BMI <= 10 && BMI > 0.6){
				viewHolder.imageView.setVisibility(View.VISIBLE);
			}else {
				viewHolder.imageView.setVisibility(View.GONE);
			}
			
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text ;
		FrameLayout layout ;
		ImageView imageView ;
	}

}
