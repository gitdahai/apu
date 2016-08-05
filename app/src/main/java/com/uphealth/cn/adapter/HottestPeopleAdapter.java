package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uphealth.cn.R;

/**
 * 最热达人
 * @description 
 * @data 2016年7月24日

 * @author jun.wang
 */
public class HottestPeopleAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	
	public HottestPeopleAdapter(Context context){
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
			convertView = inflater.inflate(R.layout.item_hottest_people, null) ;
			viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.layout.removeAllViews(); 
		for(int i = 0 ; i < 3 ; i ++){
			 View view = inflater.inflate(R.layout.item_view_image, null) ;
			 view.setPadding(30, 0, 30, 0);
			 ImageView imageView = (ImageView)view.findViewById(R.id.image) ;
			 
			 viewHolder.layout.addView(view);
		}
		
//		viewHolder.layout.removeAllViews(); 
//		for(int i = 0 ; i < 3 ; i ++){
//			 ImageView imageView = new ImageView(context) ;
//			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100) ;
//			 imageView.setBackgroundResource(R.drawable.test_01);
//			 imageView.setPadding(30, 0, 30, 0);
//			 imageView.setLayoutParams(params);
//			 imageView.setTag(i);
//			 viewHolder.layout.addView(imageView);
//		}
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout layout ;
		
	}

}
